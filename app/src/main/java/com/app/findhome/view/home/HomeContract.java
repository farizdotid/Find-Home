package com.app.findhome.view.home;

import com.app.findhome.model.entity.Property;

import java.util.List;

public interface HomeContract {

    interface View {
        void showLoading();

        void dismissLoading();

        void showMessage(String message);

        void isReqProperty(List<Property> properties);
    }

    interface Presenter {
        void requestDisposable();

        void requestProperty();

        void requestSearchProperty(String search);
    }
}