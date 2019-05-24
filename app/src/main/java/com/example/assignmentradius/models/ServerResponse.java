package com.example.assignmentradius.models;

import java.util.List;

public class ServerResponse {
    private List<FacilityModel> facilities;

    private List<List<ExclusionEntityModel>> exclusions;

    public ServerResponse() {
    }

    public ServerResponse(List<FacilityModel> facilities, List<List<ExclusionEntityModel>> exclusions) {
        this.facilities = facilities;
        this.exclusions = exclusions;
    }

    public List<FacilityModel> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<FacilityModel> facilities) {
        this.facilities = facilities;
    }

    public List<List<ExclusionEntityModel>> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<List<ExclusionEntityModel>> exclusions) {
        this.exclusions = exclusions;
    }
}
