package ca.ualberta.symptomaticapp;


import java.util.Date;

/**
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

public class Comment {

    protected User author;
    protected Date date;
    protected String  aComment;

    /**
     * User adds comment record
     * @param user
     * @param date
     * @param newComment
     */
    public Comment(User user, Date date, String newComment) {
        this.author = user;
        this.date = date;
        this.aComment = newComment;
    }

    /**
     * gets the user author of the comment
     * @return author of the comment
     */
    public User getAuthor() {
        return this.author;

    }

    /**
     * gets the date of the comment
     * @return publish date of comment
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * gets the comment
     * @return comment
     */
    public String getComment(){
        return this.aComment;
    }
}
