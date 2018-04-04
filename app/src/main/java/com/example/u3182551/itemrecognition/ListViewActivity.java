package com.example.u3182551.itemrecognition;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.u3182551.itemrecognition.imagehandling.CaptureImage;
import com.example.u3182551.itemrecognition.imagehandling.LoadImage;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ListViewActivity extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";

    private IRListDbHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        mListView = findViewById(R.id.listView);
        mDatabaseHelper = new IRListDbHelper(this);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        ArrayList<Images> data = mDatabaseHelper.getData();
        IRAdapter adapter = new IRAdapter(this, data);
        mListView.setAdapter(adapter);
        mListView.invalidateViews();
        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Images item = (Images) adapterView.getItemAtPosition(i);
                Intent editScreenIntent = new Intent(ListViewActivity.this, EditDataActivity.class);
                editScreenIntent.putExtra("dbId",item.getId());
                editScreenIntent.putExtra("titleDescription", item.getTitle());
                editScreenIntent.putExtra("imageDescription", item.getDescription());
                startActivity(editScreenIntent);
            }
        });
    }

    public void gotoNewIRCapture(View v) {
        Intent intent = new Intent(this, NewIRCapture.class);
        startActivity(intent);
    }

    //Menu Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.commonmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.previous_reco:
                Intent intent = new Intent(this, ListViewActivity.class);
                startActivity(intent);
                return true;
            case R.id.capture_image:
                Intent intent1 = new Intent(this, CaptureImage.class);
                startActivity(intent1);
                return true;
            case R.id.load_image:
                Intent intent2 = new Intent(this, LoadImage.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //End Menu Bar
}
