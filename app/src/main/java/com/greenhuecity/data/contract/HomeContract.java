package com.greenhuecity.data.contract;

import android.content.Context;
import android.location.LocationManager;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.greenhuecity.data.model.Cars;
import com.greenhuecity.view.activity.MainActivity;

import java.util.List;

public interface HomeContract {
    interface IView{
        void getCarsList(List<Cars> carsList);
        void setUserLocation(String address);
        void setImgUser(String url);
        void notifiEmptyText();
        void setListBanner(List<String> mList);
    }
    interface IPresenter{
        void getCarList();
        void getUserLocation(MainActivity activity);
        void getImgUserFromShared(Context context);
        void searchProcessing(List<Cars> filterCarList,String inputText);
        void getBanner();
        void sliderAuto(ViewPager2 viewPager2);
        void stopAutoScroll();

    }
}
