package com.whty.xqt.base;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.whty.xqt.MainActivity;
import com.whty.xqt.R;
import com.whty.xqt.cons.AppConst;
import com.whty.xqt.utils.DesUtil;
import com.whty.xqt.utils.LogUtils;
import com.whty.xqt.utils.ToastUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/3/23.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    public static String[][] TECHLISTS; //NFC 标签列表
    public static IntentFilter[] FILTERS; //过滤器

    static {
        try {
            TECHLISTS = new String[][]{{IsoDep.class.getName()}, {NfcA.class.getName()}};
            FILTERS = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED, "*/*")};
        } catch (Exception ignored) {
        }
    }

    private LoadingDialog loadingDialog;
    public ToastUtil toastUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            AppConst.$phoneNum = savedInstanceState.getString("mobile");
            AppConst.seid = savedInstanceState.getString("se_id");
            AppConst.sessionid = savedInstanceState.getString("sessionid");
            DesUtil.sessionKey = savedInstanceState.getString("sessionKey");
        }
        //导航栏
//        Window window = getWindow();
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.setStatusBarColor(Color.WHITE);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .autoDarkModeEnable(true)
                .fitsSystemWindows(true)
                .init();
        setContentView(getLayoutId());

        ButterKnife.bind(this);
        AppManager.getInstance().addActivty(this);
        toastUtil = new ToastUtil(this);
        //初始化nfc适配器
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //初始化卡片信息
        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, FILTERS, TECHLISTS);
            if(this instanceof MainActivity) {
                LogUtils.e("www","");
            } else {
//                NfcManager.checkNfc(this);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
        if(loadingDialog!=null){
            loadingDialog.close();
        }
//        ImmersionBar.with(this).destroy(); //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }


    public abstract void initData();

    public abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();


    public void closeCurrent() {
        AppManager.getInstance().removeCurrent();
    }

    public void closeAll() {
        AppManager.getInstance().clear();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!TextUtils.isEmpty(AppConst.$phoneNum)) {
            outState.putString("mobile", AppConst.$phoneNum);
        }
        if (!TextUtils.isEmpty(AppConst.seid)) {
            outState.putString("se_id", AppConst.seid);
        }
        if (!TextUtils.isEmpty(DesUtil.sessionKey)) {
            outState.putString("sessionKey", DesUtil.sessionKey);
        }
        if (!TextUtils.isEmpty(AppConst.sessionid)) {
            outState.putString("sessionid", AppConst.sessionid);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //获取当前获得当前焦点所在View
            View view = getCurrentFocus();
            if (isClickEt(view, event)) {
                //如果不是edittext，则隐藏键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    //隐藏键盘
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(event);
        }
        /**
         * 看源码可知superDispatchTouchEvent  是个抽象方法，用于自定义的Window
         * 此处目的是为了继续将事件由dispatchTouchEvent(MotionEvent event)传递到onTouchEvent(MotionEvent event)
         * 必不可少，否则所有组件都不能触发 onTouchEvent(MotionEvent event)
         */
        if (getWindow().superDispatchTouchEvent(event)) {
            return true;
        }
        return onTouchEvent(event);
    }

    /**
     * 获取当前点击位置是否为et
     *
     * @param view  焦点所在View
     * @param event 触摸事件
     * @return
     */
    public boolean isClickEt(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            //此处根据输入框左上位置和宽高获得右下位置
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void gotoActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void gotoActivityForResult(Class clazz, Bundle bundle, int requetCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        startActivityForResult(intent, requetCode);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showProgressDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog .setLoadingText(msg)
                .setSuccessText("加载成功")
                .setFailedText("加载失败")
                .closeSuccessAnim()
                .closeFailedAnim()
                .show();
    }

    public void dismissProgressDialog() {
        loadingDialog.close();
    }
    public void dismissSuccessDialog() {
        loadingDialog.loadSuccess();
    }

    public void dismissFailedDialog() {
        loadingDialog.loadFailed();
    }
    public void dismissFailedDialog(String msg) {
        loadingDialog.setFailedText(""+msg).loadFailed();
    }


    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
//        if (resources.getConfiguration().fontScale!=1){
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        }
        return resources;
    }
}
