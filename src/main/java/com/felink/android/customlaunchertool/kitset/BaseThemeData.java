package com.felink.android.customlaunchertool.kitset;

import java.util.HashMap;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月14日 13:48.</br>
 * @update: </br>
 */

public class BaseThemeData {

    public static String THEME_APP_SELECT_ACTIVITY = "com.nd.hilauncherdev.app.activity.AppResolverSelectActivity";
    public static String INTENT_BROWSER = "http://www.google.com#Intent;action=android.intent.action.VIEW;launchFlags=0x10000000;component=com.nd.android.pandahome2/com.nd.hilauncherdev.app.activity.AppResolverSelectActivity;B.is_always_select=false;end";
    public static String INTENT_PHONE = "#Intent;action=android.intent.action.DIAL;launchFlags=0x10000000;component=com.nd.android.pandahome2/com.nd.hilauncherdev.app.activity.AppResolverSelectActivity;B.is_always_select=false;end";
    public static String INTENT_CONTACTS = "content://com.android.contacts/contacts#Intent;action=android.intent.action.VIEW;launchFlags=0x10000000;component=com.nd.android.pandahome2/com.nd.hilauncherdev.app.activity.AppResolverSelectActivity;B.is_always_select=false;end";
    public static String INTENT_MMS = "content://mms-sms/#Intent;action=android.intent.action.MAIN;launchFlags=0x10000000;component=com.nd.android.pandahome2/com.nd.hilauncherdev.app.activity.AppResolverSelectActivity;B.is_always_select=false;end";
    public static String INTENT_MY_THEME = "#Intent;component="+BaseConfig.PKG_NAME+"/com.nd.android.pandahome2.manage.shop.ThemeShopMainActivity;end";
    public static String ICON_APP_STORE = "com.nd.android.pandahome2|com.nd.hilauncherdev.appstore.AppStoreSwitchActivity";
    public static final String TEXT_COLOR = "text_color";
    public static final String TEXT_SIZE = "text_size";
    public static final String ICON_MAPS = "com.google.android.apps.maps|com.google.android.maps.mapsactivity";
    public static final String ICON_THEME = "com.nd.android.pandahome2|com.nd.android.pandahome2.manage.shop.ThemeShopMainActivity";
    public static final String ICON_BROWSER = "com.android.browser|com.android.browser.browseractivity";
    public static final String ICON_PHONE = "com.android.contacts|com.android.contacts.dialtactsactivity";
    public static final String ICON_CONTACTS = "com.android.contacts|com.android.contacts.dialtactscontactsentryactivity";
    public static final String ICON_SETTINGS = "com.android.settings|com.android.settings.settings";
    public static final String ICON_MMS = "com.android.mms|com.android.mms.ui.conversationlist";
    public static final String ICON_CAMERA = "com.android.camera|com.android.camera.camera";
    public static final String ICON_CALENDAR = "com.android.calendar|com.android.calendar.calendaractivity";
    public static final String ICON_GALLERYPICKER = "com.android.camera|com.android.camera.gallerypicker";
    public static final String ICON_ALARMCLOCK = "com.android.alarmclock|com.android.alarmclock.alarmclock";
    public static final String ICON_EMAIL = "com.android.email|com.android.email.activity.welcome";
    public static final String ICON_CALCULATOR = "com.android.calculator2|com.android.calculator2.calculator";
    public static final String ICON_MUSIC = "com.android.music|com.android.music.musicbrowseractivity";
    public static final String ICON_VIDEO_CAMERA = "com.android.camera|com.android.camera.videocamera";
    public static final String ICON_VOICE_DIALER = "com.android.voicedialer|com.android.voicedialer.voicedialeractivity";
    public static final String ICON_VOICE_SEARCH = "com.google.android.voicesearch|com.google.android.voicesearch.recognitionactivity";
    public static final String ICON_GOOGLE_PLAY = "com.android.vending|com.android.vending.assetbrowseractivity";
    public static final String ICON_WEATHER = "com.android.weather|com.android.weather.weatheractivity";
    public static final String ICON_TAOBAO = "com.taobao.taobao|com.taobao.tao.mainactivity2";
    public static final String ICON_CAMERA360 = "vstudio.android.camera360|vstudio.android.camera360.gphotomain";
    public static final String ICON_HIAPK = "com.hiapk.marketpho|com.hiapk.marketpho.marketmainframe";
    public static final String ICON_MM = "com.tencent.mm|com.tencent.mm.ui.launcherui";
    public static final String ICON_WEIBO = "com.sina.weibo|com.sina.weibo.splashactivity";
    public static final String ICON_QQ = "com.tencent.qq|com.tencent.qq.splashactivity";
    public static final String ICON_PANDASPACE = "com.dragon.android.pandaspace|com.dragon.android.pandaspace.main.LoadingActivity";
    public static final String ICON_FACEBOOK = "com.facebook.katana|com.facebook.katana.loginactivity";
    public static final String ICON_TWITTER = "com.twitter.android|com.twitter.android.startactivity";
    public static final String ICON_SKYPE = "com.skype.rover|com.skype.rover.main";
    public static final String PANDA_ICON_FOREGROUND_MASK = "panda_icon_foreground_mask";
    public static final String PANDA_ICON_CUT_MASK = "panda_icon_cut_mask";
    public static final String PANDA_ICON_BACKGROUND_MASK = "panda_icon_background_mask";
    public static String PANDA_FOLDER_BACKGROUND = "panda_folder_background";
    public static final String PANDA_FOLDER_FOREGROUND_CLOSED = "panda_folder_foreground_closed";
    public static final String PANDA_FOLDER_FOREGROUND_OPEN = "panda_folder_foreground_open";
    public static final String PANDA_FOLDER_ENCRIPT_MASK = "panda_folder_encript_mask";
    public static final String PANDA_ANDROID_FOLDER_BACKGROUND = "panda_android_folder_background";
    public static final String PANDA_ANDROID_FOLDER_ENCRIPT_MASK = "panda_android_folder_encript_mask";
    public static final String WALLPAPER = "wallpaper";
    public static final String DRAWER = "drawer";
    public static final String THUMBNAIL = "thumbnail";
    public static final String PREVIEW0 = "preview0";
    public static final String PREVIEW1 = "preview1";
    public static final String PREVIEW2 = "preview2";
    public static final String LAUNCHER_LIGHT_TYPE = "launcher_light_type";
    public static final String HOME_LIGHT_HL = "home_light_hl";
    public static final String LAUNCHER_LIGHT_LINE = "launcher_light_line";
    public static String LAUNCHER_LIGHT_SELECTED = "launcher_light_point_selected";
    public static String LAUNCHER_LIGHT_NORMAL = "launcher_light_point_normal";
    public static final String ZNS_LOCK_BG = "lock_bg";
    public static final String PANDA_LOCK_MAIN_BACKGROUND = "panda_lock_main_background";
    public static final String[] iconKeys = new String[]{"com.google.android.apps.maps|com.google.android.maps.mapsactivity", "com.android.browser|com.android.browser.browseractivity", "com.android.contacts|com.android.contacts.dialtactsactivity", "com.android.contacts|com.android.contacts.dialtactscontactsentryactivity", "com.android.settings|com.android.settings.settings", "com.android.mms|com.android.mms.ui.conversationlist", "com.android.camera|com.android.camera.camera", "com.android.calendar|com.android.calendar.calendaractivity", "com.android.camera|com.android.camera.gallerypicker", "com.android.alarmclock|com.android.alarmclock.alarmclock", "com.android.email|com.android.email.activity.welcome", "com.android.calculator2|com.android.calculator2.calculator", "com.android.music|com.android.music.musicbrowseractivity", "com.android.camera|com.android.camera.videocamera", "com.android.voicedialer|com.android.voicedialer.voicedialeractivity", "com.google.android.voicesearch|com.google.android.voicesearch.recognitionactivity", "com.android.vending|com.android.vending.assetbrowseractivity", "com.android.weather|com.android.weather.weatheractivity", "com.taobao.taobao|com.taobao.tao.mainactivity2", "vstudio.android.camera360|vstudio.android.camera360.gphotomain", "com.hiapk.marketpho|com.hiapk.marketpho.marketmainframe", "com.tencent.mm|com.tencent.mm.ui.launcherui", "com.sina.weibo|com.sina.weibo.splashactivity", "com.tencent.qq|com.tencent.qq.splashactivity", "com.dragon.android.pandaspace|com.dragon.android.pandaspace.main.LoadingActivity", "com.facebook.katana|com.facebook.katana.loginactivity", "com.twitter.android|com.twitter.android.startactivity", "com.skype.rover|com.skype.rover.main"};
    public static final String[] themeThumbApps = new String[]{"com.android.contacts|com.android.contacts.dialtactsactivity", "com.android.contacts|com.android.contacts.dialtactscontactsentryactivity", "com.android.mms|com.android.mms.ui.conversationlist", "com.android.browser|com.android.browser.browseractivity", "com.android.alarmclock|com.android.alarmclock.alarmclock", "com.android.email|com.android.email.activity.welcome", "com.google.android.apps.maps|com.google.android.maps.mapsactivity", "com.android.vending|com.android.vending.assetbrowseractivity", "com.android.calculator2|com.android.calculator2.calculator", "com.android.camera|com.android.camera.camera", "com.android.camera|com.android.camera.gallerypicker", "com.android.camera|com.android.camera.videocamera", "com.android.music|com.android.music.musicbrowseractivity", "com.android.settings|com.android.settings.settings", "com.android.voicedialer|com.android.voicedialer.voicedialeractivity", "com.google.android.voicesearch|com.google.android.voicesearch.recognitionactivity"};
    public static HashMap<String, String> jpgKeysMap = new HashMap();
    public static final String[] jpgDrawableKeys = new String[]{"wallpaper", "drawer", "thumbnail", "preview0", "preview1", "preview2"};
    public static HashMap<String, String> largeIconMap = new HashMap();
    public static HashMap<String, String> drawableMap = new HashMap();
    public static HashMap<String, String> nodpiDrawableMap = new HashMap();
    private static BaseThemeData baseThemeData;

