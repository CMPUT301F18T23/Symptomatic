package ca.ualberta.symptomaticapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static ca.ualberta.symptomaticapp.queryUser.next;
import static ca.ualberta.symptomaticapp.queryUser.thisContext;
import static ca.ualberta.symptomaticapp.queryUser.thisUsername;

public class queryUser {
    static String thisUsername;
    static User user;
    static Context thisContext;
    static boolean next;


    public queryUser(String inputUser,Context inputContext,Boolean next_task){
        thisUsername = inputUser;
        thisContext = inputContext;
        next = next_task;
    }

    public User execute(){
        ExecutorService thisQuery = Executors.newFixedThreadPool(10);

        Future<User> future = thisQuery.submit(new userQuery());

        try {
            user = future.get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        return user;
    }

}

class userQuery implements Callable<User> {
    private static User fetchedUser;
    Intent nextActivity;

    @Override
    public User call() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference users= db.collection("users");

        Query problemsQuery = users.whereEqualTo("username",thisUsername);

        problemsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Login.thisUser = document.toObject(User.class);
                    }
                    fetchedUser = Login.thisUser;
                    if(next) {
                        if(Login.thisUser != null) {
                            nextActivity = new Intent(thisContext, MainActivity.class);
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
