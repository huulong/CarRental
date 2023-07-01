package com.greenhuecity.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.CarDetailContract;
import com.greenhuecity.data.database.FavoriteCarDatabaseHelper;
import com.greenhuecity.data.model.CarDistributor;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.presenter.CarDetailPresenter;

import java.text.NumberFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class CarDetailActivity extends AppCompatActivity implements CarDetailContract.IView {
    ImageView img, img_heart, imgBack, imgPhone;
    CircleImageView imgDistributors;
    TextView tvName, tvTopSpeed, tvHoursPower, tvMileage, tvDescription, tvPrice, tvDistributors, tvTimeStart, tvTimeEnd, tvStatus;
    LinearLayout btnBooking;
    CarDetailPresenter mPresenter;
    Cars car = null;
    Distributors dist = null;
    FavoriteCarDatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initGUI();

        db = new FavoriteCarDatabaseHelper(this);
        car = (Cars) getIntent().getSerializableExtra("car");
        if (car != null) {
            mPresenter = new CarDetailPresenter(this, this);
            mPresenter.getDataDetailCar(car);
            mPresenter.getDistributors(car.getDistributor_id());
        }
        if (isCheckCarFavoriteDB(car)) img_heart.setImageResource(R.drawable.favorite);
        else img_heart.setImageResource(R.drawable.heart);
        img_heart.setOnClickListener(view -> mPresenter.updateDataFavorite(car, img_heart));


        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (car.getStatus().equals("Đang rảnh")) {
                    if (mPresenter.isLogged()) {
                        Intent intent = new Intent(CarDetailActivity.this, RentCarActivity.class);
                        CarDistributor carDistributor = new CarDistributor(car, dist);
                        if (car != null && dist != null) {
                            intent.putExtra("cars-distributors", carDistributor);
                            startActivity(intent);
                        }
                    } else notloggedIn("Bạn cần đăng nhập để được thuê xe!");
                } else
                    Toast.makeText(CarDetailActivity.this, "Xin lỗi! " + car.getStatus(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (dist != null)
            super.onBackPressed();
    }

    private void initGUI() {
        img = findViewById(R.id.img_detail_car);
        img_heart = findViewById(R.id.img_heart);
        tvName = findViewById(R.id.textView_nameCar);
        tvTopSpeed = findViewById(R.id.textView_km_h);
        tvHoursPower = findViewById(R.id.textView_ps);
        tvMileage = findViewById(R.id.textView_km_l);
        tvDescription = findViewById(R.id.textView_description);
        tvPrice = findViewById(R.id.textView_priceDetail);
        btnBooking = findViewById(R.id.button_booking);
        imgDistributors = findViewById(R.id.imageView_distributor);
        tvDistributors = findViewById(R.id.textView_NameDistributors);
        tvTimeEnd = findViewById(R.id.textView_timeRentEnd);
        tvTimeStart = findViewById(R.id.textView_timeRentStart);
        tvStatus = findViewById(R.id.status);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void setDataDetailCar(Cars car) {
        Glide.with(this).load(car.getCar_img()).into(img);
        tvName.setText(car.getCar_name());
        tvTopSpeed.setText(car.getSpec_top_speed() + "");
        tvHoursPower.setText(car.getSpec_horse_power() + "");
        tvMileage.setText(car.getSpec_mileage() + "");
        tvDescription.setText(car.getDescription());
        //
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String rent_price = currencyFormatter.format(car.getPrice()) + "/ngày";
        tvPrice.setText(rent_price);
        tvTimeStart.setText(String.valueOf(car.getFrom_time()));
        tvTimeEnd.setText(String.valueOf(car.getEnd_time()));
        tvStatus.setText(car.getStatus());

    }

    @Override
    public boolean isCheckCarFavoriteDB(Cars car) {
        for (Cars c : db.getAllCars())
            if (c.getCar_id() == car.getCar_id())
                return true;

        return false;
    }

    @Override
    public void setDistributors(Distributors distributors) {
        dist = distributors;
        tvDistributors.setText(dist.getName());
        Glide.with(this).load(dist.getPhoto()).into(imgDistributors);
    }

    @Override
    public void notloggedIn(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chưa đăng nhập");
        builder.setMessage(mess);
        builder.setNegativeButton("Đăng nhập ngay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 5000);
    }
}
