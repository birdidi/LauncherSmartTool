package com.felink.android.customlaunchertool.kitset.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月14日 20:21.</br>
 * @update: </br>
 */

public class ThreadUtil {

    private static ExecutorService executorService;
    private static ExecutorService drawerExecutorService;
    private static ExecutorService moreExecutorService;
    private static ExecutorService otherExecutorService;

    public ThreadUtil() {
    }

    public static void execute(Runnable command) {
        if(executorService == null) {
            executorService = Executors.newFixedThreadPool(1);
        }

        executorService.execute(command);
    }

    public static void executeDrawer(Runnable command) {
        if(drawerExecutorService == null) {
            drawerExecutorService = Executors.newFixedThreadPool(1);
        }

        drawerExecutorService.execute(command);
    }

    public static void executeMore(Runnable command) {
        if(moreExecutorService == null) {
            moreExecutorService = Executors.newCachedThreadPool();
        }

        moreExecutorService.execute(command);
    }

    public static void executeOther(Runnable command) {
        if(otherExecutorService == null) {
            otherExecutorService = Executors.newFixedThreadPool(1);
        }

        otherExecutorService.execute(command);
    }
}
