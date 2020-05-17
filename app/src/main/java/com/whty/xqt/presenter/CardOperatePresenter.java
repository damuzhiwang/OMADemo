package com.whty.xqt.presenter;


import com.whty.xqt.bean.TradeRecordBean;
import com.whty.xqt.cardoperate.SwpSimCardManager;
import com.whty.xqt.cons.AppConst;
import com.whty.xqt.interf.view.ICardView;
import com.whty.xqt.utils.CommonMethods;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by jiangzhe on 2018/4/24.
 */

public class CardOperatePresenter {

    private ICardView mView;
    private SwpSimCardManager mSimCardManager;

    public CardOperatePresenter(SwpSimCardManager swpSimCardManager, ICardView view) {
        this.mSimCardManager = swpSimCardManager;
        this.mView = view;
    }

    public void readCardInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TradeRecordBean> rechargeHistory = mSimCardManager.getRechargeHistory(AppConst.APP_CARD_AID);
                mView.showCardInfo(rechargeHistory);
            }
        }).start();
    }

    public String getcardNo(String appCardAid) {
        String cardNumber = mSimCardManager.getCardNumber(appCardAid);
        return cardNumber;
    }

    public String getcardBalance(String appaid) {
        double cardBalance = mSimCardManager.getCardBalance(appaid);
        String balance = CommonMethods.getTwoDouble(cardBalance);
        return balance;
    }

//    public void prepare(final String money) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String cityCode = mSimCardManager.getCityCode(AppConst.APP_CARD_AID);
//                    String[] recharge8050Resp = null;
//                    if (cityCode.equals("3140")) {
//                        recharge8050Resp = mSimCardManager.getRecharge8050Resp(money, AppConst.POS_ID_ZJ);
//                    } else {
//                        recharge8050Resp = mSimCardManager.getRecharge8050Resp(money, AppConst.POS_ID);
//                    }
////                    if (cityCode.equals("3140")){
////                        int count = Integer.parseInt(recharge8050Resp[1],16);
////                        count++;
////                        recharge8050Resp[1] = CommonMethods.Int2HexStr(count, 4);
////                    }
//                    mView.prepare(recharge8050Resp[0], recharge8050Resp[5], recharge8050Resp[4], recharge8050Resp[1]);
//                } catch (Exception e) {
//                    mView.prepare("", "", "", "");
//                }
//            }
//        }).start();
//    }

    public String writeCard(String transtime, String mac2) {
        String tac = "";
        tac = mSimCardManager.recharge8052(transtime, mac2);
        return tac;
    }

    public String getCityCode() {
        String cityCode = mSimCardManager.getCityCode(AppConst.APP_CARD_AID);
        return cityCode;
    }

    public String getTradeStatus() {
        String tradeStatus = mSimCardManager.getTradeStatus();
//        switch (tradeStatus) {
//            case "00":
//                tradeStatus="--";
//                break;
//            case "01":
//                tradeStatus="进站";
//                break;
//            case "02":
//                tradeStatus="出站";
//                break;
//            case "03":
//                tradeStatus="进站更新";
//                break;
//            case "04":
//                tradeStatus="出站更新";
//                break;
//
//        }
        return tradeStatus;
    }

    public short sendApdu() {
        short i = mSimCardManager.openChannel(AppConst.APP_CARD_AID);
        mSimCardManager.closeSessionAndChannel();
        return i;
    }
}
