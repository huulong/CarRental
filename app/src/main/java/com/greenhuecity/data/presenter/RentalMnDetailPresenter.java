package com.greenhuecity.data.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.greenhuecity.data.contract.RentailMnDetailContract;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.view.activity.RentalManagementActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentalMnDetailPresenter implements RentailMnDetailContract.IPresenter {
    Context context;
    ApiService apiService;
    ProgressDialog progressDialog;
    RentailMnDetailContract.IView mView;

    public RentalMnDetailPresenter(Context context,  RentailMnDetailContract.IView mView) {
        this.context = context;
        apiService = RetrofitClient.getClient().create(ApiService.class);
        this.mView = mView;
    }


    public void censorshipUpdate(String id, String approve, String mess) {
        progressDialog = ProgressDialog.show(context, "Loading...", "Please wait...", false, false);
        apiService.updateCensored(id, approve).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        mView.notificationUpdate(mess);
                    }
                }, 1000);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

}
