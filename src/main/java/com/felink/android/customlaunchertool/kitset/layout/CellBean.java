package com.felink.android.customlaunchertool.kitset.layout;

import android.content.ComponentName;

import com.felink.android.customlaunchertool.kitset.BaseThemeData;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月13日 20:48.</br>
 * @update: </br>
 */

public class CellBean {


    public static final int TYPE_APP = 0x0001;
    public static final int TYPE_SHORTCUT = 0x0001 << 1;
    public static final int TYPE_FOLDER = 0x0001 << 2;
    public static final int TYPE_HI_APP = 0x0001 << 3;

    public static final String[] CANDIDATENAME_TIMER = new String[]{"^时钟$", "^闹钟$", "^时钟闹钟$", "[\\S\\s]*时钟[\\S\\s]*", "[\\S\\s]*闹钟[\\S\\s]*"};
    public static final String[] CANDIDATENAME_BROWSER = new String[]{"^浏览器$", "^互联网$", "[\\S\\s]*浏览器[\\S\\s]*"};
    public static final String[] CANDIDATENAME_CALULATOR = new String[]{"^计算器$", "[\\s\\S]*计算器[\\S\\s]*"};
    public static final String[] CANDIDATENAME_CALENDAR = new String[]{"^日历$", "[\\S\\s]*日历[\\S\\s]*", "[\\S\\s]*年历[\\S\\s]*"};
    public static final String[] CANDIDATENAME_CAMERA = new String[]{"^相机$", "[\\s\\S]*相机[\\s\\S]*"};
    public static final String[] CANDIDATENAME_GALLERY = new String[]{"^图库$", "^相册$", "^照片$", "[\\S\\s]*图库[\\S\\s]*", "[\\S\\s]*像册[\\S\\s]*", "[\\S\\s]*照片[\\S\\s]*"};
    public static final String[] CANDIDATENAME_VIDEO = new String[]{"^视频$", "[\\S\\s]*视频[\\S\\s]*"};
    public static final String[] CANDIDATENAME_DIA = new String[]{"^电话$", "^拔号$", "^拨号盘$", "[\\S\\s]*拨号[\\S\\s]*"};
    public static final String[] CANDIDATENAME_CONTACTS = new String[]{"^联系人$", "^通讯录$", "[\\S\\s]*通讯录[\\S\\s]*"};
    public static final String[] CANDIDATENAME_EMAIL = new String[]{"^电子邮件$", "^邮件$", "[\\S\\s]*邮件[\\S\\s]*"};
    public static final String[] CANDIDATENAME_SMS = new String[]{"^短信$", "^信息$", "[\\S\\s]*信息[\\S\\s]*"};
    public static final String[] CANDIDATENAME_MUSIC = new String[]{"^音乐$", "^Music$", "[\\S\\s]*音乐[\\S\\s]*", "[\\S\\s]*Music[\\S\\s]*"};
    public static final String[] CANDIDATENAME_SETTING = new String[]{"^设置$", "^设定$", "[\\S\\s]*设置[\\S\\s]*", "[\\S\\s]*设定[\\S\\s]*"};
    public static final String[] CANDIDATENAME_STORE = new String[]{"^应用商店$", "^应用商城$", "^应用中心$", "^软件商店$", "^软件市场$", "^软件中心$", "^应用市场$",
            "[\\s\\S]*应用市场[\\s\\S]*", "[\\s\\S]*软件商店[\\s\\S]*", "[\\s\\S]*软件市场[\\s\\S]*", "[\\s\\S]*软件中心[\\s\\S]*", "[\\s\\S]*应用中心[\\s\\S]*", "[\\s\\S]*应用商店[\\s\\S]*"};
    public static final String[] CANDIDATENAME_THEME = new String[]{"^主题$", "^主题商店$", "[\\S\\s]*主题[\\S\\s]*"};
    public static final String[] CANDIDATENAME_WEATHER = new String[]{"^天气$", "[\\S\\s]*天气[\\S\\s]*"};

    public String packageName;//应用包名
    public String className;//启动类名

    public String actualPkg;
    public String actualClazz;

    public String appName;//应用名称
    public String appEnName;//应用英文名
    public String appIcon;//图标名称
    public String downloadUrl;//下载地址

    public String iconType = null;

    public String apkPath;//应用安装包路径
    public int itemType = TYPE_APP;//图标类型,0表示应用，1表示快捷方式, 2表示文件夹。默认为图标
    public String intentUri;//快捷方式uri
    public int container = 2;//所在区域，0表示图标区，1表示dock栏 2 文件夹
    public int screen;//所在屏
    public int x;//所在屏幕的X坐标
    public int y;//所在屏幕的Y坐标

    public boolean isCollectedAll = false;//文件夹属性，是否将除未明显指定位置的应用均放于此

    public ArrayList<String> switchActions;//依次轮流打开的应用

    public List<CellBean> appList;//推荐文件夹推荐列表

    public CellBean alterTarget;
    public List<CellBean> alterApps;//可变目录应用列表

    public String[] candidateAppNames;//候选的应用匹配名称

