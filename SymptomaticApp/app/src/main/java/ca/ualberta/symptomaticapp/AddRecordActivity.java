package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        Collection<Problem> problems = ProblemListController.getProblemList().getProblems();
        ArrayList<Problem> problemList = new ArrayList<>(problems);
        ArrayAdapter<Problem> problemAdapter = new ArrayAdapter<Problem>(this, android.R.layout.simple_spinner_dropdown_item, problemList);
        problemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.ProblemsSpinner);
        spinner.setAdapter(problemAdapter);

        final Button savedPhoto = findViewById(R.id.savedPhoto);
        final Button takePhoto = findViewById(R.id.newPhoto);

        savedPhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);



    }

//    Gets images from gallery - Reference:https://en.proft.me/2017/07/1/how-get-image-gallery-or-camera-android/
    public void onClick(View v){
        int viewId = v.getId();

        if (viewId == R.id.savedPhoto){

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PICK_IMAGE_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();

                }
        }
    }




}
