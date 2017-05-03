package com.vise.bluetoothchat;

//public class ClsUtils {
//
//    public ClsUtils() {
//        // TODO Auto-generated constructor stub
//    }
//
//    /**
//     * 与设备配对 参考源码：platform/packages/apps/Settings.git
//     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
//     */
//    static public boolean createBond(Class<? extends BluetoothDevice> btClass, BluetoothDevice btDevice)
//            throws Exception {
//
//        Method createBondMethod = btClass.getMethod("createBond");
//        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
//        return returnValue.booleanValue();
//    }
//
//    /**
//     * 与设备解除配对 参考源码：platform/packages/apps/Settings.git
//     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
//     */
//    static public boolean removeBond(Class<? extends BluetoothDevice> btClass, BluetoothDevice btDevice)
//            throws Exception {
//        Method removeBondMethod = btClass.getMethod("removeBond");
//        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
//        return returnValue.booleanValue();
//    }
//
//    static public boolean setPin(Class btClass, BluetoothDevice btDevice,
//                                 String str) throws Exception {
//        try {
//            Method removeBondMethod = btClass.getDeclaredMethod("setPin",
//                    new Class[]{byte[].class});
//            Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice,
//                    new Object[]
//                            {str.getBytes()});
//            Log.e("returnValue设置密码:" + returnValue.booleanValue());
//            return returnValue.booleanValue();
//        } catch (SecurityException e) {
//            // throw new RuntimeException(e.getMessage());
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            // throw new RuntimeException(e.getMessage());
//            e.printStackTrace();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return false;
//
//    }
//
//    // 取消用户输入
//    static public boolean cancelPairingUserInput(Class<?> btClass,BluetoothDevice device)
//            throws Exception {
//        Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
//        cancelBondProcess(btClass, device);
//        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
//        Log.i("取消对话框cancelPairingUserInput:" + returnValue.booleanValue());
//        return returnValue.booleanValue();
//    }
//
//    // 取消配对
//    static public boolean cancelBondProcess(Class<?> btClass,
//                                            BluetoothDevice device)
//
//            throws Exception {
//        Method createBondMethod = btClass.getMethod("cancelBondProcess");
//        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
//        return returnValue.booleanValue();
//    }
//
//    /**
//     * @param clsShow
//     */
//    static public void printAllInform(Class<?> clsShow) {
//        try {
//            // 取得所有方法
//            Method[] hideMethod = clsShow.getMethods();
//            int i = 0;
//            for (; i < hideMethod.length; i++) {
//                Log.e("method name " + hideMethod[i].getName() + ";and the i is:" + i);
//            }
//            // 取得所有常量
//            Field[] allFields = clsShow.getFields();
//            for (i = 0; i < allFields.length; i++) {
//                Log.e("Field name "+ allFields[i].getName());
//            }
//        } catch (SecurityException e) {
//            // throw new RuntimeException(e.getMessage());
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            // throw new RuntimeException(e.getMessage());
//            e.printStackTrace();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//
//    static public boolean pair(String strAddr, String strPsw) {
//        boolean result = false;
//        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
//                .getDefaultAdapter();
//
//        bluetoothAdapter.cancelDiscovery();
//
//        if (!bluetoothAdapter.isEnabled()) {
//            bluetoothAdapter.enable();
//        }
//
//
//        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(strAddr);
//
//        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//            try {
//                Log.d("NOT BOND_BONDED");
//                boolean flag1 = ClsUtils.setPin(device.getClass(), device, strPsw); // 手机和蓝牙采集器配对
//                boolean flag2 = ClsUtils.createBond(device.getClass(), device);
////                remoteDevice = device; // 配对完毕就把这个设备对象传给全局的remoteDevice
//
//                result = true;
//
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//
//                Log.d("setPiN failed!");
//                e.printStackTrace();
//            } //
//
//        } else {
//            Log.d("HAS BOND_BONDED");
//            try {
//                ClsUtils.removeBond(device.getClass(), device);
//                //ClsUtils.createBond(device.getClass(), device);
//                boolean flag1 = ClsUtils.setPin(device.getClass(), device, strPsw); // 手机和蓝牙采集器配对
//                boolean flag2 = ClsUtils.createBond(device.getClass(), device);
////                remoteDevice = device; // 如果绑定成功，就直接把这个设备对象传给全局的remoteDevice
//
//                result = true;
//
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                Log.d("setPiN failed!");
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//}

/************************************ 蓝牙配对函数 * **************/

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClsUtils
{
    /**
     * 与设备配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    static public boolean createBond(Class btClass, BluetoothDevice btDevice)
            throws Exception
    {
        Method createBondMethod = btClass.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    /**
     * 与设备解除配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    static public boolean removeBond(Class<?> btClass, BluetoothDevice btDevice)
            throws Exception
    {
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    static public boolean setPin(Class<? extends BluetoothDevice> btClass, BluetoothDevice btDevice,
                                 String str) throws Exception
    {
        try
        {
            Method removeBondMethod = btClass.getDeclaredMethod("setPin",
                    new Class[]
                            {byte[].class});
            Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice,
                    new Object[]
                            {str.getBytes()});
            Log.e("returnValue", "" + returnValue);
        }
        catch (SecurityException e)
        {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;

    }

    // 取消用户输入
    static public boolean cancelPairingUserInput(Class<?> btClass,
                                                 BluetoothDevice device)  throws Exception
    {
        Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
//        cancelBondProcess(btClass, device);
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }

    // 取消配对
    static public boolean cancelBondProcess(Class<?> btClass,
                                            BluetoothDevice device)

            throws Exception
    {
        Method createBondMethod = btClass.getMethod("cancelBondProcess");
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }

    //确认配对

    static public void setPairingConfirmation(Class<?> btClass,BluetoothDevice device,boolean isConfirm)throws Exception
    {
        Method setPairingConfirmation = btClass.getDeclaredMethod("setPairingConfirmation",boolean.class);
        setPairingConfirmation.invoke(device,isConfirm);
    }


    /**
     *
     * @param clsShow
     */
    static public void printAllInform(Class clsShow)
    {
        try
        {
            // 取得所有方法
            Method[] hideMethod = clsShow.getMethods();
            int i = 0;
            for (; i < hideMethod.length; i++)
            {
                Log.e("method name", hideMethod[i].getName() + ";and the i is:"
                        + i);
            }
            // 取得所有常量
            Field[] allFields = clsShow.getFields();
            for (i = 0; i < allFields.length; i++)
            {
                Log.e("Field name", allFields[i].getName());
            }
        }
        catch (SecurityException e)
        {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}