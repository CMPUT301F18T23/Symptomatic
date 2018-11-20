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
        // Initialize the Photo object
        this.photoBitmap = photoBitmap;
        this.timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        this.quality = 60;

        // Method 1: Convert the bitmap into a byte array
        int size = photoBitmap.getRowBytes() * photoBitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        photoBitmap.copyPixelsToBuffer(byteBuffer);
        this.photoByteArray = byteBuffer.array();

        // Method 2: Another way to convert a bitmap into a byte array but very slow.
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        this.photoByteArray = stream.toByteArray();

        // Check bitmap size and compress if necessary
        this.checkBitmapSize();
    }

    private void checkBitmapSize() {
        // for testing: Log.d("Checked Bitmap Size", "hello" );
        if (photoByteArray.length > 65536){
            compressPhoto();
        }
        // If bitmap doesn't exceed the maximum size, then set the photoSize
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

        if(photoBitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream)) {
            this.photoByteArray = stream.toByteArray();
            // for testing: Log.d("COMPRESSION COUNTER:", "harry potter");
            this.photoBitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);
            // for testing: Log.d("Photo Size:", photoSize.toString());
            // for testing: this.quality = this.quality - 10; // // decremnt quality if we want, assuming it doesnt go to 0
            this.checkBitmapSize();

        }
        else{
            // Print our error message saying the compression wasn't successful
            Log.d("COMPRESSION ERROR", "Bitmap cannot be compressed.");
        }


    }

    public byte[] getPhotoByteArray (){
        // Return the byte array representation of the photo
        return this.photoByteArray;
    }

    public Bitmap getPhotoBitmap(){
        // Return the bitmap representation of the photo
        return this.photoBitmap;
    }

    public void setPhotoSize(int photoSize) {
        // Return the size of the photo
        this.photoSize = photoSize;
    }


}
