package com.app.findhome.util.apiservice;

import com.app.findhome.model.remote.property.ResponseProperty;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface BaseApiService {

    @GET("b/5e1552c35df6407208319336/1")
    Observable<Response<ResponseProperty>> requestProperty();
}
