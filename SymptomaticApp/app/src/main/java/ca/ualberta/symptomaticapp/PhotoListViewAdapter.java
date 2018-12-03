/*
 * PhotoListViewAdapter.java
 *
 * Version 1
 *
 * November, 26, 2018.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this source code package.
 *
 * Custom adapter that displays the photos within a record along with
 * its timestamp and a delete button that removes the photo if clicked
 *
 * Issues:
 *
 */

package ca.ualberta.symptomaticapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
// Custom list view adapter with image view reference:
//AZZOLA, FRANCESCO. “Android ListView Tutorial: Custom Adapter, ImageView Rows, Checkbox.” SwA, 19 Nov. 2018,
// www.survivingwithandroid.com/2012/10/android-listview-custom-adapter-and.html#android_listview_with_custom_layout_using_image_imageview.
// Accessed: 24th November, 2018

public class PhotoListViewAdapter extends ArrayAdapter<Photo> {
    private ArrayList<Photo> photoList;
    private Context context;

    /**
     * Constructor that initiates the listview
     * @param photoList
     * @param context
     */
    public PhotoListViewAdapter(ArrayList<Photo> photoList, Context context) {
        super(context, R.layout.photos_listview, photoList);
        this.photoList = photoList;
        this.context = context;
    }

    /**
     * creates the custom photo adapter
     * @param position
     * @param convertView
     * @param parent
     * @return the custom view
     */
    public View getView(final int position, View convertView, ViewGroup parent){
        View view = convertView;

        if (convertView == null) {
            // Creates and inflates the new view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.photos_listview, null);

        }


        // Fill the view with the correct values
        TextView date = (TextView) view.findViewById(R.id.photoDate);
        ImageView img = (ImageView) view.findViewById(R.id.photoImage);
        Photo p = photoList.get(position);
        date.setText("" + p.getTimestamp());

        // converts from the string representation of a photo to its bitmap
        String stringPhoto = p.getPhotoString();
        byte[] bytePhoto = Base64.decode(stringPhoto, Base64.DEFAULT);
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);

        img.setImageBitmap(photoBitmap);


        // delete button that deletes the photo upon click
        Button deletePhoto = view.findViewById(R.id.photoDelete);

        deletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo p = photoList.get(position);
                photoList.remove(p);
                notifyDataSetChanged();

            }
        });


        return view;
    }



}
