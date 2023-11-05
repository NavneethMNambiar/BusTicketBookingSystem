package com.digiledge.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView reg;
    private TextInputEditText edtPhone;
    private TextInputEditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        edtPhone = findViewById(R.id.phone);
        edtPassword = findViewById(R.id.edtPassword);
        reg = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edtPhone.getText().toString();
                String password = edtPassword.getText().toString();
                boolean arraysMatch=false;
                String[] array1 = DataHolder.getInstance().getArray1();
                String[] array2 = DataHolder.getInstance().getArray2();
                if (array1.length>0 && array2.length>0) {
                    for (int i = 0; i < array1.length; i++) {
                        if (array1[i].equals(phone) && array2[i].equals(password)) {
                            arraysMatch = true; // Both strings match at the same index
                            break; // Exit the loop as we found a match
                        }
                    }
                    if (arraysMatch) {

                        Intent intent = new Intent(MainActivity.this, BookingActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
                // Check if the credentials are in the user HashMap
                else {
                    // If not found, display a toast
                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reg.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

    }

}