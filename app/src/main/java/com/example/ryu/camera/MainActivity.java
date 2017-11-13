package com.example.ryu.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    android.hardware.Camera mCamera =null;
    SurfaceTexture mSurface = null;
    Button btn ;
    int w=0,h=0;
    ImageView img;
    //test opencv
    static{
        if(!OpenCVLoader.initDebug())
        {
            Log.e("Ryu","Opencv not loaded");
        }
        else
        {
            Log.e("Ryu","Opencv ready");
        }
    }
    //
    private android.hardware.Camera.PictureCallback mPicture = new android.hardware.Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] bytes, android.hardware.Camera camera) {
            Log.e("Ryu","data length: "+bytes.length + "w: "+w +"h: "+h);
            //logic code here
            Mat mat = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
            //display image to image view
            Bitmap bm = Bitmap.createBitmap(mat.cols(), mat.rows(),Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bm);
            img = (ImageView) findViewById(R.id.imageView);
            img.setImageBitmap(bm);
            ///

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
            mCamera = android.hardware.Camera.open();
            android.hardware.Camera.Parameters param = mCamera.getParameters();
            List<android.hardware.Camera.Size> listSize = param.getSupportedPictureSizes();
            for (android.hardware.Camera.Size size : listSize) {
                if (size.width > w || size.height > h) {
                    w = size.width;
                    h = size.height;
                }

            }
            mCamera.setPreviewTexture(mSurface);

            Log.e("Ryu","ryu here");
            mCamera.startPreview();
            Log.e("Ryu","ryu here");

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
