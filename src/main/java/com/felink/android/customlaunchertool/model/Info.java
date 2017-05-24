package com.felink.android.customlaunchertool.model;

import android.content.pm.ActivityInfo;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月26日 18:52.</br>
 * @update: </br>
 */

public class Info {

    public ActivityInfo activityInfo;
    public int priority = 0;

    public Info(ActivityInfo activityInfo, int priority){
        this.activityInfo = activityInfo;
        this.priority = priority;
    }
}
