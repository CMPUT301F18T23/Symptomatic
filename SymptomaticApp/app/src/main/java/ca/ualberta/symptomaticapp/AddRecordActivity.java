/*
 * AddRecordActivity.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Adds a user input record to database.
 *
 * Issues: Currently do not have the database linked.
 *
 */
package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class AddRecordActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 100; // to access the gallery to choose an image
    static final int REQUEST_IMAGE_CAPTURE = 1; // to access the camera to take an image
    static final int GET_GEOLOCATION = 2;
    private ListView photoListView;
    PhotoListViewAdapter photoListViewAdapter;
    ArrayList<Photo> displayPhotos;

    Button addBackBodyPart,addFrontBodyPart;

    private boolean backRightForearmSelected, leftButtoxSelected, backHeadSelected, backRightFootSelected, backLeftHandSelected, backLeftAnkleSelected, backLeftKneeSelected, leftTricepSelected, backRightShoulderSelected, backLeftFootSelected, backLeftForearmSelected, backLeftShoulderSelected, backRightAnkleSelected, lowerBackSelected, upperBackSelected, rightButtoxSelected, backRightThighSelected, rightTricepSelected, midBackSelected, backLeftCalveSelected, backRightCalveSelected, backRightHandSelected, backRightKneeSelected, backLeftThighSelected;
    private boolean frontRightHandSelected, leftShinSelected, frontLeftFootSelected, frontRightThighSelected, frontRightFootSelected, frontLeftThighSelected, abdomenSelected, frontRightForearmSelected, frontLeftForearmSelected, upperChestSelected, rightShinSelected, rightBicepSelected, groinSelected, leftBicepSelected, frontLeftKneeSelected, frontLeftHandSelected, faceSelected, frontRightKneeSelected, rightShoulderSelected, leftShoulderSelected;
    private boolean foreheadSelected, eyesSelected, noseSelected, mouthSelected, chinSelected, rightCheekSelected, leftCheekSelected, rightEarSelected, leftEarSelected, neckSelected;

    ArrayList<String> bodyPartsSelected;

    Bitmap bmp;

    PhotoList photoList = new PhotoList();

    String mCurrentPhotoPath; // the photo's file path

    Problem problem;
    int year, month, day, hour, min;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private TimePickerDialog.OnTimeSetListener TimeSetListener;


    private LatLng geolocation;

    private String geolocationString;
    private double lat, lng;

    TextView currentTime;
    String currentTimeString;
    boolean timeChanged;
    Date timeStamp;
    EditText commentEdit,titleEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = findViewById(R.id.addRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Record");
        final Calendar cal = Calendar.getInstance();
//        ListView photoListView = findViewById(R.id.photoListView);

        bodyPartsSelected = new ArrayList<>();

        backRightForearmSelected = false; leftButtoxSelected = false; backHeadSelected = false; backRightFootSelected = false; backLeftHandSelected = false; backLeftAnkleSelected = false; backLeftKneeSelected = false; leftTricepSelected = false; backRightShoulderSelected = false; backLeftFootSelected = false; backLeftForearmSelected = false; backLeftShoulderSelected = false; backRightAnkleSelected = false; lowerBackSelected = false; upperBackSelected = false; rightButtoxSelected = false; backRightThighSelected = false; rightTricepSelected = false; midBackSelected = false; backLeftCalveSelected = false; backRightCalveSelected = false; backRightHandSelected = false; backRightKneeSelected = false; backLeftThighSelected = false;
        frontRightHandSelected = false; leftShinSelected = false; frontLeftFootSelected = false; frontRightThighSelected = false; frontRightFootSelected = false; frontLeftThighSelected = false; abdomenSelected = false; frontRightForearmSelected = false; frontLeftForearmSelected = false; upperChestSelected = false; rightShinSelected = false; rightBicepSelected = false; groinSelected = false; leftBicepSelected = false; frontLeftKneeSelected = false; frontLeftHandSelected = false; faceSelected = false; frontRightKneeSelected = false; rightShoulderSelected = false; leftShoulderSelected = false;
        foreheadSelected = false; eyesSelected = false; noseSelected = false; mouthSelected = false; chinSelected = false; rightCheekSelected = false; leftCheekSelected = false; rightEarSelected = false; leftEarSelected = false; neckSelected = false;


        problem = (Problem) getIntent().getSerializableExtra("problem");

        addBackBodyPart = findViewById(R.id.addBackBodyPart);
        addFrontBodyPart = findViewById(R.id.addFrontBodyPart);

        photoListView = findViewById(R.id.photoListView);
        displayPhotos = new ArrayList<Photo>();

        TextView textView = findViewById(R.id.InputProblemTextView);
        textView.setText(problem.getTitle());
        commentEdit = findViewById(R.id.addCommentEditText);
        titleEdit = findViewById(R.id.addTitleEditText);

        currentTime = findViewById(R.id.currentTimeTextView);
        currentTimeString = "Current Time: " + cal.getTime().toString();
        currentTime.setText(currentTimeString);

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        

        addFrontBodyPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontBodyPartDialog();
            }
        });

        addBackBodyPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBodyPartDialog();
            }
        });


        Button dateButton = findViewById(R.id.changeDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentYear = cal.get(Calendar.YEAR);
                int currentMonth = cal.get(Calendar.MONTH);
                int currentDay = cal.get(Calendar.DAY_OF_MONTH);

                TimePickerDialog editTimeDialog = new TimePickerDialog(AddRecordActivity.this, android.R.style.Theme_DeviceDefault_Dialog, TimeSetListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(AddRecordActivity.this));
                editTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                editTimeDialog.show();

                DatePickerDialog dialog = new DatePickerDialog(AddRecordActivity.this, android.R.style.Theme_DeviceDefault_Dialog, DateSetListener, currentYear, currentMonth, currentDay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();

            }
        });

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int chosenYear, int chosenMonth, int chosenDay) {
                year = chosenYear;
                month = chosenMonth;
                day = chosenDay;
            }
        };

        TimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                updateTime();

            }
        };

       Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecordActivity.this, MapsActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, GET_GEOLOCATION);
            }
        });


        initListView();

        //todo: show the chosen date
        final Button savedPhoto = findViewById(R.id.savedPhoto);
        final Button takePhoto = findViewById(R.id.takePhoto);

        savedPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }

        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the intent to take the photo
                Intent takePictureintent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

