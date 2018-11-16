/*
Activity for the caregiver to enter a patient's username that they wish to add to their list of patients.
Username will be verified and added upon verification. Caregiver can also cancel the activity to return
back to their list of patients.
 */
package ca.ualberta.symptomaticapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        Button cancel = (Button) findViewById(R.id.btn_Cancel);
        Button addpatient = (Button) findViewById(R.id.btn_addpatient);
        cancel.setOnClickListener(new View.OnClickListener() { // on cancel click
            @Override
            public void onClick(View v) {
                finish(); //finish this activity and go back
            }
        });

        addpatient.setOnClickListener(new View.OnClickListener() { //on add patient click
            @Override
            public void onClick(View v) {
                EditText entry = (EditText) findViewById(R.id.et_username); //find the edittext
                String content= entry.getText().toString(); //get their entry

            }
        });
    }
}
