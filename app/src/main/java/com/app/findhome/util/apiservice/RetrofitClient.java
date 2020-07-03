package com.app.findhome.util.apiservice;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    static Retrofit getClient(String baseUrl) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor headerAuthorizationInterceptor = chain -> {
            String token = "$2b$10$z032e5RzBFljvw44bmQIyeDpK7yxz.gh9W2NO5VuiLtXfDciFYWq2";
            Request request = chain.request();
            Headers headers = request.headers().newBuilder().add("secret-key", token).build();
            request = request.newBuilder().headers(headers).build();

            return chain.proceed(request);
        };


        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .addInterceptor(headerAuthorizationInterceptor)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
