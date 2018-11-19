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

import java.util.ArrayList;
import java.util.List;

public class ViewPatients extends AppCompatActivity {
    public Caregiver caregiver;
    ArrayList<String> patients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);

        Toolbar toolbar = findViewById(R.id.viewPatient_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Patients");

        if (Login.thisUser == null && Login.thisCaregiver == null) {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }

        caregiver= Login.thisCaregiver; //fetch current caregiver.
        if (caregiver != null) {
            patients = caregiver.getPatients();
            ListView patientsview = (ListView) findViewById(R.id.lv_Patients);
            Button addpatient = (Button) findViewById(R.id.btn_AddPatient);
            List<String> usernames = new ArrayList<String>();
            for(String patient : patients){
                usernames.add(patient);
            }
            ArrayAdapter<String> patientsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usernames);
            patientsview.setAdapter(patientsadapter);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_patients_menu, menu);
        return true;
    }

    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(ViewPatients.this, ViewPatients.class);
        startActivity(intent);
    }
}
