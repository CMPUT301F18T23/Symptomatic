/*
 * Comment.java
 *
 * Version 2
 *
 * December, 3, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a caregiver comment on a patients record.
 * Each comment is composed of a user, a date, and a comment string.
 */

public class Comment {

    protected String author;
    protected Date date;
    protected String  aComment;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * User adds comment record
     * @param user
     * @param date
     * @param newComment
     */
    public Comment(String user, String date, String newComment) {
        this.author = user;
        try{
            this.date = simpleDateFormat.parse(date);
        }catch(java.text.ParseException e){
            e.printStackTrace();
        }
        this.aComment = newComment;
    }

    /**
     * User adds comment record
     * @param whole
     */
    public Comment(String whole) {
        String arr[] = whole.split(" ");

        this.author = arr[0];
        String date="";
        String comment="";
        for(int i=-2; i<0; ++i){
            date =date + arr[i];
        }
        for(int i=1; i<arr.length -2; ++i){
            comment =comment + arr[i]+ " ";
        }
        try{
            this.date = simpleDateFormat.parse(date);
        }catch(java.text.ParseException e){
            e.printStackTrace();
        }
        this.aComment = comment;
    }
    /**
     * gets the user author of the comment
     * @return author of the comment
     */
    public String getAuthor() {
        return this.author;

    }

    /**
     * gets the date of the comment
     * @return publish date of comment
     */
    public Date getDate(){
        return this.date;
    }

    public String getDateString(){
        return simpleDateFormat.format(this.date);
    }

    /**
     * gets the comment
     * @return comment
     */
    public String getComment(){
        return this.aComment;
    }

    public String toString(){
        return this.author + " " + this.aComment + " " + this.getDateString();
    }

}
