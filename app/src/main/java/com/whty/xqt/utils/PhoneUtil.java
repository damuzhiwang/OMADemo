package com.whty.xqt.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Locale;

import io.reactivex.functions.Consumer;

/**
 * Created by jiangzhe on 2018/4/24.
 */

public class PhoneUtil {

    /**
     * 获取手机号
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context) {
        LogUtils.e("phoneutils","获取电话号码");
        final String[] line1Number = {""};
        RxPermissions rxPermissions = new RxPermissions((FragmentActivity) context);
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_NUMBERS)
                .subscribe(new Consumer<Boolean>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LogUtils.e("phoneutils",""+aBoolean);
                        if(aBoolean){
                            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                            line1Number[0] = tm.getLine1Number();
                        }
                    }
                });

        return line1Number[0];
    }
    /**
     * 获取手机系统语言
     *
     * @return
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI(Context ctx) {
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.READ_PHONE_STATE},
                    100);

        }else {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
            if (tm != null) {
                return tm.getDeviceId();
            }
        }

        return null;
    }
}
