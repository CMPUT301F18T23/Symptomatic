/*
 * ViewFullProblemActivity.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Activity to view a patients full problem.
 *
 * Issues: Edit problem button currently not being used.
 *
 */

package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViewFullProblemActivity extends AppCompatActivity {

    Problem problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_problem);
        Toolbar toolbar = findViewById(R.id.viewFullProblem_toolbar);
        setSupportActionBar(toolbar);


        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        problem = (Problem)getIntent().getSerializableExtra("problem");

        getSupportActionBar().setTitle(problem.getTitle());


        TextView textView1 = findViewById(R.id.titleTextView);
        textView1.setText(problem.getTitle());

        TextView textView2 = findViewById(R.id.dateTextView);
        textView2.setText(problem.getDate().toString());

        TextView textView3 = findViewById(R.id.descriptionTextView);
        textView3.setText(problem.getComment());

        Button AddRecordButton = (Button) findViewById(R.id.addRecordButton);
        AddRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewFullProblemActivity.this, AddRecordActivity.class);
                startActivity(intent);
            }
        });

        Button viewProblemsButton = (Button) findViewById(R.id.viewProblemsButton);
        viewProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewFullProblemActivity.this, ListProblemsActivity.class);
                startActivity(intent);
            }
        });

        /*Button EditProblemButton = findViewById(R.id.editButton);
        EditProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewFullProblemActivity.this, EditProblemActivity.class);
                intent.putExtra("problem", problem);
                startActivity(intent);
            }
        }); */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(ViewFullProblemActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void viewAddProblem(MenuItem menu){
        Intent intent = new Intent(ViewFullProblemActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(ViewFullProblemActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(ViewFullProblemActivity.this, MainActivity.class);
        startActivity(intent);
    }


}

