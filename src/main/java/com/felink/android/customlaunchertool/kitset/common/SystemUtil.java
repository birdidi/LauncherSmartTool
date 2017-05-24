package com.felink.android.customlaunchertool.kitset.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月15日 14:09.</br>
 * @update: </br>
 */

public class SystemUtil {

    private static final String TAG = "SystemUtil";

    public SystemUtil() {
    }

    public static void openPage(Context ctx, String url) {
        try {
            if(null != url && !url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }

            Uri e = Uri.parse(url);
            Intent intent = new Intent("android.intent.action.VIEW", e);
            intent.setFlags(268435456);
            ctx.startActivity(intent);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void showKeyboard(View view) {
        if(null != view) {
            InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService("input_method");
            imm.showSoftInput(view, 0);
        }
    }

    public static void hideKeyboard(View view) {
        if(null != view) {
            InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService("input_method");
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void createHideInputMethod(Activity ctx) {
        final InputMethodManager manager = (InputMethodManager)ctx.getSystemService("input_method");
        ctx.getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(manager.isActive()) {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                return true;
            }
        });
    }

    public static void makeShortToast(Context ctx, int resId) {
        if(resId != 0) {
            Toast.makeText(ctx, ctx.getText(resId), 0).show();
        }
    }

    public static Drawable getDrawableByResourceName(Context ctx, String resName) {
        if(TextUtils.isEmpty(resName)) {
            return null;
        } else {
            Resources res = ctx.getResources();
            int resId = res.getIdentifier(resName, "drawable", ctx.getPackageName());
            return resId == 0?null:res.getDrawable(resId);
        }
    }

    public static Bitmap getBitmapByResourceName(Context ctx, String resName) {
        if(TextUtils.isEmpty(resName)) {
            return null;
        } else {
            Resources res = ctx.getResources();
            int resId = res.getIdentifier(resName, "drawable", ctx.getPackageName());
            return resId == 0?null:((BitmapDrawable)res.getDrawable(resId)).getBitmap();
        }
    }

    public static int getResourceId(Context ctx, String key, String type) {
        return ctx.getResources().getIdentifier(key, type, ctx.getPackageName());
    }

    public static boolean isSystemApplication(ApplicationInfo appInfo) {
        return appInfo == null?false:isSystemApplication(appInfo.flags);
    }

    public static boolean isSystemApplication(int flags) {
        return (flags & 128) != 0?true:(flags & 1) != 0;
    }

    public static boolean isVoiceRecognitionEnable(Context context) {
        PackageManager pm = context.getPackageManager();
        List activities = pm.queryIntentActivities(new Intent("android.speech.action.RECOGNIZE_SPEECH"), 0);
        return activities.size() != 0;
    }

    public static String getCurProcessName(Context context) {
        try {
            int e = Process.myPid();
            ActivityManager mActivityManager = (ActivityManager)context.getSystemService("activity");
            Iterator var3 = mActivityManager.getRunningAppProcesses().iterator();

            ActivityManager.RunningAppProcessInfo appProcess;
            do {
                if(!var3.hasNext()) {
                    return null;
                }

                appProcess = (ActivityManager.RunningAppProcessInfo)var3.next();
            } while(appProcess.pid != e);

            return appProcess.processName;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }
}
