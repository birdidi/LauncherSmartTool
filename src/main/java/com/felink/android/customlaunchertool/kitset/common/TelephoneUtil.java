package com.felink.android.customlaunchertool.kitset.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月14日 13:51.</br>
 * @update: </br>
 */

public class TelephoneUtil {

    private static final String TAG = "TelephoneUtil";
    public static final String CONST_STRING_PREFIX_MAC = "HIM_";
    public static final String CONST_STRING_PREFIX_IMEI = "HIE_";
    public static boolean switchPrefix = false;
    private static String MIUI_VERSION;
    private static boolean IS_READ_MIUI = false;

    public TelephoneUtil() {
    }

    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager)ctx.getSystemService("phone");
        if(tm == null) {
            return "";
        } else {
            String result = null;

            try {
                result = tm.getDeviceId();
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return result == null?"":result;
        }
    }

    public static String getIMSI(Context ctx) {
        TelephonyManager tm = (TelephonyManager)ctx.getSystemService("phone");
        if(tm == null) {
            return "";
        } else {
            String result = null;

            try {
                result = tm.getSubscriberId();
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return result == null?"":result;
        }
    }

    public static String getMAC(Context ctx) {
        String mac = "";

        try {
            WifiManager e = (WifiManager)((WifiManager)ctx.getSystemService("wifi"));
            if(e == null) {
                return mac;
            }

            WifiInfo info = e.getConnectionInfo();
            if(info == null) {
                return mac;
            }

            mac = info.getMacAddress();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return mac;
    }

    public static String getMachineName() {
        return switchPrefix?"HIM_" + Build.MODEL:Build.MODEL;
    }

    public static String getFirmWareVersion() {
        String version_3 = "1.5";
        String version_4 = "1.6";
        String version_5 = "2.0";
        String version_6 = "2.0.1";
        String version_7 = "2.1";
        String version_8 = "2.2";
        String version_9 = "2.3";
        String version_10 = "2.3.3";
        String version_11 = "3.0";
        String version_12 = "3.1";
        String version_13 = "3.2";
        String version_14 = "4.0";
        String version_15 = "4.0.3";
        String version_16 = "4.1.1";
        String version_17 = "4.2";
        String version_18 = "4.3";
        String version_19 = "4.4";
        String version_20 = "4.4W";
        String version_21 = "5.0";
        String version_22 = "5.1";
        String version_23 = "6.0";
        String versionName = "";

        try {
            int e = Integer.parseInt(Build.VERSION.SDK);
            switch(e) {
                case 3:
                    versionName = "1.5";
                    break;
                case 4:
                    versionName = "1.6";
                    break;
                case 5:
                    versionName = "2.0";
                    break;
                case 6:
                    versionName = "2.0.1";
                    break;
                case 7:
                    versionName = "2.1";
                    break;
                case 8:
                    versionName = "2.2";
                    break;
                case 9:
                    versionName = "2.3";
                    break;
                case 10:
                    versionName = "2.3.3";
                    break;
                case 11:
                    versionName = "3.0";
                    break;
                case 12:
                    versionName = "3.1";
                    break;
                case 13:
                    versionName = "3.2";
                    break;
                case 14:
                    versionName = "4.0";
                    break;
                case 15:
                    versionName = "4.0.3";
                    break;
                case 16:
                    versionName = "4.1.1";
                    break;
                case 17:
                    versionName = "4.2";
                    break;
                case 18:
                    versionName = "4.3";
                    break;
                case 19:
                    versionName = "4.4";
                    break;
                case 20:
                    versionName = "4.4W";
                    break;
                case 21:
                    versionName = "5.0";
                    break;
                case 22:
                    versionName = "5.1";
                    break;
                case 23:
                    versionName = "6.0";
                    break;
                default:
                    versionName = "6.0";
            }
        } catch (Exception var23) {
            versionName = "6.0";
        }

        return versionName;
    }

    public static String getVersionName(Context ctx) {
        return getVersionName(ctx, ctx.getPackageName());
    }

    public static String getVersionName(Context context, String packageName) {
        String versionName = "";

        try {
            PackageInfo e = context.getPackageManager().getPackageInfo(packageName, 128);
            versionName = e.versionName;
        } catch (Exception var4) {
            Log.e("TelephoneUtil", var4.toString());
        }

        return versionName;
    }

    public static int getVersionCode(Context ctx) {
        return getVersionCode(ctx, ctx.getPackageName());
    }

    public static int getVersionCode(Context ctx, String packageName) {
        int versionCode = 0;

        try {
            PackageInfo e = ctx.getPackageManager().getPackageInfo(packageName, 16);
            versionCode = e.versionCode;
        } catch (Exception var4) {
            Log.e("TelephoneUtil", var4.toString());
        }

        return versionCode;
    }

    public static boolean isExistNewVersion(String newVersionName, String oldeVersionName) {
        if(oldeVersionName.toLowerCase().startsWith("v")) {
            oldeVersionName = oldeVersionName.substring(1, oldeVersionName.length());
        }

        if(newVersionName.toLowerCase().startsWith("v")) {
            newVersionName = newVersionName.substring(1, oldeVersionName.length());
        }

        if(oldeVersionName != null && newVersionName != null) {
            if(oldeVersionName.trim().length() != 0 && newVersionName.trim().length() != 0) {
                try {
                    List e = parser(oldeVersionName.trim(), '.');
                    List versionCodes = parser(newVersionName.trim(), '.');

                    for(int i = 0; i < e.size(); ++i) {
                        if(i > versionCodes.size() - 1) {
                            return false;
                        }

                        int a = Integer.parseInt(((String)e.get(i)).trim());
                        int b = Integer.parseInt(((String)versionCodes.get(i)).trim());
                        if(a < b) {
                            return true;
                        }

                        if(a > b) {
                            return false;
                        }
                    }

                    if(e.size() < versionCodes.size()) {
                        return true;
                    }
                } catch (Exception var7) {
                    var7.printStackTrace();
                }

                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static List<String> parser(String value, char c) {
        ArrayList ss = new ArrayList();
        char[] cs = value.toCharArray();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < cs.length; ++i) {
            if(c == cs[i]) {
                ss.add(sb.toString());
                sb = new StringBuffer();
            } else {
                sb.append(cs[i]);
            }
        }

        if(sb.length() > 0) {
            ss.add(sb.toString());
        }

        return ss;
    }

    public static synchronized boolean isNetworkAvailable(Context context) {
        boolean result = false;
        if(context == null) {
            return result;
        } else {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
            NetworkInfo networkInfo = null;
            if(null != connectivityManager) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if(null != networkInfo && networkInfo.isAvailable() && networkInfo.isConnected()) {
                    result = true;
                }
            }

            return result;
        }
    }

    public static boolean isWifiEnable(Context ctx) {
        if(ctx == null) {
            return false;
        } else {
            try {
                return isWifiNetwork(ctx);
            } catch (Exception var2) {
                var2.printStackTrace();
                return isWifiOpen(ctx);
            }
        }
    }

    private static boolean isWifiOpen(Context ctx) {
        try {
            Object e = ctx.getSystemService("wifi");
            if(e == null) {
                return false;
            } else {
                WifiManager wifiManager = (WifiManager)e;
                return wifiManager.isWifiEnabled();
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    private static boolean isWifiNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == 1;
    }

    public static int getNetworkState(Context ctx) {
        return isWifiEnable(ctx)?0:1;
    }

    public static boolean isSimExist(Context ctx) {
        TelephonyManager manager = (TelephonyManager)ctx.getSystemService("phone");
        return manager.getSimState() != 1;
    }

    public static boolean isSdcardExist() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String getScreenResolution(Context ctx) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)ctx.getApplicationContext().getSystemService("window");
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        String resolution = width + "x" + height;
        return resolution;
    }

    public static int[] getScreenResolutionXY(Context ctx) {
        int[] resolutionXY = new int[2];
        if(resolutionXY[0] != 0) {
            return resolutionXY;
        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager)ctx.getApplicationContext().getSystemService("window");
            windowManager.getDefaultDisplay().getMetrics(metrics);
            resolutionXY[0] = metrics.widthPixels;
            resolutionXY[1] = metrics.heightPixels;
            return resolutionXY;
        }
    }

    public static float getScreenDensity(Context ctx) {
        return ctx.getResources().getDisplayMetrics().density;
    }

    public static boolean queryBroadcastReceiver(Context ctx, String actionName) {
        PackageManager pm = ctx.getPackageManager();

        try {
            Intent e = new Intent(actionName);
            List apps = pm.queryBroadcastReceivers(e, 0);
            return !apps.isEmpty();
        } catch (Exception var5) {
            Log.d("TelephoneUtil", "queryBroadcastReceivers: " + var5.toString());
            return false;
        }
    }

    public static String getWifiAddress(Context ctx) {
        try {
            WifiManager e = (WifiManager)ctx.getSystemService("wifi");
            if(e.isWifiEnabled()) {
                WifiInfo wifiInfo = e.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                String ip = intToIp(ipAddress);
                return ip;
            } else {
                return "";
            }
        } catch (Exception var5) {
            var5.printStackTrace();
            return "";
        }
    }

    public static int getApiLevel() {
        int apiLevel = 7;

        try {
            apiLevel = Integer.parseInt(Build.VERSION.SDK);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return apiLevel;
    }

    public static String getCPUABI() {
        String abi = Build.CPU_ABI;
        abi = abi != null && abi.trim().length() != 0?abi:"";

        try {
            String cpuAbi2 = Build.class.getField("CPU_ABI2").get((Object)null).toString();
            cpuAbi2 = cpuAbi2 != null && cpuAbi2.trim().length() != 0?cpuAbi2:null;
            if(cpuAbi2 != null) {
                abi = abi + "," + cpuAbi2;
            }
        } catch (Exception var2) {
            ;
        }

        return abi;
    }

    private static String intToIp(int i) {
        return (i & 255) + "." + (i >> 8 & 255) + "." + (i >> 16 & 255) + "." + (i >> 24 & 255);
    }

    public static boolean isZh(Context ctx) {
        Locale lo = ctx.getResources().getConfiguration().locale;
        return lo.getLanguage().equals("zh");
    }

    public static boolean hasRootPermission() {
        boolean rooted = true;

        try {
            File e = new File("/system/bin/su");
            if(!e.exists()) {
                e = new File("/system/xbin/su");
                if(!e.exists()) {
                    rooted = false;
                }
            }
        } catch (Exception var2) {
            rooted = false;
        }

        return rooted;
    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getLocalMacAddress(Context ctx) {
        WifiManager wifi = (WifiManager)ctx.getSystemService("wifi");
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public static String getNetworkTypeName(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService("connectivity");
        NetworkInfo info = cm.getActiveNetworkInfo();
        return null != info && null != info.getTypeName()?info.getTypeName():"unknown";
    }

    public static String getNT(Context ctx) {
        String imsi = getIMSI(ctx);
        String nt = "0";
        if(isWifiEnable(ctx)) {
            nt = "10";
        } else if(imsi == null) {
            nt = "0";
        } else if(imsi.length() > 5) {
            String mnc = imsi.substring(3, 5);
            if(!mnc.equals("00") && !mnc.equals("02")) {
                if(mnc.equals("01")) {
                    nt = "31";
                } else if(mnc.equals("03")) {
                    nt = "32";
                }
            } else {
                nt = "53";
            }
        }

        return nt;
    }

    public static boolean isMI2Moble() {
        try {
            return getMachineName().contains("MI 2");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isMIMoble() {
        try {
            return getManufacturer().equalsIgnoreCase("xiaomi");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isLetvMoble() {
        try {
            return getManufacturer().equalsIgnoreCase("letv") || getManufacturer().equalsIgnoreCase("leeco") || getManufacturer().equalsIgnoreCase("lemobile");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isHTC_G13_Mobile() {
        try {
            return getMachineName().contains("HTC A510e");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isNote2() {
        try {
            return getMachineName().contains("GT-N71") || getMachineName().contains("SCH-N719");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isHTC_ONE() {
        try {
            return getMachineName().contains("HTC 802") || getMachineName().contains("HTC 801e") || getMachineName().contains("HTC One");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isHTCPhone() {
        try {
            return getMachineName().toLowerCase().contains("htc");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isLGPhone() {
        try {
            return getMachineName().toLowerCase().contains("lg-");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isOppo_42() {
        try {
            String e = getManufacturer();
            String version = getFirmWareVersion();
            return "OPPO".equalsIgnoreCase(e) && "4.2".equals(version);
        } catch (Exception var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static boolean isHTC_4OrAbove() {
        try {
            return getManufacturer().equalsIgnoreCase("HTC") && getApiLevel() >= 14;
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isAmoiN79_235() {
        try {
            return getMachineName().contains("AMOI_N79+") && Build.VERSION.RELEASE.equals("2.3.5");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isOppoOldPhone() {
        try {
            return getMachineName().contains("R805") || getMachineName().contains("T703") || getManufacturer().equalsIgnoreCase("alps");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isVivoPhone() {
        try {
            return getMachineName().toLowerCase().contains("vivo") || getManufacturer().toLowerCase().contains("vivo") || getManufacturer().equalsIgnoreCase("BBK");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isOppoPhone() {
        try {
            return getMachineName().toLowerCase().contains("oppo") || getManufacturer().toLowerCase().contains("oppo");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isSamsungPhone() {
        try {
            return getMachineName().toLowerCase().contains("samsung") || getManufacturer().toLowerCase().contains("samsung");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isKtouchPhone() {
        try {
            return getMachineName().contains("W700");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isCoolpadPhone() {
        try {
            return getMachineName().contains("Coolpad") || getManufacturer().equalsIgnoreCase("YuLong");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isLenovoPhone() {
        try {
            return getMachineName().toLowerCase().contains("lenovo") || getManufacturer().toLowerCase().contains("lenovo");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isHuaweiPhone() {
        try {
            return getMachineName().toLowerCase().contains("huawei") || getManufacturer().toLowerCase().contains("huawei");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isHuaweiMT1() {
        try {
            return isHuaweiPhone() && getMachineName().contains("MT1");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isCoolpad8908() {
        try {
            return isCoolpadPhone() && getMachineName().contains("8908");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isCoolpad8750() {
        try {
            return isCoolpadPhone() && getMachineName().contains("8750");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isLenovoPhoneK900() {
        try {
            return isLenovoPhone() && getMachineName().contains("K900");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isMotorolaPhone() {
        try {
            return getManufacturer().toLowerCase().contains("motorola");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isSonyPhone() {
        try {
            String e = getManufacturer();
            return e.equalsIgnoreCase("SONY") || e.equalsIgnoreCase("Sony Ericsson");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isMeizuPhone42() {
        try {
            return getManufacturer().toLowerCase().contains("meizu") && Build.VERSION.SDK_INT >= 17;
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isMeizuPhone() {
        try {
            return getManufacturer().equalsIgnoreCase("meizu");
        } catch (Exception var1) {
            return false;
        }
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static boolean isNexus6() {
        try {
            return getMachineName().toLowerCase().contains("nexus 6");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isMINoteLte() {
        try {
            return getMachineName().toLowerCase().contains("mi note lte");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isCoolpadT1() {
        try {
            return getMachineName().toLowerCase().contains("coolpad t1");
        } catch (Exception var1) {
            return false;
        }
    }

    public static String getMiuiVersion() {
        if(!IS_READ_MIUI) {
            IS_READ_MIUI = true;
            MIUI_VERSION = getProp("ro.miui.ui.version.code");
        }

        Log.e("getMiuiVersion", MIUI_VERSION + "");
        return MIUI_VERSION;
    }

    public static boolean isOppoColorOS200() {
        String version = getProp("ro.build.version.opporom");
        return version != null && version.startsWith("V2.");
    }

    public static String getProp(String propName) {
        String result = null;

        try {
            Process e1 = Runtime.getRuntime().exec("getprop " + propName);
            InputStreamReader ir = new InputStreamReader(e1.getInputStream());
            BufferedReader input = new BufferedReader(ir);
            String prop = input.readLine();
            ir.close();
            e1.destroy();
            result = prop;
        } catch (Exception var6) {
            result = null;
        } catch (Error var7) {
            result = null;
        }

        if("".equals(result)) {
            result = null;
        }

        return result;
    }

    public static boolean isLeShiX600() {
        try {
            return getMachineName().contains("X600");
        } catch (Exception var1) {
            return false;
        }
    }

    public static boolean isBlueMeizu() {
        try {
            return getMachineName().equals("m2") && getManufacturer().equals("Meizu");
        } catch (Exception var1) {
            return false;
        }
    }
}
