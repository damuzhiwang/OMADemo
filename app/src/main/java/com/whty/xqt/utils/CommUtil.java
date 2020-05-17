package com.whty.xqt.utils;

/**
 * Created by IntelliJ IDEA.
 * User: fibiger
 * Date: 2009-05-05
 * Time: 10:18:48
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.whty.xqt.MyApplication;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


public class CommUtil {

    /**
     * 32位随机数
     *
     * @return
     */
    public static String getRandom() {
        String chars = "ABCDEF0123456789";
        String res = "";
        for (int i = 0; i < 32; i++) {
            Random random = new Random();
            res += chars.charAt(random.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * 生成指定长度的10以内的随机数
     *
     * @param len
     * @return
     */
    public static String generateRandom(int len) {
        StringBuffer strBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int randomInt = Math.abs(random.nextInt()) % 10;
            if (i == 7 && randomInt == 9) {
                randomInt = randomInt;
            } else if (i == 0 && randomInt == 0) {
                randomInt += 1;
            }
            strBuffer.append(randomInt);
        }
        return strBuffer.toString();

    }

    /**
     * 将xml转换成view对象
     *
     * @param resId
     * @return
     */
    public static View getXmlView(int resId) {
        return View.inflate(getContext(), resId, null);
    }

    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    public static int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void Toast(String text, boolean isLong) {
        Toast.makeText(getContext(), text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取字符串数组
     *
     * @param arrId
     * @return
     */
    public static String[] getStringArr(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    /**
     * 获取字符串
     *
     * @param arrId
     * @return
     */
    public static String getString(int arrId) {
        return getContext().getResources().getString(arrId);
    }

    /**
     * 获取颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }


    /**
     * @param runnable
     */
    public static void runOnMainThread(Runnable runnable) {
        int tid = android.os.Process.myTid();
        if (tid == MyApplication.mainThreadId) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

    private static Handler getHandler() {
        return MyApplication.handler;
    }

    private static Context getContext() {
        return MyApplication.mContext;
    }

    /**
     * 是否汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 是否数字
     *
     * @param c
     * @return
     */
    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    /**
     * 是否字母
     *
     * @param c
     * @return
     */
    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    /**
     * 比较版本
     *
     * @param currentVersion
     * @param version
     * @return
     */
    public static boolean compareVersion(String currentVersion, String version) {
        boolean result = false;
        if (TextUtils.isEmpty(currentVersion) || TextUtils.isEmpty(version)) {
            throw new IllegalArgumentException("参数不合法");
        }
        String[] curStrings = currentVersion.split("\\.");
        String[] verStrings = version.split("\\.");
        if (Integer.valueOf(curStrings[0]) < Integer.valueOf(verStrings[0])) {
            return true;
        }
        if (Integer.valueOf(curStrings[1]) < Integer.valueOf(verStrings[1])) {
            return true;
        }
        if (Integer.valueOf(curStrings[2]) < Integer.valueOf(verStrings[2])) {
            return true;
        }
        return result;
    }

    /**
     * 获取版本名称
     *
     * @return
     * @throws
     */
    public static String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getContext().getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本不详";
        }
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 判断应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppAvaliable(Context context, String packageName) {
        PackageManager packagemanager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packagemanager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 图片转二进制
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static final int MIN_DELAY_TIME = 1500;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

//    public static void initPull2RefreshView(PullToRefreshListView lv, PullToRefreshBase.OnRefreshListener<ListView> listener) {
//        lv.setOnRefreshListener(listener);
//        // 设置PullRefreshListView上提加载时的加载提示
//        lv.setMode(PullToRefreshBase.Mode.BOTH);
//        lv.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
//        lv.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
//        lv.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
//
//        // 设置PullRefreshListView下拉加载时的加载提示
//        lv.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
//        lv.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
//        lv.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
//    }

    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    private static final String REGEX_PHONE_NUMBER = "^[1][3578][0-9]{9}$";

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }


    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_PHONE_NUMBER, mobile);
    }

    /**
     * 税号格式
     * @param idfNum
     * @return
     */
    public static boolean isTaxpayerNum(String idfNum) {
        int len = idfNum.length();
        if (len == 15 || len == 18 || len == 20) {
            char[] chars = idfNum.toCharArray();
            for (char c : chars) {
                if (isDigit(c) || isLetter(c)) {
                    continue;
                } else {
                    return false;
                }
            }
        }else {
            return false;
        }
        return true;
    }
}