//                // Create the name of the file for the photo
//                try {
//                    File photoDir = createImageFile();
//                    // for testing: Print the photo's path
//                    Log.d("Photo Directory:", mCurrentPhotoPath);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                // Start the camera for the photo to be taken
                startActivityForResult(takePictureintent, REQUEST_IMAGE_CAPTURE);

            }
        });

        Button saveRecordBttn = findViewById(R.id.saveRecordBttn);

        saveRecordBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean goodRecord = true;

                if (goodRecord) {
                    // Prepare the attributes required to instantiate the Record class
                    if (timeChanged){
                        final Calendar cal = Calendar.getInstance();
                        cal.set(year, month, day, hour, min);
                        timeStamp = cal.getTime();


                    } else{
                        timeStamp = Calendar.getInstance().getTime();
                    }
                    String currProbName = problem.getTitle();
                    //Date currDate = Calendar.getInstance().getTime();

                    String comment = commentEdit.getText().toString();
                    String title = titleEdit.getText().toString();

                    // Create the new record
                    Record currRecord = new Record(currProbName, timeStamp,Login.thisUser.returnUsername(),title);
                    currRecord.addComment(comment);

                    //currRecord.setPhotoList(displayPhotos);
                    currRecord.addGeolocation(geolocationString);

              //so     currRecord.setPhotoList(displayPhotos);

                    currRecord.addRecToDb();

                    // Switch back to the previous activity
                    Intent intent = new Intent(AddRecordActivity.this, ListRecordsActivity.class);
                    intent.putExtra("problem", problem);
                    startActivity(intent);
                }

            }
        });



    }

    public void updateTime(){
        final Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, min);
        currentTime = findViewById(R.id.currentTimeTextView);
        currentTimeString = "Current Time: " + cal.getTime().toString();
        currentTime.setText(currentTimeString);
        timeChanged = true;


    }



    public void initListView(){
        if(photoListViewAdapter == null){
            photoListViewAdapter = new PhotoListViewAdapter(displayPhotos, this);
        }

        photoListView.setAdapter(photoListViewAdapter);

    }

    // setListViewHeightBasedonChildren class reference:
//    Skidan, Oleg. “ListView inside ScrollView. Solve the Problem. – Oleg Skidan – Medium.” Medium.com, Medium,
//    5 Feb. 2016, medium.com/@skidanolegs/listview-inside-scrollview-solve-the-problem-a06fdff2a4e0.
//    Accessed: 25th November, 2018
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            // If selecting a photo from gallery
            case PICK_IMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        // Convert the photo selected from gallery into a bitmap and display it
                        bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                        iv.setImageBitmap(bmp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Create an instance of the Photo class
                    Photo photo = new Photo(bmp);
                    if (displayPhotos.size() < 10) {
                        displayPhotos.add(photo);
                        photoListViewAdapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(photoListView);
                    } else {
                        Toast.makeText(getApplicationContext(), "Max. 10 Photos Allowed", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case REQUEST_IMAGE_CAPTURE:
                // If capturing a photo using the camera
                if (resultCode == RESULT_OK) {
                    // Do try and catch
                    try {
                        // Convert the photo captured into a bitmap
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");


                        // Store the image as a Photo object
                        Photo photo = new Photo(bitmap);

                        if (displayPhotos.size() < 10) {
                            displayPhotos.add(photo);
                            photoListViewAdapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(photoListView);
                        } else {
                            Toast.makeText(getApplicationContext(), "Max. 10 Photos Allowed", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Log.d("ERROR", "Captured photo could not be created.");
                    }


                }
                break;

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

//    // Reference: https://developer.android.com/training/camera/photobasics#java
//    private File createImageFile() throws IOException {
//        // Create the image file name with its timestamp
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String photoFileName = "JPEG_" + timeStamp + "_";
//
//        // Get the app's directory for photos
//        File picturesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//
//        // Create the file
//        File photo = File.createTempFile(
//                photoFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                picturesDir      /* directory */
//        );
//
//        // Store the photo's path name and return the image File
//        mCurrentPhotoPath = photo.getAbsolutePath();
//        return photo;
//    }



    //EVERYTHING BELOW HERE IS FOR THE BODY PART DIALOGS

    private void frontBodyPartDialog(){
        final Dialog frontBodyPartDialog = new Dialog(AddRecordActivity.this);
        frontBodyPartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        frontBodyPartDialog.setContentView(R.layout.body_part_front);
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

        //Gets a click for the Front Right Hand button
        final ImageButton frontRightHand = frontBodyPartDialog.findViewById(R.id.front_right_hand);
        frontRightHand.setEnabled(true);
        if(frontRightHandSelected){
            frontRightHand.setImageResource(R.drawable.front_right_hand_selected);
        }
        frontRightHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(frontRightHandSelected){
                    frontRightHand.setImageResource(R.drawable.front_right_hand);
                    frontRightHandSelected = false;
                    bodyPartsSelected.remove("Front Right Hand");
                } else {
                    frontRightHand.setImageResource(R.drawable.front_right_hand_selected);
                    frontRightHandSelected = true;
                    bodyPartsSelected.add("Front Right Hand");
                }
            }
        });


//Gets a click for the Left Shin button
        final ImageButton leftShin = frontBodyPartDialog.findViewById(R.id.left_shin);
        leftShin.setEnabled(true);
        if(leftShinSelected){
            leftShin.setImageResource(R.drawable.left_shin_selected);
        }
        leftShin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(leftShinSelected){
                    leftShin.setImageResource(R.drawable.left_shin);
                    leftShinSelected = false;
                    bodyPartsSelected.remove("Left Shin");
                } else {
                    leftShin.setImageResource(R.drawable.left_shin_selected);
                    leftShinSelected = true;
                    bodyPartsSelected.add("Left Shin");
                }
            }
        });


