package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import static ca.ualberta.symptomaticapp.MainActivity.database;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button create_button, login_button;
    TextView input_user;
    Intent next_activity;

    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRef = database.getReference("users");

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
        } else if (viewId == R.id.login_button){
            next_activity = new Intent(this,MainActivity.class);
        }
        if (next_activity != null) {
            startActivity(next_activity);
        }
    }
}
