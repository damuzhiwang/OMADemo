package com.whty.xqt.base;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.whty.xqt.bean.TradeRecordBean;
import com.whty.xqt.cons.AppConst;
import com.whty.xqt.http.ApiService;
import com.whty.xqt.http.RetrofitUtils;
import com.whty.xqt.interf.IBasePresenter;
import com.whty.xqt.interf.IBaseView;
import com.whty.xqt.utils.DesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2017/8/3.
 */
public class BasePresenterImpl<T extends IBaseView, V> implements IBasePresenter<V> {

    public IBaseView iView;

    /**
     * 构造方法
     *
     * @param view 具体业务的接口对象
     */
    public BasePresenterImpl(T view) {
        this.iView = view;
    }

    @Override
    public void sendRequest(Object data) {
//        iView.showProgress();
        sendRequest(data, true, false);
    }

    @Override
    public void requestError(String e) {
        //通知UI具体的错误信息
//        iView.hideProgress();
        iView.loadDataError(e);
    }

    @Override
    public void requestComplete() {
        //隐藏Loading
//        iView.hideProgress();
    }

    @Override
    public void requestSuccess(Object callBack) {
        //将获取的数据回调给UI
        iView.loadDataSuccess((JSONObject) callBack);
    }

    public void onSuccess(String result) throws JSONException {
        Log.d("www", "onSuccess: "+result);
        JSONObject jsonObject = new JSONObject(result);
        String return_code = jsonObject.optString("return_code");
        if (TextUtils.equals("SUCCESS", return_code)) {//接口返回正常
            String result_code = jsonObject.optString("result_code");
            if (TextUtils.equals("SUCCESS", result_code)) {
                requestSuccess(jsonObject);
            } else {
                requestError(jsonObject.optString("error_msg"));
            }
        } else {
            requestError(jsonObject.optString("return_msg"));
        }
    }

    public void sendRequest(Object t, final boolean encode, boolean sign) {
        String data = new Gson().toJson(t);
        Log.e("RetrofitUtils", "request data=" + data);
        ApiService service;
        if (!encode && !sign) {
            service = RetrofitUtils.getInstance().getService(RetrofitUtils.AdUrl);
        }else {
            service = RetrofitUtils.getInstance().getService(null);
        }
        Observable<ResponseBody> observable = null;
        if (!encode && !sign) {
            observable = service.getAd(data);
        } else if (encode && !sign) {
            data = DesUtil.encrypt(DesUtil.sessionKey, data);
            observable = service.sendRequest(data, AppConst.sessionid);
        } else if (!encode && sign) {
            observable = service.sign(data);
        } else {
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String result = responseBody.string();
                            if (encode) {
                                result = DesUtil.decrypt(DesUtil.sessionKey, result);
                            }
                            onSuccess(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                            requestError("数据解析错误！");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RetrofitUtils", "onError==" + e.getMessage());
                        if (TextUtils.equals("unexpected end of stream", e.getMessage())) {
                            requestError("网络不稳定，请重试！");
                        } else {
                            requestError("网络连接异常！");
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.e("RetrofitUtils", "onCompleted==");
                    }
                });
    }



}
