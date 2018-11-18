/*
An activity that only the caregiver will be able to see and is used for viewing the records of a specific problem.
The caregiver will select a patient through the spinner,select a problem as well, and then be presented with a list of
records associated with that patient's problem. The caregiver can also view that patients' contact information.
 */

package ca.ualberta.symptomaticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class CViewRecords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview_records);
        Spinner selectpatient = (Spinner) findViewById(R.id.sp_Patient);
        Spinner selectproblem = (Spinner) findViewById(R.id.sp_Problems);
        Button viewproblem = (Button) findViewById(R.id.btn_ViewProblems);
        Button viewpatient = (Button) findViewById(R.id.btn_ViewPatient);
        Button viewphotoalbum = (Button) findViewById(R.id.btn_ViewPhotos);
        Button viewcontactinfo = (Button) findViewById(R.id.btn_ViewContactInfo);
        TextView numprob = (TextView) findViewById(R.id.tv_numprob);
        ListView recordview = (ListView) findViewById(R.id.lv_records);


    }
}