    public static final String THEME_SHOP_CLASS_NAME = "com.android.newline.launcher.themeshop";
    public static final String DIA_PKG_NAME = "com.android.newline.launcher.dial";
    public static final String SMS_PKG_NAME = "com.android.newline.launcher.sms";
    public static final String CONTACTS_PKG_NAME = "com.android.newline.launcher.contacts";
    public static final String CALENDAR_PKG_NAME = "com.android.newline.launcher.calendar";

    public BaseThemeData() {
    }

    public static void init() {
        String THEME_COMP_APP_SELECT_ACTIVITY = BaseConfig.PKG_NAME + "/" + THEME_APP_SELECT_ACTIVITY;
        INTENT_BROWSER = "http://www.google.com#Intent;action=android.intent.action.VIEW;launchFlags=0x10000000;component=" + THEME_COMP_APP_SELECT_ACTIVITY + ";B.is_always_select=false;end";
        INTENT_PHONE = "#Intent;action=android.intent.action.DIAL;launchFlags=0x10000000;component=" + THEME_COMP_APP_SELECT_ACTIVITY + ";B.is_always_select=false;end";
        INTENT_CONTACTS = "content://com.android.contacts/contacts#Intent;action=android.intent.action.VIEW;launchFlags=0x10000000;component=" + THEME_COMP_APP_SELECT_ACTIVITY + ";B.is_always_select=false;end";
        INTENT_MMS = "content://mms-sms/#Intent;action=android.intent.action.MAIN;launchFlags=0x10000000;component=" + THEME_COMP_APP_SELECT_ACTIVITY + ";B.is_always_select=false;end";
        ICON_APP_STORE = BaseConfig.PKG_NAME + "|com.nd.hilauncherdev.appstore.AppStoreSwitchActivity";
    }

