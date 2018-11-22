package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class EditAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        Toolbar toolbar = findViewById(R.id.editAccount_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Account");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_account_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void viewAddProblems(MenuItem menu) {
        Intent intent = new Intent(EditAccountActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }
    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(EditAccountActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
