package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EditAccountActivity extends AppCompatActivity {

    String UName;
    String email;
    String phoneNumber;

    Boolean goodName;
    Boolean goodEmail;
    Boolean goodPhone;

    String nameError;
    String emailError;
    String phoneError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        Toolbar toolbar = findViewById(R.id.editAccount_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Account");

        TextView usernameTextView = findViewById(R.id.usernameEditText);
        usernameTextView.setText(Login.thisUser.username);

        TextView emailTextView = findViewById(R.id.emailAddressEditText);
        emailTextView.setText(Login.thisUser.email);

        TextView phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        phoneNumberEditText.setText(Login.thisUser.phone);
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
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void editInfo(View V) {

        TextView usernameTextView = findViewById(R.id.usernameEditText);
        UName = usernameTextView.getText().toString();

        TextView emailTextView = findViewById(R.id.emailAddressEditText);
        email = emailTextView.getText().toString();

        TextView phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        phoneNumber = phoneNumberEditText.getText().toString();

        // Check for bad username
        if (UName.length() == 0){
            goodName = false;
            nameError = "Username cannot be empty.";
        } else if (UName.length() < 8){
            goodName = true;
            nameError = "Username size must be a minimum of 8 characters.";
        }

        // Check for bad email
        if (email.length() == 0){
            goodEmail = false;
            emailError = "Email cannot be empty";
        }






    }

}
