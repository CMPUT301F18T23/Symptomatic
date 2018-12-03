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
 * Stores all problems in an ArrayList for a user.
 *
 */

package ca.ualberta.symptomaticapp;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/** Represents a list of user problems.
 *
 *
 * Issues: No current issues.
 *
 */

public class ProblemList implements Serializable {
    private ArrayList<Problem> thisProblemList;

    /**
     * Creates the instance of a problem list object
     */
    public ProblemList(){
        this.thisProblemList = new ArrayList<>();
    }

    /**
     * Gets the problem list
     * @return A list of the users problems
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
            int putPlace = this.thisProblemList.size();
            for(Problem tempProb: this.thisProblemList){
                if(tempProb.getDate().before(problem.getDate())){
                    putPlace = this.thisProblemList.indexOf(tempProb);
                    break;
                }
            }
            this.thisProblemList.add(putPlace,problem);
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
     * @return The size of the users problem list.
     */
    public int getSize() {
        return this.thisProblemList.size();
    }

    public void empty(){
        int i = 0;
        while(i<this.thisProblemList.size()){
            this.thisProblemList.remove(this.thisProblemList.get(i));
        }
    }

}
