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
    File Filepath;;
   private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmain);

        image = (ImageView) findViewById(R.id.imageView);

    }

    public void taking_foto(View v)
    {
     // startActivity(new Intent(this,MainActivity.class));
        //startActivityForResult

        Intent pickContactIntent = new Intent(this,MainActivity.class);
        startActivityForResult(pickContactIntent,PICK_CONTACT_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String result=data.getStringExtra("result");


                File imfFile = new File(result);
                Filepath = imfFile;

                //Bitmap mybitma = BitmapFactory.decodeFile(imfFile.getAbsolutePath());
               // image.setImageBitmap(mybitma);

                setPiv(result);

            }
            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }
    }

    private void setPiv(String mCurrentPhotoPath)
    {
        int targetW = image.getWidth();
        int targetH = image.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW,photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        image.setImageBitmap(bitmap);
    }
}
