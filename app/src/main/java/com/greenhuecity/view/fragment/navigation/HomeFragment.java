package com.greenhuecity.view.fragment.navigation;


import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import android.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;
import com.greenhuecity.Helper.Tooltip;
import com.greenhuecity.R;
import com.greenhuecity.WeatherForecast.WeatherForecast;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.Constant;
import com.greenhuecity.data.remote.RetrofitClient;
import com.greenhuecity.view.activity.ChatBotActivity;
import com.greenhuecity.view.activity.MainActivity;

import com.greenhuecity.data.contract.HomeContract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.presenter.HomePresenter;
import com.greenhuecity.view.adapter.BannerAdapter;
import com.greenhuecity.view.adapter.ViewPagerHomeAdapter;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements HomeContract.IView {
    TabLayout tabLayout;
    ViewPager2 viewPager2, viewPagerBanner;
    CircleIndicator3 circleIndicator3;
//    Timer timer;
    TextInputLayout inputLayout;
    private Context context;
    AutoCompleteTextView completeTextView;
    ImageButton btnSearch;
    TextView tvLocation;
    CircleImageView imgUser;
    ImageView imagechat;
    View view;
    List<Cars> carsList;
    HomePresenter mPresenter;
    String textSearch = "";
    BannerAdapter bannerAdapter;
    private TextView txtDate;
    private TextView txtWeather;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initGUI();
        tabLayout.setSaveEnabled(false);
        viewPager2.setAdapter(new ViewPagerHomeAdapter(this.getActivity()));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Nổi bật");
                    break;
                case 1:
                    tab.setText("Honda");
                    break;
                case 2:
                    tab.setText("Yamaha");
                    break;
                case 3:
                    tab.setText("Ducati");
                    break;
                case 4:
                    tab.setText("BMW");
                    break;
                case 5:
                    tab.setText("Aprilia");
                    break;

            }
        }).attach();
        viewPager2.setUserInputEnabled(false);
        mPresenter = new HomePresenter(this, getActivity());
        mPresenter.getCarList();
        mPresenter.getBanner();

        mPresenter.getUserLocation((MainActivity) getActivity());
        mPresenter.getImgUserFromShared(getActivity());
        getCurrentWeather();
        return view;
    }


    private void initGUI() {
        context = requireContext();
        imagechat = view.findViewById(R.id.img_chat);
        tabLayout = view.findViewById(R.id.tablayout_brand);
        viewPager2 = view.findViewById(R.id.viewpager2_car);
        inputLayout = view.findViewById(R.id.textInputLayout);
        completeTextView = view.findViewById(R.id.autoCompleteText_search);
        btnSearch = view.findViewById(R.id.imageButton_search);
        tvLocation = view.findViewById(R.id.textView_addresLocation);
        imgUser = view.findViewById(R.id.img_user);
        viewPagerBanner = view.findViewById(R.id.viewpager2_banner);
        circleIndicator3 = view.findViewById(R.id.circleIndicator);
        txtWeather = view.findViewById(R.id.txtWeather);
        txtDate = view.findViewById(R.id.txtDate);
    }

//    void test() {
//
//
//
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                int currenItem = viewPagerBanner.getCurrentItem();
//                int totalItem = viewPagerBanner.getAdapter().getItemCount();
//
//                if (currenItem == totalItem - 1) {
//                    viewPagerBanner.setCurrentItem(0);
//                } else {
//                    viewPagerBanner.setCurrentItem(currenItem + 1);
//                }
//            }
//        };
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(runnable);
//            }
//        }, 5000, 5000);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.stopAutoScroll();
    }

    private void changeTextSearch() {
        try {
            List<String> suggestSearchResults = new ArrayList<>();
            for (Cars cars : carsList) {
                suggestSearchResults.add(cars.getCar_name());
            }
            if (suggestSearchResults == null || suggestSearchResults.isEmpty()) return;

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, suggestSearchResults);
            completeTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    inputLayout.setHintEnabled(false);
                    if (adapter != null) completeTextView.setAdapter(adapter);
                    textSearch = s.toString().toLowerCase();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } catch (Exception e) {
            // Xử lý ngoại lệ ở đây.
        }

    }


    @Override
    public void getCarsList(List<Cars> carsList) {
        this.carsList = carsList;
        changeTextSearch();
        completeTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    mPresenter.searchProcessing(carsList, textSearch);
                    return true;
                }
                return false;
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.searchProcessing(carsList, textSearch);
            }
        });
        imagechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagechat.setOnClickListener(view -> startActivity(new Intent(requireContext(),ChatBotActivity.class)));
            }
        });
    }

    @Override
    public void setUserLocation(String address) {
       tvLocation.setText(address);
    }

    @Override
    public void setImgUser(String url) {
        if (url != null) Glide.with(getActivity()).load(url).into(imgUser);
    }

    @Override
    public void notifiEmptyText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Lỗi");
        builder.setMessage("Không được để trống");
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }

    @Override
    public void setListBanner(List<String> mList) {
        bannerAdapter = new BannerAdapter(getActivity(), mList);
        viewPagerBanner.setAdapter(bannerAdapter);
        circleIndicator3.setViewPager(viewPagerBanner);
        mPresenter.sliderAuto(viewPagerBanner);

    }
    /**
     * @since 22-12-2022
     */
    private void getCurrentWeather()
    {
        Retrofit service = RetrofitClient.getOpenWeatherMapInstance();
        ApiService api = service.create(ApiService.class);

        /*Step 3*/
        Map<String, String> parameters = new HashMap<>();
        String latitude = "16.4637";// kinh độ Hue
        String longitude = "107.5833";// vĩ độ Hue
        String apiKey = Constant.OPEN_WEATHER_MAP_API_KEY();// api key của mình trên open weather map.org

        parameters.put("lat", latitude);
        parameters.put("lon", longitude);
        parameters.put("appid", apiKey);
        Call<WeatherForecast> container = api.getCurrentWeather(parameters);

        /*Step 4*/
        container.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecast> call, @NonNull Response<WeatherForecast> response) {
                if(response.isSuccessful())
                {
                    WeatherForecast content = response.body();
                    assert content != null;
//                    System.out.println(TAG);
//                    System.out.println("timezone: " + content.getTimeZone());
//                    System.out.println("name: " + content.getName());
                    printDateAndWeather(content);

                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecast> call, @NonNull Throwable t) {
                System.out.println(TAG);
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    /**
     * @since 12-12-2023
     * in ngày hôm nay và thời tiết lên màn hình
     */
    private void printDateAndWeather(WeatherForecast content)
    {
        String today = Tooltip.getReadableToday(context);
        txtDate.setText(today);

        String temperature = String.valueOf(content.getMain().getTemp());
        String weatherInfo = temperature.substring(0,2) + getString(R.string.celsius);
        txtWeather.setText(weatherInfo);
    }
}
