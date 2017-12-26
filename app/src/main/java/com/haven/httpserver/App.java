package com.haven.httpserver;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by lsq on 12/26/2017.12:42 PM
 *
 * @author lsq
 * @describe 添加描述
 */

public class App extends Application {

    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        AndroidNetworking.initialize(this);
    }

    public static App getApp() {
        return sApp;
    }
}
