package com.ngxson.uetwifi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class LoginUETWifi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_uetwifi);
        //data == html data which you want to load
        WebView webview = (WebView)this.findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("", "<h1>hello</h1>", "text/html", "UTF-8", "");
    }
}
