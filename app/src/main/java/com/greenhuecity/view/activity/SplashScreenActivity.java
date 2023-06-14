package com.greenhuecity.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.SplashScreenContract;
import com.greenhuecity.data.presenter.SplashScreenPresenter;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {

    private SplashScreenPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        presenter = new SplashScreenPresenter(this, getApplicationContext());
        presenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void showNoNetworkError() {
        Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivityDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }
}