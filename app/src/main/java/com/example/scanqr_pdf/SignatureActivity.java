package com.example.scanqr_pdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.hardware.Camera;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import utils.PaintView;

public class SignatureActivity extends AppCompatActivity implements SurfaceHolder.Callback{


    private final String VIDEO_PATH_NAME = "/mnt/sdcard/VGA_30fps_512vbrate.mp4"; // config file mp4 here

    Button btnClear, btnOk;
    PaintView paintView;


    private MediaRecorder mMediaRecorder;
    private Camera mCamera;
    TextView textView;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private View mToggleButton;
    private boolean mInitSuccesful;
    int time = 3;

    CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {

        public void onTick(long millisUntilFinished) {
            textView.setText(time-- + "");
        }

        public void onFinish() {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = viewToImage(paintView);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            Intent intent = new Intent();
            intent.putExtra("img", stream.toByteArray());
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        mSurfaceView = findViewById(R.id.surfaceView);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        textView = findViewById(R.id.txtTime);

        mToggleButton = findViewById(R.id.toggleRecordingButton);
        // toggle video recording
        mToggleButton.setOnClickListener(v -> {
            if (((ToggleButton)v).isChecked()) {
                mMediaRecorder.start();
                countDownTimer.start();
            }
        });
        paintView = findViewById(R.id.pwSign);


    }
    private void initRecorder(Surface surface) throws IOException {
        // It is very important to unlock the camera before doing setCamera
        // or it will results in a black preview
        if(mCamera == null) {
            mCamera = Camera.open();
            mCamera.unlock();
        }

        if(mMediaRecorder == null)  mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setPreviewDisplay(surface);
        mMediaRecorder.setCamera(mCamera);

        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        //       mMediaRecorder.setOutputFormat(8);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoSize(640, 480);
        mMediaRecorder.setOutputFile(VIDEO_PATH_NAME);

        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            // This is thrown if the previous calls are not called with the
            // proper order
            e.printStackTrace();
        }

        mInitSuccesful = true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        try {
            if(!mInitSuccesful)
                initRecorder(mHolder.getSurface());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        shutdown();
    }

    private void shutdown() {
        // Release MediaRecorder and especially the Camera as it's a shared
        // object that can be used by other applications
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mCamera.release();

        // once the objects have been released they can't be reused
        mMediaRecorder = null;
        mCamera = null;
    }

    private Bitmap viewToImage(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
//        Drawable bgDrawable = view.getBackground();
//        if (bgDrawable != null)
//            bgDrawable.draw(canvas);
//        else
//            canvas.drawColor(Color.WHITE);
        view.draw(canvas);

        return returnedBitmap;
    }

}