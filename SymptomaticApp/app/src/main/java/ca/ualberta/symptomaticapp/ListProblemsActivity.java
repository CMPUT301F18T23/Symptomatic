package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problems);

        TextView textView = (TextView) findViewById(R.id.NumberProblemsTextView);
        String active_problem_count = "Number of active problems:" + " " + ProblemListController.getProblemList().getSize();
        textView.setText(active_problem_count);

        ListView listView = findViewById(R.id.problemsListView);
        final Collection<Problem> problems = ProblemListController.getProblemList().getProblems();
        final ArrayList<Problem> problemList = new ArrayList<>(problems);
        final ListViewAdapter adapter = new ListViewAdapter(problemList, this);
        listView.setAdapter(adapter);

    }
}


