package com.app.findhome.view.map;

import com.app.findhome.model.entity.Property;
import com.app.findhome.model.local.MapMarker;
import com.app.findhome.util.database.DatabaseClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mView;
    private CompositeDisposable compositeDisposable;

    public MapPresenter(MapContract.View mView) {
        this.mView = mView;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void requestDisposable() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    @Override
    public void requestMarkerMap() {
        Single.just(DatabaseClient.getInstance().getAppDatabase().propertyDao()
                .getProperty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Property>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Property> properties) {
                        List<MapMarker> mapMarkers = new ArrayList<>();
                        List<String> titles = new ArrayList<>();
                        for (int i = 0; i < properties.size(); i++) {
                            String id = properties.get(i).id;
                            String title = properties.get(i).title;
                            String lat = properties.get(i).lat;
                            String lng = properties.get(i).lng;

                            double initLat = Double.parseDouble(lat);
                            double initLng = Double.parseDouble(lng);
                            LatLng initLatLng = new LatLng(initLat, initLng);

                            mapMarkers.add(new MapMarker(id, title, initLatLng));
                            titles.add(title);
                        }

                        mView.isReqMarkerMap(mapMarkers, titles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.getMessage());
                    }
                });
    }

    @Override
    public void requestMarkerDetail(String id) {
        Single.just(DatabaseClient.getInstance().getAppDatabase().propertyDao().getPropertyById(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Property>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Property property) {
                        String image = property.image;
                        int price = property.price;
                        String title = property.title;
                        String address = property.address;
                        int isFavorite = property.isFavorite;
                        String id = property.id;

                        mView.isReqMarkerDetail(image, price, title, address, isFavorite,
                                id);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.getMessage());
                    }
                });
    }
}
