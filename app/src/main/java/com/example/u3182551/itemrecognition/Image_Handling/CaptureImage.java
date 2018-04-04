package com.example.u3182551.itemrecognition.Image_Handling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

public class CaptureImage extends NewIRCapture {

    protected Uri mImageUri;
    protected Bitmap mBitmap;
    protected static final int REQUEST_TAKE_PHOTO = 0;
    IRListDbHelper mDatabaseHelper;
    long dbId;

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
            mBitmap = (Bitmap) extras.get("data");
        }

        if (mBitmap != null) {
            // Show the image on screen.
            ImageView imageView = (ImageView) findViewById(R.id.postcaptureImage);
            imageView.setImageBitmap(mBitmap);
            doAnalyse();

        }
    }


    public void doAnalyse(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());


        final AsyncTask<InputStream, String, String> visionTask = new AsyncTask<InputStream, String, String>() {
            ProgressDialog mDialog = new ProgressDialog(CaptureImage.this);


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
            public void onPostExecute(String s) {

                setContentView(R.layout.activity_post_capture_image);


                mDialog.dismiss();

                AnalysisResult result = new Gson().fromJson(s, AnalysisResult.class);
                TextView textView = findViewById(R.id.PostCaptureText);
                StringBuilder stringBuilder = new StringBuilder();

                for (Caption caption : result.description.captions) {
                    stringBuilder.append(caption.text);
                    String newEntry = (caption.text);
                    addData(newEntry);

                }
                textView.setText(stringBuilder);


            }


            public void addData(String newEntry) {

                //boolean insertData = mDatabaseHelper.addData(newEntry);
                long insertId = mDatabaseHelper.addData(newEntry);
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
                TextView description = (TextView) findViewById(R.id.PostCaptureText);


                //     Integer testId =cursor.getInt(cursor.getColumnIndex("ID"));
                editScreenIntent.putExtra("dbId",dbId);
                editScreenIntent.putExtra("imageDescription",description.getText());
                startActivity(editScreenIntent);
            }
        }



