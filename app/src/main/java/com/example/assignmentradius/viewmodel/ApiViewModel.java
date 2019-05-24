package com.example.assignmentradius.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.SparseIntArray;

import com.example.assignmentradius.models.ServerResponse;
import com.example.assignmentradius.respository.AppRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiViewModel extends ViewModel {
    private MutableLiveData<ServerResponse> serverResponse;

    public void init(){
        if (serverResponse != null) {
            return;
        }
        AppRepository repository = AppRepository.getInstance();
        serverResponse = repository.getServerResponse();
    }

    public MutableLiveData<ServerResponse> getServerResponse() {
        return serverResponse;
    }
}
