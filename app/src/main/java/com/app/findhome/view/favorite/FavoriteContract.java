package com.app.findhome.view.favorite;

import com.app.findhome.model.entity.Property;

import java.util.List;

public interface FavoriteContract {

    interface View {
        void showLoading();

        void dismissLoading();

        void showMessage(String message);

        void isReqFavorites(List<Property> properties);
    }

    interface Presenter {
        void requestDisposable();
        void requestFavoriteProperty();
    }
}