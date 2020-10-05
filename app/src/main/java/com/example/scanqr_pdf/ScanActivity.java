package com.example.scanqr_pdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        }
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v("SCAN_RS", rawResult.getText());
        Log.v("SCAN_RS", rawResult.getBarcodeFormat().toString());

        String pdfUrl = rawResult.getText();
        Intent intent = new Intent(ScanActivity.this, WebViewActivity.class);

        intent.putExtra("url", pdfUrl);

        startActivity(intent);

//        String pattern = "\\w*(\\.pdf)$";
//
//        Pattern r = Pattern.compile(pattern);
//
//        // Now create matcher object.
//        Matcher m = r.matcher(pdfUrl.trim());
//
//        String fileName = "";
//        while (m.find()) {
//            fileName = m.group();
//        }
//
//        Log.d("pdf url", pdfUrl);
//        Toast.makeText(this, pdfUrl, Toast.LENGTH_LONG).show();

        //mScannerView.resumeCameraPreview(this);
    }
}