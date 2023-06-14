package com.greenhuecity.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.greenhuecity.R;
import com.greenhuecity.data.contract.RentailMnDetailContract;
import com.greenhuecity.data.model.RentManagement;
import com.greenhuecity.data.presenter.RentalMnDetailPresenter;


import java.text.NumberFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RentMnDetailActivity extends AppCompatActivity implements RentailMnDetailContract.IView{
    ImageView img,imgBack;
    CircleImageView imgUsers;
    TextView tvNameCar, tvTopSpeed, tvHoursPower, tvMileage, tvDescription, tvPrice, tvUsers, tvTimeStart, tvTimeEnd, tvPlates;
    Button btnConfirm, btnRefuse;
    RentManagement rentManagement;
    RentalMnDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_management_dt);
        initGUI();
        //get intent
        rentManagement = (RentManagement) getIntent().getSerializableExtra("list");
        setData();
        mPresenter = new RentalMnDetailPresenter(this,this);
        eventButton();

    }

    private void initGUI() {
        img = findViewById(R.id.img_detail_car);
        tvNameCar = findViewById(R.id.textView_nameCar);
        tvTopSpeed = findViewById(R.id.textView_km_h);
        tvHoursPower = findViewById(R.id.textView_ps);
        tvMileage = findViewById(R.id.textView_km_l);
        tvDescription = findViewById(R.id.textView_description);
        tvPrice = findViewById(R.id.textView_price);
        imgUsers = findViewById(R.id.imageView_users);
        tvUsers = findViewById(R.id.textView_users);
        tvTimeEnd = findViewById(R.id.textView_timeRentEnd);
        tvTimeStart = findViewById(R.id.textView_timeRentStart);
        tvPlates = findViewById(R.id.textView_license_plates);
        btnConfirm = findViewById(R.id.button_confirm);
        btnRefuse = findViewById(R.id.button_refuse);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view->onBackPressed());
    }

    private void setData() {
        Glide.with(this).load(rentManagement.getCar_img()).into(img);
        tvNameCar.setText(rentManagement.getCar_name());
        tvTopSpeed.setText(rentManagement.getSpec_top_speed() + "");
        tvHoursPower.setText(rentManagement.getSpec_horse_power() + "");
        tvMileage.setText(rentManagement.getSpec_mileage() + "");
        tvDescription.setText(rentManagement.getDescription());
        //
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String rent_price = currencyFormatter.format(rentManagement.getPrice()) + "/ngày";
        tvPrice.setText(rent_price);
        tvTimeStart.setText(String.valueOf(rentManagement.getFrom_time()));
        tvTimeEnd.setText(String.valueOf(rentManagement.getEnd_time()));
        tvPlates.setText(rentManagement.getLicense_plates());
        tvUsers.setText(rentManagement.getFullname());
        if (rentManagement.getUser_photo() != null)
            Glide.with(this).load(rentManagement.getUser_photo()).into(imgUsers);
    }
    private void eventButton(){
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.censorshipUpdate(rentManagement.getCar_id()+"","Yes","Đã xác nhận đơn đăng ký thuê");
            }
        });
        btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.censorshipUpdate(rentManagement.getCar_id()+"","No","Đã từ chối đơn đăng ký thuê");
            }
        });
    }


    @Override
    public void notificationUpdate(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage(mess);
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                onBackPressed();
            }
        },2000);

    }
}
