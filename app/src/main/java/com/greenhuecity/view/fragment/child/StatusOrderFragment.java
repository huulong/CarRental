package com.greenhuecity.view.fragment.child;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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
import com.greenhuecity.view.adapter.CarAdapter;

import java.util.List;

public class StatusOrderFragment extends Fragment implements StatusOrderContract.IView {
    RecyclerView rvCar;
    StatusOrderPresenter mPresenter;
    CarAdapter mAdapter;
    List<Cars> carList;
    String status;


    public StatusOrderFragment(String status) {
        this.status = status;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statuso, container, false);
        rvCar = view.findViewById(R.id.recyclerView_status);
        rvCar.setHasFixedSize(true);
        rvCar.setNestedScrollingEnabled(false);
        rvCar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mPresenter = new StatusOrderPresenter(this);
        mPresenter.getOrderListByStatus(status);
        return view;
    }

    @Override
    public void setDataRecyclerView(List<UserOrder> mList) {
        mAdapter = new CarAdapter(carList, getContext());
        rvCar.setAdapter(mAdapter);

    }
}
