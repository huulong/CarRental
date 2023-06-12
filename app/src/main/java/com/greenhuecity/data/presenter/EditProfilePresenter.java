package com.greenhuecity.data.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;

import com.google.gson.Gson;
import com.greenhuecity.data.contract.EditProfileContract;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

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
    public Users getDataProfileFromShared() {
        SharedPreferences preferences = context.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            Gson gson = new Gson();
            Users users = gson.fromJson(key, Users.class);
            return users;
        }
        return null;
    }

    @Override
    public void updateProfileInformation(int id, String photo, String fullname, String phone, String email, String address, String age, String cccd, Users users) {
        if (id == 0 || photo == null || fullname.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || age.isEmpty() || cccd.isEmpty()) {
            mView.updateFailed("Vui lòng nhập đầy đủ thông tin");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || !Patterns.PHONE.matcher(phone).matches() ||
                !TextUtils.isDigitsOnly(age) || !TextUtils.isDigitsOnly(cccd) || cccd.length() != 12) {
            mView.updateFailed("Thông tin không hợp lệ!");
        } else {
            progressDialog = ProgressDialog.show(context, "Loading...", "Please wait .....", false, false);
            apiService.updateUser(id, photo, fullname, email, phone, age, cccd, address).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            mView.updateSuccess();
                            Users nUsers = new Users(id, email, users.getPassword(), photo, address, Integer.parseInt(age), fullname, phone, cccd);
                            updateShared(nUsers);
                        }
                    }, 2000);
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            mView.updateSuccess();
                            Users nUsers = new Users(id, email, users.getPassword(), photo, address, Integer.parseInt(age), fullname, phone, cccd);
                            updateShared(nUsers);
                        }
                    }, 2000);
                }
            });
        }
    }

    @Override
    public void updateShared(Users users) {
        Gson gson = new Gson();
        String user = gson.toJson(users);
        SharedPreferences.Editor editor = context.getSharedPreferences("Success", MODE_PRIVATE).edit();
        editor.remove("users");
        editor.putString("users", user);
        editor.apply();
    }
}
