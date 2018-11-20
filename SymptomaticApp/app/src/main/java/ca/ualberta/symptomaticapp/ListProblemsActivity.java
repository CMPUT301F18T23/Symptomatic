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
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ListProblemsActivity extends AppCompatActivity {

    private ListViewAdapter listAdapter;

    private ListView listView;

    private ArrayList<Problem> displayList;

    private String active_problem_count;

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problems);
        Toolbar toolbar = findViewById(R.id.listProblem_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Problems");

        listView = findViewById(R.id.problemsListView);
        displayList = new ArrayList<Problem>();
        textView = (TextView) findViewById(R.id.NumberRecordsTextView);

        active_problem_count = "Number of active problems:";
        textView.setText(active_problem_count);

        initListView();

        getProblems(Login.thisUser.username);

        /*final Collection<Problem> problems = ProblemListController.getProblemList().getProblems();
        final ArrayList<Problem> problemList = new ArrayList<>(problems);*/
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

    public void viewAddProblem(MenuItem menu) {
        Intent intent = new Intent(ListProblemsActivity.this, AddProblemActivity.class);
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
            listAdapter = new ListViewAdapter(displayList, this);
        }
        listView.setAdapter(listAdapter);
    }

    private void getProblems(String username){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference problems = db.collection("problems");

        Query problemsQuery = problems.whereEqualTo("user",username);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Problem problem = document.toObject(Problem.class);
                        displayList.add(problem);
                    }
                    listAdapter.notifyDataSetChanged();
                    if (displayList != null) {
                        active_problem_count = "Number of active problems:"+" " + displayList.size();
                        textView.setText(active_problem_count);
                    } else {
                        active_problem_count = "Number of active problems: 0";
                        textView.setText(active_problem_count);
                    }
                } else {
                    AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(ListProblemsActivity.this);
                    badUsernameDialog.setMessage("Data Load Error");
                    badUsernameDialog.show();
                }
            }
        });
        for(Problem problem: displayList){
            problem.updateRecords();
        }
    }


}


