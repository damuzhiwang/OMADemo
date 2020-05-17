package com.whty.xqt.cardoperate;


import com.whty.xqt.utils.CommonMethods;
import com.whty.xqt.utils.LogUtils;

public abstract class AbsCardManager implements ICardManager {
	private static final String TAG = "AbsCardManager";

	/** 存放卡片返回的数据域,分配了512字节的空间,数据域是多少字节就存多少字节 */
	protected byte[] resBuf = new byte[512];
	/** 存放卡片返回的数据域的长度(由于某种特殊原因用数组) */
	protected short[] resLenBuf = new short[1];
	/** byte[]类型的apdu命令 */
	protected byte[] apduBytes;
	/** apdu命令长度,以字节为单位 */
	protected short apduLength;
	/** 执行apdu后返回的数据域(不是整个报文) */
	protected String responseStr;

	@Override
	public void initCard() {

	}

	@Override
	public void closeCard() {

	}

	@Override
	public String getSeId() {
		return getSeId();
	}

	@Override
	public boolean cardConnected() {

		return false;
	}

	@Override
	public boolean judgeAppInstalled(String cardAppAid) {
		return false;
	}

	@Override
	public double getCardBalance(String cardAppAid) {
		return 0;
	}

	/**
	 * SWP-SIM卡用来关闭Session和Channel的方法，SWP-SD卡不需要
	 */
	public abstract void closeSessionAndChannel();

	/**
	 * 将apdu指令由String类型转成byte[],并求得长度.分别作为jni方法的参数.
	 * 
	 * @param apdu
	 */
	public void apduStrToBytes(String apdu) {
		apduBytes = CommonMethods.stringToBytes(apdu);
		apduLength = (short) apduBytes.length;
	}

	/**
	 * 取出resBuf里面的数据域并转化成String
	 */
	public void apduResponseBytesToStr() {
		byte[] data = new byte[resLenBuf[0]];
		System.arraycopy(resBuf, 0, data, 0, resLenBuf[0]);
		responseStr = CommonMethods.bytesToHex(data);
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：将查询余额指令获取到的返回值转换为十进制的余额信息<br>
	 * 参数：查询余额指令获取到的返回值<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-6-24 下午2:48:01<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	public double convertResToBalance(String responseStr) {
		double balance = 0;
		try {
			// 去掉后面的9000
			String balanceStr = responseStr.substring(0,
					responseStr.length() - 4);
//			LogUtil.e(TAG, "16进制余额值为：  " + balanceStr);
			balance = Integer.parseInt(balanceStr, 16);
//			LogUtil.e(TAG, "10进制未处理的余额值为：  " + balance);
			balance = (double) balance / 100;
//			LogUtil.e(TAG, "10进制处理后的余额值为：  " + balance);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LogUtils.e(TAG, "转换余额异常 ");
		}
		return balance;
	}

	public String getApduRes() {
		return responseStr;
	}

	public byte[] getResBytes() {
		return resBuf;
	}

	public short[] getResLen() {
		return resLenBuf;
	}
}
