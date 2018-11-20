/*
 * Photo.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Contains a photo.
 *
 * Issues:
 *      photo compression not 100% working
 *
 */

package ca.ualberta.symptomaticapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Photo {
    protected String photoPath;
    protected String timestamp;
    protected Integer photoSize;
    protected byte[] photoByteArray;
    protected Bitmap photoBitmap;
//    protected int quality;

    public Photo(Bitmap photoBitmap){
        this.photoBitmap = photoBitmap;
        this.timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        this.quality = 60;

//
        int size = photoBitmap.getRowBytes() * photoBitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        photoBitmap.copyPixelsToBuffer(byteBuffer);
        this.photoByteArray = byteBuffer.array();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        this.photoByteArray = stream.toByteArray();
        this.checkBitmapSize();


        // Check bitmap size and compress if necessary
    }

    private void checkBitmapSize() {
        Log.d("Checked Bitmap Size", "hello" );
        if (photoByteArray.length > 65536){
            compressPhoto();
        }

        this.setPhotoSize(photoByteArray.length);

    }



    public String getPhotoPath() {
        // Return the photo's file path as a String
        return this.photoPath;
    }

    public String getTimestamp() {
        // Return the timestamp the photo was added as a Date
        return this.timestamp;
    }

    public Integer getPhotoSize() {
        // Return the photo's size as an Integer
        return this.photoSize;
    }

    public void compressPhoto() {
        // If the photo's size exceeds 65536 bytes, compress the image.
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // decremnt quality if we want
        if(photoBitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream)) {
            this.photoByteArray = stream.toByteArray();
            Log.d("COMPRESSION COUNTER:", "harry potter");
            this.photoBitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);
//            Log.d("Photo Size:", photoSize.toString());
//            this.quality = this.quality - 10; // assuming it doesnt go to 0
            this.checkBitmapSize();

        }
        else{
            Log.d("COMPRESSION ERROR", "Bitmap was not converted to byte array.");
//            Log.d("Stream", stream.toString());

        }


    }

    public byte[] getPhotoByteArray (){
        return this.photoByteArray;
    }

    public Bitmap getPhotoBitmap(){
        return this.photoBitmap;
    }

    public void setPhotoSize(int photoSize) {
        this.photoSize = photoSize;
    }


}
