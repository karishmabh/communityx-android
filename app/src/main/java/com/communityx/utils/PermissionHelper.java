package com.communityx.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class PermissionHelper {

    private Activity mActivity;

    public PermissionHelper(Activity activity) {
        this.mActivity = activity;
    }

    public boolean checkPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) return false;
        }

        return true;
    }

}
