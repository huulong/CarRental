package com.greenhuecity.data.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.greenhuecity.data.contract.SplashScreenContract;

public class SplashScreenPresenter implements SplashScreenContract.Presenter {

    private SplashScreenContract.View view;
    private Context context;
    private NetworkChangeReceiver networkChangeReceiver;
    private boolean isNetworkConnected;

    public SplashScreenPresenter(SplashScreenContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onCreate() {
        // Khởi tạo BroadcastReceiver
        networkChangeReceiver = new NetworkChangeReceiver();

        // Kiểm tra kết nối mạng trong onCreate()
        isNetworkConnected = isNetworkConnected();
        if (isNetworkConnected) {
            // Thiết bị đã kết nối Internet, chuyển sang MainActivity
            view.startMainActivityDelayed();
        } else {
            view.showNoNetworkError();
        }
    }

    @Override
    public void onStart() {
        // Đăng ký BroadcastReceiver trong onStart()
        context.registerReceiver(networkChangeReceiver, networkChangeReceiver.getIntentFilter());
    }

    @Override
    public void onStop() {
        // Hủy đăng ký BroadcastReceiver trong onStop()
        context.unregisterReceiver(networkChangeReceiver);
    }

    private boolean isNetworkConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private class NetworkChangeReceiver extends android.content.BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isNetworkConnected && isNetworkConnected(context)) {
                // Kết nối mạng đã được thiết lập lại, chuyển sang MainActivity
                isNetworkConnected = true;
                view.startMainActivityDelayed();
            }
        }

        private boolean isNetworkConnected(Context context){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
            return false;
        }

        public IntentFilter getIntentFilter() {
            // Trả về IntentFilter để đăng ký
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            return intentFilter;
        }
    }
}