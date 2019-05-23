package com.example.assignmentradius.models;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Exclusions {

    @PrimaryKey
    private int id;

    private RealmList<ExclusionEntityModel> exclusions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<ExclusionEntityModel> getExclusions() {
        return exclusions;
    }

    public void setExclusions(RealmList<ExclusionEntityModel> exclusions) {
        this.exclusions = exclusions;
    }
}
