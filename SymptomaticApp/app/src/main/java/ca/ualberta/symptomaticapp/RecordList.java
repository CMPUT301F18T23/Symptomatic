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


/**
 * A list of records that gets created from the database.
 */
public class RecordList implements Serializable {

    protected ArrayList<Record> recordList;

    /**
     * Creates the instance of a record list object
     */
    public RecordList() {
        recordList = new ArrayList<>();
    }

    /**
     * Gets the record list
     * @return this.recordList
     */
    public Collection<Record> getRecords() {
        return recordList;
    }

    /**
     * Adds a record to record list
     * @param testRecord
     */
    public void addRecord(Record testRecord) {
        recordList.add(testRecord);

    }

    /**
     * Deletes record from record list
     * @param testRecord
     */
    public void deleteRecord(Record testRecord) {
        recordList.remove(testRecord);
    }

    /**
     * Gets the size of record list
     * @return record list size
     */
    public int size() {
        return recordList.size();
    }
}

