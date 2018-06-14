package com.huizhong.baselibs.Net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by shaochun on 14-7-9.
 */
public class MyHttp {

    //get请求
    public static JSONArray getJSONArray(String baseUrl) {

        JSONArray jsonArray = null;

        //将URL与参数拼接
        HttpGet getMethod = new HttpGet(baseUrl);

        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);

        HttpClient httpClient = new DefaultHttpClient(httpParams);
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

                if (content == "") return null;

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


        return jsonArray;
    }

    //post 请求
    public static JSONObject HttpPostResponse(String baseUrl, List<BasicNameValuePair> params) {

        JSONObject jsonObject = null;

        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);
        HttpClient httpClient = new DefaultHttpClient(httpParams);
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

        return jsonObject;

    }

    //上传文件
    public static JSONObject upload(String pathToOurFile, String urlServer) {

        HttpClient httpclient = new DefaultHttpClient();
        //设置通信协议版本
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(urlServer);
        File file = new File(pathToOurFile);

        MultipartEntity mpEntity = new MultipartEntity(); //文件传输
        ContentBody cbFile = new FileBody(file);
        mpEntity.addPart("file", cbFile); // <input type="file" name="logfile" />  对应的

        httppost.setEntity(mpEntity);
        System.out.println("executing request " + httppost.getRequestLine());

        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            System.out.println(response.getStatusLine());//通信Ok

            String json = "";
            if (resEntity != null) {
                //System.out.println(EntityUtils.toString(resEntity,"utf-8"));
                json = EntityUtils.toString(resEntity, "utf-8");
                JSONObject p = null;
                try {
                    p = new JSONObject(json);
                    //path=(String) p.get("path");
                    return p;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (resEntity != null) {
                resEntity.consumeContent();
            }

            httpclient.getConnectionManager().shutdown();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
