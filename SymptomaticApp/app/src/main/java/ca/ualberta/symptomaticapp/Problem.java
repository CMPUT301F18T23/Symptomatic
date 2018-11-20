/*
 * Problem.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Represents a patient problem. Each problem is composed of a title, date, and comment.
 *
 * Issues: Currently unable to edit problems in the database.
 *
 */

package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import ca.ualberta.symptomaticapp.Login;
import ca.ualberta.symptomaticapp.RecordList;

public class Problem implements Serializable {
    private static String title;
    private Date date;
    private String comment;
    private static int numberRecords;
    private String user;
    Context thisContext;

    static FirebaseFirestore db;
    private RecordList recordList;


    public Problem (String title, Date date, String comment){
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.user = Login.thisUser.username;
        this.numberRecords = 0;
        db = FirebaseFirestore.getInstance();
        this.recordList = new RecordList();
    }

    public Problem (){}

    public static void addProbToDb(Problem problem){
        db = FirebaseFirestore.getInstance();

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

    public int getRecordListSize() {
        return numberRecords;
    }

    public String getUser(){return this.user;}

    public String toString() {
        return this.title+ "\n" + this.date.toString() + "\n" + "Number of records:" + " " + this.getRecordListSize();
    }

    public void setNumberRecords(int num){
        this.numberRecords = num;
    }

    public void updateRecords(){
        db = FirebaseFirestore.getInstance();

        final CollectionReference records = db.collection("records");

        Query recordsQuery = records.whereEqualTo("problem",this.getTitle());

        recordsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int x = 0;
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Record record = document.toObject(Record.class);
                        numberRecords += 1;
                    }
                } else {
                }
            }
        });
    }
    private void deleteProblem(String username, Context inputContext){
        thisContext = inputContext;

        db = FirebaseFirestore.getInstance();

        CollectionReference problems = db.collection("problems");

        Query problemsQuery = problems.whereEqualTo("user",username).whereEqualTo("title",title);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int y = task.getResult().size();
                    for(QueryDocumentSnapshot document: task.getResult()){
                        String document_id = document.getId();
                        db.collection("problems").document(document_id).delete();
                    }
                } else {
                    AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(thisContext);
                    badUsernameDialog.setMessage("Data Load Error");
                    badUsernameDialog.show();
                }
            }
        });

    }

    public RecordList getRecordList() {
        return this.recordList;
    }

}
