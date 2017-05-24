package com.felink.android.customlaunchertool.kitset.common;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年04月15日 14:10.</br>
 * @update: </br>
 */

public class StringUtil {

    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() <= 0;
    }

    public static boolean isAnyEmpty(String... strs) {
        if(strs == null) {
            return true;
        } else {
            int N = strs.length;

            for(int i = 0; i < N; ++i) {
                if(isEmpty(strs[i])) {
                    return true;
                }
            }

            return false;
        }
    }
}
