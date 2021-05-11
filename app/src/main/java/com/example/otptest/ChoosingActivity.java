package com.example.otptest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoosingActivity extends AppCompatActivity implements View.OnClickListener{

    Dialog d;
    Button btnVote, btnBack;
    ImageView ivCompetitorSelfie;

    String Id;
    TextView tvName;
    String competitorChosenId;
    Intent intent, intent2;
    String s2 = "";

    Bitmap[] selfies;
    ArrayList<Competitor> competitorsList;
    ListView lv;
    CompetitorAdapter competitorAdapter;

    ServerConnectionService mService;
    boolean mBound = false;

    // The actual connection with the server side
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ServerConnectionService.LocalBinder binder = (ServerConnectionService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            final String msgToSend = "FIND " + Id;
            s2 = String.valueOf(mService.getServerReturns(msgToSend));
            setListView(s2);
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
        setContentView(R.layout.activity_choosing);

        lv = (ListView)findViewById(R.id.lv);

        selfies = new Bitmap[5];
        selfies[0] = BitmapFactory.decodeResource(getResources(), R.drawable.stark);
        selfies[1] = BitmapFactory.decodeResource(getResources(), R.drawable.arryn);
        selfies[2] = BitmapFactory.decodeResource(getResources(), R.drawable.baratheon);
        selfies[3] = BitmapFactory.decodeResource(getResources(), R.drawable.bronn);
        selfies[4] = BitmapFactory.decodeResource(getResources(), R.drawable.greyjoy);
        // Obtain the ID from the previous activity
        intent = getIntent();
        Id = intent.getExtras().getString("id");
        final String msgToSend = "FIND " + Id;

        // Set up the connection with the server
        intent2 = new Intent(ChoosingActivity.this, ServerConnectionService.class);
        bindService(intent2, connection, Context.BIND_AUTO_CREATE);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createMakeChoiceDialog(competitorsList.get(position).getFirstName(), competitorsList.get(position).getLastName(), competitorsList.get(position).getId(), competitorsList.get(position).getBitmap());
            }
        });

    }
    public void setListView(String s2)
    {
        int competitorsCounter = 0;
        int length = 0;
        String temp = "";
        String[] competitor = new String[5];
        competitorsList = new ArrayList<Competitor>();
        for(int i = 0; i < s2.length(); i++)
        {
            temp += s2.charAt(i);
            if(s2.charAt(i) == ' ')
            {
                competitor[length] = temp.substring(0, temp.length()-1);
                temp = "";
                if(length == 4)
                {
                    Competitor c = new Competitor(competitor[0], competitor[1], competitor[3], competitor[2], competitor[4], selfies[competitorsCounter]);
                    competitorsList.add(c);
                    length = 0;
                    competitorsCounter++;
                    if(competitorsCounter  == 5) {
                        competitorsCounter = 0;
                    }
                }
                else
                {
                    length++;
                }
            }
        }
        competitorAdapter = new CompetitorAdapter(ChoosingActivity.this, 0, 0, competitorsList);
        lv.setAdapter(competitorAdapter);
    }

    public String createMakeChoiceDialog(String FirstName, String LastName, String Id, Bitmap bitmap)
    {
        String name = FirstName + " " + LastName;
        competitorChosenId = Id;
        d = new Dialog(this);
        d.setContentView(R.layout.make_choise);
        d.setTitle("Are You Sure");
        d.setCancelable(true);
        btnVote = (Button)d.findViewById(R.id.btnVote);
        btnVote.setOnClickListener(this);
        btnBack = (Button)d.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        tvName = (TextView) d.findViewById(R.id.tvName);
        tvName.setText(name);
        ivCompetitorSelfie = (ImageView)d.findViewById(R.id.ivCompetitorSelfie);
        ivCompetitorSelfie.setImageBitmap(bitmap);
        d.show();
        return name;
    }

    @Override
    public void onClick(View v) {
        if(v == btnBack)
        {
            Toast.makeText(this, "חזרת. עכשיו בחר שוב", Toast.LENGTH_LONG).show();
            d.dismiss();
        }
        else if(v == btnVote)
        {
            d.dismiss();
            final String msgToSend = "VOTE " + Id + " " + competitorChosenId;
            s2 = String.valueOf(mService.getServerReturns(msgToSend));
            Log.d("Voting", "connection: onServiceConnected: s2: " + s2);
            unbindService(connection);
            Intent endIntent = new Intent(ChoosingActivity.this, AfterVoteActivity.class);
            startActivity(endIntent);
        }
    }

    /**
     * This override function is to disable the Back Button on this activity.
     */
    @Override
    public void onBackPressed(){}


}