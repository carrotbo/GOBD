package com.gwkj.qixiubaodian.obd.thread;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.gwkj.qixiubaodian.obd.Utils.BaseCacheUtil;
import com.gwkj.qixiubaodian.obd.contants.Constant;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.TELEPHONY_SERVICE;

public class InitThread extends Thread {
    /**
     * TessBaseAPI初始化用到的第一个参数，是个目录。
     */
    private static final String DATAPATH = Environment.getExternalStorageDirectory() + "/android/data/" + "com.gwkj.qixiubaodian" + "/files/mounted/";
    ;
    /**
     * 在DATAPATH中新建这个目录，TessBaseAPI初始化要求必须有这个目录。
     */
    private static final String tessdata = DATAPATH + "tessdata";

    /**
     * TessBaseAPI初始化测第二个参数，就是识别库的名字不要后缀名。
     */
    private static final String DEFAULT_LANGUAGE = "eng";
    /**
     * assets中的文件名
     */
    private static final String DEFAULT_LANGUAGE_NAME = DEFAULT_LANGUAGE + ".traineddata";
    /**
     * 保存到SD卡中的完整文件名
     */
    private static final String LANGUAGE_PATH = tessdata + File.separator + DEFAULT_LANGUAGE_NAME;
    private static InitThread instanceInitThread = null;
    private static Context mcontext;

    public static InitThread getInstnceInitThread(Context context) {

        if (instanceInitThread == null)
            instanceInitThread = new InitThread(context);
        return instanceInitThread;
    }

    private InitThread(Context context) {
        mcontext = context;
    }

    @Override
    public void run() {

        getDeviceId();

//		FileUtils.getInstance(mcontext).copyToSD(LANGUAGE_PATH, DEFAULT_LANGUAGE_NAME);
    }

    /**
     * 获取设备ID
     */
    private void getDeviceId() {
        try {
            Constant.Coding = BaseCacheUtil.getString(mcontext, "deviceid");
            TelephonyManager TelephonyMgr = (TelephonyManager) mcontext.getSystemService(TELEPHONY_SERVICE);
			if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
				return;
			}

            assert TelephonyMgr != null;
            @SuppressLint("HardwareIds") String szImei = TelephonyMgr.getDeviceId();

            String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10; //13 digits
            //安卓ID
            @SuppressLint("HardwareIds") String m_szAndroidID = Settings.Secure.getString(mcontext.getContentResolver(), Settings.Secure.ANDROID_ID);
            //wlan地址
            WifiManager wm = (WifiManager) mcontext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            assert wm != null;
            @SuppressLint("HardwareIds") String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
            //蓝牙地址
            BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
            m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            @SuppressLint("HardwareIds") String m_szBTMAC = m_BluetoothAdapter.getAddress();

            String m_szLongID = szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
            MessageDigest m = null;
            try {
                m = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            assert m != null;
            m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
            byte p_md5Data[] = m.digest();
            String m_szUniqueID = "";
            for (int i = 0; i < p_md5Data.length; i++) {
                int b = (0xFF & p_md5Data[i]);
                // 如果它是一个数字，确保它前面有0(适当的填充)
                if (b <= 0xF)
                    m_szUniqueID += "0";
                m_szUniqueID += Integer.toHexString(b);
            }
            m_szUniqueID = m_szUniqueID.toUpperCase();

            Constant.Coding = m_szUniqueID;
            Log.e("设备Id", m_szUniqueID);
            BaseCacheUtil.setString(mcontext, "deviceid", m_szUniqueID);
        } catch (Exception e) {
            e.printStackTrace();
            if (Constant.Coding.isEmpty()) {
                random();
            }
        }
    }

    /**
     * 生成随机数
     */

    private void random() {
        try {
            String main_goodluck = "goodluck";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            //获取当前时间
            String str = formatter.format(curDate);
            String random = main_goodluck + str;
            MessageDigest m = null;
            try {
                m = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            m.update(random.getBytes(), 0, random.length());
            byte p_md5Data[] = m.digest();
            String m_szUniqueID = new String();
            for (int i = 0; i < p_md5Data.length; i++) {
                int b = (0xFF & p_md5Data[i]);
                if (b <= 0xF)
                    m_szUniqueID += "0";
                m_szUniqueID += Integer.toHexString(b);
            }
            Constant.Coding = m_szUniqueID.toUpperCase();

            //            Log.e("生成的随机数--1--", newrandom);
            BaseCacheUtil.setString(mcontext, "deviceid", Constant.Coding);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
