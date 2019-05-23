package com.example.assignmentradius.api;

import com.example.assignmentradius.constants.ApiConstants;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static RetrofitHelper instance;
    private Retrofit retrofit;

    private RetrofitHelper() {
        retrofit = new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
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
