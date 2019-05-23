package com.example.assignmentradius.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

public class ServerResponse {
    private List<Facilities> facilities;

    private List<List<ExclusionEntityModel>> exclusions;

    public ServerResponse() {
    }

    public ServerResponse(List<Facilities> facilities, List<List<ExclusionEntityModel>> exclusions) {
        this.facilities = facilities;
        this.exclusions = exclusions;
    }

    public List<Facilities> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facilities> facilities) {
        this.facilities = facilities;
    }

    public List<List<ExclusionEntityModel>> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<List<ExclusionEntityModel>> exclusions) {
        this.exclusions = exclusions;
    }


    public static class Facilities {
        @SerializedName("facility_id")
        private final int facilityId;

        @SerializedName("name")
        private final String name;

        @SerializedName("options")
        private final List<OptionModel> options;

        private int selectedOption = -1;

        private List<Integer> facilityIdsOfOptionBlockingFacility;

        public Facilities(int facilityId, String name, List<OptionModel> options) {
            this.facilityId = facilityId;
            this.name = name;
            this.options = options;
        }

        public int getFacilityId() {
            return facilityId;
        }

        public String getName() {
            return name;
        }

        public List<OptionModel> getOptions() {
            return options;
        }

        public List<Integer> getFacilityIdsOfOptionBlockingFacility() {
            return facilityIdsOfOptionBlockingFacility;
        }

        public void addBlockingFacilityId(int blockingFacilityId) {
            if(facilityIdsOfOptionBlockingFacility == null) {
                facilityIdsOfOptionBlockingFacility = new ArrayList<>();
            }
            if(!facilityIdsOfOptionBlockingFacility.contains(blockingFacilityId)) {
                facilityIdsOfOptionBlockingFacility.add(blockingFacilityId);
            }
        }

        public int getSelectedOption() {
            return selectedOption;
        }

        public void setSelectedOption(int selectedOption) {
            this.selectedOption = selectedOption;
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
}
