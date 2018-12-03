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
        this.recordList = new ArrayList<>();
    }

    /**
     * Gets the record list
     * @return A list of the patients records.
     */
    public ArrayList<Record> getRecords() {
        return recordList;
    }

    public int getSize(){
        if(this.recordList == null){
            return 0;
        } else {
            return this.recordList.size();
        }
    }

    /**
     * Adds a record to record list
     * @param thisRecord
     */
    public void addRecord(Record thisRecord) {
        this.recordList.add(thisRecord);
    }

    public void empty(){
        this.recordList = new ArrayList<Record>();
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
     * @return The size of the patients record list.
     */
    public int size() {
        return recordList.size();
    }
}

