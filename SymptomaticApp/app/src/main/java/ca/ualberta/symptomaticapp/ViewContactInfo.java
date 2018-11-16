package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewContactInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_info);
        // get all text views and buttons
        TextView username = (TextView) findViewById(R.id.tv_User);
        TextView email = (TextView) findViewById(R.id.tv_Email);
        TextView phone = (TextView) findViewById(R.id.tv_Phone);
        Button goback = (Button) findViewById(R.id.btn_return);


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
