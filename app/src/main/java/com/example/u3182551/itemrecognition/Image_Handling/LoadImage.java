package com.example.u3182551.itemrecognition.Image_Handling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.u3182551.itemrecognition.EditDataActivity;
import com.example.u3182551.itemrecognition.IRListDbHelper;
import com.example.u3182551.itemrecognition.ImageHelper;
import com.example.u3182551.itemrecognition.NewIRCapture;
import com.example.u3182551.itemrecognition.R;
import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Caption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by corey on 2/04/2018.
 */




public class LoadImage extends NewIRCapture {
    protected Uri mImageUri;
    protected Bitmap mBitmap;
    protected static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 1;
    IRListDbHelper mDatabaseHelper;
    long dbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        mDatabaseHelper = new IRListDbHelper(this);


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == RESULT_OK) {
            // If image is selected successfully, set the image URI and bitmap.
            mImageUri = data.getData();
            mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                    mImageUri, getContentResolver());
        }

        if (mBitmap != null) {
            // Show the image on screen.
            ImageView imageView = (ImageView) findViewById(R.id.postloadImage);
            imageView.setImageBitmap(mBitmap);
            doAnalyse();
        }
    }

    public void doAnalyse() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());


        final AsyncTask<InputStream, String, String> visionTask = new AsyncTask<InputStream, String, String>() {
            ProgressDialog mDialog = new ProgressDialog(LoadImage.this);

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

                setContentView(R.layout.activity_post_load_image);


                mDialog.dismiss();

                AnalysisResult result = new Gson().fromJson(s, AnalysisResult.class);
                TextView textView = findViewById(R.id.PostLoadText);
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


/*

    public void SaveData(View v) {
        setContentView(R.layout.activity_post_load_image);
        TextView textView = findViewById(R.id.PostLoadText);
        StringBuilder stringBuilder = new StringBuilder();

        textView.setText(stringBuilder);

    }
*/


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    public void EditData(View v) {

                Intent editScreenIntent = new Intent(LoadImage.this, EditDataActivity.class);
                TextView description = (TextView) findViewById(R.id.PostLoadText);


                //     Integer testId =cursor.getInt(cursor.getColumnIndex("ID"));
                editScreenIntent.putExtra("dbId",dbId);
                editScreenIntent.putExtra("imageDescription",description.getText());
                startActivity(editScreenIntent);
            }

    }

