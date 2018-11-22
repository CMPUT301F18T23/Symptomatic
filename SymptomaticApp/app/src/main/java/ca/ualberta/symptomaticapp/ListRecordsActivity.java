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

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

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

    ArrayList<Record> recordDisplayList;

    FirebaseFirestore db;

    Problem problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_records);

        db = FirebaseFirestore.getInstance();

        problem = (Problem)getIntent().getSerializableExtra("problem");

        recordslistview = findViewById(R.id.recordsListView);

        recordDisplayList = new ArrayList<Record>();

        initRecordView();
        getRecords(Login.thisUser.username);
    }

    private void initRecordView(){
        if(recordListViewAdapter == null){
            recordListViewAdapter = new RecordListViewAdapter(recordDisplayList, this);
        }
        recordslistview.setAdapter(recordListViewAdapter);
    }

    private void getRecords(String enteredUser){
        db = FirebaseFirestore.getInstance();

        final CollectionReference records = db.collection("records");

        Query recordsQuery = records.whereEqualTo("problem",problem.getTitle()).whereEqualTo("user",Login.thisUser.returnUsername());

        recordsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int x = 0;
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Record returnedRecord = document.toObject(Record.class);
                        recordDisplayList.add(returnedRecord);
                    }
                }
                recordListViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
