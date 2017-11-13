package com.example.ryu.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    android.hardware.Camera mCamera =null;
    SurfaceTexture mSurface = null;
    Button btn ;
    private android.hardware.Camera.PictureCallback mPicture = new android.hardware.Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] bytes, android.hardware.Camera camera) {
            Log.e("Ryu"," here 2");
            Log.e("Ryu","data length: "+bytes.length);
            //
//            mCamera.takePicture(null,null,mPicture);
        }

    };
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            Log.e("Ryu","Co camera");
            return true;
        } else {
            Log.e("Ryu","Khong Co camera");
            // no camera on this device
            return false;
        }
    }

    public void startCamera(Context context)
    {
         mSurface = new SurfaceTexture(10);
        try{
            Log.e("Ryu","ryu here1");
            mCamera = android.hardware.Camera.open();
            Log.e("Ryu","ryu here");
            mCamera.setPreviewTexture(mSurface);
            Log.e("Ryu","ryu here");
            mCamera.startPreview();
            Log.e("Ryu","ryu here");
//            mCamera.takePicture(null,null,mPicture);
//            mCamera.setPreviewCallback(new android.hardware.Camera.PreviewCallback() {
//                @Override
//                public void onPreviewFrame(byte[] bytes, android.hardware.Camera camera) {
//                    Log.e("Ryu","data length: "+bytes.length);
//                }
//            });
        }
        catch (Exception ex)
        {
            Log.e("Ryu"," loi"+ex.getMessage());
        }

    }

    public void startCamera2(Context context)
    {
        CameraManager camera = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        checkCameraHardware(this);
        final Context mContext = this;
        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera(mContext);
                mCamera.takePicture(null,null,mPicture);
            }
        });
    }
}
