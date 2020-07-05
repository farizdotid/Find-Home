package com.app.findhome.model.local;

import com.google.android.gms.maps.model.LatLng;

public class MapMarker {
    private String id;
    private String title;
    private LatLng latLng;

    public MapMarker(String id, String title, LatLng latLng) {
        this.id = id;
        this.title = title;
        this.latLng = latLng;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}
