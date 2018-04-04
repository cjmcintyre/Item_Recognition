package com.example.u3182551.itemrecognition.imagehandling;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.u3182551.itemrecognition.EditDataActivity;
import com.example.u3182551.itemrecognition.IRListDbHelper;
import com.example.u3182551.itemrecognition.NewIRCapture;
import com.example.u3182551.itemrecognition.R;
import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Caption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@SuppressWarnings("ALL")
public class CaptureImage extends NewIRCapture {
    private static final int REQUEST_TAKE_PHOTO = 0;
    private Bitmap mBitmap;
    private IRListDbHelper mDatabaseHelper;
    private long dbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        mDatabaseHelper = new IRListDbHelper(this);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
    }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            assert extras != null;
            mBitmap = (Bitmap) extras.get("data");
        }

        if (mBitmap != null) {
            // Show the image on screen.
            ImageView imageView = findViewById(R.id.postcaptureImage);
            imageView.setImageBitmap(mBitmap);
            doAnalyse();

        }
    }

    private void doAnalyse(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        @SuppressWarnings("deprecation") @SuppressLint("StaticFieldLeak") final AsyncTask<InputStream, String, String> visionTask = new AsyncTask<InputStream, String, String>() {
            final ProgressDialog mDialog = new ProgressDialog(CaptureImage.this);

            @Override
            protected String doInBackground(InputStream... params) {
                try {
                    publishProgress("Recognising...");
                    String[] features = {"Description"};
                    String[] details = {};

                    AnalysisResult result = visionServiceClient.analyzeImage(params[0], features, details);

                    return new Gson().toJson(result);

                } catch (Exception e) {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPreExecute() {
                mDialog.show();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPostExecute(String s) {

                setContentView(R.layout.activity_post_capture_image);


                mDialog.dismiss();

                AnalysisResult result = new Gson().fromJson(s, AnalysisResult.class);
                TextView textView = findViewById(R.id.PostCaptureText);
                TextView textView2 = findViewById(R.id.LoadCaptureName);
                StringBuilder stringBuilder = new StringBuilder();

                for (Caption caption : result.description.captions) {
                    stringBuilder.append(caption.text);
                    String newEntry = (caption.text);
                    String newEntry2 = ("Enter Title");
                    addData(newEntry, newEntry2);

                }
                textView.setText(stringBuilder);
                textView2.setText("Enter Title");

            }

            public void addData(String newEntry, String newEntry2) {
                long insertId = mDatabaseHelper.addData(newEntry, newEntry2);
                if (insertId > -1) {
                    toastMessage("Data Successfully Inserted!");
                    dbId=insertId;
                } else {
                    toastMessage("Something went wrong");
                }
            }

            @Override
            protected void onProgressUpdate(String... values) {
                mDialog.setMessage(values[0]);
            }

        };
        visionTask.execute(inputStream);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
    public void gotoEditData(View v) {
        Intent editScreenIntent = new Intent(CaptureImage.this, EditDataActivity.class);
        TextView description = findViewById(R.id.PostCaptureText);
        TextView title = findViewById(R.id.LoadCaptureName);
        editScreenIntent.putExtra("dbId", dbId);
        editScreenIntent.putExtra("titleDescription", title.getText());
        editScreenIntent.putExtra("imageDescription", description.getText());
        startActivity(editScreenIntent);
    }

}
