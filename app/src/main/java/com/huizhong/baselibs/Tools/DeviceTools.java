package com.huizhong.baselibs.Tools;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;

public class DeviceTools {
    private static DeviceTools instance;


    public static DeviceTools getInstance(){
        if(instance==null){
            instance=new DeviceTools();
        }
        return instance;
    }

    private static PowerManager.WakeLock wakeLock = null;

    /**
     * 获取屏幕的宽度
     */
    public   int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public   int getWindowsHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     *  获取手机型号
     */
    public   String getPhoneModel() {
        String mtype = android.os.Build.MODEL;
        return mtype;
    }

    /**
     *  获取手机品牌
     */
    public   String getPhoneBrand() {
        String mtyb= android.os.Build.BRAND;
        return mtyb;
    }


    /**
     *  获取手机品牌
     */
    public   TelephonyManager getPhoneManager(Activity activity) {

        TelephonyManager  tm = (TelephonyManager) activity.getSystemService(activity.TELEPHONY_SERVICE);
        return tm;
    }

    /**
     * 返回当前移动终端的唯一标识
     * 如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID
     */
    public   String getDeviceId(Activity activity) {
        String deviceID=getPhoneManager(activity).getDeviceId();
        return deviceID;
    }


    /**
     *  返回手机号码，对于GSM网络来说即MSISDN（未必能获取到手机号）
     */
    public   String getPhoneNumber(Activity activity) {
        String phoneNumber=  getPhoneManager(activity).getLine1Number();
        return phoneNumber;
    }

    /**
     *  返回手机号码，对于GSM网络来说即MSISDN
     */
    public  String getSubscriberId(Activity activity) {
        String SubscriberId=  getPhoneManager(activity).getSubscriberId();
        return SubscriberId;
    }

    /**
     *  获取卫星定位服务打开或关闭状态
     */
    public boolean getGpsLocationOPenable(Activity activity) {
        LocationManager locationManager
                = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gps;
    }

    /**
     *  获取手机网络定位服务打开或关闭状态
     */
    public  final boolean getNetLocationOPenable(Activity activity) {
        LocationManager locationManager
                = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return network;
    }

    /**
     * 强制打开GPS
     */
    public  final void openGPS(Activity activity) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(activity, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
     */
    public void acquireWakeLock(Activity activity) {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, activity.getClass()
                    .getCanonicalName());
            if (null != wakeLock) {
                wakeLock.acquire();
            }
        }
    }

    // 释放设备电源锁
    public  void releaseWakeLock() {
        if (null != wakeLock && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }
    }

    //获取手机安卓系统版本号
    public  int getPhoneAndroidSDK() {
        // TODO Auto-generated method stub
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;

    }

    //获取状态栏高度
    public  int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * @param context
     * @return 所有缓存大小
     */
    public  String getCacheSize(Context context) {
        long cacheSize = 0;
        long externalCacheSize = 0;
        long externalFilesSize= 0;
        long appCacheSize = 0;
        String strAppCacheSize = "0M";

        try {
//            cacheSize = getFileSize(context.getApplicationContext().getCacheDir());
            externalFilesSize = getFileSize(context.getApplicationContext().getFilesDir());
            externalCacheSize = getFileSize(context.getApplicationContext().getExternalCacheDir());

            appCacheSize = externalFilesSize+ externalCacheSize;

            strAppCacheSize = FormatFileSize(appCacheSize);

            Log.i("cache", "In getCacheSize cacheSize = " + cacheSize);
            Log.i("cache", "In getCacheSize externalCacheSize = " + externalCacheSize);
            Log.i("cache", "In getCacheSize appCacheSize = " + appCacheSize);
            Log.i("cache", "In getCacheSize strAppCacheSize = " + strAppCacheSize);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return strAppCacheSize;
    }



    public  long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }


    public  String FormatFileSize(long fileS) {
        if (fileS == 0) return "0B";
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


/*
//        context.deleteDatabase("webview.db");
//        context.deleteDatabase("webviewCache.db");*/
    public  void clearCache(Context context) {

        cleanFiles(context);
        cleanInternalCache(context);
        cleanExternalCache(context);
    }

    /** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context */
    public  void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * context
     */
    public  void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context */
    public  void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private  void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 是否是竖向
     */
    public  boolean isScreenChange(Context context) {

        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation ; //获取屏幕方向
//横屏
        if(ori == mConfiguration.ORIENTATION_LANDSCAPE){
            return true;
//竖屏
        }else if(ori == mConfiguration.ORIENTATION_PORTRAIT){
            return false;
        }
        return false;
    }
}
