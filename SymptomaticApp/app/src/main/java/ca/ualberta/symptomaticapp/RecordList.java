/*
 * RecordList.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * A list of records that gets created from the database.
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class RecordList implements Serializable {

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

