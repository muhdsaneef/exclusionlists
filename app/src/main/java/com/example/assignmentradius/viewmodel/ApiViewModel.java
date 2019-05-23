package com.example.assignmentradius.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.assignmentradius.models.ServerResponse;
import com.example.assignmentradius.respository.AppRepository;

public class ApiViewModel extends ViewModel {
    private MutableLiveData<ServerResponse> serverResponse;
    private AppRepository repository;

    public void init(){
        if (serverResponse != null){
            return;
        }
        repository = AppRepository.getInstance();
        serverResponse = repository.getServerResponse();
    }

    public MutableLiveData<ServerResponse> getServerResponse() {
        return serverResponse;
    }
}
