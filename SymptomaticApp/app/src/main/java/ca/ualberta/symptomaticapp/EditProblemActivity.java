/*
 * EditProblemActivity.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Activity for a user to edit their problems. The can change the title, date, and description.
 *
 * Issues: Currently unable to edit problems in the database, thus code is commented out.
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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

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

    Calendar cal;

    Button editProbButton, deleteProbButton;

    @Override
    protected void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_edit_problem);

        db = FirebaseFirestore.getInstance();

        problem = (Problem) getIntent().getSerializableExtra("problem");

        Button dateButton = findViewById(R.id.selectNewDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                cal.setTime(problem.getDate());
                int currentYear = cal.get(Calendar.YEAR);
                int currentMonth = cal.get(Calendar.MONTH);
                int currentDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditProblemActivity.this, android.R.style.Theme_DeviceDefault_Dialog, DateSetListener, currentYear, currentMonth, currentDay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();
            }
        });

        editTitleEditText = findViewById(R.id.editTitleEditText);
        editTitleEditText.setText(problem.getTitle());

        editDescriptionEditText = findViewById(R.id.editDescriptionEditText);
        editDescriptionEditText.setText(problem.getComment());

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

    public void editProblem(){
        db = FirebaseFirestore.getInstance();

        CollectionReference problems = db.collection("problems");

        Query problemsQuery = problems.whereEqualTo("title",problem.getTitle());

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String docID = document.getId();
                        DocumentReference thisDocument = db.collection("problems").document(docID);
                        thisDocument.update("title", editTitleEditText.getText().toString(), "date", cal.getTime(), "comment", editDescriptionEditText.getText().toString());
                    }
                }
            }
        });
    }
    public void deleteProblem(){
        db = FirebaseFirestore.getInstance();

        CollectionReference problems = db.collection("problems");

        Query problemsQuery = problems.whereEqualTo("title",problem.getTitle());

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String docID = document.getId();
                        DocumentReference thisDocument = db.collection("problems").document(docID);
                        thisDocument.update("title", editTitleEditText.getText().toString(), "date", cal.getTime(), "comment", editDescriptionEditText.getText().toString());
                    }
                }
            }
        });
    }
}
