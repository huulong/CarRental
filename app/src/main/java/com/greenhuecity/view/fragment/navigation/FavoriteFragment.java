package com.greenhuecity.view.fragment.navigation;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.greenhuecity.R;
import com.greenhuecity.view.activity.MainActivity;
import com.greenhuecity.view.activity.SearchActivity;
import com.greenhuecity.data.contract.FavoriteContract;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.presenter.FavoritePresenter;
import com.greenhuecity.view.adapter.CarAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteFragment extends Fragment implements FavoriteContract.IView {
    TextView tvEmpty;
    RecyclerView rvCar;
    FavoritePresenter mPresenter;
    View view;
    TextInputLayout inputLayout;
    AutoCompleteTextView completeTextView;
    ImageButton btnSearch;
    TextView tvLocation;
    CircleImageView imgUser;
    List<Cars> carsList;
    String textSearch = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initGUI();
        rvCar.setHasFixedSize(true);
        rvCar.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        mPresenter = new FavoritePresenter(this, requireContext());
        mPresenter.getCarList();
        mPresenter.getCarListAPI();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (carsList != null) changeTextSearch();
            }
        }, 2000);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mPresenter.getUserLocation(locationManager);
        mPresenter.getImgUserFromShared();
        return view;
    }

    private void initGUI() {
        tvEmpty = view.findViewById(R.id.textView_favoriteNull);
        rvCar = view.findViewById(R.id.recyclerView_car);
        inputLayout = view.findViewById(R.id.textInputLayout);
        completeTextView = view.findViewById(R.id.autoCompleteText_search);
        btnSearch = view.findViewById(R.id.imageButton_search);
        tvLocation = view.findViewById(R.id.textView_addresLocation);
        imgUser = view.findViewById(R.id.img_user);
    }

    @Override
    public void setDataRecyclerViewCar(List<Cars> mList) {
        CarAdapter adapter = new CarAdapter(mList, requireContext());
        rvCar.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setDataEmpty(String mess) {
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(mess);
    }

    @Override
    public void setDataExist() {
        tvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getCarList();
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
    public void searchTextChangedListener(List<Cars> carsList) {
        completeTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    intentSearch(carsList);
                    return true;
                }
                return false;
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSearch(carsList);
            }
        });
    }

    @Override
    public void getCarsList(List<Cars> carsList) {
        this.carsList = carsList;
        if (carsList != null && textSearch != null) searchTextChangedListener(carsList);

    }

    void intentSearch(List<Cars> carsList) {
        List<Cars> searchList = mPresenter.filterCarList(textSearch, carsList);
        if (searchList != null) {
            Intent intent = new Intent(requireContext(), SearchActivity.class);
            intent.putExtra("list", (Serializable) searchList);
            startActivity(new Intent(intent));
        }
    }

    @Override
    public void setUserLocation(String address) {
        tvLocation.setText(address);
    }

    @Override
    public void setImgUser(String url) {
        if (url != null) Glide.with(getActivity()).load(url).into(imgUser);
    }
}
