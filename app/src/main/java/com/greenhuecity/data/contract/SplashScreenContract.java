package com.greenhuecity.data.contract;

public interface SplashScreenContract {

    interface View {
        void showNoNetworkError();
        void startMainActivityDelayed();
    }

    interface Presenter {
        void onCreate();
        void onStart();
        void onStop();
    }
}