//Gets a click for the Front Left Foot button
        final ImageButton frontLeftFoot = frontBodyPartDialog.findViewById(R.id.front_left_foot);
        frontLeftFoot.setEnabled(true);
        if(frontLeftFootSelected){
            frontLeftFoot.setImageResource(R.drawable.front_left_foot_selected);
        }
        frontLeftFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(frontLeftFootSelected){
                    frontLeftFoot.setImageResource(R.drawable.front_left_foot);
                    frontLeftFootSelected = false;
                    bodyPartsSelected.remove("Front Left Foot");
                } else {
                    frontLeftFoot.setImageResource(R.drawable.front_left_foot_selected);
                    frontLeftFootSelected = true;
                    bodyPartsSelected.add("Front Left Foot");
                }
            }
        });


//Gets a click for the Front Right Thigh button
        final ImageButton frontRightThigh = frontBodyPartDialog.findViewById(R.id.front_right_thigh);
        frontRightThigh.setEnabled(true);
        if(frontRightThighSelected){
            frontRightThigh.setImageResource(R.drawable.front_right_thigh_selected);
        }
        frontRightThigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(frontRightThighSelected){
                    frontRightThigh.setImageResource(R.drawable.front_right_thigh);
                    frontRightThighSelected = false;
                    bodyPartsSelected.remove("Front Right Thigh");
                } else {
                    frontRightThigh.setImageResource(R.drawable.front_right_thigh_selected);
                    frontRightThighSelected = true;
                    bodyPartsSelected.add("Front Right Thigh");
                }
            }
        });


//Gets a click for the Front Right Foot button
        final ImageButton frontRightFoot = frontBodyPartDialog.findViewById(R.id.front_right_foot);
        frontRightFoot.setEnabled(true);
        if(frontRightFootSelected){
            frontRightFoot.setImageResource(R.drawable.front_right_foot_selected);
        }
        frontRightFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(frontRightFootSelected){
                    frontRightFoot.setImageResource(R.drawable.front_right_foot);
                    frontRightFootSelected = false;
                    bodyPartsSelected.remove("Front Right Foot");
                } else {
                    frontRightFoot.setImageResource(R.drawable.front_right_foot_selected);
                    frontRightFootSelected = true;
                    bodyPartsSelected.add("Front Right Foot");
                }
            }
        });


//Gets a click for the Front Left Thigh button
        final ImageButton frontLeftThigh = frontBodyPartDialog.findViewById(R.id.front_left_thigh);
        frontLeftThigh.setEnabled(true);
        if(frontLeftThighSelected){
            frontLeftThigh.setImageResource(R.drawable.front_left_thigh_selected);
        }
        frontLeftThigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(frontLeftThighSelected){
                    frontLeftThigh.setImageResource(R.drawable.front_left_thigh);
                    frontLeftThighSelected = false;
                    bodyPartsSelected.remove("Front Left Thigh");
                } else {
                    frontLeftThigh.setImageResource(R.drawable.front_left_thigh_selected);
                    frontLeftThighSelected = true;
                    bodyPartsSelected.add("Front Left Thigh");
                }
            }
        });


//Gets a click for the Abdomen button
        final ImageButton abdomen = frontBodyPartDialog.findViewById(R.id.abdomen);
        abdomen.setEnabled(true);
        if(abdomenSelected){
            abdomen.setImageResource(R.drawable.abdomen_selected);
        }
        abdomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(abdomenSelected){
                    abdomen.setImageResource(R.drawable.abdomen);
                    abdomenSelected = false;
                    bodyPartsSelected.remove("Abdomen");
                } else {
                    abdomen.setImageResource(R.drawable.abdomen_selected);
                    abdomenSelected = true;
                    bodyPartsSelected.add("Abdomen");
                }
            }
        });


//Gets a click for the Front Right Forearm button
        final ImageButton frontRightForearm = frontBodyPartDialog.findViewById(R.id.front_right_forearm);
        frontRightForearm.setEnabled(true);
        if(frontRightForearmSelected){
            frontRightForearm.setImageResource(R.drawable.front_right_forearm_selected);
        }
        frontRightForearm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(frontRightForearmSelected){
                    frontRightForearm.setImageResource(R.drawable.front_right_forearm);
                    frontRightForearmSelected = false;
                    bodyPartsSelected.remove("Front Right Forearm");
                } else {
                    frontRightForearm.setImageResource(R.drawable.front_right_forearm_selected);
                    frontRightForearmSelected = true;
                    bodyPartsSelected.add("Front Right Forearm");
                }
            }
        });


//Gets a click for the Front Left Forearm button
        final ImageButton frontLeftForearm = frontBodyPartDialog.findViewById(R.id.front_left_forearm);
        frontLeftForearm.setEnabled(true);
        if(frontLeftForearmSelected){
            frontLeftForearm.setImageResource(R.drawable.front_left_forearm_selected);
        }
        frontLeftForearm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(frontLeftForearmSelected){
                    frontLeftForearm.setImageResource(R.drawable.front_left_forearm);
                    frontLeftForearmSelected = false;
                    bodyPartsSelected.remove("Front Left Forearm");
                } else {
                    frontLeftForearm.setImageResource(R.drawable.front_left_forearm_selected);
                    frontLeftForearmSelected = true;
                    bodyPartsSelected.add("Front Left Forearm");
                }
            }
        });


//Gets a click for the Upper Chest button
        final ImageButton upperChest = frontBodyPartDialog.findViewById(R.id.upper_chest);
        upperChest.setEnabled(true);
        if(upperChestSelected){
            upperChest.setImageResource(R.drawable.upper_chest_selected);
        }
        upperChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(upperChestSelected){
                    upperChest.setImageResource(R.drawable.upper_chest);
                    upperChestSelected = false;
                    bodyPartsSelected.remove("Upper Chest");
                } else {
                    upperChest.setImageResource(R.drawable.upper_chest_selected);
                    upperChestSelected = true;
                    bodyPartsSelected.add("Upper Chest");
                }
            }
        });


//Gets a click for the Right Shin button
        final ImageButton rightShin = frontBodyPartDialog.findViewById(R.id.right_shin);
        rightShin.setEnabled(true);
        if(rightShinSelected){
            rightShin.setImageResource(R.drawable.right_shin_selected);
        }
        rightShin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(rightShinSelected){
                    rightShin.setImageResource(R.drawable.right_shin);
                    rightShinSelected = false;
                    bodyPartsSelected.remove("Right Shin");
                } else {
                    rightShin.setImageResource(R.drawable.right_shin_selected);
                    rightShinSelected = true;
                    bodyPartsSelected.add("Right Shin");
                }
            }
        });


