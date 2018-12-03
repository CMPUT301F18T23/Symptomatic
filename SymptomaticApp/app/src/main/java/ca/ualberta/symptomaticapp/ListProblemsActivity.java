/*
 * ListProblemsActivity.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Represents a list of patient problems.
 *
 * Issues: No current issues.
 *
 */

package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ListProblemsActivity extends AppCompatActivity {

    public static ListViewAdapter listAdapter;

    private ListView listView;

    private static String active_problem_count;

    public static ProblemList thisProbList;

    private static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problems);
        Toolbar toolbar = findViewById(R.id.listProblem_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Problems");

        listView = findViewById(R.id.problemsListView);
        textView = (TextView) findViewById(R.id.NumberRecordsTextView);

        thisProbList = new ProblemList();

        active_problem_count = "Number of active problems:";
        textView.setText(active_problem_count);

        initListView();

        if(Login.thisUser != null) {
            getProblems(Login.thisUser.returnUsername());
        }




    }

    @Override
    public void onResume(){
        super.onResume();
        if(Login.thisUser != null) {
            getProblems(Login.thisUser.returnUsername());

        }
        for (Problem thisProblem: thisProbList.getProblems()){
            thisProblem.updateRecords();
            listAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_problems_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(ListProblemsActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(ListProblemsActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }

    public void viewAddProblem(MenuItem menu) {
        Intent intent = new Intent(ListProblemsActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        Intent intent = new Intent(ListProblemsActivity.this, ViewQRCode.class);
        startActivity(intent);
    }

    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(ListProblemsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void initListView(){
        if(listAdapter == null){
            listAdapter = new ListViewAdapter(thisProbList.getProblems(), this);
        }
        listView.setAdapter(listAdapter);

    }


    public static void getProblems(String username){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference problems = db.collection("problems");

        Query problemsQuery = problems.whereEqualTo("user",username);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ListProblemsActivity.thisProbList.empty();
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        Problem problem = document.toObject(Problem.class);
                        problem.updateRecords();
                        ListProblemsActivity.thisProbList.addProblem(problem);
                    }
                    active_problem_count = "Number of active problems:"+" " + ListProblemsActivity.thisProbList.getSize();
                    textView.setText(active_problem_count);
                    listAdapter.notifyDataSetChanged();
<<<<<<< HEAD
                } else {
                    AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(ListProblemsActivity.this);
                    badUsernameDialog.setMessage("Unable to load problem's list due to corruption.");
                    badUsernameDialog.show();
=======
>>>>>>> pineo
                }
            }
        });
    }




}


