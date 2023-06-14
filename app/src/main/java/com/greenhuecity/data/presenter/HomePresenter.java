package com.greenhuecity.data.presenter;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.greenhuecity.data.contract.CarContract;
import com.greenhuecity.data.contract.HomeContract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.view.activity.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.IPresenter {
    HomeContract.IView mView;
    ApiService apiService;

    public HomePresenter(HomeContract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getCarList() {
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
    public List<Cars> filterCarList(String searchText, List<Cars> carsList) {
        List<Cars> filteredList = new ArrayList<>();
        for (Cars car : carsList) {
            if (car.getCar_name().toLowerCase().contains(searchText)
                    || car.getBrand_name().toLowerCase().contains(searchText)) {
                filteredList.add(car);
            }
        }
        return filteredList;
    }

    @Override
    public void getUserLocation(MainActivity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Lấy thông tin địa chỉ từ latitude và longitude
                    Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
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
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void getImgUserFromShared(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            Gson gson = new Gson();
            Users users = gson.fromJson(key, Users.class);
            mView.setImgUser(users.getPhoto());
        }
    }
}
