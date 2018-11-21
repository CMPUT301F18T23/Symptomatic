package ca.ualberta.symptomaticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class SlideShowModeActivity extends AppCompatActivity {
    protected ArrayList<Photo> displayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show_mode);

        displayList = new ArrayList<Photo>();

        Button previous = findViewById(R.id.previousBttn);
        Button next = findViewById(R.id.nextBttn);


    }
}
