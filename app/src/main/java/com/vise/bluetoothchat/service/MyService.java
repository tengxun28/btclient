package com.vise.bluetoothchat.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.vise.bluetoothchat.ClsUtils;
import com.vise.bluetoothchat.Log;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by wangyaohui on 17-4-28.
 */

public class MyService extends Service {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle bundle = intent.getExtras();
            Set<String> keys = bundle.keySet();
            StringBuffer sb = new StringBuffer("\n");
            for (String key : keys) {
                sb.append(key).append(":").append(bundle.get(key)).append("\n");
            }
            Log.e("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n" + action + "," + sb.toString());

            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    if (isSpecialDevice(device)) {
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                        int connectState = device.getBondState();
                        switch (connectState) {
                            case BluetoothDevice.BOND_NONE:
                                try {
                                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                                    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                                    createBondMethod.invoke(device);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case BluetoothDevice.BOND_BONDED:
                                Log.e("My phone has paried with the searched device befored " + device.getBondState());
                                Intent i = new Intent("android.intent.action.pairing_device");
                                sendBroadcast(i);
                                break;
                        }
                    }
                    break;

                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    /*int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        case BluetoothAdapter.STATE_TURNING_ON:
                            break;
                        case BluetoothAdapter.STATE_ON:
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            Log.e("onReceive---------STATE_OFF");
                            break;
                    }*/
                    break;

                case BluetoothDevice.ACTION_PAIRING_REQUEST:
                    try {
                        int pairingKey = intent.getIntExtra(BluetoothDevice.EXTRA_PAIRING_KEY, BluetoothDevice.ERROR);
                        ClsUtils.setPairingConfirmation(device.getClass(), device, true);
                        //Log.e("order..." + " isOrderedBroadcast:" + isOrderedBroadcast() /*+ ",isInitialStickyBroadcast:" + isInitialStickyBroadcast()*/);
                        abortBroadcast();
                        boolean ret = ClsUtils.setPin(device.getClass(), device, String.valueOf(pairingKey));
                        Toast.makeText(context, "setPin finished:" + String.valueOf(pairingKey), Toast.LENGTH_SHORT).show();

                        Intent i = new Intent("android.intent.action.pairing_device");
                        sendBroadcast(i);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                case BluetoothAdapter.ACTION_SCAN_MODE_CHANGED:
                    /*int scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.SCAN_MODE_NONE);
                    if (scanMode != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)*/
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    if(isSpecialDevice(device)) {
                        int previous_state = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.BOND_BONDED);
                        int new_state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
                        if (previous_state == BluetoothDevice.BOND_BONDED && new_state == BluetoothDevice.BOND_NONE) {
                            try {
                                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                                Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                                createBondMethod.invoke(device);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("onCreate");
        listen();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("onStartCommand");
        BluetoothAdapter.getDefaultAdapter().startDiscovery();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy");
        unListen();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void listen() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, intentFilter);
    }

    public void unListen() {
        unregisterReceiver(mReceiver);
    }

    public boolean isSpecialDevice(BluetoothDevice remoteDevice) {
        return "wangyaohui".equals(remoteDevice.getName()) || "00:18:12:F0:7E:AD".equals(remoteDevice.getAddress());
    }

}
