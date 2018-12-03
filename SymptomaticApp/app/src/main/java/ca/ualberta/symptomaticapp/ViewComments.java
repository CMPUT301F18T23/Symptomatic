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
        record = (Record) getIntent().getSerializableExtra("record");
        TextView title = (TextView)findViewById(R.id.tv_title);
        ListView commentview = (ListView) findViewById(R.id.lv_comments);
        List<String> comments = record.getComments();
        title.setText("Comments for " + record.getTitle());
        CommentAdapter adapter = new CommentAdapter(comments, this);
        commentview.setAdapter(adapter);
    }
}