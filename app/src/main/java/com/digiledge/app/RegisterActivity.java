package com.digiledge.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;



public class RegisterActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;
    EditText email;
    EditText pass;
    EditText num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn1=findViewById(R.id.btnSignUp);
        num=findViewById(R.id.edt_phonenumber);
        pass=findViewById(R.id.edt_pass);
        email=findViewById(R.id.edt_emailid);
        btn2=findViewById(R.id.btnlog);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        String emailHelperText = validEmail();
        String passwordHelperText = validPassword();
        String phoneHelperText = validPhone();

        if (emailHelperText != null) {
            showToast(emailHelperText);
        } else if (passwordHelperText != null) {
            showToast(passwordHelperText);
        } else if (phoneHelperText != null) {
            showToast(phoneHelperText);
        } else {
            EditText edtPhoneNumber = findViewById(R.id.edt_phonenumber);
            EditText edtPassword = findViewById(R.id.edt_pass);
            String phoneNumber = edtPhoneNumber.getText().toString();
            String password = edtPassword.getText().toString();
            String[] array1 = new String[1];
            String[] array2 = new String[1];
            array1[0] = phoneNumber;
            array2[0] = password;
            DataHolder.getInstance().setArray1(array1);
            DataHolder.getInstance().setArray2(array2);
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String validEmail() {
        String emailText = email.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address";
        }
        return null;
    }

    private String validPassword() {
        String passwordText = pass.getText().toString();
        if (passwordText.length() < 8) {
            return "Minimum 8 Character Password";
        }
        if (!passwordText.matches(".*[A-Z].*")) {
            return "Must Contain 1 Upper-case Character";
        }
        if (!passwordText.matches(".*[a-z].*")) {
            return "Must Contain 1 Lower-case Character";
        }
        if (!passwordText.matches(".*[@#\\$%^&+=].*")) {
            return "Must Contain 1 Special Character (@#\\$%^&+=)";
        }
        return null;
    }

    private String validPhone() {
        String phoneText = num.getText().toString();
        if (!phoneText.matches(".*[0-9].*")) {
            return "Must be all Digits";
        }
        if (phoneText.length() != 10) {
            return "Must be 10 Digits";
        }
        return null;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}

