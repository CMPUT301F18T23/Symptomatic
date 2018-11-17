package ca.ualberta.symptomaticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class ListProblemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problems);

        TextView textView = (TextView) findViewById(R.id.NumberProblemsTextView);
        textView.setText("Number of active problems:" + " " + ProblemListController.getProblemList().getSize());


        ListView listView = findViewById(R.id.problemsListView);
        final Collection<Problem> problems = ProblemListController.getProblemList().getProblems();
        final ArrayList<Problem> problemList = new ArrayList<>(problems);
        final ListViewAdapter adapter = new ListViewAdapter(problemList, this);
        listView.setAdapter(adapter);


    }
}
