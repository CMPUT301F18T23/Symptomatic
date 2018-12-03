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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ca.ualberta.symptomaticapp.AddRecordActivity;
import ca.ualberta.symptomaticapp.Problem;
import ca.ualberta.symptomaticapp.R;
import ca.ualberta.symptomaticapp.ViewFullProblemActivity;

public class CRecordAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Record> recordList;
    private Context context;

    public CRecordAdapter(ArrayList<Record> list, Context context) {
        this.recordList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Record getItem(int pos) {
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
            view = inflater.inflate(R.layout.crecord_listview, null);
        }



        //Handle TextView and display string from your list
        TextView listItemText = view.findViewById(R.id.recordList_item_string);
        listItemText.setText(recordList.get(position).toString());

        //Handle buttons
        Button AddCommentButton = view.findViewById( R.id.AddCommentButton);
        Button viewPhotoButton = view.findViewById(R.id.viewPhotoButton);
        Button viewGeoButton = view.findViewById(R.id.viewGeoButton);

        AddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add Comment");

// Set up the input
                final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String comment = input.getText().toString();
                        final Record rec = getItem(position);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dateString = simpleDateFormat.format(new Date());
                        final Comment newcomment = new Comment(Login.thisCaregiver.returnUsername(), dateString, comment);
                        Toast.makeText(context, newcomment.toString(), Toast.LENGTH_LONG);
                        update(newcomment, rec);
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });

        viewPhotoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SlideShowModeActivity.class);
                intent.putExtra("record", recordList.get(position));
                context.startActivity(intent);
            }
        });

        viewGeoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String[] latlng = recordList.get(position).geolocation.split(",");
                double latitude = Double.parseDouble(latlng[0]);
                double longitude = Double.parseDouble(latlng[1]);
                LatLng location = new LatLng(latitude, longitude);
                Intent intent = new Intent(context, MapOfSingleRecordActivity.class);
                intent.putExtra("title", recordList.get(position).getTitle());
                intent.putExtra("geolocation", location);
                context.startActivity(intent);
            }
        });
        return view;
    }

    public void update(final Comment comment, Record rec){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference records = db.collection("records");
        Query recquery = records.whereEqualTo("recordTitle", rec.getTitle()).whereEqualTo("user", rec.user);

        recquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String recordDocId = document.getId();
                        DocumentReference thisDocument = db.collection("records").document(recordDocId);
                        thisDocument.update("comments", FieldValue.arrayUnion(comment.toString()));
                    }
                }
            }
        });
    }
}
