
package ca.ualberta.symptomaticapp;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import ca.ualberta.symptomaticapp.ListProblemsActivity;
import ca.ualberta.symptomaticapp.Problem;
import ca.ualberta.symptomaticapp.ProblemListController;
import ca.ualberta.symptomaticapp.R;


public class AddProblemActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener DateSetListener;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);


        Button dateButton = findViewById(R.id.AddDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int currentYear = cal.get(Calendar.YEAR);
                int currentMonth = cal.get(Calendar.MONTH);
                int currentDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddProblemActivity.this, android.R.style.Theme_DeviceDefault_Dialog, DateSetListener, currentYear, currentMonth, currentDay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();
            }
        });

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int chosenYear, int chosenMonth, int chosenDay) {
                year = chosenYear;
                month = chosenMonth;
                day = chosenDay;
            }
        };


    }

    public void addProblem(View v){

        EditText editTextTitle = findViewById(R.id.EnterTitleEditText);
        String title = editTextTitle.getText().toString();
        EditText editTextDescription = findViewById(R.id.EnterDescriptionEditText);
        String description = editTextDescription.getText().toString();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Boolean goodProblem = true;
        if (title.length() == 0) {
            AlertDialog.Builder noTitleDialog = new AlertDialog.Builder(AddProblemActivity.this);
            noTitleDialog.setMessage("Title cannot be empty");
            noTitleDialog.show();
            goodProblem = false;
        }
        if (year == 0) {
            AlertDialog.Builder noDateDialog = new AlertDialog.Builder(AddProblemActivity.this);
            noDateDialog.setMessage("Date cannot be empty");
            noDateDialog.show();
            goodProblem = false;
        }

        if (description.length() == 0) {
            AlertDialog.Builder noDescriptionDialog = new AlertDialog.Builder(AddProblemActivity.this);
            noDescriptionDialog.setMessage("Description cannot be empty");
            noDescriptionDialog.show();
            goodProblem = false;
        }

        if (goodProblem == true) {
            ProblemListController pt = new ProblemListController();
            Problem newProblem = new Problem(title, cal.getTime(), description);
            pt.addProblem(newProblem);
            Intent intent = new Intent(AddProblemActivity.this, ListProblemsActivity.class);
            startActivity(intent);
        }




    }





}
