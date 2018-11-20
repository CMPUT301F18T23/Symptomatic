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



import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class Record {
    protected String recordTitle;
    protected String recordComment;
    protected String geolocation;
    protected ArrayList<String> bodyLocation;
    protected ArrayList<String> photos;
    protected ArrayList<Photo> photoList;
    protected Date recordDate;
    protected String user;
    protected String problem;
    static FirebaseFirestore db;


    /**
     * Creates the instance of a record object
     * @param probName: The problem associated with the record
     * @param date: The date of the record
     */
    public Record(String probName,Date date) {
      this.recordDate = date;
      this.problem = probName;
    }

    /**
     * Adds record to the database
     * @param  record
     */
    public Record addRecord(Record record) {
        //add record to the database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference newUser = db.collection("records")
                .document();

        newUser.set(record).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) { }});

        return record;
    }

    /**
     * Gets the date of the record.
     * @return date of record
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
     * @return title = ""
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
     * @return comment  = ""
     */
    public void removeComment() {
        this.recordComment = "";
    }

    /**
     * gets the comment of a record
     * @return record comment
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
     * @return bodyLocation
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
     * Adds photo to a record
     * @param photos
     */
    public void addPhoto(ArrayList<String> photos) {
        this.photos = photos;
    }

    /**
     * gets the photos of a record
     * @return record photos
     */
    public ArrayList<String> getPhoto() {
        return this.photos;
    }

    /**
     * removes the photo of a record
     */
    public void removePhoto(String photo) {
        this.photos.remove(photo);
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
     * @return geolocation
     */
    public String getGeolocation() {
        return this.geolocation;
    }

    /**
     * removes geolocation to a record
     */
    public void removeGeolocation(String location) {
        this.geolocation = "";
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

    /**
     * Updates the geolocation of a record
     * @param location
     */
    public void updateGeolocation(String location) {
        this.geolocation = location;
    }

    /**
     * Gets the problem associated with a record
     * @return this.problem
     */
    public String getProblem() {
        return this.problem;
    }

    /**
     * Adds record to a database
     * @param record
     */
    public static void addRecToDb(Record record){
        db = FirebaseFirestore.getInstance();

        DocumentReference newUser = db.collection("records")
                .document();

        newUser.set(record).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }
}
