package com.example_api12.mamani.taking_picture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Camera mCamera;
  private CameraPreview mPreview;
    public static final int MEDIA_TYPE_IMAGE =1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int mPreviewState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCamera = getCameraInstance();

        mPreview = new CameraPreview(this,mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);


        final Button captureButton = (Button) findViewById(R.id.photo_button);
        captureButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        mCamera.takePicture(null,null,mPicture);
                        captureButton.setClickable(false);

                    }
                }
        );


    }

    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

            //if(pictureFile == null)
            // return;

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(bytes);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",pictureFile.toString());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();

        }


    };

    //checking the camera
    private boolean checkCamera(Context cont)
    {
        if(cont.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            return true;
        }

        else{
            return false;
        }
    }

    public static Camera getCameraInstance()
    {
     Camera c = null;

        try{
            c =Camera.open();
        }
        catch (Exception e)
        {

        }
        return c;
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera()
    {
        if(mCamera != null)
        {
            mCamera.release();
            mCamera = null;
        }
    }

    /////// SAVING......
    private static Uri getOutputMediaFileUri(int type)
    {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type)
    {
        //File mediaStoregeDir = new File(Environment.getExternalStoragePublicDirectory(
          //      Environment.DIRECTORY_PICTURES),"MyCameraApp");
        File mediaStoregeDir =  Environment.getExternalStoragePublicDirectory("GSA/imagens");

        if(!mediaStoregeDir.exists()){
            if(!mediaStoregeDir.mkdirs()){
                //faile to create directory;
                return null;
            }
        }


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type == MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(mediaStoregeDir.getPath()+File.separator+"IMG_"+timeStamp + ".jpg");
        }
        else{
            return null;
        }
        return mediaFile;
    }

}
