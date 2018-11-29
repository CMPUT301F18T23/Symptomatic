package ca.ualberta.symptomaticapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditRecordActivity extends AppCompatActivity {

    Record record;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private TimePickerDialog.OnTimeSetListener TimeSetListener;
    Calendar cal;
    int year;
    int month;
    int day;
    int hour;
    int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
        Toolbar toolbar = findViewById(R.id.editRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Record");

        record = (Record) getIntent().getSerializableExtra("record");

        cal = Calendar.getInstance();
        cal.setTime(record.recordDate);

        // Input the title
        EditText titleEditText = findViewById(R.id.editTitleEditText);
        titleEditText.setText(record.recordTitle);

        // Input the time stamp
        TextView timeTextView = findViewById(R.id.currentTimeTextView);
        timeTextView.setText(record.recordDate.toString());

        // Input the comment
        EditText commentEditText = findViewById(R.id.commentEditText);
        commentEditText.setText(record.recordComment);

        // Change time stamp button
        Button timeButton = findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog editTimeDialog = new TimePickerDialog(EditRecordActivity.this, android.R.style.Theme_DeviceDefault_Dialog, TimeSetListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(EditRecordActivity.this));
                editTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                editTimeDialog.show();

                DatePickerDialog dialog = new DatePickerDialog(EditRecordActivity.this, android.R.style.Theme_DeviceDefault_Dialog, DateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();


            }
        });

        TimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;

            }
        };

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int chosenYear, int chosenMonth, int chosenDay) {
                year = chosenYear;
                month = chosenMonth;
                day = chosenDay;

            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(EditRecordActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(EditRecordActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }

    public void viewAddProblem(MenuItem menu){
        Intent intent = new Intent(EditRecordActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(EditRecordActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        Intent intent = new Intent(EditRecordActivity.this, ViewQRCode.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(EditRecordActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
