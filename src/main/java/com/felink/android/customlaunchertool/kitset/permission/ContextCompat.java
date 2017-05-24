package com.felink.android.customlaunchertool.kitset.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.util.Log;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class ContextCompat {

    public static int checkSelfPermission(Context context, String permission) {
        if (permission != null) {
            int granted = context.checkPermission(permission, Process.myPid(), Process.myUid());

            if (Build.VERSION.SDK_INT >= 19) {
                try {
                    int checkOp = checkOp(context, permission);
                    if (checkOp == AppOpsManagerCompat.MODE_DEFAULT) {
                        checkOp = context.checkCallingOrSelfPermission(permission);
                    }
                    return (granted == 0 && (checkOp == AppOpsManagerCompat.MODE_ALLOWED || checkOp == AppOpsManagerCompat.MODE_ERRORED))
                            ? PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED;
                } catch (Exception e) {
                }
            }

            return granted;
        }
        throw new IllegalArgumentException("permission is null");
    }

    public static int checkOp(Context context, String permission) {
        int checkOp = AppOpsManagerCompat.MODE_IGNORED;
        try {
            int op = AppOpsManagerCompat.permissionToOpInt(permission);
            if (op == -1) {
                checkOp = AppOpsManagerCompat.MODE_DEFAULT;
            } else {
                if (Build.VERSION.SDK_INT >= 23) {
                    checkOp = AppOpsManagerCompat.checkOp(context, op, Binder.getCallingUid(), context.getPackageName());
                } else {
                    checkOp = AppOpsManagerCompat.checkOp(context, op, Binder.getCallingUid(), context.getPackageName());
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            checkOp = AppOpsManagerCompat.MODE_ERRORED;
        }

        return checkOp;
    }
}
