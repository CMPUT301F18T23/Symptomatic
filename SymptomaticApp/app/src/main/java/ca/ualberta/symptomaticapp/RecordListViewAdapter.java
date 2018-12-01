/*
 * RecordListViewAdapter.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Adapter to add buttons to a list of records.
 *
 * Issues: Currently not used.
 *
 */

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

public class RecordListViewAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Record> recordList;
    private Context context;

    public RecordListViewAdapter(ArrayList<Record> list, Context context) {
        this.recordList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int pos) {
        return recordList.get(pos);
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
            view = inflater.inflate(R.layout.record_listview, null);
        }


        //Handle TextView and display string from your list
        TextView listItemText = view.findViewById(R.id.recordList_item_string);
        listItemText.setText(recordList.get(position).toString());

        //Handle buttons
        Button editRecordButton = view.findViewById(R.id.editRecordBtn);
        Button viewFullRecordButton = view.findViewById(R.id.viewFullRecordButton);


        editRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditRecordActivity.class);
                intent.putExtra("record", recordList.get(position));
                context.startActivity(intent);

            }
        });

        viewFullRecordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewFullRecordActivity.class);
                intent.putExtra("record", recordList.get(position));
                context.startActivity(intent);

            }
        });

        return view;
    }


}
