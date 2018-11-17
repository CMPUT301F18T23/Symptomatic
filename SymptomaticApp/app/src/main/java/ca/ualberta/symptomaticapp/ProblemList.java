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
    protected static ArrayList<Problem> problemList;
    protected static transient ArrayList<Listener> listeners;

    public ProblemList(){
        problemList = new ArrayList<Problem>();
        listeners = new ArrayList<Listener>();
    }

    private static ArrayList<Listener> getListeners(){
        if (listeners == null) {
            listeners = new ArrayList<Listener>();
        }
        return listeners;
    }

    public Collection<Problem> getProblems() {
        problemList = getListFromDb();
        return problemList;
    }

    public void addProblem(Problem problem) {
        problemList.add(problem);
        notifyListeners();
    }

    public void deleteProblem(Problem problem) {
        problemList.remove(problem);
        notifyListeners();
    }

    public void clearProblems(){
        for (Problem problem: problemList){
            problem = null;
        }
    }

    public Problem getProblem(Problem problem) {
        int index = problemList.indexOf(problem);
        return problemList.get(index);
    }

    public int getSize() {
        return problemList.size();
    }

    public static void notifyListeners(){
        for (Listener listener: getListeners()) {
            listener.update();
        }
    }

    public static ArrayList<Problem> getListFromDb(String username) {
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
                            problemList.add(thisProblem);
                        }

                    } else {
                        //No users with that username exists
                    }
                } else {
                    //Query Did not Work
                }
            }
        });
        notifyListeners();
        return problemList;
    }

    public void addListener(Listener l) {
        getListeners().add(l);
    }

    public void removeListener(Listener l) {
        getListeners().remove(l);
    }


}
