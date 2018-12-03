/*
Activity used for providing the caregiver type of users a homepage with two buttons present for them
to choose from. Add patient and View Patient opens their respective activities.
 */
package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.graphics.Typeface;
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
        //setup custom toolbar
        Toolbar toolbar = findViewById(R.id.caregiver_home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home Page");
        //end of toolbar setup
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.REGULARFONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.buttoncontainer), iconFont);

        //get ui elements
        TextView hellouser = (TextView) findViewById(R.id.HelloCareProviderTextView);
        Button AddPatientButton = (Button) findViewById(R.id.ViewPatientButton);
        Button ViewPatientButton = (Button) findViewById(R.id.AddPatientButton);
        //end of getting ui elements

        //set welcome message for current caregiver
        hellouser.setText("Hello "+ Login.thisCaregiver.returnUsername() + "!");

        ViewPatientButton.setOnClickListener(new View.OnClickListener() { //viewpatient button listener
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverHome.this, AddPatientActivity.class);
                startActivity(intent); //open viewpatients activity
            }
        });

        AddPatientButton.setOnClickListener(new View.OnClickListener() { //addpatient button listener
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverHome.this, ViewPatients.class);
                startActivity(intent);  //open addpatient activity
            }
        });

    }

    //toolbar functions
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.caregiver_home_menu, menu);
        return true;
    }
    public void viewViewPatients(MenuItem menu) { //open viewpatients activity
        Intent intent = new Intent(CaregiverHome.this, ViewPatients.class);
        startActivity(intent);
    }

    public void viewAddPatients(MenuItem menu) { //open add patients activity
        Intent intent = new Intent(CaregiverHome.this, AddPatientActivity.class);
        startActivity(intent);
    }

    public void viewViewQR(MenuItem menu) {
        //adds the view problems button to the menu
        Intent intent = new Intent(CaregiverHome.this, CaregiverViewQRCode.class);
        startActivity(intent);
    }

    public void viewLogout(MenuItem menu){ //log current caregiver out and open login page
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(CaregiverHome.this, MainActivity.class);
        startActivity(intent);
    }
}
