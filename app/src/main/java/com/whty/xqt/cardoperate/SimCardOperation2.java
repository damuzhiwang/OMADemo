package com.whty.xqt.cardoperate;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.se.omapi.Channel;
import android.se.omapi.Reader;
import android.se.omapi.SEService;
import android.se.omapi.Session;

import androidx.annotation.RequiresApi;

import com.whty.xqt.utils.LogUtils;


import java.util.concurrent.Executor;

public class SimCardOperation2 extends SimCardOperation{
	private static final String TAG = "SimCardOperation2";
	private SEService seService = null;
	private Session session = null;
	private Channel channel = null;
	private Context context;
	private static SimCardOperation2 simCardOperation = null;

	private SimCardOperation2(Context context) {
		this.context = context;
	}

	/**
	 *
	 * ********************************************************************<br>
	 * 方法功能：单例模式获取SimOperation对象<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:24:29<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	public static SimCardOperation2 getSimOperation(Context context) {
		if (simCardOperation == null) {
			simCardOperation = new SimCardOperation2(context);
		}
		return simCardOperation;
	}

	/**
	 *
	 * *************************************************************************
	 * ****<br>
	 * 工程名称: MIDAm二期 <br>
	 * 模块功能：SEService的回调函数，在回调当中初始化SEService和Session<br>
	 * 作 者: 杨明<br>
	 * 单 位：武汉天喻信息 研发中心 <br>
	 * 开发日期：2013-9-9 下午7:24:17 <br>
	 * *************************************************************************
	 * ****<br>
	 */
	@RequiresApi(api = Build.VERSION_CODES.P)
	public class SEServiceCallback implements SEService.OnConnectedListener {

		@Override
		public void onConnected() {
			initSession();
		}
	}


	/**
	 *
	 * ********************************************************************<br>
	 * 方法功能：关闭连接的通道、会话和SEService<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:24:09<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	@RequiresApi(api = Build.VERSION_CODES.P)
	public void closeConnection() {
		if (session != null) {
			session.closeChannels();
			session.close();
			session = null;
		}
		if (seService != null) {
			seService.shutdown();
			seService = null;
		}
	}

	/**
	 *
	 * ********************************************************************<br>
	 * 方法功能：关闭通道和会话<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:24:49<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	@RequiresApi(api = Build.VERSION_CODES.P)
	public void closeChannelAndSession() {
		if (channel != null) {
			channel.close();
			channel = null;
		}
		if (session != null) {
			session.closeChannels();
			session.close();
			session = null;
		}
	}

	/**
	 *
	 * ********************************************************************<br>
	 * 方法功能：初始化会话，可以根据会话获取到通道<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:24:59<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	@RequiresApi(api = Build.VERSION_CODES.P)
	public void initSession() {
		if (seService == null) {
			LogUtils.e(TAG, "seService is not connected");
			return;
		}
		if (session == null) {
			try {
				Reader[] readers = seService.getReaders();
				// 选择Reader，Name中包含SIM或者UICC字符
				for (Reader r : readers) {
					String name = r.getName().toUpperCase();
					if (name.contains("UICC") || name.contains("SIM")) {
						session = r.openSession();
						break;
					}
				}
			} catch (Exception e) {
				LogUtils.e(TAG, "open Session happen exception");
			}
//			Reader[] readers = seService.getReaders();
//			for (Reader reader : readers) {
//				if (!reader.isSecureElementPresent()
//						|| reader.getName().indexOf("SIM") == -1||reader.getName().equalsIgnoreCase("SIM2")) {
//					continue;
//				}
//				try {
//					session = reader.openSession();
//				} catch (Exception e) {
//					Log.e(TAG, "open Session happen exception");
//				}
//				if (session == null) {
//					continue;
//				}
//			}
		}
	}

	/**
	 *
	 * ********************************************************************<br>
	 * 方法功能：判断SEService是否连接上<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:25:07<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	@RequiresApi(api = Build.VERSION_CODES.P)
	public boolean serviceConnected() {

		if (seService == null) {
			return false;
		} else {

			return seService.isConnected();
		}
	}

	/**
	 *
	 * ********************************************************************<br>
	 * 方法功能：打开SEService连接，注册回调函数，等待服务连接上<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:25:24<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	@RequiresApi(api = Build.VERSION_CODES.P)
	public void openConnection() {
			SEServiceCallback seServiceConnection = new SEServiceCallback();
			Executor executor = new Executor() {
				@Override
				public void execute(Runnable command) {

				}
			};
			seService = new SEService(context, executor,seServiceConnection);
		LogUtils.d("www", "openConnection: 9999");
	}

	@RequiresApi(api = Build.VERSION_CODES.P)
	public int getCardAtr() {
		LogUtils.d("www", "getCardAtr: 9.0路线");
		if (session == null) {
			initSession();
		}
		if (session != null) {
			byte[] atrRes = session.getATR();
			// 表示取atr成功
			return 0;
		} else {
			// 表示取atr失败
			return -1;
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.P)
	public short sendApdu(byte[] aid, byte[] apdu, short apduLen,
						  byte[] apduRes, short[] apduResLen) {
		try {
			if (session == null) {
				initSession();
			}
			if (session != null) {
				// 打开通道
				if (channel == null) {
					channel = session.openLogicalChannel(aid);
				}
				if (channel != null && !channel.isOpen()) {
					LogUtils.e(TAG, "channel.isClosed() and reOpen");
					channel = session.openLogicalChannel(aid);
				}
				if (null != channel) {
					LogUtils.e(TAG, "open channel success!!!");
				}
				// 利用通道传输指令，并将返回值赋给传入的参数
				byte[] res = channel.transmit(apdu);
				System.arraycopy(res, 0, apduRes, 0, res.length);
				apduResLen[0] = (short) res.length;
			} else {
				return -1;
			}
		} catch (Exception e) {
			LogUtils.e(TAG, "open channel fail!!!");
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	/**
	 *
	 * ********************************************************************<br>
	 * 方法功能：判断逻辑通道能不能打开，如果能打开，则表明卡上存在该应用，不能则表明不存在该应用<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-12 下午5:04:10<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	@RequiresApi(api = Build.VERSION_CODES.P)
	public short openLogicChannel(byte[] aid) {
		try {
			if (session == null) {
				initSession();
			}
			if (session != null) {
				channel = session.openLogicalChannel(aid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}



}
