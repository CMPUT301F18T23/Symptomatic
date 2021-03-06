/*
 * PhotoList.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * An ArrayList that stores all the photos of a record.
 *
 * Issues:
 *
 */
package ca.ualberta.symptomaticapp;

import java.io.Serializable;
import java.util.ArrayList;


/**
 *  A list of photo that gets created when adding records.
 */

public class PhotoList implements Serializable {
    protected ArrayList<Photo> photoList;

    /**
     * Constructor for PhotoList
     */
    public PhotoList(){
        // Initialize the PhotoList to be an array of Photo objects
        photoList = new ArrayList<Photo>();
    }

    /**
     * Photo getter
     * @return a list of photo's
     */
    public ArrayList<Photo> getPhotos() {
        return photoList;
    }

    /**
     * Adds a photo into the list
     * @param newPhoto
     */
    public void addPhoto(Photo newPhoto) {
        // Add a photo to the list for a record
        photoList.add(newPhoto);
    }

    /**
     * Deletes a photo from the photo list
     * @param deletePhoto
     */
    public void deletePhoto(Photo deletePhoto) {
        // Delete a photo from the list for a record
        photoList.remove(deletePhoto);
    }

    /**
     * gets the photoListSize
     * @return an integer containing the photolist size
     */
    public int getPhotoListSize() {
        // Return the size of the photoList currently
        return photoList.size();
    }

    /**
     * sets the entire photolist
     * @param photos
     */
    public void setPhotos(ArrayList<Photo> photos) {
        this.photoList = photos;
    }
}
