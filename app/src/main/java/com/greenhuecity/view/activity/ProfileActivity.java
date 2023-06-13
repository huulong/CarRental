package com.greenhuecity.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.contract.ProfileContract;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.presenter.ProfilePresenter;


public class ProfileActivity extends AppCompatActivity implements ProfileContract.IView {
    TextView tvUserName, tvAddress, tvEmail, tvPhone, tvCCCD;
    ImageView imgUser, btnEdit,imgBack;
    ProfilePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initGUI();
        mPresenter = new ProfilePresenter(this);
        mPresenter.getDataProfileFromShared(this);
        btnEdit.setOnClickListener(view -> startActivity(new Intent(this, EditProfileActivity.class)));
    }

    private void initGUI() {
        tvUserName = findViewById(R.id.textView_userName);
        tvAddress = findViewById(R.id.textView_address);
        tvEmail = findViewById(R.id.textView_email);
        tvPhone = findViewById(R.id.textView_phone);
        tvCCCD = findViewById(R.id.textView_cccd);
        imgUser = findViewById(R.id.imageView_user);
        btnEdit = findViewById(R.id.button_edit);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view->onBackPressed());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("current",3);
        startActivity(intent);
    }

    @Override
    public void setDataProfile(Users users) {
        if (users != null) {
            if (users.getFullname() != null) tvUserName.setText(users.getFullname());
            if (users.getAddress() != null) tvAddress.setText(users.getAddress());
            if (users.getEmail() != null) tvEmail.setText(users.getEmail());
            if (users.getPhone() != null) tvPhone.setText(users.getPhone());
            if (users.getCccd() != null) tvCCCD.setText(users.getCccd());
            if (users.getPhoto() != null) Glide.with(this).load(users.getPhoto()).into(imgUser);
        }
    }
}
