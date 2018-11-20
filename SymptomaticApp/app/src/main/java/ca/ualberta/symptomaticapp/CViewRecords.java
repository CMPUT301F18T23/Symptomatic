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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CViewRecords extends AppCompatActivity {
    String passeduser;
    Integer passedprob;
    TextView numrecords;
    private ArrayList<Problem> problemlist;
    private ArrayList<Record> recordList;
    private ArrayList<String> problemtitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview_records);
        Intent intent = getIntent();
        passeduser = intent.getExtras().getString("username");
        passedprob = intent.getExtras().getInt("problem");

        problemtitles = new ArrayList<String>();
        problemlist = new ArrayList<Problem>();
        recordList = new ArrayList<Record>();

        getProblems(passeduser);
        for(Problem problem: problemlist){
            problemtitles.add(problem.getTitle());
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

        Caregiver caregiver = Login.thisCaregiver;
        final ArrayList<String> patients = caregiver.getPatients();


        ArrayAdapter<String> patientadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, patients);

        final ArrayAdapter<String> problemadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, problemtitles);

        patientadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectpatient.setAdapter(patientadapter);
        problemadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectproblem.setAdapter(problemadapter);


        //POPULATE LISTVIEW USING THE SELECTED PROBLEM
        ArrayList<String> recordtitles = new ArrayList<String>();
        for(Record record: recordList){
            recordtitles.add(record.getTitle());
        }
        ArrayAdapter<String> recordadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recordtitles);
        recordview.setAdapter(recordadapter);

        viewpatient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // take current selected patient and populate list view using their problems
                viewproblem.setEnabled(true); //allow view contact info to be able to be pressed
                passeduser = selectpatient.getSelectedItem().toString(); //get current selection

                // GET PROBLEMS OF SELECTED USER HERE USING THE SELECTED INDEX
                getProblems(passeduser);
                // POPULATE THE SPINNER FOR PROBLEMS USING ABOVE
                problemtitles.clear();
                for(Problem problem: problemlist){
                    problemtitles.add(problem.getTitle());
                }
                problemadapter.notifyDataSetChanged();

            }
        });

        viewproblem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // take current selected problem and show records related to it
                String selection = selectproblem.getSelectedItem().toString(); //get current selection

            }
        });

        viewcontactinfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CViewRecords.this, ViewContactInfo.class);
                intent.putExtra("username", passeduser);
                intent.putExtra("usertype", "user");
                startActivity(intent);

            }
        });



    }

    private void getProblems(String username){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference problems = db.collection("problems");

        Query problemsQuery = problems.whereEqualTo("user",username);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Problem problem = document.toObject(Problem.class);
                        problemlist.add(problem);
                    }
                    if (problemlist != null) {
                        //FIX LATER//
                        numrecords.setText("Number of active records: 0");
                    }
                } else {
                    AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(CViewRecords.this);
                    badUsernameDialog.setMessage("Data Load Error");
                    badUsernameDialog.show();
                }
            }
        });
        for(Problem problem: problemlist){
            problem.updateRecords();
        }
    }
}
