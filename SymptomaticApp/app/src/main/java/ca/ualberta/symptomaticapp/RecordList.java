package ca.ualberta.symptomaticapp;

import java.util.ArrayList;
import java.util.Collection;

public class RecordList  {

    protected ArrayList<Record> recordList;

    public RecordList() {
        recordList = new ArrayList<>();
    }


    public Collection<Record> getRecords() {
        return recordList;
    }

    public void addRecord(Record testRecord) {
        recordList.add(testRecord);

    }

    public void deleteRecord(Record testRecord) {
        recordList.remove(testRecord);
    }


    public int size() {
        return recordList.size();
    }
}

