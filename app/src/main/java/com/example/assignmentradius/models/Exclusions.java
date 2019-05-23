package com.example.assignmentradius.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Exclusions extends RealmObject {

    private String id;

    private RealmList<ExclusionEntityModel> exclusions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<ExclusionEntityModel> getExclusions() {
        return exclusions;
    }

    public void setExclusions(RealmList<ExclusionEntityModel> exclusions) {
        this.exclusions = exclusions;
    }
}
