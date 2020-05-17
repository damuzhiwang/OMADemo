//package com.whty.xqt.utils;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.nfc.NfcAdapter;
//import android.view.View;
//
//
///**
// * NFC管理标签初始化
// *
// * @author 钱磊
// */
//public class NfcManager {
//
//    private static NfcManager instance = new NfcManager();
//    private static Dialog noticeDialog;
//
//    private NfcManager() {
//    }
//
//    public static NfcManager getInstance() {
//        return instance;
//    }
//
//    //NFC
//    private NfcAdapter nfcAdapter;
//
//    /**
//     * 判断nfc是否可用
//     * @return
//     */
//    public int isEnabled(Activity mActivity) {
//        nfcAdapter = NfcAdapter.getDefaultAdapter(mActivity);
//        int nfcFlag = -1;
//        // 判定设备是否支持NFC或启动NFC
//        if (nfcAdapter == null) {
//            // 设备不支持NFC
//            return nfcFlag;
//        }
//        if (!nfcAdapter.isEnabled()) {
//            //请在系统设置中先启用NFC功能
//            nfcFlag = 0;
//        }else{
//            nfcFlag = 1;
//        }
//        return nfcFlag;
//    }
//
//    public static void checkNfc(final Activity context){
//        int code = NfcManager.getInstance().isEnabled(context);
//        if (noticeDialog!=null&&noticeDialog.isShowing()){
//            noticeDialog.cancel();
//        }
//        switch (code) {
//            case 0:
//                noticeDialog = DialogUtil.getInstance(context).getNoticeDialog(
//                        "NFC功能未开启，\n请前往设置中心开启NFC功能", "立即开启", null,
//                        new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                noticeDialog.cancel();
//                                context.startActivity(new Intent("android.settings.NFC_SETTINGS"));
//                            }
//                        }, null);
//                noticeDialog.setCanceledOnTouchOutside(true);
//                noticeDialog.setCancelable(false);
//                if (noticeDialog!=null&&!noticeDialog.isShowing()){
//                    noticeDialog.show();
//                }
//                break;
//            case 1:
//                break;
//            case -1:
//                noticeDialog = DialogUtil.getInstance(context).getNoticeDialog(
//                        "手机不支持NFC功能", "确定", null,
//                        new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                noticeDialog.cancel();
//                                context.finish();
//                            }
//                        }, null);
//                noticeDialog.setCanceledOnTouchOutside(false);
//                noticeDialog.setCancelable(false);
//                if (noticeDialog!=null&&!noticeDialog.isShowing()){
//                    noticeDialog.show();
//                }
//                break;
//        }
//    }
//
//}
