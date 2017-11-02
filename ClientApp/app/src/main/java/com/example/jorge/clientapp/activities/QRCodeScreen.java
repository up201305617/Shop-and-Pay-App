package com.example.jorge.clientapp.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.clientapp.entities.Product;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import com.example.jorge.clientapp.R;

public class QRCodeScreen extends AppCompatActivity {
    ImageView qrCodeImageview;
    TextView errorTv;
    String uuid;
    String errorMsg = "";
    public final static int WIDTH=500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView titleTv;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_screen);
        qrCodeImageview=(ImageView) findViewById(R.id.img_qr_code_image);
        errorTv = (TextView) findViewById(R.id.error);
        titleTv = (TextView) findViewById(R.id.title);
        Bundle bundle = getIntent().getExtras();
        uuid = (String) bundle.get("UUID");
        titleTv.setTextSize(18);
        titleTv.setTextColor(Color.BLACK);
        titleTv.setText("Your purchase has been successful. Present the following QRCode to a printer.");

        Thread t = new Thread(new Runnable() {  // do the creation in a new thread to avoid ANR Exception
            public void run() {
                final Bitmap bitmap;
                try {
                    bitmap = encodeAsBitmap(uuid);
                    runOnUiThread(new Runnable() {  // runOnUiThread method used to do UI task in main thread.
                        @Override
                        public void run() {
                            qrCodeImageview.setImageBitmap(bitmap);
                        }
                    });
                }
                catch (WriterException e) {
                    errorMsg += "\n" + e.getMessage();
                }
                if (!errorMsg.isEmpty())
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            errorTv.setText(errorMsg);
                        }
                    });
            }
        });
        t.start();
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        }
        catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.colorPrimary):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }
}
