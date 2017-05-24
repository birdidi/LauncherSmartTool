package com.felink.android.customlaunchertool.kitset.permission;

import android.annotation.TargetApi;
import android.content.Context;

import com.felink.android.customlaunchertool.config.Global;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月13日 10:16.</br>
 * @update: </br>
 */

@TargetApi(23)
public class AppOpsManagerCompatLess23 {

    public static String permissionToOp(String permission) throws Exception {
        return null;
    }

    public static int noteOp(Context context, String op, int uid, String packageName) throws Exception {
        Object appOpsManager = context.getSystemService("appops");
        Object legacy = reflectInvoke("checkOpNoThrow", appOpsManager, appOpsManager.getClass(), new Object[]{op, uid, packageName}, new Class[]{Integer.TYPE, Integer.TYPE, String.class});
        return legacy == null ? -1 : (Integer) (legacy);
    }

    public static int noteProxyOp(Context context, String op, String proxiedPackageName) throws Exception {
        return AppOpsManagerCompat.MODE_IGNORED;
    }

    public static int checkOp(Context context, int op, int uid, String packageName) throws Exception {
        Object appOpsManager = context.getSystemService("appops");
        Object legacy = reflectInvoke("checkOp", appOpsManager, appOpsManager.getClass(), new Object[]{op, uid, packageName}, new Class[]{Integer.TYPE, Integer.TYPE, String.class});
        return (Integer) legacy;
    }

    public static int permissionToOpInt(String permission) throws Exception {
        Object appOpsManager = Global.getApplicationContext().getSystemService("appops");
        Object obj = reflectField("sOpPerms", null, appOpsManager.getClass());
        if (obj instanceof String[]) {
            String[] sOpPerms = (String[]) obj;
            for (int i = 0, len = sOpPerms.length; i < len; i++) {
                String p = sOpPerms[i];
                if (permission.equals(p)) {
                    return i;
                }
            }
        }

        return -1;
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

    private static Object reflectField(String field, Object instance, Class clazz) throws Exception {
        Field sOpPerms = clazz.getDeclaredField(field);
        sOpPerms.setAccessible(true);
        return sOpPerms.get(instance);
    }
}
