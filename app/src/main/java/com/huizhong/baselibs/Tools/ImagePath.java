package com.huizhong.baselibs.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shaochun on 14-11-27.
 */
public class ImagePath implements Runnable {

    Context context;
    FileCache fileCache;
    private String url;

    private String fullpath;
    private Bitmap bitmap;

    //定义回调事件
    public interface OnCustomCallListener {
        public void back(String fullpath, Bitmap bitmap);
    }

    private OnCustomCallListener customCallListener;

    public ImagePath(Context context, String url, OnCustomCallListener customCallListener) {
        this.context = context;
        this.url = url;
        this.customCallListener = customCallListener;
        fileCache = new FileCache(context);

    }

    @Override
    public void run() {

        this.bitmap = getBitmap(this.url);
        this.customCallListener.back(this.fullpath, this.bitmap);


    }

    public Bitmap getBitmap(String url) {
        //从sd卡中读取
        Bitmap bitmap = fileCache.getFiles(url);

        if (bitmap != null) {
            //Log.v("图片加载", "从缓存中读取图片");
            fullpath = fileCache.getFullPath(url);
            return bitmap;
        }

        //从网络读取
        try {

            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            fullpath = fileCache.setFiles(url, bitmap);

            //bitmap = decodeFile(f);
            is.close();
            conn.disconnect();

            return bitmap;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
