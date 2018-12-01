package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ViewFullRecordActivity extends AppCompatActivity {

    Record record;
    ArrayList<Photo> displayPhotos;
    PhotoListViewAdapter2 photoListViewAdapter2;
    ListView photoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_record);
        Toolbar toolbar = findViewById(R.id.viewFullRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Record Details");

        record = (Record) getIntent().getSerializableExtra("record");

       // photoListView = findViewById(R.id.fullRPhotoListView);
       // displayPhotos = record.getPhotoList();
        //initPhotoListView();
        //setListViewHeightBasedOnChildren(photoListView);

        photoListView = findViewById(R.id.fullRPhotoListView);
//        displayPhotos = record.getPhotoList();
     //   initPhotoListView();
      //  setListViewHeightBasedOnChildren(photoListView);

        //record.geolocation = new LatLng(-34, 151);


        // Insert the records problem
        TextView problemTextView = findViewById(R.id.InputProblemTextView);
        problemTextView.setText(record.problem);

        // Insert the records title
        TextView titleTextView = findViewById(R.id.addTitleTextView);
        titleTextView.setText(record.recordTitle);

        // Insert the records time stamp
        TextView timeStampTextView = findViewById(R.id.InputDateTextView);
        timeStampTextView.setText(record.recordDate.toString());

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


        Button viewRecordsButton = findViewById(R.id.viewAllRecordsBtn);
        viewRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }

   /* public void initPhotoListView(){
        if(photoListViewAdapter2 == null){
            photoListViewAdapter2 = new PhotoListViewAdapter2(displayPhotos, this);
        }

        photoListView.setAdapter(photoListViewAdapter2);

    } */

    // setListViewHeightBasedonChildren class reference:
//    Skidan, Oleg. “ListView inside ScrollView. Solve the Problem. – Oleg Skidan – Medium.” Medium.com, Medium,
//    5 Feb. 2016, medium.com/@skidanolegs/listview-inside-scrollview-solve-the-problem-a06fdff2a4e0.
//    Accessed: 25th November, 2018
  /*  public static void setListViewHeightBasedOnChildren(ListView listView) {
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
    } */

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
