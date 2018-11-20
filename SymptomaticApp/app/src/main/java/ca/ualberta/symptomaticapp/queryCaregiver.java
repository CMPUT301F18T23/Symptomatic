package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static ca.ualberta.symptomaticapp.queryCaregiver.next;
import static ca.ualberta.symptomaticapp.queryCaregiver.thisContext;
import static ca.ualberta.symptomaticapp.queryCaregiver.thisUsername;

public class queryCaregiver {
    static String thisUsername;
    static Caregiver user;
    static Context thisContext;
    static boolean next;


    public queryCaregiver(String inputUser,Context inputContext,Boolean next_task){
        thisUsername = inputUser;
        thisContext = inputContext;
        next = next_task;
    }

    public Caregiver execute(){
        ExecutorService thisQuery = Executors.newFixedThreadPool(10);

        Future<Caregiver> future = thisQuery.submit(new caregiverQuery());

        try {
            user = future.get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        return user;
    }

}

class caregiverQuery implements Callable<Caregiver> {
    private static Caregiver fetchedUser;
    Intent nextActivity;

    @Override
    public Caregiver call() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference users = db.collection("caregivers");

        Query problemsQuery = users.whereEqualTo("username",thisUsername);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int temp = task.getResult().size();
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Login.thisCaregiver = document.toObject(Caregiver.class);
                    }
                    fetchedUser = Login.thisCaregiver;
                    if(next) {
                        if (fetchedUser != null) {
                            nextActivity = new Intent(thisContext, ViewPatients.class);
                            thisContext.startActivity(nextActivity);
                        } else {
                            AlertDialog.Builder badUsernameDialog = new AlertDialog.Builder(thisContext);
                            badUsernameDialog.setMessage("The User Entered Does Not Exist");
                            badUsernameDialog.show();
                        }
                    }
                }
            }
        });
        return fetchedUser;
    }
}
