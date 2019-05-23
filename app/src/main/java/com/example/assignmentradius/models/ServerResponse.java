package com.example.assignmentradius.models;

import com.google.gson.annotations.SerializedName;

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
        private final String facilityId;

        @SerializedName("name")
        private final String name;

        @SerializedName("options")
        private final List<Options> options;

        public Facilities(String facilityId, String name, List<Options> options) {
            this.facilityId = facilityId;
            this.name = name;
            this.options = options;
        }

        public String getFacilityId() {
            return facilityId;
        }

        public String getName() {
            return name;
        }

        public List<Options> getOptions() {
            return options;
        }

        public static class Options {
            @SerializedName("name")
            private final String name;

            @SerializedName("icon")
            private final String icon;

            @SerializedName("id")
            private final String id;

            public Options(String name, String icon, String id) {
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

            public String getId() {
                return id;
            }
        }
    }

    public static class Exclusions {
        @SerializedName("facility_id")
        private final String facilityId;

        @SerializedName("options_id")
        private final String optionsId;

        public Exclusions(String facilityId, String optionsId) {
            this.facilityId = facilityId;
            this.optionsId = optionsId;
        }

        public String getFacilityId() {
            return facilityId;
        }

        public String getOptionsId() {
            return optionsId;
        }
    }
}
