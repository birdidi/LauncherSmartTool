package com.felink.android.customlaunchertool.kitset.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class ActivityCompat {

    public interface OnRequestPermissionsResultCallback {
        void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);
    }

    public static void requestPermissions(final Activity activity, final String[] permissions, final int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompatApi23.requestPermissions(activity, permissions, requestCode);
        } else if (activity instanceof OnRequestPermissionsResultCallback) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    int[] grantResults = new int[permissions.length];
                    PackageManager packageManager = activity.getPackageManager();
                    String packageName = activity.getPackageName();
                    int permissionCount = permissions.length;
                    for (int i = 0; i < permissionCount; i++) {
                        grantResults[i] = packageManager.checkPermission(permissions[i], packageName);
                    }
                    ((OnRequestPermissionsResultCallback) activity).onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            });
        }
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ActivityCompatApi23.shouldShowRequestPermissionRationale(activity, permission);
        }
        return false;
    }


}
