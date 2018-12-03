/*
 * SlideShowModeActivity.java
 *
 * Version 1
 *
 * November, 27, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * This activity displays the photos in a record along with
 * 2 buttons that get the next and previous photo
 *
 * Issues:
 *
 */


package ca.ualberta.symptomaticapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SlideShowModeActivity extends AppCompatActivity implements View.OnClickListener{
    protected ArrayList<Photo> displayList;
    protected Record record;
    protected Button previous;
    protected Button next;
    protected ImageView photoImage;
    protected TextView description;
    protected boolean empty;
    protected int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show_mode);
        record = (Record) getIntent().getSerializableExtra("record");
        displayList = record.getPhotoList();

        // checks if photoList is empty and sets the position to 0
        empty = isEmpty();
        position = 0;

        // initializes the buttons
        previous = findViewById(R.id.previousBttn);
        next = findViewById(R.id.nextBttn);
        photoImage = findViewById(R.id.slideShow);
        description = findViewById(R.id.noImages);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);

        // if the photoList isn't empty, display the first photo and its timestamp
        if (!isEmpty()) {
            Photo p = displayList.get(position);
            String stringPhoto = p.getPhotoString();
            byte[] bytePhoto = Base64.decode(stringPhoto, Base64.DEFAULT);
            Bitmap photoBitmap = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);

            photoImage.setImageBitmap(photoBitmap);
            description.setText(p.getTimestamp());
        }
    }

    // tests if the photoList is empty and returns true if empty and false if not
    private boolean isEmpty() {
        if (displayList.size() == 0) {
            String text = "NO PHOTOS";
            description.setText(text);
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        // get previous photo unless there is no previous photo
        if ((viewId == R.id.previousBttn) && !empty)  {
            position--;
            if ((position >= 0) && (position < displayList.size())) {
                Photo p = displayList.get(position);
                String stringPhoto = p.getPhotoString();
                byte[] bytePhoto = Base64.decode(stringPhoto, Base64.DEFAULT);
                Bitmap photoBitmap = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);

                photoImage.setImageBitmap(photoBitmap);
                description.setText(p.getTimestamp());
            } else {
                position++;
            }

        }

        //get next photo unless there is no next photo
        if ((viewId == R.id.nextBttn) && !empty)  {
            position++;
            if ((position >= 0) && (position < displayList.size())) {
                Photo p = displayList.get(position);
                String stringPhoto = p.getPhotoString();
                byte[] bytePhoto = Base64.decode(stringPhoto, Base64.DEFAULT);
                Bitmap photoBitmap = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);

                photoImage.setImageBitmap(photoBitmap);
                description.setText(p.getTimestamp());

            } else {
                position--;
            }

        }
    }
}