//Gets a click for the Right Bicep button
        final ImageButton rightBicep = frontBodyPartDialog.findViewById(R.id.right_bicep);
        rightBicep.setEnabled(true);
        if(rightBicepSelected){
            rightBicep.setImageResource(R.drawable.right_bicep_selected);
        }
        rightBicep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(rightBicepSelected){
                    rightBicep.setImageResource(R.drawable.right_bicep);
                    rightBicepSelected = false;
                    bodyPartsSelected.remove("Right Bicep");
                } else {
                    rightBicep.setImageResource(R.drawable.right_bicep_selected);
                    rightBicepSelected = true;
                    bodyPartsSelected.add("Right Bicep");
                }
            }
        });


//Gets a click for the Groin button
        final ImageButton groin = frontBodyPartDialog.findViewById(R.id.groin);
        groin.setEnabled(true);
        if(groinSelected){
            groin.setImageResource(R.drawable.groin_selected);
        }
        groin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(groinSelected){
                    groin.setImageResource(R.drawable.groin);
                    groinSelected = false;
                    bodyPartsSelected.remove("Groin");
                } else {
                    groin.setImageResource(R.drawable.groin_selected);
                    groinSelected = true;
                    bodyPartsSelected.add("Groin");
                }
            }
        });


//Gets a click for the Left Bicep button
        final ImageButton leftBicep = frontBodyPartDialog.findViewById(R.id.left_bicep);
        leftBicep.setEnabled(true);
        if(leftBicepSelected){
            leftBicep.setImageResource(R.drawable.left_bicep_selected);
        }
        leftBicep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(leftBicepSelected){
                    leftBicep.setImageResource(R.drawable.left_bicep);
                    leftBicepSelected = false;
                    bodyPartsSelected.remove("Left Bicep");
                } else {
                    leftBicep.setImageResource(R.drawable.left_bicep_selected);
                    leftBicepSelected = true;
                    bodyPartsSelected.add("Left Bicep");
                }
            }
        });


//Gets a click for the Front Left Knee button
        final ImageButton frontLeftKnee = frontBodyPartDialog.findViewById(R.id.front_left_knee);
        frontLeftKnee.setEnabled(true);
        if(frontLeftKneeSelected){
            frontLeftKnee.setImageResource(R.drawable.front_left_knee_selected);
        }
        frontLeftKnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(frontLeftKneeSelected){
                    frontLeftKnee.setImageResource(R.drawable.front_left_knee);
                    frontLeftKneeSelected = false;
                    bodyPartsSelected.remove("Front Left Knee");
                } else {
                    frontLeftKnee.setImageResource(R.drawable.front_left_knee_selected);
                    frontLeftKneeSelected = true;
                    bodyPartsSelected.add("Front Left Knee");
                }
            }
        });


//Gets a click for the Front Left Hand button
        final ImageButton frontLeftHand = frontBodyPartDialog.findViewById(R.id.front_left_hand);
        frontLeftHand.setEnabled(true);
        if(frontLeftHandSelected){
            frontLeftHand.setImageResource(R.drawable.front_left_hand_selected);
        }
        frontLeftHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(frontLeftHandSelected){
                    frontLeftHand.setImageResource(R.drawable.front_left_hand);
                    frontLeftHandSelected = false;
                    bodyPartsSelected.remove("Front Left Hand");
                } else {
                    frontLeftHand.setImageResource(R.drawable.front_left_hand_selected);
                    frontLeftHandSelected = true;
                    bodyPartsSelected.add("Front Left Hand");
                }
            }
        });


//Gets a click for the Face button
        final ImageButton face = frontBodyPartDialog.findViewById(R.id.face);
        face.setEnabled(true);
        if((foreheadSelected || eyesSelected || noseSelected || mouthSelected || chinSelected || rightCheekSelected || leftCheekSelected || rightEarSelected || leftEarSelected || neckSelected)){
            face.setImageResource(R.drawable.face_selected);
        } else {
            face.setImageResource(R.drawable.face);

        }
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                faceDialog();
                frontBodyPartDialog.dismiss();
            }
        });


//Gets a click for the Front Right Knee button
        final ImageButton frontRightKnee = frontBodyPartDialog.findViewById(R.id.front_right_knee);
        frontRightKnee.setEnabled(true);
        if(frontRightKneeSelected){
            frontRightKnee.setImageResource(R.drawable.front_right_knee_selected);
        }
        frontRightKnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(frontRightKneeSelected){
                    frontRightKnee.setImageResource(R.drawable.front_right_knee);
                    frontRightKneeSelected = false;
                    bodyPartsSelected.remove("Front Right Knee");
                } else {
                    frontRightKnee.setImageResource(R.drawable.front_right_knee_selected);
                    frontRightKneeSelected = true;
                    bodyPartsSelected.add("Front Right Knee");
                }
            }
        });


//Gets a click for the Right Shoulder button
        final ImageButton rightShoulder = frontBodyPartDialog.findViewById(R.id.front_right_shoulder);
        rightShoulder.setEnabled(true);
        if(rightShoulderSelected){
            rightShoulder.setImageResource(R.drawable.front_right_shoulder_selected);
        }
        rightShoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(rightShoulderSelected){
                    rightShoulder.setImageResource(R.drawable.front_right_shoulder);
                    rightShoulderSelected = false;
                    bodyPartsSelected.remove("Right Shoulder");
                } else {
                    rightShoulder.setImageResource(R.drawable.front_right_shoulder_selected);
                    rightShoulderSelected = true;
                    bodyPartsSelected.add("Right Shoulder");
                }
            }
        });


