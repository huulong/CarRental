package com.greenhuecity.data.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.greenhuecity.data.contract.FavoriteContract;
import com.greenhuecity.data.database.FavoriteCarDatabaseHelper;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.view.activity.MainActivity;
import com.greenhuecity.view.activity.SearchActivity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoritePresenter implements FavoriteContract.IPresenter {
    FavoriteContract.IView mView;
    List<Cars> carList;
    FavoriteCarDatabaseHelper db;
    ApiService apiService;
    Context context;

    public FavoritePresenter(FavoriteContract.IView mView,Context context) {
        this.mView = mView;
        carList = new ArrayList<>();
        db = new FavoriteCarDatabaseHelper(context);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        this.context = context;
    }


    @Override
    public void getCarList() {
        if(db.getAllCars() != null && !db.getAllCars().isEmpty()){
            mView.setDataRecyclerViewCar(db.getAllCars());
            mView.setDataExist();
        } else {
            mView.setDataRecyclerViewCar(db.getAllCars());
            mView.setDataEmpty("Danh sách yêu thích trống");
        }
    }
    //
    @Override
    public void getCarListAPI() {
        apiService.getCarByBrand("").enqueue(new Callback<List<Cars>>() {
            @Override
            public void onResponse(Call<List<Cars>> call, Response<List<Cars>> response) {
                List<Cars> mList = response.body();
                mView.getCarsList(mList);
            }

            @Override
            public void onFailure(Call<List<Cars>> call, Throwable t) {

            }
        });
    }

    @Override
    public void searchProcessing(List<Cars> carsList, String inputText) {
        List<Cars> filteredList = new ArrayList<>();
        for (Cars car : carsList) {
            if (car.getCar_name().toLowerCase().contains(inputText)
                    || car.getBrand_name().toLowerCase().contains(inputText)) {
                filteredList.add(car);
            }
        }
        if (filteredList != null && inputText != null && !inputText.isEmpty()) {
            Intent intent = new Intent(context, SearchActivity.class);
            intent.putExtra("list", (Serializable) filteredList);
            context.startActivity(new Intent(intent));
        }else{
            mView.notifiEmptyText();
        }
    }
    //
    @Override
    public void getUserLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Lấy thông tin địa chỉ từ latitude và longitude
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        String address = addresses.get(0).getAddressLine(0);
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();

                        // Gán thông tin địa chỉ vào TextView
                        mView.setUserLocation( state+" "+ country);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void getImgUserFromShared() {
        SharedPreferences preferences = context.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            Gson gson = new Gson();
            Users users = gson.fromJson(key, Users.class);
            mView.setImgUser(users.getPhoto());
        }
    }
}
