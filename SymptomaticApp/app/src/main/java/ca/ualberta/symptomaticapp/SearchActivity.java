package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    // all geolocation related
    static final int GET_GEOLOCATION = 1;
    private LatLng geolocation;
    private Double lat, lng;
    private String geolocationString;

    private String keywords;
    private String distance;
    private RadioButton keywordButton, geoButton, bodyButton;

    private ProblemList problemList;
    private Problem currProb;
    private ArrayList<Problem> problemArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        //initialize a toolbar
        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");

        // Initialize radio buttons
        keywordButton = findViewById(R.id.keyWordRadio);
        geoButton = findViewById(R.id.geoRadio);
        bodyButton = findViewById(R.id.bodyRadio);

        problemList = new ProblemList();

        if(Login.thisUser != null) {
            getProblems(Login.thisUser.returnUsername());
        }

        // Get km
        final EditText distanceEditText = findViewById(R.id.distanceEditText);

        // get geolocation
        Button mapButton = findViewById(R.id.geolocationButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, GET_GEOLOCATION);
            }
        });

        // search problems button
        Button searchProblems = findViewById(R.id.searchProbsButton);
        searchProblems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, problemArrayList.size(), Toast.LENGTH_SHORT).show();
                if( keywordButton.isChecked()){
                    Toast.makeText(SearchActivity.this, "keybutton", Toast.LENGTH_SHORT).show();


                } else if( geoButton.isChecked()){
                    Toast.makeText(SearchActivity.this, "geoButton", Toast.LENGTH_SHORT).show();
                    if (geolocation == null){
                        // No geolocation exists, must add in edit record
                        AlertDialog.Builder noGeolocationDialog = new AlertDialog.Builder(SearchActivity.this);
                        noGeolocationDialog.setMessage("No Geolocation selected.");
                        noGeolocationDialog.show();
                    }
                    if (distanceEditText.length() == 0){
                        // No distance exists
                        AlertDialog.Builder noDistanceDialog = new AlertDialog.Builder(SearchActivity.this);
                        noDistanceDialog.setMessage("No distance Input.");
                        noDistanceDialog.show();

                    } else{
                    //geoSearch();
                    }


                }
                else {
                    // body button is selected
                    Toast.makeText(SearchActivity.this, "bodyButton", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case GET_GEOLOCATION:
                if (resultCode == RESULT_OK){
                    geolocation = data.getExtras().getParcelable("geolocation");
                    lat = geolocation.latitude;
                    lng = geolocation.longitude;
                    geolocationString = Double.toString(lat) + ',' + Double.toString(lng);
                    Toast.makeText(this, geolocation.toString(), Toast.LENGTH_SHORT).show();
                }
                break;

        }


    }

    public void geoSearch(String username){

    }


    private void getProblems(String username){
        final ArrayList<Problem> foundRecords = new ArrayList<Problem>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference problems = db.collection("problems");

        Query problemsQuery = problems.whereEqualTo("user",username);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    problemList.empty();
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        Problem problem = document.toObject(Problem.class);
                        problem.updateRecords();
                        problemList.addProblem(problem);
                        foundRecords.add(problem);

                    }

                }
                for (int i = 0; i < foundRecords.size(); i++) {
                    currProb = foundRecords.get(i);
                    problemArrayList.add(currProb);

                }

            }
        });
    }





    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }



}
