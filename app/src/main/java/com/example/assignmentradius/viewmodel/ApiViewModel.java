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
    private AppRepository repository;
    private SparseIntArray facilitiesOptionSelections;

    public void init(){
        if (serverResponse != null){
            return;
        }
        if(facilitiesOptionSelections == null) {
            facilitiesOptionSelections = new SparseIntArray();
        }
        repository = AppRepository.getInstance();
        serverResponse = repository.getServerResponse();
    }

    public MutableLiveData<ServerResponse> getServerResponse() {
        return serverResponse;
    }

    public void addOptionSelection(int facilityId, int optionId) {
        facilitiesOptionSelections.put(facilityId, optionId);
    }


}
