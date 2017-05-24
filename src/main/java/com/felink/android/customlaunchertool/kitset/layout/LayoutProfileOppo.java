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

public class LayoutProfileOppo {

    public static List<CellBean> layout() {

        List<CellBean> list = new ArrayList<CellBean>();
        //第一屏
        list.add(CellBean.build(0, 0, 0, 0, "日历", CellBean.CANDIDATENAME_CALENDAR, RecommandAppPool.TYPE_CALENDAR));
        list.add(CellBean.build(0, 0, 1, 0, CellBean.TYPE_APP, "时钟", CellBean.CANDIDATENAME_TIMER));
        list.add(CellBean.build(0, 0, 2, 0, "天气", CellBean.CANDIDATENAME_WEATHER, RecommandAppPool.TYPE_WEATHER));
        list.add(CellBean.build(0, 0, 3, 0, CellBean.TYPE_APP, "设置", CellBean.CANDIDATENAME_SETTING));
        list.add(CellBean.build(0, 0, 0, 1, "音乐", CellBean.CANDIDATENAME_MUSIC, RecommandAppPool.TYPE_MUSIC));
        list.add(CellBean.build(0, 0, 1, 1, CellBean.TYPE_APP, "相册", CellBean.CANDIDATENAME_GALLERY));
        list.add(CellBean.build(0, 0, 2, 1, "主题", CellBean.CANDIDATENAME_THEME, RecommandAppPool.TYPE_THEME));
        list.add(CellBean.build(0, 0, 3, 1, "手机管家", RecommandAppPool.TYPE_SAFE));
        list.add(CellBean.build(0, 0, 0, 2, "阅读", RecommandAppPool.getFuzzyRegexs("阅读"), RecommandAppPool.TYPE_I_READER));
        list.add(CellBean.build(0, 0, 1, 2, "应用商店", CellBean.CANDIDATENAME_STORE, RecommandAppPool.TYPE_APP_STORE));
        list.add(CellBean.build(0, 0, 2, 2, CellBean.TYPE_APP, "腾讯新闻", RecommandAppPool.TENCENT_NEWS));
        list.add(CellBean.build(0, 0, 3, 2, CellBean.TYPE_APP, "文件管理"));

        list.add(CellBean.build(0, 0, 0, 3, "工具", new ArrayList<CellBean>(), true));

        //第二屏
        list.add(CellBean.build(0, 1, 0, 0, CellBean.TYPE_APP, "便签", RecommandAppPool.getFuzzyRegexs("便签")));
        list.add(CellBean.build(0, 1, 1, 0, CellBean.TYPE_APP, "OPPO会员"));
        list.add(CellBean.build(0, 1, 2, 0, CellBean.TYPE_APP, "高德地图", RecommandAppPool.GAODE));
        list.add(CellBean.build(0, 1, 3, 0, "视频", CellBean.CANDIDATENAME_VIDEO, RecommandAppPool.TYPE_VIDEO));
        list.add(CellBean.build(0, 1, 0, 1, CellBean.TYPE_APP, "今日头条", RecommandAppPool.DAILYNEWS));
        list.add(CellBean.build(0, 1, 1, 1, CellBean.TYPE_APP, "搜狗搜索", RecommandAppPool.SOGOU_SEARCH));
        list.add(CellBean.build(0, 1, 2, 1, CellBean.TYPE_APP, "美团", RecommandAppPool.MEITUAN));
        list.add(CellBean.build(0, 1, 3, 1, CellBean.TYPE_APP, "搜狐新闻", RecommandAppPool.SOHU_NEWS));
        list.add(CellBean.build(0, 1, 0, 2, CellBean.TYPE_APP, "QQ浏览器", RecommandAppPool.QB));

        List<CellBean> commons = new ArrayList<CellBean>();
        commons.add(CellBean.build(RecommandAppPool.CALCULATOR));
        commons.add(CellBean.build(RecommandAppPool.YINGYONGBAO));
        commons.add(CellBean.build(RecommandAppPool.ASSIST2345));
        commons.add(CellBean.build(RecommandAppPool.STORM));
        commons.add(CellBean.build(RecommandAppPool.WANDOU));
        commons.add(CellBean.build(RecommandAppPool.WEATHER));
        commons.add(CellBean.build(RecommandAppPool.SOGOU_BROWSER));
        commons.add(CellBean.build(RecommandAppPool.QIHOO360));
        commons.add(CellBean.build(RecommandAppPool.QIHOO360_STORE));
        commons.add(CellBean.build(RecommandAppPool.LIEBAO_CLEANER));
        list.add(CellBean.build(0, 1, 1, 2, "常用应用", commons));

        List<CellBean> yule = new ArrayList<CellBean>();
        yule.add(CellBean.build(RecommandAppPool.JJ));
        yule.add(CellBean.build(RecommandAppPool.TAOBAO));
        yule.add(CellBean.build(RecommandAppPool.TMALL));
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
        list.add(CellBean.build(0, 1, 2, 2, "娱乐", yule));

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
        list.add(CellBean.build(0, 1, 3, 2, "推荐", recomment));

        //DOCK
        list.add(CellBean.build(1, 0, 0, 0, "电话", BaseThemeData.INTENT_PHONE));
        list.add(CellBean.build(1, 0, 1, 0, "信息", BaseThemeData.INTENT_MMS));
        list.add(CellBean.build(1, 0, 2, 0, "浏览器", CellBean.CANDIDATENAME_BROWSER, RecommandAppPool.TYPE_BROWSER));
        list.add(CellBean.build(1, 0, 3, 0, CellBean.TYPE_APP, "相机", CellBean.CANDIDATENAME_CAMERA));

        return list;
    }
}
