package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static FirebaseDatabase database;
    public User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();




        database = FirebaseDatabase.getInstance();

        DatabaseReference userRef = database.getReference("users");
        userRef.child("alanisawesome").setValue(new User("alanisawesome","(780)481-3905","alan@hotmail.com"));

        TextView textView = (TextView) findViewById(R.id.HelloUserTextView);
        textView.setText("Hello <User> !");

        Button AddProblemButton = (Button) findViewById(R.id.AddProblemButton);
        AddProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProblemActivity.class);
                startActivity(intent);
            }
        });

        Button AddRecordButton = (Button) findViewById(R.id.AddRecordButton);
        AddRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                startActivity(intent);
            }
        });

        Intent login = new Intent(this, Login.class);
        startActivity(login);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
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
    public void viewAddRecord(MenuItem menu) {
        Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
        startActivity(intent);
    }








}
