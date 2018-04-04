package com.example.u3182551.itemrecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.u3182551.itemrecognition.imagehandling.CaptureImage;
import com.example.u3182551.itemrecognition.imagehandling.LoadImage;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void gotolistview(View v) {
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);

    }

}