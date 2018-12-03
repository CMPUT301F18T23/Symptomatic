
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
import java.util.List;

import ca.ualberta.symptomaticapp.AddRecordActivity;
import ca.ualberta.symptomaticapp.Problem;
import ca.ualberta.symptomaticapp.R;
import ca.ualberta.symptomaticapp.ViewFullProblemActivity;

public class CommentAdapter extends BaseAdapter implements ListAdapter {
    private List<String> commentList; //data to display
    private Context context;

    public CommentAdapter(List<String> list, Context context) { //constructor
        this.commentList = list; //assign given data to be the data that will be displayed
        this.context = context;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public String getItem(int pos) {
        return commentList.get(pos);
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
            view = inflater.inflate(R.layout.comment_listview, null); //using our custom XML file instead of default
        }

        //Handle UI elements and assign them the correct values
        String item[] = getItem(position).split(" ");
        final String author = item[0];
        String comment="";
        for(int i=1; i<item.length -2; ++i){
            comment =comment + item[i]+ " ";
        }
        String date=item[item.length-2] + " " +item[item.length-1];

        TextView listItemText = view.findViewById(R.id.textual);
        String vtext="Author:  " + author + "\n" +
                "Comment:  " + comment + "\n" +
                "Date:  " + date;
        listItemText.setText(vtext);
        Button viewAuthor = view.findViewById(R.id.viewAuthor);

        viewAuthor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { //view records button to open the view records page
                Intent intent = new Intent(context, ViewContactInfo.class);
                intent.putExtra("username", author);
                intent.putExtra("usertype", "caregiver");
                context.startActivity(intent);
            }
        });
        return view;
    }
}