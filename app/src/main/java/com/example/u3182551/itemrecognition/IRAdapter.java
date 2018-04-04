package com.example.u3182551.itemrecognition;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
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

@SuppressWarnings("DefaultFileTemplate")
class IRAdapter extends ArrayAdapter<Images> {

    private ArrayList<Images> images = new ArrayList<>();
    public IRAdapter(Context context, ArrayList<Images> objects) {
        super(context, R.layout.activity_listview_item,objects);
        images = objects;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @SuppressWarnings("NullableProblems") ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.activity_listview_item, parent, false);
        }

        if (position % 2 == 0) convertView.setBackgroundColor(Color.parseColor("#ccffff"));
        else
        {
            convertView.setBackgroundColor(Color.parseColor("#ffffe6"));
        }

        Images event = images.get(position);

        ImageView icon = convertView.findViewById(R.id.imageViewIcon);
        icon.setImageResource(R.mipmap.ic_launcher);

        TextView title = convertView.findViewById(R.id.textViewTitle);
        title.setText(event.getTitle());

        return convertView;
    }
}


