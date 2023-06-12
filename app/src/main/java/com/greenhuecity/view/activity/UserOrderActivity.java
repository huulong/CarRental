package com.greenhuecity.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.UserOrderContract;
import com.greenhuecity.data.model.UserOrder;
import com.greenhuecity.data.presenter.UserOrderPresenter;
import com.greenhuecity.itf.OnClickButtonUserOrder;
import com.greenhuecity.view.adapter.UserOrderAdapter;
import com.greenhuecity.view.fragment.bottomsheet.MapBottomSheetFragment;

import java.util.List;

public class UserOrderActivity extends AppCompatActivity implements UserOrderContract.IView {
    RecyclerView rvUserOrder;
    TextView tvTotalOrder;
    ImageView imgBack;
    int user_id;
    UserOrderPresenter mPresenter;
    UserOrderAdapter userOrderAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);
        rvUserOrder = findViewById(R.id.recyclerView_order);
        tvTotalOrder = findViewById(R.id.textView_total_order);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view->onBackPressed());
        rvUserOrder.setHasFixedSize(true);
        rvUserOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //
        mPresenter = new UserOrderPresenter(this, this);
        user_id = mPresenter.getUsersId();
        mPresenter.getUserOrderList(user_id);


    }


    @Override
    public void setDataRecyclerView(List<UserOrder> mList) {
        userOrderAdapter = new UserOrderAdapter(mList, this);
        rvUserOrder.setAdapter(userOrderAdapter);
        eventClickButtonItem();
    }



    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserOrderList(user_id);
    }

    private void eventClickButtonItem() {
        userOrderAdapter.setOnClickButtonOrder(new OnClickButtonUserOrder() {
            @Override
            public void eventCancelOrder(int order_id, int car_id) {
                mPresenter.updateStatusOrder(order_id, "Bị hủy từ khách hàng", car_id, "Đang rảnh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onResume();
                    }
                }, 2000);
            }

            @Override
            public void eventCompleteOrder(int order_id, int car_id) {
                mPresenter.updateStatusOrder(order_id, "Đang được thuê", car_id, "Đang được thuê");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onResume();
                    }
                }, 2000);
            }

            @Override
            public void eventMapView(UserOrder userOrder) {
                MapBottomSheetFragment bottomSheetFragment = MapBottomSheetFragment.newInstance(userOrder);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });
    }
}