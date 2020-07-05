package com.app.findhome.view.detail;

import com.app.findhome.model.entity.Property;
import com.app.findhome.util.database.DatabaseClient;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;
    private CompositeDisposable compositeDisposable;

    public DetailPresenter(DetailContract.View mView) {
        this.mView = mView;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void requestDisposable() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    @Override
    public void requestDetail(String id) {
        mView.showLoading();
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
                        mView.dismissLoading();
                        mView.isReqDetail(property);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissLoading();
                        mView.showMessage(e.getMessage());
                    }
                });
    }

    @Override
    public void requestFavorite(int isFavorite, String id) {
        Completable.fromAction(() ->
                DatabaseClient.getInstance().getAppDatabase().propertyDao().updateFavorite(isFavorite, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
