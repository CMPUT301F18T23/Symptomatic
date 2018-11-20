package ca.ualberta.symptomaticapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class PhotoListTest extends TestCase {
    public Context context;

    public void testEmptyPhotoList(){
        // Test to detect when PhotoList is initialized, that it has an empty photoList.
        PhotoList photoList = new PhotoList();
        Collection<Photo> photos = photoList.getPhotos();
        assertTrue("Empty Photo List", photos.size() == 0);
    }

    public void testAddPhoto(){
        // Use a dummy image to add to the photoList
        Bitmap testBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon);
        Photo testPhoto = new Photo(testBitmap);
        PhotoList photoList = new PhotoList();
        photoList.addPhoto(testPhoto);
        Collection<Photo> photos = photoList.getPhotos();
        assertTrue("Photo List Size", photos.size() == 1);
        assertTrue("Photo Not Contained", photos.contains(testPhoto));
    }

    public void testDeletePhoto(){
        // Use a dummy image to delete from photoList
        Bitmap testBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon);
        Photo testPhoto = new Photo(testBitmap);
        PhotoList photoList = new PhotoList();
        photoList.addPhoto(testPhoto); // Add the photo
        Collection<Photo> photos = photoList.getPhotos();
        assertTrue("Photo List Size", photos.size() == 1);
        assertTrue("Photo Not Contained", photos.contains(testPhoto));

        photoList.deletePhoto(testPhoto); // Then delete it
        Collection<Photo> photos2 = photoList.getPhotos();
        assertTrue("Photo List Size", photos.size() == 1);
        assertTrue("Photo Not Contained", photos.contains(testPhoto));
    }

    public void testGetPhoto(){
        // Use a dummy image to add to the photoList
        Bitmap testBitmap1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon);
        Photo testPhoto1 = new Photo(testBitmap1);
        PhotoList photoList = new PhotoList();
        photoList.addPhoto(testPhoto1);


        Bitmap testBitmap2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon);
        Photo testPhoto2 = new Photo(testBitmap2);
        photoList.addPhoto(testPhoto2);
        Collection<Photo> photos = photoList.getPhotos();

        assertTrue("Problem1 does not match", photos.iterator().next() == testPhoto1);

    }

    public void testHasPhoto(){
        Bitmap testBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon);
        Photo testPhoto = new Photo(testBitmap);
        PhotoList photoList = new PhotoList();
        photoList.addPhoto(testPhoto);

        Bitmap testBitmap2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon);
        Photo testPhoto2 = new Photo(testBitmap);
//        PhotoList photoList2 = new PhotoList();
        photoList.addPhoto(testPhoto2);

        Collection<Photo> photos = photoList.getPhotos();
        assertTrue("HasProblem List Size not big enough", photos.size() == 2);
        assertTrue("HasProblem2 Not Contained", photos.contains(testPhoto));
        assertTrue("HasProblem1 Not Contained", photos.contains(testPhoto2));

    }

}
