package com.greenhuecity.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.greenhuecity.R;
import com.greenhuecity.view.adapter.ViewPagerHomeAdapter;
import com.greenhuecity.view.adapter.ViewPagerOrderSAdapter;

public class UserOrderActivity extends AppCompatActivity {
    ImageView imgBack;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);
        initGUI();
        tabLayout.setSaveEnabled(false);

        viewPager2.setAdapter(new ViewPagerOrderSAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Chờ xác nhận");
                    break;
                case 1:
                    tab.setText("Đã xác nhận");
                    break;
                case 2:
                    tab.setText("Đang thuê");
                    break;
                case 3:
                    tab.setText("Đã thuê");
                    break;
                case 4:
                    tab.setText("Bị hủy");
                    break;

            }
        }).attach();
        viewPager2.setUserInputEnabled(false);


    }

    private void initGUI() {
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view -> onBackPressed());
        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewpager2_status);
    }
}