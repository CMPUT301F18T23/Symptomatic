package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewFullRecordActivity extends AppCompatActivity {

    Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_record);
        Toolbar toolbar = findViewById(R.id.viewFullRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Record Details");

        record = (Record) getIntent().getSerializableExtra("record");

        // Insert the records problem
        TextView problemTextView = findViewById(R.id.InputProblemTextView);
        problemTextView.setText(record.problem);

        // Insert the records title
        TextView titleTextView = findViewById(R.id.addTitleTextView);
        titleTextView.setText(record.recordTitle);

        // Insert the records time stamp
        TextView timeStampTextView = findViewById(R.id.InputDateTextView);
        timeStampTextView.setText(record.recordDate.toString());











    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(ViewFullRecordActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(ViewFullRecordActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }


    public void viewAddProblem(MenuItem menu){
        Intent intent = new Intent(ViewFullRecordActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(ViewFullRecordActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        Intent intent = new Intent(ViewFullRecordActivity.this, ViewQRCode.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(ViewFullRecordActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
