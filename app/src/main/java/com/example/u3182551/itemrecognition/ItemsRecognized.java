package com.example.u3182551.itemrecognition;

/**
 * Created by corey on 26/03/2018.
 */

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;

public class ItemsRecognized {

    String title;
    int imageResource;

    public ItemsRecognized(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;

    }

    public  String getTitle() {
        return this.title;

    }

    public int getImageResource() {
        return this.imageResource;

    }

    @Override
    public String toString() {
        return title;
    }

}


