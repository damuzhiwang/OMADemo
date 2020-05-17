package com.whty.xqt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.whty.xqt.base.BaseActivity;
import com.whty.xqt.cardoperate.SwpSimCardManager;
import com.whty.xqt.cons.AppConst;
import com.whty.xqt.presenter.CardOperatePresenter;
import com.whty.xqt.ui.TradingRecordActivity;
import com.whty.xqt.utils.LogUtils;
import com.whty.xqt.utils.PhoneUtil;
import com.whty.xqt.utils.ToastUtil;

import java.util.Set;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlBack;
    private ImageView ivHeadPic;
    /**
     * 王不留行
     */
    private TextView tvName;
    /**
     * 华中科技大学
     */
    private TextView tvSchool;
    /**
     * 计算机
     */
    private TextView tvProfessional;
    /**
     * 大二（5）班
     */
    private TextView tvGradle;
    /**
     * 100.00
     */
    private TextView tvBalance;
    /**
     * 充值
     */
    private TextView tvRecharge;
    /**
     * 15584211111
     */
    private TextView tvCardNum;
    /**
     * 交易记录
     */
    private TextView tvTradeRecord;
    private Boolean hasPermission;

    private SwpSimCardManager simCardManager;


    @Override
    public void initData() {
        RequestOptions options = new RequestOptions()
                .centerCrop();
        Glide.with(this)
                .load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3596176733,1185061963&fm=11&gp=0.jpg")
                .apply(options)
                .into(ivHeadPic);

        simCardManager = ((MyApplication) getApplication()).getSwpSimCardManager();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String cardNumber = simCardManager.getCardNumber(AppConst.APP_CARD_AID);
                double cardBalance = simCardManager.getCardBalance(AppConst.APP_CARD_AID);
                LogUtils.d("www", cardNumber + "---" + cardBalance);
                tvCardNum.setText(cardNumber);
                tvBalance.setText("" + cardBalance);
            }
        }, 1000);

    }




    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        rlBack.setOnClickListener(this);
        ivHeadPic = (ImageView) findViewById(R.id.iv_head_pic);
        ivHeadPic.setOnClickListener(this);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvSchool = (TextView) findViewById(R.id.tv_school);
        tvProfessional = (TextView) findViewById(R.id.tv_professional);
        tvGradle = (TextView) findViewById(R.id.tv_gradle);
        tvBalance = (TextView) findViewById(R.id.tv_balance);
        tvRecharge = (TextView) findViewById(R.id.tv_recharge);
        tvRecharge.setOnClickListener(this);
        tvCardNum = (TextView) findViewById(R.id.tv_card_num);
        tvTradeRecord = (TextView) findViewById(R.id.tv_trade_record);
        tvTradeRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_back:
                break;
            case R.id.iv_head_pic:
//                    String phone = PhoneUtil.getPhoneNumber(MainActivity.this);
//                    LogUtils.e("mainactivity","电话为："+phone);
                break;
            case R.id.tv_recharge:
                showProgressDialog("开始加载");
                autoClosed();
                break;
            case R.id.tv_trade_record:
//                gotoActivity(TradingRecordActivity.class,null);
                Intent intent = new Intent(this, TradingRecordActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void autoClosed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                dismissFailedDialog();
                dismissProgressDialog();
            }
        }, 5000);
    }


}
