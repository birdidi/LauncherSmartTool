package com.felink.android.customlaunchertool.kitset.permission;

import android.content.Context;
import android.os.Build;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月13日 10:16.</br>
 * @update: </br>
 */

public class AppOpsManagerCompat {

    /**
     * Result from {@link #noteOp}: the given caller is allowed to
     * perform the given operation.
     */
    public static final int MODE_ALLOWED = 0;

    /**
     * Result from {@link #noteOp}: the given caller is not allowed to perform
     * the given operation, and this attempt should <em>silently fail</em> (it
     * should not cause the app to crash).
     */
    public static final int MODE_IGNORED = 1;

    /**
     * Result from {@link #noteOp}: the given caller should use its default
     * security check.  This mode is not normally used; it should only be used
     * with appop permissions, and callers must explicitly check for it and
     * deal with it.
     */
    public static final int MODE_DEFAULT = 3;

    public static final int MODE_ERRORED = 2;

    private static class AppOpsManagerImpl {
        public String permissionToOp(String permission) throws Exception {
            return AppOpsManagerCompatLess23.permissionToOp(permission);
        }

        public int noteOp(Context context, String op, int uid, String packageName) throws Exception {
            return AppOpsManagerCompatLess23.noteOp(context, op, uid, packageName);
        }

        public int noteProxyOp(Context context, String op, String proxiedPackageName) throws Exception {
            return AppOpsManagerCompatLess23.noteProxyOp(context, op, proxiedPackageName);
        }

        public int checkOp(Context context, int op, int uid, String packageName) throws Exception {
            return AppOpsManagerCompatLess23.checkOp(context, op, uid, packageName);
        }

        public int permissionToOpInt(String permission) throws Exception {
            return AppOpsManagerCompatLess23.permissionToOpInt(permission);
        }
    }

    private static class AppOpsManager23 extends AppOpsManagerImpl {
        @Override
        public String permissionToOp(String permission) throws Exception {
            return AppOpsManagerCompat23.permissionToOp(permission);
        }

        @Override
        public int noteOp(Context context, String op, int uid, String packageName) throws Exception {
            return AppOpsManagerCompat23.noteOp(context, op, uid, packageName);
        }

        @Override
        public int noteProxyOp(Context context, String op, String proxiedPackageName) throws Exception {
            return AppOpsManagerCompat23.noteProxyOp(context, op, proxiedPackageName);
        }

        @Override
        public int checkOp(Context context, int op, int uid, String packageName) throws Exception {
            return AppOpsManagerCompat23.checkOp(context, op, uid, packageName);
        }

        @Override
        public int permissionToOpInt(String permission) throws Exception {
            return super.permissionToOpInt(permission);
        }
    }

    private static final AppOpsManagerImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new AppOpsManager23();
        } else {
            IMPL = new AppOpsManagerImpl();
        }
    }

    private AppOpsManagerCompat() {
    }

    /**
     * Gets the app op name associated with a given permission.
     *
     * @param permission The permission.
     * @return The app op associated with the permission or null.
     */
    public static String permissionToOp(String permission) throws Exception {
        return IMPL.permissionToOp(permission);
    }

    /**
     * Make note of an application performing an operation.  Note that you must pass
     * in both the uid and name of the application to be checked; this function will verify
     * that these two match, and if not, return {@link #MODE_IGNORED}.  If this call
     * succeeds, the last execution time of the operation for this app will be updated to
     * the current time.
     *
     * @param context     Your context.
     * @param op          The operation to note.  One of the OPSTR_* constants.
     * @param uid         The user id of the application attempting to perform the operation.
     * @param packageName The name of the application attempting to perform the operation.
     * @return Returns {@link #MODE_ALLOWED} if the operation is allowed, or
     * {@link #MODE_IGNORED} if it is not allowed and should be silently ignored (without
     * causing the app to crash).
     * @throws SecurityException If the app has been configured to crash on this op.
     */
    public static int noteOp(Context context, String op, int uid, String packageName) throws Exception {
        return IMPL.noteOp(context, op, uid, packageName);
    }

    /**
     * Make note of an application performing an operation on behalf of another
     * application when handling an IPC. Note that you must pass the package name
     * of the application that is being proxied while its UID will be inferred from
     * the IPC state; this function will verify that the calling uid and proxied
     * package name match, and if not, return {@link #MODE_IGNORED}. If this call
     * succeeds, the last execution time of the operation for the proxied app and
     * your app will be updated to the current time.
     *
     * @param context            Your context.
     * @param op                 The operation to note.  One of the OPSTR_* constants.
     * @param proxiedPackageName The name of the application calling into the proxy application.
     * @return Returns {@link #MODE_ALLOWED} if the operation is allowed, or
     * {@link #MODE_IGNORED} if it is not allowed and should be silently ignored (without
     * causing the app to crash).
     * @throws SecurityException If the app has been configured to crash on this op.
     */
    public static int noteProxyOp(Context context, String op, String proxiedPackageName) throws Exception {
        return IMPL.noteProxyOp(context, op, proxiedPackageName);
    }

    public static int checkOp(Context context, int op, int uid, String packageName) throws Exception {
        return IMPL.checkOp(context, op, uid, packageName);
    }

    public static int permissionToOpInt(String permission) throws Exception {
        return IMPL.permissionToOpInt(permission);
    }
}
