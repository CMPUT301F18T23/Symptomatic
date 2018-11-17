package ca.ualberta.symptomaticapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class Login extends AppCompatActivity implements View.OnClickListener{

    Button create_button, login_button;
    TextView input_user;
    Intent next_activity;

    Context context = this;

    Toast toast;

    public static User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        create_button = findViewById(R.id.login_create_acc_button);
        login_button = findViewById(R.id.login_button);

        create_button.setOnClickListener(this);
        login_button.setOnClickListener(this);

        input_user = findViewById(R.id.enter_user);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.login_create_acc_button){
            next_activity = new Intent(this,createAccount.class);
            startActivity(next_activity);
        } else if (viewId == R.id.login_button){
            //Get the input from the login
            String inputuser = input_user.getText().toString();

            Login.thisUser = query.getUserFromDb(inputuser);

            int temp = 1;

            if(Login.thisUser != null) {
                next_activity = new Intent(Login.this, MainActivity.class);
                startActivity(next_activity);
            } else {
                toast = Toast.makeText(getApplication(),"The entered username does not exist",Toast.LENGTH_LONG);
                toast.show();

            }

        }
    }
}
