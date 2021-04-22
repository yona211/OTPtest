package com.example.otptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Process;

public class AfterVote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_vote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.end_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        if(id==R.id.action_home_page)
        {
            Intent intent = new Intent(AfterVote.this, StartActivity.class);
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