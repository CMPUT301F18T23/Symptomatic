package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home Page");

        TextView textView = (TextView) findViewById(R.id.HelloUserTextView);

        Button AddProblemButton = (Button) findViewById(R.id.AddProblemButton);
        AddProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProblemActivity.class);
                startActivity(intent);
            }
        });

        Button AddRecordButton = (Button) findViewById(R.id.ViewProblemsButton);
        AddRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListProblemsActivity.class);
                startActivity(intent);
            }
        });

        if (Login.thisUser == null && Login.thisCaregiver == null) {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        } else {
            textView.setText("Hello "+Login.thisUser.returnUsername()+"!");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }
    public void viewAddProblem(MenuItem menu) {
        Intent intent = new Intent(MainActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(MainActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }

    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
