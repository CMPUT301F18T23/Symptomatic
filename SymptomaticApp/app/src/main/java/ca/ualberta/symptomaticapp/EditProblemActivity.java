/*
 * EditProblemActivity.java
 *
 * Version 2
 *
 * December, 3, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Activity for a user to edit their problems. The can change the title, date, and description.
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class EditProblemActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener DateSetListener;

    private int year;
    private int month;
    private int day;
    private Date thisDate;

    private Problem problem;

    FirebaseFirestore db;

    EditText editTitleEditText,editDescriptionEditText;

    TextView dateTextView;

    Calendar cal;

    Button editProbButton, deleteProbButton;
    SimpleDateFormat dateFormatter;
    String chosenDateText;


    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_edit_problem);
        Toolbar toolbar = findViewById(R.id.editProblem_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Problem");

        db = FirebaseFirestore.getInstance();

        problem = (Problem) getIntent().getSerializableExtra("problem");

        cal = Calendar.getInstance();
        cal.setTime(problem.getDate());
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        Button dateButton = findViewById(R.id.selectNewDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatePickerDialog dialog = new DatePickerDialog(EditProblemActivity.this, android.R.style.Theme_DeviceDefault_Dialog, DateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();
            }
        });

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int chosenYear, int chosenMonth, int chosenDay) {
                cal.set(chosenYear, chosenMonth, chosenDay);
                chosenDateText = "Chosen date: " + dateFormatter.format(cal.getTime());
                dateTextView.setText(chosenDateText);

            }
        };

        editTitleEditText = findViewById(R.id.editTitleEditText);
        editTitleEditText.setText(problem.getTitle());

        editDescriptionEditText = findViewById(R.id.editDescriptionEditText);
        editDescriptionEditText.setText(problem.getComment());


        dateTextView = findViewById(R.id.currentDateTextView);
        chosenDateText = "Chosen date: " + dateFormatter.format(problem.getDate());
        dateTextView.setText(chosenDateText);

        editProbButton = findViewById(R.id.saveProblemButton);
        editProbButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editProblem();
            }
        });
        deleteProbButton = findViewById(R.id.deleteProblemButton);
        deleteProbButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteProblem();
            }
        });

    }

    // Confirm with user that they want to exit without saving changes
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditProblemActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(EditProblemActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(EditProblemActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }

    public void viewAddProblem(MenuItem menu){
        Intent intent = new Intent(EditProblemActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(EditProblemActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        Intent intent = new Intent(EditProblemActivity.this, ViewQRCode.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(EditProblemActivity.this, MainActivity.class);
        startActivity(intent);
    }




    public void editProblem(){
        db = FirebaseFirestore.getInstance();

        CollectionReference problems = db.collection("problems");

        Query problemsQuery = problems.whereEqualTo("title",problem.getTitle()).whereEqualTo("user",Login.thisUser.username);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        //update records to proper title
                        CollectionReference records = db.collection("records");

                        Query recordsQuery = records.whereEqualTo("problem",problem.getTitle()).whereEqualTo("user",Login.thisUser.username);

                        recordsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document: task.getResult()) {
                                    String recordDocID = document.getId();
                                    DocumentReference thisDocument = db.collection("records").document(recordDocID);
                                    thisDocument.update("problem",editTitleEditText.getText().toString());
                                }
                            }
                        });

                        //update problem to new information
                        String problemDocID = document.getId();
                        DocumentReference thisDocument = db.collection("problems").document(problemDocID);
                        thisDocument.update("title", editTitleEditText.getText().toString(), "date", cal.getTime(), "comment", editDescriptionEditText.getText().toString());
                    }
                }
                finish();
            }
        });
    }
    public void deleteProblem(){
        db = FirebaseFirestore.getInstance();

        CollectionReference problems = db.collection("problems");

        Query problemsQuery = problems.whereEqualTo("title",problem.getTitle()).whereEqualTo("user",Login.thisUser.username);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        CollectionReference records = db.collection("records");

                        Query recordsQuery = records.whereEqualTo("problem",problem.getTitle()).whereEqualTo("user",Login.thisUser.username);

                        recordsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document: task.getResult()) {
                                    String recordDocID = document.getId();
                                    DocumentReference thisDocument = db.collection("records").document(recordDocID);
                                    thisDocument.delete();
                                }
                            }
                        });

                        String problemDocID = document.getId();
                        DocumentReference thisDocument = db.collection("problems").document(problemDocID);
                        thisDocument.delete();
                    }
                }
                finish();
            }
        });
    }
}
