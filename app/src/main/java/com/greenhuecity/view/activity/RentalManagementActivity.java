package com.greenhuecity.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.RentalManagementContract;
import com.greenhuecity.data.model.RentManagement;
import com.greenhuecity.data.presenter.RentalManagementPresenter;
import com.greenhuecity.view.adapter.RentManagementAdapter;

import java.util.List;


public class RentalManagementActivity extends AppCompatActivity implements RentalManagementContract.IView {
    RecyclerView rvOrder;
    TextView tvQuantity;
    ImageView imgBack;
    RentalManagementPresenter mPresenter;
    RentManagementAdapter rentMnRvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_management);
        rvOrder = findViewById(R.id.recyclerView_car);
        tvQuantity = findViewById(R.id.textView_quantity);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view->onBackPressed());
        rvOrder.setHasFixedSize(true);
        rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mPresenter = new RentalManagementPresenter(this);
        mPresenter.getRentManagementList(mPresenter.getUsersId(this));

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ManagerActivity.class));
    }

    @Override
    public void setDataRecyclerView(List<RentManagement> mList) {
        rentMnRvAdapter = new RentManagementAdapter(mList, this);
        rvOrder.setAdapter(rentMnRvAdapter);
        if (mList != null) tvQuantity.setText(String.valueOf(mList.size()));
        else tvQuantity.setText(String.valueOf(0));
    }

}
