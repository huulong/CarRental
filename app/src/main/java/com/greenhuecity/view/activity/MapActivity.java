package com.greenhuecity.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.greenhuecity.R;
import com.greenhuecity.data.model.UserOrder;

import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
//        GoogleApiClient.OnConnectionFailedListener, RoutingListener

    //google map object
    private GoogleMap mMap;
    ImageView imgBack;

    //current and destination location objects
//    Location myLocation = null;
//    protected LatLng start = null;
    protected LatLng end = null;

    //to get location permissions.
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;

    UserOrder userOrder;
    //polyline object
//    private List<Polyline> polylinesList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view->onBackPressed());
        userOrder = (UserOrder) getIntent().getSerializableExtra("order");
        //request location permission.
        requestPermision();
//
//        //init google map fragment to show map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    private void requestPermision() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        } else {
            locationPermission = true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted.
                    locationPermission = true;
                    getMyLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    //to get user location
    @SuppressLint("MissingPermission")
    private void getMyLocation() {
        end = new LatLng(userOrder.getLatitude(),userOrder.getLongitude());

        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(end, 13));
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(end)
                .title(userOrder.getDistributor_name())
                .snippet(userOrder.getAddress()));

//         Hiển thị InfoWindow khi click vào Marker
        marker.showInfoWindow();

//        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//            @Override
//            public void onMyLocationChange(Location location) {
//                myLocation = location;
//                LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//                        ltlng, 13);
//                mMap.animateCamera(cameraUpdate);
//                start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//                //start route finding
//                Findroutes(start, end);
//            }
//        });

        //get destination location when user click on map
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//                end=latLng;
//
//                mMap.clear();
//
//                start=new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
//                //start route finding
//                Findroutes(start,end);
//            }
//        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getMyLocation();

    }


    // function to find Routes.
//    public void Findroutes(LatLng Start, LatLng End) {
//        if (Start == null || End == null) {
//            Toast.makeText(this, "Unable to get location", Toast.LENGTH_LONG).show();
//        } else {
//            Routing routing = new Routing.Builder()
//                    .travelMode(AbstractRouting.TravelMode.DRIVING)
//                    .withListener(this)
//                    .alternativeRoutes(true)
//                    .waypoints(Start, End)
//                    .key("AIzaSyBa9ET3UAixDwz23xhiS8c7qqvnyXcAqaY")  //also define your api key here.
//                    .build();
//            routing.execute();
//        }
//    }
//
//    //Routing call back functions.
//    @Override
//    public void onRoutingFailure(RouteException e) {
//        View parentLayout = findViewById(android.R.id.content);
//        Snackbar snackbar = Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
//
//        snackbar.show();
//        Findroutes(start,end);
//    }
//
//    @Override
//    public void onRoutingStart() {
//        Toast.makeText(this, "Finding Route...", Toast.LENGTH_LONG).show();
//    }
//
//    //If Route finding success..
//    @Override
//    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
//
//        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
//        if (polylinesList != null) {
//            polylinesList.clear();
//        }
//        PolylineOptions polyOptions = new PolylineOptions();
//        LatLng polylineStartLatLng = null;
//        LatLng polylineEndLatLng = null;
//
//
//        polylinesList = new ArrayList<>();
//        //add route(s) to the map using polyline
//        for (int i = 0; i < route.size(); i++) {
//
//            if (i == shortestRouteIndex) {
//                polyOptions.color(getResources().getColor(R.color.blue));
//                polyOptions.width(7);
//                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
//                Polyline polyline = mMap.addPolyline(polyOptions);
//                polylineStartLatLng = polyline.getPoints().get(0);
//                int k = polyline.getPoints().size();
//                polylineEndLatLng = polyline.getPoints().get(k - 1);
//                polylinesList.add(polyline);
//
//            } else {
//
//            }
//
//        }
//
//        //Add Marker on route starting position
//        MarkerOptions startMarker = new MarkerOptions();
//        startMarker.position(polylineStartLatLng);
//        startMarker.title("My Location");
//        mMap.addMarker(startMarker);
//
//        //Add Marker on route ending position
//        MarkerOptions endMarker = new MarkerOptions();
//        endMarker.position(polylineEndLatLng);
//        endMarker.title("Destination");
//        mMap.addMarker(endMarker);
//    }
//
//    @Override
//    public void onRoutingCancelled() {
//        Findroutes(start, end);
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Findroutes(start, end);
//
//    }
}