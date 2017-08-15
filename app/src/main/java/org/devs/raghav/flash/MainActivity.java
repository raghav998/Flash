package org.devs.raghav.flash;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;


public class MainActivity extends AppCompatActivity {

    private CameraManager camera;
    private String cameraId;
    boolean hasFlash,isFlashOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button flashButton=(Button)findViewById(R.id.FlashButtonID);
        hasFlash=false;
        isFlashOn=false;
        checkFlash();
        getCamera();

        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFlashOn)
                    turnOffFlash();
                else
                    turnOnFlash();
            }
        });

    }


    public void checkFlash()
    {
        hasFlash=getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(!hasFlash) {
            myToast("Sorry! Your Device has No Flash Light");
            finish();
        }
    }

    public void turnOnFlash()
    {
       try
       {
           camera.setTorchMode(cameraId,true);
           myToast("Flash Turned On");
       }
       catch (Exception e)
       {
           e.printStackTrace();
           myToast("Error while turning On flash");
       }
        isFlashOn=true;
    }

    public void turnOffFlash()
    {
        try
        {
            camera.setTorchMode(cameraId,false);
            myToast("Flash Turned Off");
        }
        catch(Exception e)
        {
            myToast("Error while Turning off Flash");
            e.printStackTrace();
        }
        isFlashOn=false;
    }

    public void getCamera()
    {
        camera=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try
        {
            cameraId=camera.getCameraIdList()[0];
        }
        catch(CameraAccessException e)
        {
            myToast("Error while opening camera");
        }
    }

    public void myToast(String s)
    {
        Toast.makeText(this.getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
