package com.huizhong.baselibs.Net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;


import com.huizhong.baselibs.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by shaochun on 14-8-5.
 */
public class ProgressTaskHttpPost extends AsyncTask<String, Void, Integer> {

    private Context context;
    private ProgressDialog progressDialog = null;
    private List<BasicNameValuePair> params;
    private JSONObject jsonObject;

    private boolean isCancel = true;
    private boolean isPgShow = true;

    private static final int STATE_FINISH = 1;
    private static final int STATE_ERROR = -1;

    //连接超时
    public int connTimeOut = 60 * 1000;

    /**
     * @param context
     * @param isShow  0 不显示进度条 1 不可取消进度条 2 可以取消点击进度条
     */
    public ProgressTaskHttpPost(Context context, int isShow) {
        this.context = context;

        if (isShow == 0) {//不显示进度条
            this.isPgShow = false;
        } else if (isShow == 1) {//不可取消进度条
            this.isCancel = false;
        } else if (isShow == 2) {//可以取消点击进度条
            this.isCancel = true;
        }

    }

    public void setParams(List<BasicNameValuePair> params) {
        this.params = params;
    }

    /* 该方法将在执行实际的后台操作前被UI thread调用。可以在该方法中做一些准备工作，如在界面上显示一个进度条。*/
    @Override
    protected void onPreExecute() {
        if (this.isPgShow) showProgress(this.isCancel);
    }

    @Override
    protected Integer doInBackground(String... datas) {

        jsonObject = httpPostResponse(datas[0], params);
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
        public void onComplete(JSONObject jsonObject1);
    }

    private void onComplete() {
        if (completeListener != null) {
            completeListener.onComplete(jsonObject);
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

    //post 请求
    public JSONObject httpPostResponse(String baseUrl, List<BasicNameValuePair> params) {

        JSONObject jsonObject = null;

        BasicHttpParams httpParams = new BasicHttpParams();
        //HttpConnectionParams.setConnectionTimeout(httpParams, connTimeOut);
        //HttpConnectionParams.setSoTimeout(httpParams, connTimeOut);

        HttpClient httpClient = new DefaultHttpClient(httpParams);
        handler.postDelayed(runnable, connTimeOut + 800);

        try {
            HttpPost postMethod = new HttpPost(baseUrl);
            postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //将参数填入POST Entity中

            HttpResponse response = httpClient.execute(postMethod); //执行POST方法

            //Log.i(TAG, "resCode = " + response.getStatusLine().getStatusCode()); //获取响应码
            //Log.i(TAG, "result = " + EntityUtils.toString(response.getEntity(), "utf-8")); //获取响应内容

            if (response.getStatusLine().getStatusCode() == 200) {
                jsonObject = new JSONObject(EntityUtils.toString(response.getEntity(), "utf-8"));
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        closeProgress();
        handler.removeCallbacks(runnable);
        return jsonObject;

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
