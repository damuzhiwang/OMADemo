//package com.whty.xqt.utils;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//
//import com.whty.xqt.R;
//
//
///**
// * Created by jiangzhe on 2018/5/23.
// */
//
//public class DialogUtil {
//    private Context mContext;
//
//    private DialogUtil(Context context) {
//        this.mContext = context;
//    }
//
//    public static DialogUtil getInstance(Context context) {
//        DialogUtil mDialogUtil = null;
//        if (mDialogUtil == null) {
//            mDialogUtil = new DialogUtil(context);
//        }
//        return mDialogUtil;
//    }
//
//    public Dialog getNoticeDialog(final String message, final String confirmBtnTxt,
//                                  final String cancelBtnTxt, final View.OnClickListener confirmListener,
//                                  final View.OnClickListener cancelListener) {
//        final Dialog dialog = new Dialog(mContext, R.style.DialogStyle);
//        dialog.setContentView(R.layout.notice_dialog_layout);
//        final TextView msgTxt = dialog.findViewById(R.id.msgText);
//        msgTxt.setText(message);
//        final TextView confirmBtn = dialog.findViewById(R.id.confirmBtn);
//        final TextView cancelBtn = dialog.findViewById(R.id.cancelBtn);
//        if (confirmListener != null) {
//            confirmBtn.setText(confirmBtnTxt);
//            confirmBtn.setOnClickListener(confirmListener);
//        } else {
//            confirmBtn.setVisibility(View.GONE);
//        }
//        if (cancelListener != null) {
//            cancelBtn.setText(cancelBtnTxt);
//            cancelBtn.setOnClickListener(cancelListener);
//        } else {
//            cancelBtn.setVisibility(View.GONE);
//        }
//        return dialog;
//    }
//
//    public Dialog getNoticeDialog(final String message, final String confirmBtnTxt,
//                                  final View.OnClickListener confirmListener,
//                                  final View.OnClickListener cancelListener) {
//        final Dialog dialog = new Dialog(mContext, R.style.DialogStyle);
//        dialog.setContentView(R.layout.notice_dialog_layout4);
//        final TextView msgTxt = dialog.findViewById(R.id.msgText);
//        msgTxt.setText(message);
//        final TextView confirmBtn = dialog.findViewById(R.id.confirmBtn);
//        final View cancelBtn = dialog.findViewById(R.id.close_iv);
//        if (confirmListener != null) {
//            confirmBtn.setText(confirmBtnTxt);
//            confirmBtn.setOnClickListener(confirmListener);
//        } else {
//            confirmBtn.setVisibility(View.GONE);
//        }
//        if (cancelListener != null) {
//            cancelBtn.setOnClickListener(cancelListener);
//        } else {
//            cancelBtn.setVisibility(View.GONE);
//        }
//        return dialog;
//    }
//
//
//    public Dialog getNoticeDialog(String message, View.OnClickListener confirmListener,
//                                  View.OnClickListener cancelListener) {
//        Dialog dialog = new Dialog(mContext, R.style.DialogStyle);
//        dialog.setContentView(R.layout.notice_dialog_layout2);
//        TextView msgTxt = dialog.findViewById(R.id.msgText);
//        msgTxt.setText(message);
//        TextView confirmBtn = dialog.findViewById(R.id.confirmBtn);
//        TextView cancelBtn = dialog.findViewById(R.id.cancelBtn);
//        CheckBox cb = dialog.findViewById(R.id.cb);
//        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                SharedPreferencesTools.setBoolean(mContext, "isChecked", isChecked);
//            }
//        });
//        if (confirmListener != null) {
//            confirmBtn.setText("确定");
//            confirmBtn.setOnClickListener(confirmListener);
//        } else {
//            confirmBtn.setVisibility(View.GONE);
//        }
//        if (cancelListener != null) {
//            cancelBtn.setText("取消");
//            cancelBtn.setOnClickListener(cancelListener);
//        } else {
//            cancelBtn.setVisibility(View.GONE);
//        }
//        return dialog;
//    }
//
//    public void showWarnDialog() {
//        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        Display display = windowManager.getDefaultDisplay();
//        final Dialog dialog = new Dialog(mContext, R.style.DialogStyle);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.notice_dialog_layout3, null);
//        dialog.setContentView(view);
//        Window dialogWindow = dialog.getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = display.getWidth() * 3 / 5;
////        lp.height = display.getHeight() / 2;
//        lp.y = -(display.getWidth() / 10);
//        dialogWindow.setAttributes(lp);
//        dialog.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });
//        CheckBox cb = dialog.findViewById(R.id.cb);
//        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                SharedPreferencesTools.setBoolean(mContext, "isChecked_invoice_notice", isChecked);
//            }
//        });
//        dialog.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });
//        dialog.show();
//    }
//
//    private int scene = -1;
//
//    public Dialog showShareDialog(final OnShareListener shareListener, View.OnClickListener cancelListener) {
//        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        Display display = windowManager.getDefaultDisplay();
//        Dialog dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_pop_share, null);
//        view.setMinimumWidth(display.getWidth());
//        dialog.setContentView(view);
//        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setGravity(Gravity.BOTTOM);
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.x = 0;
//        lp.y = 0;
//        dialogWindow.setAttributes(lp);
////        dialog.findViewById(R.id.wx_share).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                scene = SendMessageToWX.Req.WXSceneSession;
////                if (shareListener != null) {
////                    shareListener.onShare(scene);
////                }
////            }
////        });
////        dialog.findViewById(R.id.pyq_share).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                scene = SendMessageToWX.Req.WXSceneTimeline;
////                if (shareListener != null) {
////                    shareListener.onShare(scene);
////                }
////            }
////        });
//        View cancelBtn = dialog.findViewById(R.id.cancel_share);
//        if (cancelListener != null) {
//            cancelBtn.setOnClickListener(cancelListener);
//        }
//        return dialog;
//    }
//}
