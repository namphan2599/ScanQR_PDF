package com.example.scanqr_pdf;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.foxit.sdk.PDFViewCtrl;
import com.foxit.sdk.common.Constants;
import com.foxit.sdk.common.Library;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.CustomAdapter;
import utils.FileDownloader;

public class PDFViewActivity_test extends AppCompatActivity {


    private FloatingActionButton fabSign;

    private ProgressDialog progressDialog;

    private PDFViewCtrl pdfViewCtrl = null;

    private String sn = "pFQYQrC0GthbID+pvFdBDLpvQbNNwldQqrinQHs5EN5SINgtZNo76g==";
    private String key = "ezJvj18mvB539NvimavWviW4/O7rg6xtzVjAGn9+7P3PRNl9eB9nmmczVHv8TzeRU+ycIq4oSkCDnKhc8ZNG26ujXyV6/sjsnDHsFcy83qW1+RWseSgsWb2LrkMeNrqkUL+/UhZXalYnCjp82oKLB4PEDizYSkM8E4KlYeOL8b7q+SEpjt2bBAGcK3JzVPsIWIgw9+BO2HqndDjuiurIYciDl+VVtZM0QotiBU2nId5TSyHZ+EdhTpbzzMMaJAH0NWCUDYr4Aj1E85yGA8MxnnR1PBoe0cv37Dk3k/3BHkZS9spvuKiMCbj3EUfu8YJjqdQ4SOrCg5KKuHkeECofK3Jur65Oiiw2TwJ0F7Up2fP6ogkQ6ZkiiSxA0dy+esRtiTdLBZGMJ1/SX4dGU8AIp5CBM4fNNpJ+S2jqepluATg7G5AQzXQO9BARTa/eiy5wx9ivwIHrQiWY/uj8anD7Yvix0iaoxzPwV/xGE0Dlc//7GkRPcoyfLwXHYXGhZly3TY3j+pKkh/Mcv9DneD0AJiVAULtgTm3a+qU3h66fx6YraqQJg6QlQleEyTmJWb0DUSpJTBYhE1bQ7ZWsr5JoIgRVWtfIfUGNADQ5cgfg9NkLt4rxIzuKx0RUDEOeAnV2v07cpQSMJp4Ltny840iLIYlNH2WwceHZ6sSDeEks+Jb30Yxagk6zM9zTkivady/zfTpilTwqjbp2jAfEc1mBYDArQRgBDFrhmwBy/r2KKIZPW5gNsoBLOvBOlAdzdZDCbAvqb9LCwSDxv+D5NhWRRePzs5fkcjTwPDACIvQLwCFAgRS0quvXv9aAgsT6IoQ6r/ee3C8u/uhGYlbFy2aTTXGA1+p0AaQc/eyNoPw3Gl5CSN6ecspTRlnpzGxVSqrNydiI/7VpmyFakZhghYnZ87faATI8zomX5qw0x3LN4eGUmKDQ7av7xUJxnWOFUPd6sk+XqVGS+t9Gnez2niO5KV0nreUsl46YtZ/SvxpaYkY+FIZN7BDJNSVVj+Av6rUoLBbsi4lQ+YmBHjwEE+Y1j4vJM2e+0GO461tN8qM2W6Ngv9CJW2W8srqnM30foUjZvwiWUQwNW0GYdgtFI9QnlrtgiI9ZyEtKkmmT+nCEj4KyfUHjR6PyvFbzNw7ExCGSGgMZVE+I+H9EDZ0b0VNIyPZ5WW6ZREhQHZAR0iBvBUmrDZkIVI+1tJwMC/VzTbOi";


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

        int errorCode = Library.initialize(sn, key);
        if (errorCode != Constants.e_ErrSuccess) {
            Toast.makeText(this, "Wrong key", Toast.LENGTH_SHORT).show();
            return;
        }

       initView();

       pdfViewCtrl.setContinuous(true);

