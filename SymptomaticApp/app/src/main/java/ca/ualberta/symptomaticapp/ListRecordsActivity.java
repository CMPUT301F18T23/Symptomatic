/*
 * ListRecordsActivity.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Represents a list of patient records for a particular problem.
 *
 * Issues: Currently not implemented.
 *
 */

package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static ca.ualberta.symptomaticapp.ListProblemsActivity.listAdapter;


public class ListRecordsActivity extends AppCompatActivity {

    ListView recordslistview;
    RecordListViewAdapter recordListViewAdapter;

    RecordList thisRecordList;


    FirebaseFirestore db;

    Problem problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_records);
        Toolbar toolbar = findViewById(R.id.listRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Records");


        db = FirebaseFirestore.getInstance();

        problem = (Problem)getIntent().getSerializableExtra("problem");

        // Header for view records
        TextView header = findViewById(R.id.titleTextView);
        String headerText = "Records for Problem: " + problem.getTitle();
        header.setText(headerText);


        recordslistview = findViewById(R.id.recordsListView);

        thisRecordList = new RecordList();

        initRecordView();

        getRecords();


        Button mapRecordsButton = findViewById(R.id.mapRecordsButton);
        mapRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        getRecords();
    }
    @Override
    protected void onPause(){
        super.onPause();
        getRecords();
    }
    @Override
    protected void onStart(){
        super.onStart();
        getRecords();
    }
    @Override
    public void onRestart(){
        super.onRestart();
        getRecords();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(ListRecordsActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(ListRecordsActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }

    public void viewAddProblem(MenuItem menu){
        Intent intent = new Intent(ListRecordsActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(ListRecordsActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        Intent intent = new Intent(ListRecordsActivity.this, ViewQRCode.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(ListRecordsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void openMap(){
        Intent intent = new Intent(ListRecordsActivity.this, MapOfRecordsActivity.class);
        intent.putExtra("problem", problem);
        startActivity(intent);

    }


    private void initRecordView(){
        if(recordListViewAdapter == null){
            recordListViewAdapter = new RecordListViewAdapter(thisRecordList.getRecords(), this);
        }
        recordslistview.setAdapter(recordListViewAdapter);
    }

    private void getRecords(){
        db = FirebaseFirestore.getInstance();
        thisRecordList.empty();

        final CollectionReference records = db.collection("records");

        Query recordsQuery = records.whereEqualTo("problem",problem.getTitle()).whereEqualTo("user",Login.thisUser.returnUsername());
        recordsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Record newRecord = document.toObject(Record.class);
                        thisRecordList.addRecord(newRecord);
                    }
                }
                recordListViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
