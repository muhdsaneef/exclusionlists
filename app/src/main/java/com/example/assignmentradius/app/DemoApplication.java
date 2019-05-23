package com.example.assignmentradius.app;

import android.app.Application;

import com.example.assignmentradius.constants.AppConstants;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(AppConstants.DB_NAME)
                .schemaVersion(AppConstants.DB_SCHEMA_VERSION)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
