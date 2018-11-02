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
