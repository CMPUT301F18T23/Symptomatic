/*
 * AddProblemActivity.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Adds a user input problem into database.
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddProblemActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener DateSetListener;

    Calendar cal;

    TextView chosenDate;

    EditText editTextTitle,editTextDescription;

    String chosenDateText,title,description;

    SimpleDateFormat dateFormatter;

    LocalSave offlineFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        initLayout();

        Button dateButton = findViewById(R.id.AddDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(AddProblemActivity.this, android.R.style.Theme_DeviceDefault_Dialog, DateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();
            }
        });

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int chosenYear, int chosenMonth, int chosenDay) {
                cal.set(chosenYear, chosenMonth, chosenDay);
                updateTime();
            }
        };


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_problem_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(AddProblemActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(AddProblemActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(AddProblemActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(AddProblemActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public void addProblem(View v){
        title = editTextTitle.getText().toString();
        description = editTextDescription.getText().toString();

        Boolean goodProblem = true;
        if (title.length() == 0) {
            AlertDialog.Builder noTitleDialog = new AlertDialog.Builder(AddProblemActivity.this);
            noTitleDialog.setMessage("Title cannot be empty.");
            noTitleDialog.show();
            goodProblem = false;
        }

        if (cal.getTime().after(new Date())){
            AlertDialog.Builder noTitleDialog = new AlertDialog.Builder(AddProblemActivity.this);
            noTitleDialog.setMessage("The Date cannot be in the future.");
            noTitleDialog.show();
            goodProblem = false;
        }

        if (description.length() == 0) {
            AlertDialog.Builder noDescriptionDialog = new AlertDialog.Builder(AddProblemActivity.this);
            noDescriptionDialog.setMessage("Description cannot be empty.");
            noDescriptionDialog.show();
            goodProblem = false;
        }

        if (goodProblem) {
            Problem newProblem = new Problem(title, cal.getTime(), description);
            newProblem.addProbToDb();
            finish();
            //Intent intent = new Intent(AddProblemActivity.this, ListProblemsActivity.class);
            //startActivity(intent);
        }
    }

    private void updateTime(){
        chosenDateText = "Chosen Date: "+dateFormatter.format(cal.getTime());
        chosenDate.setText(chosenDateText);
    }

    private void initLayout(){
        Toolbar toolbar = findViewById(R.id.addProblem_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Problem");

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        editTextTitle = findViewById(R.id.EnterTitleEditText);
        editTextDescription = findViewById(R.id.EnterDescriptionEditText);

        cal = Calendar.getInstance();

        chosenDate = findViewById(R.id.dateTextView);

        updateTime();
    }






}
