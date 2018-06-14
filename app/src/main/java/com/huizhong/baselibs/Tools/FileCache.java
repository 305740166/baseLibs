package com.huizhong.baselibs.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shaochun on 14-3-26.
 */
public class FileCache {
    private File cacheDir;

    public FileCache(Context context) {
        //找一个用来缓存的图片路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), AppTools.getInstance().getAppName(context) + "/cache/");
        } else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    //直接读取sk卡图片
    public Bitmap getCameraFiles(String url) {
        Log.v("getCameraFiles", url);

        Bitmap bitmap = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(url);


            bitmap = BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*
        File f=new File(cacheDir,filename);
        return f;
        */

        return bitmap;
    }

    public Bitmap createNewBitmapAndCompressByFile(String filePath, int wh[]) {
        int offset = 100;
        File file = new File(filePath);
        long fileSize = file.length();
        if (200 * 1024 < fileSize && fileSize <= 1024 * 1024)
            offset = 90;
        else if (1024 * 1024 < fileSize)
            offset = 85;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 为true里只读图片的信息，如果长宽，返回的bitmap为null
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inDither = false;
        /**
         * 计算图片尺寸 //TODO 按比例缩放尺寸
         */
        BitmapFactory.decodeFile(filePath, options);

        int bmpheight = options.outHeight;
        int bmpWidth = options.outWidth;
        int inSampleSize = bmpheight / wh[1] > bmpWidth / wh[0] ? bmpheight / wh[1] : bmpWidth / wh[0];
        // if(bmpheight / wh[1] < bmpWidth / wh[0]) inSampleSize = inSampleSize * 2 / 3;//TODO 如果图片太宽而高度太小，则压缩比例太大。所以乘以2/3
        if (inSampleSize > 1)
            options.inSampleSize = inSampleSize;// 设置缩放比例
        options.inJustDecodeBounds = false;

        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (OutOfMemoryError e) {

            System.gc();
            bitmap = null;
        }
        if (offset == 100)
            return bitmap;
        // 缩小质量
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, offset, baos);
        byte[] buffer = baos.toByteArray();
        options = null;
        if (buffer.length >= fileSize)
            return bitmap;
        return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
    }

    /**
     * 获取图片的物理路径
     *
     * @param url
     * @return
     */
    public String getFullPath(String url) {
        String filename = String.valueOf(url.hashCode());
        String fullPath = cacheDir + "/" + filename;

        return fullPath;
    }

    public Bitmap getFiles(String url) {
        //Log.w("ZXTImage","In  FileCache getFiles and url =  "+url);

        String filename = String.valueOf(url.hashCode())/*+".png"*/;
        //Log.w("ZXTImage","In  FileCache getFiles and filename =  "+filename);

        Log.v("FileCache", filename);
        String fullPath = cacheDir + "/" + filename;
        //Log.w("ZXTImage","In  FileCache getFiles and fullPath =  "+fullPath);

        File file = new File(fullPath);
        if (!file.exists()) {
            return null;
        }
        //Log.w("ZXTImage","In  FileCache getFiles file exsit and try to getBitmap");
        Bitmap bitmap = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fullPath);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*
        File f=new File(cacheDir,filename);
        return f;
        */

        return bitmap;
    }

    //缓存图片到sd卡中
    public String setFiles(String url, Bitmap bitmap) {

        String filename = String.valueOf(url.hashCode())/*+".png"*/;
        String fullPath = cacheDir + "/" + filename;

        /*Log.w("ZXTImage","In  FileCache setFiles and cacheDir =  "+cacheDir);
        Log.w("ZXTImage","In  FileCache setFiles and url =  "+url);
        Log.w("ZXTImage","In  FileCache setFiles and filename =  "+filename);*/

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);

            return fullPath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    //缓存图片到sd卡中
    public String setFiles2(String filename, Bitmap bitmap) {


        String filePath = cacheDir + "/" + filename;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            f.delete();
        }
    }
}
