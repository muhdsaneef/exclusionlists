package com.example.assignmentradius.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

public class ServerResponse {
    private List<FacilityModel> facilities;

    private List<Exclusions> exclusions;

    public ServerResponse() {
    }

    public ServerResponse(List<FacilityModel> facilities, List<Exclusions> exclusions) {
        this.facilities = facilities;
        this.exclusions = exclusions;
    }

    public List<FacilityModel> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<FacilityModel> facilities) {
        this.facilities = facilities;
    }

    public List<Exclusions> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<Exclusions> exclusions) {
        this.exclusions = exclusions;
    }
}
