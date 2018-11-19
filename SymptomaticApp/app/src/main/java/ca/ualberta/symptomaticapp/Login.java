package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;


public class Login extends AppCompatActivity implements View.OnClickListener{

    Button create_button, login_button;
    TextView input_user;
    Intent next_activity;

    RadioButton patientLogin,caregiverLogin;

    public static User thisUser;
    public static Caregiver thisCaregiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        create_button = findViewById(R.id.login_create_acc_button);
        login_button = findViewById(R.id.login_button);

        create_button.setOnClickListener(this);
        login_button.setOnClickListener(this);

        input_user = findViewById(R.id.enter_user);

        patientLogin = findViewById(R.id.patientLoginButton);
        caregiverLogin = findViewById(R.id.careproviderLoginButton);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.login_create_acc_button){
            next_activity = new Intent(this,createAccount.class);
            startActivity(next_activity);
        } else if (viewId == R.id.login_button){
            //Get the input from the login
            final String inputuser = input_user.getText().toString();

            //Access Firestore database
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference active_users;

            if (caregiverLogin.isChecked()){
                active_users = db.collection("caregivers");
            } else {
                active_users = db.collection("users");
            }

            //Build the query
            Query query = active_users
                    .whereEqualTo("username",inputuser);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                //If Query Worked on not
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        //Query Worked
                        if (task.getResult().size() == 1){
                            //A user with that username exists
                            for(QueryDocumentSnapshot document: task.getResult()){
                                if (patientLogin.isChecked()) {
                                    thisUser = document.toObject(User.class);
                                    thisCaregiver = null;
                                } else {
                                    thisCaregiver = document.toObject(Caregiver.class);
                                    thisUser = null;
                                }
                            }
                            if (patientLogin.isChecked()) {
                                next_activity = new Intent(Login.this,MainActivity.class);
                            } else {
                                next_activity = new Intent(Login.this,CaregiverHome.class);
                            }
                            startActivity(next_activity);

                        } else if (inputuser.isEmpty()) {
                            //No users with that username exists
                            Toast.makeText(Login.this, "Username not entered.", Toast.LENGTH_SHORT).show();

                        }

                        else {
                            //No users with that username exists
                            Toast.makeText(Login.this, "User Does Not Exist.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Query Did not Work
                        Toast.makeText(Login.this, "Load Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}

