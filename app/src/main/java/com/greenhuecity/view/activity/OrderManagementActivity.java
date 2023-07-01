package com.greenhuecity.view.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.greenhuecity.R;
import com.greenhuecity.view.adapter.ViewPagerMnOrderAdapter;


public class OrderManagementActivity extends AppCompatActivity {
    ImageView imgBack;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mn_order);
        initGUI();

        viewPager2.setAdapter(new ViewPagerMnOrderAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Chờ xác nhận");
                    break;
                case 1:
                    tab.setText("Đã xác nhận");
                    break;
                case 2:
                    tab.setText("Đang cho thuê");
                    break;
                case 3:
                    tab.setText("Đã hoàn thành");
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
        imgBack.setOnClickListener(view->onBackPressed());
        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewpager2_mno);
        tabLayout.setSaveEnabled(false);
    }


}
