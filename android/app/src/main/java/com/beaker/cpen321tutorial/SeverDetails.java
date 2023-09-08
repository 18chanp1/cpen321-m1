package com.beaker.cpen321tutorial;

import androidx.appcompat.app.AppCompatActivity;


import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SeverDetails extends AppCompatActivity {
    private String serveraddr = "cpen321-m1.azurewebsites.net";
    private TextView serverDetailsTitle;
    private TextView clientIP;
    private TextView serverIP;

    public TextView getServerTime() {
        return serverTime;
    }

    private TextView serverTime;
    private TextView clientTime;

    public TextView getDevName() {
        return devName;
    }

    private TextView devName;
    private TextView username;
    final static String TAG = "ServerDetails";

    //setup timer handler
    Handler timeHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            Calendar currentTime = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            clientTime.setText(sdf.format(new Date()));
            timeHandler.postDelayed(this, 1000);

            Services.getTime(SeverDetails.this);


        }
    };


    public TextView getServerIP() {
        return serverIP;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sever_details);

        clientIP = findViewById(R.id.client_ip_addr_entry);
        serverIP = findViewById(R.id.server_ip_addr_entry);
        serverTime = findViewById(R.id.server_time_entry);
        clientTime = findViewById(R.id.client_time_entry);
        devName = findViewById(R.id.dev_name_entry);
        username = findViewById(R.id.user_name_entry);


        //client ip
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        clientIP.setText(Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress()));


        //set timer handler
        timeHandler.post(timerRunnable);



        //get user details

        Bundle extras = getIntent().getExtras();

        GoogleSignInAccount user = null;

        if(extras != null)
        {
            user = (GoogleSignInAccount) extras.get("account");
        }

        if(user != null)
        {
            username.setText(user.getFamilyName() + ", " + user.getGivenName());
        }

        //get developer name
        Services.getDeveloperName(this);

        //get server public ip
        Services.getPublicIP(this);


    }

    @Override
    public void onPause()
    {
        super.onPause();
        timeHandler.removeCallbacks(timerRunnable);
    }


}