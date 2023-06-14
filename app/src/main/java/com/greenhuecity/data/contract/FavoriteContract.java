package com.greenhuecity.data.contract;

import android.content.Context;
import android.location.LocationManager;

import com.greenhuecity.data.model.Cars;
import com.greenhuecity.view.activity.MainActivity;

import java.util.List;

public interface FavoriteContract {
    interface IView {
        void setDataRecyclerViewCar(List<Cars> mList);

        void setDataEmpty(String mess);

        void setDataExist();

        void notifiEmptyText();

        void getCarsList(List<Cars> carsList);

        void setUserLocation(String address);

        void setImgUser(String url);
    }

    interface IPresenter {
        void getCarList();

        void getCarListAPI();

        void searchProcessing(List<Cars> filterCarList, String inputText);

        void getUserLocation();

        void getImgUserFromShared();
    }
}
