/*
Activity for the caregiver to enter a patient's username that they wish to add to their list of patients.
Username will be verified and added upon verification. Caregiver can also cancel the activity to return
back to their list of patients.
 */
package ca.ualberta.symptomaticapp;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AddPatient extends AppCompatActivity {
    public static User addedpatient;
    private Caregiver caregiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        //setup our custom toolbar
        Toolbar toolbar = findViewById(R.id.addPatient_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Patient");
        //end of toolbar setup.

        caregiver = Login.thisCaregiver; //get caregiver from login page.
        //get buttons from UI
        Button cancel = (Button) findViewById(R.id.btn_Cancel);
        Button addpatient = (Button) findViewById(R.id.btn_addpatient);
        Button scanqr = (Button) findViewById(R.id.btn_addpatientqr);
        cancel.setOnClickListener(new View.OnClickListener() { // on cancel click
            @Override
            public void onClick(View v) { //cancel button listener
                finish(); //finish this activity and go back, dont want to do intents, because couldve come from several places
            }
        });

        addpatient.setOnClickListener(new View.OnClickListener() { //on add patient click
            @Override
            public void onClick(View v) { //add patient button listener
                EditText entry = (EditText) findViewById(R.id.et_username); //find the edittext
                final String content= entry.getText().toString(); //get their entry
                validEntry(content, "patient");
            }
        });
    }
    public void scanqr(View v){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null && scanResult.getContents()!=null) {

            String entry = scanResult.getContents();
            validEntry(entry.split(",")[0],entry.split(",")[1]);

        }

    }
    public boolean validEntry(final String username, String type){
        //check if scanned user is a patient/caregiver
        if (type.contains("caregiver")){
            Toast.makeText(this, "User is not a patient!", Toast.LENGTH_SHORT).show();
            return false;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance(); //get our db
        CollectionReference active_users = db.collection("users"); //looking at collection patients

        //Build the query
        Query query = active_users
                .whereEqualTo("username",username);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            //If Query Worked on not
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    //Query Worked
                    if (task.getResult().size() == 1){
                        //A user with that username exists
                        for(QueryDocumentSnapshot document: task.getResult()){
                            caregiver.addPatient(username); //add the patient
                            Toast.makeText(AddPatient.this, "Patient added!", Toast.LENGTH_SHORT).show(); //display message.
                            Intent intent = new Intent(AddPatient.this, ViewPatients.class);
                            startActivity(intent); //go to the view patients activity.
                        }
                    } else if (username=="") {
                        //No users with that username exists
                        Toast.makeText(AddPatient.this, "Username not entered.", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    else {
                        //No users with that username exists
                        Toast.makeText(AddPatient.this, "User Does Not Exist.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Query Did not Work
                    Toast.makeText(AddPatient.this, "Load Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return false;
    }

    // Confirm with user that they want to exit without saving changes
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit without saving?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddPatient.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    //toolbar methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_patient_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) { //opens home page for caregiver
        Intent intent = new Intent(AddPatient.this, CaregiverHome.class);
        startActivity(intent);
    }

    public void viewViewPatients(MenuItem menu) { //opens view patients for caregiver
        Intent intent = new Intent(AddPatient.this, ViewPatients.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){ //logs user out, and sends them to the login screen
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(AddPatient.this, MainActivity.class);
        startActivity(intent);
    }
}
