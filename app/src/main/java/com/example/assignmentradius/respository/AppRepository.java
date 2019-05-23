package com.example.assignmentradius.respository;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.assignmentradius.api.RetrofitHelper;
import com.example.assignmentradius.models.ServerResponse;

import java.util.List;

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
        // Persist your data in a transaction
        realm.beginTransaction();
        ServerResponse serverResponseObject = realm.copyToRealm(serverResponse); // Create managed objects directly
        realm.commitTransaction();
    }

    public void storeFacilitiesList(final List<ServerResponse.Facilities> facilities) {
        try(Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ServerResponse serverResponseObj = new ServerResponse(); // <-- create unmanaged
                    RealmList<ServerResponse.Facilities> facilitiesList = new RealmList<>();
                    facilitiesList.addAll(facilities);
                    serverResponseObj.setFacilities(facilitiesList);
                    serverResponseObj.setId("facilities");
                    realm.insert(serverResponseObj); // <-- insert unmanaged to Realm
                }
            });
        }
    }
}
