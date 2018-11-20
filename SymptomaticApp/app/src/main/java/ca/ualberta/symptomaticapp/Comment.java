/*
 * Comment.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Represents a caregiver comment on a patients record.
 * Each comment is composed of a user, a date, and a comment string.
 *
 * Issues: Not currently utilized.
 *
 */

package ca.ualberta.symptomaticapp;


import java.util.Date;

public class Comment {

    protected User author;
    protected Date date;
    protected String  aComment;


    public Comment(User user, Date date, String newComment) {
        this.author = user;
        this.date = date;
        this.aComment = newComment;
    }

    public User getAuthor() {
        return this.author;

    }

    public Date getDate(){
        return this.date;
    }

    public String getComment(){
        return this.aComment;
    }
}
