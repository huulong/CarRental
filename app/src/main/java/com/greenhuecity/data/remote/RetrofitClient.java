package com.greenhuecity.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://carrental006.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }

    public static Retrofit getOpenWeatherMapInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        String OPEN_WEATHER_MAP_PATH = Constant.OPEN_WEATHER_MAP_PATH(); // Đường dẫn OpenWeatherMap từ lớp Constant

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_MAP_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }
}
