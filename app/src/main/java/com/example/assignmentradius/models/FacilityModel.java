package com.example.assignmentradius.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class FacilityModel extends RealmObject {

    @PrimaryKey
    @SerializedName("facility_id")
    private int facilityId;

    private String name;

    private RealmList<OptionModel> options;

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<OptionModel> getOptions() {
        return options;
    }

    public void setOptions(RealmList<OptionModel> options) {
        this.options = options;
    }
}
