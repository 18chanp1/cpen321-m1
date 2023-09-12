package com.beaker.cpen321tutorial;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class Services {
    static final String SERVER_ADDRESS = "https://cpen321-m1.westus.cloudapp.azure.com";

    static public void getTime(SeverDetails context)
    {
        OkHttpClient client = new OkHttpClient();
        String url = SERVER_ADDRESS + "/time";

        Request req = new Request.Builder()
                .url(url)
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String res = response.body().string();

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getServerTime().setText(res);
                        }
                    });
                }

            }
        });
    }
    static public void getDeveloperName(SeverDetails context)
    {
        OkHttpClient client = new OkHttpClient();
        String url = SERVER_ADDRESS + "/dev-name";

        Request req = new Request.Builder()
                .url(url)
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String res = response.body().string();

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getDevName().setText(res);
                        }
                    });
                }

            }
        });
    }

    static public void getPublicIP(SeverDetails context)
    {
        OkHttpClient client = new OkHttpClient();
        String url = SERVER_ADDRESS + "/server-ip";

        Request req = new Request.Builder()
                .url(url)
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String res = response.body().string();

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getServerIP().setText(res);
                        }
                    });
                }

            }
        });
    }
}
