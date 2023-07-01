package com.greenhuecity.data.presenter;

import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.data.contract.CarContract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarPresenter implements CarContract.IPresenter {
    CarContract.IView mView;
    ApiService apiService;
    private Handler mHandler;
    private Runnable mRunnable;

    public CarPresenter(CarContract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
        public void getCarList(String brands) {
            apiService.getCarByBrand(brands).enqueue(new Callback<List<Cars>>() {
                @Override
                public void onResponse(Call<List<Cars>> call, Response<List<Cars>> response) {
                    List<Cars> mList = response.body();
                    mView.setDataRecyclerViewCar(mList);

                }

                @Override
                public void onFailure(Call<List<Cars>> call, Throwable t) {

                }
            });
        }

    @Override
    public void startAutoScroll(RecyclerView recyclerView) {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            int nextPosition = 0;
            LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
            @Override
            public void run() {

                int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();
                if (nextPosition < recyclerView.getAdapter().getItemCount()) {
                    if (lastVisible < recyclerView.getAdapter().getItemCount() - 1) {
                        nextPosition = lastVisible + 1;
                    } else {
                        nextPosition = 0;
                    }
                }
                layoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), nextPosition);
                mHandler.postDelayed(this, 10000,10000);
            }
        };
        mHandler.postDelayed(mRunnable,10000);
    }

    @Override
    public void stopAutoScroll() {
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

}
