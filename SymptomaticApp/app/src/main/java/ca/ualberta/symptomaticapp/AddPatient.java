/*
Activity for the caregiver to enter a patient's username that they wish to add to their list of patients.
Username will be verified and added upon verification. Caregiver can also cancel the activity to return
back to their list of patients.
 */
package ca.ualberta.symptomaticapp;
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

public class AddPatient extends AppCompatActivity {
    public static User addedpatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        Toolbar toolbar = findViewById(R.id.addPatient_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Patient");


        final Caregiver caregiver = Login.thisCaregiver;
        Button cancel = (Button) findViewById(R.id.btn_Cancel);
        Button addpatient = (Button) findViewById(R.id.btn_addpatient);
        cancel.setOnClickListener(new View.OnClickListener() { // on cancel click
            @Override
            public void onClick(View v) {
                finish(); //finish this activity and go back, dont want to do intents, because couldve come from several places
            }
        });

        addpatient.setOnClickListener(new View.OnClickListener() { //on add patient click
            @Override
            public void onClick(View v) {
                EditText entry = (EditText) findViewById(R.id.et_username); //find the edittext
                final String content= entry.getText().toString(); //get their entry
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference active_users = db.collection("users");

                //Build the query
                Query query = active_users
                        .whereEqualTo("username",content);

                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    //If Query Worked on not
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            //Query Worked
                            if (task.getResult().size() == 1){
                                //A user with that username exists
                                for(QueryDocumentSnapshot document: task.getResult()){
                                    addedpatient = document.toObject(User.class);
                                    caregiver.addPatient(addedpatient.returnUsername());
                                    Toast.makeText(AddPatient.this, "Patient added!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } else if (content.isEmpty()) {
                                //No users with that username exists
                                Toast.makeText(AddPatient.this, "Username not entered.", Toast.LENGTH_SHORT).show();

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
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_patient_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(AddPatient.this, MainActivity.class);
        startActivity(intent);
    }

    public void viewAddPatients(MenuItem menu) {
        Intent intent = new Intent(AddPatient.this, AddPatient.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(AddPatient.this, MainActivity.class);
        startActivity(intent);
    }
}
