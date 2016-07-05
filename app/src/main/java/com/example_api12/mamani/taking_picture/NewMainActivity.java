package com.example_api12.mamani.taking_picture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by mamani on 30/06/16.
 */
public class NewMainActivity extends AppCompatActivity {
static final int PICK_CONTACT_REQUEST = 1;
private TextView text;
    File Filepath;
    Button cancelButton;
    Button confirmButtom;
   private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmain);

        text = (TextView) findViewById(R.id.Textview);

        image = (ImageView) findViewById(R.id.imageView);

        cancelButton = (Button) findViewById(R.id.button2);
        cancelButton.setVisibility(View.GONE);

        confirmButtom = (Button) findViewById(R.id.button3);
        confirmButtom.setVisibility(View.GONE);

    }

    public void taking_foto(View v)
    {
     // startActivity(new Intent(this,MainActivity.class));
        //startActivityForResult
        Intent pickContactIntent = new Intent(this,MainActivity.class);
        startActivityForResult(pickContactIntent,PICK_CONTACT_REQUEST);

    }

    public void canceling_manouver(View v)
    {
        image.setImageBitmap(null);
        Filepath.delete();
        cancelButton.setVisibility(View.GONE);
        confirmButtom.setVisibility(View.GONE);
    }

    public void confirButttom(View v)
    {
        cancelButton.setVisibility(View.GONE);
        confirmButtom.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String result=data.getStringExtra("result");
                text.setText(result);

                File imfFile = new File(result);
                Filepath = imfFile;
                Bitmap mybitma = BitmapFactory.decodeFile(imfFile.getAbsolutePath());

                image.setImageBitmap(mybitma);
                cancelButton.setVisibility(View.VISIBLE);
                confirmButtom.setVisibility(View.VISIBLE);
            }
            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }
    }
}
