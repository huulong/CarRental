package com.greenhuecity.data.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;

import com.google.gson.Gson;
import com.greenhuecity.data.contract.LoginContract;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.util.Utils;
import com.greenhuecity.view.activity.LoginActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.IPresenter {
    LoginContract.IView mView;
    LoginActivity loginActivity;
    ApiService apiService;
    boolean isCancelled = false;

    public LoginPresenter(LoginContract.IView mView, LoginActivity loginActivity) {
        this.mView = mView;
        this.loginActivity = loginActivity;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void login(String users, String password, Context context) {
        String password_256 = Utils.sha256(password);
        if (users.isEmpty() || password.isEmpty()) {
            mView.showErrorMessage("Vui lòng nhập tài khoản hoặc mật khẩu!");
        } else {
            apiService.getUsers().enqueue(new Callback<List<Users>>() {
                @Override
                public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                    List<Users> mList = response.body();
                    if (mList != null && !mList.isEmpty()) {
                        for (int i = 0; i < mList.size(); i++) {
                            if ((users.equals(mList.get(i).getPhone()) || users.equals(mList.get(i).getEmail())) && (password_256.equals(mList.get(i).getPassword()) || password.equals(mList.get(i).getPassword()))) {
                                //save tam thoi tai khoan tren app
                                Gson gson = new Gson();
                                String user = gson.toJson(mList.get(i));
                                SharedPreferences.Editor editor = context.getSharedPreferences("Success", MODE_PRIVATE).edit();
                                editor.remove("users");
                                editor.putString("users", user);
                                editor.apply();
                                //dialog success and intent MainActivity
                                mView.showSuccessMessage("Chào mừng đến với ứng dụng của chúng tôi!");
                                return;
                            }
                        }
                        mView.showErrorMessage("Sai tài khoản hoặc mật khẩu");
                    }
                }

                @Override
                public void onFailure(Call<List<Users>> call, Throwable t) {
                }
            });
        }
    }

    @Override
    public void loadingLogin(String user, String password, Context context) {
        ProgressDialog progressDialog = new ProgressDialog(loginActivity);
        progressDialog.setMessage("Loading..."); // Thiết lập thông điệp
        progressDialog.setCancelable(false); // Không cho phép hủy
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Loại spinner
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Huỷ công việc của bạn và ẩn ProgressDialog
                progressDialog.dismiss();
                isCancelled = true;
            }
        });
        // Hiển thị ProgressDialog
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                if (!isCancelled) {
                    login(user, password, context);
                }
                isCancelled = false;
            }
        }, 2000);
    }

}
