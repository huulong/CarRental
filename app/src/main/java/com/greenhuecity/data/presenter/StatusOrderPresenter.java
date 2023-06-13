package com.greenhuecity.data.presenter;

import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.data.contract.CarContract;
import com.greenhuecity.data.contract.StatusOrderContract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusOrderPresenter implements StatusOrderContract.IPresenter {
    StatusOrderContract.IView mView;
    ApiService apiService;
    private Handler mHandler;
    private Runnable mRunnable;

    public StatusOrderPresenter(StatusOrderContract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getOrderListByStatus(String status) {

    }


}
