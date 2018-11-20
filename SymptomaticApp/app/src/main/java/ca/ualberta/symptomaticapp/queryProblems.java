package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static ca.ualberta.symptomaticapp.queryRecords.thisAdapter;
import static ca.ualberta.symptomaticapp.queryUser.thisUsername;

public class queryProblems {
    static String thisUsername;

    public static ListViewAdapter thisAdapter;

    private static ArrayList<Problem> theseProblems;


    public queryProblems(String inputUsername,ListViewAdapter inputAdapter){
        thisUsername = inputUsername;
        thisAdapter = inputAdapter;
        theseProblems = new ArrayList<Problem>();
    }

    public ArrayList<Problem> execute(){
        theseProblems = new ArrayList<Problem>();

        ExecutorService thisQuery = Executors.newFixedThreadPool(10);

        Future<ArrayList<Problem>> future = thisQuery.submit(new problemsQuery());

        try {
            theseProblems = future.get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        return theseProblems;
    }

}

class problemsQuery implements Callable<ArrayList<Problem>> {
    private static ArrayList<Problem> foundProblems;

    @Override
    public ArrayList<Problem> call() {
        foundProblems = new ArrayList<Problem>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference users= db.collection("users");

        Query problemsQuery = users.whereEqualTo("username",thisUsername);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Problem thisProblem = document.toObject(Problem.class);
                        foundProblems.add(thisProblem);
                    }
                    thisAdapter.notifyDataSetChanged();
                }
            }
        });
        return foundProblems;
    }
}
