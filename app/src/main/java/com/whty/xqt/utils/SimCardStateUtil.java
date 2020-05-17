package com.whty.xqt.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class SimCardStateUtil {

    public static boolean isSimCardEnable(Context context, ToastUtil toastUtil) {
        boolean flag = false;
        TelephonyManager mTelephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        int simState = mTelephonyManager.getSimState();
        String hintMessage = "";
        switch (simState) {
            case TelephonyManager.SIM_STATE_UNKNOWN:
                hintMessage = "Unknown";
                hintMessage = "SIM卡处于未知状态!";
                break;
            case TelephonyManager.SIM_STATE_ABSENT:
                hintMessage = "no SIM card is available in the device";
                hintMessage = "未插入SIM卡!";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                hintMessage = "Locked: requires the user's SIM PIN to unlock";
                hintMessage = "SIM卡需要PIN解锁";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                hintMessage = "Locked: requires the user's SIM PUK to unlock ";
                hintMessage = "SIM卡需要PUK解锁";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                hintMessage = "Locked: requries a network PIN to unlock";
                hintMessage = "SIM卡需要NetworkPIN解锁";
                break;
            case TelephonyManager.SIM_STATE_READY:
                hintMessage = "Ready";
                flag = true;
                break;
            case TelephonyManager.SIM_STATE_NOT_READY:
                hintMessage = "SIM卡未识别";
                break;
            default:
                break;
        }
        if (!flag) {
            toastUtil.showToast(hintMessage);
        }

        return flag;

    }
}
