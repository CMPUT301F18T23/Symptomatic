
package ca.ualberta.symptomaticapp;
/*
ListView adapter customized for displaying problems from a caregiver's point of view.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ca.ualberta.symptomaticapp.AddRecordActivity;
import ca.ualberta.symptomaticapp.Problem;
import ca.ualberta.symptomaticapp.R;
import ca.ualberta.symptomaticapp.ViewFullProblemActivity;

public class CProblemsAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Problem> problemList; //data to display
    private Context context;

    public CProblemsAdapter(ArrayList<Problem> list, Context context) { //constructor
        this.problemList = list; //assign given data to be the data that will be displayed
        this.context = context;
    }

    @Override
    public int getCount() {
        return problemList.size();
    }

    @Override
    public Problem getItem(int pos) {
        return problemList.get(pos);
    } //fetch function

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cproblems_listview, null); //using our custom XML file instead of default
        }

        //Handle UI elements and assign them the correct values
        TextView listItemText = view.findViewById(R.id.list_item_string);
        String vtext= problemList.get(position).getTitle() +"\n"+ problemList.get(position).getDate().toString();
        listItemText.setText(vtext);
        Button viewRecordsButton = view.findViewById(R.id.viewRecordsButton);

        viewRecordsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { //view records button to open the view records page
                Intent intent = new Intent(context, CViewRecords.class);
                //pass selected problem and patient to view records.
                intent.putExtra("problem", getItem(position));
                intent.putExtra("username", CViewProblems.currentpatient);
                context.startActivity(intent); //open the activity
            }
        });
        return view;
    }
}