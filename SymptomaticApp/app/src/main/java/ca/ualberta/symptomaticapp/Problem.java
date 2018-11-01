package ca.ualberta.symptomaticapp;

import java.util.Date;

public class Problem {
    protected  String title;
    protected Date date;
    protected String comment;

    public Problem (String title, Date date, String comment){
        this.title = title;
        this.date = date;
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
}
