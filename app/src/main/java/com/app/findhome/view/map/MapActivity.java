package com.app.findhome.view.map;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.findhome.R;
import com.app.findhome.databinding.ActivityMapBinding;
import com.app.findhome.model.local.MapMarker;
import com.app.findhome.util.FunctionHelper;
import com.app.findhome.util.SharedPrefManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import mumayank.com.airlocationlibrary.AirLocation;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapContract.View {

    public static void startActivity(Context context) {
        Intent intent = new Intent(new Intent(context, MapActivity.class));
        context.startActivity(intent);
    }

    private ActivityMapBinding binding;
    private SupportMapFragment mapFragment;
    private MapPresenter mapPresenter;
    private AirLocation airLocation;

    private List<MapMarker> mMapMarkers = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapPresenter = new MapPresenter(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        loadLocation();

        initAction();
    }

    private void loadLocation(){
        airLocation = new AirLocation(this, true, true,
                new AirLocation.Callbacks() {
                    @Override
                    public void onSuccess(Location location) {
                        double userLat = location.getLatitude();
                        double userLng = location.getLongitude();

                        SharedPrefManager.getInstance().put(SharedPrefManager.Key.User.LAT, userLat);
                        SharedPrefManager.getInstance().put(SharedPrefManager.Key.User.LNG, userLng);

                        mapPresenter.requestMarkerMap();
                    }

                    @Override
                    public void onFailed(AirLocation.LocationFailedEnum locationFailedEnum) {
                        Toast.makeText(MapActivity.this, getString(R.string.error_cannot_get_location),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initAction() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        binding.ivBack.bringToFront();

        for (int i = 0; i < mMapMarkers.size(); i++) {
            LatLng latLng = mMapMarkers.get(i).getLatLng();
            String title = mMapMarkers.get(i).getTitle();

            googleMap.addMarker(new MarkerOptions()
                    .title(title)
                    .position(latLng)
                    .icon(FunctionHelper.bitmapDescriptorFromVector(this,
                            R.drawable.ic_baseline_location_on_primary_dark_24)));


        }

        double userLat = SharedPrefManager.getInstance().getDouble(SharedPrefManager.Key.User.LAT);
        double userLng = SharedPrefManager.getInstance().getDouble(SharedPrefManager.Key.User.LNG);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLat, userLng), 15));

        googleMap.setOnMarkerClickListener(marker -> {
            String id = mMapMarkers.get(getPositionMarker(marker.getTitle())).getId();
            mapPresenter.requestMarkerDetail(id);
            return false;
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isReqMarkerMap(List<MapMarker> mapMarkers, List<String> titles) {
        mMapMarkers = mapMarkers;
        mTitles = titles;
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private int getPositionMarker(String title){
        return mTitles.indexOf(title);
    }

    @Override
    public void isReqMarkerDetail(String image, int price, String title, String address, int isFavorite,
                                  String id) {
        DetailMarkerFragment detailMarkerFragment = DetailMarkerFragment
                .newInstance(image, price, title, address, isFavorite, id);
        detailMarkerFragment.show(getSupportFragmentManager(), DetailMarkerFragment.class.getCanonicalName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
        mapPresenter.requestDisposable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapFragment.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapFragment.onStop();
    }

    // override and call airLocation object's method by the same name
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        airLocation.onActivityResult(requestCode, resultCode, data);
    }

    // override and call airLocation object's method by the same name
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}