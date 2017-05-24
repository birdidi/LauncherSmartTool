package com.felink.android.customlaunchertool.kitset.permission;

import android.annotation.TargetApi;
import android.app.Activity;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/4/11 0011.
 */
@TargetApi(23)
public class ActivityCompatApi23 {

    public interface RequestPermissionsRequestCodeValidator {
        void validateRequestPermissionsRequestCode(int i);
    }

    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (activity instanceof RequestPermissionsRequestCodeValidator) {
            ((RequestPermissionsRequestCodeValidator) activity).validateRequestPermissionsRequestCode(requestCode);
        }

        try {
            Class clazz = activity.getClass();
            Method requestPermissions = clazz.getMethod("requestPermissions", String[].class, int.class);
            requestPermissions.setAccessible(true);
            requestPermissions.invoke(activity, permissions, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        try {
            Class clazz = activity.getClass();
            Method requestPermissions = clazz.getMethod("shouldShowRequestPermissionRationale", String.class);
            requestPermissions.setAccessible(true);
            boolean r = (Boolean) requestPermissions.invoke(activity, permission);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
