/*
 * Problem.java
 *
 *
 *
 *
 */


package ca.ualberta.symptomaticapp;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.Date;

import ca.ualberta.symptomaticapp.Login;
import ca.ualberta.symptomaticapp.RecordList;

public class Problem {
    protected  String title;
    protected Date date;
    protected String comment;
    protected RecordList recordList = new RecordList();
    protected int numberRecords;
    protected String user;

    public Problem (String title, Date date, String comment){
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.user = Login.thisUser.username;
    }


    public static void addProbToDb(Problem problem){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference newUser = db.collection("problems")
                .document();

        newUser.set(problem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public RecordList getRecordList() {
        return this.recordList;
    }

    public int getRecordListSize() {
        this.numberRecords = this.recordList.size();
        return this.numberRecords;
    }

    public String getUser(){return this.user;}

    public String toString() {
        return this.title+ "\n" + this.date.toString() + "\n" + "Number of records:" + " " + this.getRecordListSize();
    }
}
