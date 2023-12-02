package com.greenhuecity.data.presenter;

import com.greenhuecity.data.contract.ResetPasswordContract;
import com.greenhuecity.data.remote.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordPresenter implements ResetPasswordContract.Presenter {
    private final ResetPasswordContract.View view;
    private final ApiService apiService;

    public ResetPasswordPresenter(ResetPasswordContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void resetPassword(String email, String oldPassword, String newPassword) {
        // Gọi API để đặt lại mật khẩu
        apiService.resetPassword(email, oldPassword, newPassword)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            view.showResetSuccess(response.body());
                        } else {
                            view.showResetError("Reset password failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        view.showResetError("Network error");
                    }
                });
    }
}
