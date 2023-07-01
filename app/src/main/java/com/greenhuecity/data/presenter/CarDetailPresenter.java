package com.greenhuecity.data.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.Toast;

import com.greenhuecity.R;

import com.greenhuecity.data.contract.CarDetailContract;
import com.greenhuecity.data.database.FavoriteCarDatabaseHelper;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.view.activity.CarDetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarDetailPresenter implements CarDetailContract.IPresenter {
    CarDetailContract.IView mView;
    FavoriteCarDatabaseHelper db;
    CarDetailActivity activity;
    ApiService apiService;



    public CarDetailPresenter(CarDetailContract.IView mView,CarDetailActivity activity) {
        this.mView = mView;
        this.activity = activity;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }


    @Override
    public void getDataDetailCar(Cars car) {
        mView.setDataDetailCar(car);
    }

    @Override
    public void updateDataFavorite(Cars car, ImageView img) {
        db = new FavoriteCarDatabaseHelper(activity);
        if(!mView.isCheckCarFavoriteDB(car)) {
            img.setImageResource(R.drawable.favorite);
            db.addCar(car);
            Toast.makeText(activity, db.getAllCars().size() + "", Toast.LENGTH_SHORT).show();
        }
        else {
            img.setImageResource(R.drawable.heart);
            db.deleteCar(car.getCar_id());
        }


    }

    @Override
    public void getDistributors(int id) {
        apiService.getDistributors(id).enqueue(new Callback<List<Distributors>>() {
            @Override
            public void onResponse(Call<List<Distributors>> call, Response<List<Distributors>> response) {
                List<Distributors> mList = response.body();
                if(mList != null){
                    mView.setDistributors(mList.get(0));
                }
            }

            @Override
            public void onFailure(Call<List<Distributors>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean isLogged() {
        SharedPreferences preferences = activity.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
           return  true;
        }
        return false;
    }
}
