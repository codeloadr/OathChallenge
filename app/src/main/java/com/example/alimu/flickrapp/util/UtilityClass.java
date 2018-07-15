package com.example.alimu.flickrapp.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.example.alimu.flickrapp.R;

import java.util.ArrayList;
import java.util.List;

public class UtilityClass {
    static AlertDialog.Builder mAlertDialogBuilder;
    static AlertDialog mAlertDialog;

    public static boolean checkNetworkConnectivity(Context context){
        NetworkInfo wifiState;
        NetworkInfo dataState;
        ConnectivityManager connManager;

        connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        wifiState = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        dataState = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(!wifiState.isConnected() && !dataState.isConnected()){
            return false;
        }

        return true;
    }

    public static void createAlertDialog(final Context context){
        if(mAlertDialogBuilder == null) {
            mAlertDialogBuilder = new AlertDialog.Builder(context);
        }

        mAlertDialogBuilder.setTitle(context.getString(R.string.alert_title_text));
        mAlertDialogBuilder.setMessage(context.getString(R.string.alert_msg_text));
        mAlertDialogBuilder.setNegativeButton(context.getString(R.string.alert_negative_button_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(myIntent);
            }
        });
        mAlertDialogBuilder.setPositiveButton(context.getString(R.string.alert_positive_button_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                context.startActivity(myIntent);
            }
        });
        clearAlertDialog();
        mAlertDialog = mAlertDialogBuilder.show();
    }

    public static void clearAlertDialog(){
        if(mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    public static class sharedVariables {
        public static final int RECYCLER_VIEW_COLUMNS_COUNT = 3;
        public static int pageNumber = 1;
        public static int pagesCount;
        public static boolean isPageLoaded = false;
        public static boolean isInternetRequested = false;
        public static List<String> imageUrlList = new ArrayList<>();
    }
}
