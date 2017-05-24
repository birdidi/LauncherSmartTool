package com.felink.android.customlaunchertool.kitset.layout;

import android.content.Context;
import android.util.Log;

import com.felink.android.customlaunchertool.kitset.common.TelephoneUtil;

import java.util.List;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月13日 20:22.</br>
 * @update: </br>
 */

public class ExportLayoutCompat {

    private static ExportLayoutImpl IMPL;

    static {
        if (TelephoneUtil.isOppoPhone()) {
            Log.d("cxydebug", "static initializer: OPPO");
            IMPL = new ExportLayoutOppo();
        } else if (TelephoneUtil.isVivoPhone()) {
            Log.d("cxydebug", "static initializer: VIVO");
            IMPL = new ExportLayoutVivo();
        } else if (TelephoneUtil.isHuaweiPhone()) {
            Log.d("cxydebug", "static initializer: HUAWEI");
            IMPL = new ExportLayoutHuawei();
        } else if (TelephoneUtil.isLetvMoble()) {
            Log.d("cxydebug", "static initializer: LETV");
            IMPL = new ExportLayoutLetv();
        } else if (TelephoneUtil.isMeizuPhone()) {
            Log.d("cxydebug", "static initializer: MEIZU");
            IMPL = new ExportLayoutMX();
        } else if (TelephoneUtil.isSamsungPhone()) {
            Log.d("cxydebug", "static initializer: SAMSUNG");
            IMPL = new ExportLayoutSamsung();
        } else if (TelephoneUtil.isMIMoble()) {
            Log.d("cxydebug", "static initializer: MI");
            IMPL = new ExportLayoutMi();
        } else {
            Log.d("cxydebug", "static initializer: OTHER");
            IMPL = new ExportLayoutImpl();
        }
    }

    public static void gen(Context context) {
        IMPL.export(context);
    }

    public static List<CellBean> layout() {
        return IMPL.getLayout();
    }

    public static String phoneType() {
        return IMPL.name();
    }

    public static class ExportLayoutImpl {

        public String name() {
            return "通用";
        }

        public void export(Context context) {

        }

        public List<CellBean> getLayout() {
            return LayoutProfileGeneric.layout();
        }
    }

    public static final class ExportLayoutOppo extends ExportLayoutImpl {

        @Override
        public String name() {
            return "Oppo";
        }

        @Override
        public void export(Context context) {
            super.export(context);
        }

        @Override
        public List<CellBean> getLayout() {
            return LayoutProfileOppo.layout();
        }
    }

    public static final class ExportLayoutVivo extends ExportLayoutImpl {

        @Override
        public String name() {
            return "Vivo";
        }

        @Override
        public void export(Context context) {
            super.export(context);
        }

        @Override
        public List<CellBean> getLayout() {
            return LayoutProfileVivo.layout();
        }
    }

    public static final class ExportLayoutHuawei extends ExportLayoutImpl {

        @Override
        public String name() {
            return "华为";
        }

        @Override
        public void export(Context context) {
            super.export(context);
        }

        @Override
        public List<CellBean> getLayout() {
            return LayoutProfileHuawei.layout();
        }
    }

    public static final class ExportLayoutLetv extends ExportLayoutImpl {

        @Override
        public String name() {
            return "乐视";
        }

        @Override
        public void export(Context context) {
            super.export(context);
        }

        @Override
        public List<CellBean> getLayout() {
            return LayoutProfileLetv.layout();
        }
    }

    public static final class ExportLayoutMX extends ExportLayoutImpl {

        @Override
        public String name() {
            return "魅族";
        }

        @Override
        public void export(Context context) {
            super.export(context);
        }

        @Override
        public List<CellBean> getLayout() {
            return LayoutProfileMeizu.layout();
        }
    }

    public static final class ExportLayoutSamsung extends ExportLayoutImpl {

        @Override
        public String name() {
            return "三星";
        }

        @Override
        public void export(Context context) {
            super.export(context);
        }

        @Override
        public List<CellBean> getLayout() {
            return LayoutProfileSamsung.layout();
        }
    }

    public static final class ExportLayoutMi extends ExportLayoutImpl {

        @Override
        public String name() {
            return "小米";
        }

        @Override
        public void export(Context context) {
            super.export(context);
        }

        @Override
        public List<CellBean> getLayout() {
            return LayoutProfileMi.layout();
        }
    }
}