    public static BaseThemeData getInstance() {
        if(null == baseThemeData) {
            baseThemeData = new BaseThemeData();
        }

        return baseThemeData;
    }

    public void buildThemeData() {
        int i;
        for(i = 0; i < jpgDrawableKeys.length; ++i) {
            jpgKeysMap.put(jpgDrawableKeys[i], "");
        }

        for(i = 0; i < iconKeys.length; ++i) {
            largeIconMap.put(iconKeys[i], "icons");
        }

        largeIconMap.put("panda_icon_foreground_mask", "icons");
        largeIconMap.put("panda_icon_cut_mask", "icons");
        largeIconMap.put("panda_icon_background_mask", "icons");
        largeIconMap.put(PANDA_FOLDER_BACKGROUND, "icons");
        largeIconMap.put("panda_folder_foreground_closed", "icons");
        largeIconMap.put("panda_folder_foreground_open", "icons");
        largeIconMap.put("panda_folder_encript_mask", "icons");
        largeIconMap.put("panda_android_folder_background", "icons");
        largeIconMap.put("panda_android_folder_encript_mask", "icons");
        drawableMap.put("thumbnail", "");
        drawableMap.put("preview0", "");
        drawableMap.put("preview1", "");
        drawableMap.put("preview2", "");
        drawableMap.put("home_light_hl".replace("home", "launcher"), "home");
        drawableMap.put("launcher_light_line", "home");
        drawableMap.put(LAUNCHER_LIGHT_SELECTED, "home");
        drawableMap.put(LAUNCHER_LIGHT_NORMAL, "home");
    }
}
