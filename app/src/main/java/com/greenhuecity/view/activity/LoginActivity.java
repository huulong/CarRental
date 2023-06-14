package com.greenhuecity.view.activity;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.greenhuecity.R;
import com.greenhuecity.view.adapter.ViewPagerLoginAdapter;


public class LoginActivity extends AppCompatActivity {
    public ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.setAdapter(new ViewPagerLoginAdapter(this));
        viewPager2.setUserInputEnabled(false);
        viewPager2.setCurrentItem(0);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }
}
