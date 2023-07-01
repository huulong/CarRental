package com.greenhuecity.data.contract;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.greenhuecity.data.model.Brands;
import com.greenhuecity.data.model.CarDistributor;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.model.Powers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.http.Field;

public interface UploadCarContract {
    interface IView {
        void setDataDistributorList(List<Distributors> mList);
        void setDataBrandList(List<Brands> mList);

        void setDataPowerList(List<Powers> mList);
        void setDataStatusList(List<String> mList);
        void notifiError(String mess);
        void notifiSuccess();
        void notifiUploadFailed(String mess);
        void setBoldTextCheckbox(SpannableString text);
    }

    interface IPresenter {
        void boldText();
        void getDataList();

        void uploadCar(String car_name,
                       String price,
                       String description,
                       String license_plates,
                       String status,
                       String from_time,
                       String end_time,
                       String approve,
                       int power_id,
                       int brand_id,
                       int user_id,
                       int distributor_id,
                       String top_speed,
                       String horse_power,
                       String mileage,
                       String image_data,
                       String random_photo);
        int getUsersId();
        String generateRandomString();
        void getDayTime(Calendar calendar,  TextView tv, Date startDate, Date endDate, int item);
    }
}
