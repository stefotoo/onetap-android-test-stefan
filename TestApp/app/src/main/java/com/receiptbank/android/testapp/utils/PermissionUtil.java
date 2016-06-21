package com.receiptbank.android.testapp.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.receiptbank.android.testapp.R;

public class PermissionUtil {
    public static boolean hasLocationPermissions(Context context) {
        return !CompatibilityUtil.hasMarshmallowApi() ||
                (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    public static void askLocationPermissions(Context context, PermissionListener listener) {
        new TedPermission(context).
                setPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION).
                setRationaleMessage(context.getString(R.string.permissions_location_rationale)).
                setPermissionListener(listener).
                check();
    }
}