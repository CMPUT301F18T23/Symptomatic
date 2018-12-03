package ca.ualberta.symptomaticapp;



import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;



public class RecordTest extends TestCase {

    public void testConstructor(){
        Date date = new Date();
        String pname = "problem1";
        String username = "newUser";
        String rtitle = "record1";
        Record record = new Record(pname, date, username, rtitle);
        assertTrue("Problem title is not equal", pname.equals(record.problem));
        assertTrue("Date is not equal", date.equals(record.recordDate));

    }

    public void testTimeStamp(){
        Date date = new Date();
        String pname = "problem1";
        String username = "newUser";
        String rtitle = "record1";
        Record record = new Record(pname, date, username, rtitle);
        assertTrue("Date is not equal", date.equals(record.getTimeStamp()));

    }

    public void testAddTitle(){
        Date date = new Date();
        String title = "A records title";
        String pname = "problem1";
        String username = "newUser";
        Record record = new Record(pname, date, username, title);
        record.addTitle(title);
        assertTrue("Title is not equal", title.equals(record.getTitle()));

    }

    public void testUpdateTitle(){
        Date date = new Date();
        String title = "A records title";
        String pname = "problem1";
        String username = "newUser";
        Record record = new Record(pname, date, username, title);
        record.addTitle(title);
        String title2 = "A new title";
        record.updateTitle(title2);
        assertFalse("Title has not changed", record.getTitle().equals(title));

    }

    public void testDeleteTitle(){
        Date date = new Date();
        String title = "A records title";
        String pname = "problem1";
        String username = "newUser";
        Record record = new Record(pname, date, username, title);
        record.addTitle(title);
        record.removeTitle();
        assertTrue("Title is not null", record.getTitle().isEmpty());
    }


    public void testAddComment(){
        Date date = new Date();
        String title = "A records title";
        String comment = "A records comment";
        String username = "newUser";
        Problem problem = new Problem();
        Record record = new Record(problem.getTitle(), date, username, title);
        record.addComment(comment);
        assertTrue("Comment is not equal", comment.equals(record.getComment()));

    }

    public void testUpdateComment(){
        Date date = new Date();
        String comment = "A records comment";
        String title = "A records title";
        String username = "newUser";
        Problem problem = new Problem();
        Record record = new Record(problem.getTitle(), date, username, title);
        String comment2 = "A new comment";
        record.addComment(comment);
        record.updateComment(comment2);
        assertFalse("Comment has not changed", record.getComment().equals(comment));

    }

    public void testRemoveComment(){
        Date date = new Date();
        String comment = "A records comment";
        String title = "A records title";
        String username = "newUser";
        Problem problem = new Problem();
        Record record = new Record(problem.getTitle(), date, username, title);
        record.addComment(comment);
        record.removeComment();
        assertTrue( "comment is not null", record.getComment().isEmpty());

    }

    public void testAddBodyLocation(){
        Date date = new Date();
        ArrayList<String> bodyLocations = new ArrayList<>();
        String bodyLocation = "A body location";
        String title = "A records title";
        String username = "newUser";
        bodyLocations.add(bodyLocation);
        Problem problem = new Problem();
        Record record = new Record(problem.getTitle(), date, username, title);
        record.addBodyLocation(bodyLocations);
        assertTrue("Body location did not add", record.getBodyLocation().contains(bodyLocation));
    }

    public void testRemoveBodyLocation(){
        Date date = new Date();
        ArrayList<String> bodyLocation = new ArrayList<>();
        bodyLocation.add("A body location");
        String title = "A records title";
        String username = "newUser";
        Problem problem = new Problem();
        Record record = new Record(problem.getTitle(), date, username, title);
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
        String title = "A records title";
        String username = "newUser";
        Problem problem = new Problem();
        Record record = new Record(problem.getTitle(), date, username, title);
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
        String title = "A records title";
        String username = "newUser";
        Problem problem = new Problem();
        Record record = new Record(problem.getTitle(), date, username, title);
        record.addBodyLocation(bodyLocations);
        record.removeBodyLocation(bodylocation);
        assertTrue("Body location does not exist", record.getBodyLocation().contains(bodylocation));


    }

