package com.greenhuecity.view.activity;

import android.app.AlertDialog;
import android.content.Context;

import android.location.LocationManager;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.RentCarConstract;
import com.greenhuecity.data.model.CarDistributor;
import com.greenhuecity.data.presenter.RentCarPresenter;


import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class RentCarActivity extends AppCompatActivity implements OnMapReadyCallback, RentCarConstract.IView {
    TextView tvName, tvBrand, tvDeparture_day, tvReturn_day, tvDistance, tvLocation, tvPay, tvDistributors, textView_priceDetail, tvStartTimeCar, tvEndTimeEnd;
    ImageView imgCalendarStar, imgCalendarEnd, imgBack;
    CircleImageView igDistributors;
    LinearLayout layoutPay;
    double latitude, longitude, price;
    private LocationManager locationManager;
    CarDistributor carDist;
    RentCarPresenter mPresenter;
    Calendar calendar;
    Date startDate = null;
    Date endDate = null;
    SimpleDateFormat dateFormat;
    NumberFormat currencyFormatter;

    int nbDays = 1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Locale locale = new Locale("vi", "VN");
        currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        calendar = Calendar.getInstance();
        initGUI();
        carDist = (CarDistributor) getIntent().getSerializableExtra("cars-distributors");
        textView_priceDetail.setText(currencyFormatter.format(carDist.getCars().getPrice()) + "/ngày");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        // Khởi tạo LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mPresenter = new RentCarPresenter(this, RentCarActivity.this, locationManager);
        mPresenter.getCurrentDate(calendar);
        mPresenter.getCarDistributor(carDist);
        mapFragment.getMapAsync(this);
        setEventSelectDay();
        eventButtonOrders();
    }

    public void initGUI() {
        tvName = findViewById(R.id.textView_Name);
        tvBrand = findViewById(R.id.textView_brand);
        tvDeparture_day = findViewById(R.id.textView_start);
        tvReturn_day = findViewById(R.id.textView_end);
        imgCalendarStar = findViewById(R.id.calendar_star);
        imgCalendarEnd = findViewById(R.id.calendar_end);
        igDistributors = findViewById(R.id.imageView_distributor);
        tvDistributors = findViewById(R.id.textView_NameDistributors);
        tvLocation = findViewById(R.id.textView_pickup_location);
        tvPay = findViewById(R.id.textView_momo);
        layoutPay = findViewById(R.id.button_paynow);
        tvDistance = findViewById(R.id.textView_distance);
        textView_priceDetail = findViewById(R.id.textView_priceDetail);
        tvStartTimeCar = findViewById(R.id.textView_timeRentStart);
        tvEndTimeEnd = findViewById(R.id.textView_timeRentEnd);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view->onBackPressed());
        //khởi tạo các Calendar

    }

    void eventButtonOrders() {
        layoutPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from_time = tvDeparture_day.getText().toString();
                String end_time = tvReturn_day.getText().toString();
                double price = nbDays * carDist.getCars().getPrice();
                mPresenter.loadOrderProcessing(from_time,end_time,price,carDist.getCars().getCar_id());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mPresenter.onMapReady(googleMap, carDist);
    }


    @Override
    public void setCurrentDate(Calendar mCalendar) {
        Date currentDate = mCalendar.getTime();
        mCalendar.add(Calendar.DATE, 1);
        Date nextDate = mCalendar.getTime();
        tvDeparture_day.setText(dateFormat.format(currentDate));
        tvReturn_day.setText(dateFormat.format(nextDate));
    }

    @Override
    public void setOrderInfo(CarDistributor carDistributor) {
        tvName.setText(carDistributor.getCars().getCar_name());
        tvBrand.setText(carDistributor.getCars().getBrand_name());
        tvDistributors.setText(carDistributor.getDistributors().getName());
        Glide.with(this).load(carDistributor.getDistributors().getPhoto()).into(igDistributors);
        latitude = carDistributor.getDistributors().getLatitude();
        longitude = carDistributor.getDistributors().getLongitude();
        price = carDistributor.getCars().getPrice();
        tvStartTimeCar.setText(String.valueOf(carDistributor.getCars().getFrom_time()));
        tvEndTimeEnd.setText(String.valueOf(carDistributor.getCars().getEnd_time()));
    }

    @Override
    public void setDistance(String formattedDistance) {
        tvDistance.setText(formattedDistance);
    }

    @Override
    public void setAddress(String address) {
        tvLocation.setText(address);
    }

    @Override
    public void setEventSelectDay() {
        imgCalendarStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startDate = dateFormat.parse(tvDeparture_day.getText().toString());
                    endDate = dateFormat.parse(tvReturn_day.getText().toString());
                    mPresenter.getDayTime(calendar, carDist, tvDeparture_day, startDate, endDate, 0);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        imgCalendarEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startDate = dateFormat.parse(tvDeparture_day.getText().toString());
                    endDate = dateFormat.parse(tvReturn_day.getText().toString());
                    mPresenter.getDayTime(calendar, carDist, tvReturn_day, startDate, endDate, 1);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void setTotalRent(int date) {
        nbDays = date;
        textView_priceDetail.setText(currencyFormatter.format((nbDays * carDist.getCars().getPrice())) + "/" + nbDays + "ngày");
    }

    @Override
    public void notifiErrorDate(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi!");
        builder.setMessage(mess);
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }


    @Override
    public void successOrders(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đặt xe thành công!");
        builder.setMessage(mess);
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                onBackPressed();
            }
        }, 2000);
    }

    @Override
    public void failedOrders(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đặt xe thất bại!");
        builder.setMessage(mess);
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }


}
