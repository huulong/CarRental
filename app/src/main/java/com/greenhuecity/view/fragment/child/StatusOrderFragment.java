package com.greenhuecity.view.fragment.child;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.StatusOrderContract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.UserOrder;
import com.greenhuecity.data.presenter.CarPresenter;
import com.greenhuecity.data.presenter.StatusOrderPresenter;
import com.greenhuecity.itf.OnClickButtonUserOrder;
import com.greenhuecity.view.adapter.CarAdapter;
import com.greenhuecity.view.adapter.UserOrderAdapter;
import com.greenhuecity.view.fragment.bottomsheet.MapBottomSheetFragment;

import java.util.List;

public class StatusOrderFragment extends Fragment implements StatusOrderContract.IView {
    RecyclerView rvOrder;
    StatusOrderPresenter mPresenter;
    UserOrderAdapter mAdapter;
    String status;

    int id = 0;


    public StatusOrderFragment(String status) {
        this.status = status;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statuso, container, false);
        rvOrder = view.findViewById(R.id.recyclerView_status);
        rvOrder.setHasFixedSize(true);
        rvOrder.setNestedScrollingEnabled(false);
        rvOrder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mPresenter = new StatusOrderPresenter(this,getActivity());
        id = mPresenter.getUsersId();
        mPresenter.getOrderListByStatus(id, status);
        return view;
    }

    @Override
    public void setDataRecyclerView(List<UserOrder> mList) {
        mAdapter = new UserOrderAdapter(mList,getActivity());
        rvOrder.setAdapter(mAdapter);
        eventClickButtonItem();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getOrderListByStatus(id,status);
    }

    private void eventClickButtonItem() {
        mAdapter.setOnClickButtonOrder(new OnClickButtonUserOrder() {
            @Override
            public void eventCancelOrder(int order_id, int car_id) {
                mPresenter.updateStatusOrder(order_id, "Bị hủy", car_id, "Đang rảnh");
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
                bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });
    }
}
