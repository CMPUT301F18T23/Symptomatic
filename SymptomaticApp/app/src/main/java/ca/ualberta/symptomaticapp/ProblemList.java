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
 * Represents a list of user problems.
 *
 * Issues: No current issues.
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

public class ProblemList {
    private ArrayList<Problem> thisProblemList;

    public ProblemList(){
        thisProblemList = new ArrayList<>();
    }

    public ArrayList<Problem> getProblems() {
        return thisProblemList;
    }

    public void addProblem(Problem problem) {
        thisProblemList.add(problem);
    }

    public void deleteProblem(Problem problem) {
        thisProblemList.remove(problem);
    }

    public Problem getProblem(Problem problem) {
        int index = thisProblemList.indexOf(problem);
        return thisProblemList.get(index);
    }

    public int getSize() {
        return thisProblemList.size();
    }

}
