package com.greenhuecity.view.fragment.bottomsheet;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.gms.maps.model.LatLng;
import com.greenhuecity.R;
import com.greenhuecity.data.model.UserOrder;


public class MapBottomSheetFragment extends BottomSheetDialogFragment {
    private double latitude, longitude;
    UserOrder mUserOrder;
    BottomSheetDialog bottomSheetDialog;
    GoogleMap mGoogleMap;

    public static MapBottomSheetFragment newInstance(UserOrder userOrder) {
        MapBottomSheetFragment bottomSheetFragment = new MapBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", userOrder);
        bottomSheetFragment.setArguments(bundle);
        return bottomSheetFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            mUserOrder = (UserOrder) bundleReceive.getSerializable("order");
            latitude = mUserOrder.getLatitude();
            longitude = mUserOrder.getLongitude();
        }
    }

    @SuppressLint("WrongConstant")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottomsheet_map, null);
        ImageView imgBack = view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(view1->bottomSheetDialog.dismiss());
        MapView mapView = view.findViewById(R.id.mapp);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mGoogleMap = googleMap;
                LatLng location = new LatLng(latitude, longitude);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(mUserOrder.getDistributor_name())
                        .snippet(mUserOrder.getAddress()));
                marker.showInfoWindow();
            }
        });

        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior behavior = BottomSheetBehavior.from((View) view.getParent());
        behavior.setSaveFlags(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        return bottomSheetDialog;
    }
}