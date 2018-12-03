/*
 * AddRecordActivity.java
 *
 * Version 2
 *
 * December, 3, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Adds a user input record to database.
 *
 * Issues:
 *
 */
package ca.ualberta.symptomaticapp;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddRecordActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 100; // to access the gallery to choose an image
    static final int REQUEST_IMAGE_CAPTURE = 1; // to access the camera to take an image
    static final int GET_GEOLOCATION = 2;
    private ListView photoListView;
    PhotoListViewAdapter photoListViewAdapter;
    ArrayList<Photo> displayPhotos;
    byte[] photoByteArray;
    Button addBackBodyPart,addFrontBodyPart;

    ArrayList<String> bodyPartsSelected;

    Bitmap bmp;
    Context context = this;

    String currProbName,comment,title;


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

    bodyPartDialog bodyPartSelector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = findViewById(R.id.addRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Record");
        final Calendar cal = Calendar.getInstance();

        bodyPartsSelected = new ArrayList<>();

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

        bodyPartSelector = new bodyPartDialog(AddRecordActivity.this,true);


        addFrontBodyPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyPartSelector.frontBodyPartDialog();
            }
        });

        addBackBodyPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyPartSelector.backBodyPartDialog();
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
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create a file to store the image
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        Log.d("Take Photo Error", "Camera could not capture image.");
                        e.printStackTrace();
                    }

                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(context, "ca.ualberta.symptomaticapp", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    }

                    // Start the camera for the photo to be taken
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }


            }
        });

        Button saveRecordBttn = findViewById(R.id.saveRecordBttn);

        saveRecordBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean goodRecord = true;

                // Obtaining the user's input fields
                currProbName = problem.getTitle();

                comment = commentEdit.getText().toString();
                title = titleEdit.getText().toString();

                if (title.length() == 0) {
                    // Check whether a title for the record was inputted
                    AlertDialog.Builder noTitleDialog = new AlertDialog.Builder(AddRecordActivity.this);
                    noTitleDialog.setMessage("Title for the record cannot be empty.");
                    noTitleDialog.show();
                    goodRecord = false;
                }


                if (goodRecord) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference records = db.collection("records");

                    Query recordsQuery = records.whereEqualTo("user",Login.thisUser.returnUsername()).whereEqualTo("problem",currProbName).whereEqualTo("recordTitle",title);

                    recordsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                if (task.getResult().size()>0){
                                    AlertDialog.Builder noDescriptionDialog = new AlertDialog.Builder(AddRecordActivity.this);
                                    noDescriptionDialog.setMessage("Please rename your record.\nYou already have a record with that name.");
                                    noDescriptionDialog.show();
                                } else {
                                    // Prepare the attributes required to instantiate the Record class
                                    if (timeChanged){
                                        final Calendar cal = Calendar.getInstance();
                                        cal.set(year, month, day, hour, min);
                                        timeStamp = cal.getTime();
                                    } else{
                                        // If no specific date was inputted, just current time instance
                                        timeStamp = Calendar.getInstance().getTime();
                                    }

                                    // Create the new record
                                    Record currRecord = new Record(currProbName, timeStamp,Login.thisUser.returnUsername(),title);
                                    currRecord.addComment(comment);
                                    currRecord.addBodyLocation(bodyPartDialog.returnPartsSelected());

                                    currRecord.setPhotoList(displayPhotos);
                                    currRecord.addGeolocation(geolocationString);

                                    currRecord.addRecToDb();

                                    // Switch back to the previous activity
                                    Intent intent = new Intent(AddRecordActivity.this, ListRecordsActivity.class);
                                    intent.putExtra("problem", problem);
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                }

            }
        });



    }

    // Confirm with user that they want to exit without saving changes
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddRecordActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            // If selecting a photo from gallery
            case PICK_IMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        // Convert the photo selected from gallery into a bitmap and display it
                        bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Create an instance of the Photo class

                    String image = formatPhoto(bmp);
                    Photo photo = new Photo(image);



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
                        // Reference: https://stackoverflow.com/questions/42516126/fileprovider-illegalargumentexception-failed-to-find-configured-root
                        bmp = BitmapFactory.decodeFile(mCurrentPhotoPath);

                        // Store the image as a Photo object
                        String image = formatPhoto(bmp);
                        Photo photo = new Photo(image);


                        if (displayPhotos.size() < 10) {
                            displayPhotos.add(photo);
                            photoListViewAdapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(photoListView);
                        } else {
                            Toast.makeText(getApplicationContext(), "Max. 10 Photos Allowed", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Log.d("ERROR", "Captured photo could not be created.");
                        e.printStackTrace();
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

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("Photo Path", mCurrentPhotoPath);
        return image;
    }

    public String formatPhoto(Bitmap bmp) {
        String image = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        photoByteArray = stream.toByteArray();

        if (photoByteArray.length > 65536) {
            int size = photoByteArray.length/65536;

            if (size > 0) {
                int quality = 100 / size;
                stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, quality, stream);
                photoByteArray = stream.toByteArray();
                image = Base64.encodeToString(photoByteArray, Base64.DEFAULT);
                return image;
            }

        }

        image = Base64.encodeToString(photoByteArray, Base64.DEFAULT);
        return image;
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




