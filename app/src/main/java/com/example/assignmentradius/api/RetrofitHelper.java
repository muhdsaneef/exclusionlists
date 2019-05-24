package com.example.assignmentradius.api;

import com.example.assignmentradius.constants.ApiConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static RetrofitHelper instance;
    private Retrofit retrofit;

    private RetrofitHelper() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(client)
                                        .build();
    }

    public static RetrofitHelper getInstance() {
        if(instance == null) {
            instance = new RetrofitHelper();
        }
        return instance;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

}
