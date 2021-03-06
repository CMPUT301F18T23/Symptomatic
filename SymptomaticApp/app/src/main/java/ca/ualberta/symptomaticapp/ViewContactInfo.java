/*
 * ViewContactInfo.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * User can view contact Info
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewContactInfo extends AppCompatActivity {
    private TextView username;
    private TextView email;
    private TextView phone;
    private String user;
    private String type;
    private User currentuser;
    private Caregiver currentcaregiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_info);

        Toolbar toolbar = findViewById(R.id.viewContactInfo_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Contact Info");


        Intent intent = getIntent();
        user = intent.getExtras().getString("username");
        type = intent.getExtras().getString("usertype");

        // get all text views and buttons
        username = (TextView) findViewById(R.id.tv_User);
        email = (TextView) findViewById(R.id.tv_Email);
        phone = (TextView) findViewById(R.id.tv_Phone);
        Button goback = (Button) findViewById(R.id.btn_return);
        //

        getuserinfo(); //updates textviews by fetching user information
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    protected void getuserinfo(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference active_users;
        if(type.equals("user")){
            active_users = db.collection("users");
        }else{
            active_users = db.collection("caregivers");
        }
        Query query = active_users.whereEqualTo("username",user);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            //If Query Worked on not
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    //Query Worked
                    if (task.getResult().size() == 1){
                        //A user with that username exists
                        for(QueryDocumentSnapshot document: task.getResult()){
                            if (type.equals("user")) {
                                currentuser = document.toObject(User.class);
                                currentcaregiver = null;
                            } else {
                                currentcaregiver = document.toObject(Caregiver.class);
                                currentuser = null;
                            }
                            //set textview values using the retrieved user.
                            if(currentcaregiver == null){ //contact is a patient
                                username.setText("Username: " + currentuser.returnUsername());
                                email.setText("E-mail: " + currentuser.returnEmail());
                                phone.setText("Phone Number: " + currentuser.returnPhone());
                            }else { //contact is a caregiver
                                username.setText("Username: " + currentcaregiver.returnUsername());
                                email.setText("E-mail: " + currentcaregiver.returnEmail());
                                phone.setText("Phone Number: " + currentcaregiver.returnPhone());
                            }
                        }
                    }else if (user.isEmpty()) {
                        //No users with that username exists
                        Toast.makeText(ViewContactInfo.this, "Username is null.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //No users with that username exists
                        Toast.makeText(ViewContactInfo.this, "User Does Not Exist.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Query Did not Work
                    Toast.makeText(ViewContactInfo.this, "Load Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //toolbar functions
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewcontactinfo_menu, menu);
        return true;
    }

    public void viewHome(MenuItem menu) {
        //adds the view problems button to the menu
        Intent intent;
        if(Login.thisCaregiver ==null){
            intent = new Intent(ViewContactInfo.this, MainActivity.class);
        }else{
            intent = new Intent(ViewContactInfo.this, CaregiverHome.class);
        }
        startActivity(intent);
    }

    public void viewLogout(MenuItem menu){ //log current caregiver out and open login page
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(ViewContactInfo.this, MainActivity.class);
        startActivity(intent);
    }
}
