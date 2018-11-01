package ca.ualberta.symptomaticapp;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.symptomaticapp.Record;

public class RecordTest extends TestCase {
    public void testRecordTitle(){
        String recordTitle = "A record ";
        Date date = new Date();
        String comment = "";
        ArrayList<String> bodyLocation = new ArrayList<>();
        Record record = new Record(recordTitle, date, comment, bodyLocation);
        assertTrue("Record title is not equal", recordTitle.equals(record.getTitle()));
    }

    public void testRecordTimeStamp(){
        String recordTitle = "A record ";
        Date date = new Date();
        String comment = "";
        ArrayList<String> bodyLocation = new ArrayList<>();
        Record record = new Record(recordTitle, date, comment, bodyLocation);
        assertTrue("Date is not equal", date.equals(record.getTimeStamp()));

    }

    public void testRecordComment(){
        String comment = "A comment for a record";
        String recordTitle = "A record";
        Date date = new Date();
        ArrayList<String> bodyLocation = new ArrayList<>();
        Record record = new Record(recordTitle, date, comment, bodyLocation);
        assertTrue("Record comment is not equal", comment.equals(record.getComment()));
    }

    public void testRecordBodyLocation(){
        String comment = "A comment for a record";
        String recordTitle = "A record";
        Date date = new Date();
        ArrayList<String> bodyLocation = new ArrayList<>();
        bodyLocation.add("A body location");
        Record record = new Record(recordTitle, date, comment, bodyLocation);
        assertTrue("Body location is not equal", bodyLocation.get(0).equals(record.getBodyLocation().get(0)));
    }

    public void testRecordPhoto() {
        String comment = "A comment for a record";
        String recordTitle = "A record";
        Date date = new Date();
        ArrayList<String> bodyLocation = new ArrayList<>();
        bodyLocation.add("A body location");
        Record record = new Record(recordTitle, date, comment, bodyLocation);

    }
}
