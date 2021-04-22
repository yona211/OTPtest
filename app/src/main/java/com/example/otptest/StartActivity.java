package com.example.otptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.start_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        if(id==R.id.action_vote)
        {
            Intent intent = new Intent(StartActivity.this, SendOTPActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.action_winners)
        {
            Intent intent = new Intent(StartActivity.this, WinnersActivity.class);
            startActivity(intent);
        }
        else  if(id==R.id.action_exit)
        {
            finish();
            System.exit(0);
        }
        return true;
    }

    protected void onDestroy(){
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }
}