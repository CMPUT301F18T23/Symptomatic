package ca.ualberta.symptomaticapp;

import java.util.Date;

public class Photo {
    protected String photoPath;
    protected String timestamp;
    protected Integer photoSize;
    protected byte[] photoByteArray;

    public Photo(byte[] photoByteArray, String timestamp){
        // Initialize the Photo object
        this.timestamp = timestamp;
        this.photoByteArray = photoByteArray;
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
        return;
    }

    public byte[] getPhotoByteArray (){
        return this.photoByteArray;
    }
}
