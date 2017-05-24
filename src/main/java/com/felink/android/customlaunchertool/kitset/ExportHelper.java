package com.felink.android.customlaunchertool.kitset;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.felink.android.customlaunchertool.MainActivity;
import com.felink.android.customlaunchertool.config.Global;
import com.felink.android.customlaunchertool.kitset.common.AndroidPackageUtils;
import com.felink.android.customlaunchertool.kitset.common.BitmapUtils;
import com.felink.android.customlaunchertool.kitset.common.SystemUtil;
import com.felink.android.customlaunchertool.kitset.common.TelephoneUtil;
import com.felink.android.customlaunchertool.kitset.layout.CellBean;
import com.felink.android.customlaunchertool.kitset.layout.ExportLayoutCompat;
import com.felink.android.customlaunchertool.kitset.layout.RecommandAppPool;
import com.felink.android.customlaunchertool.model.Info;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月23日 18:28.</br>
 * @update: </br>
 */

public class ExportHelper {

    private Context mContext;

    //布局数据
    private List<CellBean> mPlainData = new ArrayList<CellBean>();
    //文件夹列表数据
    public List<CellBean> mFolderList = new ArrayList<CellBean>();
    //替补列表数据
    public List<CellBean> mAlterList = new ArrayList<CellBean>();
    //隐藏列表数据
    public List<CellBean> mHindden = new ArrayList<CellBean>();

    public List<CellBean> mAlter = new ArrayList<CellBean>();
    public List<CellBean> mFolder = new ArrayList<CellBean>();
    public List<CellBean> mReplace = new ArrayList<CellBean>();

    //总数据集 = 布局数据 + 文件夹列表数据 + 替补列表数据
    private List<CellBean> mData = new ArrayList<CellBean>();

    // 包名-> CellBean (白名单列表，已明确指定的APP)
    private HashMap<String, CellBean> mWhitDict = new HashMap<String, CellBean>();

    private HashMap<String, CellBean> mDefIconDict = new HashMap<String, CellBean>();

    private boolean hasInitialized = false;
    private boolean hasAddedTheme = false;

    public ExportHelper(Context context) {
        this.mContext = context;
    }

