package com.whty.xqt.cardoperate;

public interface ICardManager {
	/**
	 * 初始化卡片
	 */
	public void initCard();

	/**
	 * 取卡片的ATR
	 * 
	 * @return
	 */
	public short getCardAtr();

	/**
	 * 发送APDU
	 * 
	 * @param apdu
	 * @return
	 */
	public short sendApdu(String aid, String apdu);

	/**
	 * 关闭卡片
	 */
	public void closeCard();

	/**
	 * 获取卡片的SeId
	 * 
	 * @return
	 */
	public String getSeId();

	/**
	 * 表示卡片是否连接上
	 * 
	 * @return
	 */
	public boolean cardConnected();

	/**
	 * 判断卡上是否有某一个卡应用
	 * 
	 * @return
	 */
	public boolean judgeAppInstalled(String cardAppAid);

	/**
	 * 获取卡片的余额
	 * 
	 * @param cardAppAid
	 * @return
	 */
	public double getCardBalance(String cardAppAid);

	/**
	 * 获取卡片的卡号
	 * 
	 * @param cardAppAid
	 * @return
	 */
	public String getCardNumber(String cardAppAid);

	/**
	 * 获取ATS
	 * 
	 * @return
	 */
	public String getATS();
}
