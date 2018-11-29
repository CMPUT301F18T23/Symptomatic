package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class EditRecordActivity extends AppCompatActivity {

    Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
        Toolbar toolbar = findViewById(R.id.editRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Record");

        record = (Record) getIntent().getSerializableExtra("record");

        // Input the title
        EditText titleEditText = findViewById(R.id.editTitleEditText);


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
