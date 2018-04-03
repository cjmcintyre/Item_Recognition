package com.example.u3182551.itemrecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PostCaptureImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_capture_image);
    }

    public void gotoEditData2(View v) {
        Intent intent = new Intent(this, EditDataActivity.class);
        startActivity(intent);

    }
}
