package com.example.scanqr_pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import utils.PaintView;

public class SignatureActivity extends AppCompatActivity {

    Button btnClear, btnOk;
    PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        paintView = new PaintView(this, null);


        btnOk = findViewById(R.id.btnOK);
        btnClear = findViewById(R.id.btnClear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Canvas clearCanvas = new Canvas();
                clearCanvas.drawColor(Color.WHITE);
                paintView.draw(clearCanvas);
            }
        });



        //customCanvas.draw(new Canvas().drawColor(Color.WHITE));
    }
}