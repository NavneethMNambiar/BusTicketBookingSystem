package com.digiledge.app;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import android.Manifest;
import android.os.Environment;
import android.content.ContentValues;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;

public class QrGenActivity extends AppCompatActivity {
    private ImageView qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_gen);

        Intent intent = getIntent();

        qrcode = findViewById(R.id.qrcode);
        TextView text1 = findViewById(R.id.textView1);
        TextView text2 = findViewById(R.id.textView2);
        TextView text3 = findViewById(R.id.textView3);
        TextView text4 = findViewById(R.id.textView4);

        String start = intent.getStringExtra("firstString");
        String destination = intent.getStringExtra("secondString");
        String currentDate = intent.getStringExtra("currentDate");

       String tid=text1.getText().toString()+(Math.abs(123456789 + (int)(Math.random() * (999999999 - 123456789))));
       String source = text2.getText().toString()+start;
       String dest = text3.getText().toString()+destination;
       String date = text4.getText().toString()+currentDate;

       text1.setText(tid);
        text2.setText(source);
        text3.setText(dest);
        text4.setText(date);


        String data = text1.getText().toString().trim();
        generateQRCode(data);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, 1);

        View imageView = findViewById(R.id.layoutid);

        imageView.postDelayed(() -> captureScreen(imageView), 3000);
    }

    private void generateQRCode(String data) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            com.google.zxing.common.BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrcode.setImageBitmap(bmp);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void captureScreen(View view) {
        Bitmap screenshot = getScreenShotFromView(view);
        if (screenshot != null) {
            saveMediaToStorage(screenshot);
        }
    }

    private Bitmap getScreenShotFromView(View view) {
        Bitmap screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenshot);
        view.draw(canvas);
        return screenshot;
    }

    private void saveMediaToStorage(Bitmap bitmap) {
        String filename = System.currentTimeMillis() + ".jpg";
        OutputStream fos = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                if (imageUri != null) {
                    fos = resolver.openOutputStream(imageUri);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            File imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = new File(imagesDir, filename);
            try {
                fos = new FileOutputStream(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (fos != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Toast.makeText(this, "Receipt Saved to Gallery!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), BookingActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}

