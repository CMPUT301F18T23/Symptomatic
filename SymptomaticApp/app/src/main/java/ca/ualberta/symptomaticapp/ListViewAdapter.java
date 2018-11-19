
package ca.ualberta.symptomaticapp;

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

public class ListViewAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Problem> problemList;
    private Context context;



    public ListViewAdapter(ArrayList<Problem> list, Context context) {
        this.problemList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return problemList.size();
    }

    @Override
    public Object getItem(int pos) {
        return problemList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_listview, null);
        }





        //Handle TextView and display string from your list
        TextView listItemText = view.findViewById(R.id.list_item_string);
        listItemText.setText(problemList.get(position).toString());

        //Handle buttons
        Button viewFullProblemButton = view.findViewById(R.id.viewFullProblemButton);
        Button addRecordButton = view.findViewById(R.id.addRecordButton);
        Button editRecordButton = view.findViewById(R.id.editRecordButton);

        editRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProblemActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("problem", problemList.get(position));
                intent.putExtra("problem", problemList.get(position));
                context.startActivity(intent);
            }
        });

        viewFullProblemButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewFullProblemActivity.class);
                intent.putExtra("problem", problemList.get(position));
                context.startActivity(intent);
            }
        });
        addRecordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddRecordActivity.class);

                context.startActivity(intent);
            }
        });


        return view;
    }


}

