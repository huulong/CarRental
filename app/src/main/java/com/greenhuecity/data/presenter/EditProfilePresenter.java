package com.greenhuecity.data.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greenhuecity.data.contract.EditProfileContract;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilePresenter implements EditProfileContract.IPresenter {
    EditProfileContract.IView mView;
    Context context;
    ApiService apiService;
    ProgressDialog progressDialog;


    public EditProfilePresenter(EditProfileContract.IView mView, Context context) {
        this.mView = mView;
        this.context = context;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getDataProfileFromShared() {
        SharedPreferences preferences = context.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            Gson gson = new Gson();
            Users users = gson.fromJson(key, Users.class);
            mView.setDataProfile(users);
        }
    }

    @Override
    public void updateProfileInformation(int id, String photo, String fullname, String phone, String email, String address, String age, String cccd) {
        if (id == 0 || fullname.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            mView.updateFailed("Vui lòng nhập thông tin cần thiết");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || !Patterns.PHONE.matcher(phone).matches()) {
            mView.updateFailed("Thông tin không hợp lệ!");
        } else if (!age.isEmpty() && !TextUtils.isDigitsOnly(age)) {
            mView.updateFailed("Tuổi không đúng!");
        }else if(!cccd.isEmpty() && (!TextUtils.isDigitsOnly(cccd) || cccd.length() != 12)){
            mView.updateFailed("Số CCCD không đúng chuẩn!");
        }else {
            progressDialog = ProgressDialog.show(context, "Loading...", "Please wait .....", false, false);
            apiService.updateUser(id, photo, fullname, email, phone, age, cccd, address).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reGetListUser(id);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    mView.updateSuccess();
                                }
                            }, 2000);
                        }
                    }, 1000);

                }
            });

        }
    }

    @Override
    public void reGetListUser(int id) {
        apiService.getUsersById(id).enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> mList = response.body();
                mView.reUpdateSharedUser(mList.get(0));
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });
    }


}
