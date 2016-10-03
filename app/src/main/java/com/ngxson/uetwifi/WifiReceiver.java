package com.ngxson.uetwifi;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

@TargetApi(11)
public class WifiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && info.isConnected()) {
            // Do your work.

            // e.g. To check the Network Name or other info:
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
            SharedPreferences sharedPref = context.getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
            //Log.d("UET Wifi", "connected to "+ssid);
            if(ssid.contains("UET-Wifi")) {
                //start activity
                Intent i = new Intent();
                i.setClassName("com.ngxson.uetwifi", "com.ngxson.uetwifi.LoginUETWifi");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}