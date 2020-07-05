package com.app.findhome.view.map;

import com.app.findhome.model.local.MapMarker;

import java.util.List;

public interface MapContract {

    interface View {
        void showMessage(String message);
        void isReqMarkerMap(List<MapMarker> mapMarkers, List<String> titles);
        void isReqMarkerDetail(String image, int price, String title, String address,
                               int isFavorite, String id);
    }

    interface Presenter {
        void requestDisposable();
        void requestMarkerMap();
        void requestMarkerDetail(String id);
    }
}