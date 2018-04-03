package com.example.u3182551.itemrecognition;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Caption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class NewIRCapture extends AppCompatActivity {

    public VisionServiceClient visionServiceClient = new VisionServiceRestClient("1caf0086471f49d08693b33a8d3e261b", "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0");


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