package com.example_api12.mamani.taking_picture;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by mamani on 30/06/16.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;


   public CameraPreview(Context context,Camera camera)
   {
       super(context);
       mCamera = camera;
       mHolder = getHolder();
       mHolder.addCallback(this);
       mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

   }

    public void surfaceCreated(SurfaceHolder holder)
    {
        try{
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
           // Log.d(TAG,"Error setting camera preview"+ e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if(mHolder.getSurface() == null)
        {
            return;
        }

        try {
            mCamera.stopPreview();
        }catch (Exception e)
         {}

        //reformating changes here

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
