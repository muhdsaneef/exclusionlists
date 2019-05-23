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

public class ServerResponse extends RealmObject {
    @SerializedName("facilities")
    private RealmList<Facilities> facilities;

    @SerializedName("exclusions")
    private RealmList<RealmList<Exclusions>> exclusions;

    @Required
    @PrimaryKey
    private String id;

    public ServerResponse() {
    }

    public ServerResponse(RealmList<Facilities> facilities, RealmList<RealmList<Exclusions>> exclusions) {
        this.facilities = facilities;
        this.exclusions = exclusions;
    }

    public RealmList<Facilities> getFacilities() {
        return facilities;
    }

    public void setFacilities(RealmList<Facilities> facilities) {
        this.facilities = facilities;
    }

    public RealmList<RealmList<Exclusions>> getExclusions() {
        return exclusions;
    }

    public void setExclusions(RealmList<RealmList<Exclusions>> exclusions) {
        this.exclusions = exclusions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Facilities extends RealmObject{
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

        public static class Options extends RealmObject{
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

    @RealmClass
    public static class Exclusions implements RealmModel {
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
