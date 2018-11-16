package ca.ualberta.symptomaticapp;



import java.util.ArrayList;
import java.util.Date;

public class Record {
    protected String recordTitle;
    protected String recordComment;
    protected String geolocation;
    protected ArrayList<String> bodyLocation;
    protected ArrayList<String> photos;
    private Date recordDate;

    public Record(Date date) {
      this.recordDate = date;
    }


    public Date getTimeStamp() {
        return this.recordDate;
    }

    public void addTitle(String title) {
        this.recordTitle = title;
    }

    public void removeTitle() {
        this.recordTitle = "";
    }

    public String getTitle() {
        return this.recordTitle;
    }

    public void addComment(String comment) {
        this.recordComment = comment;
    }

    public void removeComment() {
        this.recordComment = "";
    }

    public String getComment() {
        return this.recordComment;
    }

    public void addBodyLocation(ArrayList<String> bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public ArrayList<String> getBodyLocation() {
        return this.bodyLocation;
    }


    public void removeBodyLocation(String bodyLocation) {
        this.bodyLocation.remove(bodyLocation);
    }

    public void addPhoto(ArrayList<String> photos) {
        this.photos = photos;
    }

    public ArrayList<String> getPhoto() {
        return this.photos;
    }

    public void removePhoto(String photo) {
        this.photos.remove(photo);
    }

    public void addGeolocation(String location) {
        this.geolocation = location;
    }

    public String getGeolocation() {
        return this.geolocation;
    }

    public void removeGeolocation(String location) {
        this.geolocation = "";
    }


    public void updateTitle(String title) {
        this.recordTitle = title;
    }

    public void updateComment(String comment) {
        this.recordComment = comment;

    }

    public void updateGeolocation(String location) {
        this.geolocation = location;
    }
}
