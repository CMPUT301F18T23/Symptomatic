/*
An activity that only the caregiver will be able to see and is used for viewing the records of a specific problem.
The caregiver will select a patient through the spinner,select a problem as well, and then be presented with a list of
records associated with that patient's problem. The caregiver can also view that patients' contact information.
 */

package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CViewRecords extends AppCompatActivity {
    private String passeduser; //for holding the passed data
    private String passedprob;
    private TextView numrecords; //textview for displaying the number of records
    private ArrayList<Problem> problemlist; //list for holding the problems of current patient
    private ArrayList<String> problemtitles;
    private ArrayAdapter<String> problemadapter;
    private CRecordAdapter recordadapter;
    private ArrayList<String> recordtitles = new ArrayList<String>();

    final ArrayList<Record> records = new ArrayList<>();

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview_records);

        //get data given to us through intent
        Intent intent = getIntent();
        passedprob = intent.getExtras().getString("problem");
        passeduser = intent.getExtras().getString("username");


        //declare empty lists
        problemtitles = new ArrayList<String>();
        problemlist = new ArrayList<Problem>();
        //fill problem list with the passeduser's problem
        getProblems(passeduser,passedprob);

        for (Problem problem : problemlist) {
            problemtitles.add(problem.getTitle()); //get the problems titles into a separate list for the spinner
        }

        // get all ui elements
        numrecords = (TextView) findViewById(R.id.tv_numprob);
        final Spinner selectpatient = (Spinner) findViewById(R.id.sp_Patient);
        final Spinner selectproblem = (Spinner) findViewById(R.id.sp_Problems);
        final Button viewproblem = (Button) findViewById(R.id.btn_ViewProblems);
        Button viewpatient = (Button) findViewById(R.id.btn_ViewPatient);
        Button viewphotoalbum = (Button) findViewById(R.id.btn_ViewPhotos);
        Button viewcontactinfo = (Button) findViewById(R.id.btn_ViewContactInfo);
        ListView recordview = (ListView) findViewById(R.id.lv_records);
        //

        Caregiver caregiver = Login.thisCaregiver; //get current logged in caregiver
        final ArrayList<String> patients = caregiver.getPatients(); //get their patients.

        //setup adapters and assign them
        ArrayAdapter<String> patientadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, patients);
        problemadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, problemtitles);
        patientadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectpatient.setAdapter(patientadapter);
        problemadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectproblem.setAdapter(problemadapter);
        selectpatient.setSelection(patients.indexOf(passeduser));
        selectproblem.setSelection(problemlist.indexOf(passedprob));
        problemadapter.notifyDataSetChanged();

        //POPULATE LISTVIEW USING THE SELECTED PROBLEM


        recordadapter = new CRecordAdapter(records, this);

        recordview.setAdapter(recordadapter);

        viewpatient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // take current selected patient and populate list view using their problems
                // GET PROBLEMS OF SELECTED USER HERE USING THE SELECTED INDEX
                problemlist.clear();
                problemtitles.clear();
                records.clear();
                getProblems(selectpatient.getItemAtPosition(selectpatient.getSelectedItemPosition()).toString(), selectproblem.getItemAtPosition(selectproblem.getSelectedItemPosition()).toString());
                // POPULATE THE SPINNER FOR PROBLEMS USING ABOVE
                problemadapter.notifyDataSetChanged();
                recordadapter.notifyDataSetChanged();
            }
        });

        viewproblem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // take current selected problem and show records related to it
                getProblems(selectproblem.getItemAtPosition(selectproblem.getSelectedItemPosition()).toString(),selectpatient.getItemAtPosition(selectpatient.getSelectedItemPosition()).toString()); //get current selection
                recordadapter.notifyDataSetChanged();
            }
        });

        viewcontactinfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CViewRecords.this, ViewContactInfo.class);
                intent.putExtra("username", selectpatient.getItemAtPosition(selectpatient.getSelectedItemPosition()).toString());
                intent.putExtra("usertype", "user");
                startActivity(intent); //open view contact info using the correct information
            }
        });
    }
    private void getProblems(final String username, final String prob) {
        //looking at problems
        db = FirebaseFirestore.getInstance();
        CollectionReference problems = db.collection("problems");
        //query for appropriate problems related to username
        Query problemsQuery = problems.whereEqualTo("user", username);
        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) { //query has a result
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Problem problem = document.toObject(Problem.class); //convert all found problems to problem objects
                        problemtitles.add(problem.getTitle()); //get all problems into our problemList
                        CollectionReference recordscol = db.collection("records");
                        Query recordsQuery = recordscol.whereEqualTo("problem",prob).whereEqualTo("user",username);
                        recordsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document: task.getResult()) {
                                    Record record = document.toObject(Record.class);
                                    records.add(record);
                                    recordtitles.add(record.getTitle());
                                    recordadapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
