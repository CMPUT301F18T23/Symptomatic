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
//    PhotoListViewAdapter2 photoListViewAdapter2;
    ListView photoListView;


    ArrayList<String> bodyLocation;


    Button viewFrontBodyPart, viewBackBodyPart;

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


        //Toast.makeText(this, record.bodyLocation.get(0), Toast.LENGTH_LONG).show();
        // Insert the records problem

        bodyLocation = record.bodyLocation;
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

        Button slideShow = findViewById(R.id.slideShowMode);
        slideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewFullRecordActivity.this, SlideShowModeActivity.class);
                intent.putExtra("record", record);
                startActivity(intent);
            }
        });

              
        // view body location related
        viewBackBodyPart = findViewById(R.id.viewBackBodyPart);
        viewFrontBodyPart = findViewById(R.id.viewFrontBodyPart);

        viewBackBodyPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBodyPartDialog();
            }
        });

        viewFrontBodyPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontBodyPartDialog();
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


    private void frontBodyPartDialog() {
        final Dialog frontBodyPartDialog = new Dialog(ViewFullRecordActivity.this);
        frontBodyPartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        frontBodyPartDialog.setContentView(R.layout.view_record_body_part_front);
        frontBodyPartDialog.setTitle("Select Body Parts");

        final Button toBack = frontBodyPartDialog.findViewById(R.id.linkToBack);
        toBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBodyPartDialog();
                frontBodyPartDialog.dismiss();
            }
        });

        final Button close = frontBodyPartDialog.findViewById(R.id.saveclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontBodyPartDialog.dismiss();
            }
        });

        if (bodyLocation != null){
           for (String location: bodyLocation) {

               //Gets a click for the Front Right Hand button
               final ImageButton frontRightHand = frontBodyPartDialog.findViewById(R.id.front_right_hand);
               if("Front Right Hand".equals(location)){
                   frontRightHand.setImageResource(R.drawable.front_right_hand_selected);
               }

               //Gets a click for the Left Shin button
               final ImageButton leftShin = frontBodyPartDialog.findViewById(R.id.left_shin);
               if("Left Shin".equals(location)){
                   leftShin.setImageResource(R.drawable.left_shin_selected);
               }


                //Gets a click for the Front Left Foot button
               final ImageButton frontLeftFoot = frontBodyPartDialog.findViewById(R.id.front_left_foot);
               if("Front Left Foot".equals(location)){
                   frontLeftFoot.setImageResource(R.drawable.front_left_foot_selected);
               }


                //Gets a click for the Front Right Thigh button
               final ImageButton frontRightThigh = frontBodyPartDialog.findViewById(R.id.front_right_thigh);
               if("Front Right Thigh".equals(location)){
                   frontRightThigh.setImageResource(R.drawable.front_right_thigh_selected);
               }


               //Gets a click for the Front Right Foot button
               final ImageButton frontRightFoot = frontBodyPartDialog.findViewById(R.id.front_right_foot);
               if("Front Right Foot".equals(location)){
                   frontRightFoot.setImageResource(R.drawable.front_right_foot_selected);
               }


                //Gets a click for the Front Left Thigh button
               final ImageButton frontLeftThigh = frontBodyPartDialog.findViewById(R.id.front_left_thigh);
               if("Front Left Thigh".equals(location)){
                   frontLeftThigh.setImageResource(R.drawable.front_left_thigh_selected);
               }

                //Gets a click for the Abdomen button
               final ImageButton abdomen = frontBodyPartDialog.findViewById(R.id.abdomen);
               if("Abdomen".equals(location)){
                   abdomen.setImageResource(R.drawable.abdomen_selected);
               }

               //Gets a click for the Front Right Forearm button
               final ImageButton frontRightForearm = frontBodyPartDialog.findViewById(R.id.front_right_forearm);
               if("Front Right Forearm".equals(location)){
                   frontRightForearm.setImageResource(R.drawable.front_right_forearm_selected);
               }

               //Gets a click for the Front Left Forearm button
               final ImageButton frontLeftForearm = frontBodyPartDialog.findViewById(R.id.front_left_forearm);
               if("Front Left Forearm".equals(location)){
                   frontLeftForearm.setImageResource(R.drawable.front_left_forearm_selected);
               }

               //Gets a click for the Upper Chest button
               final ImageButton upperChest = frontBodyPartDialog.findViewById(R.id.upper_chest);
               if("Upper Chest".equals(location)){
                   upperChest.setImageResource(R.drawable.upper_chest_selected);
               }
               //Gets a click for the Right Shin button
               final ImageButton rightShin = frontBodyPartDialog.findViewById(R.id.right_shin);
               if("Right Shin".equals(location)){
                   rightShin.setImageResource(R.drawable.right_shin_selected);
               }

               //Gets a click for the Right Bicep button
               final ImageButton rightBicep = frontBodyPartDialog.findViewById(R.id.right_bicep);
               if("Right Bicep".equals(location)){
                   rightBicep.setImageResource(R.drawable.right_bicep_selected);
               }

               //Gets a click for the Groin button
               final ImageButton groin = frontBodyPartDialog.findViewById(R.id.groin);
               if("Groin".equals(location)){
                   groin.setImageResource(R.drawable.groin_selected);
               }

               //Gets a click for the Left Bicep button
               final ImageButton leftBicep = frontBodyPartDialog.findViewById(R.id.left_bicep);
               if("Left Bicep".equals(location)){
                   leftBicep.setImageResource(R.drawable.left_bicep_selected);
               }


               //Gets a click for the Front Left Knee button
               final ImageButton frontLeftKnee = frontBodyPartDialog.findViewById(R.id.front_left_knee);
               if("Front Left Knee".equals(location)){
                   frontLeftKnee.setImageResource(R.drawable.front_left_knee_selected);
               }

               //Gets a click for the Front Left Hand button
               final ImageButton frontLeftHand = frontBodyPartDialog.findViewById(R.id.front_left_hand);
               if("Front Left Hand".equals(location)){
                   frontLeftHand.setImageResource(R.drawable.front_left_hand_selected);
               }

               //Gets a click for the Front Right Knee button
               final ImageButton frontRightKnee = frontBodyPartDialog.findViewById(R.id.front_right_knee);
               if("Front Right Knee".equals(location)){
                   frontRightKnee.setImageResource(R.drawable.front_right_knee_selected);
               }

               //Gets a click for the Right Shoulder button
               final ImageButton rightShoulder = frontBodyPartDialog.findViewById(R.id.front_right_shoulder);
               if("Right Shoulder".equals(location)){
                   rightShoulder.setImageResource(R.drawable.front_right_shoulder_selected);
               }

               //Gets a click for the Left Shoulder button
               final ImageButton leftShoulder = frontBodyPartDialog.findViewById(R.id.front_left_shoulder);
               if("Left Shoulder".equals(location)){

                   leftShoulder.setImageResource(R.drawable.front_left_shoulder_selected);
               }

               //Gets a click for the Face button
               final ImageButton face = frontBodyPartDialog.findViewById(R.id.face);
               if(("Forehead".equals(location) || "Eyes".equals(location) || "Nose".equals(location)|| "Mouth".equals(location) || "Chin".equals(location) || "Right Cheek".equals(location) || "Left Cheek".equals(location)|| "Right Ear".equals(location) || "Left Ear".equals(location) || "Neck".equals(location))){
                   face.setImageResource(R.drawable.face_selected);
               }

               face.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v){
                       faceDialog();
                       frontBodyPartDialog.dismiss();
                   }
               });


           }
        }



        frontBodyPartDialog.show();
    }

    private void faceDialog() {
        final Dialog faceDialog = new Dialog(ViewFullRecordActivity.this);
        faceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        faceDialog.setContentView(R.layout.view_record_face);
        faceDialog.setTitle("Select Face Parts");

        final Button close = faceDialog.findViewById(R.id.faceSave);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontBodyPartDialog();
                faceDialog.dismiss();
            }
        });

        if (bodyLocation != null) {
            for (String location : bodyLocation) {

                //Gets a click for the Right Ear button
                final ImageButton rightEar = faceDialog.findViewById(R.id.right_ear);
                if ("Right Ear".equals(location)) {
                    rightEar.setImageResource(R.drawable.right_ear_selected);

                }


                //Gets a click for the Mouth button
                final ImageButton mouth = faceDialog.findViewById(R.id.mouth);
                if ("Mouth".equals(location)) {
                    mouth.setImageResource(R.drawable.mouth_selected);
                }


                //Gets a click for the Nose button
                final ImageButton nose = faceDialog.findViewById(R.id.nose);
                if ("Nose".equals(location)) {
                    nose.setImageResource(R.drawable.nose_selected);
                }


                //Gets a click for the Right Cheek button
                final ImageButton rightCheek = faceDialog.findViewById(R.id.right_cheek);
                if ("Right Cheek".equals(location)) {
                    rightCheek.setImageResource(R.drawable.right_cheek_selected);
                }

                //Gets a click for the Neck button
                final ImageButton neck = faceDialog.findViewById(R.id.neck);
                if ("Neck".equals(location)) {
                    neck.setImageResource(R.drawable.neck_selected);
                }


                //Gets a click for the Chin button
                final ImageButton chin = faceDialog.findViewById(R.id.chin);
                if ("Chin".equals(location)) {
                    chin.setImageResource(R.drawable.chin_selected);
                }


                //Gets a click for the Eyes button
                final ImageButton eyes = faceDialog.findViewById(R.id.eyes);
                if ("Eyes".equals(location)) {
                    eyes.setImageResource(R.drawable.eyes_selected);
                }


                //Gets a click for the Forehead button
                final ImageButton forehead = faceDialog.findViewById(R.id.forehead);
                if ("Forehead".equals(location)) {
                    forehead.setImageResource(R.drawable.forehead_selected);
                }


                //Gets a click for the Left Cheek button
                final ImageButton leftCheek = faceDialog.findViewById(R.id.left_cheek);
                if ("Left Cheek".equals(location)) {
                    leftCheek.setImageResource(R.drawable.left_cheek_selected);
                }


                //Gets a click for the Left Ear button
                final ImageButton leftEar = faceDialog.findViewById(R.id.left_ear);
                if ("Left Ear".equals(location)) {
                    leftEar.setImageResource(R.drawable.left_ear_selected);
                }

            }




        }
        faceDialog.show();
    }



    private void backBodyPartDialog() {
        final Dialog backBodyPartDialog = new Dialog(ViewFullRecordActivity.this);
        backBodyPartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        backBodyPartDialog.setContentView(R.layout.view_record_body_part_back);
        backBodyPartDialog.setTitle("Select Back Body Parts");

        final Button toFront = backBodyPartDialog.findViewById(R.id.linkToFront);
        toFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontBodyPartDialog();
                backBodyPartDialog.dismiss();
            }
        });

        final Button close = backBodyPartDialog.findViewById(R.id.saveclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBodyPartDialog.dismiss();
            }
        });

        if (bodyLocation != null) {
            for (String location : bodyLocation) {

                //Gets a click for the Back Right Forearm button
                final ImageButton backRightForearm = backBodyPartDialog.findViewById(R.id.back_right_forearm);
                if("Back Right Forearm".equals(location)){
                    backRightForearm.setImageResource(R.drawable.back_right_forearm_selected);
                }

                //Gets a click for the Left Buttox button
                final ImageButton leftButtox = backBodyPartDialog.findViewById(R.id.left_buttox);
                if("Left Buttox".equals(location)){
                    leftButtox.setImageResource(R.drawable.left_buttox_selected);
                }

                //Gets a click for the Back Head button
                final ImageButton backHead = backBodyPartDialog.findViewById(R.id.back_head);
                if("Back Head".equals(location)){
                    backHead.setImageResource(R.drawable.back_head_selected);
                }


                //Gets a click for the Back Right Foot button
                final ImageButton backRightFoot = backBodyPartDialog.findViewById(R.id.back_right_foot);
                if("Back Right Foot".equals(location)){
                    backRightFoot.setImageResource(R.drawable.back_right_foot_selected);
                }


                //Gets a click for the Back Left Hand button
                final ImageButton backLeftHand = backBodyPartDialog.findViewById(R.id.back_left_hand);
                if("Back Left Hand".equals(location)){
                    backLeftHand.setImageResource(R.drawable.back_left_hand_selected);
                }


                //Gets a click for the Back Left Ankle button
                final ImageButton backLeftAnkle = backBodyPartDialog.findViewById(R.id.back_left_ankle);
                if("Back Left Ankle".equals(location)){
                    backLeftAnkle.setImageResource(R.drawable.back_left_ankle_selected);
                }

                //Gets a click for the Back Left Knee button
                final ImageButton backLeftKnee = backBodyPartDialog.findViewById(R.id.back_left_knee);
                if("Back Left Knee".equals(location)){
                    backLeftKnee.setImageResource(R.drawable.back_left_knee_selected);
                }

                //Gets a click for the Left Tricep button
                final ImageButton leftTricep = backBodyPartDialog.findViewById(R.id.left_tricep);
                if("Left Tricep".equals(location)){
                    leftTricep.setImageResource(R.drawable.left_tricep_selected);
                }

                //Gets a click for the Back Right Shoulder button
                final ImageButton backRightShoulder = backBodyPartDialog.findViewById(R.id.back_right_shoulder);
                if("Back Right Shoulder".equals(location)){
                    backRightShoulder.setImageResource(R.drawable.back_right_shoulder_selected);
                }

                //Gets a click for the Back Left Foot button
                final ImageButton backLeftFoot = backBodyPartDialog.findViewById(R.id.back_left_foot);
                if("Back Left Foot".equals(location)){
                    backLeftFoot.setImageResource(R.drawable.back_left_foot_selected);
                }


                //Gets a click for the Back Left Forearm button
                final ImageButton backLeftForearm = backBodyPartDialog.findViewById(R.id.back_left_forearm);
                if("Back Left Forearm".equals(location)){
                    backLeftForearm.setImageResource(R.drawable.back_left_forearm_selected);
                }


                //Gets a click for the Back Left Shoulder button
                final ImageButton backLeftShoulder = backBodyPartDialog.findViewById(R.id.back_left_shoulder);
                if("Back Left Shoulder".equals(location)){
                    backLeftShoulder.setImageResource(R.drawable.back_left_shoulder_selected);
                }

                //Gets a click for the Back Right Ankle button
                final ImageButton backRightAnkle = backBodyPartDialog.findViewById(R.id.back_right_ankle);
                if("Back Right Ankle".equals(location)){
                    backRightAnkle.setImageResource(R.drawable.back_right_ankle_selected);
                }


                //Gets a click for the Lower Back button
                final ImageButton lowerBack = backBodyPartDialog.findViewById(R.id.lower_back);
                if("Lower Back".equals(location)){
                    lowerBack.setImageResource(R.drawable.lower_back_selected);
                }


                //Gets a click for the Upper Back button
                final ImageButton upperBack = backBodyPartDialog.findViewById(R.id.upper_back);
                if("Upper Back".equals(location)){
                    upperBack.setImageResource(R.drawable.upper_back_selected);
                }


                //Gets a click for the Right Buttox button
                final ImageButton rightButtox = backBodyPartDialog.findViewById(R.id.right_buttox);
                if("Right Buttox".equals(location)){
                    rightButtox.setImageResource(R.drawable.right_buttox_selected);
                }


                //Gets a click for the Back Right Thigh button
                final ImageButton backRightThigh = backBodyPartDialog.findViewById(R.id.back_right_thigh);
                if("Back Right Thigh".equals(location)){
                    backRightThigh.setImageResource(R.drawable.back_right_thigh_selected);
                }


                //Gets a click for the Right Tricep button
                final ImageButton rightTricep = backBodyPartDialog.findViewById(R.id.right_tricep);
                if("Right Tricep".equals(location)){
                    rightTricep.setImageResource(R.drawable.right_tricep_selected);
                }


                //Gets a click for the Mid Back button
                final ImageButton midBack = backBodyPartDialog.findViewById(R.id.mid_back);
                if("Mid Back".equals(location)){
                    midBack.setImageResource(R.drawable.mid_back_selected);
                }


                //Gets a click for the Back Left Calve button
                final ImageButton backLeftCalve = backBodyPartDialog.findViewById(R.id.back_left_calve);
                if("Back Left Calve".equals(location)){
                    backLeftCalve.setImageResource(R.drawable.back_left_calve_selected);
                }


                //Gets a click for the Back Right Calve button
                final ImageButton backRightCalve = backBodyPartDialog.findViewById(R.id.back_right_calve);
                if("Back Right Calve".equals(location)){
                    backRightCalve.setImageResource(R.drawable.back_right_calve_selected);
                }


                //Gets a click for the Back Right Hand button
                final ImageButton backRightHand = backBodyPartDialog.findViewById(R.id.back_right_hand);
                if("Back Right Hand".equals(location)){
                    backRightHand.setImageResource(R.drawable.back_right_hand_selected);

                }


                //Gets a click for the Back Right Knee button
                final ImageButton backRightKnee = backBodyPartDialog.findViewById(R.id.back_right_knee);
                if("Back Right Knee".equals(location)){
                    backRightKnee.setImageResource(R.drawable.back_right_knee_selected);
                }


                //Gets a click for the Back Left Thigh button
                final ImageButton backLeftThigh = backBodyPartDialog.findViewById(R.id.back_left_thigh);
                if("Back Left Thigh".equals(location)){
                    backLeftThigh.setImageResource(R.drawable.back_left_thigh_selected);
                }
            }
        }

        backBodyPartDialog.show();
    }

}
