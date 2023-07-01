package com.greenhuecity.view.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.greenhuecity.R;

public class TutorialRentActivity extends AppCompatActivity {
    ImageView imgBack;
    WebView webYtb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorialrent);
        imgBack = findViewById(R.id.img_back);
        webYtb = findViewById(R.id.webview_ytb);
        imgBack.setOnClickListener(view->onBackPressed());
        webYtb.getSettings().setJavaScriptEnabled(true);
        webYtb.setWebChromeClient(new WebChromeClient());
        webYtb.setWebViewClient(new WebViewClient());
        String urlYTB = "https://carrental006.000webhostapp.com/video/tutorial-rental.mp4";
        webYtb.loadUrl(urlYTB);
    }
}