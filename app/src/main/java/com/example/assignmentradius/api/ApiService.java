package com.example.assignmentradius.api;

import com.example.assignmentradius.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("https://my-json-server.typicode.com/iranjith4/ad-assignment/db")
    public Call<ServerResponse> getApiResponse();

}
