package com.example.u3182551.itemrecognition;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete,btnCancel;
    private EditText edit_description;
    private EditText edit_title;

    IRListDbHelper mDatabaseHelper;
    private String description;
    private String selectedName;
    private long selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ir_edit);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        edit_description = (EditText) findViewById(R.id.edit_description);
        edit_title = (EditText) findViewById(R.id.edit_title);
        mDatabaseHelper = new IRListDbHelper(this);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();
        Bundle bundle = getIntent().getExtras();
        description = bundle.getString("imageDescription");
        //now get the itemID we passed as an extra
        selectedID = bundle.getLong("dbId"); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");

        //set the text to show the current selected name
        edit_description.setText(description);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edit_description.getText().toString();
                if(!item.equals("")){
                    mDatabaseHelper.updateName(item,selectedID,description);
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
}


