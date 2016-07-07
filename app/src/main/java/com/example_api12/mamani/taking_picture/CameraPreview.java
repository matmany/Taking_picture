package com.example_api12.mamani.taking_picture;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
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

       setFocusable(true);
       setFocusableInTouchMode(true);

   }

    public void surfaceCreated(SurfaceHolder holder)
    {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(parameters);

        try{
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mCamera.autoFocus(null);
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

       Camera.Parameters parameters = mCamera.getParameters();
       parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
       mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            mCamera.autoFocus(null);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("down", "focusing now");

            mCamera.autoFocus(null);
        }

        return true;
    }


}
