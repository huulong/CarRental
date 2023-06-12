package com.greenhuecity.data.contract;

import android.content.Context;

import com.greenhuecity.data.model.Users;


public interface ProfileContract {
    interface IView{
        void setDataProfile(Users users);
    }
    interface IPresenter{
        void getDataProfileFromShared(Context context);
    }
}
