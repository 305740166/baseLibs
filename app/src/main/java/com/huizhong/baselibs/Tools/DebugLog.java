package com.huizhong.baselibs.Tools;


import android.util.Log;

public class DebugLog {

    public static boolean isDebug = true;//BuildConfig.DEBUG;


    public static void v(String TAG, String log) {
        if (isDebug) {
            Log.v(TAG, log);
        }

    }

    public static void d(String TAG, String log) {
        if (isDebug) {
            Log.d(TAG, log);
        }

    }

    public static void i(String TAG, String log) {
        if (isDebug) {
            Log.i(TAG, log);
        }

    }

    public static void w(String TAG, String log) {
        if (isDebug) {
            Log.w(TAG, log);
        }

    }

    public static void e(String TAG, String log) {
        if (isDebug) {
            Log.e(TAG, log);
        }

    }


}
