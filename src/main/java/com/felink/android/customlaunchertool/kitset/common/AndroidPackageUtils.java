package com.felink.android.customlaunchertool.kitset.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月15日 14:08.</br>
 * @update: </br>
 */

public class AndroidPackageUtils {

    public AndroidPackageUtils() {
    }

    public static boolean hasNewVersion(Context context, String packageName, String oldVersion) {
        Resources res = context.getResources();
        int resId = res.getIdentifier(packageName, "string", context.getPackageName());
        if(0 != resId) {
            String newVersion = res.getString(resId);
            return TelephoneUtil.isExistNewVersion(newVersion, oldVersion);
        } else {
            return false;
        }
    }

    public static boolean hasNewVersion(Context context, String packageName, int oldVersionCode) {
        Resources res = context.getResources();
        int resId = res.getIdentifier(packageName, "string", context.getPackageName());
        if(0 != resId) {
            int newVersionCode = Integer.parseInt(res.getString(resId));
            return newVersionCode > oldVersionCode;
        } else {
            return false;
        }
    }

    public static List<ResolveInfo> queryMainIntentActivity(PackageManager pm) {
        Intent mainIntent = new Intent("android.intent.action.MAIN", (Uri)null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        Object result = pm.queryIntentActivities(mainIntent, 0);
        if(result == null) {
            result = new ArrayList();
        }

        return (List)result;
    }

    public static Map<ComponentName, String> queryIntentActivity(PackageManager pm, Intent intent) {
        List result = pm.queryIntentActivities(intent, 0);
        if(result == null) {
            return new HashMap();
        } else {
            HashMap map = new HashMap();
            Iterator var4 = result.iterator();

            while(var4.hasNext()) {
                ResolveInfo info = (ResolveInfo)var4.next();
                map.put(new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name), "");
            }

            return map;
        }
    }

    public static ResolveInfo getResolveInfo(Intent intent, PackageManager pm) {
        if(intent == null) {
            return null;
        } else {
            ResolveInfo result = pm.resolveActivity(intent, 0);
            return result;
        }
    }

    public static String getIntentLabel(Intent intent, PackageManager pm) {
        if(intent == null) {
            return null;
        } else {
            ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            String title = resolveInfo.loadLabel(pm).toString();
            if(title == null) {
                title = resolveInfo.activityInfo.name;
            }

            return title;
        }
    }

    public static void showAppDetails(Context ctx, String packageName) {
        Intent intent = new Intent();
        if(Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            Uri e = Uri.fromParts("package", packageName, (String)null);
            intent.setData(e);
            intent.setFlags(276824064);
        } else {
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", packageName);
            intent.putExtra("pkg", packageName);
        }

        try {
            ctx.startActivity(intent);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static boolean isPkgInstalled(Context ctx, String packageName) {
        if(StringUtil.isEmpty(packageName)) {
            return false;
        } else {
            PackageManager pm = ctx.getPackageManager();

            try {
                pm.getPackageInfo(packageName, 0);
                return true;
            } catch (PackageManager.NameNotFoundException var4) {
                return false;
            }
        }
    }

    public static boolean isPkgInstalled(Context ctx, String packageName, int versionCode) {
        PackageManager pm = ctx.getPackageManager();

        try {
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            return packageName.equalsIgnoreCase(info.packageName) && versionCode == info.versionCode;
        } catch (PackageManager.NameNotFoundException var5) {
            return false;
        }
    }


    public static Intent getPackageMainIntent(Context ctx, String packageName) {
        if(StringUtil.isAnyEmpty(new String[]{packageName})) {
            return null;
        } else {
            PackageManager pm = ctx.getPackageManager();

            try {
                Intent e = new Intent("android.intent.action.MAIN", (Uri)null);
                e.addCategory("android.intent.category.LAUNCHER");
                e.setFlags(270532608);
                e.setPackage(packageName);
                List apps = pm.queryIntentActivities(e, 0);
                Iterator var5 = apps.iterator();

                ResolveInfo info;
                do {
                    if(!var5.hasNext()) {
                        return null;
                    }

                    info = (ResolveInfo)var5.next();
                } while(!packageName.equals(info.activityInfo.applicationInfo.packageName));

                ComponentName componentName = new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);
                e.setComponent(componentName);
                return e;
            } catch (Exception var8) {
                return null;
            }
        }
    }

    public static Intent getNewTaskIntent(ComponentName actualComponent) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setComponent(actualComponent);
        intent.setFlags(270532608);
        return intent;
    }

    public static int getVersionCode(Context ctx, String pkg) {
        if(ctx == null) {
            return -1001;
        } else {
            try {
                PackageInfo e = ctx.getPackageManager().getPackageInfo(pkg, 0);
                return e.versionCode;
            } catch (Exception var3) {
                var3.printStackTrace();
                return -1001;
            }
        }
    }
}
