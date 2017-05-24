package com.felink.android.customlaunchertool;

import android.app.Application;
import android.os.Handler;

import com.felink.android.customlaunchertool.config.Global;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月19日 19:52.</br>
 * @update: </br>
 */

public class ToolApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Global.handler = new Handler();
        Global.context = getApplicationContext();
    }
}
