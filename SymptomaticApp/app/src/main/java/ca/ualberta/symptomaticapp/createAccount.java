package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class createAccount extends Activity implements View.OnClickListener {

    EditText username,email,phone;
    Button create_account;
    boolean usernameOk,emailOk,phoneOk;
    String errormsg;

    Toast toast;

    Intent next_activity;

    RadioButton caregiverButton,patientButton;

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

        caregiverButton = findViewById(R.id.careProviderRadioButton);
        patientButton = findViewById(R.id.patientRadioButton);

        errormsg = "";

        create_account.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                errormsg = "";
                //Validate the username
                if (User.validateUser(username.getText().toString())) {
                    usernameOk = true;
                } else {
                    errormsg += "Username Already in Use, Please Choose Another";
                }

                //Validate the Email
                if (User.validateEmail(email.getText().toString())) {
                    emailOk = true;
                } else {
                    if (errormsg.equals("")){
                        errormsg += "Invalid email entered, please fix";
                    } else{
                        errormsg += "\nInvalid email entered, please fix";
                    }
                }

                //Validate the Phone
                if (User.validatePhone(phone.getText().toString())){
                    phoneOk = true;
                } else {
                    if (errormsg.equals("")){
                        errormsg += "Invalid phone entered, please fix";
                    } else {
                        errormsg += "\nInvalid phone entered, please fix";
                    }
                }

                //Check all validations
                if (errormsg.equals("")){
                    //No errors, proceed with the creation of a user
                    if (patientButton.isChecked()) {
                        User newUser = User.createNewUser(username.getText().toString(), phone.getText().toString(), email.getText().toString());
                        Login.thisUser = newUser;
                        Login.thisCaregiver = null;
                        next_activity = new Intent(createAccount.this,MainActivity.class);
                    } else {
                        Caregiver newCaregiver = Caregiver.createNewCaregiver(username.getText().toString(), phone.getText().toString(), email.getText().toString());
                        Login.thisUser = null;
                        Login.thisCaregiver = newCaregiver;
                        next_activity = new Intent(createAccount.this,ViewPatients.class);
                    }
                    startActivity(next_activity);
                } else {
                    //Errors found, post messages to correct
                    toast = Toast.makeText(getApplication(),errormsg,Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }

    @Override
    public void onClick(View v){

    }
}