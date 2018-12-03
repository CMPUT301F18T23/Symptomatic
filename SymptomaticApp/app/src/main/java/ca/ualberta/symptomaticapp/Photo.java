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
 * Stores the string representation of a photo along with its timestamp
 *
 * Issues:
 *
 */


package ca.ualberta.symptomaticapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * contains a photo either from gallery or from camera when
 * adding record
 */

public class Photo implements Serializable {
    protected String timestamp;
    protected String stringPhoto;

    /**
     * Constructor for photo
     * @param photo
     */
    public Photo(String photo){
        // Initialize the Photo object
        this.timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        this.stringPhoto = photo;
    }

    /**
     * empty constructor for casting from firebase
     */
    public Photo(){}

    /**
     * gets the string TimeStamp
     * @return the timestamp the photo was added as a date
     */
    public String getTimestamp() {
        // Return the timestamp the photo was added as a Date
        return this.timestamp;
    }

    /**
     * gets the string representation of the photo
     * @return string representation of photo
     */
    public String getPhotoString() {
        return this.stringPhoto;
    }


}
