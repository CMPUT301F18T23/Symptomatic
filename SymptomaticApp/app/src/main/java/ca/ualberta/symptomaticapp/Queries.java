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

public class Queries {
    public static ProblemList theseProblems;

    public static RecordList theseRecords;

    public static ProblemList getProbFromDb(String username) {

        //Access Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Build the query
        CollectionReference problems = db.collection("problems");
        Query query = problems
                .whereEqualTo("user", username);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            //If Query Worked on not
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //Query Worked
                    if (task.getResult().size() >= 1) {
                        //A user with that username exists
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Problem thisProblem = document.toObject(Problem.class);
                            Queries.theseProblems.addProblem(thisProblem);
                        }
                    } else {
                        //No users with that username exists
                    }
                } else {
                    //Query Did not Work
                }
            }
        });
        ProblemList textProbs = Queries.theseProblems;
        return theseProblems;
    }

    public static RecordList getRecFromDb(String username, String probName) {
        theseRecords = null;
        //Access Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Build the query
        CollectionReference problems = db.collection("records");
        Query query = problems
                .whereEqualTo("user", username).whereEqualTo("problem", probName);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            //If Query Worked on not
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //Query Worked
                    if (task.getResult().size() >= 1) {
                        //A user with that username exists
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Record thisRecord = document.toObject(Record.class);
                            theseRecords.addRecord(thisRecord);
                        }
                    } else {
                        //No users with that username exists
                    }
                } else {
                    //Query Did not Work
                }
            }
        });
        return theseRecords;
    }

}

