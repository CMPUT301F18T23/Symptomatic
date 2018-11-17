package ca.ualberta.symptomaticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collection;

public class EditProblemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);

        Collection<Problem> problems = ProblemListController.getProblemList().getProblems();
        ArrayList<Problem> problemList = new ArrayList<>(problems);

        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("position");


        EditText editTitleEditText = findViewById(R.id.editTitleEditText);
        editTitleEditText.setText(problemList.get(position).getTitle());

        EditText editDescriptionEditText = findViewById(R.id.editDescriptionEditText);
        editDescriptionEditText.setText(problemList.get(position).getComment());
    }
}
