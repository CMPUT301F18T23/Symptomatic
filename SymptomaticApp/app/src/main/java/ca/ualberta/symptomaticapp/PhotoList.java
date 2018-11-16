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
