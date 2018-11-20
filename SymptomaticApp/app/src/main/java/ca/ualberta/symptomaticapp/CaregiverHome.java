package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CaregiverHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_home);
        Toolbar toolbar = findViewById(R.id.caregiver_home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home Page");

        TextView hellouser = (TextView) findViewById(R.id.HelloCareProviderTextView);
        hellouser.setText("Hello "+ Login.thisCaregiver.returnUsername() + "!");

        Button ViewPatientButton = (Button) findViewById(R.id.ViewPatientButton);
        Button AddPatientButton = (Button) findViewById(R.id.AddPatientButton);

        ViewPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverHome.this, AddPatient.class);
                startActivity(intent);
            }
        });

        AddPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverHome.this, ViewPatients.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.caregiver_home_menu, menu);
        return true;
    }
    public void viewViewPatients(MenuItem menu) {
        Intent intent = new Intent(CaregiverHome.this, ViewPatients.class);
        startActivity(intent);
    }

    public void viewAddPatients(MenuItem menu) {
        Intent intent = new Intent(CaregiverHome.this, AddPatient.class);
        startActivity(intent);
    }

    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(CaregiverHome.this, MainActivity.class);
        startActivity(intent);
    }
}
