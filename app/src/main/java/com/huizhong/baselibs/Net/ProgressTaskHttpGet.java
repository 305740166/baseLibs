package com.huizhong.baselibs.Net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;


import com.huizhong.baselibs.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by shaochun on 14-8-5.
 */
public class ProgressTaskHttpGet extends AsyncTask<String, Void, Integer> {

    private Context context;
    private ProgressDialog progressDialog;
    JSONArray jsonArray;

    private boolean isCancel = true;
    private boolean isPgShow = true;

    private static final int STATE_FINISH = 1;
    private static final int STATE_ERROR = -1;

    //连接超时
    public int connTimeOut = 15 * 1000;

    public ProgressTaskHttpGet(Context context, int isShow) {
        this.context = context;

        if (isShow == 0) {//不显示进度条
            this.isPgShow = false;
        } else if (isShow == 1) {//不可取消进度条
            this.isCancel = false;
        } else if (isShow == 2) {//可以取消点击进度条
            this.isCancel = true;
        }
    }

    /* 该方法将在执行实际的后台操作前被UI thread调用。可以在该方法中做一些准备工作，如在界面上显示一个进度条。*/
    @Override
    protected void onPreExecute() {
        if (this.isPgShow) showProgress(this.isCancel);
    }

    @Override
    protected Integer doInBackground(String... datas) {

        jsonArray = getJSONArray(datas[0]);

        return STATE_FINISH;
    }


    @Override
    protected void onPostExecute(Integer result) {
        int state = result.intValue();

        closeProgress();
        handler.removeCallbacks(runnable);


        switch (state) {
            case STATE_FINISH:
                onComplete();

                break;

            case STATE_ERROR:

                break;
        }


    }

    private OnCompleteListener completeListener;

    public void setOnCompleteListener(OnCompleteListener completeListener) {
        this.completeListener = completeListener;
    }

    public interface OnCompleteListener {
        public void onComplete(JSONArray jsonArray1);
    }

    private void onComplete() {
        if (completeListener != null) {
            completeListener.onComplete(jsonArray);
        }
    }

    //数据加载提示ui
    public void showProgress(boolean cancelable) {
        if (progressDialog != null) progressDialog.dismiss();

        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("网络连接");
        progressDialog.setMessage("数据读取中，请稍后");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }

    //关闭加载
    public void closeProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    //get请求
    public JSONArray getJSONArray(String baseUrl) {

        JSONArray jsonArray = null;

        //将URL与参数拼接
        HttpGet getMethod = new HttpGet(baseUrl);

        BasicHttpParams httpParams = new BasicHttpParams();
        //HttpConnectionParams.setConnectionTimeout(httpParams, connTimeOut);
        //HttpConnectionParams.setSoTimeout(httpParams, connTimeOut);

        HttpClient httpClient = new DefaultHttpClient(httpParams);
        handler.postDelayed(runnable, connTimeOut + 800);

        try {
            HttpResponse response = httpClient.execute(getMethod); //发起GET请求

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                InputStream inputStream = httpEntity.getContent();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String content = "";
                String readLine = null;
                while ((readLine = bufferedReader.readLine()) != null) {
                    //response = br.readLine();
                    content = content + readLine;
                }
                inputStream.close();
                bufferedReader.close();

                if (content==null||content.equals("")) return null;

                jsonArray = new JSONArray(content);
                return jsonArray;

            }


        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        handler.removeCallbacks(runnable);
        closeProgress();
        return jsonArray;
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            closeProgress();
            Toast.makeText(context, "抱歉，连接超时", Toast.LENGTH_LONG).show();
        }
    };

}
