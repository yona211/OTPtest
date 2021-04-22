package com.example.otptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etId;
    Button btnSendOTP;
    String OTPCode;
    String verificationId;
    String phoneNumber;

    ServerConnectionService mService;
    boolean mBound = false;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_o_t_p);

        etId = (EditText)findViewById(R.id.etId);
        btnSendOTP = (Button)findViewById(R.id.btnSendOTP);
        btnSendOTP.setOnClickListener(this);

        intent = new Intent(SendOTPActivity.this, ServerConnectionService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }



    @Override
    public void onClick(View v) {

        if(v == btnSendOTP)
        {
            // Checking if any ID has been entered.
            if(etId.getText().toString().trim().isEmpty()){
                Toast.makeText(SendOTPActivity.this, "הכנס מספר ת.ז", Toast.LENGTH_LONG).show();
            }
            else {
                // Get the phone number from the server by sending the id
                final String msgToSend = "PHONE " + etId.getText().toString();
                phoneNumber = String.valueOf(mService.getServerReturns(msgToSend));
                Log.d("OTPErrors", "phoneNumber: " + phoneNumber);
                // Checking if the user that has been sign is already voted
                if(phoneNumber.equals("Exist Voted")) {
                    Intent AlreadyVotedIntent = new Intent(SendOTPActivity.this, AfterVote.class);
                    startActivity(AlreadyVotedIntent);
                }
                else {
                    unbindService(connection);
                    final String phoneNumberValid = "+972" + phoneNumber.substring(1);
                    Log.d("OTPErrors", "else: phoneNumber: " + phoneNumberValid);
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(phoneNumberValid)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    Log.d("OTPErrors", "onVerificationCompleted: phoneAuthCredential: smsCode: " + phoneAuthCredential.getSmsCode().toString());
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Log.d("OTPErrors", e.getMessage());
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent intent = new Intent(SendOTPActivity.this, VerifyOTPActivity.class);
                                    intent.putExtra("mobile", phoneNumberValid);
                                    intent.putExtra("id", etId.getText().toString());
                                    intent.putExtra("verificationId", verificationId);
                                    startActivity(intent);
                                }
                            }).build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        }
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ServerConnectionService.LocalBinder binder = (ServerConnectionService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    protected void onDestroy(){
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }
}