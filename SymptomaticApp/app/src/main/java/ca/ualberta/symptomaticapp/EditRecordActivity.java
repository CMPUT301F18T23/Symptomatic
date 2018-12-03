package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class EditRecordActivity extends AppCompatActivity {

    Record record;

    // time stamp variables
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private TimePickerDialog.OnTimeSetListener TimeSetListener;
    Calendar cal;
    int year;
    int month;
    int day;
    int hour;
    int min;

    private static final int PICK_IMAGE_REQUEST = 100; // to access the gallery to choose an image
    static final int REQUEST_IMAGE_CAPTURE = 1; // to access the camera to take an image
    ListView photoListView;
    PhotoListViewAdapter photoListViewAdapter;
    ArrayList<Photo> displayPhotos;
    Bitmap bmp;


    // geolocation variables
    private LatLng newGeolocation;
    private Double lat, lng;
    private String newGeolocationString;
    private boolean geoChanged;

    static final int GET_GEOLOCATION = 2;

    Button viewFrontBodyPart,viewBackBodyPart,saveButton,deleteButton;

    bodyPartDialog thisDialog;

    EditText titleEditText,commentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
        Toolbar toolbar = findViewById(R.id.editRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Record");

        record = (Record) getIntent().getSerializableExtra("record");

        // get geolocation
        newGeolocationString = record.geolocation;

        cal = Calendar.getInstance();
        cal.setTime(record.recordDate);

        // Input the title
        titleEditText = findViewById(R.id.editTitleEditText);
        titleEditText.setText(record.recordTitle);

        // Input the time stamp
        TextView timeTextView = findViewById(R.id.currentTimeTextView);
        timeTextView.setText(record.recordDate.toString());

        // Input the comment
        commentEditText = findViewById(R.id.commentEditText);
        commentEditText.setText(record.recordComment);

        // Change time stamp button
        Button timeButton = findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog editTimeDialog = new TimePickerDialog(EditRecordActivity.this, android.R.style.Theme_DeviceDefault_Dialog, TimeSetListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(EditRecordActivity.this));
                editTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                editTimeDialog.show();

                DatePickerDialog dialog = new DatePickerDialog(EditRecordActivity.this, android.R.style.Theme_DeviceDefault_Dialog, DateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();


            }
        });

        // view body location related
        viewBackBodyPart = findViewById(R.id.viewBackBodyPart);
        viewFrontBodyPart = findViewById(R.id.viewFrontBodyPart);

        thisDialog = new bodyPartDialog(EditRecordActivity.this,true);
        thisDialog.readList(record.getBodyLocation());

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

        TimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;

            }
        };

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int chosenYear, int chosenMonth, int chosenDay) {
                year = chosenYear;
                month = chosenMonth;
                day = chosenDay;

            }
        };

        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record.geolocation == null) {
                    Intent intent = new Intent(EditRecordActivity.this, MapsActivity.class);
                    //startActivity(intent);
                    startActivityForResult(intent, GET_GEOLOCATION);

                } else {
                    String[] latlng = record.geolocation.split(",");
                    double latitude = Double.parseDouble(latlng[0]);
                    double longitude = Double.parseDouble(latlng[1]);

                    LatLng location = new LatLng(latitude, longitude);
                    Intent intent = new Intent(EditRecordActivity.this, EditRecordMap.class);
                    intent.putExtra("geolocation", location);
                    intent.putExtra("title", record.recordTitle);
                    startActivityForResult(intent, GET_GEOLOCATION);

                }


            }
        });

        photoListView = findViewById(R.id.EditRPhotoListView);
        displayPhotos = record.getPhotoList();
        initPhotoListView();
        setListViewHeightBasedOnChildren(photoListView);

        Button savedPhoto = findViewById(R.id.editSavedPhoto);
        Button takePhoto = findViewById(R.id.editTakePhoto);

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
                Intent takePictureintent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureintent, REQUEST_IMAGE_CAPTURE);
            }
        });

        saveButton = findViewById(R.id.saveRecordBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecord();
            }
        });

        deleteButton = findViewById(R.id.deleteRecordBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecord();
            }
        });

    }

    private void initPhotoListView() {
        if(photoListViewAdapter == null){
            photoListViewAdapter = new PhotoListViewAdapter(displayPhotos, this);
        }

        photoListView.setAdapter(photoListViewAdapter);
    }

    // Confirm with user that they want to exit without saving changes
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit without saving?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditRecordActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            // if changing the geolocation
            case GET_GEOLOCATION:
                if (resultCode == RESULT_OK){
                    newGeolocation = data.getExtras().getParcelable("geolocation");
                    lat = newGeolocation.latitude;
                    lng = newGeolocation.longitude;
                    newGeolocationString = Double.toString(lat) + ',' + Double.toString(lng);
                    geoChanged = true;
                    Toast.makeText(this, newGeolocation.toString(), Toast.LENGTH_SHORT).show();
                }
                break;

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
                        // Convert the photo captured into a bitmap
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");


                        String image = formatPhoto(bitmap);
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
                    }
                }
                break;

        }


    }

    public String formatPhoto(Bitmap bmp) {
        String image = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] photoByteArray = stream.toByteArray();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(EditRecordActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(EditRecordActivity.this, EditAccountActivity.class);
        startActivity(intent);
    }

    public void viewAddProblem(MenuItem menu){
        Intent intent = new Intent(EditRecordActivity.this, AddProblemActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(EditRecordActivity.this, ListProblemsActivity.class);
        startActivity(intent);
    }
    public void viewViewQR(MenuItem menu) {
        Intent intent = new Intent(EditRecordActivity.this, ViewQRCode.class);
        startActivity(intent);
    }
    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(EditRecordActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void saveRecord(){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final PhotoList photoList = new PhotoList();
        photoList.setPhotos(displayPhotos);

        CollectionReference records = db.collection("records");

        Query recordsQuery = records.whereEqualTo("problem", record.getProblem()).whereEqualTo("user", Login.thisUser.username);

        recordsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //update problem to new information
                        String recordDocId = document.getId();
                        DocumentReference thisDocument = db.collection("records").document(recordDocId);
                        thisDocument.update("recordTitle",titleEditText.getText().toString(),"recordComment",commentEditText.getText().toString(),"bodyLocation",thisDialog.returnPartsSelected(),"geolocation",newGeolocationString,"photoList",photoList);
                    }
                }
                finish();
            }
        });
    }

    public void deleteRecord(){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference records = db.collection("records");

        Query recordsQuery = records.whereEqualTo("problem",record.getProblem()).whereEqualTo("user",Login.thisUser.username);

        recordsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String problemDocID = document.getId();
                        DocumentReference thisDocument = db.collection("records").document(problemDocID);
                        thisDocument.delete();
                    }
                }
                finish();
            }
        });
    }


}