    public void testSetPhoto(){
        Date date = new Date();
        ArrayList<Photo> photos = new ArrayList<>();
        String stringPhoto = "A photo";
        Photo photo = new Photo(stringPhoto);
        photos.add(photo);
        Problem problem = new Problem();
        String title = "A records title";
        String username = "newUser";
        Record record = new Record(problem.getTitle(), date, username, title);
        record.setPhotoList(photos);
        assertTrue("Photo is not added1", record.photoList.photoList.contains(photo));
    }

    public void testGetPhotos(){
        Date date = new Date();
        ArrayList<Photo> photos = new ArrayList<>();
        String stringPhoto = "A photo";
        Photo photo = new Photo(stringPhoto);
        photos.add(photo);
        Problem problem = new Problem();
        String title = "A records title";
        String username = "newUser";
        Record record = new Record(problem.getTitle(), date, username, title);
        record.setPhotoList(photos);
        assertTrue("get Photos doesn't work", record.getPhotoList() == photos);
    }




//    public void testRemovePhoto1(){
//        Date date = new Date();
//        ArrayList<String> photos = new ArrayList<>();
//        String photo1 = "A photo";
//        photos.add(photo1);
//        Problem problem = new Problem();
//        Record record = new Record(problem.getTitle(), date);
//        record.addPhoto(photos);
//        record.removePhoto(photo1);
//        assertTrue("Photo array is not empty", record.getPhoto().isEmpty());
//
//    }
//
//    // removing an non-existent photo
//    // should fail
//    public void testRemovePhoto2(){
//        Date date = new Date();
//        ArrayList<String> photos = new ArrayList<>();
//        String photo1 = "A photo";
//        String photo2 = "Another photo";
//        photos.add(photo1);
//        Problem problem = new Problem();
//        Record record = new Record(problem.getTitle(), date);
//        record.addPhoto(photos);
//        record.removePhoto(photo2);
//        assertTrue("Photo does not exist", record.getPhoto().isEmpty());
//
//    }
//
//    // making sure the right photo is removed
//    public void testRemovePhoto3(){
//        Date date = new Date();
//        ArrayList<String> photos = new ArrayList<>();
//        String photo1 = "A photo1";
//        String photo2 = "A photo2";
//        String photo3 = "A photo3";
//        photos.add(photo1);
//        photos.add(photo2);
//        photos.add(photo3);
//        Problem problem = new Problem();
//        Record record = new Record(problem.getTitle(), date);
//        record.addPhoto(photos);
//        record.removePhoto(photo2);
//        assertFalse("Photo did not get removed", record.getPhoto().contains(photo2));
//
//    }

    // Currently working on best way to store geolocations
    // Using a string for testing purposes
    public void testAddGeolocation(){
        Date date = new Date();
        String location = "A geolocation";
        String title = "A records title";
        String username = "newUser";
        Problem problem = new Problem();
        Record record = new Record(problem.getTitle(), date, username, title);
        record.addGeolocation(location);
        assertTrue("Geolocation is not equal", location.equals(record.getGeolocation()));
    }

    public void testUpdateGeolocation(){
        Date date = new Date();
        String location1 = "A geolocation";
        String title = "A records title";
        String username = "newUser";
        Problem problem = new Problem();
        Record record = new Record(problem.getTitle(), date, username, title);
        record.addGeolocation(location1);
        String location2 = "A new geolocation";
        record.updateGeolocation(location2);
        assertFalse("Geolocation has not changed", record.getGeolocation().equals(location1));

    }

//    public void testRemoveGeolocation(){
//        Date date = new Date();
//        String location = "A geolocation";
//        String title = "A records title";
//        String username = "newUser";
//        Problem problem = new Problem();
//        Record record = new Record(problem.getTitle(), date, username, title);
//        record.addGeolocation(location);
//        record.removeGeolocation(location);
//        assertTrue("Geolocation is not null", record.getGeolocation().isEmpty());
//
//    }
}
