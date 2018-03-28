package com.example.u3182551.itemrecognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class IRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ir);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        int imageRes = extras.getInt("imageResource");

        TextView tv = (TextView) findViewById(R.id.textViewLarge);
        tv.setText(title);
        ImageView image = (ImageView) findViewById(R.id.imageView2);
        image.setImageResource(imageRes);




    }

    //Menu Bar
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.commonmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(IRActivity.this, "Selected options menu item: About", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.help:
                Toast.makeText(IRActivity.this, "Selected options menu item: Help", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //End Menu Bar
}
