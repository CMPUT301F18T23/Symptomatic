/*
 * ViewComments.java
 *
 * Version 1
 *
 * December, 1, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * View Comments of the caregiver.
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewComments extends AppCompatActivity {
    private Record record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
        Toolbar toolbar = findViewById(R.id.comment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comment");
        record = (Record) getIntent().getSerializableExtra("record");
        TextView title = (TextView)findViewById(R.id.tv_title);
        ListView commentview = (ListView) findViewById(R.id.lv_comments);
        List<String> comments = record.getComments();
        title.setText("Comments for " + record.getTitle());
        CommentAdapter adapter = new CommentAdapter(comments, this);
        commentview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(ViewComments.this, MainActivity.class);
        startActivity(intent);
    }

    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(ViewComments.this, EditAccountActivity.class);
        startActivity(intent);
    }


    public void viewAddProblem(MenuItem menu){
        Intent intent = new Intent(ViewComments.this, AddProblemActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(ViewComments.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        Intent intent = new Intent(ViewComments.this, ViewQRCode.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(ViewComments.this, MainActivity.class);
        startActivity(intent);
    }
}