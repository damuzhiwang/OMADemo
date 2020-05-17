package com.whty.xqt.http;



import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Administrator on 2017/7/31.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("interface4mobilefront")
    Observable<ResponseBody> sendRequest(@Field("data") String data, @Field("sessionid") String sessionid);

    @FormUrlEncoded
    @POST("sign")
    Observable<ResponseBody> sign(@Field("data") String data);

    @FormUrlEncoded
    @POST("getAd2")
    Observable<ResponseBody> getAd(@Field("data") String data);
}
