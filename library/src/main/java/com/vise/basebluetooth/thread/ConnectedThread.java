package com.vise.basebluetooth.thread;

import android.bluetooth.BluetoothSocket;

import com.vise.basebluetooth.BluetoothChatHelper;
import com.vise.basebluetooth.Log;
import com.vise.basebluetooth.common.ChatConstant;
import com.vise.basebluetooth.utils.BleLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description: 连接后维护线程
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2016-09-13 18:38
 */
public class ConnectedThread extends Thread {

    private final BluetoothChatHelper mHelper;
    private final BluetoothSocket mSocket;
    private final InputStream mInStream;
    private final OutputStream mOutStream;

    public ConnectedThread(BluetoothChatHelper bluetoothChatHelper, BluetoothSocket socket) {
        BleLog.i("create ConnectedThread" );
        mHelper = bluetoothChatHelper;
        mSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            BleLog.e("temp sockets not created", e);
        }

        mInStream = tmpIn;
        mOutStream = tmpOut;
    }

    public void run() {
        setName("TalkWithServerThread-" + getId());
        BleLog.i("BEGIN mConnectedThread");
        int bytes;
        byte[] buffer = new byte[1024];

        // Keep listening to the InputStream while connected
        while (true) {
            try {
                bytes = mInStream.read(buffer);
                byte[] data = new byte[bytes];
                System.arraycopy(buffer, 0, data, 0, data.length);
                mHelper.getHandler().obtainMessage(ChatConstant.MESSAGE_READ, bytes, -1, data).sendToTarget();
            } catch (IOException e) {
                BleLog.e("disconnected", e);
                if(!exitFlag) {
                    mHelper.start();
                } else {
                }
                break;
            }
        }
    }

    public void write(byte[] buffer) {
        if(mSocket.isConnected()){
            try {
                mOutStream.write(buffer);
                mHelper.getHandler().obtainMessage(ChatConstant.MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                BleLog.e("Exception during write", e);
            }
        }
    }

    public void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) {
            BleLog.e("close() of connect socket failed", e);
        }
    }

    private boolean exitFlag = false;
    public void setExitFlag(boolean flag) {
        this.exitFlag = flag;
    }
    public boolean getExitFlag() {return exitFlag;}

    public String pInfo() {
        return pInfo(Thread.currentThread());
    }

    public String pInfo(Thread thread) {
        StringBuffer sb = new StringBuffer();
        sb.append("(Thread Name:").append(thread.getName()).append(",Id:").append(thread.getId()).append(")");
        return sb.toString();
    }

    public void printAllthreadInfo() {
        StringBuffer sb = new StringBuffer("\n");
        Thread thread = Thread.currentThread();
        Map map = Thread.getAllStackTraces(); //也可以map<Thread, StackTraceElement[]>
        sb.append("当前线程数：" + map.size()).append("\n");
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            Thread t = (Thread) it.next(); //
            sb.append(pInfo(t)).append("\n");
        }
        Log.e(sb.toString());
    }
}
