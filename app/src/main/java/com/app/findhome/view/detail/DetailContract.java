package com.app.findhome.view.detail;

import com.app.findhome.model.entity.Property;

public interface DetailContract {

    interface View {
        void showLoading();

        void dismissLoading();

        void showMessage(String message);

        void isReqDetail(Property property);
    }

    interface Presenter {
        void requestDisposable();
        void requestDetail(String id);
        void requestFavorite(int isFavorite, String id);
    }
}