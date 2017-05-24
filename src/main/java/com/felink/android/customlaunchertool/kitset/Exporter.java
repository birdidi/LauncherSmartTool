package com.felink.android.customlaunchertool.kitset;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Xml;

import com.felink.android.customlaunchertool.kitset.common.FileUtil;
import com.felink.android.customlaunchertool.kitset.common.TelephoneUtil;
import com.felink.android.customlaunchertool.kitset.layout.CellBean;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月14日 11:52.</br>
 * @update: </br>
 */

public class Exporter {

    public static final String EXPORT_XML_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HWHome/custominfo/";
    public static final String EXPORT_XML_NAME = "info.xml";

    private static final int xCount = 4;
    private static final int yCount = 5;

    private static final String WEATHCER_BG_COLOR = "#7f000000";
    private static final String WEATHCER_BG_STYLE = "";

    /**
     * 2017.03.16 鼎开定制版要求，systemapp新增不可删除时间，单位（分钟）
     * 当expire <= 0 时，结果字符串兼容以前的格式：packageName;packageName;...;
     * 当expire >0 时， 结果字符串格式：packageName|expire;packageName|expire;...;
     * 以上两种格式可以混编，解析处会兼容
     */
    private static final String SYSTEM_APP_PACKAGES = "";

    /**
     * packagename:appname:appicon::action:action:...##
     */
    private static final String SWITCH_APP_CONTENT_INFO = "";

    /**
     * packagename::action:action:...0##
     */
    private static final String SWITCH_APP_ACTION_INFO = "";

    /**
     *
     */
    private static final String HIDE_APP_NAMES = "";

    /**
     * packagename:appicon:appname;packagename:appicon:appname;...
     */
    private static final String REPLACE_APP_LIST = "";

    private static final String CALENDAR_FONT_COLOR = "";

    private Context mContext;
    private ExportHelper mHelper;

    public Exporter(Context context) {
        this.mContext = context;
        mHelper = new ExportHelper(context);
    }

