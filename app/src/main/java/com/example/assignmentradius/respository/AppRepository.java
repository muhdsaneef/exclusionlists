package com.example.assignmentradius.respository;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.assignmentradius.api.RetrofitHelper;
import com.example.assignmentradius.models.ExclusionEntityModel;
import com.example.assignmentradius.models.Exclusions;
import com.example.assignmentradius.models.FacilityModel;
import com.example.assignmentradius.models.OptionModel;
import com.example.assignmentradius.models.ServerResponse;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    private static AppRepository appRepository;
    private Realm realm;

    private AppRepository() {
        realm = Realm.getDefaultInstance();
    }

    public static AppRepository getInstance(){
        if (appRepository == null){
            appRepository = new AppRepository();
        }
        return appRepository;
    }

    public MutableLiveData<ServerResponse> getServerResponse() {
        final MutableLiveData<ServerResponse> serverResponse = new MutableLiveData<>();
        Call<ServerResponse> call = RetrofitHelper.getInstance().getApiService().getApiResponse();
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if(response.isSuccessful()) {
                    ServerResponse serverResponseBody = response.body();
                    serverResponse.setValue(serverResponseBody);
                    writeResponseToDatabase(serverResponseBody);
                } else {
                    serverResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                serverResponse.setValue(null);
            }
        });
        return serverResponse;
    }

    private void writeResponseToDatabase(ServerResponse serverResponse) {
        realm.beginTransaction();
        if(serverResponse.getFacilities() != null && !serverResponse.getFacilities().isEmpty()) {
            for(ServerResponse.Facilities facility : serverResponse.getFacilities()) {
                // Persist your data in a transaction
                FacilityModel facilityModel = new FacilityModel();
                facilityModel.setFacilityId(facility.getFacilityId());
                facilityModel.setName(facility.getName());
                List<OptionModel> options = facility.getOptions();
                facilityModel.setOptions(new RealmList<>(options.toArray(new OptionModel[options.size()])));
                FacilityModel facilityObject = realm.copyToRealm(facilityModel);
            }
        }

        if(serverResponse.getExclusions() != null && !serverResponse.getExclusions().isEmpty()) {
            for(List<ExclusionEntityModel> exclusions : serverResponse.getExclusions()) {
                Exclusions unmanagedExclusion = new Exclusions();
                unmanagedExclusion.setId(UUID.randomUUID().toString());
                unmanagedExclusion.setExclusions(new RealmList<>(exclusions.toArray(new ExclusionEntityModel[exclusions.size()])));
                Exclusions managedExclusions = realm.copyToRealm(unmanagedExclusion);
            }
        }
        realm.commitTransaction();
    }
}
