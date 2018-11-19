package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class createAccount extends Activity implements View.OnClickListener {

    EditText username, email, phone;
    Button create_account;
    boolean usernameOk, emailOk, phoneOk;
    String usernameError;
    String emailError;
    String phoneError;
    boolean goodUser;


    Intent next_activity;

    RadioButton caregiverButton, patientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

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
                //Validate the username
                if (username.getText().toString().length() == 0) {
                    usernameError = "Username cannot be empty.";
                    goodUser = false;
                } else if (username.getText().toString().length() < 8){
                    usernameError = "Username size must be a minimum of 8 characters.";
                    goodUser = false;

                }
                else if (User.validateUser(username.getText().toString())) {
                    usernameOk = true;

                } else {
                    usernameError = "Username Already in Use, Please Choose Another.";
                    goodUser = false;
                }

                //Validate the Email
                if (User.validateEmail(email.getText().toString())) {
                    emailOk = true;
                } else if (email.getText().toString().length() == 0) {
                    emailError = "Email cannot be empty.";
                    goodUser = false;
                } else {
                    emailError = "Invalid email entered.";
                    goodUser = false;
                }


                //Validate the Phone
                if (User.validatePhone(phone.getText().toString())) {
                    phoneOk = true;
                } else if (phone.getText().toString().length() == 0) {
                    phoneError = "Phone number cannot be empty";
                    goodUser = false;
                } else {
                    phoneError = "Invalid phone number. Should be in (XXX)XXX-XXXX format.";
                    goodUser = false;
                }

                //Check all validations
                if (goodUser == true) {
                    //No errors, proceed with the creation of a user
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






    @Override
    public void onClick(View v){

    }
}