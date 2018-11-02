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

    public void testUpdateTitle(){
        Date date = new Date();
        String title = "A records title";
        Record record = new Record(date);
        record.addTitle(title);
        String title2 = "A new title";
        record.updateTitle(title2);
        assertFalse("Title has not changed", record.getTitle().equals(title));

    }

    public void testDeleteTitle(){
        Date date = new Date();
        String title = "A records title";
        Record record = new Record(date);
        record.addTitle(title);
        record.removeTitle();
        assertTrue("Title is not null", record.getTitle().isEmpty());
    }


    public void testAddComment(){
        Date date = new Date();
        String comment = "A records comment";
        Record record = new Record(date);
        record.addComment(comment);
        assertTrue("Comment is not equal", comment.equals(record.getComment()));

    }

    public void testUpdateComment(){
        Date date = new Date();
        String comment = "A records comment";
        Record record = new Record(date);
        String comment2 = "A new comment";
        record.addComment(comment);
        record.updateComment(comment2);
        assertFalse("Comment has not changed", record.getComment().equals(comment));

    }

    public void testRemoveComment(){
        Date date = new Date();
        String comment = "A records comment";
        Record record = new Record(date);
        record.addComment(comment);
        record.removeComment();
        assertTrue( "comment is not null", record.getComment().isEmpty());

    }

    public void testAddBodyLocation(){
        Date date = new Date();
        ArrayList<String> bodyLocations = new ArrayList<>();
        String bodyLocation = "A body location";
        bodyLocations.add(bodyLocation);
        Record record = new Record(date);
        record.addBodyLocation(bodyLocations);
        assertTrue("Body location did not add", record.getBodyLocation().contains(bodyLocation));
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

    // Adding multiple body locations and deleting just one
    // to test that it is not just deleting by position
    public void testRemoveBodyLocation2(){
        Date date = new Date();
        ArrayList<String> bodyLocation = new ArrayList<>();
        String location1 = "A body location";
        String location2 = "A body location2";
        String location3 = "A body location3";
        bodyLocation.add(location1);
        bodyLocation.add(location2);
        bodyLocation.add(location3);
        Record record = new Record(date);
        record.addBodyLocation(bodyLocation);
        record.removeBodyLocation(location2);
        assertFalse("location is not deleted ", record.getBodyLocation().contains(location2));


        }

        // Testing the removal of a non existent BodyLocation
        // This test should fail
    public void testRemoveBodyLocation3(){
        Date date = new Date();
        ArrayList<String> bodyLocations = new ArrayList<>();
        String bodylocation = "A body location";
        Record record = new Record(date);
        record.addBodyLocation(bodyLocations);
        record.removeBodyLocation(bodylocation);
        assertTrue("Body location does not exist", record.getBodyLocation().contains(bodylocation));


    }

    // photo's are being represented as a string array for testing functionality
    public void testAddPhoto(){
        Date date = new Date();
        ArrayList<String> photos = new ArrayList<>();
        String photo = "A photo";
        photos.add(photo);
        Record record = new Record(date);
        record.addPhoto(photos);
        assertTrue("Photo is not added", record.photos.contains(photo));
        }



    public void testRemovePhoto1(){
        Date date = new Date();
        ArrayList<String> photos = new ArrayList<>();
        String photo1 = "A photo";
        photos.add(photo1);
        Record record = new Record(date);
        record.addPhoto(photos);
        record.removePhoto(photo1);
        assertTrue("Photo array is not empty", record.getPhoto().isEmpty());

    }

    // removing an non-existent photo
    // should fail
    public void testRemovePhoto2(){
        Date date = new Date();
        ArrayList<String> photos = new ArrayList<>();
        String photo1 = "A photo";
        String photo2 = "Another photo";
        photos.add(photo1);
        Record record = new Record(date);
        record.addPhoto(photos);
        record.removePhoto(photo2);
        assertTrue("Photo does not exist", record.getPhoto().isEmpty());

    }

    // making sure the right photo is removed
    public void testRemovePhoto3(){
        Date date = new Date();
        ArrayList<String> photos = new ArrayList<>();
        String photo1 = "A photo1";
        String photo2 = "A photo2";
        String photo3 = "A photo3";
        photos.add(photo1);
        photos.add(photo2);
        photos.add(photo3);
        Record record = new Record(date);
        record.addPhoto(photos);
        record.removePhoto(photo2);
        assertFalse("Photo did not get removed", record.getPhoto().contains(photo2));

    }

    // Currently working on best way to store geolocations
    // Using a string for testing purposes
    public void testAddGeolocation(){
        Date date = new Date();
        String location = "A geolocation";
        Record record = new Record(date);
        record.addGeolocation(location);
        assertTrue("Geolocation is not equal", location.equals(record.getGeolocation()));
    }

    public void testUpdateGeolocation(){
        Date date = new Date();
        String location1 = "A geolocation";
        Record record = new Record(date);
        record.addGeolocation(location1);
        String location2 = "A new geolocation";
        record.updateGeolocation(location2);
        assertFalse("Geolocation has not changed", record.getGeolocation().equals(location1));

    }

    public void testRemoveGeolocation(){
        Date date = new Date();
        String location = "A geolocation";
        Record record = new Record(date);
        record.addGeolocation(location);
        record.removeGeolocation(location);
        assertTrue("Geolocation is not null", record.getGeolocation().isEmpty());

    }
}
