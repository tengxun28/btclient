package com.vise.bluetoothchat.activity;

import android.app.Activity;
import android.os.Bundle;

import com.vise.bluetoothchat.Log;

/**
 * Created by wangyaohui on 17-5-2.
 */

public class MyActivity extends /*BaseChat*/Activity {
    public MyActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate");
    }

    protected void initWidget() {
        Log.e("initWidget");
    }

    protected void initData() {
        Log.e("initData");
    }

    protected void initEvent() {
        Log.e("initEvent");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("onBackPressed");
    }
}
