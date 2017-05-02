package com.vise.basebluetooth.thread;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.vise.basebluetooth.BluetoothChatHelper;
import com.vise.basebluetooth.Log;
import com.vise.basebluetooth.common.ChatConstant;
import com.vise.basebluetooth.utils.BleLog;

import java.io.IOException;

/**
 * @Description: 连接线程
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2016-09-13 17:58
 */
public class ConnectThread extends Thread {

    private BluetoothChatHelper mHelper;
    private final BluetoothSocket mSocket;
    private final BluetoothDevice mDevice;

    public ConnectThread(BluetoothChatHelper bluetoothChatHelper/*, BluetoothDevice device*/) {
        mHelper = bluetoothChatHelper;
        mDevice = mHelper.getDevice();
        BluetoothSocket tmp = null;
        try {
            tmp = mDevice.createRfcommSocketToServiceRecord(ChatConstant.UUID_SECURE);
        } catch (IOException e) {
            BleLog.e("Socket create() failed", e);
        }
        mSocket = tmp;
    }

    public void run() {
        Log.e("BEGIN mConnectThread:");
        setName("ConnectThread");

        mHelper.getAdapter().cancelDiscovery();

        try {
            mSocket.connect();
        } catch (IOException e) {
            try {
                mSocket.close();
            } catch (IOException e2) {
                BleLog.e("unable to close() socket during connection failure", e2);
            }
            try {
                Thread.sleep(5000);
            }catch (Exception e3){
            }
            mHelper.connectionFailed();

            return;
        }
        Log.e("connect to server success!");

        synchronized (this) {
            mHelper.setConnectThread(null);
        }

        mHelper.connected(mSocket, mDevice);
    }

    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) {
            BleLog.e("close() of connect socket failed", e);
        }
    }
}
