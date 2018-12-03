package ca.ualberta.symptomaticapp;

import junit.framework.TestCase;


import java.util.Collection;
import java.util.Date;

public class RecordListTest extends TestCase {

    public void testConstructor(){
        RecordList recordList = new RecordList();
        assertTrue("Not constructed", recordList != null);

    }

    public void testEmptyRecordList() {
        RecordList recordList = new RecordList();
        Collection<Record> records = recordList.getRecords();
        assertTrue("Empty record list", records.size() == 0 );
    }

    public void testAddRecord() {
        String title = "A records title";
        String username = "newUser";
        RecordList recordList = new RecordList();
        Date date = new Date();
        Problem problem = new Problem();
        Record testRecord = new Record(problem.getTitle(), date, username, title);
        recordList.addRecord(testRecord);
        Collection<Record> records = recordList.getRecords();
        assertTrue("Record list size == 1", records.size() == 1);
        assertTrue("", records.contains(testRecord));
    }

    public void testDeleteRecord() {
        String title = "A records title";
        String username = "newUser";
        RecordList recordList = new RecordList();
        Date date = new Date();
        Problem problem = new Problem();
        Record testRecord1 = new Record(problem.getTitle(), date, username, title);
        Record testRecord2 = new Record(problem.getTitle(), date, username, title);
        Record testRecord3 = new Record(problem.getTitle(), date, username, title);
        recordList.addRecord(testRecord1);
        recordList.addRecord(testRecord2);
        recordList.addRecord(testRecord3);
        recordList.deleteRecord(testRecord2);
        Collection<Record> records = recordList.getRecords();
        assertFalse("", records.contains(testRecord2));
    }
}


