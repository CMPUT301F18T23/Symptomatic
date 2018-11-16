/*
An activity that only the caregiver will be able to see and is used for viewing the records of a specific problem.
The caregiver will select a patient through the spinner,select a problem as well, and then be presented with a list of
records associated with that patient's problem. The caregiver can also view that patients' contact information.
 */

package ca.ualberta.symptomaticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CViewRecords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview_records);
    }
}
