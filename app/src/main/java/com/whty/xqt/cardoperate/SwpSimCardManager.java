package com.whty.xqt.cardoperate;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.whty.xqt.bean.TradeRecordBean;
import com.whty.xqt.cons.AppConst;
import com.whty.xqt.utils.CommonMethods;
import com.whty.xqt.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SwpSimCardManager extends AbsCardManager {
    private static final String TAG = "SwpSimCardManager";

    private SimCardOperation simOperation;
    private static SwpSimCardManager swpSimCardManager = null;

    public static SwpSimCardManager getSwpSimCardManager(Context context) {
        if (swpSimCardManager == null) {
            swpSimCardManager = new SwpSimCardManager(context);
        }
        return swpSimCardManager;
    }

    private SwpSimCardManager(Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            simOperation = SimCardOperation2.getSimOperation(context);
        }else {
            // TODO: 2019/12/30 类库的有效性检查，反射方法 
            simOperation = SimCardOperation.getSimOperation(context);
        }

    }

    @Override
    public void initCard() {
        simOperation.openConnection();
    }

    @Override
    public void closeCard() {
        simOperation.closeConnection();
    }

    @Override
    public String getSeId() {
        String seId = "";
        closeSessionAndChannel();
        int artTag = simOperation.getCardAtr();
        // 复位成功，发送选应用指令
        if (artTag == 0) {
            String apdu = "80ca004400";
            short flag1 = sendApdu("D1560001010001600000000100000000", apdu);
            apduResponseBytesToStr();
            if (flag1 == 0 && responseStr.endsWith("9000")) {
                seId = responseStr.substring(10, responseStr.length() - 4);
            }
        }
        closeSessionAndChannel();
        Log.d("www", "getSeId: "+seId);
        return seId;
    }

    @Override
    public String getATS() {
        int artTag = simOperation.getCardAtr();
        String ats = "";
        closeSessionAndChannel();
        // 复位成功，发送选应用指令
        if (artTag == 0) {
        }
        closeSessionAndChannel();
        return ats;
    }

    @Override
    public boolean cardConnected() {
        return simOperation.serviceConnected();
    }

    @Override
    public boolean judgeAppInstalled(String cardAppAid) {
        boolean flag = false;
        int atrTag = simOperation.getCardAtr();
        if (atrTag == 0) {
        }
        return flag;
    }

    @Override
    public double getCardBalance(String cardAppAid) {
        closeSessionAndChannel();
        double money = 0.00;
        int artTag = simOperation.getCardAtr();
        String apdu = "";
        if (artTag == 0) {
            apdu = "805C000204";
            short selectAppTag = sendApdu(cardAppAid, apdu);
            apduResponseBytesToStr();
//            Log.e(TAG, "余额:" + responseStr);
            Log.e("www", "余额:" + responseStr);
            if (selectAppTag == 0 && responseStr.endsWith("9000")) {
                money = convertResToBalance(responseStr);
            }
        }
        closeSessionAndChannel();
        Log.d("www", "getCardArt: "+artTag);
        Log.d("www", "getCardBalance: "+money);
        return money;
    }

    public String getCardBalanceHex(String cardAppAid) {
        closeSessionAndChannel();
        String money = "";
        int artTag = simOperation.getCardAtr();
        String apdu = "";
        if (artTag == 0) {
            apdu = "805C000204";
            short selectAppTag = sendApdu(cardAppAid, apdu);
            apduResponseBytesToStr();
//            Log.e(TAG, "余额:" + responseStr);
            if (selectAppTag == 0 && responseStr.endsWith("9000")) {
                money = responseStr.substring(0, 8);
            }
        }
        closeSessionAndChannel();
        return money;
    }

    public String getCardNumber(String cardAppAid) {
        closeSessionAndChannel();
        String cardNum = "";
        String apdu = "";
        int artTag = simOperation.getCardAtr();
        if (artTag == 0) {
            apdu = "00b0950a0a";
            short selectAppTag = sendApdu(cardAppAid, apdu);
            apduResponseBytesToStr();
            if (selectAppTag == 0 && responseStr.endsWith("9000")) {
                responseStr = responseStr.substring(0, responseStr.length() - 4);
                // 市民卡号，计算得出
                if (responseStr.startsWith("0")) {
                    cardNum = responseStr.substring(1, 20);
                } else if (responseStr.endsWith("f") || responseStr.endsWith("F")) {
                    cardNum = responseStr.substring(0, 19);
                }
            }
        }
        closeSessionAndChannel();
        Log.d("www", "getCardNumber: "+cardNum);
        return cardNum;
    }


    /**
     * 充值 记录
     *
     * @param cardAppAid
     * @return
     */
    public List<TradeRecordBean> getRechargeHistory(String cardAppAid) {
        closeSessionAndChannel();
        List<TradeRecordBean> QClist = new ArrayList<>();
        int artTag = simOperation.getCardAtr();
        if (artTag == 0) {
        } else {
            return null;
        }
        String apdu = "00A40000020018";
        short selectAppTag = sendApdu(cardAppAid, apdu);
        apduResponseBytesToStr();
        if (selectAppTag == 0 && responseStr.endsWith("9000")) {
            for (int i = 0; i < 10; i++) {// 读取最近10条交易记录
                String index = CommonMethods.Int2HexStr(i + 1, 2);
                selectAppTag = sendApdu(cardAppAid, "00b2" + index + "c400");
                apduResponseBytesToStr();
                if (selectAppTag == 0 && responseStr.endsWith("9000")) {
                    TradeRecordBean history = new TradeRecordBean();
                    String tradeNum = responseStr.substring(0, 4);
                    String tradeType = responseStr.substring(18, 20);
                    String posid = responseStr.substring(20, 32);
                    String tradeDate = responseStr.substring(32, 40);
                    String tradeTime = responseStr.substring(40, 44);
                    String tradeMoney = responseStr.substring(10, 18);
                    if (tradeMoney.equals("00000000")) {
                        continue;
                    }
                    tradeMoney = CommonMethods.convertResToBalance(tradeMoney);
                    StringBuilder sb = new StringBuilder(tradeDate + tradeTime);
                    sb.insert(4, "-");
                    sb.insert(7, "-");
                    sb.insert(10, " ");
                    sb.insert(13, ":");
                    history.tradeCount = tradeNum;
                    history.posid = posid;
                    history.tradeType = tradeType;
                    history.tradeTime = sb.toString();
                    history.tradeMoney = tradeMoney;
                    QClist.add(history);
                } else {
                    return QClist;
                }
            }
        } else {
            return null;
        }
        return QClist;
    }

    public String getRechargeHistory() {
        closeSessionAndChannel();
        StringBuilder QClist = new StringBuilder();
        int artTag = simOperation.getCardAtr();
        if (artTag == 0) {
        } else {
            return null;
        }
        for (int i = 0; i < 10; i++) {// 读取最近10条交易记录
            String index = CommonMethods.Int2HexStr(i + 1, 2);
            short selectAppTag = sendApdu(AppConst.APP_CARD_AID, "00b2" + index + "c400");
            apduResponseBytesToStr();
//            Log.e("getRechargeHistory", responseStr);
            if (selectAppTag == 0 && responseStr.endsWith("9000")) {
                QClist.append(responseStr);
                QClist.append(",");
            } else {
                if (QClist.length() == 0) {
                    return responseStr;
                }
                return QClist.toString();
            }
        }

        return QClist.toString();
    }

    @Override
    public void closeSessionAndChannel() {
        simOperation.closeChannelAndSession();
    }

    // 向SWP-SIM卡发送APDU指令
    public short sendApdu(String isdAid, String apdu) {
        byte[] aid = CommonMethods.str2bytes(isdAid);
        try {
            apduBytes = CommonMethods.str2bytes(apdu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (apduBytes == null) {
            return -1;
        }
        apduLength = (short) apduBytes.length;
        resBuf = new byte[512];
        resLenBuf = new short[1];
        short flag = simOperation.sendApdu(aid, apduBytes, apduLength, resBuf, resLenBuf);
        byte[] data = new byte[resLenBuf[0]];
        System.arraycopy(resBuf, 0, data, 0, resLenBuf[0]);
        Log.e(TAG, "apdu:" + apdu + ", apdu res: " + CommonMethods.bytesToHexString(data));
        return flag;
    }

    public short openChannel(String isdAid) {
        byte[] aid = CommonMethods.str2bytes(isdAid);
        return simOperation.openLogicChannel(aid);
    }

    @Override
    public short getCardAtr() {
        return (short) simOperation.getCardAtr();
    }


    /**
     * 8050返回6个字段 ：原交易金额，交易序号（计数器），密钥版本号，算法标识，伪随机数，mac1
     *
     * @param money
     * @param posid
     * @return
     * @throws IOException
     */
    public String[] getRecharge8050Resp(String money, String posid) {
        int artTag = simOperation.getCardAtr();
        if (artTag == 0) {
            String apdu = "805000020b" + "01" + CommonMethods.longToHex(Long.parseLong(money), 8) + posid;
            short selectAppTag = sendApdu(AppConst.APP_CARD_AID, apdu);
            apduResponseBytesToStr();
            if (selectAppTag == 0 && responseStr.endsWith("9000")) {
                // 返回值1-4字节为交易前余额
                String moneyBeforeTrade = responseStr.substring(0, 8);
                // 5-6字节为卡计数器
                String cardCounter = responseStr.substring(8, 12);
                String cardKeyVer = responseStr.substring(12, 14);
                String computtag = responseStr.substring(14, 16);
                // 9-12字节为伪随机数
                String randomNumber = responseStr.substring(16, 24);
                // 13-16为MAC1
                String mac1 = responseStr.substring(24, 32);
                return new String[]{moneyBeforeTrade, cardCounter, cardKeyVer, computtag, randomNumber, mac1};
            }
        }

        return null;
    }

    public String recharge8052(String txntime, String mac2) {
        int artTag = simOperation.getCardAtr();
        if (artTag == 0) {
            String apdu = "805200000b" + txntime + mac2;
            short selectAppTag = sendApdu(AppConst.APP_CARD_AID, apdu);
            apduResponseBytesToStr();
            if (selectAppTag == 0 && responseStr.endsWith("9000")) {
                String tac = responseStr.substring(0, 8);
                Log.e(TAG, "-------> tac : " + tac);
                return tac;
            }
        }
        return "";

    }

    public String getCardRec(String cardAppAid) {
        closeSessionAndChannel();
        StringBuffer sb = new StringBuffer();
        int artTag = simOperation.getCardAtr();
        if (artTag == 0) {
        } else {
            return null;
        }
        String apdu = "00A40000020018";
        short selectAppTag = sendApdu(AppConst.APP_CARD_AID, apdu);
        apduResponseBytesToStr();
        if (selectAppTag == 0 && responseStr.endsWith("9000")) {
            for (int i = 1; i <= 10; i++) {
                String index = CommonMethods.Int2HexStr(i, 2);
                selectAppTag = sendApdu(cardAppAid, "00b2" + index + "c400");
                apduResponseBytesToStr();
                if (selectAppTag == 0 && responseStr.endsWith("9000")) {
                    if (responseStr.startsWith("000000000000000000000000"))
                        continue;
//                    if (responseStr.substring(18, 20).equals("02")) {
                    sb.append(responseStr);
                    sb.append(",");
//                    }
                }else {
                    break;
                }
            }

        }
        return sb.toString();
    }

    /**
     * 激活
     *
     * @param apdus
     */
    public String active(String appaid, String[] apdus) {
        String responseAll = "";
        String index = "";
        for (int i = 0; i < apdus.length; i++) {
            String[] strings = apdus[i].split("\\|");
            String temp_apdu = strings[1];
            if (temp_apdu.equalsIgnoreCase("RESET")) {
                closeSessionAndChannel();
                continue;
            } else if (temp_apdu.startsWith("00a40400") || temp_apdu.startsWith("00A40400")) {
                // 个人化中，创建3f01后紧接着创建3f02时会报6982，解决此问题可以在创建完3f01后关闭通道，然后再去创建3f02
                appaid = temp_apdu.substring(10);
                closeSessionAndChannel();
                continue;
            }
            Log.i("88888888", "写卡指令: "+temp_apdu);
            short selectAppTag = sendApdu(appaid, temp_apdu);
            Log.i("88888888", "写卡指令返回tag: "+selectAppTag);
            apduResponseBytesToStr();
            Log.i("88888888", "写卡指令返回responseStr: "+responseStr);
            index = strings[0];
            if (selectAppTag == 0) {
                if (responseStr.endsWith("9000") || responseStr.startsWith("60") || responseStr.startsWith("61")) {
                    responseAll = index + "|" + responseStr;
                } else {
                    return index + "|" + responseStr;
                }
            } else {
                return index + "|" + selectAppTag;
            }

        }
        return responseAll;
    }

    public String getRandom() {
        int artTag = simOperation.getCardAtr();
        if (artTag == 0) {
        } else {
            return null;
        }
        String apdu = "0084000004";
        short selectAppTag = sendApdu(AppConst.APP_CARD_AID, apdu);
        apduResponseBytesToStr();
        if (selectAppTag == 0 && responseStr.endsWith("9000")) {
            return responseStr.substring(0, 8) + "00000000";
        } else {
            return null;
        }
    }

    public String updateFile(String apdu) {
        String result;
        short selectAppTag = sendApdu(AppConst.APP_CARD_AID, apdu);
        apduResponseBytesToStr();
        if (selectAppTag == 0 && responseStr.endsWith("9000")) {
            result = "00";
            return result;
        } else {
            result = "01";
            return result;
        }
    }

    public String getCityCode(String cardAppAid) {
        closeSessionAndChannel();
        String cityCode = "";
        String apdu = "";
        int artTag = simOperation.getCardAtr();
        if (artTag == 0) {
            apdu = "00b097003c";
            short selectAppTag = sendApdu(cardAppAid, apdu);
            apduResponseBytesToStr();
            if (selectAppTag == 0 && responseStr.endsWith("9000")) {
                // 市民卡号，计算得出
                cityCode = responseStr.substring(12, 16);
            }
        }
        closeSessionAndChannel();
        return cityCode;
    }

    public String getTradeStatus() {
        int artTag = simOperation.getCardAtr();
        if (artTag == 0) {
        } else {
            return null;
        }
        short selectAppTag = sendApdu(AppConst.APP_CARD_AID, "00B201D480");
        apduResponseBytesToStr();
        if (selectAppTag == 0 && responseStr.endsWith("9000")) {
            return responseStr.substring(28, 30);
        }
        return null;
    }
}



