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

        empty = isEmpty();
        position = 0;
//
        previous = findViewById(R.id.previousBttn);
        next = findViewById(R.id.nextBttn);
        photoImage = findViewById(R.id.slideShow);
        description = findViewById(R.id.noImages);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);

        if (!isEmpty()) {
            Photo p = displayList.get(position);
            String stringPhoto = p.getPhotoString();
            byte[] bytePhoto = Base64.decode(stringPhoto, Base64.DEFAULT);
            Bitmap photoBitmap = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);

            photoImage.setImageBitmap(photoBitmap);
            description.setText(p.getTimestamp());
        }
    }

    private boolean isEmpty() {
        if (displayList.size() == 0) {
            description.setText("No Photos");
            return true;
        }
        return false;
    }
//
//
    @Override
    public void onClick(View v) {
        int viewId = v.getId();

//        if (viewId == R.id.nextBttn) {
//            Log.d("clicked next", "clicked");
//             Photo photo = displayList.get(1);
//            String stringPhoto = photo.getPhotoString();
//            byte[] bytePhoto = Base64.decode(stringPhoto, Base64.DEFAULT);
//            Bitmap photoBitmap = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);
//
//            photoImage.setImageBitmap(photoBitmap);
//
//        }

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
//
    }
}
