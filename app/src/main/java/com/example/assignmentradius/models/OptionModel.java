package com.example.assignmentradius.models;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class OptionModel extends RealmObject {
    private String name;

    private String icon;

    @PrimaryKey
    private int id;

    @Ignore
    private boolean isOptionDisabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOptionDisabled() {
        return isOptionDisabled;
    }

    public void setOptionDisabled(boolean optionEnabled) {
        isOptionDisabled = optionEnabled;
    }
}
