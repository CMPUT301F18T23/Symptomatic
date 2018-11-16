package ca.ualberta.symptomaticapp;

import java.util.Date;

public class Photo {
    protected String photoPath;
    protected Date timestamp;
    protected Integer photoSize;

    public Photo(String photoPath, Date timestamp, Integer photoSize){
        // Initialize the Photo object
        this.photoPath = photoPath;
        this.timestamp = timestamp;
        this.photoSize = photoSize;
    }

    public String getPhotoPath() {
        // Return the photo's file path as a String
        return this.photoPath;
    }

    public Date getTimestamp() {
        // Return the timestamp the photo was added as a Date
        return this.timestamp;
    }

    public Integer getPhotoSize() {
        // Return the photo's size as an Integer
        return this.photoSize;
    }

    public void compressPhoto() {
        // If the photo's size exceeds 65536 bytes, compress the image.
        return;
    }
}
