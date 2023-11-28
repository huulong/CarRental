package com.greenhuecity.data.contract;

import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.greenhuecity.data.model.CarDistributor;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Orders;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public interface RentCarConstract {
    interface IView {
        void setCurrentDate(Calendar mCalendar);

        void setOrderInfo(CarDistributor carDistributor);

        void setDistance(String formattedDistance);

        void setAddress(String addressLine);

        void setEventSelectDay();

        void setTotalRent(int date);

        void notifiErrorDate(String mess);
        void successOrders(String mess);
        void failedOrders(String mess);
        void showPaymentMethods(String[] paymentMethods);

    }

    interface IPresenter {
        void getCarDistributor(CarDistributor carDistributor);

        void onMapReady(GoogleMap googleMap, CarDistributor carDistributor);

        void getAddressFromLatLng(LatLng latLng);

        String distanceToDistributor(LatLng origin, LatLng destination);

        void getDayTime(Calendar calendar, CarDistributor carDistributor, TextView tv, Date startDate, Date endDate, int item);

        void getCurrentDate(Calendar calendar);

        String generateRandomString();

        int getUsersId();

        void updateStatusCars(int id);

        void upOrders(String cod, String from_time, String end_time);

        void upOrderItems(int car_id, int order_id, double price);
        void loadOrderProcessing(String from_time,String end_time,double price,int car_id);
        void showPaymentMethodsDialog();

    }
}
