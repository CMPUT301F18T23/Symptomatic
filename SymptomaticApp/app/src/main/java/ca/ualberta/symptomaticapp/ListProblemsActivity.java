package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
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
import java.util.Collection;


public class ListProblemsActivity extends AppCompatActivity {

    private ListViewAdapter listadapter;

    ListView listView;

    ProblemList displayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problems);

        TextView textView = (TextView) findViewById(R.id.NumberProblemsTextView);
        listView = findViewById(R.id.problemsListView);

        displayList = new ProblemList();

        displayList.getFromDb();

        String active_problem_count = "Number of active problems:" + " " + displayList.getProblems().size();
        textView.setText(active_problem_count);

        final Collection<Problem> problem_list = displayList.getProblems();
        final ArrayList<Problem> problemList = new ArrayList<>(problem_list);
        listadapter = new ListViewAdapter(problemList, this);
        listView.setAdapter(listadapter);

        /*final Collection<Problem> problems = ProblemListController.getProblemList().getProblems();
        final ArrayList<Problem> problemList = new ArrayList<>(problems);*/
    }

}


