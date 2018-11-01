package ca.ualberta.symptomaticapp;



import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;



public class RecordTest extends TestCase {
    public void testTimeStamp(){
        Date date = new Date();
        Record record = new Record(date);
        assertTrue("Date is not equal", date.equals(record.getTimeStamp()));

    }

    public void testAddTitle(){
        Date date = new Date();
        String title = "A records title";
        Record record = new Record(date);
        record.addTitle(title);
        assertTrue("Title is not equal", title.equals(record.getTitle()));

    }

    public void testDeleteTitle(){
        Date date = new Date();
        String title = "A records title";
        Record record = new Record(date);
        record.addTitle(title);
        record.removeTitle(title);
        assertTrue("Title is not null", record.getTitle() == null);
    }


    public void testAddComment(){
        Date date = new Date();
        String comment = "A records comment";
        Record record = new Record(date);
        record.addComment(comment);
        assertTrue("Comment is not equal", comment.equals(record.getComment()));

    }

    public void testRemoveComment(){
        Date date = new Date();
        String comment = "A records comment";
        Record record = new Record(date);
        record.addComment(comment);
        record.removeComment(comment);
        assertTrue( "comment is not null", record.getComment() == null);

    }

    public void testAddBodyLocation(){
        Date date = new Date();
        ArrayList<String> bodyLocation = new ArrayList<>();
        bodyLocation.add("A body location");
        Record record = new Record(date);
        record.addBodyLocation(bodyLocation);
        assertTrue("Body location is not equal", bodyLocation.get(0).equals(record.getBodyLocation().get(0)));
    }

    public void testRemoveBodyLocation(){
        Date date = new Date();
        ArrayList<String> bodyLocation = new ArrayList<>();
        bodyLocation.add("A body location");
        Record record = new Record(date);
        record.addBodyLocation(bodyLocation);
        record.removeBodyLocation("A body location");
        assertTrue("Body location array is not empty", record.getBodyLocation().isEmpty() == true);


    }

    // photo's are being represented as a string array for testing functionality
    public void testAddPhoto(){
        Date date = new Date();
        ArrayList<String> photos = new ArrayList<>();
        photos.add("A photo");
        Record record = new Record(date);
        record.addPhoto(photos);
        assertTrue("Photo is not equal", photos.get(0).equals(record.getPhoto().get(0)));
        }



    public void testRemovePhoto(){
        Date date = new Date();
        ArrayList<String> photos = new ArrayList<>();
        photos.add("A photo");
        Record record = new Record(date);
        record.addPhoto(photos);
        record.removePhoto("A photo");
        assertTrue("Photo array is not empty", record.getPhoto().isEmpty() == true);

    }

    public void testAddGeolocation(){
        Date date = new Date();
        String location = "A geolocation";
        Record record = new Record(date);
        record.addGeolocation(location);
        assertTrue("Geolocation is not equal", location.equals(record.getGeolocation()));
    }

    public void testRemoveGeolocation(){
        Date date = new Date();
        String location = "A geolocation";
        Record record = new Record(date);
        record.addGeolocation(location);
        record.removeGeolocation(location);
        assertTrue("Geolocation is not null", record.getGeolocation() == null);

    }
}
