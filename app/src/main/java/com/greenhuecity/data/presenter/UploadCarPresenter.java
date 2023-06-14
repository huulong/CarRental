package com.greenhuecity.data.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.greenhuecity.data.contract.UploadCarContract;
import com.greenhuecity.data.model.Brands;
import com.greenhuecity.data.model.CarDistributor;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.model.Powers;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.view.activity.LeaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadCarPresenter implements UploadCarContract.IPresenter {
    UploadCarContract.IView mView;
    Context context;
    ApiService apiService;
    ProgressDialog progressDialog;

    public UploadCarPresenter(UploadCarContract.IView mView, Context context) {
        this.mView = mView;
        this.context = context;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getDataList() {
        List<String> statusList = new ArrayList<>();
        statusList.add("Đang rảnh");
        statusList.add("Xe đang được thuê");
        statusList.add("Tạm ngừng cho thuê");
        statusList.add("Xe đang bảo dưỡng");
        //Status
        mView.setDataStatusList(statusList);

        //distributors
        apiService.getDistributor().enqueue(new Callback<List<Distributors>>() {
            @Override
            public void onResponse(Call<List<Distributors>> call, Response<List<Distributors>> response) {
                List<Distributors> mList = response.body();
                mView.setDataDistributorList(mList);

            }

            @Override
            public void onFailure(Call<List<Distributors>> call, Throwable t) {

            }
        });
        //brands
        apiService.getBrand().enqueue(new Callback<List<Brands>>() {
            @Override
            public void onResponse(Call<List<Brands>> call, Response<List<Brands>> response) {
                List<Brands> mList = response.body();
                mView.setDataBrandList(mList);
            }

            @Override
            public void onFailure(Call<List<Brands>> call, Throwable t) {

            }
        });
        //powers
        apiService.getPower().enqueue(new Callback<List<Powers>>() {
            @Override
            public void onResponse(Call<List<Powers>> call, Response<List<Powers>> response) {
                List<Powers> mList = response.body();
                mView.setDataPowerList(mList);
            }

            @Override
            public void onFailure(Call<List<Powers>> call, Throwable t) {

            }
        });
    }

    @Override
    public void uploadCar(String car_name, String price, String description, String license_plates, String status, String from_time, String end_time, String approve,
                          int power_id, int brand_id, int user_id, int distributor_id, String top_speed, String horse_power, String mileage, String image_data, String random_photo) {
        progressDialog = ProgressDialog.show(context, "Upload Image", "Please wait...", false, false);
        if (car_name.isEmpty() || price.isEmpty() || description.isEmpty() || license_plates.isEmpty() || status.isEmpty() || from_time.isEmpty() ||
                end_time.isEmpty() || power_id == 0 || brand_id == 0 || user_id == 0 || distributor_id == 0 || top_speed.isEmpty() ||
                horse_power.isEmpty() || mileage.isEmpty() || image_data == null || image_data.isEmpty()) {
            progressDialog.dismiss();
            mView.notifiUploadFailed("Không được để trống!");
        } else if (!TextUtils.isDigitsOnly(price) || !TextUtils.isDigitsOnly(top_speed) || !TextUtils.isDigitsOnly(horse_power) || !TextUtils.isDigitsOnly(mileage)) {
            progressDialog.dismiss();
            mView.notifiUploadFailed("Định dạng không đúng!");
        } else {
            double mPrice = Double.parseDouble(price);
            double mTopSpeed = Double.parseDouble(top_speed);
            double mHorsePower = Double.parseDouble(horse_power);
            double mMileage = Double.parseDouble(mileage);
            apiService.uploadCars(car_name, mPrice, description, license_plates, status, from_time, end_time,
                    approve, power_id, brand_id, user_id, distributor_id, mTopSpeed, mHorsePower,
                    mMileage, image_data, random_photo).enqueue(new Callback<Cars>() {
                @Override
                public void onResponse(Call<Cars> call, Response<Cars> response) {
                    progressDialog.dismiss();
                    mView.notifiSuccess();
                }

                @Override
                public void onFailure(Call<Cars> call, Throwable t) {
                    progressDialog.dismiss();
                    mView.notifiSuccess();
                }
            });
        }
    }

    @Override
    public int getUsersId() {
        SharedPreferences preferences = context.getSharedPreferences("Success", Context.MODE_PRIVATE);
        String key = preferences.getString("users", "");
        if (!key.isEmpty()) {
            Gson gson = new Gson();
            Users users = gson.fromJson(key, Users.class);
            return users.getId();
        }
        return 0;
    }

    @Override
    public String generateRandomString() {
        int length = 8;
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }

        return sb.toString();
    }

    @Override
    public void getDayTime(Calendar calendar, TextView tv, Date startDate, Date endDate, int item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                // Thiết lập giá trị cho calendar
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Thiết lập TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        // Thiết lập giá trị cho calendar
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        Date chooseDate = calendar.getTime();

                        if (chooseDate.getTime() < currentDate.getTime()) {
                            mView.notifiError("Ngày giờ không hợp lệ");
                            return;
                        }

                        if (item == 0) {
                            long dateRent = (endDate.getTime() - chooseDate.getTime()) / (24 * 60 * 60 * 1000);
                            if (chooseDate.getTime() < endDate.getTime() && dateRent > 1) {
                                tv.setText(sdf.format(chooseDate));
                            } else
                                mView.notifiError("Ngày cho thuê phải nhỏ hơn ngày kết thúc cho thuê 2 ngày");
                        } else if (item == 1) {
                            long dateRent = (chooseDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                            if (chooseDate.getTime() > startDate.getTime() && dateRent > 1) {
                                tv.setText(sdf.format(chooseDate));
                            } else
                                mView.notifiError("Ngày kết thúc cho thuê phải lớn hơn ngày cho thuê 1 ngày");
                        }
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


}