    public void init() {
        mPlainData = ExportLayoutCompat.layout();
        mData.addAll(mPlainData);
        for (CellBean bean : mPlainData) {

            if (bean.alterApps != null && !bean.alterApps.isEmpty()) {
                //华为机型的阅读图标特殊处理
                for (int i = 0, len = bean.alterApps.size(); i < len; i++) {
                    CellBean alter = bean.alterApps.get(i);
                    mAlterList.add(alter);
                    addToWhit(alter);
                }
                mAlter.add(bean);
            }

            if (bean.appList != null && !bean.appList.isEmpty()) {
                for (int i = 0, len = bean.appList.size(); i < len; i++) {
                    CellBean app = bean.appList.get(i);
                    mFolderList.add(app);
                    addToWhit(app);
                }
                mFolder.add(bean);
            }

            addToWhit(bean);
        }
        mData.addAll(mAlterList);
        mData.addAll(mFolderList);

        mWhitDict.clear();
        for (CellBean bean : mData) {
            enchance(bean);
            addToWhit(bean);
        }

        if (!hasAddedTheme) {
            //默认需要替换的
            ComponentName cn = queryMainActivityByName("主题", true, new String[]{});
            if (cn != null) {
                if (!inWhit(cn.getPackageName(), cn.getClassName())) {
                    CellBean bean = new CellBean.Builder()
                            .setTitle("主题")
                            .setItemType(CellBean.TYPE_APP)
                            .setAlterAppType(RecommandAppPool.TYPE_THEME)
                            .setComponentName(cn)
                            .build();
                    mAlterList.add(new CellBean.Builder()
                            .setComponentName(RecommandAppPool.LAUNCHER_THEME)
                            .setItemType(CellBean.TYPE_HI_APP)
                            .build()
                    );
                    mAlter.add(bean);
                    mData.add(bean);
                }
            }
        }

        CellBean readerBean = null;
        for (CellBean alter : mAlter) {
            if (alter.alterApps != null && !alter.alterApps.isEmpty()
                    && AndroidPackageUtils.isPkgInstalled(mContext, alter.packageName)) {
                if (TelephoneUtil.isHuaweiPhone() && alter.appName != null && alter.appName.contains("阅读") && RecommandAppPool.IYD.getPackageName().equals(alter.packageName)) {
                    readerBean = alter;
                    continue;
                }
                mHindden.addAll(alter.alterApps);
            } else if (TelephoneUtil.isHuaweiPhone() && alter.appName != null && alter.appName.contains("阅读")) {
                readerBean = alter;
            }
        }
        if (readerBean != null) {
            try {
                readerBean.alterApps = null;
                //系统未安装阅读APP-将爱阅读替换进去，并且不把爱阅读加到隐藏队列中
                readerBean.packageName = RecommandAppPool.IYD.getPackageName();
                readerBean.className = RecommandAppPool.IYD.getClassName();
                readerBean.appIcon = "huawei_reader.png";
                readerBean.intentUri = AndroidPackageUtils.getNewTaskIntent(new ComponentName(readerBean.packageName, readerBean.className)).toUri(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mReplace.add(readerBean);
        }

        hasInitialized = true;

        assembleDefIcon();
    }

    public boolean isInitialized() {
        return hasInitialized;
    }

    /**
     * 增强属性
     *
     * @param bean
     */
    private void enchance(CellBean bean) {

        switch (bean.itemType) {
            case CellBean.TYPE_APP:

                if (TextUtils.isEmpty(bean.packageName)) {
                    //模糊查找应用
                    ComponentName cn = queryMainActivityByName(bean);
                    if (cn != null) {
                        MainActivity.sb.append(bean.appName + " = [" + cn.getPackageName() + "/" + cn.getClassName() + "]\n");
                        bean.packageName = cn.getPackageName();
                        bean.className = cn.getClassName();
                    }
                }

                if (TextUtils.isEmpty(bean.intentUri)) {
                    if (!TextUtils.isEmpty(bean.packageName)) {
                        if (TextUtils.isEmpty(bean.className)) {
                            bean.intentUri = AndroidPackageUtils.getPackageMainIntent(mContext, bean.packageName).toUri(0);
                        } else {
                            bean.intentUri = AndroidPackageUtils.getNewTaskIntent(new ComponentName(bean.packageName, bean.className)).toUri(0);
                        }
                    }
                }

                break;
            case CellBean.TYPE_FOLDER:
                break;
            case CellBean.TYPE_SHORTCUT:

                if (BaseThemeData.CONTACTS_PKG_NAME.equals(bean.packageName) || BaseThemeData.SMS_PKG_NAME.equals(bean.packageName)
                        || BaseThemeData.DIA_PKG_NAME.equals(bean.packageName) || BaseThemeData.CALENDAR_PKG_NAME.equals(bean.packageName)) {//联系人
                    try {
                        Intent tmpIntent = Intent.parseUri(bean.intentUri, 0);
                        Intent newIntent = new Intent(tmpIntent.getAction(), tmpIntent.getData());
                        ResolveInfo info = querySystemApp(newIntent, mContext.getPackageManager());
                        ComponentName cn = queryMainActivityByName(bean.appName, true, bean.candidateAppNames);

                        if (cn != null) {
                            bean.actualPkg = cn.getPackageName();
                            bean.actualClazz = cn.getClassName();
                        } else if (info != null) {
                            bean.actualPkg = info.activityInfo.packageName;
                            bean.actualClazz = info.activityInfo.name;
                        }
                    } catch (Exception e) {
                    }

                    if (TextUtils.isEmpty(bean.intentUri)) {
                        if (BaseThemeData.CONTACTS_PKG_NAME.equals(bean.packageName)) {
                            bean.intentUri = BaseThemeData.INTENT_CONTACTS;
                        } else if (BaseThemeData.SMS_PKG_NAME.equals(bean.packageName)) {
                            bean.intentUri = BaseThemeData.INTENT_MMS;
                        } else if (BaseThemeData.DIA_PKG_NAME.equals(bean.packageName)) {
                            bean.intentUri = BaseThemeData.INTENT_PHONE;
                        }
                    }
                }

                break;
            case CellBean.TYPE_HI_APP:
                hasAddedTheme = true;
                ComponentName cn = queryMainActivityByName(bean.appName, true, bean.candidateAppNames);
                if (cn != null) {
                    bean.actualPkg = cn.getPackageName();
                    bean.actualClazz = cn.getClassName();
                    bean.intentUri = AndroidPackageUtils.getNewTaskIntent(cn).toUri(0);
                }

                break;
        }
    }

    private void assembleDefIcon() {
        DefIcon[] defs = DefIcon.values();
        for (int i = 0, len = defs.length; i < len; i++) {
            DefIcon icon = defs[i];
            CellBean bean = icon.query(new DefIcon.Verifier() {
                @Override
                public boolean verify(ActivityInfo info) {
                    return inWhit(info);
                }
            });
            if (bean != null) {
                mReplace.add(bean);
                mData.add(bean);
                if (bean.itemType == CellBean.TYPE_SHORTCUT) {
                    mDefIconDict.put(bean.actualPkg, bean);
                } else {
                    mDefIconDict.put(bean.packageName, bean);
                }
            }
        }

        List<CellBean> list = queryRemainBeans(true, false);
        for (CellBean bean : list) {
            if (!mDefIconDict.containsKey(bean.packageName)) {
                bean.intentUri = AndroidPackageUtils.getNewTaskIntent(new ComponentName(bean.packageName, bean.className)).toUri(0);
                mReplace.add(bean);
                mDefIconDict.put(bean.packageName, bean);
            }
        }


    }

    private void addToWhit(CellBean src) {
        String pkg = null;
        if (src.itemType == CellBean.TYPE_SHORTCUT) {
            pkg = src.actualPkg;
        } else {
            pkg = src.packageName;
        }

        if (!TextUtils.isEmpty(pkg)) {
            if (!mWhitDict.containsKey(pkg)) {
                mWhitDict.put(pkg, src);
            }
        }
    }

    public List<CellBean> queryByType(int type) {
        List<CellBean> res = new ArrayList<CellBean>();
        for (int i = 0, len = mPlainData.size(); i < len; i++) {
            CellBean bean = mPlainData.get(i);
            if ((bean.itemType & type) == bean.itemType) {
                res.add(bean);
            }
        }
        return res;
    }


    public List<CellBean> queryByCate(Cate cate) {
        if (cate == Cate.HINDDEN) {

            List<CellBean> hidden = new ArrayList<CellBean>();
//            hidden.addAll(mAlterList);
            hidden.addAll(mHindden);

            hidden.add(new CellBean.Builder()
                    .setPkg(Global.getApplicationContext().getPackageName())
                    .setClass(MainActivity.class.getName())
                    .setAction(AndroidPackageUtils.getNewTaskIntent(new ComponentName(Global.getApplicationContext().getPackageName(), MainActivity.class.getName())).toUri(0))
                    .build());
            return hidden;

        } else if (cate == Cate.REPLACE) {
            return mReplace;
        } else if (cate == Cate.ALTER) {
            return mAlter;
        } else if (cate == Cate.FOLDERLIST) {
            return mFolder;
        }

        return null;
    }

    /**
     * 通过名称查找APP
     *
     * @param appName
     * @param isSystem
     * @param candidate
     * @return
     */
    public ComponentName queryMainActivityByName(String appName, boolean isSystem, String... candidate) {
        if (TextUtils.isEmpty(appName)) {
            return null;
        }
        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> result = queryMainActivities();
        List<Info> matched = new ArrayList<Info>();
        int priority = 0;
        if (result != null && !result.isEmpty()) {
            for (ResolveInfo info : result) {
                ActivityInfo ainfo = info.activityInfo;
                if (ainfo != null) {
                    if (isSystem && !SystemUtil.isSystemApplication(ainfo.applicationInfo)) {
                        continue;
                    }
                    if (inWhit(ainfo)) {
                        continue;
                    }
                    CharSequence name = ainfo.loadLabel(pm);
                    if (appName.equals(name.toString())) {
                        matched.add(new Info(ainfo, priority));
                        priority++;
                    }
                }
            }

            if (candidate != null && candidate.length > 0) {
                for (int i = 0, len = candidate.length; i < len; i++) {
                    Pattern pattern = Pattern.compile(candidate[i]);
                    for (ResolveInfo info : result) {
                        ActivityInfo ainfo = info.activityInfo;
                        if (ainfo != null) {
                            if (isSystem && !SystemUtil.isSystemApplication(ainfo.applicationInfo)) {
                                continue;
                            }
                            if (inWhit(ainfo)) {
                                continue;
                            }
                            CharSequence name = ainfo.loadLabel(pm);
                            Matcher matcher = pattern.matcher(name.toString());
                            if (matcher.find()) {
                                matched.add(new Info(ainfo, priority));
                                priority++;
                            }
                        }
                    }
                }
            }
        }

        if (!matched.isEmpty()) {
            if (matched.size() > 1) {
                Comparator<Info> comparator = new Comparator<Info>() {
                    @Override
                    public int compare(Info o1, Info o2) {
                        boolean isSystem1 = SystemUtil.isSystemApplication(o1.activityInfo.applicationInfo);
                        boolean isSystem2 = SystemUtil.isSystemApplication(o2.activityInfo.applicationInfo);
                        if (!(isSystem1 ^ isSystem2)) {
                            if (o1.priority > o2.priority) {
                                return 1;
                            }
                            if (o1.priority < o2.priority) {
                                return -1;
                            }
                            return 0;
                        }
                        if (!isSystem1 && isSystem2) {
                            return 1;
                        }
                        if (isSystem1 && !isSystem2) {
                            return -1;
                        }
                        return 0;
                    }
                };
                Collections.sort(matched, comparator);
            }

            Info info = matched.get(0);
            return new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
        }

        return null;
    }

    public ComponentName queryMainActivityByName(CellBean bean) {
        return queryMainActivityByName(bean.appName, false, bean.candidateAppNames);
    }

    /**
     * 查找系统APP
     *
     * @param intent
     * @param pm
     * @return
     */
    public ResolveInfo querySystemApp(Intent intent, PackageManager pm) {
        List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
        if (list != null && !list.isEmpty()) {
            for (ResolveInfo info : list) {
                try {
                    if (SystemUtil.isSystemApplication(info.activityInfo.applicationInfo)) {
                        return info;
                    }
                } catch (Exception e) {

                }
            }
        }
        return null;
    }

    /**
     * 搜索剩余App
     *
     * @return
     */
    public List<CellBean> queryRemainBeans() {
        List<ResolveInfo> result = queryMainActivities();
        List<CellBean> remainList = new ArrayList<CellBean>();
        if (result != null && !result.isEmpty()) {
            for (ResolveInfo info : result) {
                if (!inWhit(info.activityInfo)) {
                    try {
                        remainList.add(new CellBean(info.activityInfo.packageName, info.activityInfo.name));
                    } catch (Exception e) {

                    }
                }
            }
        }
        return remainList;
    }

    /**
     * 搜索剩余App
     *
     * @return
     */
    public List<CellBean> queryRemainBeans(boolean isSystem, boolean inWhit) {
        List<ResolveInfo> result = queryMainActivities();
        List<CellBean> remainList = new ArrayList<CellBean>();
        if (result != null && !result.isEmpty()) {
            for (ResolveInfo info : result) {
                if (isSystem) {
                    if (!SystemUtil.isSystemApplication(info.activityInfo.applicationInfo)) {
                        continue;
                    }
                }

                if (inWhit) {
                    if (!inWhit(info.activityInfo)) {
                        try {
                            remainList.add(new CellBean(info.activityInfo.packageName, info.activityInfo.name));
                        } catch (Exception e) {

                        }
                    }
                } else {
                    try {
                        remainList.add(new CellBean(info.activityInfo.packageName, info.activityInfo.name));
                    } catch (Exception e) {

                    }
                }
            }
        }
        return remainList;
    }

    /**
     * @param info
     * @return
     */
    public boolean inWhit(ActivityInfo info) {

        String pkg = info.packageName;
        String clazz = info.name;

        if (mWhitDict.containsKey(pkg)) {
            CellBean bean = mWhitDict.get(pkg);
            String tClazz = bean.itemType == CellBean.TYPE_SHORTCUT ? bean.actualClazz : bean.className;
            if (TextUtils.isEmpty(tClazz)) {
                return true;
            } else {
                return tClazz.equals(clazz);
            }
        }
        return false;
    }

    public boolean inWhit(String pkg, String clazz) {
        if (mWhitDict.containsKey(pkg)) {
            CellBean bean = mWhitDict.get(pkg);
            if (TextUtils.isEmpty(bean.className)) {
                return true;
            } else {
                return bean.className.equals(clazz);
            }
        }
        return false;
    }

    /**
     * 是否为默认快捷
     *
     * @param iconType
     * @return
     */
    public boolean isDefaultShortcut(String iconType) {
        if (TextUtils.isEmpty(iconType)) {
            return false;
        }
        if (iconType.equals(BaseThemeData.INTENT_BROWSER) || iconType.equals(BaseThemeData.INTENT_CONTACTS)
                || iconType.equals(BaseThemeData.INTENT_MMS) || iconType.equals(BaseThemeData.INTENT_MY_THEME)
                || iconType.equals(BaseThemeData.INTENT_PHONE)) {
            return true;
        }

        return false;
    }

    public List<ResolveInfo> queryMainActivities() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = mContext.getPackageManager();
        return pm.queryIntentActivities(mainIntent, 0);
    }

    public String genIcon(String dir, String pkg, String className) {
        PackageManager pm = mContext.getPackageManager();
        try {
            Drawable dr = pm.getActivityIcon(new ComponentName(pkg, className));
            Bitmap bp = BitmapUtils.drawable2Bitmap(dr);
            File file = new File(dir, (pkg + "_" + className).replaceAll("\\.", "_") + ".png");
            BitmapUtils.saveBitmap2file(bp, file.getAbsolutePath(), Bitmap.CompressFormat.PNG);
            return file.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
