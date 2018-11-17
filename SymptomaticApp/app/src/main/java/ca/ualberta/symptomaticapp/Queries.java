package ca.ualberta.symptomaticapp;

<<<<<<< HEAD:SymptomaticApp/app/src/main/java/ca/ualberta/symptomaticapp/Queries.java
import android.provider.Settings;
=======
>>>>>>> a603444937e2f661db68247192821bf9434f73e5:SymptomaticApp/app/src/main/java/ca/ualberta/symptomaticapp/query.java
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

<<<<<<< HEAD:SymptomaticApp/app/src/main/java/ca/ualberta/symptomaticapp/Queries.java
public class Queries {
=======

public class query {
>>>>>>> a603444937e2f661db68247192821bf9434f73e5:SymptomaticApp/app/src/main/java/ca/ualberta/symptomaticapp/query.java
    public static ProblemList theseProblems;

    public static RecordList theseRecords;

    public static User currUser;

    public static ProblemList getProbFromDb(String username) {
        theseProblems = null;

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
                            theseProblems.addProblem(thisProblem);
                        }
                    } else {
                        //No users with that username exists
                    }
                } else {
                    //Query Did not Work
                }
            }
        });
        return theseProblems;
    }
    public static RecordList getRecFromDb(String username,String probName) {
        theseRecords = null;
        //Access Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Build the query
        CollectionReference problems = db.collection("records");
        Query query = problems
                .whereEqualTo("user", username).whereEqualTo("problem",probName);

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

    public static User getUserFromDb (String username) {
        //Access Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Build the query
        CollectionReference active_users = db.collection("users");
        Query query = active_users
                .whereEqualTo("username", username);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            //If Query Worked on not
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //Query Worked
                    if (task.getResult().size() == 1) {
                        //A user with that username exists
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Login.thisUser = document.toObject(User.class);
                        }
                    } else {
                        //No users with that username exists
                    }
                } else {
                    //Query Did not Work
                }
            }
        });
        return Login.thisUser;
    }
}
