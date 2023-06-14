package com.greenhuecity.data.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.greenhuecity.data.contract.LeaseCarContract;
import com.greenhuecity.data.model.LeaseCar;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaseCarPresenter implements LeaseCarContract.IPresenter {
    LeaseCarContract.IView mView;
    Context context;
    ApiService apiService;

    public LeaseCarPresenter(LeaseCarContract.IView mView, Context context) {
        this.mView = mView;
        this.context = context;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getCarList(int id) {
        apiService.getLeaseCar(id).enqueue(new Callback<List<LeaseCar>>() {
            @Override
            public void onResponse(Call<List<LeaseCar>> call, Response<List<LeaseCar>> response) {
                List<LeaseCar> mList = response.body();
                mView.setDataRecyclerView(mList);
            }

            @Override
            public void onFailure(Call<List<LeaseCar>> call, Throwable t) {

            }
        });
    }

    @Override
    public int getUserId() {
        SharedPreferences preferences = context.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            Gson gson = new Gson();
            Users users = gson.fromJson(key, Users.class);
            return users.getId();
        }
        return 0;
    }
}