       fabSign.setEnabled(false);
       fabSign.setBackgroundColor(Color.GRAY);


       addEvent();

        ActivityCompat.requestPermissions(PDFViewActivity_test.this, PERMISSIONS, 112);

//        get url from scan activity
//        Bundle bundle = getIntent().getExtras();

//        pdfUrl = bundle.getString("url");
        download();

    }

    private void addEvent() {

        fabSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PDFViewActivity_test.this, SignatureActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        PDFViewCtrl.IPageEventListener event = new PDFViewCtrl.IPageEventListener() {
            @Override
            public void onPageVisible(int i) {

            }

            @Override
            public void onPageInvisible(int i) {

            }

            @Override
            public void onPageChanged(int i, int i1) {
                Log.d("Page: ", String.valueOf(i1));
                Log.d("Page: ", String.valueOf(pdfViewCtrl.getPageCount()));
                if(i1 == pdfViewCtrl.getPageCount() - 1) {
                    fabSign.setEnabled(true);
                    fabSign.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onPageJumped() {

            }

            @Override
            public void onPagesWillRemove(int[] ints) {

            }

            @Override
            public void onPageWillMove(int i, int i1) {

            }

            @Override
            public void onPagesWillRotate(int[] ints, int i) {

            }

            @Override
            public void onPagesRemoved(boolean b, int[] ints) {

            }

            @Override
            public void onPageMoved(boolean b, int i, int i1) {

            }

            @Override
            public void onPagesRotated(boolean b, int[] ints, int i) {

            }

            @Override
            public void onPagesInserted(boolean b, int i, int[] ints) {

            }

            @Override
            public void onPagesWillInsert(int i, int[] ints) {

            }
        };

        pdfViewCtrl.registerPageEventListener(event);

    }

    private void initView() {
        pdfViewCtrl = findViewById(R.id.pdfViewCtrl);

        fabSign = findViewById(R.id.fabSign);

        imwSign = findViewById(R.id.imwSign);
    }

    public void download() {

        if (!hasPermissions(PDFViewActivity_test.this, PERMISSIONS)) {


            Toast t = Toast.makeText(getApplicationContext(), "You don't have write access !", Toast.LENGTH_LONG);
            t.show();

        } else {
            Log.v(TAG, "download() Method HAVE PERMISSIONS ");


            //new DownloadFile().execute("http://maven.apache.org/maven-1.x/maven.pdf", "maven.pdf");
            new DownloadTask().execute("http://maven.apache.org/maven-1.x/maven.pdf", "file.pdf");

        }

        Log.v(TAG, "download() Method completed ");

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void view() throws IOException {
        Log.v(TAG, "view() Method invoked ");

        if (!hasPermissions(PDFViewActivity_test.this, PERMISSIONS)) {

            Log.v(TAG, "download() Method DON'T HAVE PERMISSIONS ");

            Toast t = Toast.makeText(getApplicationContext(), "You don't have read access !", Toast.LENGTH_LONG);
            t.show();

        } else {
            File d = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File pdfFile = new File(d, "file.pdf");



            Log.v(TAG, "view() Method pdfFile " + pdfFile.getAbsolutePath());

            pdfViewCtrl.openDoc(pdfFile.getPath(), null);


        }

    }



    private class DownloadTask extends AsyncTask<String, Void, Void> {
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

            String fileUrl = strings[0];   // -> url
            String fileName = strings[1];  // -> file name
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            Log.v(TAG, extStorageDirectory);
            File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

            File pdfFile = new File(folder, fileName);

            if(pdfFile.exists()) {
                return null;
            }


            try {
                pdfFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            try {
                view();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        byte[] byteArray = data.getByteArrayExtra("img");
        //imwSign.setBackgroundColor(Color.YELLOW);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imwSign.setImageBitmap(bmp);

        //Toast.makeText(this, bitmap.toString(), Toast.LENGTH_SHORT).show();
    }
}