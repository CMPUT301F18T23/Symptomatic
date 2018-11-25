package ca.ualberta.symptomaticapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotoListViewAdapter extends ArrayAdapter<Photo> {
    private ArrayList<Photo> photoList;
    private Context context;

    public PhotoListViewAdapter(ArrayList<Photo> photoList, Context context) {
        super(context, R.layout.photos_listview, photoList);
        this.photoList = photoList;
        this.context = context;
    }

    private static class PhotoHolder{
        public TextView PhotoDate;
        public ImageView PhotoImage;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        PhotoHolder holder = new PhotoHolder();

        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.photos_listview, null);
            // Now we can fill the layout with the right values
            TextView date = (TextView) view.findViewById(R.id.photoDate);
            ImageView img = (ImageView) view.findViewById(R.id.photoImage);

            holder.PhotoDate = date;
            holder.PhotoImage = img;

            view.setTag(holder);
        }
        else
            holder = (PhotoHolder) view.getTag();

        System.out.println("Position ["+position+"]");
        Photo p = photoList.get(position);
        holder.PhotoDate.setText("" + p.getTimestamp());
        holder.PhotoImage.setImageBitmap(p.getPhotoBitmap());


        return view;
    }



}
