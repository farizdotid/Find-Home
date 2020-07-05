package com.app.findhome.view.home;

import com.app.findhome.model.entity.Property;
import com.app.findhome.model.remote.property.ListItem;
import com.app.findhome.model.remote.property.ResponseProperty;
import com.app.findhome.util.apiservice.BaseApiService;
import com.app.findhome.util.apiservice.UtilsApi;
import com.app.findhome.util.database.DatabaseClient;
import com.app.findhome.util.database.daos.PropertyDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private BaseApiService mApiService;
    private CompositeDisposable compositeDisposable;
    private PropertyDao propertyDao;

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
        this.mApiService = UtilsApi.getAPIService();
        this.compositeDisposable = new CompositeDisposable();
        this.propertyDao = DatabaseClient.getInstance().getAppDatabase().propertyDao();
    }

    @Override
    public void requestDisposable() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    @Override
    public void requestProperty() {
        mView.showLoading();
        mApiService.requestProperty()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseProperty>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Response<ResponseProperty> responsePropertyResponse) {
                        if (responsePropertyResponse.body() != null) {
                            List<ListItem> listItems = responsePropertyResponse.body().getData().getList();
                            for (int i = 0; i < listItems.size(); i++) {
                                String id = listItems.get(i).getId();
                                String image = listItems.get(i).getImage();
                                String price = listItems.get(i).getAttribute().getPrice();
                                String title = listItems.get(i).getTitle();
                                String address = listItems.get(i).getLocation().getAddress();
                                String lat = listItems.get(i).getLocation().getLatitude();
                                String lng = listItems.get(i).getLocation().getLongitude();
                                String isPremium = listItems.get(i).getIsPremium();
                                String desc = listItems.get(i).getDescription();
                                String facilities = listItems.get(i).getFasilities();
                                String agentAvatar = listItems.get(i).getAgent().getPhoto();
                                String agentName = listItems.get(i).getAgent().getName();
                                String agentType = listItems.get(i).getAgent().getType();

                                int initPrice = Integer.parseInt(price);
                                int initIsPremium = isPremium.toLowerCase().equals("true") ? 1 : 0;

                                Property property = new Property();
                                property.id = id;
                                property.image = image;
                                property.price = initPrice;
                                property.title = title;
                                property.address = address;
                                property.lat = lat;
                                property.lng = lng;
                                property.isPremium = initIsPremium;
                                property.isFavorite = 0;
                                property.deskripsi = desc;
                                property.fasilitas = facilities;
                                property.agentAvatar = agentAvatar;
                                property.agentName = agentName;
                                property.agentType = agentType;

                                propertyDao.insertData(property);
                            }

                            Single.just(DatabaseClient.getInstance()
                                    .getAppDatabase().propertyDao().getProperty())
                                    .subscribe(new SingleObserver<List<Property>>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            compositeDisposable.add(d);
                                        }

                                        @Override
                                        public void onSuccess(List<Property> properties) {
                                            mView.isReqProperty(properties);
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissLoading();
                        mView.showMessage(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.dismissLoading();
                    }
                });
    }

    @Override
    public void requestSearchProperty(String search) {
        List<Property> propertyList = search.isEmpty() ?
                propertyDao.getProperty()
                : propertyDao.searchProperty(search);

        Single.just(propertyList)
                .subscribe(new SingleObserver<List<Property>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Property> properties) {
                        mView.isReqProperty(properties);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.getMessage());
                    }
                });
    }
}
