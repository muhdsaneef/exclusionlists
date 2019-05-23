package com.example.assignmentradius.utils;

import com.example.assignmentradius.R;
import com.example.assignmentradius.constants.AppConstants;

public class AppUtils {

    public static int getIconResourceId(String iconName) {
        int resID = -1;
        switch (iconName) {
            case AppConstants.ICON_ROOMS:
                resID = R.drawable.rooms;
                break;
            case AppConstants.ICON_APARTMENT:
                resID = R.drawable.apartment;
                break;
            case AppConstants.ICON_CONDO:
                resID = R.drawable.condo;
                break;
            case AppConstants.ICON_LAND:
                resID = R.drawable.land;
                break;
            case AppConstants.ICON_NO_ROOMS:
                resID = R.drawable.no_room;
                break;
            case AppConstants.ICON_BOAT:
                resID = R.drawable.boat;
                break;
            case AppConstants.ICON_SWIMMING:
                resID = R.drawable.swimming;
                break;
            case AppConstants.ICON_GARDEN:
                resID = R.drawable.garden;
                break;
            case AppConstants.ICON_GARAGE:
                resID = R.drawable.garage;
                break;
        }
        return resID;
    }
}
