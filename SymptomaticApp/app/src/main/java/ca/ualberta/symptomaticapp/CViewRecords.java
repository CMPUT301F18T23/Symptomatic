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
    private Problem passedprob;
    private TextView numrecords; //textview for displaying the number of records
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
        passedprob = (Problem)getIntent().getSerializableExtra("problem");
        passeduser = intent.getExtras().getString("username");
        //declare empty lists
        //fill problem list with the passeduser's problem
        getProblems(passeduser,passedprob.getTitle());


        // get all ui elements
        numrecords = (TextView) findViewById(R.id.tv_numprob);

        Button viewgeolocations = (Button) findViewById(R.id.btn_ViewGeos);
        Button viewcontactinfo = (Button) findViewById(R.id.btn_ViewContactInfo);
        ListView recordview = (ListView) findViewById(R.id.lv_records);

        TextView tvusername = (TextView) findViewById(R.id.tv_username);
        TextView tvproblem = (TextView) findViewById(R.id.tv_problem);
        //

        tvusername.setText(passeduser);
        tvproblem.setText(passedprob.getTitle());

        Caregiver caregiver = Login.thisCaregiver; //get current logged in caregiver
        final ArrayList<String> patients = caregiver.getPatients(); //get their patients.

        //POPULATE LISTVIEW USING THE SELECTED PROBLEM
        recordadapter = new CRecordAdapter(records, this);

        recordview.setAdapter(recordadapter);

        viewcontactinfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CViewRecords.this, ViewContactInfo.class);
                intent.putExtra("username", passeduser);
                intent.putExtra("usertype", "user");
                startActivity(intent); //open view contact info using the correct information
            }
        });
        viewgeolocations.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CViewRecords.this, CMapOfRecords.class);
                intent.putExtra("problem", passedprob);
                startActivity(intent); //open view contact info using the correct information
            }
        });
    }
    private void getProblems(final String username, final String prob) {
        //looking at problems
        db = FirebaseFirestore.getInstance();
        CollectionReference problems = db.collection("records");
        //query for appropriate problems related to username
        Query problemsQuery = problems.whereEqualTo("user", username).whereEqualTo("problem",prob);
        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) { //query has a result
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Record record = document.toObject(Record.class);
                        records.add(record);
                        recordtitles.add(record.getTitle());
                        recordadapter.notifyDataSetChanged();
                        numrecords.setText("Number of records: " + records.size());
                    }
                }
            }
        });
    }
}
