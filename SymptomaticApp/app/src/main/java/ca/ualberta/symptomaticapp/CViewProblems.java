/*
An activity that only the caregiver will be able to see and is used for viewing the problems of a specific patient.
The caregiver will select a patient through the spinner, press the View button and be presented with a list of
problems associated with that patient. The caregiver can also view that patients' contact information.
 */

package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.List;


public class CViewProblems extends AppCompatActivity {
    private ArrayList<Problem> displayList;
    private String active_problem_count;
    public static String currentpatient;
    private TextView numproblems;
    private CProblemsAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview_problems);
        Intent intent = getIntent();
        currentpatient = intent.getExtras().getString("username");
        //selecteduser is the user that was selected beforehand to view the problems of
        Toolbar toolbar = findViewById(R.id.cviewProblems_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Problems of Patient");
        displayList = new ArrayList<Problem>();

        numproblems = (TextView) findViewById(R.id.tv_ProbNum); //need to change this text when view button pressed
        final Spinner patient = (Spinner) findViewById(R.id.sp_Patients); //need to get selections
        Button viewproblems = (Button) findViewById(R.id.btn_View);
        final Button viewcontactinfo = (Button) findViewById(R.id.btn_viewcontactinfo);
        ListView problemview = (ListView) findViewById(R.id.lv_problems);

        Caregiver caregiver = Login.thisCaregiver;

        ArrayList<String> patients = caregiver.getPatients();

        final List<String> usernames = new ArrayList<String>();
        for(String user : patients){
            usernames.add(user);
        }
        int passeduser = usernames.indexOf(currentpatient);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, usernames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patient.setAdapter(adapter);
        patient.setSelection(passeduser);

        //SETUP LISTVIEW ADAPTER
        getProblems(currentpatient);
        listAdapter = new CProblemsAdapter(displayList, this);
        problemview.setAdapter(listAdapter);

        viewproblems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // take current selected patient and populate list view using their problems
                viewcontactinfo.setEnabled(true); //allow view contact info to be able to be pressed
                currentpatient= patient.getSelectedItem().toString(); //get current selection
                displayList.clear();
                getProblems(currentpatient);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cview_problems_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(CViewProblems.this, CaregiverHome.class);
        startActivity(intent);
    }
    public void viewViewRecords(MenuItem menu) {
        Intent intent = new Intent(CViewProblems.this, CViewRecords.class);
        startActivity(intent);
    }public void viewViewPatients(MenuItem menu) {
        Intent intent = new Intent(CViewProblems.this, ViewPatients.class);
        startActivity(intent);
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
                        displayList.add(problem);
                    }
                    listAdapter.notifyDataSetChanged();
                    if (displayList != null) {
                        active_problem_count = "Number of active problems:"+" " + displayList.size();
                        numproblems.setText(active_problem_count);
                    } else {
                        active_problem_count = "Number of active problems: 0";
                        numproblems.setText(active_problem_count);
                    }
                } else {
                    AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(CViewProblems.this);
                    badUsernameDialog.setMessage("Data Load Error");
                    badUsernameDialog.show();
                }
            }
        });
        for(Problem problem: displayList){
            problem.updateRecords();
        }
    }

}
