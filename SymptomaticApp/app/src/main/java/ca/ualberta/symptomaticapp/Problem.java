/**
 * Problem.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
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
import static ca.ualberta.symptomaticapp.ListProblemsActivity.listAdapter;


/**
 * Represents a patient problem. Each problem is composed of a title, date, and comment.
 *
 * Issues: Currently unable to edit problems in the database.
 *
 */

public class Problem implements Serializable {
    private String title;
    private Date date;
    private String comment;
    private int numberRecords;
    private String user;
    Context thisContext;

    static FirebaseFirestore db;


    /**
     * Creates the instance of a problem object
     * @param title: The title of the problem
     * @param date: The date of the problem
     * @param comment: The comment of the problem
     */
    public Problem (String title, Date date, String comment){
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.user = Login.thisUser.username;
        this.numberRecords = 0;
        db = FirebaseFirestore.getInstance();
    }

    public Problem (){}

    public void addProbToDb(){
        db = FirebaseFirestore.getInstance();

        DocumentReference newUser = db.collection("problems")
                .document();

        newUser.set(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    /**
     * Sets the title of the problem
     * @param  title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Sets the title of the problem
     * @param  date
     */
    public void setDate(Date date){
        this.date = date;
    }

    /**
     * Sets the comment of the problem
     * @param  comment
     */
    public void setComment(String comment){
        this.comment = comment;
    }

    /**
     * Gets the problem title
     * @return The title of the problem
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the problem date
     * @return The date of the problem
     */
    public Date getDate() {
        return date;
    }


    /**
     * Gets the problem comment
     * @return The comment of a problem
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the problem's record list size
     * @return The number of records associated with a problem
     */
    public int getRecordListSize() {
        return numberRecords;
    }

    /**
     * Gets the user
     * @return The current user's username
     */
    public String getUser(){return this.user;}

    /**
     * Creates a problem string
     * @return A string representing a problem
     */
    public String toString() {
        return this.title+ "\n" + this.date.toString() + "\n" + "Number of records:" + " " + this.getRecordListSize();
    }

    public void setNumberRecords(int num){
        this.numberRecords = num;
    }

    /**
     * Updates records in database
     */
    public void updateRecords(){
        db = FirebaseFirestore.getInstance();

        final CollectionReference records = db.collection("records");

        Query recordsQuery = records.whereEqualTo("problem",this.getTitle()).whereEqualTo("user",Login.thisUser.returnUsername());

        recordsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int x = 0;
                    setNumberRecords(task.getResult().size());
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Deletes problem from database
     * @param username
     * @param inputContext
     */
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

}
