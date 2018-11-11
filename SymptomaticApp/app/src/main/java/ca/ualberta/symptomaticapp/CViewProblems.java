package ca.ualberta.symptomaticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class CViewProblems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview_problems);
        TextView numproblems = (TextView) findViewById(R.id.tv_ProbNum); //need to change this text when view button pressed
        final Spinner patient = (Spinner) findViewById(R.id.sp_Patients); //need to get selections
        Button viewproblems = (Button) findViewById(R.id.btn_View);
        final Button viewcontactinfo = (Button) findViewById(R.id.btn_viewcontactinfo);
        ListView problemview = (ListView) findViewById(R.id.lv_problems);

        viewproblems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // take current selected patient and populate list view using their problems
                viewcontactinfo.setEnabled(true); //allow view contact info to be able to be pressed
                String selection = patient.getSelectedItem().toString(); //get current selection

            }
        });
    }
}