//Gets a click for the Left Shoulder button
        final ImageButton leftShoulder = frontBodyPartDialog.findViewById(R.id.front_left_shoulder);
        leftShoulder.setEnabled(true);
        if(leftShoulderSelected){
            leftShoulder.setImageResource(R.drawable.front_left_shoulder_selected);
        }
        leftShoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(leftShoulderSelected){
                    leftShoulder.setImageResource(R.drawable.front_left_shoulder);
                    leftShoulderSelected = false;
                    bodyPartsSelected.remove("Left Shoulder");
                } else {
                    leftShoulder.setImageResource(R.drawable.front_left_shoulder_selected);
                    leftShoulderSelected = true;
                    bodyPartsSelected.add("Left Shoulder");
                }
            }
        });

        frontBodyPartDialog.show();
    }

    private void backBodyPartDialog() {
        final Dialog backBodyPartDialog = new Dialog(AddRecordActivity.this);
        backBodyPartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        backBodyPartDialog.setContentView(R.layout.body_part_back);
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

        //Gets a click for the Back Right Forearm button
        final ImageButton backRightForearm = backBodyPartDialog.findViewById(R.id.back_right_forearm);
        backRightForearm.setEnabled(true);
        if(backRightForearmSelected){
            backRightForearm.setImageResource(R.drawable.back_right_forearm_selected);
        }
        backRightForearm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backRightForearmSelected){
                    backRightForearm.setImageResource(R.drawable.back_right_forearm);
                    backRightForearmSelected = false;
                    bodyPartsSelected.remove("Back Right Forearm");
                } else {
                    backRightForearm.setImageResource(R.drawable.back_right_forearm_selected);
                    backRightForearmSelected = true;
                    bodyPartsSelected.add("Back Right Forearm");
                }
            }
        });


//Gets a click for the Left Buttox button
        final ImageButton leftButtox = backBodyPartDialog.findViewById(R.id.left_buttox);
        leftButtox.setEnabled(true);
        if(leftButtoxSelected){
            leftButtox.setImageResource(R.drawable.left_buttox_selected);
        }
        leftButtox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(leftButtoxSelected){
                    leftButtox.setImageResource(R.drawable.back_left_buttox);
                    leftButtoxSelected = false;
                    bodyPartsSelected.remove("Left Buttox");
                } else {
                    leftButtox.setImageResource(R.drawable.left_buttox_selected);
                    leftButtoxSelected = true;
                    bodyPartsSelected.add("Left Buttox");
                }
            }
        });


//Gets a click for the Back Head button
        final ImageButton backHead = backBodyPartDialog.findViewById(R.id.back_head);
        backHead.setEnabled(true);
        if(backHeadSelected){
            backHead.setImageResource(R.drawable.back_head_selected);
        }
        backHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backHeadSelected){
                    backHead.setImageResource(R.drawable.back_head);
                    backHeadSelected = false;
                    bodyPartsSelected.remove("Back Head");
                } else {
                    backHead.setImageResource(R.drawable.back_head_selected);
                    backHeadSelected = true;
                    bodyPartsSelected.add("Back Head");
                }
            }
        });


//Gets a click for the Back Right Foot button
        final ImageButton backRightFoot = backBodyPartDialog.findViewById(R.id.back_right_foot);
        backRightFoot.setEnabled(true);
        if(backRightFootSelected){
            backRightFoot.setImageResource(R.drawable.back_right_foot_selected);
        }
        backRightFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backRightFootSelected){
                    backRightFoot.setImageResource(R.drawable.back_right_foot);
                    backRightFootSelected = false;
                    bodyPartsSelected.remove("Back Right Foot");
                } else {
                    backRightFoot.setImageResource(R.drawable.back_right_foot_selected);
                    backRightFootSelected = true;
                    bodyPartsSelected.add("Back Right Foot");
                }
            }
        });


