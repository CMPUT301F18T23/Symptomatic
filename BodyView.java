package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class BodyView extends AppCompatActivity {
    /* Initialize Variables that have to be passed to add record */
    String Location;
    String Direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_view);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Direction = "Front";
            }
        });
        final Button button1 = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Direction = "Back";
            }
        });
        final Button button2 = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                startActivity(new Intent(BodyView.this, AddRecordActivity.class ));
            }
        });
    }
}