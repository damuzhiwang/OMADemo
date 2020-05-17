package com.whty.xqt.interf;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/3.
 * <p/>
 * 界面基础回调接口
 */
public interface IBaseView<T> {
    /**
     * 通过toast提示用户
     *
     * @param msg        提示的信息
     */
    void showToast(String msg);
//
//    /**
//     * 显示进度
//     */
//    void showProgress();
//
//    /**
//     * 隐藏进度
//     */
//    void hideProgress();

    /**
     * 基础的请求的返回
     *
     * @param jsonObject
     */
    void loadDataSuccess(JSONObject jsonObject);

    /**
     * 基础请求的错误
     *
     * @param describe
     */
    void loadDataError(String describe);
}
