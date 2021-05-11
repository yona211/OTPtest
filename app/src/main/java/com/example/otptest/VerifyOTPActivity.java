package com.example.otptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    EditText etCode1, etCode2, etCode3, etCode4, etCode5, etCode6;
    TextView tvMobile;
    Button btnVerify, btnResendOTP;
    String verificationId, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        btnResendOTP = (Button)findViewById(R.id.btnResendOTP);
        tvMobile = (TextView)findViewById(R.id.tvMobile);
        tvMobile.setText(String.format(getIntent().getStringExtra("mobile")));
        id = getIntent().getStringExtra("id");

        etCode1 = (EditText)findViewById(R.id.etCode1);
        etCode2 = (EditText)findViewById(R.id.etCode2);
        etCode3 = (EditText)findViewById(R.id.etCode3);
        etCode4 = (EditText)findViewById(R.id.etCode4);
        etCode5 = (EditText)findViewById(R.id.etCode5);
        etCode6 = (EditText)findViewById(R.id.etCode6);

        setupOTPInputs();

        // TODO Add an option for reSend the OTP
        verificationId = getIntent().getStringExtra("verificationId");
        btnVerify = (Button)findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Clear and give another chance if someone enter wrong pass
                if(etCode1.getText().toString().trim().isEmpty() || etCode2.getText().toString().trim().isEmpty() || etCode3.getText().toString().trim().isEmpty() || etCode4.getText().toString().trim().isEmpty() || etCode5.getText().toString().trim().isEmpty() || etCode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(VerifyOTPActivity.this, "Please enter valid code", Toast.LENGTH_LONG).show();
                    return;
                }

                String code = etCode1.getText().toString() + etCode2.getText().toString() + etCode3.getText().toString() + etCode4.getText().toString() + etCode5.getText().toString() + etCode6.getText().toString();
                // TODO Make the OTP verification part more efficient with more functions and more
                Log.d("OTPErrors", "onClick verify: code: " + code);
                if(verificationId != null) {
                    Log.d("OTPErrors", "onClick verify: verificationId:  " + verificationId);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    Log.d("OTPErrors", "onClick verify: phoneAuthCredential:  " + phoneAuthCredential.getSmsCode().toString());
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), ChoosingActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.putExtra("id", id);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(VerifyOTPActivity.this, "The verification code entered was invalid", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        btnResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        String.format(getIntent().getStringExtra("mobile")),
                        60L,
                        TimeUnit.SECONDS,
                        VerifyOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId = newVerificationId;
                                Toast.makeText(VerifyOTPActivity.this, "OTP Resent", Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        });
    }

    /**
     * This function is to make the input EditText for the OTP to be
     * followed by each other.
     */
    private void setupOTPInputs() {
        etCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()) {
                    etCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()) {
                    etCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()) {
                    etCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()) {
                    etCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()) {
                    etCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * This override function is to disable the Back Button on this activity.
     */
    @Override
    public void onBackPressed(){}


}