package com.felink.android.customlaunchertool.kitset.permission;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;

import com.felink.android.customlaunchertool.config.Global;

import java.lang.reflect.Method;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月13日 10:16.</br>
 * @update: </br>
 */
@TargetApi(23)
public class AppOpsManagerCompat23 {
    public static String permissionToOp(String permission) throws Exception {
        Object appOpsManager = Global.getApplicationContext().getSystemService("appops");
        Object legacy = reflectInvoke("permissionToOp", null, appOpsManager.getClass(), new Object[]{permission}, new Class[]{String.class});
        return legacy == null ? null : legacy.toString();
    }

    public static int noteOp(Context context, String op, int uid, String packageName) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        return appOpsManager.noteOp(op, uid, packageName);
    }

    public static int noteProxyOp(Context context, String op, String proxiedPackageName) throws Exception {
        Object appOpsManager = context.getSystemService("appops");
        Object legacy = reflectInvoke("noteProxyOp", appOpsManager, appOpsManager.getClass(), new Object[]{op, proxiedPackageName}, new Class[]{String.class, String.class});
        return (Integer) legacy;
    }

    public static int checkOp(Context context, int op, int uid, String packageName) throws Exception {
        Object appOpsManager = context.getSystemService("appops");
        Object legacy = reflectInvoke("checkOpNoThrow", appOpsManager, appOpsManager.getClass(), new Object[]{op, uid, packageName}, new Class[]{Integer.TYPE, Integer.TYPE, String.class});
        return (Integer) legacy;
    }

    private static Object reflectInvoke(String method, Object instance, Class clazz, Object[] params, Class[] clazzes) throws Exception {

        Method target;
        if (params != null) {
            target = clazz.getDeclaredMethod(method, clazzes);
        } else {
            target = clazz.getDeclaredMethod(method);
        }

        if (target != null) {
            target.setAccessible(true);
            return target.invoke(instance, params);
        }
        return null;
    }
}
