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
        thisProblemList = new ArrayList<>();
    }

    /**
     * Gets the problem list
     * @return thisProblemList
     */
    public ArrayList<Problem> getProblems() {
        return thisProblemList;
    }

    /**
     * Adds a problem to the problemList
     * @param  problem
     */
    public void addProblem(Problem problem) {
        thisProblemList.add(problem);
    }

    /**
     * Deletes a problem from the problemList
     * @param  problem
     */
    public void deleteProblem(Problem problem) {
        thisProblemList.remove(problem);
    }

    /**
     * Returns a problem if it is in the problem list
     * @param  problem
     */
    public Problem getProblem(Problem problem) {
        int index = thisProblemList.indexOf(problem);
        return thisProblemList.get(index);
    }

    /**
     * Get the size of the problem list
     * @return Problem list size
     */
    public int getSize() {
        return thisProblemList.size();
    }

}
