package com.example.scanqr_pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    private WebView wwPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view_test);

//        wwPdf = findViewById(R.id.ww_pdf);
//
//        WebSettings webSettings = wwPdf.getSettings();
//
//        webSettings.setJavaScriptEnabled(true);
//
//        Bundle bundle = getIntent().getExtras();
//
//        String url = bundle.getString("url");
//
//        Toast.makeText(this, url, Toast.LENGTH_LONG).show();
//
//        wwPdf.loadUrl(url);
    }
}