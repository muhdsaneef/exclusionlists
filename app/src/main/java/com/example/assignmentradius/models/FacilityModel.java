package com.example.assignmentradius.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class FacilityModel extends RealmObject {

    @SerializedName("facility_id")
    private int facilityId;

    private String name;

    private RealmList<OptionModel> options;

    @Ignore
    private int selectedOption = -1;

    @Ignore
    private List<Integer> facilityIdsOfOptionBlockingFacility;

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

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public List<Integer> getFacilityIdsOfOptionBlockingFacility() {
        return facilityIdsOfOptionBlockingFacility;
    }

    public void setFacilityIdsOfOptionBlockingFacility(List<Integer> facilityIdsOfOptionBlockingFacility) {
        this.facilityIdsOfOptionBlockingFacility = facilityIdsOfOptionBlockingFacility;
    }

    public void addBlockingFacilityId(int blockingFacilityId) {
        if(facilityIdsOfOptionBlockingFacility == null) {
            facilityIdsOfOptionBlockingFacility = new ArrayList<>();
        }
        if(!facilityIdsOfOptionBlockingFacility.contains(blockingFacilityId)) {
            facilityIdsOfOptionBlockingFacility.add(blockingFacilityId);
        }
    }

    public void enableAllOptions() {
        if(options != null) {
            for (OptionModel option : options) {
                option.setOptionDisabled(false);
            }
        }
    }

    public void disableOption(int optionID) {
        if(options != null) {
            for (OptionModel option : options) {
                if(option.getId() == optionID) {
                    option.setOptionDisabled(true);
                    break;
                }
            }
        }
    }
}
