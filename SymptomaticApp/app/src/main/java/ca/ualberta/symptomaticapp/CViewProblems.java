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
    private ArrayList<Problem> displayList; //list of problems to display
    private String active_problem_count; //string for holding the number of active problems of selected patient
    public static String currentpatient; //holds the username of the selected patient
    private TextView numproblems; //textview for displaying number of problems
    private CProblemsAdapter listAdapter; //our custom listview adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview_problems);
        //get data passed to this activity
        Intent intent = getIntent();
        currentpatient = intent.getExtras().getString("username");
        //


        //setup custom toolbar
        Toolbar toolbar = findViewById(R.id.cviewProblems_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Problems of Patient");
        //end of custom toolbar setup

        displayList = new ArrayList<Problem>(); //make it an arraylist of problems

        //get UI elements
        numproblems = (TextView) findViewById(R.id.tv_ProbNum); //need to change this text when view button pressed
        final Spinner patient = (Spinner) findViewById(R.id.sp_Patients); //need to get selections
        Button viewproblems = (Button) findViewById(R.id.btn_View);
        final Button viewcontactinfo = (Button) findViewById(R.id.btn_viewcontactinfo);
        ListView problemview = (ListView) findViewById(R.id.lv_problems);
        //


        Caregiver caregiver = Login.thisCaregiver; //get current logged in caregiver

        ArrayList<String> patients = caregiver.getPatients(); //get caregivers patients
        int passeduser = patients.indexOf(currentpatient); //find index of given username

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, patients);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //set spinner to use basic view
        patient.setAdapter(adapter); //set the spinner adapter
        patient.setSelection(passeduser); //set the selected value of spinner to be the given user.

        //SETUP LISTVIEW ADAPTER
        getProblems(currentpatient); //fills display list with problems of currentpatient
        listAdapter = new CProblemsAdapter(displayList, this); //initialize our custom adapter.
        problemview.setAdapter(listAdapter); //set our listview to use the custom adapter.

        viewproblems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //view problems button listener
                // take current selected patient and populate list view using their problems
                currentpatient= patient.getSelectedItem().toString(); //update currentpatient value
                displayList.clear(); //empty problem list
                getProblems(currentpatient); //fill problem list with new patient's problems.
            }
        });
        viewcontactinfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //viewcontactinfo button listener
                Intent intent = new Intent(CViewProblems.this, ViewContactInfo.class);
                intent.putExtra("username", currentpatient); //pass username and user type to viewcontactinfo
                intent.putExtra("usertype", "user");
                startActivity(intent);
            }
        });
    }
    //toolbar functions
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.caregiver_general_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(CViewProblems.this, CaregiverHome.class);
        startActivity(intent);
    }
    public void viewViewPatients(MenuItem menu) { //open viewpatients activity
        Intent intent = new Intent(CViewProblems.this, ViewPatients.class);
        startActivity(intent);
    }

    public void viewAddPatients(MenuItem menu) { //open add patients activity
        Intent intent = new Intent(CViewProblems.this, AddPatientActivity.class);
        startActivity(intent);
    }

    public void viewViewQR(MenuItem menu) {
        //adds the view problems button to the menu
        Intent intent = new Intent(CViewProblems.this, CaregiverViewQRCode.class);
        startActivity(intent);
    }

    public void viewLogout(MenuItem menu){ //log current caregiver out and open login page
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(CViewProblems.this, MainActivity.class);
        startActivity(intent);
    }
    /**
     * Fills displayList with the problems of the given username by accessing our database and querying for the correct problems.
     * @param username: The username that the problems are associated to
     */
    private void getProblems(String username){
        //get db instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final CollectionReference problems = db.collection("problems"); //looking at problems

        Query problemsQuery = problems.whereEqualTo("user",username); //look at problems associated with given username

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){ //query didn't fail
                    for(QueryDocumentSnapshot document: task.getResult()){ //for each problem that fits query
                        Problem problem = document.toObject(Problem.class); //create object from given data
                        displayList.add(problem); //add it to our problem
                    }
                    listAdapter.notifyDataSetChanged(); //update adapter
                    if (displayList != null) {
                        active_problem_count = "Number of active problems:"+" " + displayList.size(); //update string and textview
                        numproblems.setText(active_problem_count);
                    } else { //display list is null and therefore user has no problems
                        active_problem_count = "Number of active problems: 0";
                        numproblems.setText(active_problem_count);
                    }
                } else {
                    AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(CViewProblems.this);
                    badUsernameDialog.setMessage("Unable to load problem's list due to corruption.");
                    badUsernameDialog.show();
                }
            }
        });
    }

}
