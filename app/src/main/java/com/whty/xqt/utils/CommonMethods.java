package com.whty.xqt.utils;

import android.content.Context;
import android.nfc.NfcAdapter;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


/**
 * 通用工具类,封装了很多常用方法
 */
public class CommonMethods {

    public static String getTwoDouble(Double d) {
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        String s = decimalFormat.format(d);
        return s;
    }

    /**
     * byte字节数组转换Short类型（未严格测试）
     *
     * @param outBuf
     * @return
     */
    public static short bytesToShort(byte[] outBuf) {

        if (outBuf.length < 2) {
            return (short) (outBuf[0] < 0 ? outBuf[0] + 256 : outBuf[0]);
        } else {
            return (short) (((outBuf[0] < 0 ? outBuf[0] + 256 : outBuf[0]) << 8) + (outBuf[1] < 0 ? outBuf[1] + 256
                    : outBuf[1]));
        }

    }

    /**
     * 填充XX数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0xXX到数据块的右边，直到数据块的长度是8的倍数。
     *
     * @param data 待填充XX的数据
     * @return
     */
    public static String padding(String data, String inData) {
        int padlen = 8 - (data.length() / 2) % 8;
        if (padlen != 8) {
            String padstr = "";
            for (int i = 0; i < padlen; i++)
                padstr += inData;
            data += padstr;
            return data;
        } else {
            return data;
        }
    }

    /**
     * 填充80数据，首先在数据块的右边追加一个
     * '80',如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
     *
     * @param data 待填充80的数据
     * @return
     */
    public static String padding80(String data) {
        int padlen = 8 - (data.length() / 2) % 8;
        String padstr = "";
        for (int i = 0; i < padlen - 1; i++)
            padstr += "00";
        data = data + "80" + padstr;
        return data;
    }

