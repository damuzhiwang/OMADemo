package com.whty.xqt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;

/**
 * *****************************************************************************<br>
 * 工程名称: <br>
 * 模块功能：对SharedPreferences的操作工具类，主要包含你set和get方法<br>
 * 作 者: 张辉<br>
 * 单 位：武汉天喻信息 研发中心 <br>
 * 开发日期：2014-12-31 上午10:38:19 <br>
 * *****************************************************************************<br>
 */
public final class SharedPreferencesTools {
    private static final String FILE_NAME = "jscmcc_sp";

    public static String getPreferenceValue(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void setPreferenceValue(Context context, String key,
                                          String value) {
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setStringSet(Context context, String key, Set<String> set) {
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putStringSet(key, set);
        editor.commit();
    }

    public static Set<String> getStringSet(Context context, String key, Set<String> set) {
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return prefs.getStringSet(key, set);
    }

    public static void cleanPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
