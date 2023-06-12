package com.greenhuecity.data.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.google.gson.Gson;
import com.greenhuecity.data.contract.OrderManagementContract;

import com.greenhuecity.data.model.OrderManagement;
import com.greenhuecity.data.model.Orders;
import com.greenhuecity.data.model.UpdateOrder;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderManagementPresenter implements OrderManagementContract.IPresenter {
    OrderManagementContract.IView mView;
    ApiService apiService;
    ProgressDialog progressDialog;
    Context context;

    public OrderManagementPresenter(OrderManagementContract.IView mView, Context context) {
        this.mView = mView;
        apiService = RetrofitClient.getClient().create(ApiService.class);
        this.context = context;
    }

    @Override
    public void getOrderManagementList(int id) {
        apiService.getOrderManagement(id).enqueue(new Callback<List<OrderManagement>>() {
            @Override
            public void onResponse(Call<List<OrderManagement>> call, Response<List<OrderManagement>> response) {
                List<OrderManagement> managementList = response.body();
                mView.setDataRecyclerViewOrderManagement(managementList);
            }

            @Override
            public void onFailure(Call<List<OrderManagement>> call, Throwable t) {

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

    @Override
    public void updateStatusOrder(int order_id, String order_status, int car_id, String car_status) {
        progressDialog = ProgressDialog.show(context, "Loading...", "Please wait...", false, false);
        apiService.updateOrders(order_id, order_status, car_id, car_status).enqueue(new Callback<UpdateOrder>() {
            @Override
            public void onResponse(Call<UpdateOrder> call, Response<UpdateOrder> response) {
                loadingDismiss();
            }

            @Override
            public void onFailure(Call<UpdateOrder> call, Throwable t) {
                loadingDismiss();
            }
        });
    }

    private void loadingDismiss() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);
    }
}