    /**
     * 获取当前时间相隔N天的日期,格式yyyymmdd
     *
     * @param distance 和今天的间隔天数
     * @return 获取的日期, 格式yyyymmdd
     */
    public static String getDateString(int distance) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, distance);
        //
        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }

    public static String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间
        return date;
    }

    public static String getDateString(String date) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        Date mDate;
        String dateFormat = "";
        try {
            mDate = df1.parse(date);

            SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
            dateFormat = df2.format(mDate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dateFormat;
    }

    public static String getDateString02(String date) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
        Date mDate;
        String dateFormat = "";
        try {
            mDate = df1.parse(date);

            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
            dateFormat = df2.format(mDate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dateFormat;
    }

    public static String getDateString03(String date) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
        Date mDate;
        String dateFormat = "";
        try {
            mDate = df1.parse(date);

            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            dateFormat = df2.format(mDate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dateFormat;
    }

    public static String formatDateHZ(String date) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
        Date mDate;
        String dateFormat = "";
        try {
            mDate = df1.parse(date);

            SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// 设置日期格式
            dateFormat = df2.format(mDate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dateFormat;
    }

    /**
     * 生成16位的动态链接库鉴权十六进制随机数字符串
     *
     * @return String
     */
    public static String yieldHexRand() {
        StringBuffer strBufHexRand = new StringBuffer();
        Random rand = new Random(System.currentTimeMillis());
        int index;
        // 随机数字符
        char charArrayHexNum[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0; i < 16; i++) {
            index = Math.abs(rand.nextInt()) % 16;
            if (i == 0) {
                while (charArrayHexNum[index] == '0') {
                    index = Math.abs(rand.nextInt()) % 16;
                }
            }
            strBufHexRand.append(charArrayHexNum[index]);
        }
        return strBufHexRand.toString();
    }

    /**
     * 分析类名
     *
     * @param strName String
     * @return String
     */
    public static String analyseClassName(String strName) {
        String strTemp = strName.substring(strName.lastIndexOf(".") + 1,
                strName.length());
        return strTemp.substring(strTemp.indexOf(" ") + 1, strTemp.length());
    }

    static public String convertInt2String(int n, int len) {
        String str = String.valueOf(n);
        int strLen = str.length();

        String zeros = "";
        for (int loop = len - strLen; loop > 0; loop--) {
            zeros += "0";
        }

        if (n >= 0) {
            return zeros + str;
        } else {
            return "-" + zeros + str.substring(1);
        }
    }

    static public int convertString2Int(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * yyyyMMddhhmmss
     */
    public static String getDateTimeString2() {
        Calendar cal = Calendar.getInstance();

        return new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss")
                .format(cal.getTime());

    }

    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuffer buff = new StringBuffer();
        int len = bytes.length;
        for (int j = 0; j < len; j++) {
            if ((bytes[j] & 0xff) < 16) {
                buff.append('0');
            }
            buff.append(Integer.toHexString(bytes[j] & 0xff));
        }
        return buff.toString();
    }

    /**
     * 16进制字符串转为byte[]
     * usage: str2bytes("0710BE8716FB"); it will return a byte array, just like
     * : b[0]=0x07;b[1]=0x10;...b[5]=0xfb;
     */
    public static byte[] str2bytes(String src) {
        if (src == null || src.length() == 0 || src.length() % 2 != 0) {
            return null;
        }
        int nSrcLen = src.length();
        byte byteArrayResult[] = new byte[nSrcLen / 2];
        StringBuffer strBufTemp = new StringBuffer(src);
        String strTemp;
        int i = 0;
        while (i < strBufTemp.length() - 1) {
            strTemp = src.substring(i, i + 2);
            byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp, 16);
            i += 2;
        }
        return byteArrayResult;
    }

    public static int strcpy(byte d[], byte s[], int from, int maxlen) {
        int i;
        for (i = 0; i < maxlen; i++) {
            d[i + from] = s[i];
        }

        d[i + from] = 0;
        return i;
    }

    public static int memcpy(byte d[], byte s[], int from, int maxlen) {
        int i;
        for (i = 0; i < maxlen; i++) {
            d[i + from] = s[i];
        }
        return i;
    }

    public static void BytesCopy(byte[] dest, byte[] source, int offset1,
                                 int offset2, int len) {
        for (int i = 0; i < len; i++) {
            dest[offset1 + i] = source[offset2 + i];
        }
    }

    /**
     * usage: input: n = 1000000000 ( n = 0x3B9ACA00) output: byte[0]:3b
     * byte[1]:9a byte[2]:ca byte[3]:00 notice: the scope of input integer is [
     * -2^32, 2^32-1] ; **In CMPP2.0,the typeof msg id is ULONG,so,need
     * ulong2Bytes***
     */
    public static byte[] int2Bytes(int n) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(n);
        return bb.array();
    }

    public static byte[] long2Bytes(long l) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(l);
        return bb.array();
    }

    /**
     * 将16进制余额值转化为10进制以元为单位
     *
     * @param responseStr
     * @return
     */
    public static String convertResToBalance(String responseStr) {
        double balance = 0;
        String d = "0.00";
        try {
            // 去掉后面的9000
            String balanceStr = "";
            if (responseStr.endsWith("9000")) {
                balanceStr = responseStr.substring(0, 8);
            } else {
                balanceStr = responseStr;
            }
            balance = Integer.parseInt(balanceStr, 16);
            // 解析出来是以分为单位，所以要除以100
            balance = balance / 100;
            DecimalFormat df = new DecimalFormat("#####0.00");   //保留小数点后两位
            d = df.format(balance);//double类型保留不到0.00位数，只能用string
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 将整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
     *
     * @param val int 待转换整数
     * @param len int 指定长度
     * @return String
     */
    public static String Int2HexStr(int val, int len) {
        String result = Integer.toHexString(val).toUpperCase();
        int r_len = result.length();
        if (r_len > len) {
            return result.substring(r_len - len, r_len);
        }
        if (r_len == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - r_len; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    public static String Long2HexStr(long val, int len) {
        String result = Long.toHexString(val).toUpperCase();
        int r_len = result.length();
        if (r_len > len) {
            return result.substring(r_len - len, r_len);
        }
        if (r_len == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - r_len; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    public static String getResString(Context context, int stringId) {
        return context.getResources().getString(stringId);
    }

    /**
     * 字符串转换为字节数组
     * <p>
     * stringToBytes("0710BE8716FB"); return: b[0]=0x07;b[1]=0x10;...b[5]=0xfb;
     */
    public static byte[] stringToBytes(String string) {
        if (string == null || string.length() == 0 || string.length() % 2 != 0) {
            return null;
        }
        int stringLen = string.length();
        byte byteArrayResult[] = new byte[stringLen / 2];
        StringBuffer sb = new StringBuffer(string);
        String strTemp;
        int i = 0;
        while (i < sb.length() - 1) {
            strTemp = string.substring(i, i + 2);
            byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp, 16);
            i += 2;
        }
        return byteArrayResult;
    }

    /**
     * 字节数组转为16进制
     *
     * @param bytes 字节数组
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuffer buff = new StringBuffer();
        int len = bytes.length;
        for (int j = 0; j < len; j++) {
            if ((bytes[j] & 0xff) < 16) {
                buff.append('0');
            }
            buff.append(Integer.toHexString(bytes[j] & 0xff));
        }
        return buff.toString();
    }

    /**
     *
     * ********************************************************************<br>
     * 方法功能：将用户圈存金额先转换为分，在把分转为16进制，再前补0组装为4字节圈存金额 如1元 为:"00000064" 参数说明：<br>
     * 作 者：杨明<br>
     * 开发日期：2013-9-18 上午11:53:56<br>
     * 修改日期：<br>
     * 修改人：<br>
     * 修改说明：<br>
     * ********************************************************************<br>
     */
    /**
     * 将长整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
     *
     * @param val int 待转换长整数
     * @param len int 指定长度
     * @return String
     */
    public static String longToHex(long val, int len) {
        String result = Long.toHexString(val).toUpperCase();
        int rLen = result.length();
        if (rLen > len) {
            return result.substring(rLen - len, rLen);
        }
        if (rLen == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - rLen; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }


    public static boolean nfcEnable(Context context) {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (null != nfcAdapter) {
            // 设备不支持NFC
            return true;
        }
        // if (!nfcAdapter.isEnabled()) {
        // //请在系统设置中先启用NFC功能！
        // return ;
        // }
        return false;
    }

}
