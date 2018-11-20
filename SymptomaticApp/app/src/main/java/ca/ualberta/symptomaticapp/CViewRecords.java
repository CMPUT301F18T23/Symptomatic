/*
An activity that only the caregiver will be able to see and is used for viewing the records of a specific problem.
The caregiver will select a patient through the spinner,select a problem as well, and then be presented with a list of
records associated with that patient's problem. The caregiver can also view that patients' contact information.
 */

package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CViewRecords extends AppCompatActivity {
    int selectedpatient;
    int selectedproblem;
    String passeduser;
    Problem passedprob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview_records);
        Intent intent = getIntent();
        passeduser = intent.getExtras().getString("username");

        // get all ui elements
        final Spinner selectpatient = (Spinner) findViewById(R.id.sp_Patient);
        final Spinner selectproblem = (Spinner) findViewById(R.id.sp_Problems);
        final Button viewproblem = (Button) findViewById(R.id.btn_ViewProblems);
        viewproblem.setEnabled(false);
        Button viewpatient = (Button) findViewById(R.id.btn_ViewPatient);
        Button viewphotoalbum = (Button) findViewById(R.id.btn_ViewPhotos);
        Button viewcontactinfo = (Button) findViewById(R.id.btn_ViewContactInfo);
        TextView numprob = (TextView) findViewById(R.id.tv_numprob);
        ListView recordview = (ListView) findViewById(R.id.lv_records);
        //
        Caregiver caregiver = Login.thisCaregiver;
        ArrayList<String> patients = caregiver.getPatients();

        final List<String> usernames = new ArrayList<String>();;
        for(String user : patients){
            usernames.add(user);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, usernames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectpatient.setAdapter(adapter);


        viewpatient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // take current selected patient and populate list view using their problems
                viewproblem.setEnabled(true); //allow view contact info to be able to be pressed
                String selection = selectpatient.getSelectedItem().toString(); //get current selection
                selectedpatient = usernames.indexOf(selection);
                // GET PROBLEMS OF SELECTED USER HERE USING THE SELECTED INDEX
                // POPULATE THE SPINNER FOR PROBLEMS USING ABOVE
            }
        });

        viewproblem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // take current selected problem and show records related to it
                String selection = selectproblem.getSelectedItem().toString(); //get current selection
                selectedproblem = usernames.indexOf(selection);
            }
        });

        //POPULATE LISTVIEW USING THE SELECTED PROBLEM

    }
}
