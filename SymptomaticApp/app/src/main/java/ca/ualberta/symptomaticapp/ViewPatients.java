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
import android.view.View;
import android.widget.Button;

public class ViewPatients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);
        Button addpatient = (Button) findViewById(R.id.btn_AddPatient);
        addpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPatients.this, AddPatient.class));
            }
        });
    }
}
