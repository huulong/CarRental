package com.greenhuecity.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.greenhuecity.R;
import com.greenhuecity.view.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rental);
        initGUI();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               int id = item.getItemId();
                if (id == R.id.nav_home) {

                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.nav_favourite) {
                    viewPager2.setCurrentItem(1);
                    return true;
                } else if (id == R.id.nav_notify) {

                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.nav_setting) {
                    viewPager2.setCurrentItem(3);
                } else return false;

                return true;
            }
        });
        int current = getIntent().getIntExtra("current",0);
        if(current != 0) {
            viewPager2.setCurrentItem(current);
            bottomNavigationView.setSelectedItemId(R.id.nav_setting);
        }
        // Kiểm tra quyền truy cập vị trí
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Quyền truy cập vị trí đã được cấp, bắt đầu sử dụng tính năng liên quan đến vị trí
        } else {
            // Yêu cầu cấp quyền truy cập vị trí
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }

    private void initGUI() {
        viewPager2 = findViewById(R.id.viewpager_body);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager2.setUserInputEnabled(false);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // Nếu yêu cầu bị hủy bỏ, mảng kết quả sẽ rỗng.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Quyền truy cập vị trí đã được cấp, bắt đầu sử dụng tính năng liên quan đến vị trí
                } else {
                    // Yêu cầu cấp quyền truy cập vị trí đã bị từ chối
                }
            }
        }
    }


}