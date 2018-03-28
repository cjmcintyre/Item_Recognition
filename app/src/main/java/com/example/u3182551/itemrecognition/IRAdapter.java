package com.example.u3182551.itemrecognition;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by corey on 26/03/2018.
 */

public class IRAdapter extends ArrayAdapter<ItemsRecognized> {

    ArrayList<ItemsRecognized> events;

    public IRAdapter(Context context, int resource, ArrayList<ItemsRecognized> objects) {
        super(context, resource, objects);
        events = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.activity_listview_item, parent, false);
        }

        if (position % 2 == 0) convertView.setBackgroundColor(Color.parseColor("#ccffff"));
        else
            {
            convertView.setBackgroundColor(Color.parseColor("#ffffe6"));
        }

        ItemsRecognized event = events.get(position);

        ImageView icon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
        icon.setImageResource(R.mipmap.ic_launcher);

        TextView title = (TextView) convertView.findViewById(R.id.textViewTitle);
        title.setText(event.getTitle());

        return convertView;
    }
}


