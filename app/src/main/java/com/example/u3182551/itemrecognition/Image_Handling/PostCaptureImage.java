package com.example.u3182551.itemrecognition.Image_Handling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.u3182551.itemrecognition.NewIRCapture;
import com.example.u3182551.itemrecognition.R;

public class PostCaptureImage extends NewIRCapture {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_capture_image);
    }

}
