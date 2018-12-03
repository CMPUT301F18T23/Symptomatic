package ca.ualberta.symptomaticapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;

import junit.framework.TestCase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class PhotoListTest extends TestCase {
    public Context context;

    public void testEmptyPhotoList(){
        // Test to detect when PhotoList is initialized, that it has an empty photoList.
        PhotoList photoList = new PhotoList();
        ArrayList<Photo> photos = photoList.getPhotos();
        assertTrue("Empty Photo List", photos.size() == 0);
    }

    public void testAddPhoto(){
        // Use a dummy image to add to the photoList
        String pseudoPhoto = "this is a placeholder for photo since photos are represented as strings";
        Photo testPhoto = new Photo(pseudoPhoto);
        PhotoList photoList = new PhotoList();

        // Add the dummy Photo object the photoList
        photoList.addPhoto(testPhoto);
        ArrayList<Photo> photos = photoList.getPhotos();

        // Test if the dummy Photo object has been added and is within the photoList
        assertTrue("Photo List Size", photos.size() == 1);
        assertTrue("Photo Not Contained", photos.contains(testPhoto));
    }

    public void testDeletePhoto(){
        // Use a dummy photo to delete from photoList
        String pseudoPhoto = "this is a placeholder for photo since photos are represented as strings";
        Photo testPhoto = new Photo(pseudoPhoto);
        PhotoList photoList = new PhotoList();

        // Add the photo
        photoList.addPhoto(testPhoto);
        ArrayList<Photo> photos = photoList.getPhotos();
        assertTrue("Photo List Size", photos.size() == 1);
        assertTrue("Photo Not Contained", photos.contains(testPhoto));

        // Then delete it
        photoList.deletePhoto(testPhoto);
        ArrayList<Photo> photos2 = photoList.getPhotos();
        assertTrue("Photo List Size", photos.size() == 0);
        assertTrue("Photo Not Contained", photos.contains(testPhoto));
    }

    public void testGetPhoto(){
        // Use a dummy image to add to the photoList
        String pseudoPhoto = "this is a placeholder for photo since photos are represented as strings";
        Photo testPhoto = new Photo(pseudoPhoto);
        PhotoList photoList = new PhotoList();
        photoList.addPhoto(testPhoto);


        String pseudoPhoto2 = "this is a placeholder for photo since photos are represented as strings";
        Photo testPhoto2 = new Photo(pseudoPhoto2);
        photoList.addPhoto(testPhoto2); // Add another photo

        ArrayList<Photo> photos = photoList.getPhotos();
        assertTrue("Photo does not match", photos.iterator().next() == testPhoto);

    }

    public void testHasPhoto(){
        // Use 2 dummy photo to test whether it can be detected in the photoList
        String pseudoPhoto = "this is a placeholder for photo since photos are represented as strings";
        Photo testPhoto = new Photo(pseudoPhoto);
        PhotoList photoList = new PhotoList();
        photoList.addPhoto(testPhoto);

        String pseudoPhoto2 = "this is a placeholder for photo since photos are represented as strings";
        Photo testPhoto2 = new Photo(pseudoPhoto2);
        photoList.addPhoto(testPhoto2); // Add another photo

        ArrayList<Photo> photos = photoList.getPhotos(); // Get the photoList to run the tests/asserts
        assertTrue("hasPhoto List Size not big enough", photos.size() == 2);
        assertTrue("hasPhoto Not Contained", photos.contains(testPhoto));
        assertTrue("hasPhoto Not Contained", photos.contains(testPhoto2));

    }

}
