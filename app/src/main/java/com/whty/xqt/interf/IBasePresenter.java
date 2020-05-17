package com.whty.xqt.interf;

/**
 * Created by Administrator on 2017/8/3.
 */
public interface IBasePresenter<T> {

    /**
     * 开始请求
     */
    void sendRequest(T data);

    /**
     * 请求失败
     *
     * @param e 失败的原因
     */
    void requestError(String e);

    /**
     * 请求结束
     */
    void requestComplete();

    /**
     * 请求成功
     *
     * @param callBack 根据业务返回相应的数据
     */
    void requestSuccess(T callBack);
}
