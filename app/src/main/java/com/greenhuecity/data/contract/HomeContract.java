package com.greenhuecity.data.contract;

import android.content.Context;
import android.location.LocationManager;

import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.data.model.Cars;
import com.greenhuecity.view.activity.MainActivity;

import java.util.List;

public interface HomeContract {
    interface IView{

        void searchTextChangedListener(List<Cars> carsList);
        void getCarsList(List<Cars> carsList);
        void setUserLocation(String address);
        void setImgUser(String url);
    }
    interface IPresenter{
        void getCarList();
        List<Cars> filterCarList(String searchText,List<Cars> carsList);
        void getUserLocation(MainActivity activity);
        void getImgUserFromShared(Context context);
    }
}