//Gets a click for the Back Left Hand button
        final ImageButton backLeftHand = backBodyPartDialog.findViewById(R.id.back_left_hand);
        backLeftHand.setEnabled(true);
        if(backLeftHandSelected){
            backLeftHand.setImageResource(R.drawable.back_left_hand_selected);
        }
        backLeftHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backLeftHandSelected){
                    backLeftHand.setImageResource(R.drawable.back_left_hand);
                    backLeftHandSelected = false;
                    bodyPartsSelected.remove("Back Left Hand");
                } else {
                    backLeftHand.setImageResource(R.drawable.back_left_hand_selected);
                    backLeftHandSelected = true;
                    bodyPartsSelected.add("Back Left Hand");
                }
            }
        });


        //Gets a click for the Back Left Ankle button
        final ImageButton backLeftAnkle = backBodyPartDialog.findViewById(R.id.back_left_ankle);
        backLeftAnkle.setEnabled(true);
        if(backLeftAnkleSelected){
            backLeftAnkle.setImageResource(R.drawable.back_left_ankle_selected);
        }
        backLeftAnkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backLeftAnkleSelected){
                    backLeftAnkle.setImageResource(R.drawable.back_left_ankle);
                    backLeftAnkleSelected = false;
                    bodyPartsSelected.remove("Back Left Ankle");
                } else {
                    backLeftAnkle.setImageResource(R.drawable.back_left_ankle_selected);
                    backLeftAnkleSelected = true;
                    bodyPartsSelected.add("Back Left Ankle");
                }
            }
        });


        //Gets a click for the Back Left Knee button
        final ImageButton backLeftKnee = backBodyPartDialog.findViewById(R.id.back_left_knee);
        backLeftKnee.setEnabled(true);
        if(backLeftKneeSelected){
            backLeftKnee.setImageResource(R.drawable.back_left_knee_selected);
        }
        backLeftKnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backLeftKneeSelected){
                    backLeftKnee.setImageResource(R.drawable.back_left_knee);
                    backLeftKneeSelected = false;
                    bodyPartsSelected.remove("Back Left Knee");
                } else {
                    backLeftKnee.setImageResource(R.drawable.back_left_knee_selected);
                    backLeftKneeSelected = true;
                    bodyPartsSelected.add("Back Left Knee");
                }
            }
        });


        //Gets a click for the Left Tricep button
        final ImageButton leftTricep = backBodyPartDialog.findViewById(R.id.left_tricep);
        leftTricep.setEnabled(true);
        if(leftTricepSelected){
            leftTricep.setImageResource(R.drawable.left_tricep_selected);
        }
        leftTricep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(leftTricepSelected){
                    leftTricep.setImageResource(R.drawable.left_tricep);
                    leftTricepSelected = false;
                    bodyPartsSelected.remove("Left Tricep");
                } else {
                    leftTricep.setImageResource(R.drawable.left_tricep_selected);
                    leftTricepSelected = true;
                    bodyPartsSelected.add("Left Tricep");
                }
            }
        });


        //Gets a click for the Back Right Shoulder button
        final ImageButton backRightShoulder = backBodyPartDialog.findViewById(R.id.back_right_shoulder);
        backRightShoulder.setEnabled(true);
        if(backRightShoulderSelected){
            backRightShoulder.setImageResource(R.drawable.back_right_shoulder_selected);
        }
        backRightShoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backRightShoulderSelected){
                    backRightShoulder.setImageResource(R.drawable.back_right_shoulder);
                    backRightShoulderSelected = false;
                    bodyPartsSelected.remove("Back Right Shoulder");
                } else {
                    backRightShoulder.setImageResource(R.drawable.back_right_shoulder_selected);
                    backRightShoulderSelected = true;
                    bodyPartsSelected.add("Back Right Shoulder");
                }
            }
        });


        //Gets a click for the Back Left Foot button
        final ImageButton backLeftFoot = backBodyPartDialog.findViewById(R.id.back_left_foot);
        backLeftFoot.setEnabled(true);
        if(backLeftFootSelected){
            backLeftFoot.setImageResource(R.drawable.back_left_foot_selected);
        }
        backLeftFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backLeftFootSelected){
                    backLeftFoot.setImageResource(R.drawable.back_left_foot);
                    backLeftFootSelected = false;
                    bodyPartsSelected.remove("Back Left Foot");
                } else {
                    backLeftFoot.setImageResource(R.drawable.back_left_foot_selected);
                    backLeftFootSelected = true;
                    bodyPartsSelected.add("Back Left Foot");
                }
            }
        });


        //Gets a click for the Back Left Forearm button
        final ImageButton backLeftForearm = backBodyPartDialog.findViewById(R.id.back_left_forearm);
        backLeftForearm.setEnabled(true);
        if(backLeftForearmSelected){
            backLeftForearm.setImageResource(R.drawable.back_left_forearm_selected);
        }
        backLeftForearm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backLeftForearmSelected){
                    backLeftForearm.setImageResource(R.drawable.back_left_forearm);
                    backLeftForearmSelected = false;
                    bodyPartsSelected.remove("Back Left Forearm");
                } else {
                    backLeftForearm.setImageResource(R.drawable.back_left_forearm_selected);
                    backLeftForearmSelected = true;
                    bodyPartsSelected.add("Back Left Forearm");
                }
            }
        });


        //Gets a click for the Back Left Shoulder button
        final ImageButton backLeftShoulder = backBodyPartDialog.findViewById(R.id.back_left_shoulder);
        backLeftShoulder.setEnabled(true);
        if(backLeftShoulderSelected){
            backLeftShoulder.setImageResource(R.drawable.back_left_shoulder_selected);
        }
        backLeftShoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backLeftShoulderSelected){
                    backLeftShoulder.setImageResource(R.drawable.back_left_shoulder);
                    backLeftShoulderSelected = false;
                    bodyPartsSelected.remove("Back Left Shoulder");
                } else {
                    backLeftShoulder.setImageResource(R.drawable.back_left_shoulder_selected);
                    backLeftShoulderSelected = true;
                    bodyPartsSelected.add("Back Left Shoulder");
                }
            }
        });


        //Gets a click for the Back Right Ankle button
        final ImageButton backRightAnkle = backBodyPartDialog.findViewById(R.id.back_right_ankle);
        backRightAnkle.setEnabled(true);
        if(backRightAnkleSelected){
            backRightAnkle.setImageResource(R.drawable.back_right_ankle_selected);
        }
        backRightAnkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backRightAnkleSelected){
                    backRightAnkle.setImageResource(R.drawable.back_right_ankle);
                    backRightAnkleSelected = false;
                    bodyPartsSelected.remove("Back Right Ankle");
                } else {
                    backRightAnkle.setImageResource(R.drawable.back_right_ankle_selected);
                    backRightAnkleSelected = true;
                    bodyPartsSelected.add("Back Right Ankle");
                }
            }
        });


        //Gets a click for the Lower Back button
        final ImageButton lowerBack = backBodyPartDialog.findViewById(R.id.lower_back);
        lowerBack.setEnabled(true);
        if(lowerBackSelected){
            lowerBack.setImageResource(R.drawable.lower_back_selected);
        }
        lowerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(lowerBackSelected){
                    lowerBack.setImageResource(R.drawable.lower_back);
                    lowerBackSelected = false;
                    bodyPartsSelected.remove("Lower Back");
                } else {
                    lowerBack.setImageResource(R.drawable.lower_back_selected);
                    lowerBackSelected = true;
                    bodyPartsSelected.add("Lower Back");
                }
            }
        });


        //Gets a click for the Upper Back button
        final ImageButton upperBack = backBodyPartDialog.findViewById(R.id.upper_back);
        upperBack.setEnabled(true);
        if(upperBackSelected){
            upperBack.setImageResource(R.drawable.upper_back_selected);
        }
        upperBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(upperBackSelected){
                    upperBack.setImageResource(R.drawable.upper_back);
                    upperBackSelected = false;
                    bodyPartsSelected.remove("Upper Back");
                } else {
                    upperBack.setImageResource(R.drawable.upper_back_selected);
                    upperBackSelected = true;
                    bodyPartsSelected.add("Upper Back");
                }
            }
        });


        //Gets a click for the Right Buttox button
        final ImageButton rightButtox = backBodyPartDialog.findViewById(R.id.right_buttox);
        rightButtox.setEnabled(true);
        if(rightButtoxSelected){
            rightButtox.setImageResource(R.drawable.right_buttox_selected);
        }
        rightButtox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(rightButtoxSelected){
                    rightButtox.setImageResource(R.drawable.back_right_buttox);
                    rightButtoxSelected = false;
                    bodyPartsSelected.remove("Right Buttox");
                } else {
                    rightButtox.setImageResource(R.drawable.right_buttox_selected);
                    rightButtoxSelected = true;
                    bodyPartsSelected.add("Right Buttox");
                }
            }
        });


        //Gets a click for the Back Right Thigh button
        final ImageButton backRightThigh = backBodyPartDialog.findViewById(R.id.back_right_thigh);
        backRightThigh.setEnabled(true);
        if(backRightThighSelected){
            backRightThigh.setImageResource(R.drawable.back_right_thigh_selected);
        }
        backRightThigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backRightThighSelected){
                    backRightThigh.setImageResource(R.drawable.back_right_thigh);
                    backRightThighSelected = false;
                    bodyPartsSelected.remove("Back Right Thigh");
                } else {
                    backRightThigh.setImageResource(R.drawable.back_right_thigh_selected);
                    backRightThighSelected = true;
                    bodyPartsSelected.add("Back Right Thigh");
                }
            }
        });


        //Gets a click for the Right Tricep button
        final ImageButton rightTricep = backBodyPartDialog.findViewById(R.id.right_tricep);
        rightTricep.setEnabled(true);
        if(rightTricepSelected){
            rightTricep.setImageResource(R.drawable.right_tricep_selected);
        }
        rightTricep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(rightTricepSelected){
                    rightTricep.setImageResource(R.drawable.right_tricep);
                    rightTricepSelected = false;
                    bodyPartsSelected.remove("Right Tricep");
                } else {
                    rightTricep.setImageResource(R.drawable.right_tricep_selected);
                    rightTricepSelected = true;
                    bodyPartsSelected.add("Right Tricep");
                }
            }
        });


        //Gets a click for the Mid Back button
        final ImageButton midBack = backBodyPartDialog.findViewById(R.id.mid_back);
        midBack.setEnabled(true);
        if(midBackSelected){
            midBack.setImageResource(R.drawable.mid_back_selected);
        }
        midBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(midBackSelected){
                    midBack.setImageResource(R.drawable.mid_back);
                    midBackSelected = false;
                    bodyPartsSelected.remove("Mid Back");
                } else {
                    midBack.setImageResource(R.drawable.mid_back_selected);
                    midBackSelected = true;
                    bodyPartsSelected.add("Mid Back");
                }
            }
        });


        //Gets a click for the Back Left Calve button
        final ImageButton backLeftCalve = backBodyPartDialog.findViewById(R.id.back_left_calve);
        backLeftCalve.setEnabled(true);
        if(backLeftCalveSelected){
            backLeftCalve.setImageResource(R.drawable.back_left_calve_selected);
        }
        backLeftCalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backLeftCalveSelected){
                    backLeftCalve.setImageResource(R.drawable.back_left_calve);
                    backLeftCalveSelected = false;
                    bodyPartsSelected.remove("Back Left Calve");
                } else {
                    backLeftCalve.setImageResource(R.drawable.back_left_calve_selected);
                    backLeftCalveSelected = true;
                    bodyPartsSelected.add("Back Left Calve");
                }
            }
        });


        //Gets a click for the Back Right Calve button
        final ImageButton backRightCalve = backBodyPartDialog.findViewById(R.id.back_right_calve);
        backRightCalve.setEnabled(true);
        if(backRightCalveSelected){
            backRightCalve.setImageResource(R.drawable.back_right_calve_selected);
        }
        backRightCalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backRightCalveSelected){
                    backRightCalve.setImageResource(R.drawable.back_right_calve);
                    backRightCalveSelected = false;
                    bodyPartsSelected.remove("Back Right Calve");
                } else {
                    backRightCalve.setImageResource(R.drawable.back_right_calve_selected);
                    backRightCalveSelected = true;
                    bodyPartsSelected.add("Back Right Calve");
                }
            }
        });


        //Gets a click for the Back Right Hand button
        final ImageButton backRightHand = backBodyPartDialog.findViewById(R.id.back_right_hand);
        backRightHand.setEnabled(true);
        if(backRightHandSelected){
            backRightHand.setImageResource(R.drawable.back_right_hand_selected);
        }
        backRightHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backRightHandSelected){
                    backRightHand.setImageResource(R.drawable.back_right_hand);
                    backRightHandSelected = false;
                    bodyPartsSelected.remove("Back Right Hand");
                } else {
                    backRightHand.setImageResource(R.drawable.back_right_hand_selected);
                    backRightHandSelected = true;
                    bodyPartsSelected.add("Back Right Hand");
                }
            }
        });


        //Gets a click for the Back Right Knee button
        final ImageButton backRightKnee = backBodyPartDialog.findViewById(R.id.back_right_knee);
        backRightKnee.setEnabled(true);
        if(backRightKneeSelected){
            backRightKnee.setImageResource(R.drawable.back_right_knee_selected);
        }
        backRightKnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backRightKneeSelected){
                    backRightKnee.setImageResource(R.drawable.back_right_knee);
                    backRightKneeSelected = false;
                    bodyPartsSelected.remove("Back Right Knee");
                } else {
                    backRightKnee.setImageResource(R.drawable.back_right_knee_selected);
                    backRightKneeSelected = true;
                    bodyPartsSelected.add("Back Right Knee");
                }
            }
        });


        //Gets a click for the Back Left Thigh button
        final ImageButton backLeftThigh = backBodyPartDialog.findViewById(R.id.back_left_thigh);
        backLeftThigh.setEnabled(true);
        if(backLeftThighSelected){
            backLeftThigh.setImageResource(R.drawable.back_left_thigh_selected);
        }
        backLeftThigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(backLeftThighSelected){
                    backLeftThigh.setImageResource(R.drawable.back_left_thigh);
                    backLeftThighSelected = false;
                    bodyPartsSelected.remove("Back Left Thigh");
                } else {
                    backLeftThigh.setImageResource(R.drawable.back_left_thigh_selected);
                    backLeftThighSelected = true;
                    bodyPartsSelected.add("Back Left Thigh");
                }
            }
        });

        backBodyPartDialog.show();
    }

    private void faceDialog() {
        final Dialog faceDialog = new Dialog(AddRecordActivity.this);
        faceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        faceDialog.setContentView(R.layout.face);
        faceDialog.setTitle("Select Face Parts");

        final Button close = faceDialog.findViewById(R.id.faceSave);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontBodyPartDialog();
                faceDialog.dismiss();
            }
        });

        //Gets a click for the Right Ear button
        final ImageButton rightEar = faceDialog.findViewById(R.id.right_ear);
        rightEar.setEnabled(true);
        if(rightEarSelected){
            rightEar.setImageResource(R.drawable.right_ear_selected);
        }
        rightEar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(rightEarSelected){
                    rightEar.setImageResource(R.drawable.right_ear);
                    rightEarSelected = false;
                    bodyPartsSelected.remove("Right Ear");
                } else {
                    rightEar.setImageResource(R.drawable.right_ear_selected);
                    rightEarSelected = true;
                    bodyPartsSelected.add("Right Ear");
                }
            }
        });


