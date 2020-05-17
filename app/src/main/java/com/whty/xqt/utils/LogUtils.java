package com.whty.xqt.utils;

import android.text.TextUtils;
import android.util.Log;

import com.whty.xqt.BuildConfig;


/**
 * @author wzj
 * 版本：1.0
 * 创建日期：2020/1/7 9:10
 * 描述：日志打印的工具类
 */
public class LogUtils {
    private static boolean IS_SHOW = BuildConfig.DEBUG;
    private final static int SEGMENT_MAX_SIZE = 3 * 1024;

    /**
     * 设置是否显示Log
     * @param enable true-显示 false-不显示
     */
    public static void setLogEnable(boolean enable) {
        IS_SHOW = enable;
    }

    public static void i(String tag, String message) {
        if (IS_SHOW) {
            Log.i(tag + "-->:"+getTargetStackTraceElement(), message);
        }
    }

    public static void e(String tag, String message) {
        if (IS_SHOW) {
            Log.e(tag + "-->:"+getTargetStackTraceElement(), message);
        }
    }
    public static void e(String tag, String message, Throwable tr) {
        if (IS_SHOW) {
            Log.e(tag + "-->:"+getTargetStackTraceElement(), message,tr);
        }
    }

    public static void w(String tag, String message) {
        if (IS_SHOW) {
            Log.w(tag + "-->:"+getTargetStackTraceElement(), message);
        }
    }

    public static void v(String tag, String message) {
        if (IS_SHOW) {
            Log.v(tag + "-->:"+getTargetStackTraceElement(), message);
        }
    }

    public static void d(String tag, String message) {
        if (IS_SHOW) {
            Log.d(tag + "-->:"+getTargetStackTraceElement(), message);
        }
    }

    private static String getTargetStackTraceElement() {
        StackTraceElement targetStackTrace = null;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if(stackTrace.length>=4){
            targetStackTrace = stackTrace[4];
        }
        String s = "";
        if(null != targetStackTrace){
            s = "(" + targetStackTrace.getFileName() + ":"
                    + targetStackTrace.getLineNumber() + ")";
        }
        return s;
    }


    /**
     * 超长日志分段打印
     * @param tag 标签，可以在logcat工具中被识别
     * @param object 日志内容，需要被打印输出的内容
     * */
    public static void LongLog(String tag, Object object) {
        boolean illegalTag = false;
        boolean illegalObject = false;
        if (TextUtils.isEmpty(tag)) {
            illegalTag = true;
        }
        if (object == null || object.toString().length() == 0) {
            illegalObject = true;
        }
        if (illegalTag || illegalObject) {
            return;
        }
        String message = object.toString();
        int length = message.length();
        if (length <= SEGMENT_MAX_SIZE) {
            if (IS_SHOW) {
                Log.d(tag + "-->:"+getTargetStackTraceElement(), message);
            }
        }else {
            while (message.length() > SEGMENT_MAX_SIZE) {
                String logContent = message.substring(0, SEGMENT_MAX_SIZE);
                message = message.substring(SEGMENT_MAX_SIZE);
                Log.d(tag + "-->:"+getTargetStackTraceElement(), logContent);
            }
            Log.d(tag + "-->:"+getTargetStackTraceElement(), message);
        }
    }



}