    public synchronized void exportLayout() {
        if (!mHelper.isInitialized()) {
            mHelper.init();
            initDefIcon();
        }
        FileOutputStream fileos = null;
        try {
            File folder = new File(EXPORT_XML_PATH);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File newXmlFile = new File(EXPORT_XML_PATH + EXPORT_XML_NAME);
            if (newXmlFile.exists()) {
                newXmlFile.delete();
            } else {
                newXmlFile.createNewFile();
            }

            fileos = new FileOutputStream(newXmlFile);
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fileos, "UTF-8");
            serializer.startDocument("UTF-8", null);
            startTagAndChangeLine(serializer, "custom");
//            final ContentResolver cr = mContext.getContentResolver();
            //1. 添加桌面和dock栏上的图标
            genAppInfo(serializer);

            genShortcutInfo(serializer);

            //2. 添加桌面和dock栏上的文件夹
            genFolderInfo(serializer);

            //3.生成隐藏app
            genHideApps(serializer);

            //4.生成替换app图标
            if (ConfigSettings.isAppliedOriginal) {
                genReplaceApps(serializer);
            }

            //5.生成系统app无法卸载
            genSystemApps(serializer);

            //6.生成轮流打开应用的app
            genSwitchApps(serializer);

            genAlterableApps(serializer);

            //7.生成小部件
            genWidgets(serializer);

            //8.其它
            genOtherConf(serializer);

            endTagAndChangeLine(serializer, "custom");
            serializer.endDocument();
            serializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileos != null) {
                try {
                    fileos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void genAppInfo(XmlSerializer serializer) {
        List<CellBean> list = mHelper.queryByType(CellBean.TYPE_APP | CellBean.TYPE_SHORTCUT | CellBean.TYPE_HI_APP);
        if (list != null && !list.isEmpty()) {
            try {
                startTagAndChangeLine(serializer, "recommend_apps");
                for (int i = 0, len = list.size(); i < len; i++) {
                    CellBean bean = list.get(i);
                    if (bean.container > 1) {
                        continue;
                    }
                    if (TextUtils.isEmpty(bean.intentUri)) {
                        continue;
                    }
                    if (TextUtils.isEmpty(bean.packageName)) {
                        continue;
                    }
                    if (i == 0) {
                        startTagAndChangeLine(serializer, "app");
                    } else {
                        startTag(serializer, "app");
                    }

                    startTagAndChangeLine(serializer, "package_name");
                    if (bean.itemType == CellBean.TYPE_HI_APP) {
                        serializer.text(BaseThemeData.THEME_SHOP_CLASS_NAME);
                    } else {
                        serializer.text(bean.packageName + (TextUtils.isEmpty(bean.className) ? "" : "/" + bean.className));
                    }
                    endTagAndChangeLine(serializer, "package_name");
                    startTag(serializer, "container");
                    serializer.text("" + bean.container);
                    endTagAndChangeLine(serializer, "container");
                    startTag(serializer, "screen");
                    serializer.text("" + bean.screen);
                    endTagAndChangeLine(serializer, "screen");
                    startTag(serializer, "x");
                    serializer.text("" + bean.x);
                    endTagAndChangeLine(serializer, "x");
                    if (bean.container == 0) {
                        startTag(serializer, "y");
                        serializer.text("" + bean.y);
                        endTagAndChangeLine(serializer, "y");
                    }
                    endTagAndChangeLine(serializer, "app");
                }
                endTagAndChangeLine(serializer, "recommend_apps");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void genShortcutInfo(XmlSerializer serializer) {
        List<CellBean> list = mHelper.queryByType(CellBean.TYPE_SHORTCUT);
        if (list != null && !list.isEmpty()) {
            try {
                startTagAndChangeLine(serializer, "shortcut_apps");
                boolean hasAdded = false;
                for (int i = 0, len = list.size(); i < len; i++) {
                    CellBean bean = list.get(i);
                    if (TextUtils.isEmpty(bean.intentUri)) {
                        continue;
                    }

                    if (mHelper.isDefaultShortcut(bean.intentUri)) {
                        continue;
                    }

                    if (TextUtils.isEmpty(bean.appName) || TextUtils.isEmpty(bean.appIcon)) {
                        continue;
                    }

                    if (!hasAdded) {
                        startTagAndChangeLine(serializer, "app");
                    } else {
                        startTag(serializer, "app");
                    }
                    hasAdded = true;
                    startTag(serializer, "app_icon");
                    serializer.text(bean.appIcon);
                    endTagAndChangeLine(serializer, "app_icon");

                    startTag(serializer, "app_name");
                    serializer.text(bean.appName);
                    endTagAndChangeLine(serializer, "app_name");

                    startTag(serializer, "container");
                    serializer.text("" + bean.container);
                    endTagAndChangeLine(serializer, "container");

                    startTag(serializer, "screen");
                    serializer.text("" + bean.screen);
                    endTagAndChangeLine(serializer, "screen");

                    startTag(serializer, "x");
                    serializer.text("" + bean.x);
                    endTagAndChangeLine(serializer, "x");

                    startTag(serializer, "action");
                    serializer.text(bean.intentUri);
                    endTagAndChangeLine(serializer, "action");

                    if (bean.container == 0) {
                        startTag(serializer, "y");
                        serializer.text("" + bean.y);
                        endTagAndChangeLine(serializer, "y");
                    }
                    endTagAndChangeLine(serializer, "app");
                }
                endTagAndChangeLine(serializer, "shortcut_apps");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void genWidgets(XmlSerializer serializer) {
//        Cursor c = null;
//        try {
//            c = cr.query(LauncherSettings.Favorites.getContentUri(), null,
//                    LauncherSettings.Favorites.APPWIDGET_ID + ">0", null, LauncherSettings.Favorites.SCREEN);
//            if (c.getCount() <= 0)
//                return;
//            final int screenIndex = c.getColumnIndexOrThrow(LauncherSettings.Favorites.SCREEN);
//            final int cellXIndex = c.getColumnIndexOrThrow(LauncherSettings.Favorites.CELLX);
//            final int cellYIndex = c.getColumnIndexOrThrow(LauncherSettings.Favorites.CELLY);
//            final int widgetIdIndex = c.getColumnIndexOrThrow(LauncherSettings.Favorites.APPWIDGET_ID);
//            startTag(serializer, "recommend_widgets");
//            int i = 0;
//            while (c.moveToNext()) {
//                int screen = c.getInt(screenIndex);
//                int cellX = c.getInt(cellXIndex);
//                int cellY = c.getInt(cellYIndex);
//                int widgetId = c.getInt(widgetIdIndex);
//                String widgetProviderKey = "";
//                AppWidgetManager widgets = AppWidgetManager.getInstance(mContext);
//                AppWidgetProviderInfo appWidgetProviderInfo = widgets.getAppWidgetInfo(widgetId);
//                widgetProviderKey = appWidgetProviderInfo.provider.getPackageName() + "/" + appWidgetProviderInfo.provider.getClassName();
//                if (TextUtils.isEmpty(widgetProviderKey))
//                    continue;
//                if (i == 0) {
//                    startTagAndChangeLine(serializer, "widget");
//                } else {
//                    startTag(serializer, "widget");
//                }
//                i++;
//                startTagAndChangeLine(serializer, "package_name");
//                serializer.text(widgetProviderKey);
//                endTagAndChangeLine(serializer, "package_name");
//                startTag(serializer, "screen");
//                serializer.text("" + screen);
//                endTagAndChangeLine(serializer, "screen");
//                startTag(serializer, "x");
//                serializer.text("" + cellX);
//                endTagAndChangeLine(serializer, "x");
//                startTag(serializer, "y");
//                serializer.text("" + cellY);
//                endTagAndChangeLine(serializer, "y");
//                endTagAndChangeLine(serializer, "widget");
//            }
//            endTagAndChangeLine(serializer, "recommend_widgets");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (c != null)
//                c.close();
//        }
    }

    private void genOtherConf(XmlSerializer serializer) throws IOException {
        startTag(serializer, "layout");
        serializer.text(xCount + "x" + yCount);
        endTagAndChangeLine(serializer, "layout");
        startTag(serializer, "navigation_screen");
        serializer.text("" + ConfigSettings.isOpenNavi);
        endTagAndChangeLine(serializer, "navigation_screen");
        startTag(serializer, "baidu_channel_id");
        serializer.text(ConfigSettings.baiduChannel);
        endTagAndChangeLine(serializer, "baidu_channel_id");
        startTag(serializer, "browser_url");
        serializer.text(ConfigSettings.baiduHomeUrl);
        endTagAndChangeLine(serializer, "browser_url");
        startTag(serializer, "baidu_search_widget");
        serializer.text("" + ConfigSettings.isAppliedSearchWidget);//待完成
        endTagAndChangeLine(serializer, "baidu_search_widget");
        startTag(serializer, "weather_widget_bg_color");
        serializer.text(ConfigSettings.isWeatcherWidgetBGTransparent ? "#00000000" : WEATHCER_BG_COLOR);
        endTagAndChangeLine(serializer, "weather_widget_bg_color");
        startTag(serializer, "weather_widget_style");
        serializer.text(WEATHCER_BG_STYLE);
        endTagAndChangeLine(serializer, "weather_widget_style");

        startTag(serializer, "weather_widget_bg_transparent");
        serializer.text(ConfigSettings.isWeatcherWidgetBGTransparent + "");
        endTagAndChangeLine(serializer, "weather_widget_bg_transparent");

    }

    private void genSystemApps(XmlSerializer serializer) throws IOException {
        String systemAppsStr = SYSTEM_APP_PACKAGES;
        if (!TextUtils.isEmpty(systemAppsStr)) {
            startTag(serializer, "sys_apps");
            String[] sysAppsArray = systemAppsStr.split(";");
            int i = 0;
            for (String compName : sysAppsArray) {
                if (i == 0) {
                    startTagAndChangeLine(serializer, "package_name");
                } else {
                    startTag(serializer, "package_name");
                }
                i++;
                serializer.text(compName);
                endTagAndChangeLine(serializer, "package_name");
            }
            endTagAndChangeLine(serializer, "sys_apps");
        }
    }

    private void genSwitchApps(XmlSerializer serializer) throws IOException {
        String swtichContentStr = SWITCH_APP_CONTENT_INFO;
        if (!TextUtils.isEmpty(swtichContentStr)) {
            startTag(serializer, "switch_apps");
            String[] swtichContentAppsArray = swtichContentStr.split("##");
            int i = 0;
            for (String swtichContent : swtichContentAppsArray) {
                if (TextUtils.isEmpty(swtichContent))
                    continue;
                if (i == 0) {
                    startTagAndChangeLine(serializer, "app");
                } else {
                    startTag(serializer, "app");
                }
                i++;

                String[] swtichContentArray = swtichContent.split("::");
                String[] swtichContentValueArray = swtichContentArray[0].split(":");
                startTagAndChangeLine(serializer, "package_name");
                serializer.text(swtichContentValueArray[0]);
                endTagAndChangeLine(serializer, "package_name");

                if (!TextUtils.isEmpty(swtichContentValueArray[1])) {
                    startTag(serializer, "app_name");
                    serializer.text(swtichContentValueArray[1]);
                    endTagAndChangeLine(serializer, "app_name");
                }

                if (!TextUtils.isEmpty(swtichContentValueArray[2])) {
                    startTag(serializer, "app_icon");
                    serializer.text(swtichContentValueArray[2].substring(swtichContentValueArray[2].lastIndexOf("/") + 1));
                    endTagAndChangeLine(serializer, "app_icon");
                }

                startTag(serializer, "switch_actions");
                String[] swtichContentActionArray = swtichContentArray[1].split(":");
                int j = 0;
                for (String swtichAction : swtichContentActionArray) {
                    if (TextUtils.isEmpty(swtichAction))
                        continue;
                    if (j == 0) {
                        startTagAndChangeLine(serializer, "action");
                    } else {
                        startTag(serializer, "action");
                    }
                    j++;
                    serializer.text(swtichAction);
                    endTagAndChangeLine(serializer, "action");
                }
                endTagAndChangeLine(serializer, "switch_actions");
                endTagAndChangeLine(serializer, "app");
            }
            endTagAndChangeLine(serializer, "switch_apps");
        }
    }

    private void genAlterableApps(XmlSerializer serializer) throws IOException {
        List<CellBean> list = mHelper.queryByCate(Cate.ALTER);
        if (list != null && !list.isEmpty()) {
            startTag(serializer, "alterable_apps");
            for (int i = 0, len = list.size(); i < len; i++) {
                CellBean bean = list.get(i);
                if (TextUtils.isEmpty(bean.packageName) || bean.alterApps == null || bean.alterApps.isEmpty()) {
                    continue;
                }
                if (i == 0) {
                    startTagAndChangeLine(serializer, "app");
                } else {
                    startTag(serializer, "app");
                }

                startTagAndChangeLine(serializer, "package_name");
                serializer.text(bean.packageName);
                endTagAndChangeLine(serializer, "package_name");

                List<CellBean> alterlist = bean.alterApps;

                startTag(serializer, "list");
                for (int j = 0, size = alterlist.size(); j < size; j++) {
                    CellBean alter = alterlist.get(j);
                    //不替换系统主题应用目标时
                    if (alter.itemType == CellBean.TYPE_HI_APP && alter.className.contains("manage.shop.ThemeShopMainActivity")
                            && !ConfigSettings.isReplaceTheme) {
                        continue;
                    }
                    if (TextUtils.isEmpty(alter.packageName)) {
                        continue;
                    }
                    if (j == 0) {
                        startTagAndChangeLine(serializer, "package_name");
                    } else {
                        startTag(serializer, "package_name");
                    }

                    if (TextUtils.isEmpty(alter.className)) {
                        serializer.text(alter.packageName);
                    } else {
                        serializer.text(alter.packageName + "/" + alter.className);
                    }
                    endTagAndChangeLine(serializer, "package_name");
                }
                endTagAndChangeLine(serializer, "list");
                endTagAndChangeLine(serializer, "app");
            }
            endTagAndChangeLine(serializer, "alterable_apps");
        }
    }

    private void genHideApps(XmlSerializer serializer) throws IOException {
//        String hideAppsStr = HIDE_APP_NAMES;
        List<CellBean> list = mHelper.queryByCate(Cate.HINDDEN);

        startTag(serializer, "hide_apps");
        if (list != null && !list.isEmpty()) {
            for (int i = 0, len = list.size(); i < len; i++) {
                CellBean bean = list.get(i);
                try {
                    String pkg = getCompString(bean.itemType, bean.iconType, bean.intentUri);
                    if (TextUtils.isEmpty(pkg)) {
                        continue;
                    }
                    if (i == 0) {
                        startTagAndChangeLine(serializer, "package_name");
                    } else {
                        startTag(serializer, "package_name");
                    }
                    serializer.text(pkg);
                    endTagAndChangeLine(serializer, "package_name");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = 0, len = CellBean.sDefaultShortcutVisibility.length; i < len; i++) {
            if (CellBean.sDefaultShortcutVisibility[i] == 0) {
                String pkg = null;
                switch (i) {
                    case 0://拔号
                        pkg = BaseThemeData.DIA_PKG_NAME;
                        break;
                    case 1://联系人
                        pkg = BaseThemeData.CONTACTS_PKG_NAME;
                        break;
                    case 2://信息
                        pkg = BaseThemeData.SMS_PKG_NAME;
                        break;
                    case 3://浏览器
                        break;
                }
                if (TextUtils.isEmpty(pkg)) {
                    continue;
                }
                startTag(serializer, "package_name");
                serializer.text(pkg);
                endTagAndChangeLine(serializer, "package_name");
            }
        }

        endTagAndChangeLine(serializer, "hide_apps");

    }

    private void genFolderInfo(XmlSerializer serializer) {
        try {
            List<CellBean> list = mHelper.queryByType(CellBean.TYPE_FOLDER);
            if (list != null && !list.isEmpty()) {
                startTag(serializer, "recommend_folders");
                for (int i = 0, len = list.size(); i < len; i++) {
                    CellBean bean = list.get(i);
                    if (i == 0) {
                        startTagAndChangeLine(serializer, "folder");
                    } else {
                        startTag(serializer, "folder");
                    }

                    String title = bean.appName;
                    startTagAndChangeLine(serializer, "apps");

                    if (bean.isCollectedAll) {
                        bean.appList = mHelper.queryRemainBeans();
                    }

                    for (int j = 0, size = bean.appList.size(); j < size; j++) {
                        CellBean app = bean.appList.get(j);
                        if (TextUtils.isEmpty(app.packageName))
                            continue;
                        if (j == 0) {
                            startTagAndChangeLine(serializer, "package_name");
                        } else {
                            startTag(serializer, "package_name");
                        }
                        serializer.text(app.packageName + (TextUtils.isEmpty(app.className) ? "" : "/" + app.className));

                        endTagAndChangeLine(serializer, "package_name");
                    }
                    endTagAndChangeLine(serializer, "apps");
                    startTag(serializer, "container");
                    serializer.text("" + bean.container);
                    endTagAndChangeLine(serializer, "container");
                    startTag(serializer, "screen");
                    serializer.text("" + bean.screen);
                    endTagAndChangeLine(serializer, "screen");
                    startTag(serializer, "x");
                    serializer.text("" + bean.x);
                    endTagAndChangeLine(serializer, "x");
                    if (bean.container == 0) {
                        startTag(serializer, "y");
                        serializer.text("" + bean.y);
                        endTagAndChangeLine(serializer, "y");
                    }
                    startTag(serializer, "folder_name");
                    serializer.text(title);
                    endTagAndChangeLine(serializer, "folder_name");
                    endTagAndChangeLine(serializer, "folder");
                }
                endTagAndChangeLine(serializer, "recommend_folders");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTag(XmlSerializer serializer, String tag) throws IOException {
//        String enter = System.getProperty("line.separator");
//        serializer.text(enter);
        serializer.startTag(null, tag);
    }

    private void startTagAndChangeLine(XmlSerializer serializer, String tag) throws IOException {
        String enter = System.getProperty("line.separator");
        serializer.text(enter);
        serializer.startTag(null, tag);
    }

    private void endTagAndChangeLine(XmlSerializer serializer, String tag) throws IOException {
        serializer.endTag(null, tag);

        String enter = System.getProperty("line.separator");
        serializer.text(enter);
    }


    /*@Nullable*/
    private String getCompString(int itemType, String iconType, String intentStr) {
        String pkgStr = null;
        try {
            if (itemType == CellBean.TYPE_HI_APP) {
                if (iconType.equals(BaseThemeData.ICON_THEME)) {
                    pkgStr = BaseThemeData.THEME_SHOP_CLASS_NAME;
                }
            } else if (itemType == CellBean.TYPE_SHORTCUT) {
                if (iconType.equals(BaseThemeData.ICON_PHONE)) {
                    pkgStr = BaseThemeData.DIA_PKG_NAME;
                } else if (iconType.equals(BaseThemeData.ICON_MMS)) {
                    pkgStr = BaseThemeData.SMS_PKG_NAME;
                } else if (iconType.equals(BaseThemeData.ICON_CONTACTS)) {
                    pkgStr = BaseThemeData.CONTACTS_PKG_NAME;
                }
            } else if (itemType == CellBean.TYPE_APP) {
                Intent intent = Intent.parseUri(intentStr, 0);
                pkgStr = intent.getComponent().getPackageName() + "/" + intent.getComponent().getClassName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pkgStr;
    }

    protected void genReplaceApps(XmlSerializer serializer) {
        List<CellBean> list = mHelper.queryByCate(Cate.REPLACE);
        if (list != null && !list.isEmpty()) {
            PackageManager pm = mContext.getPackageManager();
            try {
                startTag(serializer, "replace_apps");
                for (int i = 0, len = list.size(); i < len; i++) {
                    try {
                        CellBean bean = list.get(i);

                        if (bean.itemType == CellBean.TYPE_SHORTCUT) {
                            if (TextUtils.isEmpty(bean.intentUri)) {
                                continue;
                            }
                        } else if (TextUtils.isEmpty(bean.packageName)) {
                            continue;
                        }

                        int itemType = bean.itemType;
                        String title = bean.appName;
                        String intentStr = bean.intentUri;
                        String pkgStr = getCompString(itemType, bean.iconType, intentStr);
                        if (TextUtils.isEmpty(pkgStr)) {
                            continue;
                        }

                        String iconName = "";
                        if (FileUtil.isFileExits(EXPORT_XML_PATH + bean.appIcon)) {
                            iconName = bean.appIcon;
                        } else if (itemType == CellBean.TYPE_SHORTCUT) {
                            if (BaseThemeData.CONTACTS_PKG_NAME.equals(pkgStr) || BaseThemeData.SMS_PKG_NAME.equals(pkgStr) || BaseThemeData.DIA_PKG_NAME.equals(pkgStr)) {//联系人
                                iconName = mHelper.genIcon(EXPORT_XML_PATH, bean.actualPkg, bean.actualClazz);
                            }
                        } else if (itemType == CellBean.TYPE_HI_APP) {
                            if (BaseThemeData.THEME_SHOP_CLASS_NAME.equals(pkgStr)) {
                                if (!TextUtils.isEmpty(bean.actualPkg)) {
                                    iconName = mHelper.genIcon(EXPORT_XML_PATH, bean.actualPkg, bean.actualClazz);
                                }
                            }
                        } else {
                            try {
                                Intent intent = Intent.parseUri(bean.intentUri, 0);
                                if (null != mHelper.querySystemApp(intent, pm)) {
                                    iconName = mHelper.genIcon(EXPORT_XML_PATH, bean.packageName, bean.className);
                                }
                            } catch (URISyntaxException e) {
                            }
                        }

                        if (TextUtils.isEmpty(iconName)) {
                            continue;
                        }

                        addReplaceAppTag(serializer, i, pkgStr, pkgStr, null, title, iconName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                endTagAndChangeLine(serializer, "replace_apps");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void addReplaceAppTag(XmlSerializer serializer, int index, String srcPkg, String pkgName, String clazzName, String appName, String iconName) {
        try {
            if (index == 0) {
                startTagAndChangeLine(serializer, "app");
            } else {
                startTag(serializer, "app");
            }

            startTagAndChangeLine(serializer, "package_name");
            serializer.text(pkgName + (TextUtils.isEmpty(clazzName) ? "" : "/" + clazzName));
            endTagAndChangeLine(serializer, "package_name");

            if (!TextUtils.isEmpty(appName)) {
                startTag(serializer, "app_name");
                serializer.text(appName);
                endTagAndChangeLine(serializer, "app_name");
            }

            startTag(serializer, "app_icon");
            serializer.text(iconName);
            endTagAndChangeLine(serializer, "app_icon");

//            if(srcPkg.equals(LauncherProviderHelper.CALENDAR_PKG_NAME) &&
//                    !TextUtils.isEmpty(SettingsPreference.getInstance().getCalendarIconFontColor())){
//                startTag(serializer, "app_icon_font_color");
//                serializer.text(SettingsPreference.getInstance().getCalendarIconFontColor());
//                endTagAndChangeLine(serializer, "app_icon_font_color");
//            }
            endTagAndChangeLine(serializer, "app");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDefIcon() {
//        if (TelephoneUtil.isLetvMoble()) {
        FileUtil.copyAssetFile(mContext.getAssets(), "letvlive.png", EXPORT_XML_PATH + "letvlive.png");
        if (TelephoneUtil.isHuaweiPhone()) {
            FileUtil.copyAssetFile(mContext.getAssets(), "huawei_reader.png", EXPORT_XML_PATH + "huawei_reader.png");
        }
//        }
    }
}
