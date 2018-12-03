package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EditAccountActivity extends AppCompatActivity {

    String email;
    String phoneNumber;

    Boolean goodEmail;
    Boolean goodPhone;

    String emailError;
    String phoneError;

    TextView usernameTextView;

    EditText emailTextView,phoneNumberEditText;

    Button editSave,close;
    boolean editing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        Toolbar toolbar = findViewById(R.id.editAccount_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        Typeface otherFont = FontManager.getTypeface(getApplicationContext(), FontManager.BOLDFONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.contactIcons), otherFont);


        usernameTextView = findViewById(R.id.usernameEditText);

        emailTextView = findViewById(R.id.emailAddressEditText);

        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);

        editSave = findViewById(R.id.editSaveButton);
        close = findViewById(R.id.closeButton);

        if(Login.thisUser != null) {
            usernameTextView.setText(Login.thisUser.username);
            emailTextView.setText(Login.thisUser.email);
            phoneNumberEditText.setText(Login.thisUser.phone);
        } else {
            usernameTextView.setText(Login.thisCaregiver.username);
            emailTextView.setText(Login.thisCaregiver.email);
            phoneNumberEditText.setText(Login.thisCaregiver.phone);
        }

        editSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editing = true;
                if (editSave.getText().toString().equals("Edit Info")){
                    enableEdit();
                    editSave.setText("Save");
                } else {
                    editInfo();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }

    // Confirm with user that they want to exit without saving changes
//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setMessage("Are you sure you want to exit without saving?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        EditAccountActivity.super.onBackPressed();
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//    }

    // Confirm with user that they want to exit without saving changes
    @Override
    public void onBackPressed() {
        if (editing) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit without saving?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditAccountActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            super.onBackPressed();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_account_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void viewAddProblems(MenuItem menu) {
        Intent intent = new Intent(EditAccountActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }
    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(EditAccountActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        Intent intent = new Intent(EditAccountActivity.this, ViewQRCode.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void enableEdit(){
        emailTextView.setEnabled(true);
        phoneNumberEditText.setEnabled(true);
    }

    public void disableEdit(){
        emailTextView.setEnabled(false);
        phoneNumberEditText.setEnabled(false);
    }

    public void editInfo() {


        email = emailTextView.getText().toString();
        phoneNumber = phoneNumberEditText.getText().toString();

        // Check for bad email
        if (email.length() == 0){
            goodEmail = false;
            emailError = "Email cannot be empty";
        } else if (!User.validateEmail(email)){
            goodEmail = false;
            emailError = "Email is not in the valid format";
        } else {
            goodEmail = true;
        }

        if (phoneNumber.length() == 0){
            goodPhone = false;
            emailError = "Phone cannot be empty";
        } else if (!User.validatePhone(phoneNumber)){
            goodPhone = false;
            phoneError = "Phone is not in a valid format";
        } else {
            goodPhone = true;
        }

        if (!goodPhone){
            AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(EditAccountActivity.this);
            badUsernameDialog.setMessage(phoneError);
            badUsernameDialog.show();
        }
        if (!goodEmail){
            AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(EditAccountActivity.this);
            badUsernameDialog.setMessage(emailError);
            badUsernameDialog.show();
        }

        if (goodPhone && goodEmail){

            final FirebaseFirestore db = FirebaseFirestore.getInstance();

            Query userQuery;
            CollectionReference user;

            if(Login.thisUser != null) {
                user = db.collection("users");
                userQuery = user.whereEqualTo("username", Login.thisUser.returnUsername());
            } else {
                user = db.collection("caregivers");
                userQuery = user.whereEqualTo("username", Login.thisUser.returnUsername());
            }
            userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(Login.thisUser != null) {
                                Login.thisUser = document.toObject(User.class);
                                String userDocID = document.getId();
                                DocumentReference thisDocument = db.collection("users").document(userDocID);
                                thisDocument.update("phone", phoneNumber, "email", email);
                            } else {
                                Login.thisCaregiver = document.toObject(Caregiver.class);
                                String userDocID = document.getId();
                                DocumentReference thisDocument = db.collection("caregivers").document(userDocID);
                                thisDocument.update("phone", phoneNumber, "email", email);
                            }
                            disableEdit();
                            editSave.setText("Edit Info");
                        }
                    }
                }
            });

        }
    }

}
