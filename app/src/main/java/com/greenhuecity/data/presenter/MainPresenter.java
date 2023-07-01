package com.greenhuecity.data.presenter;

import com.greenhuecity.data.contract.MainContract;
import com.greenhuecity.data.model.SlidePopup;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.IPresenter {
    ApiService apiService;
    MainContract.IView mView;

    public MainPresenter(MainContract.IView mView) {
        apiService = RetrofitClient.getClient().create(ApiService.class);
        this.mView = mView;
    }

    @Override
    public void getListSlidePopUp() {
        apiService.getSlidePopup().enqueue(new Callback<List<SlidePopup>>() {
            @Override
            public void onResponse(Call<List<SlidePopup>> call, Response<List<SlidePopup>> response) {
                List<SlidePopup> mList = response.body();
                mView.setDataListPopUp(mList);
            }

            @Override
            public void onFailure(Call<List<SlidePopup>> call, Throwable t) {

            }
        });
    }
}
