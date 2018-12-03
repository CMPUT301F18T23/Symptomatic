/*
Activity for the caregiver to view the number of patients and the patients they are currently
taking care of. The ListView will allow the caregiver to view the patient, their problems or remove
a patient entirely. At the bottom of the activity lies a button 'Add Patient' which will take the
caregiver to a new page where they can add a patient.
 */

package ca.ualberta.symptomaticapp;

import android.content.Intent;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewPatients extends AppCompatActivity {
    public Caregiver caregiver; //current caregiver
    ArrayList<String> patients; //caregivers patients
    private PatientViewAdapter patientsAdapter; //custom listtview adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);
        //custom toolbar setup
        Toolbar toolbar = findViewById(R.id.viewPatient_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Patients");
        //

        //check if anyone is currently logged in and if not, redirect to login page.
        if (Login.thisUser == null && Login.thisCaregiver == null) {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }

        caregiver= Login.thisCaregiver; //fetch current caregiver.

        // fetch ui elements
        ListView patientsview = (ListView) findViewById(R.id.lv_Patients);
        Button addpatient = (Button) findViewById(R.id.btn_AddPatient);
        TextView numpat = (TextView) findViewById(R.id.tv_NumPatients);

        if (caregiver != null) {
            //get the associated patients
            patients = caregiver.getPatients();
            //set textview value
            numpat.setText("Number of patients: " + patients.size());

            patientsAdapter = new PatientViewAdapter(patients, this);
            patientsview.setAdapter(patientsAdapter);
            addpatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ViewPatients.this, AddPatient.class));
                }
            });
        } else {
            patients = null;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        patientsAdapter.notifyDataSetChanged(); //when coming back from add patient, update incase a patient was added
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_patients_menu, menu);
        return true;
    }

    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(ViewPatients.this, CaregiverHome.class);
        startActivity(intent);
    }

    public void viewAddPatients(MenuItem menu) {
        Intent intent = new Intent(ViewPatients.this, AddPatient.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        //adds the view problems button to the menu
        Intent intent = new Intent(ViewPatients.this, CaregiverViewQRCode.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){ //logs user out
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(ViewPatients.this, Login.class);
        startActivity(intent);
    }
}
