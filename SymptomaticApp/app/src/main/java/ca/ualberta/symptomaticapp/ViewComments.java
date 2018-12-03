/* Activity for viewing the comments on a given record */

package ca.ualberta.symptomaticapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewComments extends AppCompatActivity {
    private Record record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
        //get passed record
        record = (Record) getIntent().getSerializableExtra("record");
        //get ui elements
        TextView title = (TextView)findViewById(R.id.tv_title);
        ListView commentview = (ListView) findViewById(R.id.lv_comments);
        //

        //set title using given record title
        List<String> comments = record.getComments();
        title.setText("Comments for " + record.getTitle());
        //use our custom adapter, fill it with the records.
        CommentAdapter adapter = new CommentAdapter(comments, this);
        commentview.setAdapter(adapter); //set adapter
    }
}