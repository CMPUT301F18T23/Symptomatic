/*
 * PatientViewAdapter.java
 *
 * Version 1
 *
 * November, 20, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Adapter to add buttons to a list of patients.
 *
 * Issues: No current issues.
 *
 */

package ca.ualberta.symptomaticapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

public class PatientViewAdapter extends BaseAdapter implements ListAdapter {
    private List<String> patientList;
    private Context context;



    public PatientViewAdapter(List<String> list, Context context) {
        this.patientList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return patientList.size();
    }

    @Override
    public Object getItem(int pos) {
        return patientList.get(pos);
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
            view = inflater.inflate(R.layout.patients_listview, null);
        }


        //Handle TextView and display string from your list
        TextView username = view.findViewById(R.id.list_item_string);
        username.setText(patientList.get(position));

        //Handle buttons
        Button viewProblems = view.findViewById(R.id.viewProblems);
        Button viewContactInfo = view.findViewById(R.id.viewContactInfo);


        viewProblems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //opens view problems activity with the focused patient
                Intent intent = new Intent(context, CViewProblems.class);
                intent.putExtra("username", patientList.get(position));
                context.startActivity(intent);
            }
        });

        viewContactInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { //opens view contact info
                Intent intent = new Intent(context, ViewContactInfo.class);
                intent.putExtra("username", patientList.get(position));
                intent.putExtra("usertype", "user");
                context.startActivity(intent);
            }
        });


        return view;
    }


}

