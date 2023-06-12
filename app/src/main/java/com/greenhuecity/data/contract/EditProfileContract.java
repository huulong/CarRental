package com.greenhuecity.data.contract;

import android.content.Context;

import com.greenhuecity.data.model.Users;

public interface EditProfileContract {
    interface IView {
        void setDataProfile(Users users);

        void updateSuccess();

        void updateFailed(String mess);
    }

    interface IPresenter {
        Users getDataProfileFromShared();

        void updateProfileInformation(int id, String photo, String name, String phone, String email, String address, String age, String cccd, Users users);
        void updateShared(Users users);
    }
}
