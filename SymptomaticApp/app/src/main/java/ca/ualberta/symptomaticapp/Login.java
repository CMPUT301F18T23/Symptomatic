/*
 * Login.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Activity to log a patient in to the app.
 *
 * Issues: Current issue is that if a user leaves a space after their username it will not register.
 *
 */

package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;


public class Login extends AppCompatActivity implements View.OnClickListener{

    Button create_button, login_button;
    TextView input_user;
    Intent next_activity;

    RadioButton patientLogin,caregiverLogin;

    String thisUserType;

    public static User thisUser;
    public static Caregiver thisCaregiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        //initialize buttons
        create_button = findViewById(R.id.login_create_acc_button);
        login_button = findViewById(R.id.login_button);
        
        //set buttons to be on a click listener
        create_button.setOnClickListener(this);
        login_button.setOnClickListener(this);

        //initialize input text
        input_user = findViewById(R.id.enter_user);
        
        //initialize radio buttons to specify a user type
        patientLogin = findViewById(R.id.patientLoginButton);
        caregiverLogin = findViewById(R.id.careproviderLoginButton);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        //check which button is clicked
        if (viewId == R.id.login_create_acc_button){
            //the create account button was clicked, and sends user to the create account activity
            next_activity = new Intent(this,createAccount.class);
            startActivity(next_activity);
        } else if (viewId == R.id.login_button){
            //The login button was clicked, and first gets the input from the login
            final String inputuser = input_user.getText().toString();

            //Access Firestore database
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference active_users;
            
            //checks to see which radio button is checked, which specifies a user type
            if (caregiverLogin.isChecked()){
                thisUserType = "Caregiver";
                active_users = db.collection("caregivers");
            } else {
                thisUserType = "Patient";
                active_users = db.collection("users");
            }

            //Build the query
            Query query = active_users
                    .whereEqualTo("username",inputuser);
           
            //retrieves the query
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                //If Query Worked on not
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        //Query Worked
                        if (task.getResult().size() == 1){
                            //A user with that username exists
                            for(QueryDocumentSnapshot document: task.getResult()){
                                if (patientLogin.isChecked()) {
                                    thisUser = document.toObject(User.class);
                                    thisCaregiver = null;
                                } else {
                                    thisCaregiver = document.toObject(Caregiver.class);
                                    thisUser = null;
                                }
                            }
                            if (patientLogin.isChecked()) {
                                //sends the user to the main activity page if the patient radio button is checked
                                next_activity = new Intent(Login.this,MainActivity.class);
                            } else {
                                //sends the user to the Caregiver homepage if the caregiver button is checked
                                next_activity = new Intent(Login.this,CaregiverHome.class);
                            }
                            if(ListProblemsActivity.thisProbList != null) {
                                ListProblemsActivity.thisProbList.empty();
                                ListProblemsActivity.getProblems(thisUser.returnUsername());
                                ListProblemsActivity.listAdapter.notifyDataSetChanged();
                            } else {
                                ListProblemsActivity.thisProbList = new ProblemList();
                            }
                            startActivity(next_activity);

                        } else if (inputuser.isEmpty()) {
                            //No users with that username exists
                            Toast.makeText(Login.this, "Username not entered.", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            //No users with that username exists
                            Toast.makeText(Login.this, "User Does Not Exist.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Query Did not Work
                        Toast.makeText(Login.this, "Load Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    public void scanqr(View v){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null && scanResult.getContents()!=null) {
            String username = scanResult.getContents().split(",")[0];
            String type= scanResult.getContents().split(",")[1];
            input_user.setText(username);
            if(type.contains("patient")){
                patientLogin.setChecked(true);
                caregiverLogin.setChecked(false);
            }else{
                patientLogin.setChecked(false);
                caregiverLogin.setChecked(true);
            }
            login_button.performClick();
        }

    }
}

