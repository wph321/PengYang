package com.pengyang.com.web;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 2018/3/29.
 */

public class WebServicePost {

    private static String IP = "47.104.83.74:80/ServletAndroidAPP/";

    public static String executeHttpPost(Map<String, String> loginMap, String address) {


        HttpURLConnection conn = null;
        String urlStr = "http://" + IP + address;
        InputStream is = null;
        String resultData = "";
        String param = "";
        try {
            URL url = new URL(urlStr); //URL对象
            conn = (HttpURLConnection) url.openConnection(); //使用URL打开一个链接,下面设置这个连接
            conn.setRequestMethod("POST"); //使用POST请求
            //参数字符串


                Iterator<String> iter = loginMap.keySet().iterator();
                while(iter.hasNext()){
                    String key=iter.next();
                    String value = loginMap.get(key);
                    param += key+"=" + URLEncoder.encode(value, "UTF-8")//服务器不识别汉字
                            + "&";
                }


//            Log.e(TAG, param );

            //用输出流向服务器发出参数，要求字符，所以不能直接用getOutputStream
            OutputStream dos = conn.getOutputStream();
            dos.write(param.getBytes());
            dos.flush();
            dos.close();

            if (conn.getResponseCode() == 200) {//返回200表示相应成功
                is = conn.getInputStream();   //获取输入流
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bufferReader = new BufferedReader(isr);
                String inputLine = "";
                while ((inputLine = bufferReader.readLine()) != null) {
                    return inputLine;
                }
                return resultData;
//                showRes("post方法取回内容：" + resultData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            return null;
    }
}