    /**
     * 是否隐藏： 拨号 联系人 信息 浏览器
     */
    public static int[] sDefaultShortcutVisibility = {0, 0, 0, 0};

    public CellBean() {

    }

    public CellBean(String pkg, String clazz) {
        this.packageName = pkg;
        this.className = clazz;
    }

    public static CellBean build(ComponentName componentName) {
        return new Builder().setComponentName(componentName).build();
    }

    /**
     * 通用
     *
     * @param container
     * @param screen
     * @param x
     * @param y
     * @param type
     * @param name
     */
    public static CellBean build(int container, int screen, int x, int y, int type, String name) {
        return new Builder().setCoordiate(container, screen, x, y).setItemType(type).setTitle(name).build();
    }

    /**
     * 通用
     *
     * @param container
     * @param screen
     * @param x
     * @param y
     * @param type
     * @param name
     */
    public static CellBean build(int container, int screen, int x, int y, int type, String name, String[] candidate) {
        return new Builder()
                .setCoordiate(container, screen, x, y)
                .setItemType(type)
                .setTitle(name)
                .setCandidateNames(candidate)
                .build();
    }

    /**
     * 快捷
     *
     * @param container
     * @param screen
     * @param x
     * @param y
     * @param name
     * @param action
     */
    public static CellBean build(int container, int screen, int x, int y, String name, String action) {

        return new Builder()
                .setCoordiate(container, screen, x, y)
                .setTitle(name)
                .setItemType(TYPE_SHORTCUT)
                .setAction(action)
                .build();
    }

    /**
     * 快捷
     *
     * @param container
     * @param screen
     * @param x
     * @param y
     * @param name
     * @param icon
     * @param action
     */
    public static CellBean build(int container, int screen, int x, int y, String name, String icon, String action) {
        return new Builder()
                .setCoordiate(container, screen, x, y)
                .setTitle(name)
                .setIcon(icon)
                .setItemType(TYPE_SHORTCUT)
                .setAction(action)
                .build();
    }


    /**
     * 应用
     *
     * @param container
     * @param screen
     * @param x
     * @param y
     * @param type
     * @param name
     * @param pkg
     * @param clazz
     */
    public static CellBean build(int container, int screen, int x, int y, int type, String name, String pkg, String clazz) {

        return new Builder()
                .setCoordiate(container, screen, x, y)
                .setItemType(type)
                .setTitle(name)
                .setPkg(pkg)
                .setClass(clazz)
                .build();
    }

    public static CellBean build(int container, int screen, int x, int y, int type, String name, ComponentName componentName) {
        return new Builder()
                .setCoordiate(container, screen, x, y)
                .setItemType(type)
                .setTitle(name)
                .setComponentName(componentName)
                .build();
    }

    /**
     * 文件夹
     *
     * @param container
     * @param screen
     * @param x
     * @param y
     * @param name
     * @param folderInner
     */
    public static CellBean build(int container, int screen, int x, int y, String name, List<CellBean> folderInner) {
        return new Builder()
                .setCoordiate(container, screen, x, y)
                .setTitle(name)
                .setItemType(TYPE_FOLDER)
                .addFolderApps(folderInner)
                .build();
    }

    /**
     * 文件夹
     *
     * @param container
     * @param screen
     * @param x
     * @param y
     * @param name
     * @param folderInner
     */
    public static CellBean build(int container, int screen, int x, int y, String name, List<CellBean> folderInner, boolean isCollectAll) {
        return new Builder()
                .setCoordiate(container, screen, x, y)
                .setTitle(name)
                .addFolderApps(folderInner)
                .isCollected(isCollectAll)
                .setItemType(TYPE_FOLDER)
                .build();
    }

    public static CellBean build(int container, int screen, int x, int y, String name, int alterType) {

        return new Builder()
                .setCoordiate(container, screen, x, y)
                .setTitle(name)
                .setAlterAppType(alterType)
                .setItemType(TYPE_APP)
                .build();
    }

    public static CellBean build(int container, int screen, int x, int y, String name, String[] candidate, int alterType) {
        return new Builder()
                .setCoordiate(container, screen, x, y)
                .setTitle(name)
                .setCandidateNames(candidate)
                .setAlterAppType(alterType)
                .setItemType(TYPE_APP)
                .build();
    }

    public static CellBean build(int container, int screen, int x, int y, String name, String[] candidate, int alterType, String iconType) {
        return new Builder()
                .setCoordiate(container, screen, x, y)
                .setTitle(name)
                .setCandidateNames(candidate)
                .setAlterAppType(alterType)
                .setItemType(TYPE_APP)
                .setIconType(iconType)
                .build();
    }

    public static final class Builder {

        private String packageName;//应用包名
        private String className;//启动类名
        private String appName;//应用名称
        private String appIcon;//图标名称

        private String iconType = null;

        private int itemType = TYPE_APP;//图标类型,0表示应用，1表示快捷方式, 2表示文件夹。默认为图标
        private String intentUri;//快捷方式uri
        private int container = 2;//所在区域，0表示图标区，1表示dock栏 2 文件夹
        private int screen;//所在屏
        private int x;//所在屏幕的X坐标
        private int y;//所在屏幕的Y坐标

