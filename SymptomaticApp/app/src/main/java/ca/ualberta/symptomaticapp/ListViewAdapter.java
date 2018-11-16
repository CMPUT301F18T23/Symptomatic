package ca.ualberta.symptomaticapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter implements ListAdapter {
    ArrayList<Problem> problemList;
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

        viewFullProblemButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


            }
        });
        addRecordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }
}

