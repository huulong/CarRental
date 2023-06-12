package com.greenhuecity.data.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greenhuecity.data.contract.SettingContract;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingPresenter implements SettingContract.IPresenter {
    SettingContract.IView mView;
    Context context;
    ApiService apiService;

    public SettingPresenter(SettingContract.IView mView, Context context) {
        this.mView = mView;
        this.context = context;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getDataShared() {
        SharedPreferences preferences = context.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            Gson gson = new Gson();
            Users users = gson.fromJson(key, Users.class);
            mView.setDataSetting(users.getFullname(),users.getPhoto());
        }
    }
    @Override
    public boolean isLogged() {
        SharedPreferences preferences = context.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            return  true;
        }
        return false;
    }

    @Override
    public void getDistributors() {
        apiService.getDistributor().enqueue(new Callback<List<Distributors>>() {
            @Override
            public void onResponse(Call<List<Distributors>> call, Response<List<Distributors>> response) {
                List<Distributors> mList = response.body();
                mView.checkDistributors(mList);
            }

            @Override
            public void onFailure(Call<List<Distributors>> call, Throwable t) {

            }
        });
    }
    @Override
    public int getUsersId() {
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
