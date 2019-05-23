package com.example.assignmentradius.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ExclusionEntityModel extends RealmObject {

    @SerializedName("facility_id")
    private int facilityId;

    @SerializedName("options_id")
    private  int optionsId;

    public ExclusionEntityModel() {
    }

    public ExclusionEntityModel(int facilityId, int optionId) {
        this.facilityId = facilityId;
        this.optionsId = optionId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(int optionsId) {
        this.optionsId = optionsId;
    }
}
