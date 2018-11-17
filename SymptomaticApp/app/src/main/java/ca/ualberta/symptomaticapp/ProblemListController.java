package ca.ualberta.symptomaticapp;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProblemListController {
    private static ProblemList problemList = null;

    public static ProblemList getProblemList(String username){
        if (problemList == null){
            problemList = new ProblemList();
            problemList.getProblems();
        }
        return problemList;
    }

    public static void addProblem(Problem problem) {
        getProblemList(Login.thisUser.username).addProblem(problem);
    }

    public void getSize() {getProblemList(Login.thisUser.username).getSize();
    }

}
