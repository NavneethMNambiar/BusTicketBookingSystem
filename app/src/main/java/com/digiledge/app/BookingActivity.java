package com.digiledge.app;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {


    private CharSequence amt;
    TextView start;
    private ActivityResultLauncher<Intent> gPayLauncher;

    TextView destination;
    Spinner spinner1;
    Spinner spinner2;
    TextView numb;
    private int num = 0;
    TextView fare;
    Button button;
    ImageButton adder;
    ImageButton subtractor;
    private boolean isAllFieldsChecked = false;
    public static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    String amount;
    String name = "SANJAY JOHNY";
    String upiId = "sanjayjohny2355@okicici";
    String transactionNote = "test 1";
    String status;
    Uri uri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        adder = findViewById(R.id.adder);
        spinner1 = findViewById(R.id.spinner1);
        fare = findViewById(R.id.fare);
        spinner2 = findViewById(R.id.spinner2);
        start = findViewById(R.id.start);
        destination = findViewById(R.id.destination);
        numb = findViewById(R.id.numb);
        button = findViewById(R.id.button);
        subtractor = findViewById(R.id.substractor);
        adder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num < 60) {
                    num++;
                }
                numb.setText(String.valueOf(num));
                if (!start.getText().toString().equals(destination.getText().toString())) {
                    fare.setText(String.valueOf(num * 10));
                }
            }
        });

        subtractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num > 0) {
                    num--;
                    numb.setText(String.valueOf(num));
                    if (!start.getText().toString().equals(destination.getText().toString())) {
                        fare.setText(String.valueOf(num * 10));
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = checkAllFields();
                if (isAllFieldsChecked) {
                    amount = fare.getText().toString();
                    uri = getUpiPaymentUri(name, upiId, transactionNote, amount);
                    payWithGPay();

                } else {
                    Toast.makeText(BookingActivity.this, "All fields are necessary", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final String[] places = {
                "Aluva", "Pulinchodu", "Companypady", "Ambattukavu", "Muttom", "Kalamassery",
                "Cochin University", "Pathadipalam", "Edapally", "Changampuzha Park", "Palarivattom", "JLN Stadium",
                "Kaloor", "Town Hall", "M.G Road", "Maharaja's College", "Ernakulam south", "Kadavanthra", "Elamkulam",
                "Vyttila", "Thaikoodam", "Petta", "Vadakkekotta", "SN Junction"
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, places);

        spinner1.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start.setText(places[position]);
                if (start.getText().toString().equals(destination.getText().toString())) {
                    fare.setText("0");
                } else {
                    fare.setText(String.valueOf(num * 10));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner2.setAdapter(arrayAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destination.setText(places[position]);
                if (start.getText().toString().equals(destination.getText().toString())) {
                    fare.setText("0");
                } else {
                    fare.setText(String.valueOf(num * 10));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        gPayLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            Intent intent = new Intent(BookingActivity.this, QrGenActivity.class);
            intent.putExtra("firstString", start.getText().toString());
            intent.putExtra("secondString", destination.getText().toString());
            intent.putExtra("currentDate", currentDate);
            startActivity(intent);
            finish();
        });
    }

    private boolean checkAllFields() {

        String spin1data = spinner1.getSelectedItem().toString();
        String spin2data = spinner2.getSelectedItem().toString();

        if (spin1data.equals(spin2data)) {
            return false;
        }

        if (numb.getText().toString().equals("0")) {
            return false;
        }

        // After all validation, return true.
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }

    private static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static Uri getUpiPaymentUri(String name, String upiId, String transactionNote, String amount) {
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .path("/pay")
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", transactionNote)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();
    }

    private void payWithGPay() {
        if (!isAppInstalled(this, GOOGLE_PAY_PACKAGE_NAME)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
            gPayLauncher.launch(intent);
        } else {
            Toast.makeText(BookingActivity.this, "Please Install GPay", Toast.LENGTH_SHORT).show();
        }
    }

}


