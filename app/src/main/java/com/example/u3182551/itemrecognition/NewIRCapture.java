package com.example.u3182551.itemrecognition;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ircapture);

        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
        ImageView imageView = findViewById(R.id.imageView);

        Button btnProcess = findViewById(R.id.btnProcess);

        imageView.setImageBitmap(mBitmap);

        // Converting the image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AsyncTask<InputStream, String, String> visionTask = new AsyncTask<InputStream, String, String>() {
                    ProgressDialog mDialog = new ProgressDialog(NewIRCapture.this);

                    @Override
                    protected String doInBackground(InputStream... params) {
                        try {
                            publishProgress("Recognising...");
                            String[] features = {"Description"};
                            String[] details = {};

                            AnalysisResult result = visionServiceClient.analyzeImage(params[0], features, details);

                            String strResult = new Gson().toJson(result);
                            return strResult;

                        } catch (Exception e) {
                            return e.getMessage();
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                        mDialog.show();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        mDialog.dismiss();
                        StringBuilder stringBuilder = new StringBuilder();
                        TextView textView = findViewById(R.id.txtDescription);
                        try {
                            AnalysisResult result = new Gson().fromJson(s, AnalysisResult.class);
                            for (Caption caption:result.description.captions) {
                                stringBuilder.append(caption.text);
                            }
                        } catch(Exception e) {
                            textView.setText(stringBuilder);
                        }

                    }

                    @Override
                    protected void onProgressUpdate(String... values) {
                        mDialog.setMessage(values[0]);
                    }
                };

                visionTask.execute(inputStream);
            }
        });
    }
}