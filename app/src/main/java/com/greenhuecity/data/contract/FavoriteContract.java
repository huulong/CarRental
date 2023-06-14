package com.greenhuecity.data.contract;

import android.content.Context;
import android.location.LocationManager;

import com.greenhuecity.data.model.Cars;
import com.greenhuecity.view.activity.MainActivity;

import java.util.List;

public interface FavoriteContract {
    interface IView{
        void setDataRecyclerViewCar(List<Cars> mList);
        void setDataEmpty(String mess);
        void setDataExist();
        void searchTextChangedListener(List<Cars> carsList);
        void getCarsList(List<Cars> carsList);
        void setUserLocation(String address);
        void setImgUser(String url);
    }
    interface IPresenter{
        void getCarList();
        void getCarListAPI();
        List<Cars> filterCarList(String searchText,List<Cars> carsList);
        void getUserLocation();
        void getImgUserFromShared();
    }
}
