package ca.ualberta.symptomaticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SlideShowModeActivity extends AppCompatActivity implements View.OnClickListener {
    protected ArrayList<Photo> displayList;
    protected Record record;
    protected Button previous;
    protected Button next;
    protected ImageView photoImage;
    protected TextView noPhotos;
    protected boolean empty;
    protected int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show_mode);
        record = (Record) getIntent().getSerializableExtra("record");

//        displayList = record.getPhotoList();
        empty = isEmpty();
        position = 0;

        previous = findViewById(R.id.previousBttn);
        next = findViewById(R.id.nextBttn);
        photoImage = findViewById(R.id.slideShow);
        noPhotos = findViewById(R.id.noImages);

        noPhotos.setText("");

    }

    private boolean isEmpty() {
        if (displayList == null) {
            noPhotos.setText("No Photos");
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if ((viewId == R.id.previousBttn) && !empty)  {
            position--;
            if ((position >= 0) && (position < displayList.size())) {
                Photo p = displayList.get(position);
                photoImage.setImageBitmap(p.getPhotoBitmap());
            } else {
                position++;
            }

        }

        if ((viewId == R.id.nextBttn) && !empty)  {
            position++;
            if ((position >= 0) && (position < displayList.size())) {
                Photo p = displayList.get(position);
                photoImage.setImageBitmap(p.getPhotoBitmap());
            } else {
                position--;
            }

        }

    }
}
