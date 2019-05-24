package com.example.assignmentradius.respository;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.assignmentradius.api.RetrofitHelper;
import com.example.assignmentradius.models.DatabaseUpdateModel;
import com.example.assignmentradius.models.ExclusionEntityModel;
import com.example.assignmentradius.models.Exclusions;
import com.example.assignmentradius.models.FacilityModel;
import com.example.assignmentradius.models.ServerResponse;
import com.example.assignmentradius.utils.AppUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    private static AppRepository appRepository;
    private Realm realm;

    private AppRepository() {
    }

    public static AppRepository getInstance(){
        if (appRepository == null){
            appRepository = new AppRepository();
        }
        return appRepository;
    }

    public MutableLiveData<ServerResponse> getServerResponse() {
        String currentTimestamp = AppUtils.getTimestamp(System.currentTimeMillis(), new Date());
        RealmResults<DatabaseUpdateModel> realmResults = getDatabaseExpiryDetails();
        if(realmResults.size() > 0) {
            if(checkForDataExpiry(realmResults.get(0), currentTimestamp)) {
                return getServerResponseFromNetWork();
            } else {
                return getServerResponseFromDatabase();
            }
        } else {
            return getServerResponseFromNetWork();
        }
    }

    private RealmResults<DatabaseUpdateModel> getDatabaseExpiryDetails() {
        realm = Realm.getDefaultInstance();
        return realm.where(DatabaseUpdateModel.class).findAll();
    }

    private boolean checkForDataExpiry(DatabaseUpdateModel databaseUpdateModel, String currentTimestamp) {
        String databaseTimestamp = databaseUpdateModel.getTimestamp();
        int databaseTimestampValue = Integer.valueOf(databaseTimestamp);
        int currentTimestampValue = Integer.valueOf(currentTimestamp);
        return currentTimestampValue > databaseTimestampValue;
    }

    private MutableLiveData<ServerResponse> getServerResponseFromNetWork() {
        final MutableLiveData<ServerResponse> serverResponse = new MutableLiveData<>();
        Call<ServerResponse> call = RetrofitHelper.getInstance().getApiService().getApiResponse();
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if(response.isSuccessful()) {
                    ServerResponse serverResponseBody = response.body();
                    serverResponse.setValue(serverResponseBody);
                    if(serverResponseBody != null) {
                        clearDatabase();
                        writeResponseToDatabase(serverResponseBody);
                    }
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

    private void clearDatabase() {
        realm.executeTransaction(realm -> realm.deleteAll());
    }

    private void writeResponseToDatabase(ServerResponse serverResponse) {
        realm = Realm.getDefaultInstance();
        clearDatabase();
        realm.executeTransaction(realm -> {
            DatabaseUpdateModel databaseUpdateModel = new DatabaseUpdateModel();
            databaseUpdateModel.setTimestamp(AppUtils.getTimestamp(System.currentTimeMillis(), new Date()));
            realm.copyToRealm(databaseUpdateModel);

            if(serverResponse.getFacilities() != null && !serverResponse.getFacilities().isEmpty()) {
                realm.insert(serverResponse.getFacilities());
            }

            if(serverResponse.getExclusions() != null && !serverResponse.getExclusions().isEmpty()) {
                for(List<ExclusionEntityModel> exclusionEntityModels : serverResponse.getExclusions()) {
                    Exclusions unManagedExclusion = new Exclusions();
                    unManagedExclusion.setId(UUID.randomUUID().toString());
                    unManagedExclusion.setExclusions(new RealmList<>(exclusionEntityModels.toArray(new ExclusionEntityModel[0])));
                    realm.copyToRealm(unManagedExclusion);
                }
            }
        });
        realm.close();
    }

    private MutableLiveData<ServerResponse> getServerResponseFromDatabase() {
        realm = Realm.getDefaultInstance();
        final MutableLiveData<ServerResponse> serverResponse = new MutableLiveData<>();
        ServerResponse response = new ServerResponse();
        RealmResults<FacilityModel> realmResults = realm.where(FacilityModel.class).findAll();
        if(realmResults.size() > 0) {
            List<FacilityModel> facilityList = realm.copyFromRealm(realmResults);
            response.setFacilities(facilityList);
        }
        RealmResults<Exclusions> realmResultsOfExclusions = realm.where(Exclusions.class).findAll();
        if(realmResultsOfExclusions.size() > 0) {
            List<Exclusions> exclusionsList = realm.copyFromRealm(realmResultsOfExclusions);
            List<List<ExclusionEntityModel>> unManagedExclusionList = new ArrayList<>();
            for(Exclusions exclusions : exclusionsList) {
                unManagedExclusionList.add(exclusions.getExclusions());
            }
            response.setExclusions(unManagedExclusionList);
        }
        serverResponse.setValue(response);
        realm.close();
        return serverResponse;
    }
}