//Gets a click for the Mouth button
        final ImageButton mouth = faceDialog.findViewById(R.id.mouth);
        mouth.setEnabled(true);
        if(mouthSelected){
            mouth.setImageResource(R.drawable.mouth_selected);
        }
        mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(mouthSelected){
                    mouth.setImageResource(R.drawable.mouth);
                    mouthSelected = false;
                    bodyPartsSelected.remove("Mouth");
                } else {
                    mouth.setImageResource(R.drawable.mouth_selected);
                    mouthSelected = true;
                    bodyPartsSelected.add("Mouth");
                }
            }
        });


//Gets a click for the Nose button
        final ImageButton nose = faceDialog.findViewById(R.id.nose);
        nose.setEnabled(true);
        if(noseSelected){
            nose.setImageResource(R.drawable.nose_selected);
        }
        nose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(noseSelected){
                    nose.setImageResource(R.drawable.nose);
                    noseSelected = false;
                    bodyPartsSelected.remove("Nose");
                } else {
                    nose.setImageResource(R.drawable.nose_selected);
                    noseSelected = true;
                    bodyPartsSelected.add("Nose");
                }
            }
        });


//Gets a click for the Right Cheek button
        final ImageButton rightCheek = faceDialog.findViewById(R.id.right_cheek);
        rightCheek.setEnabled(true);
        if(rightCheekSelected){
            rightCheek.setImageResource(R.drawable.right_cheek_selected);
        }
        rightCheek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(rightCheekSelected){
                    rightCheek.setImageResource(R.drawable.right_cheek);
                    rightCheekSelected = false;
                    bodyPartsSelected.remove("Right Cheek");
                } else {
                    rightCheek.setImageResource(R.drawable.right_cheek_selected);
                    rightCheekSelected = true;
                    bodyPartsSelected.add("Right Cheek");
                }
            }
        });


