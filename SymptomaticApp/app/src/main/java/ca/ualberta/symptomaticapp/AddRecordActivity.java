package ca.ualberta.symptomaticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;

public class AddRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);


        Collection<Problem> problems = ProblemListController.getProblemList().getProblems();
        ArrayList<Problem> problemList = new ArrayList<>(problems);
        ArrayAdapter<Problem> problemAdapter = new ArrayAdapter<Problem>(this, android.R.layout.simple_spinner_dropdown_item, problemList);
        problemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.ProblemsSpinner);
        spinner.setAdapter(problemAdapter);

    }
}
