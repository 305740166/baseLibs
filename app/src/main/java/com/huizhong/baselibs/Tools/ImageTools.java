package com.huizhong.baselibs.Tools;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

/**
 * Created by wyx on 17/7/11.
 */
public class ImageTools {

    //重置初始
    public static void resetBackgroundResource(ImageView view){
        view.setBackgroundResource(0);
    }

    //重置初始
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void resetBackground(ImageView view){
        view.setBackground(null);
    }
    //设置耗内存大图
    public static void setBigBackgroundResource(ImageView view,int drawableID){
        resetBackgroundResource(view);
        view.setBackgroundResource(drawableID);
    }
    //设置耗内存大图
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setBigBackground(ImageView view, Drawable drawable){
        resetBackground(view);
        view.setBackground(drawable);
    }
}
