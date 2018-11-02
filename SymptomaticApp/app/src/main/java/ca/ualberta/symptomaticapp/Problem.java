package ca.ualberta.symptomaticapp;

import java.util.Date;

public class Problem {
    protected  String title;
    protected Date date;
    protected String comment;
    protected RecordList recordList = new RecordList();

    public Problem (String title, Date date, String comment){
        this.title = title;
        this.date = date;
        this.comment = comment;
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
        return recordList;
    }
}
