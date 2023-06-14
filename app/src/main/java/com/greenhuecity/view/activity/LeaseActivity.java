package com.greenhuecity.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.LeaseCarContract;
import com.greenhuecity.data.model.LeaseCar;
import com.greenhuecity.data.presenter.LeaseCarPresenter;
import com.greenhuecity.view.adapter.LeaseCarAdapter;

import java.util.List;

public class LeaseActivity extends AppCompatActivity implements LeaseCarContract.IView {
    ImageView imgBack;
    Button btnAdd;
    RecyclerView recyclerView;
    TextView tvTotalCarRental;
    LeaseCarAdapter mAdapter;

    int userID = 0;

    LeaseCarPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease);
        initGUI();
        eventClickButton();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(LeaseActivity.this, LinearLayoutManager.VERTICAL, false));

        mPresenter = new LeaseCarPresenter(this, LeaseActivity.this);
        userID = mPresenter.getUserId();
        if (userID != 0) mPresenter.getCarList(userID);
    }


    private void initGUI() {
        imgBack = findViewById(R.id.img_back);
        btnAdd = findViewById(R.id.button_addCar);
        recyclerView = findViewById(R.id.recyclerView_car);
        tvTotalCarRental = findViewById(R.id.textView_quantity);
    }

    private void eventClickButton() {
        imgBack.setOnClickListener(view -> onBackPressed());
        btnAdd.setOnClickListener(view1 -> startActivity(new Intent(this, UploadCarsActivity.class)));
    }

    @Override
    public void setDataRecyclerView(List<LeaseCar> mList) {
        mAdapter = new LeaseCarAdapter(mList, this);
        recyclerView.setAdapter(mAdapter);
        if(mList != null) tvTotalCarRental.setText(String.valueOf(mList.size()));
    }
}