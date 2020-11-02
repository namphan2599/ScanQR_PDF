package com.example.scanqr_pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.foxit.sdk.PDFException;
import com.foxit.sdk.PDFViewCtrl;
import com.foxit.sdk.common.Constants;
import com.foxit.sdk.common.Library;
import com.foxit.sdk.pdf.PDFDoc;
import com.foxit.sdk.pdf.PDFPage;
import com.foxit.sdk.pdf.annots.Annot;
import com.foxit.sdk.pdf.interform.Control;
import com.foxit.sdk.pdf.interform.Form;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class PdfViewActivity_sdk extends AppCompatActivity {

    private PDFViewCtrl pdfViewCtrl = null;
    private FloatingActionButton signBtn;

    private PDFDoc pdfDoc;

    private String pdfFileName = "noname.pdf";

    private String sn = "pFQYQrC0GthbID+pvFdBDLpvQbNNwldQqrinQHs5EN5SINgtZNo76g==";
    private String key = "ezJvj18mvB539NvimavWviW4/O7rg6xtzVjAGn9+7P3PRNl9eB9nmmczVHv8TzeRU+ycIq4oSkCDnKhc8ZNG26ujXyV6/sjsnDHsFcy83qW1+RWseSgsWb2LrkMeNrqkUL+/UhZXalYnCjp82oKLB4PEDizYSkM8E4KlYeOL8b7q+SEpjt2bBAGcK3JzVPsIWIgw9+BO2HqndDjuiurIYciDl+VVtZM0QotiBU2nId5TSyHZ+EdhTpbzzMMaJAH0NWCUDYr4Aj1E85yGA8MxnnR1PBoe0cv37Dk3k/3BHkZS9spvuKiMCbj3EUfu8YJjqdQ4SOrCg5KKuHkeECofK3Jur65Oiiw2TwJ0F7Up2fP6ogkQ6ZkiiSxA0dy+esRtiTdLBZGMJ1/SX4dGU8AIp5CBM4fNNpJ+S2jqepluATg7G5AQzXQO9BARTa/eiy5wx9ivwIHrQiWY/uj8anD7Yvix0iaoxzPwV/xGE0Dlc//7GkRPcoyfLwXHYXGhZly3TY3j+pKkh/Mcv9DneD0AJiVAULtgTm3a+qU3h66fx6YraqQJg6QlQleEyTmJWb0DUSpJTBYhE1bQ7ZWsr5JoIgRVWtfIfUGNADQ5cgfg9NkLt4rxIzuKx0RUDEOeAnV2v07cpQSMJp4Ltny840iLIYlNH2WwceHZ6sSDeEks+Jb30Yxagk6zM9zTkivady/zfTpilTwqjbp2jAfEc1mBYDArQRgBDFrhmwBy/r2KKIZPW5gNsoBLOvBOlAdzdZDCbAvqb9LCwSDxv+D5NhWRRePzs5fkcjTwPDACIvQLwCFAgRS0quvXv9aAgsT6IoQ6r/ee3C8u/uhGYlbFy2aTTXGA1+p0AaQc/eyNoPw3Gl5CSN6ecspTRlnpzGxVSqrNydiI/7VpmyFakZhghYnZ87faATI8zomX5qw0x3LN4eGUmKDQ7av7xUJxnWOFUPd6sk+XqVGS+t9Gnez2niO5KV0nreUsl46YtZ/SvxpaYkY+FIZN7BDJNSVVj+Av6rUoLBbsi4lQ+YmBHjwEE+Y1j4vJM2e+0GO461tN8qM2W6Ngv9CJW2W8srqnM30foUjZvwiWUQwNW0GYdgtFI9QnlrtgiI9ZyEtKkmmT+nCEj4KyfUHjR6PyvFbzNw7ExCGSGgMZVE+I+H9EDZ0b0VNIyPZ5WW6ZREhQHZAR0iBvBUmrDZkIVI+1tJwMC/VzTbOi";
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view_sdk);

        int errorCode = Library.initialize(sn, key);
        if (errorCode != Constants.e_ErrSuccess) {
            Toast.makeText(this, "Wrong key", Toast.LENGTH_SHORT).show();
            return;
        }




        initView();

        signBtn.setEnabled(false);
        signBtn.setBackgroundColor(Color.GRAY);

        File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File pdfFile = new File(folder, pdfFileName);

        path = pdfFile.getAbsolutePath();

        Log.d("File location", path);

        pdfViewCtrl.setContinuous(true);
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
                    signBtn.setEnabled(true);
                    signBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
        pdfViewCtrl.openDoc(path, null);

        testCount();
    }

    private void initView() {
        pdfViewCtrl = findViewById(R.id.pdfViewCtrl);
        signBtn = findViewById(R.id.signBtn);
    }

    private void testCount() {

        try {
            pdfDoc = new PDFDoc(path);

            pdfDoc.load(null);
//            Log.d("PDFDoc", String.valueOf(pdfDoc.getPageCount()));
//
            Form form = new Form(pdfDoc);


            //got the controls but only got 2 instead 4
            PDFPage pdfPage = pdfDoc.getPage(0);
            int controlCount = form.getControlCount(pdfPage);

            int annotCount = pdfPage.getAnnotCount();

            Log.d("Annot count", String.valueOf(annotCount));


            for(int i = 0; i < annotCount; ++i) {
                Annot annot = pdfPage.getAnnot(i);
                Log.d("Annot Postion",
                        "Left: " + annot.getRect().getLeft() +
                            " Right: " + annot.getRect().getRight() +
                            " Top: " + annot.getRect().getTop() +
                            " Bottom: " + annot.getRect().getBottom()
                            );
            }

            for(int i = 0; i < controlCount; ++i) {

                Log.d("Control Info", i+"");
                Control control = form.getControl(pdfPage, i);
                Log.d("Control Info", control.getField().getValue());
            }

            Log.d("Control count", String.valueOf(controlCount));

        } catch (PDFException e) {
            Log.d("PDFDoc", "Cannot load the pdf file");
            e.printStackTrace();
        }



    }
}