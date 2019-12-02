package com.zhuan.jetpacksample.livedata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.LiveData;

/**
 * description：   <br/>
 * ===============================<br/>
 * creator：Jiacheng<br/>
 * create time：2019-12-02 15:57<br/>
 * ===============================<br/>
 * reasons for modification：  <br/>
 * Modifier：  <br/>
 * Modify time：  <br/>
 */
public class NetworkLiveData extends LiveData<NetworkInfo> {

    private static NetworkLiveData instance;
    private Context mContext;
    private NetworkReceiver mNetworkReceiver;
    private final IntentFilter mIntentFilter;

    public NetworkLiveData(Context ctx) {
        this.mContext = ctx.getApplicationContext();
        mNetworkReceiver = new NetworkReceiver();
        mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    public static NetworkLiveData getInstance(Context ctx) {
        if (instance == null) {
            instance = new NetworkLiveData(ctx);
        }
        return instance;
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.e("NetworkLiveData", "onActive");
        mContext.registerReceiver(mNetworkReceiver, mIntentFilter);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.e("NetworkLiveData", "onInactive");
        mContext.unregisterReceiver(mNetworkReceiver);
    }

    private static class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            getInstance(context).setValue(activeNetwork);
            Log.e("NetworkReceiver", "NetworkReceiver#onReceive");
        }
    }
}