//Gets a click for the Neck button
        final ImageButton neck = faceDialog.findViewById(R.id.neck);
        neck.setEnabled(true);
        if(neckSelected){
            neck.setImageResource(R.drawable.neck_selected);
        }
        neck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(neckSelected){
                    neck.setImageResource(R.drawable.neck);
                    neckSelected = false;
                    bodyPartsSelected.remove("Neck");
                } else {
                    neck.setImageResource(R.drawable.neck_selected);
                    neckSelected = true;
                    bodyPartsSelected.add("Neck");
                }
            }
        });


//Gets a click for the Chin button
        final ImageButton chin = faceDialog.findViewById(R.id.chin);
        chin.setEnabled(true);
        if(chinSelected){
            chin.setImageResource(R.drawable.chin_selected);
        }
        chin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(chinSelected){
                    chin.setImageResource(R.drawable.chin);
                    chinSelected = false;
                    bodyPartsSelected.remove("Chin");
                } else {
                    chin.setImageResource(R.drawable.chin_selected);
                    chinSelected = true;
                    bodyPartsSelected.add("Chin");
                }
            }
        });


//Gets a click for the Eyes button
        final ImageButton eyes = faceDialog.findViewById(R.id.eyes);
        eyes.setEnabled(true);
        if(eyesSelected){
            eyes.setImageResource(R.drawable.eyes_selected);
        }
        eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(eyesSelected){
                    eyes.setImageResource(R.drawable.eyes);
                    eyesSelected = false;
                    bodyPartsSelected.remove("Eyes");
                } else {
                    eyes.setImageResource(R.drawable.eyes_selected);
                    eyesSelected = true;
                    bodyPartsSelected.add("Eyes");
                }
            }
        });


//Gets a click for the Forehead button
        final ImageButton forehead = faceDialog.findViewById(R.id.forehead);
        forehead.setEnabled(true);
        if(foreheadSelected){
            forehead.setImageResource(R.drawable.forehead_selected);
        }
        forehead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(foreheadSelected){
                    forehead.setImageResource(R.drawable.forehead);
                    foreheadSelected = false;
                    bodyPartsSelected.remove("Forehead");
                } else {
                    forehead.setImageResource(R.drawable.forehead_selected);
                    foreheadSelected = true;
                    bodyPartsSelected.add("Forehead");
                }
            }
        });


//Gets a click for the Left Cheek button
        final ImageButton leftCheek = faceDialog.findViewById(R.id.left_cheek);
        leftCheek.setEnabled(true);
        if(leftCheekSelected){
            leftCheek.setImageResource(R.drawable.left_cheek_selected);
        }
        leftCheek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(leftCheekSelected){
                    leftCheek.setImageResource(R.drawable.left_cheek);
                    leftCheekSelected = false;
                    bodyPartsSelected.remove("Left Cheek");
                } else {
                    leftCheek.setImageResource(R.drawable.left_cheek_selected);
                    leftCheekSelected = true;
                    bodyPartsSelected.add("Left Cheek");
                }
            }
        });


//Gets a click for the Left Ear button
        final ImageButton leftEar = faceDialog.findViewById(R.id.left_ear);
        leftEar.setEnabled(true);
        if(leftEarSelected){
            leftEar.setImageResource(R.drawable.left_ear_selected);
        }
        leftEar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(leftEarSelected){
                    leftEar.setImageResource(R.drawable.left_ear);
                    leftEarSelected = false;
                    bodyPartsSelected.remove("Left Ear");
                } else {
                    leftEar.setImageResource(R.drawable.left_ear_selected);
                    leftEarSelected = true;
                    bodyPartsSelected.add("Left Ear");
                }
            }
        });

        faceDialog.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_record_menu, menu);
        return true;
    }

    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(AddRecordActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(AddRecordActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(AddRecordActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }

    public void viewAddProblem(MenuItem menu) {
        Intent intent = new Intent(AddRecordActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        Intent intent = new Intent(AddRecordActivity.this, ViewQRCode.class);
        startActivity(intent);
    }

    public void viewLogout(MenuItem menu) {
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(AddRecordActivity.this, MainActivity.class);
        startActivity(intent);
    }

}




