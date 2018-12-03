/*
 * createAccount.java
 *
 * Version 2
 *
 * December, 3, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Activity that allows a user to create an account
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class createAccount extends AppCompatActivity implements View.OnClickListener {

    String usernameError,emailError, phoneError;

    static boolean goodUser,usernameOk, emailOk, phoneOk;;

    Intent next_activity;

    RadioButton caregiverButton, patientButton;
    Button create_account;
    EditText username, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = findViewById(R.id.createAccount_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.enterUsernameEditText);
        email = findViewById(R.id.enterEmailEditText);
        phone = findViewById(R.id.enterPhoneEditText);

        create_account = findViewById(R.id.createAccountButton);

        usernameOk = false;
        emailOk = false;
        phoneOk = false;
        goodUser = true;

        caregiverButton = findViewById(R.id.careProviderRadioButton);
        patientButton = findViewById(R.id.patientRadioButton);

        create_account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Validate the data
                validateUser();
                validatePhone();
                validateEmail();

                //Check for Errors
                if(usernameOk && phoneOk && emailOk){
                    goodUser = true;
                } else {
                    goodUser = false;
                }

                //Check all validations
                if (goodUser == true) {
                    //No errors, proceed with the creation of a user
                    //Access Firestore database
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference active_users;

                    if (caregiverButton.isChecked()){
                        active_users = db.collection("caregivers");
                    } else {
                        active_users = db.collection("users");
                    }

                    //Build the query
                    Query query = active_users
                            .whereEqualTo("username",username.getText().toString());

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        //If Query Worked on not
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                //Query Worked
                                if (task.getResult().size() > 0) {
                                    //A user with that username already exists
                                    AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(createAccount.this);
                                    badUsernameDialog.setMessage("Username in use, please choose another...");
                                    badUsernameDialog.show();
                                } else {
                                    if (patientButton.isChecked()) {
                                        User newUser = User.createNewUser(username.getText().toString(), phone.getText().toString(), email.getText().toString());
                                        Login.thisUser = newUser;
                                        Login.thisCaregiver = null;
                                        next_activity = new Intent(createAccount.this, MainActivity.class);
                                    } else {
                                        Caregiver newCaregiver = Caregiver.createNewCaregiver(username.getText().toString(), phone.getText().toString(), email.getText().toString());
                                        Login.thisUser = null;
                                        Login.thisCaregiver = newCaregiver;
                                        next_activity = new Intent(createAccount.this, ViewPatients.class);
                                    }
                                    startActivity(next_activity);
                                }
                            } else {
                                //Query Did not Work
                                Toast.makeText(createAccount.this, "Load Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    if (phoneOk == false) {
                        //Errors found, post messages to correct
                        AlertDialog.Builder badPhoneDialog = new AlertDialog.Builder(createAccount.this);
                        badPhoneDialog.setMessage(phoneError);
                        badPhoneDialog.show();
                    }

                    if (emailOk == false) {
                        //Errors found, post messages to correct
                        AlertDialog.Builder badEmailDialog = new AlertDialog.Builder(createAccount.this);
                        badEmailDialog.setMessage(emailError);
                        badEmailDialog.show();
                    }
                    if (usernameOk == false) {
                        //Errors found, post messages to correct
                        AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(createAccount.this);
                        badUsernameDialog.setMessage(usernameError);
                        badUsernameDialog.show();
                    }
                }
            }
        });
    }

    public void validateUser(){
        //check_unique();
        if (username.getText().toString().length() == 0) {
            //username is empty
            usernameError = "Username cannot be empty.";
            usernameOk = false;
        } else if (username.getText().toString().length() < 8){
            //username is less than 8 characters
            usernameError = "Username size must be a minimum of 8 characters.";
            usernameOk = false;
        } else {
            usernameOk = true;
        }
    }

    public void validatePhone(){
        //Validate the Phone
        if (User.validatePhone(phone.getText().toString())) {
            phoneOk = true;
        } else if (phone.getText().toString().length() == 0) {
            //phone number is empty
            phoneOk = false;
            phoneError = "Phone number cannot be empty.";
        } else {
            //invalid phone number format
            phoneOk = false;
            phoneError = "Invalid phone number. Should be in (XXX)XXX-XXXX format.";
        }
    }

    public void validateEmail(){
        //Validate the Email
        if (User.validateEmail(email.getText().toString())) {
            emailOk = true;
        } else if (email.getText().toString().length() == 0) {
            //email is empty
            emailOk = false;
            emailError = "Email cannot be empty.";
        } else {
            //email address not in a good format
            emailOk = false;
            emailError = "Invalid email entered.";
        }
    }

    @Override
    public void onClick(View v){

    }
}
