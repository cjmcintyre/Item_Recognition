package com.example.u3182551.itemrecognition;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {


    private static final String TAG = "ListDataActivity";

    IRListDbHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new IRListDbHelper(this);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
        mListView.invalidateViews();
        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = mDatabaseHelper.getItemID(name); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(ListViewActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void gotoNewIRCapture(View v) {
        Intent intent = new Intent(this, NewIRCapture.class);
        startActivity(intent);

    }
}










/*
    ArrayList<ItemsRecognized> events = new ArrayList<ItemsRecognized>();
    IRListDbHelper mydb = new IRListDbHelper(
            this, "ItemsRecognizedDB", null, 4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // events.add(new ItemsRecognized("Test1", R.drawable.placeholder));
        // events.add(new ItemsRecognized("Test2", R.drawable.placeholder));
        // events.add(new ItemsRecognized("Test3", R.drawable.placeholder));

        events = mydb.getAllEvents();
        if (events.isEmpty()) {
            mydb.insertEvent(new ItemsRecognized("Test1", R.drawable.placeholder));
            mydb.insertEvent(new ItemsRecognized("Test2", R.drawable.placeholder));
            mydb.insertEvent(new ItemsRecognized("Test3", R.drawable.placeholder));
            events = mydb.getAllEvents();
        }



        //    ArrayAdapter<ItemsRecognized> adapter = new ArrayAdapter<ItemsRecognized>(this, android.R.layout.simple_list_item_1, events);
        IRAdapter adapter = new IRAdapter(this, R.layout.activity_listview_item, events);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ItemsRecognized IRevent = events.get(position);
                        Intent intent = new Intent(view.getContext(), IRActivity.class);
                        intent.putExtra("title", IRevent.getTitle());
                        intent.putExtra("imageResource", IRevent.getImageResource());
                        startActivity(intent);

                    }
                }
        );
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
            case R.id.about:
                Toast.makeText(ListViewActivity.this, "Selected options menu item: About", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.help:
                Toast.makeText(ListViewActivity.this, "Selected options menu item: Help", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //End Menu Bar

    public void gotoNewIRCapture(View v) {
        Intent intent = new Intent(this, NewIRCapture.class);
        startActivity(intent);

    }
}*/
