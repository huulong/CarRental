package com.greenhuecity.data.contract;

public interface ResetPasswordContract {
    interface View {
        void showResetSuccess(String message);
        void showResetError(String error);
    }

    interface Presenter {
        void resetPassword(String email, String oldPassword, String newPassword);
    }
}
