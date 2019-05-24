package com.example.assignmentradius.models;

import io.realm.RealmObject;

public class DatabaseUpdateModel extends RealmObject {
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
