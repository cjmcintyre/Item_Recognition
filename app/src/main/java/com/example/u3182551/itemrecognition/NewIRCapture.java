package com.example.u3182551.itemrecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.u3182551.itemrecognition.imagehandling.CaptureImage;
import com.example.u3182551.itemrecognition.imagehandling.LoadImage;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;


@SuppressWarnings("ALL")
public class NewIRCapture extends AppCompatActivity {

    protected final VisionServiceClient visionServiceClient = new VisionServiceRestClient("1caf0086471f49d08693b33a8d3e261b", "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0");

    public void gotoLoadImage(View v) {
        Intent intent = new Intent(this, LoadImage.class);
        startActivity(intent);

    }

    public void gotoCaptureImage(View v) {
        Intent intent = new Intent(this, CaptureImage.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ircapture);


    }
}