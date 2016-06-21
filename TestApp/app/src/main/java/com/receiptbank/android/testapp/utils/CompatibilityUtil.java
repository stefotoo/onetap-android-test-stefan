package com.receiptbank.android.testapp.utils;

import android.os.Build;

public class CompatibilityUtil {

    public static boolean hasLollipopApi() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean hasMarshmallowApi() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
