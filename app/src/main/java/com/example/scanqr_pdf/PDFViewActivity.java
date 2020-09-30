package com.example.scanqr_pdf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PDFViewActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    WebView wwPdfContent;
    ImageButton imBtnSign;
    Button btnSubmit;

    View loading;

    int shortAnimationDuration;
//
//    private void crossFade() {
//        wwPdfContent.setAlpha(0f);
//        wwPdfContent.setVisibility(View.VISIBLE);
//
//        wwPdfContent.animate()
//                    .alpha(1f)
//                    .setDuration(shortAnimationDuration)
//                    .setListener(null);
//
//        loading.animate()
//                .alpha(0f)
//                .setDuration(shortAnimationDuration)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        loading.setVisibility(View.VISIBLE);
//                        wwPdfContent.setVisibility(View.VISIBLE);
//                    }
//                });
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_p_d_f_view);
//
//        loading = findViewById(R.id.loading_spinner);
//
//        btnSubmit = findViewById(R.id.btnSubmit);
//
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//
//                 String url = "https://jsonplaceholder.typicode.com/posts/1";
//
//                 RequestQueue queue = Volley.newRequestQueue(PDFViewActivity.this);
//
//
//                 // Request a string response from the provided URL.
//                 StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                         new Response.Listener<String>() {
//                             @Override
//                             public void onResponse(String response) {
//                                 // Display the first 500 characters of the response string.
//                                 try {
//                                     JSONObject reader = new JSONObject(response);
//                                     String resultFromJson = reader.getString("body");
//                                     Toast.makeText(PDFViewActivity.this, resultFromJson, Toast.LENGTH_SHORT).show();
//                                 } catch (JSONException e) {
//                                     e.printStackTrace();
//                                 }
//
//                             }
//                         }, new Response.ErrorListener() {
//                     @Override
//                     public void onErrorResponse(VolleyError error) {
//                         Toast.makeText(PDFViewActivity.this, "That didn't work!", Toast.LENGTH_SHORT).show();
//                     }
//                 });
//
//// Add the request to the RequestQueue.
//                 queue.add(stringRequest);
//             }
//         });
//
//        imBtnSign = findViewById(R.id.imBtnSign);
//
//        imBtnSign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                  if (intent.resolveActivity(getPackageManager()) != null) {
//                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//                }
//            }
//        });
//
//
//
//        wwPdfContent = findViewById(R.id.ww_pdf_content);
//        wwPdfContent.getSettings().setSupportZoom(true);
//        wwPdfContent.getSettings().setJavaScriptEnabled(true);
//        wwPdfContent.setVisibility(View.GONE);
//        Bundle bundle = getIntent().getExtras();
//        String pdfUrl = bundle.getString("url");
//
//        wwPdfContent.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                crossFade();
//            }
//        });
//
//        shortAnimationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);
//
//        if(!pdfUrl.isEmpty()) {
//            wwPdfContent.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfUrl);
//        } else {
//            Toast.makeText(this, "Khong tim thay url !!", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imBtnSign.setImageBitmap(imageBitmap);
//        }
//    }
}