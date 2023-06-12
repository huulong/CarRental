package com.greenhuecity.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.OrderManagementContract;
import com.greenhuecity.data.model.OrderManagement;
import com.greenhuecity.data.presenter.OrderManagementPresenter;
import com.greenhuecity.itf.UpdateOrderIF;
import com.greenhuecity.view.adapter.OrderManagementAdapter;

import java.util.List;


public class OrderManagementActivity extends AppCompatActivity implements OrderManagementContract.IView {
    RecyclerView rvOrder;
    TextView tvNumberOfOrders;
    ImageView imgBack;
    OrderManagementPresenter mPresenter;
    OrderManagementAdapter orderManagementAdapter;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);
        rvOrder = findViewById(R.id.recyclerView_car);
        tvNumberOfOrders = findViewById(R.id.textView_quantity);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view->onBackPressed());
        rvOrder.setHasFixedSize(true);
        rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mPresenter = new OrderManagementPresenter(this, this);
        id = mPresenter.getUsersId();
        mPresenter.getOrderManagementList(id);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getOrderManagementList(id);
    }

    @Override
    public void setDataRecyclerViewOrderManagement(List<OrderManagement> mList) {
        orderManagementAdapter = new OrderManagementAdapter(mList, this);
        rvOrder.setAdapter(orderManagementAdapter);
        if (mList != null) tvNumberOfOrders.setText(String.valueOf(mList.size()));
        else tvNumberOfOrders.setText(String.valueOf(0));
        if (orderManagementAdapter != null) {
            eventStatusOrder();
        }
    }

    private void eventStatusOrder() {
        orderManagementAdapter.setUpdateOrderIF(new UpdateOrderIF() {
            @Override
            public void updateCofirm(int car_id, int order_id) {
                mPresenter.updateStatusOrder(order_id, "Đã xác nhận", car_id, "Xe đang được thuê");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onResume();
                    }
                }, 2000);
            }

            @Override
            public void updateRefuse(int car_id, int order_id) {
                mPresenter.updateStatusOrder(order_id, "Bị hủy từ nhà phân phối", car_id, "Đang rảnh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onResume();
                    }
                }, 2000);
            }

            @Override
            public void updateComplete(int car_id, int order_id) {
                mPresenter.updateStatusOrder(order_id, "Đã hoàn thành", car_id, "Đang rảnh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onResume();
                    }
                }, 2000);
            }

            @Override
            public void updateCancel(int car_id, int order_id) {
                mPresenter.updateStatusOrder(order_id, "Bị hủy do không nhận xe", car_id, "Đang rảnh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onResume();
                    }
                }, 2000);
            }
        });
    }

}
