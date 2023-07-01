package com.greenhuecity.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.greenhuecity.R;
import com.greenhuecity.data.contract.MainContract;
import com.greenhuecity.data.model.SlidePopup;
import com.greenhuecity.data.presenter.MainPresenter;
import com.greenhuecity.view.adapter.ViewPagerAdapter;
import com.greenhuecity.view.adapter.ViewPagerPopUpAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.IView {
    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;
    ViewPager viewPagerPopup;
    RelativeLayout layoutPopup;
    Button btnNext, btnPrev, btnClose;
    Dialog mDialog;
    ViewPagerPopUpAdapter popUpAdapter;
    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rental);
        initGUI();
        initUIPopup();
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
        // Kiểm tra quyền truy cập vị trí
        checkLocationPermission();

//
        mPresenter = new MainPresenter(this);
        mPresenter.getListSlidePopUp();



    }

    private void initGUI() {
        viewPager2 = findViewById(R.id.viewpager_body);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager2.setUserInputEnabled(false);


    }

    private void initUIPopup() {
        mDialog = new Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_layout);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.setCancelable(false);

        //ánh xạ
        layoutPopup = mDialog.findViewById(R.id.relativeLayout_popup);
        viewPagerPopup = mDialog.findViewById(R.id.viewPager_Popup);
        btnClose = mDialog.findViewById(R.id.btnClose);
        btnNext = mDialog.findViewById(R.id.btnNext);
        btnPrev = mDialog.findViewById(R.id.btnPrev);
        layoutPopup.setVisibility(View.VISIBLE);
        mDialog.show();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Quyền truy cập vị trí đã được cấp, bắt đầu sử dụng tính năng liên quan đến vị trí
        } else {
            // Yêu cầu cấp quyền truy cập vị trí
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
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


    @Override
    public void setDataListPopUp(List<SlidePopup> mList) {
        popUpAdapter = new ViewPagerPopUpAdapter(mList, this);
        viewPagerPopup.setAdapter(popUpAdapter);
        isCurrentItemPopUp();
        setEventButtonPopUp();
    }
    void isCurrentItemPopUp(){
        if(viewPagerPopup.getCurrentItem() == 0) btnPrev.setVisibility(View.INVISIBLE);
        else btnPrev.setVisibility(View.VISIBLE);
    }
    @Override
    public void setEventButtonPopUp() {
        viewPagerPopup.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position > 0) {
                    btnPrev.setVisibility(View.VISIBLE);
                }else btnPrev.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setVisibility(View.VISIBLE);
                mDialog.dismiss();
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalItem = viewPagerPopup.getAdapter().getCount();
                int currentItem = viewPagerPopup.getCurrentItem();
                if (currentItem < totalItem - 1) {
                    viewPagerPopup.setCurrentItem(currentItem + 1);
                } else viewPagerPopup.setCurrentItem(0);
                isCurrentItemPopUp();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalItem = viewPagerPopup.getAdapter().getCount();
                int currentItem = viewPagerPopup.getCurrentItem();
                if (currentItem == 0) {
                    viewPagerPopup.setCurrentItem(totalItem - 1);
                } else viewPagerPopup.setCurrentItem(currentItem - 1);
                isCurrentItemPopUp();
            }
        });
    }
}