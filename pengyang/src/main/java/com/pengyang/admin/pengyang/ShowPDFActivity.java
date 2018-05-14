package com.pengyang.admin.pengyang;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.pengyang.com.web.WebService;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ShowPDFActivity extends AppCompatActivity {


    String tmp="";
    String docPath="";
    // 返回主线程更新数据
    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf);

        Intent intent=getIntent();
        String tmp = intent.getStringExtra("pdf_id");
        // 获取控件
        new Thread(new MyThread1()).start();

        Toast.makeText(ShowPDFActivity.this,tmp,Toast.LENGTH_LONG);




    }

    public class MyThread1 implements Runnable{

        @Override
        public void run() {

            Intent intent=getIntent();
            tmp = intent.getStringExtra("pdf_id");
            Map<String,String> showPdfMap = new HashMap<>();
            showPdfMap.put("id",tmp);
            docPath = "http://47.104.83.74:80/"+WebService.executeHttpGet( showPdfMap,"patient/baogao.do");

            Log.e("TAG", "run: "+docPath);
//            webView.loadUrl("http://47.104.83.74:80/ServletAndroidAPP/web/viewer.html?file="+pdfUrl);

        handler.post(new Runnable(){

            @Override
            public void run() {

                WebView pdfViewerWeb = (WebView) findViewById(R.id.webView);


                WebSettings webSettings = pdfViewerWeb.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setAllowFileAccess(true);
                webSettings.setAllowFileAccessFromFileURLs(true);
                webSettings.setAllowUniversalAccessFromFileURLs(true);
                webSettings.setAppCacheEnabled(true);
                if (webSettings.getDatabaseEnabled()) {

                // 装载URL
                pdfViewerWeb.loadUrl("http://47.104.83.74:80/ServletAndroidAPP/web/viewer.html?file=http://47.104.83.74:80/ServletAndroidAPP/patient/showbaogao.do?id=" + tmp);
                //    http://mozilla.github.io/pdf.js/web/viewer.html?file=http://47.104.83.74:80/ServletAndroidAPP/patient/showbaogao.do?id="+tmp
                //   "http://47.104.83.74:80/ServletAndroidAPP/web/viewer.html?file=http://47.104.83.74:80/ServletAndroidAPP/patient/showbaogao.do?id="+tmp
                // 设置WebViewClient来接收处理请求和通知
                }
                pdfViewerWeb.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        // 返回true则表明使用的是WebView
                        return true;
                    }
                });
                // 获取焦点
                pdfViewerWeb.requestFocus();
            }
        });

        }
    }



}
