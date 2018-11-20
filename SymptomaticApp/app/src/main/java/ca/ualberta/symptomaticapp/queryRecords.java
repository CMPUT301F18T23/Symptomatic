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
import static ca.ualberta.symptomaticapp.queryRecords.thisProblem;
import static ca.ualberta.symptomaticapp.queryUser.thisUsername;

public class queryRecords {
    static String thisProblem, thisUsername;

    public static ListViewAdapter thisAdapter;

    private static ArrayList<Record> theseRecords;


    public queryRecords(String inputUsername,String inputProblem,ListViewAdapter inputAdapter){
        thisUsername = inputUsername;
        thisProblem = inputProblem;
        thisAdapter = inputAdapter;
        theseRecords = new ArrayList<Record>();
    }

    public void execute(){
        theseRecords = new ArrayList<Record>();

        ExecutorService thisQuery = Executors.newFixedThreadPool(10);

        Future<ArrayList<Record>> future = thisQuery.submit(new recordQuery());

        try {
            theseRecords = future.get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }

}

class recordQuery implements Callable<ArrayList<Record>> {
    private static ArrayList<Record> foundRecords;

    @Override
    public ArrayList<Record> call() {
        foundRecords = new ArrayList<Record>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference users= db.collection("users");

        Query problemsQuery = users.whereEqualTo("username",thisUsername).whereEqualTo("problem",thisProblem);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Record thisRecord = document.toObject(Record.class);
                        foundRecords.add(thisRecord);
                    }
                    thisAdapter.notifyDataSetChanged();
                }
            }
        });
        return foundRecords;
    }
}
