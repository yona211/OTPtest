    package com.example.otptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WinnersActivity extends AppCompatActivity {

    Intent intent2;

    String s2 = "";

    Bitmap[] selfies;
    ArrayList<Winner> winnersList;
    ListView lv;
    WinnerAdapter winnerAdapter;

    ServerConnectionService mService;
    boolean mBound = false;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ServerConnectionService.LocalBinder binder = (ServerConnectionService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            final String msgToSend = "WINNERS" + " j";
            s2 = String.valueOf(mService.getServerReturns(msgToSend));
            Log.d("WinnersT", "s2: " + s2);
            if(s2.equals("n")) {
                Toast.makeText(WinnersActivity.this, "You can't see the results now, please wait.", Toast.LENGTH_SHORT).show();
            } else {
                setListView(s2);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService.onDestroy();
            mBound = false;
            Log.d("Voting", "connection: onServiceDisconnected");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners);

        lv = (ListView)findViewById(R.id.lv);

        selfies = new Bitmap[5];
        selfies[0] = BitmapFactory.decodeResource(getResources(), R.drawable.stark);
        selfies[1] = BitmapFactory.decodeResource(getResources(), R.drawable.arryn);
        selfies[2] = BitmapFactory.decodeResource(getResources(), R.drawable.baratheon);
        selfies[3] = BitmapFactory.decodeResource(getResources(), R.drawable.bronn);
        selfies[4] = BitmapFactory.decodeResource(getResources(), R.drawable.greyjoy);

        intent2 = new Intent(WinnersActivity.this, ServerConnectionService.class);
        bindService(intent2, connection, Context.BIND_AUTO_CREATE);
    }

    public void setListView(String s2)
    {
        Log.d("WinnersT", "setListView: 0");
        int winnersCounter = 0;
        int length = 0;
        String temp = "";
        String[] winner = new String[4];
        Log.d("WinnersT", "setListView: 1");
        winnersList = new ArrayList<Winner>();
        Log.d("WinnersT", "setListView: 2");
        for(int i = 0; i < s2.length(); i++)
        {
            temp += s2.charAt(i);
            if(s2.charAt(i) == ' ')
            {
                winner[length] = temp.substring(0, temp.length()-1);
                temp = "";
                if(length == 3)
                {
                    Winner w = new Winner(winner[1], winner[2], winner[0], winner[3], selfies[winnersCounter]);
                    Log.d("WinnersT", w.print());
                    winnersList.add(w);

                    length = 0;
                    winnersCounter++;
                    if(winnersCounter  == 4) {
                        winnersCounter = 0;
                    }
                }
                else
                {
                    length++;
                }
            }

        }
        winnerAdapter = new WinnerAdapter(WinnersActivity.this, 0, 0, winnersList);
        lv.setAdapter(winnerAdapter);
    }
}