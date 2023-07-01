package com.greenhuecity.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.greenhuecity.R;

public class SupportActivity extends AppCompatActivity {
    ImageView imgBack,imgCompany,imgPolicy,imgResolution,imgWeb;
    Button btnCall,btnMess;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        initGUI();
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0396647951"));
                startActivityForResult(callIntent, 0);
            }
        });

        btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.me/huulongmedia"));
                startActivity(intent);
            }
        });

        imgCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://devlong.id.vn/Info.html"; // Đường dẫn tới trang web cần hiển thị
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        imgPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://devlong.id.vn/chinhsach.html"; // Đường dẫn tới trang web cần hiển thị
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        imgResolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://devlong.id.vn/tranhchap.html"; // Đường dẫn tới trang web cần hiển thị
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        imgWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://devlong.id.vn/"; // Đường dẫn tới trang web cần hiển thị
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private void initGUI() {
        imgBack = findViewById(R.id.img_back);
        imgCompany = findViewById(R.id.imageViewCompany);
        imgResolution = findViewById(R.id.imageViewResolution);
        imgPolicy = findViewById(R.id.imageViewPolicy);
        imgWeb = findViewById(R.id.imageViewWeb);
        btnMess = findViewById(R.id.buttonMess);
        btnCall = findViewById(R.id.buttonCall);
    }
}
