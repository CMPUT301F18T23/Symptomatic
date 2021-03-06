/*
 * Record.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Represents a user record object. Each record is referenced by a problem name and a date.
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;



import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * Represents a user record object. Each record is referenced by a problem name and a date.
 */
public class Record implements Serializable {
    protected String recordTitle,recordComment,user,problem;

    protected String geolocation;
    protected ArrayList<String> comments;
    protected ArrayList<String> bodyLocation;

    protected PhotoList photoList;

    protected Date recordDate;

    static FirebaseFirestore db;


    /**
     * The coonstructor for a new record
     * @param probName
     * @param date
     * @param username
     * @param title
     */
    public Record(String probName,Date date,String username,String title) {
      this.recordDate = date;
      this.problem = probName;
      this.user = username;
      this.recordTitle = title;
      this.bodyLocation = null;
      this.recordComment = null;
      this.comments = new ArrayList<>();
      this.geolocation = null;
      this.photoList = new PhotoList();

    }

    public Record(){
        this.photoList = new PhotoList();
    }

    /**
     * Gets the date of the record.
     * @return The date of the record.
     */
    public Date getTimeStamp() {
        return this.recordDate;
    }

    /**
     * Adds title to the record
     * @param  title
     */
    public void addTitle(String title) {
        this.recordTitle = title;
    }

    /**
     * removes the title of the record
     */
    public void removeTitle() {
        this.recordTitle = "";
    }

    /**
     * Gets the title of the problem
     * @return record title
     */
    public String getTitle() {
        return this.recordTitle;
    }

    /**
     * Adds a comment to the record
     * @param  comment
     */
    public void addComment(String comment) {
        this.recordComment = comment;
    }

    /**
     * removes the comment of a record
     */
    public void removeComment() {
        this.recordComment = "";
    }

    /**
     * Adds a comment to the record
     * @param  comment
     */
    public void addComments(Comment comment) {
        this.comments.add(comment.toString());
    }

    /**
     * Gets all comments on the record
     *
     */
    public ArrayList<String> getComments() {
        return this.comments;
    }

    /**
     * gets the comment of a record
     * @return The records comment
     */
    public String getComment() {
        return this.recordComment;
    }

    /**
     * adds body location to a record
     * @param bodyLocation
     */
    public void addBodyLocation(ArrayList<String> bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    /**
     * gets body location of a record
     * @return A list of the records body parts.
     */
    public ArrayList<String> getBodyLocation() {
        return this.bodyLocation;
    }

    /**
     * removes the body location of a record
     */
    public void removeBodyLocation(String bodyLocation) {
        this.bodyLocation.remove(bodyLocation);
    }

    /**
     * Sets the photos of a record
     * @param photos
     */

    public void setPhotoList(ArrayList<Photo> photos) {
        this.photoList.setPhotos(photos);
    }
//
//    /**
//     * gets the photos of a record
//     * @return A list of the records photos.
//     */
    public ArrayList<Photo> getPhotoList() {
        return this.photoList.getPhotos();
    }


    /**
     * Adds geolocation to a record
     * @param location
     */
    public void addGeolocation(String location) {
        this.geolocation = location;
    }

    /**
     * Gets geolocation to a record
     * @return The geolocation of the record
     */
    public String getGeolocation() {
        return this.geolocation;
    }

    /**
     * removes geolocation to a record
     */
    public void removeGeolocation(LatLng location) {
        this.geolocation = null;
    }


    /**
     * Updates the title of a record
     * @param title
     */
    public void updateTitle(String title) {
        this.recordTitle = title;
    }

    /**
     * Updates the comment of a record
     * @param comment
     */
    public void updateComment(String comment) {
        this.recordComment = comment;
    }


    public String toString() {
        String returnString = "";
        if(this.recordTitle!= null) {
            returnString += this.recordTitle + "\n";
        }
        if (this.recordDate!=null){
            returnString += this.recordDate.toString();
        }
        return returnString;
    }

    /**
     * Updates the geolocation of a record
     * @param location
     */
    public void updateGeolocation(String location) {
        this.geolocation = location;
    }

    /**
     * Gets the problem associated with a record
     * @return The problem associated with the record
     */
    public String getProblem() {
        return this.problem;
    }

    /**
     * Adds record to a database
     * @param
     */
    public void addRecToDb(){
        db = FirebaseFirestore.getInstance();

        DocumentReference newUser = db.collection("records")
                .document();

        newUser.set(this);
    }
}
