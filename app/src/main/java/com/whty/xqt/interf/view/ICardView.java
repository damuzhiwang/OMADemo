package com.whty.xqt.interf.view;

import com.whty.xqt.bean.TradeRecordBean;

import java.util.List;


/**
 * Created by jiangzhe on 2018/5/8.
 */

public interface ICardView {
//    void prepare(String balance, String mac1, String random, String tradeCount);
//
    void showCardInfo(List<TradeRecordBean> rechargeHistory);
}
