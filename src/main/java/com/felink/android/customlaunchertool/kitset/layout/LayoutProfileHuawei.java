package com.felink.android.customlaunchertool.kitset.layout;

import com.felink.android.customlaunchertool.kitset.BaseThemeData;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月13日 20:33.</br>
 * @update: </br>
 */

public class LayoutProfileHuawei {

    public static List<CellBean> layout() {

        List<CellBean> list = new ArrayList<CellBean>();
        //第一屏
        list.add(CellBean.build(0, 0, 0, 3, "音乐", CellBean.CANDIDATENAME_MUSIC, RecommandAppPool.TYPE_MUSIC, BaseThemeData.ICON_MUSIC));
        list.add(CellBean.build(0, 0, 1, 3, "视频", CellBean.CANDIDATENAME_VIDEO, RecommandAppPool.TYPE_VIDEO, BaseThemeData.ICON_VIDEO_CAMERA));
        list.add(CellBean.build(0, 0, 2, 3, CellBean.TYPE_APP, "电子邮件", CellBean.CANDIDATENAME_EMAIL));
        list.add(CellBean.build(0, 0, 3, 3, CellBean.TYPE_APP, "图库", CellBean.CANDIDATENAME_GALLERY));
        list.add(CellBean.build(0, 0, 0, 4, "应用市场", CellBean.CANDIDATENAME_STORE, RecommandAppPool.TYPE_APP_STORE));
        list.add(CellBean.build(0, 0, 1, 4, CellBean.TYPE_HI_APP, "主题", RecommandAppPool.LAUNCHER_THEME));
        list.add(CellBean.build(0, 0, 2, 4, CellBean.TYPE_APP, "设置", CellBean.CANDIDATENAME_SETTING));
        list.add(CellBean.build(0, 0, 3, 4, CellBean.TYPE_APP, "相机", CellBean.CANDIDATENAME_CAMERA));

        //第二屏
        list.add(CellBean.build(0, 1, 0, 0, "日历", CellBean.CANDIDATENAME_CALENDAR, RecommandAppPool.TYPE_CALENDAR));
        list.add(CellBean.build(0, 1, 1, 0, CellBean.TYPE_APP, "时钟", CellBean.CANDIDATENAME_TIMER));
        list.add(CellBean.build(0, 1, 2, 0, "手机管家", RecommandAppPool.getFuzzyRegexs("手机管家", "系统管家"), RecommandAppPool.TYPE_SAFE));
        list.add(CellBean.build(0, 1, 3, 0, CellBean.TYPE_APP, "手机服务", RecommandAppPool.getFuzzyRegexs("会员服务")));
        list.add(CellBean.build(0, 1, 0, 1, CellBean.TYPE_APP, "文件管理"));
        list.add(CellBean.build(0, 1, 1, 1, "天气", CellBean.CANDIDATENAME_WEATHER, RecommandAppPool.TYPE_WEATHER));
        list.add(CellBean.build(0, 1, 2, 1, "阅读", RecommandAppPool.getFuzzyRegexs("阅读"), RecommandAppPool.TYPE_I_READER));
        list.add(CellBean.build(0, 1, 3, 1, CellBean.TYPE_APP, "应用宝", RecommandAppPool.YINGYONGBAO));
        list.add(CellBean.build(0, 1, 0, 2, "实用工具", new ArrayList<CellBean>(), true));

        List<CellBean> commons = new ArrayList<CellBean>();
        commons.add(CellBean.build(RecommandAppPool.ASSIST2345));
        commons.add(CellBean.build(RecommandAppPool.STORM));
        commons.add(CellBean.build(RecommandAppPool.WANDOU));
        commons.add(CellBean.build(RecommandAppPool.WEATHER));
        commons.add(CellBean.build(RecommandAppPool.SOGOU_BROWSER));
        commons.add(CellBean.build(RecommandAppPool.QIHOO360));
        commons.add(CellBean.build(RecommandAppPool.QIHOO360_STORE));
        commons.add(CellBean.build(RecommandAppPool.LIEBAO_CLEANER));
        commons.add(CellBean.build(RecommandAppPool.TMALL));
        commons.add(CellBean.build(RecommandAppPool.MEITUAN));
        list.add(CellBean.build(0, 1, 1, 2, "社交生活", commons));

        List<CellBean> recomment = new ArrayList<CellBean>();
        recomment.add(CellBean.build(RecommandAppPool.BAIDU_WEISHI));
        recomment.add(CellBean.build(RecommandAppPool.BAIDU_ASSISTANT));
        recomment.add(CellBean.build(RecommandAppPool.BAIDU_SEARCH));
        recomment.add(CellBean.build(RecommandAppPool.IFLY));
        recomment.add(CellBean.build(RecommandAppPool.QIHOO360_BROWER));
        recomment.add(CellBean.build(RecommandAppPool.GO));
        recomment.add(CellBean.build(RecommandAppPool.SHUXIANG));
        recomment.add(CellBean.build(RecommandAppPool.LIEYING_BROWSER));
        recomment.add(CellBean.build(RecommandAppPool.SOGOU_ASSIST));
        recomment.add(CellBean.build(RecommandAppPool.ZAKER));
        recomment.add(CellBean.build(RecommandAppPool.DIANCHUAN));
        recomment.add(CellBean.build(RecommandAppPool.BROWSER2345));
        recomment.add(CellBean.build(RecommandAppPool.WIFI));
        recomment.add(CellBean.build(RecommandAppPool.QB));
        recomment.add(CellBean.build(RecommandAppPool.SOGOU_SEARCH));
        recomment.add(CellBean.build(RecommandAppPool.GAODE));
        list.add(CellBean.build(0, 1, 2, 2, "轻松工作", recomment));

        List<CellBean> yule = new ArrayList<CellBean>();
        yule.add(CellBean.build(RecommandAppPool.JJ));
        yule.add(CellBean.build(RecommandAppPool.TAOBAO));
        yule.add(CellBean.build(RecommandAppPool.JD));
        yule.add(CellBean.build(RecommandAppPool.LETV_VIDEO));
        yule.add(CellBean.build(RecommandAppPool.PP_ASSISTANT));
        yule.add(CellBean.build(RecommandAppPool.WEIBO));
        yule.add(CellBean.build(RecommandAppPool.QQ));
        yule.add(CellBean.build(RecommandAppPool.WECHAT));
        yule.add(CellBean.build(RecommandAppPool.DOUDIZHU));
        yule.add(CellBean.build(RecommandAppPool.QIYI));
        yule.add(CellBean.build(RecommandAppPool.DIANPING));
        yule.add(CellBean.build(RecommandAppPool.BUYU_GAME));
        yule.add(CellBean.build(RecommandAppPool.TENCENT_NEWS));
        yule.add(CellBean.build(RecommandAppPool.DAILYNEWS));
        yule.add(CellBean.build(RecommandAppPool.SOHU_NEWS));
        yule.add(CellBean.build(RecommandAppPool.IYD));
        list.add(CellBean.build(0, 1, 3, 2, "休闲娱乐", yule));

        //DOCK
        list.add(new CellBean.Builder()
                .setCoordiate(1, 0, 0, 0)
                .setItemType(CellBean.TYPE_SHORTCUT)
                .setTitle("电话")
                .setAction(BaseThemeData.INTENT_PHONE)
                .setCandidateNames(CellBean.CANDIDATENAME_DIA)
                .build());
        list.add(CellBean.build(1, 0, 1, 0, "联系人", BaseThemeData.INTENT_CONTACTS));
        list.add(CellBean.build(1, 0, 2, 0, "信息", BaseThemeData.INTENT_MMS));
        list.add(CellBean.build(1, 0, 3, 0, "浏览器", CellBean.CANDIDATENAME_BROWSER, RecommandAppPool.TYPE_BROWSER));

        return list;
    }
}
