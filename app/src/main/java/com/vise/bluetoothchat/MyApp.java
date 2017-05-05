package com.vise.bluetoothchat;

import android.app.Application;
import android.content.Intent;

import com.vise.bluetoothchat.service.MyService;

/**
 * Created by wangyaohui on 17-4-28.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }
}
