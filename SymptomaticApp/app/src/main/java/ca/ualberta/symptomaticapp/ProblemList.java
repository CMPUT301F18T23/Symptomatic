/*
 * ProblemList.java
 *
 *
 *
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

    public ArrayList<Problem> getFromDb() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Build the query
        CollectionReference problems = db.collection("problems");
        Query query = problems
                .whereEqualTo("user", Login.thisUser.username);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            //If Query Worked on not
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //Query Worked
                    int x = task.getResult().size();
                    if (task.getResult().size() >= 1) {
                        //A user with that username exists
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Problem thisProblem = document.toObject(Problem.class);
                            thisProblemList.add(thisProblem);
                        }
                    } else {
                        //No users with that username exists
                    }
                } else {
                    //Query Did not Work
                }
                int y = thisProblemList.size();
            }
        });
        int x = thisProblemList.size();
        return thisProblemList;
    }


}
