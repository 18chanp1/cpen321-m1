package com.beaker.cpen321tutorial;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SeverDetails extends AppCompatActivity {
    private String serveraddr = "http://20.163.101.103";
    private TextView serverDetailsTitle;
    private TextView clientIP;
    private TextView serverIP;
    private TextView serverTime;
    private TextView clientTime;
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

            //get server time
//            HttpURLConnection urlConnection = null;
//            try {
//                Log.d(TAG, "reading1");
//                URL url = new URL(serveraddr +"/time");
//                Log.d(TAG, "reading2");
//                urlConnection = (HttpURLConnection) url.openConnection();
//                Log.d(TAG, "reading3");
//                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//
//                String inputLine;
//                Log.d(TAG, "reading4");
//
//                while((inputLine = in.readLine())!= null)
//                {
//                    Log.d(TAG, "reading");
//                }
//                in.close();
//
//            } catch (MalformedURLException e1)
//            {
//                Log.d(TAG, "Malformed URL");
//            } catch (IOException e2)
//            {
//                Log.d(TAG, e2.toString());
//            } finally
//            {
//                urlConnection.disconnect();
//            }

        }
    };



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


    }

    @Override
    public void onPause()
    {
        super.onPause();
        timeHandler.removeCallbacks(timerRunnable);
    }
}