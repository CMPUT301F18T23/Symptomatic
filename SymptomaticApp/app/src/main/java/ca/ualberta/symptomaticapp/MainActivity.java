/*
 * MainActivity.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Home screen for patient view.
 *
 * Issues: No current issues.
 *
 */

package ca.ualberta.symptomaticapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.REGULARFONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.button_container), iconFont);

        //initialize a toolbar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home Page");

        //initialize the welcome text message
        TextView textView = (TextView) findViewById(R.id.HelloUserTextView);

        //initialize the add view problems button, and set it on click
        Button ViewProblemsButton = (Button) findViewById(R.id.ViewProblemsButton);
        ViewProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sends user to the view problems activity
                Intent intent = new Intent(MainActivity.this, ListProblemsActivity.class);
                startActivity(intent);
            }
        });

        Button viewContact = (Button) findViewById(R.id.ViewContactInfo);
        viewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sends user to the view problems activity
                Intent intent = new Intent(MainActivity.this, EditAccountActivity.class);
                startActivity(intent);
            }
        });

        //check to see if there is a user currently logged in
        if (Login.thisUser == null && Login.thisCaregiver == null) {
            //if no users are logged in, sends the user to the login page
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        } else {
            //sets the welcome message to greet the user
            textView.setText("Hello "+Login.thisUser.returnUsername()+"!");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //creates the menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }
    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(MainActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }
    public void viewAddProblem(MenuItem menu) {
        //adds the Add problem button to the menu
        Intent intent = new Intent(MainActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }


    public void viewViewProblems(MenuItem menu) {
        //adds the view problems button to the menu
        Intent intent = new Intent(MainActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        //adds the view problems button to the menu
        Intent intent = new Intent(MainActivity.this, ViewQRCode.class);
        startActivity(intent);
    }

    public void viewSearch(MenuItem menu){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void viewLogout(MenuItem menu){
        //adds the logout button to the menu
        //sets both thisCaregiver and thisUser to null, and refreshes the page, effectively sending them to the login page.
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
