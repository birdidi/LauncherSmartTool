package com.felink.android.customlaunchertool.kitset;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;

import com.felink.android.customlaunchertool.config.Global;
import com.felink.android.customlaunchertool.kitset.common.AndroidPackageUtils;
import com.felink.android.customlaunchertool.kitset.common.SystemUtil;
import com.felink.android.customlaunchertool.kitset.layout.CellBean;
import com.felink.android.customlaunchertool.model.Info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月25日 10:37.</br>
 * @update: </br>
 */

public enum DefIcon {

    /**
     * 时钟
     * 浏览器
     * 计算器
     * 日历
     * 相机
     * 图库
     * 视频
     * 电话
     * 联系人
     * 邮件
     * 短信
     * 音乐
     * 设置
     * 应用商店
     * 主题
     */
    ALARM(BaseThemeData.ICON_ALARMCLOCK, CellBean.CANDIDATENAME_TIMER),
    BROWSER(CellBean.TYPE_SHORTCUT, BaseThemeData.ICON_BROWSER, CellBean.CANDIDATENAME_BROWSER),
    CALULATER(BaseThemeData.ICON_CALCULATOR, CellBean.CANDIDATENAME_CALULATOR),
    CALENDAR(BaseThemeData.ICON_CALENDAR, CellBean.CANDIDATENAME_CALENDAR),
    CAMEAR(BaseThemeData.ICON_CAMERA, CellBean.CANDIDATENAME_CAMERA),
    GALLERY(BaseThemeData.ICON_GALLERYPICKER, CellBean.CANDIDATENAME_GALLERY),
    VIDEO(BaseThemeData.ICON_VIDEO_CAMERA, CellBean.CANDIDATENAME_VIDEO),
    PHONE(CellBean.TYPE_SHORTCUT, BaseThemeData.ICON_PHONE, CellBean.CANDIDATENAME_DIA),
    CONTACTS(CellBean.TYPE_SHORTCUT, BaseThemeData.ICON_CONTACTS, CellBean.CANDIDATENAME_CONTACTS),
    EMAIL(BaseThemeData.ICON_EMAIL, CellBean.CANDIDATENAME_EMAIL),
    SMS(CellBean.TYPE_SHORTCUT, BaseThemeData.ICON_MMS, CellBean.CANDIDATENAME_SMS),
    MUSIC(BaseThemeData.ICON_MUSIC, CellBean.CANDIDATENAME_MUSIC),
    SETTING(BaseThemeData.ICON_SETTINGS, CellBean.CANDIDATENAME_SETTING),
    STORE(BaseThemeData.ICON_APP_STORE, CellBean.CANDIDATENAME_STORE),
    THEME(CellBean.TYPE_HI_APP, BaseThemeData.ICON_THEME, CellBean.CANDIDATENAME_THEME),
    THEME_APP(CellBean.TYPE_APP, BaseThemeData.ICON_THEME, CellBean.CANDIDATENAME_THEME),
    PHONE_APP(BaseThemeData.ICON_PHONE, CellBean.CANDIDATENAME_DIA),
    SMS_APP(BaseThemeData.ICON_MMS, CellBean.CANDIDATENAME_SMS),
    BROWSER_APP(BaseThemeData.ICON_BROWSER, CellBean.CANDIDATENAME_BROWSER),
    CONTACTS_APP(BaseThemeData.ICON_CONTACTS, CellBean.CANDIDATENAME_CONTACTS),
    WEATHER(BaseThemeData.ICON_WEATHER, CellBean.CANDIDATENAME_WEATHER);

    private String[] mRegexArr;
    private String mIconType;
    private int mItemType = CellBean.TYPE_APP;
    private String mAction;

    DefIcon(String iconType, String[] regexStr) {
        this(CellBean.TYPE_APP, iconType, regexStr);
    }

    DefIcon(int itemType, String iconType, String[] regexStr) {
        this.mRegexArr = regexStr;
        this.mIconType = iconType;
        this.mItemType = itemType;

        if (this.mItemType == CellBean.TYPE_SHORTCUT) {
            if (BaseThemeData.ICON_PHONE.equals(mIconType)
                    || BaseThemeData.ICON_MMS.equals(mIconType)
                    || BaseThemeData.ICON_BROWSER.equals(mIconType)
                    || BaseThemeData.ICON_CONTACTS.equals(mIconType)
                    ) {
                if (BaseThemeData.ICON_PHONE.equals(mIconType)) {
                    mAction = BaseThemeData.INTENT_PHONE;
                }
                if (BaseThemeData.ICON_MMS.equals(mIconType)) {
                    mAction = BaseThemeData.INTENT_MMS;
                }
                if (BaseThemeData.ICON_BROWSER.equals(mIconType)) {
                    mAction = BaseThemeData.INTENT_BROWSER;
                }

                if (BaseThemeData.ICON_CONTACTS.equals(mIconType)) {
                    mAction = BaseThemeData.INTENT_CONTACTS;
                }
            }
        }
    }

    public CellBean query(Verifier verifier) {
        ComponentName cn = queryMainActivityByName(verifier, this.mRegexArr);
        CellBean bean = null;
        if (cn != null) {
            String action = TextUtils.isEmpty(mAction) ? AndroidPackageUtils.getNewTaskIntent(cn).toUri(0) : mAction;
            bean = new CellBean.Builder()
                    .setComponentName(cn)
                    .setIconType(this.mIconType)
                    .setItemType(this.mItemType)
                    .setAction(action)
                    .build();

            if (mItemType == CellBean.TYPE_SHORTCUT || mItemType == CellBean.TYPE_HI_APP) {
                bean.actualPkg = cn.getPackageName();
                bean.actualClazz = cn.getClassName();
            }
        }

        return bean;
    }

    private ComponentName queryMainActivityByName(Verifier verifier, String... candidate) {
        PackageManager pm = Global.getApplicationContext().getPackageManager();
        List<ResolveInfo> result = AndroidPackageUtils.queryMainIntentActivity(pm);
        List<Info> matched = new ArrayList<Info>();
        if (result != null && !result.isEmpty()) {
            if (matched.isEmpty() && candidate != null && candidate.length > 0) {
//                for (ResolveInfo info : result) {
//                    ActivityInfo ainfo = info.activityInfo;
//                    if (ainfo != null) {
//                        CharSequence name = ainfo.loadLabel(pm);
//                        for (int i = 0, len = candidate.length; i < len; i++) {
//                            Pattern pattern = Pattern.compile(candidate[i]);
//                            Matcher matcher = pattern.matcher(name.toString());
//                            if (matcher.find()) {
//                                matched.add(ainfo);
//                            }
//                        }
//                    }
//                }
                int priority = 0;
                for (int i = 0, len = candidate.length; i < len; i++) {
                    Pattern pattern = Pattern.compile(candidate[i]);
                    for (ResolveInfo info : result) {
                        ActivityInfo ainfo = info.activityInfo;
                        if (ainfo != null) {
//                            if (verifier.verify(ainfo)) {
//                                continue;
//                            }
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

    public interface Verifier {
        boolean verify(ActivityInfo info);
    }
}
