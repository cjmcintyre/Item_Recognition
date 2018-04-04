package com.example.u3182551.itemrecognition;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.u3182551.itemrecognition.imagehandling.CaptureImage;
import com.example.u3182551.itemrecognition.imagehandling.LoadImage;

@SuppressWarnings("ALL")
public class EditDataActivity extends AppCompatActivity {

    private IRListDbHelper mDatabaseHelper;
    private EditText edit_description;
    private EditText edit_title;
    private String description;
    private long selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ir_edit);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnCancel = findViewById(R.id.btnCancel);
        edit_description = findViewById(R.id.edit_description);
        edit_title = findViewById(R.id.edit_title);
        mDatabaseHelper = new IRListDbHelper(this);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();
        Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        description = bundle.getString("imageDescription");
        String title = bundle.getString("titleDescription");

        //Get Details and pass as extra
        selectedID = bundle.getLong("dbId");
        String selectedName = receivedIntent.getStringExtra("information");
        String selectedTitle = receivedIntent.getStringExtra("title");

        //set the text to show the current selected name
        edit_description.setText(description);
        edit_title.setText(title);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edit_description.getText().toString();
                String item2 = edit_title.getText().toString();
                if (!item2.equals("")) {
                    mDatabaseHelper.updateName(item, item2, selectedID, description);
                    toastMessage("Data Successfully Saved");
                    Intent backtoListView = new Intent(EditDataActivity.this, ListViewActivity.class);
                    startActivity(backtoListView);

                }else{
                    toastMessage("Data cannot be empty");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedID,description);
                edit_description.setText("");
                toastMessage("Deleted from Database");
                Intent backtoListView = new Intent(EditDataActivity.this, ListViewActivity.class);
                startActivity(backtoListView);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent backtoListView = new Intent(EditDataActivity.this, ListViewActivity.class);
                startActivity(backtoListView);
                toastMessage("No Changes made");
            }
        });

    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    //Menu Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.commonmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.previous_reco:
                Intent intent = new Intent(this, ListViewActivity.class);
                startActivity(intent);
                return true;
            case R.id.capture_image:
                Intent intent1 = new Intent(this, CaptureImage.class);
                startActivity(intent1);
                return true;
            case R.id.load_image:
                Intent intent2 = new Intent(this, LoadImage.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //End Menu Bar
}


