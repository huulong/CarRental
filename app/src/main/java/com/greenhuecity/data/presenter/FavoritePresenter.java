package com.greenhuecity.data.presenter;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.greenhuecity.data.contract.FavoriteContract;
import com.greenhuecity.data.database.FavoriteCarDatabaseHelper;
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
    //
    @Override
    public void getUserLocation(LocationManager locationManager) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastKnownLocation != null){
                LatLng origin = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(origin.latitude, origin.longitude, 1);
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String locality = address.getLocality();
                        String country = address.getCountryName();
                        String addressLine = address.getAddressLine(0);
                        mView.setUserLocation(locality + " " + country);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            ActivityCompat.requestPermissions((MainActivity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
