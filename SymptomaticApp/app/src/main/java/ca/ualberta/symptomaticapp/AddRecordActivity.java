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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
    static final int REQUEST_TAKE_PHOTO = 1;
    private String ivId;
    private int photoCounter;
    private ListView photoListView;
    PhotoListViewAdapter photoListViewAdapter;
    ArrayList<Photo> photos;


    Bitmap bmp;

    PhotoList photoList = new PhotoList();

    String mCurrentPhotoPath; // the photo's file path

    Problem problem;
    int year;
    int month;
    int day;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    boolean selectedDateDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = findViewById(R.id.addRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Record");
        final Calendar cal = Calendar.getInstance();
//        ListView photoListView = findViewById(R.id.photoListView);


        problem = (Problem) getIntent().getSerializableExtra("problem");

        photoListView = findViewById(R.id.photoListView);
        photos = new ArrayList<Photo>();

        TextView textView = findViewById(R.id.InputProblemTextView);
        textView.setText(problem.getTitle());

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        selectedDateDone = false;


        Button dateButton = findViewById(R.id.changeDateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentYear = cal.get(Calendar.YEAR);
                int currentMonth = cal.get(Calendar.MONTH);
                int currentDay = cal.get(Calendar.DAY_OF_MONTH);

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

       /*Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecordActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });*/



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

                // Create the name of the file for the photo
                try {
                    File photoDir = createImageFile();
                    // for testing: Print the photo's path
                    Log.d("Photo Directory:", mCurrentPhotoPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Start the camera for the photo to be taken
                startActivityForResult(takePictureintent, REQUEST_IMAGE_CAPTURE);

            }
        });


        Button saveRecordBttn = findViewById(R.id.saveRecordBttn);

        saveRecordBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean goodRecord = true;
                // If the record's date is not given
                // TODO: selectedDateDone is always true for some reason...
                if (selectedDateDone) {
                    AlertDialog.Builder noDescriptionDialog = new AlertDialog.Builder(AddRecordActivity.this);
                    noDescriptionDialog.setMessage("A date must be inputted");
                    noDescriptionDialog.show();
                    goodRecord = false;
                }
                // When the required information to create a record is filled out
                if (goodRecord) {
                    // Prepare the attributes required to instantiate the Record class
                    RecordList currProblemRecordList = problem.getRecordList();
                    String currProbName = problem.getTitle();
                    Date currDate = Calendar.getInstance().getTime();

                    // Create the new record
                    Record currRecord = new Record(currProbName, currDate);
                    Record.addRecToDb(currRecord);

                    // Add it to the problem's recordList
                    // currProblemRecordList.addRecord(currRecord);
                    //Log.d("Problem's Record List", problem.getRecordList().toString());

                    // Switch back to the previous activity
                    Intent intent = new Intent(AddRecordActivity.this, ListProblemsActivity.class);
                    startActivity(intent);
                }

            }
        });

        initListView();

    }

    public void initListView(){
        if(photoListViewAdapter == null){
            photoListViewAdapter = new PhotoListViewAdapter(photos, this);
        }

        photoListView.setAdapter(photoListViewAdapter);

    }

   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_record_menu, menu);
        return true;
    }

    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(AddRecordActivity.this, AddProblemActivity.class);
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

    public void viewLogout(MenuItem menu) {
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(AddRecordActivity.this, MainActivity.class);
        startActivity(intent);
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

                    // Add the photo selected from gallery onto the photoList
                    photoList.addPhoto(photo);
                    photos.add(photo);
                    photoListViewAdapter.notifyDataSetChanged();

//                    If we want to use the bitmap from the Photo class
                    Bitmap image = photo.getPhotoBitmap();
                    incrementPhotoCounter();
                }
                break;

            case REQUEST_IMAGE_CAPTURE:
                // If capturing a photo using the camera
                if (resultCode == RESULT_OK) {
                    // Do try and catch
                    try {
                        // Convert the photo captured into a bitmap
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                        // Display the bitmap in the ImageView
//                        iv.setImageBitmap(bitmap);

                        // Store the image as a Photo object
                        Photo photo = new Photo(bitmap);

                        photos.add(photo);
                        photoListViewAdapter.notifyDataSetChanged();


                        // Add the new Photo object into the photoList
                        photoList.addPhoto(photo);
                        incrementPhotoCounter();
                    } catch (Exception e) {
                        Log.d("ERROR", "Captured photo could not be created.");
                    }


                }
                break;
        }


    }

    // Reference: https://developer.android.com/training/camera/photobasics#java
    private File createImageFile() throws IOException {
        // Create the image file name with its timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String photoFileName = "JPEG_" + timeStamp + "_";

        // Get the app's directory for photos
        File picturesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Create the file
        File photo = File.createTempFile(
                photoFileName,  /* prefix */
                ".jpg",         /* suffix */
                picturesDir      /* directory */
        );

        // Store the photo's path name and return the image File
        mCurrentPhotoPath = photo.getAbsolutePath();
        return photo;
    }

    private void incrementPhotoCounter() {
        photoCounter += 1;
//        ivId = "photo" + photoCounter;
//        iv = (ImageView) findViewById(R.id.ivId);
//        Collection<Photo> photos = photoList.getPhotos();
//        for (Photo currPhoto : photos) {
//            photoListView.
//            //Use variable
////            Log.d("Added another photo!", "lol");
//            Log.d("Timestamp for Photo", currPhoto.getTimestamp());
//
//        }


    }
}




