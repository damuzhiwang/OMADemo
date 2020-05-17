package com.whty.xqt.http;



import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2017/8/3.
 */
public class RetrofitUtils<T> {

    private static final long DEFAULT_TIMEOUT = 20000;
    private static RetrofitUtils retrofitUitls;
//    public static final String url = "http://10.8.3.188:8080/nt-server/";
//    public static final String url = "http://s.whty.com.cn/nt-server/";  //高科
//    public static final String url = "http://10.8.3.121:8080/nt-server/";//黄亮
//    public static final String url = "http://s.whty.com.cn/nt-server/";//余根
//    public static final String url = "http://10.8.3.242/nt-server/";//内网242
    public static final String url = "http://183.207.103.144:12005/nt-server/";//生产
//    public static final String url = "http://183.207.103.144:11005/nt-server/";//生产测试
    public static final String AdUrl = "http://txwx.tyjulink.com/ruoyi/Ad2/";//石伟

    public static final HashMap<String, String> TagList = new HashMap<>();

    public static void getTag() {
        TagList.put("pre_charge", "1001");
        TagList.put("charge_confirm", "1002");
        TagList.put("pay", "1003");
        TagList.put("refund", "1004");
        TagList.put("order_list", "1005");
        TagList.put("person", "1006");
        TagList.put("query_pay_result", "1007");
        TagList.put("order_doubt", "1008");
        TagList.put("creat_order_active", "1009");
        TagList.put("get_banner", "1010");
        TagList.put("costs", "1011");
        TagList.put("creat_order_zero", "1012");

        TagList.put("order_active", "1021");
        TagList.put("refund_active", "1022");

        TagList.put("allow_made_invoice_list", "1023");
        TagList.put("made_invoice_record_list", "1024");
        TagList.put("invoice_info", "1025");
        TagList.put("contain_order", "1026");
        TagList.put("resend_invoice", "1027");
        TagList.put("made_invoice", "1028");
        TagList.put("online_preview_invoice", "1029");
        TagList.put("person_cz", "1030");

        TagList.put("pay_hebao_charge", "1033");
        TagList.put("pay_hebao_acitve", "1034");

        TagList.put("query_card_info", "1036");
        TagList.put("update_1A", "1044");
        TagList.put("query_update_1A", "1045");
    }

    public static RetrofitUtils getInstance() {
        if (retrofitUitls == null) {
            retrofitUitls = new RetrofitUtils();
        }
        return retrofitUitls;
    }

    public RetrofitUtils() {
    }

    private Retrofit getRetrofit(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
//        okHttpClient.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        okHttpClient.setReadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        okHttpClient.setWriteTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        okHttpClient.networkInterceptors().add(new HttpLoggingInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public ApiService getService(String argUrl) {
        ApiService apiService;
        if (argUrl != null) {
            apiService = getRetrofit(argUrl).create(ApiService.class);
            Log.i("888888", "getService: "+argUrl);
        } else {
            apiService = getRetrofit(url).create(ApiService.class);
            Log.i("888888", "getService: "+url);
        }

        return apiService;
    }

}
