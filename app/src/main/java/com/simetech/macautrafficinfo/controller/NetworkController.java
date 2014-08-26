package com.simetech.macautrafficinfo.controller;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by honsam on 8/23/14.
 */
public class NetworkController {

    public NetworkController() {}

    public boolean isNetworkOn(ConnectivityManager cm) {
        boolean ret = false;

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnected())
            ret = true;

        return ret;
    }
}
