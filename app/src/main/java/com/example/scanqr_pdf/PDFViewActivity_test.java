package com.example.scanqr_pdf;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.FileDownloader;

public class PDFViewActivity_test extends AppCompatActivity {


    private FloatingActionButton fabSign;

    private ProgressDialog progressDialog;

    private PDFView pdfView;

    private ImageView imwSign;

    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String TAG = "PDFViewActivity_test";
    private String pdfUrl;


    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view_test);
        setTitle("Pdf viewer");
        pdfView = findViewById(R.id.pdfView);

        fabSign = findViewById(R.id.fabSign);

        imwSign = findViewById(R.id.imw_sign);

        fabSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PDFViewActivity_test.this, SignatureActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        ActivityCompat.requestPermissions(PDFViewActivity_test.this, PERMISSIONS, 112);

        Bundle bundle = getIntent().getExtras();

        pdfUrl = bundle.getString("url");

        download(pdfUrl);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "into return");
        if(requestCode == 0) {
            Log.v(TAG, "req code");
            if(resultCode == RESULT_OK) {
                Log.v(TAG, "result code");
                byte[] byteArray = data.getByteArrayExtra("img");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imwSign.setImageBitmap(bmp);
                Toast.makeText(this, "Nhận rùi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void download(String url) {
        Log.v(TAG, "download() Method invoked ");

        if (!hasPermissions(PDFViewActivity_test.this, PERMISSIONS)) {

            Log.v(TAG, "download() Method DON'T HAVE PERMISSIONS ");

            Toast t = Toast.makeText(getApplicationContext(), "You don't have write access !", Toast.LENGTH_LONG);
            t.show();

        } else {
            Log.v(TAG, "download() Method HAVE PERMISSIONS ");


            //new DownloadFile().execute("http://maven.apache.org/maven-1.x/maven.pdf", "maven.pdf");

            String pattern = "\\w*(\\.pdf)$";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(pdfUrl.trim());
            String fileName = "";

            while (m.find()) {
                fileName = m.group();
            }

            new DownloadTask().execute(url, fileName);

        }

        Log.v(TAG, "download() Method completed ");

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void view(String fileName) throws IOException {
        Log.v(TAG, "view() Method invoked ");

        if (!hasPermissions(PDFViewActivity_test.this, PERMISSIONS)) {

            Log.v(TAG, "download() Method DON'T HAVE PERMISSIONS ");

            Toast t = Toast.makeText(getApplicationContext(), "You don't have read access !", Toast.LENGTH_LONG);
            t.show();

        } else {
            File d = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            File pdfFile = new File(d, fileName);



            Log.v(TAG, "view() Method pdfFile " + pdfFile.getAbsolutePath());

            pdfView.fromFile(pdfFile).load();


        }
        Log.v(TAG, "view() Method completed ");

    }



    private class DownloadTask extends AsyncTask<String, Void, Void> {

        String fileUrl;
        String fileName;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PDFViewActivity_test.this);
            progressDialog.setMessage("Đang tải...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.v(TAG, "doInBackground() Method invoked ");

            fileUrl = strings[0];   // -> url
            fileName = strings[1];  // -> file name
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            Log.v(TAG, extStorageDirectory);
            //File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

            File pdfFile = new File(folder, fileName);

            if(pdfFile.exists()) {
                return null;
            }
            Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsolutePath());
            Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsoluteFile());

            try {
                pdfFile.createNewFile();
                Log.v(TAG, "doInBackground() file created" + pdfFile);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground() error" + e.getMessage());
                Log.e(TAG, "doInBackground() error" + e.getStackTrace());


            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            Log.v(TAG, "doInBackground() file download completed");

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            try {
                view(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}