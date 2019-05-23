package com.example.assignmentradius.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;

public class ServerResponse implements RealmModel{
    @SerializedName("facilities")
    private List<Facilities> facilities;

    @SerializedName("exclusions")
    private List<List<Exclusions>> exclusions;

    @PrimaryKey
    private String id;

    public ServerResponse() {
    }

    public ServerResponse(List<Facilities> facilities, List<List<Exclusions>> exclusions) {
        this.facilities = facilities;
        this.exclusions = exclusions;
    }

    public List<Facilities> getFacilities() {
        return facilities;
    }

    public List<List<Exclusions>> getExclusions() {
        return exclusions;
    }

    public void setFacilities(List<Facilities> facilities) {
        this.facilities = facilities;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Facilities implements RealmModel{
        @SerializedName("facility_id")
        private final int facilityId;

        @SerializedName("name")
        private final String name;

        @SerializedName("options")
        private final List<Options> options;

        private int selectedOption = -1;

        private List<Integer> facilityIdsOfOptionBlockingFacility;

        public Facilities(int facilityId, String name, List<Options> options) {
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

        public List<Options> getOptions() {
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

        public static class Options {
            @SerializedName("name")
            private final String name;

            @SerializedName("icon")
            private final String icon;

            @SerializedName("id")
            private final int id;

            private boolean isOptionDisabled;

            public Options(String name, String icon, int id) {
                this.name = name;
                this.icon = icon;
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public String getIcon() {
                return icon;
            }

            public int getId() {
                return id;
            }

            public boolean isOptionDisabled() {
                return isOptionDisabled;
            }

            public void setOptionDisabled(boolean optionEnabled) {
                isOptionDisabled = optionEnabled;
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
                for (Options option : options) {
                    option.setOptionDisabled(false);
                }
            }
        }

        public void disableOption(int optionID) {
            if(options != null) {
                for (Options option : options) {
                    if(option.getId() == optionID) {
                        option.setOptionDisabled(true);
                        break;
                    }
                }
            }
        }

    }

    public static class Exclusions {
        @SerializedName("facility_id")
        private final int facilityId;

        @SerializedName("options_id")
        private final int optionsId;

        public Exclusions(int facilityId, int optionsId) {
            this.facilityId = facilityId;
            this.optionsId = optionsId;
        }

        public int getFacilityId() {
            return facilityId;
        }

        public int getOptionsId() {
            return optionsId;
        }
    }
}
