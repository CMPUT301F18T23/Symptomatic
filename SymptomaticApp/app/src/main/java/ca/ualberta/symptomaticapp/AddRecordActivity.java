package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
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
import java.util.Collection;
import java.util.Date;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 100; // to access the gallery to choose an image
    static final int REQUEST_IMAGE_CAPTURE = 1; // to access the camera to take an image
    static final int REQUEST_TAKE_PHOTO = 1;
    private ImageView iv;

    byte[] byteArray;

    PhotoList photolist;

    String mCurrentPhotoPath; // the photo's file path

    Problem problem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = findViewById(R.id.addRecord_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Record");

        problem = (Problem)getIntent().getSerializableExtra("problem");

        TextView textView = findViewById(R.id.InputProblemTextView);
        textView.setText(problem.getTitle());






        // ------------------------------ WORKING ON SAVING PHOTOS -------------------------------
        // CURRENT STATUS: Not working!
        // Set the file path
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File outputFile = new File(path,"Symptomatic");
        // If the directory doesn't exist, create it
        if (!outputFile.exists()) {
            outputFile.mkdir();
        }

        final Button savedPhoto = findViewById(R.id.savedPhoto);
        final Button takePhoto = findViewById(R.id.takePhoto);
        iv = (ImageView) findViewById(R.id.iv);

        savedPhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);


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



    // ---------------------------------- PHOTO FUNCTIONALITY ----------------------------------
    public void onClick(View v){
        int viewId = v.getId();
        // Gets images from gallery - Reference: https://en.proft.me/2017/07/1/how-get-image-gallery-or-camera-android/
        if (viewId == R.id.savedPhoto){

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }

        // If the user wants to take a photo using their camera
        // Reference: https://developer.android.com/training/camera/photobasics#java
        if (viewId == R.id.takePhoto){
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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
<<<<<<< HEAD
        //convertBitmapToByteArray(data);
=======
>>>>>>> 1bfda5aa1c688ba2ea2e2f02aa80772148780a2f
        switch(requestCode){
            case PICK_IMAGE_REQUEST:

                if(resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    Photo photo = new Photo(byteArray, timeStamp);

                    photolist = new PhotoList();

                    photolist.addPhoto(photo);



                    Bitmap image = BitmapFactory.decodeByteArray(photo.getPhotoByteArray(), 0, photo.getPhotoByteArray().length);
                    iv.setImageBitmap(image);









                }

            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {

                }

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

    // Reference: https://developer.android.com/training/camera/photobasics#java
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println("Error in creating the file for the image");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}
