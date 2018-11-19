package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CaregiverHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_home);
        TextView hellouser = (TextView) findViewById(R.id.tv_hellouser);
        hellouser.setText("Hello "+ Login.thisCaregiver.returnUsername() + "!");

        Button addpat = (Button) findViewById(R.id.btn_addpatient);
        Button viewpat = (Button) findViewById(R.id.btn_viewpatients);
        Button openviewprob = (Button) findViewById(R.id.btn_openviewproblems);
        Button openviewrecords = (Button) findViewById(R.id.btn_openviewrecords);
        addpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverHome.this, AddPatient.class);
                startActivity(intent);
            }
        });

        viewpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverHome.this, ViewPatients.class);
                startActivity(intent);
            }
        });

        openviewprob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverHome.this, CViewProblems.class);
                startActivity(intent);
            }
        });

        openviewrecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverHome.this, CViewRecords.class);
                startActivity(intent);
            }
        });
    }
}