        private boolean isCollectedAll = false;//文件夹属性，是否将除未明显指定位置的应用均放于此

        private List<CellBean> appList;//推荐文件夹推荐列表

        private List<CellBean> alterApps;//可变目录应用列表

        private String[] candidateAppNames;//候选的应用匹配名称

        public Builder() {

        }

        public CellBean.Builder setPkg(String pkg) {
            this.packageName = pkg;
            return this;
        }

        public CellBean.Builder setClass(String clazz) {
            this.className = clazz;
            return this;
        }

        public CellBean.Builder setComponentName(ComponentName componentName) {
            this.packageName = componentName.getPackageName();
            this.className = componentName.getClassName();
            return this;
        }

        public CellBean.Builder setTitle(String title) {
            this.appName = title;
            return this;
        }

        public CellBean.Builder setIcon(String icon) {
            this.appIcon = icon;
            return this;
        }

        public CellBean.Builder setIconType(String iconType) {
            this.iconType = iconType;
            return this;
        }

        public CellBean.Builder setItemType(int type) {
            this.itemType = type;

            if (type == TYPE_HI_APP) {
                this.iconType = BaseThemeData.ICON_THEME;
                this.candidateAppNames = RecommandAppPool.getFuzzyRegexs("主题");
            }
            return this;
        }

        public CellBean.Builder setAction(String action) {
            if (BaseThemeData.INTENT_CONTACTS.equals(action)) {
                this.packageName = BaseThemeData.CONTACTS_PKG_NAME;
                sDefaultShortcutVisibility[1] = 1;
            } else if (BaseThemeData.INTENT_MMS.equals(action)) {
                this.packageName = BaseThemeData.SMS_PKG_NAME;
                sDefaultShortcutVisibility[2] = 1;
            } else if (BaseThemeData.INTENT_PHONE.equals(action)) {
                this.packageName = BaseThemeData.DIA_PKG_NAME;
                sDefaultShortcutVisibility[0] = 1;
            } else if (BaseThemeData.INTENT_BROWSER.equals(action)) {
                sDefaultShortcutVisibility[3] = 1;
            }
            this.intentUri = action;
            return this;
        }

        public CellBean.Builder setCoordiate(int container, int screen, int x, int y) {
            this.container = container;
            this.screen = screen;
            this.x = x;
            this.y = y;
            return this;
        }

        public CellBean.Builder isCollected(boolean isCollectedRemain) {
            this.isCollectedAll = isCollectedRemain;
            return this;
        }

        public CellBean.Builder addFolderApp(CellBean bean) {
            if (this.appList == null) {
                this.appList = new ArrayList<CellBean>();
            }

            this.appList.add(bean);
            return this;
        }

        public CellBean.Builder addFolderApps(List<CellBean> list) {
            if (this.appList == null) {
                this.appList = new ArrayList<CellBean>();
            }

            this.appList.addAll(list);
            return this;
        }

        public CellBean.Builder addAlterApp(CellBean bean) {
            if (this.alterApps == null) {
                this.alterApps = new ArrayList<CellBean>();
            }
            this.alterApps.add(bean);
            return this;
        }

        public CellBean.Builder addAlterApps(List<CellBean> list) {
            if (this.alterApps == null) {
                this.alterApps = new ArrayList<CellBean>();
            }

            this.alterApps.addAll(list);
            return this;
        }

        public CellBean.Builder setAlterAppType(int type) {
            List<ComponentName> cns = RecommandAppPool.getAlterApps(type);
            if (cns != null && cns.size() > 0) {
                this.alterApps = new ArrayList<CellBean>();
                for (ComponentName cn : cns) {
                    CellBean bean = new CellBean(cn.getPackageName(), cn.getClassName());
                    if (cn == RecommandAppPool.LAUNCHER_THEME) {
                        bean.itemType = TYPE_HI_APP;
                    }
                    this.alterApps.add(bean);
                }
            }
            return this;
        }

        public CellBean.Builder addCandidateName(String name) {
            if (this.candidateAppNames == null) {
                this.candidateAppNames = new String[]{name};
            }
            return this;
        }

        public CellBean.Builder setCandidateNames(String[] names) {
            this.candidateAppNames = names;
            return this;
        }

        public CellBean build() {
            CellBean bean = new CellBean();
            bean.packageName = packageName;
            bean.className = className;
            bean.appName = appName;
            bean.appIcon = appIcon;
            bean.iconType = iconType;
            bean.itemType = itemType;
            bean.intentUri = intentUri;
            bean.isCollectedAll = isCollectedAll;
            bean.container = container;
            bean.screen = screen;
            bean.x = x;
            bean.y = y;
            bean.appList = appList;
            bean.alterApps = alterApps;
            bean.candidateAppNames = candidateAppNames;

            if (alterApps != null && !alterApps.isEmpty()) {
                for (int i = 0, len = alterApps.size(); i < len; i++) {
                    CellBean item = alterApps.get(i);
                    item.alterTarget = bean;
                }
            }

            return bean;
        }
    }
}
