package com.whty.xqt;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.whty.xqt.cardoperate.SimCardOperation;
import com.whty.xqt.cardoperate.SimCardOperation2;
import com.whty.xqt.cardoperate.SwpSimCardManager;
import com.whty.xqt.utils.CommonMethods;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static int mainThreadId;
    public static Handler handler;
    private SwpSimCardManager cardManager;
    private SimCardOperation simOperation;
    private SimCardOperation2 simCardOperation2;
    public static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        closeAndroidPDialog();
        mContext = getApplicationContext();
        mainThreadId = android.os.Process.myTid();
        handler = new Handler();
//        RetrofitUtils.getTag();
//        initCardManager();
    }

    private void closeAndroidPDialog() {
        if (Build.VERSION.SDK_INT < 28) {
            return;
        }
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method declaredMethod = clazz.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(invoke, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetContext() {
        mContext = this;
    }

    public void initCardManager() {
        // 初始化SIM卡
        GetContext();
        cardManager = SwpSimCardManager.getSwpSimCardManager(getApplicationContext());
        if (CommonMethods.nfcEnable(this)) {
            cardManager.initCard();
        }
    }

    public SwpSimCardManager getSwpSimCardManager() {
        if (cardManager == null) {
            cardManager = SwpSimCardManager.getSwpSimCardManager(getApplicationContext());
            if (CommonMethods.nfcEnable(this)) {
                cardManager.initCard();
            }
        }
        return cardManager;
    }

//    public boolean serviceConnected() {
//        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
//            if (simCardOperation2 == null) {
//                return false;
//            } else {
//                return simCardOperation2.serviceConnected();
//            }
//        }else {
//            if (simOperation == null) {
//                return false;
//            } else {
//                return simOperation.serviceConnected();
//            }
//        }
//
//    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}