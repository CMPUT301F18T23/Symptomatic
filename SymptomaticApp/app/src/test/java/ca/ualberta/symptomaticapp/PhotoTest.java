package ca.ualberta.symptomaticapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoTest extends TestCase {

    public void testConstructor(){
        String pseudoPhoto = "this is a placeholder for photo since photos are represented as strings";
        Photo photo = new Photo(pseudoPhoto);
        assertTrue("Photo Constructor Failed Photo", photo.getPhotoString() == pseudoPhoto);
        assertTrue("Photo Constructor Failed Timestamp", photo.getTimestamp() != null);

    }

    public void testPhoto(){
        String pseudoPhoto = "this is a placeholder for photo since photos are represented as strings";
        Photo photo = new Photo(pseudoPhoto);
        assertTrue("Photo is not the same", photo.getPhotoString() == pseudoPhoto);
    }

    public void testTimestamp(){
        String pseudoPhoto = "this is a placeholder for photo since photos are represented as strings";
        Photo photo = new Photo(pseudoPhoto);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        assertTrue("Timestamp not the same", photo.getTimestamp() == timestamp);
    }


    
}
