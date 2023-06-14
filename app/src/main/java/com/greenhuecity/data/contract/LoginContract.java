package com.greenhuecity.data.contract;

import android.content.Context;

public interface LoginContract {
    interface IView{
        void showSuccessMessage(String message);
        void showErrorMessage(String message);

    }
    interface IPresenter{
        void login(String name,String password, Context context);

    }
}
