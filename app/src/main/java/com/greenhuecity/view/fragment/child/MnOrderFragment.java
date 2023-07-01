package com.greenhuecity.view.fragment.child;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.greenhuecity.R;
import com.greenhuecity.data.contract.MnOrderFragmentContract;
import com.greenhuecity.data.model.OrderManagement;
import com.greenhuecity.data.presenter.MnOrderFragmentPresenter;
import com.greenhuecity.itf.UpdateOrderIF;
import com.greenhuecity.view.adapter.OrderManagementAdapter;


import java.util.List;

public class MnOrderFragment extends Fragment implements MnOrderFragmentContract.IView {
    int id;
    String stt;
    RecyclerView rvOrder;

    View view;
    OrderManagementAdapter orderManagementAdapter;
    MnOrderFragmentPresenter mPresenter;
    public MnOrderFragment(String stt) {
        this.stt = stt;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mn_rental, container, false);
        initGUI();
        mPresenter = new MnOrderFragmentPresenter(this,getActivity());
        id = mPresenter.getUsersId();
        mPresenter.getOrderManagementList(id,stt);
        return view;
    }
    private void initGUI(){
        rvOrder = view.findViewById(R.id.recyclerView_mn_rental);
        rvOrder.setHasFixedSize(true);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setDataRecyclerViewOrderManagement(List<OrderManagement> mList) {
        orderManagementAdapter = new OrderManagementAdapter(mList, getActivity());
        rvOrder.setAdapter(orderManagementAdapter);
        if (orderManagementAdapter != null) {
            eventStatusOrder();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getOrderManagementList(id,stt);
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

                mPresenter.updateStatusOrder(order_id, "Bị hủy", car_id, "Đang rảnh");
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
                mPresenter.updateStatusOrder(order_id, "Bị hủy", car_id, "Đang rảnh");
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
