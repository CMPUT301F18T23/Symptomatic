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


/**
 * contains a photo either from gallery or from camera when
 * adding record
 */

public class Photo {
    protected String photoPath;
    protected String timestamp;
    protected Integer photoSize;
    protected byte[] photoByteArray;
    protected Bitmap photoBitmap;
    protected Bitmap reducedSizeBitmap;

    /**
     * Constructor for photo
     * @param photoBitmap
     */
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
        checkBitmapSize();
    }

    /**
     * Checks if the bitmap size of the photo is under required size
     */
    private void checkBitmapSize() {
        // for testing: Log.d("Checked Bitmap Size", "hello" );
        if (photoByteArray.length > 65536){
            int size = photoByteArray.length/65536;
            if (size > 0) {
                int quality = 100 / size;
                compressPhoto(quality);
            }
        }
        // If bitmap doesn't exceed the maximum size, then set the photoSize
        this.setPhotoSize(photoByteArray.length);

    }

    /**
     * gets the String photoPath
     * @return the String photoPath
     */
    public String getPhotoPath() {
        // Return the photo's file path as a String
        return this.photoPath;
    }

    /**
     * gets the string TimeStamp
     * @return string TimeStamp
     */
    public String getTimestamp() {
        // Return the timestamp the photo was added as a Date
        return this.timestamp;
    }

    /**
     * gets integer photo size
     * @return integer photoSize
     */
    public Integer getPhotoSize() {
        // Return the photo's size as an Integer
        return this.photoSize;
    }

    /**
     * If initial photo size is larger than specified, compress the photo
     * and do so continually until the photo is under the specified size
     */
    public void compressPhoto(int quality) {
        // If the photo's size exceeds 65536 bytes, compress the image.
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        if(photoBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)) {
            this.photoByteArray = stream.toByteArray();
            // for testing: Log.d("COMPRESSION COUNTER:", "harry potter");
            this.reducedSizeBitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);

        }
        else{
            // Print our error message saying the compression wasn't successful
            Log.d("COMPRESSION ERROR", "Bitmap cannot be compressed.");
        }


    }

    /**
     * gets the byte array of the photo
     * @return byte array of the photo
     */
    public byte[] getPhotoByteArray (){
        // Return the byte array representation of the photo
        return this.photoByteArray;
    }

    /**
     * gets the bitmap of the photo
     * @return bitmap of the photo
     */
    public Bitmap getPhotoBitmap(){
        // Return the bitmap representation of the photo
        return this.photoBitmap;
    }

    /**
     * sets the size of the photo
     * @param photoSize
     */
    public void setPhotoSize(int photoSize) {
        // Return the size of the photo
        this.photoSize = photoSize;
    }


}
