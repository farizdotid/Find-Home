package com.app.findhome.util.apiservice;


import com.app.findhome.BuildConfig;

public class UtilsApi {
    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BuildConfig.SERVER_URL).create(BaseApiService.class);
    }
}
