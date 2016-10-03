package com.ngxson.uetwifi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class LoginUETWifi extends AppCompatActivity {

    final String REDIRECTTO = "http://ngxson.github.io/uet/okay_wifi.html";
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_uetwifi);
        //data == html data which you want to load

        SharedPreferences sharedPref = this.getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String user = sharedPref.getString("uet_user", "username");
        String pass = sharedPref.getString("uet_pass", "password");

        WifiManager wifiManager = (WifiManager) getSystemService (Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo ();
        String ssid  = info.getSSID();

        if(!ssid.contains("UET-Wifi")) {
            Toast.makeText(this, "Chưa kết nối vào UET-Wifi", Toast.LENGTH_LONG).show();
            finishdelayed();
        }

        if(user.contains("username") && pass.contains("password")) {
            Toast.makeText(this, "Chưa có id và pass", Toast.LENGTH_LONG).show();
            finishdelayed();
        }

        String html = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body> <script>var MSSV = \""+
                user+"\";var PASS = \""+
                pass+"\";</script><form method=\"post\" action=\"https://fw.uet.vnu.edu.vn:8003/index.php?zone=cpzone\"> <input name=\"redirurl\" type=\"hidden\" value=\""+
                REDIRECTTO+"\"><input name=\"auth_user\" type=\"text\"><input name=\"auth_pass\" type=\"password\"><input name=\"accept\" type=\"hidden\" value=\"Continue\"><input name=\"accept\" type=\"submit\" value=\"Continue\"></form><script>document.getElementsByTagName(\"input\")[1].value=MSSV;document.getElementsByTagName(\"input\")[2].value=PASS;setTimeout(function() {document.getElementsByTagName(\"form\")[0].submit();}, "+
                "5000"+");</script></body></html>";

        webview = (WebView)this.findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(!url.contentEquals("about:blank"))
                    if (url.contains(REDIRECTTO)) loginDone();
                    else if (url.contains("zone=cpzone")) error();
                Log.d("uetwv", "url="+url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                //Your code to do
                error();
            }
        });
    }

    void finishdelayed() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                kill_activity();
            }
        }, 500);
    }

    private void loginDone() {
        Toast.makeText(this, "UET-Wifi: Okay!" , Toast.LENGTH_SHORT).show();
        finishdelayed();
    }

    public void error() {
        Toast.makeText(this, "UET-Wifi: Có 1 lỗi nào đó..." , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
    }

    public void thoatra(View v) {
        kill_activity();
    }

    public void onDestroy() {
        webview.loadDataWithBaseURL("", "<html></html>", "text/html", "UTF-8", "");
        super.onDestroy();
    }

    void kill_activity()
    {
        finish();
    }
}
