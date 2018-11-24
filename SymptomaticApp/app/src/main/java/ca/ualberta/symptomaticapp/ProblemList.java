/*
 * ProblemList.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 */

package ca.ualberta.symptomaticapp;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;

/** Represents a list of user problems.
 *
 *
 * Issues: No current issues.
 *
 */

public class ProblemList {
    private ArrayList<Problem> thisProblemList;

    /**
     * Creates the instance of a problem list object
     */
    public ProblemList(){
        this.thisProblemList = new ArrayList<>();
    }

    /**
     * Gets the problem list
     * @return thisProblemList
     */
    public ArrayList<Problem> getProblems() {
        return  this.thisProblemList;
    }

    /**
     * Adds a problem to the problemList
     * @param  problem
     */
    public void addProblem(Problem problem) {
        if (!this.thisProblemList.contains(problem)) {
            this.thisProblemList.add(problem);
        }
    }

    /**
     * Deletes a problem from the problemList
     * @param  problem
     */
    public void deleteProblem(Problem problem) {
        this.thisProblemList.remove(problem);
    }

    /**
     * Returns a problem if it is in the problem list
     * @param  problem
     */
    public Problem getProblem(Problem problem) {
        return this.thisProblemList.get(this.thisProblemList.indexOf(problem));
    }

    /**
     * Get the size of the problem list
     * @return Problem list size
     */
    public int getSize() {
        return this.thisProblemList.size();
    }

    public void sortArray(){
        ArrayList<Problem> sortedArray = new ArrayList<Problem>();
        while(this.thisProblemList.size()>0){
            Problem largestDate = null;
            for (Problem currProb: this.thisProblemList){
                if(largestDate == null){
                    largestDate = currProb;
                } else if (largestDate.getDate().compareTo(currProb.getDate()) < 0) {
                    largestDate = currProb;
                }
            }
            this.thisProblemList.remove(largestDate);
            sortedArray.add(largestDate);
        }
        this.thisProblemList = sortedArray;

    }

    public void empty(){
        int i = 0;
        while(i<this.thisProblemList.size()){
            this.thisProblemList.remove(this.thisProblemList.get(i));
        }
    }

}
