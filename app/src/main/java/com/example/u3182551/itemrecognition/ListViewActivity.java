package com.example.u3182551.itemrecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

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
}
