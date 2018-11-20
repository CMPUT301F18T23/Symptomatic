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
 * A list of photo that gets created when adding records.
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;

import java.util.ArrayList;
import java.util.Collection;

public class PhotoList {
    protected ArrayList<Photo> photoList;

    public PhotoList(){
        // Initialize the PhotoList
        photoList = new ArrayList<Photo>();
    }

    public Collection<Photo> getPhotos() {
        // Return the list of photo's for a record
        return photoList;
    }

    public void addPhoto(Photo newPhoto) {
        // Add a photo to the list for a record
        photoList.add(newPhoto);
    }

    public void deletePhoto(Photo deletePhoto) {
        // Delete a photo from the list for a record
        photoList.remove(deletePhoto);
    }

    public int getPhotoListSize() {
        // Return the size of the photoList currently
        return photoList.size();
    }
}
