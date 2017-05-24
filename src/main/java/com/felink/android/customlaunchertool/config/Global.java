package com.felink.android.customlaunchertool.config;

import android.content.Context;
import android.os.Handler;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月19日 19:52.</br>
 * @update: </br>
 */

public class Global {

    public static Context context;
    public static Handler handler;

    public static Context getApplicationContext() {
        return context;
    }

    public static void runInMainThread(Runnable task) {
        handler.post(task);
    }
}
