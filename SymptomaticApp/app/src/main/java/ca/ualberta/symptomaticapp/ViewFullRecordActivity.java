/*
 * ViewFullRecordActivity.java
 *
 * Version 1
 *
 * November, 25, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * User can view the full record.
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.errorprone.annotations.Var;

import java.util.ArrayList;

public class ViewFullRecordActivity extends AppCompatActivity {

    Record record;
    ArrayList<Photo> displayPhotos;
    PhotoListViewAdapter2 photoListViewAdapter2;
    ListView photoListView;

    ArrayList<String> bodyLocation;

    bodyPartDialog thisDialog;

    Button viewFrontBodyPart, viewBackBodyPart, viewComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_record);
        Toolbar toolbar = findViewById(R.id.viewFullRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Record Details");

        record = (Record) getIntent().getSerializableExtra("record");

        photoListView = findViewById(R.id.fullRPhotoListView);
        displayPhotos = record.getPhotoList();
        initPhotoListView();
        setListViewHeightBasedOnChildren(photoListView);

        bodyLocation = record.bodyLocation;
        TextView problemTextView = findViewById(R.id.InputProblemTextView);
        problemTextView.setText(record.problem);
        viewComments = (Button) findViewById(R.id.btn_viewcomments);
        // Insert the records title
        TextView titleTextView = findViewById(R.id.addTitleTextView);
        titleTextView.setText(record.recordTitle);

        // Insert the records time stamp
        TextView timeStampTextView = findViewById(R.id.InputDateTextView);
        if(record.getTimeStamp()!=null) {
            timeStampTextView.setText(record.getTimeStamp().toString());
        } else {
            timeStampTextView.setText("No Date Entered");
        }

        // Insert the records comment
        TextView commentTextView = findViewById(R.id.addCommentTextView);
        commentTextView.setText(record.recordComment);

        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record.geolocation != null) {

                    String[] latlng = record.geolocation.split(",");
                    double latitude = Double.parseDouble(latlng[0]);
                    double longitude = Double.parseDouble(latlng[1]);

                    LatLng location = new LatLng(latitude, longitude);
                    Intent intent = new Intent(ViewFullRecordActivity.this, MapOfSingleRecordActivity.class);
                    intent.putExtra("geolocation", location);
                    intent.putExtra("title", record.recordTitle);
                    startActivity(intent);
                }
                else{
                    // No geolocation exists, must add in edit record
                    AlertDialog.Builder noGeolocationDialog = new AlertDialog.Builder(ViewFullRecordActivity.this);
                    noGeolocationDialog.setMessage("No Geolocation exists. You may add a geolocation in Edit Record.");
                    noGeolocationDialog.show();
                }
            }
        });

        viewComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewFullRecordActivity.this, ViewComments.class);
                intent.putExtra("record", record);
                startActivity(intent);
            }
        });

        Button slideShow = findViewById(R.id.slideShowMode);
        slideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record.getPhotoList().size() > 0) {
                    Intent intent = new Intent(ViewFullRecordActivity.this, SlideShowModeActivity.class);
                    intent.putExtra("record", record);
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder noPhotosDialog = new AlertDialog.Builder(ViewFullRecordActivity.this);
                    noPhotosDialog.setMessage("No photos exists. You may add photos in Edit Record.");
                    noPhotosDialog.show();
                }
            }
        });

              
        // view body location related
        viewBackBodyPart = findViewById(R.id.viewBackBodyPart);
        viewFrontBodyPart = findViewById(R.id.viewFrontBodyPart);

        thisDialog = new bodyPartDialog(ViewFullRecordActivity.this,false);
        thisDialog.readList(bodyLocation);

        viewBackBodyPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisDialog.backBodyPartDialog();
            }
        });

        viewFrontBodyPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisDialog.frontBodyPartDialog();
            }
        });

        // return to view all records
        Button viewRecordsButton = findViewById(R.id.viewAllRecordsBtn);
        viewRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // edit record
        Button editRecordButton = findViewById(R.id.editRecordBtn);
        editRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewFullRecordActivity.this, EditRecordActivity.class);
                intent.putExtra("record", record);
                startActivity(intent);
            }
        });
    }

    public void initPhotoListView(){
        if(photoListViewAdapter2 == null){
            photoListViewAdapter2 = new PhotoListViewAdapter2(displayPhotos, this);
        }

        photoListView.setAdapter(photoListViewAdapter2);

    }

    //Resource: https://medium.com/@skidanolegs/listview-inside-scrollview-solve-the-problem-a06fdff2a4e0
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(ViewFullRecordActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(ViewFullRecordActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }


    public void viewAddProblem(MenuItem menu){
        Intent intent = new Intent(ViewFullRecordActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(ViewFullRecordActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        Intent intent = new Intent(ViewFullRecordActivity.this, ViewQRCode.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(ViewFullRecordActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
