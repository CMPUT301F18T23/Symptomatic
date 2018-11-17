package ca.ualberta.symptomaticapp;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class EditProblemActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener DateSetListener;
    private int year;
    private int month;
    private int day;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);

        Collection<Problem> problems = ProblemListController.getProblemList().getProblems();
        final ArrayList<Problem> problemList = new ArrayList<>(problems);

        Bundle bundle = getIntent().getExtras();
        final int position = bundle.getInt("position");




        Button dateButton = findViewById(R.id.selectNewDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(problemList.get(position).getDate());
                int currentYear = cal.get(Calendar.YEAR);
                int currentMonth = cal.get(Calendar.MONTH);
                int currentDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditProblemActivity.this, android.R.style.Theme_DeviceDefault_Dialog, DateSetListener, currentYear, currentMonth, currentDay);
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



        EditText editTitleEditText = findViewById(R.id.editTitleEditText);
        editTitleEditText.setText(problemList.get(position).getTitle());

        EditText editDescriptionEditText = findViewById(R.id.editDescriptionEditText);
        editDescriptionEditText.setText(problemList.get(position).getComment());
    }
    public void saveProblem(View v){

        Collection<Problem> problems = ProblemListController.getProblemList().getProblems();
        final ArrayList<Problem> problemList = new ArrayList<>(problems);

        Bundle bundle = getIntent().getExtras();
        final int position = bundle.getInt("position");

        EditText editTitleEditText = findViewById(R.id.editTitleEditText);
        problemList.get(position).setTitle(editTitleEditText.getText().toString());

        EditText editDescriptionEditText = findViewById(R.id.editDescriptionEditText);
        problemList.get(position).setComment(editDescriptionEditText.getText().toString());

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        if (year != 0) {
            problemList.get(position).setDate(cal.getTime());
        }

        problemList.clear();
        problemList.addAll(problems);

        Intent intent = new Intent(EditProblemActivity.this, ListProblemsActivity.class);
        startActivity(intent);


    }

}
