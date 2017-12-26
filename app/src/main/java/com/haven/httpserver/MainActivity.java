package com.haven.httpserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String LOCALHOST_URL = "http://127.0.0.1:";
    private static final int SERVER_PORT = 9999;
    private HttpServer httpServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpServer = new HttpServer(SERVER_PORT);
        findViewById(R.id.btStartServer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    httpServer.start();
                    Log.i(TAG, "http服务已启动 ");
                    Toast.makeText(MainActivity.this, "http服务已启动", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i(TAG, "http服务启动失败：" + e.getMessage());
                    Toast.makeText(MainActivity.this, "http服务启动失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btStopServer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (httpServer != null) {
                    httpServer.stop();
                    Log.i(TAG, "http服务已停止 ");
                    Toast.makeText(MainActivity.this, "http服务已停止", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.btTestGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 1; i++) {
                    final String requestTag = String.valueOf(i);
                    AndroidNetworking.get(LOCALHOST_URL + SERVER_PORT + "/login")
                            .addQueryParameter("tag", requestTag)
                            .setTag("login")
                            .build()
                            .getAsString(new StringRequestListener() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i(TAG, "request " + requestTag + " onResponse: " + response);
                                    Toast.makeText(MainActivity.this, "onResponse：" + response, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Log.i(TAG, "onError: " + anError.getErrorBody());
                                    Toast.makeText(MainActivity.this, "onError：" + anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
        findViewById(R.id.btTestPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 1; i++) {
                    final String requestTag = String.valueOf(i);
                    AndroidNetworking.post(LOCALHOST_URL + SERVER_PORT + "/login")
                            .addBodyParameter("tag", requestTag)
                            .setTag("login")
                            .build()
                            .getAsString(new StringRequestListener() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i(TAG, "request " + requestTag + " onResponse: " + response);
                                    Toast.makeText(MainActivity.this, "onResponse：" + response, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Log.i(TAG, "onError: " + anError.getErrorBody());
                                    Toast.makeText(MainActivity.this, "onError：" + anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
    }
}
