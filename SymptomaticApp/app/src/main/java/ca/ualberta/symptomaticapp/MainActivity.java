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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.HelloUserTextView);
        textView.setText("Hello <User> !");

        Button AddRecordButton = (Button) findViewById(R.id.AddProblemButton);
        AddRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProblemActivity.class);
                startActivity(intent);


            }
        });

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
