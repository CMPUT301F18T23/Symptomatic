package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class ViewFullProblemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_problem);


        final Collection<Problem> problems = ProblemListController.getProblemList().getProblems();
        final ArrayList<Problem> problemList = new ArrayList<>(problems);


        final Bundle bundle = getIntent().getExtras();
        final int position = bundle.getInt("position");

        TextView textView1 = findViewById(R.id.titleTextView);
        textView1.setText("Title: " + problemList.get(position).title);

        TextView textView2 = findViewById(R.id.dateTextView);
        textView2.setText("Date: " + problemList.get(position).date.toString());


        TextView textView3 = findViewById(R.id.descriptionTextView);
        textView3.setText("Description: " + problemList.get(position).comment);

        Button AddProblemButton = (Button) findViewById(R.id.addRecordButton);
        AddProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewFullProblemActivity.this, AddRecordActivity.class);
                startActivity(intent);
            }
        });

        Button AddRecordButton = (Button) findViewById(R.id.viewProblemsButton);
        AddRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewFullProblemActivity.this, ListProblemsActivity.class);
                startActivity(intent);
            }
        });

        Button EditProblemButton = findViewById(R.id.editButton);
        EditProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewFullProblemActivity.this, EditProblemActivity.class);
                Bundle newBundle = new Bundle();
                newBundle.putInt("position", position);
                intent.putExtras(newBundle);
                startActivity(intent);
            }
        });


    }
}
