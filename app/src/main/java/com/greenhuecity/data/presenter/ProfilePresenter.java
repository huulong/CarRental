package com.greenhuecity.data.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.greenhuecity.data.contract.ProfileContract;
import com.greenhuecity.data.model.Users;

public class ProfilePresenter implements ProfileContract.IPresenter {
    ProfileContract.IView mView;

    public ProfilePresenter(ProfileContract.IView mView) {
        this.mView = mView;
    }

    @Override
    public void getDataProfileFromShared(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            Gson gson = new Gson();
            Users users = gson.fromJson(key, Users.class);
            mView.setDataProfile(users);
        }
    }
}
