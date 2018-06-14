package com.huizhong.baselibs.Tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.huizhong.baselibs.R;

import java.io.File;

/**
 * Created by wyx on 16/10/21.
 */
public class AppTools {
    private static AppTools instance;


    public static AppTools getInstance(){
        if(instance==null){
            instance=new AppTools();
        }
        return instance;
    }

    /**
     * 获取屏幕的宽度
     */
    public  int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     */
    public  int getWindowsHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     * @author zuolongsnail
     */
    public  boolean isAppInstall(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 获取软件名称
     */
    public  String getAppName(Context context) {
        String app_name=context.getResources().getString(R.string.app_name);
        return app_name;
    }
    /**
     * 获取软件名称
     */
    public  String getAppVersion(Context context) {
        String app_vs=context.getResources().getString(R.string.app_version);
        return app_vs;
    }


    /**
     *  返回手机号码，对于GSM网络来说即MSISDN（未必能获取到手机号）
     */
    public  String getPhoneNumber(Activity activity) {
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
     *  获取手机品牌
     */
    public TelephonyManager getPhoneManager(Activity activity) {
        TelephonyManager  tm = (TelephonyManager) activity.getSystemService(activity.TELEPHONY_SERVICE);
        return tm;
    }

    @TargetApi(19)
    /**
     * 设置全透明状态栏,activity,颜色
     */
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup decorViewGroup = (ViewGroup)activity.getWindow().getDecorView();
            //获取自己布局的根视图
            View rootView = ((ViewGroup) (decorViewGroup.findViewById(android.R.id.content))).getChildAt(0);
            //预留状态栏位置
            rootView.setFitsSystemWindows(true);

            //添加状态栏高度的视图布局，并填充颜色
            View statusBarTintView = new View(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    getInternalDimensionSize(activity.getResources(), "status_bar_height"));
            params.gravity = Gravity.TOP;
            statusBarTintView.setLayoutParams(params);
            statusBarTintView.setBackgroundColor(color);
            decorViewGroup.addView(statusBarTintView);
        }
    }

    public static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
