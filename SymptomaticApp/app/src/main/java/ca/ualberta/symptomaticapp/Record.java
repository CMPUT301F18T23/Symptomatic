package ca.ualberta.symptomaticapp;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Date;

public class Record {
    protected String recordTitle;
    protected String recordComment;
    protected ArrayList<String> bodyLocation;
    private Date recordDate;

    public Record(String recordTitle, Date date, String comment, ArrayList bodyLocation) {
        this.recordTitle = recordTitle;
        this.recordDate = date;
        this.recordComment = comment;
        this.bodyLocation = bodyLocation;
    }

    public String getTitle() {
        return this.recordTitle;
    }

    public Date getTimeStamp() {
        return this.recordDate;
    }

    public String getComment() {
        return this.recordComment;

    }

    public ArrayList<String> getBodyLocation() {
        return this.bodyLocation;
    }
}
