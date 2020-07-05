package com.app.findhome.view.favorite;

import com.app.findhome.model.entity.Property;
import com.app.findhome.util.database.DatabaseClient;
import com.app.findhome.util.database.daos.PropertyDao;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoritePresenter implements FavoriteContract.Presenter {

    private FavoriteContract.View mView;
    private CompositeDisposable compositeDisposable;
    private PropertyDao propertyDao;

    public FavoritePresenter(FavoriteContract.View mView) {
        this.mView = mView;
        this.compositeDisposable = new CompositeDisposable();
        this.propertyDao = DatabaseClient.getInstance().getAppDatabase().propertyDao();
    }

    @Override
    public void requestDisposable() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    @Override
    public void requestFavoriteProperty() {
        mView.showLoading();
        Single.just(propertyDao.getPropertyFavorites())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Property>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Property> properties) {
                        mView.dismissLoading();
                        mView.isReqFavorites(properties);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.getMessage());
                        mView.dismissLoading();
                    }
                });
    }
}
