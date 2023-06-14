package com.greenhuecity.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.greenhuecity.R;


public class ManagerActivity extends AppCompatActivity {
    Button btnLease, btnOrder, btnRevenue;
    ImageView imgBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        btnRevenue = findViewById(R.id.btn_revenue_statistics);
        btnLease = findViewById(R.id.btn_regis_rental);
        btnOrder = findViewById(R.id.btn_order_rental);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view->onBackPressed());
        eventButton();

    }


    void eventButton() {
        btnOrder.setOnClickListener(view -> startActivity(new Intent(this, OrderManagementActivity.class)));
        btnLease.setOnClickListener(view -> startActivity(new Intent(this, RentalManagementActivity.class)));
        btnRevenue.setOnClickListener(view -> updateNotifiSoon());
    }

    void updateNotifiSoon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Soon");
        builder.setMessage("Tính năng sẽ sớm được cập nhật");
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
    }
}
