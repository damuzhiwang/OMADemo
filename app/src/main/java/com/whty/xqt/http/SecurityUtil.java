package com.whty.xqt.http;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtil {

	/**
	 * 微信通信密钥 通过他对消息加密解密
	 */
	public static final String TRAN_INIT_KEY = "11223344556677889900aabbccddeeff";

	/**
	 * ECB模式中的DES/3DES/TDES算法(数据不需要填充),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与
	 * 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
	 * 
	 * @param key
	 *            加解密密钥(8字节:DES算法 16字节:3DES/TDES算法
	 *            24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
	 * @param data
	 *            待加/解密数据(长度必须是8的倍数)
	 * @param mode
	 *            0-加密，1-解密
	 * @return 加解密后的数据 为null表示操作失败
	 */
	public static String desecb(String key, String data, int mode) {
		try {

			// 判断加密还是解密
			int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
					: Cipher.DECRYPT_MODE;
			SecretKey keySpec = null;
			Cipher enc = null;
			// 判断密钥长度
			if (key.length() == 16) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(str2bytes(key), "DES");// key
				// 生成算法
				enc = Cipher.getInstance("DES/ECB/NoPadding");
			} else if (key.length() == 32) {
				// 计算加解密密钥，即将16个字节的密钥转换成24个字节的密钥，24个字节的密钥的前8个密钥和后8个密钥相同,并生成安全密钥
				keySpec = new SecretKeySpec(str2bytes(key
						+ key.substring(0, 16)), "DESede");// 将key前8个字节复制到keyecb的最后8个字节
				// 生成算法
				enc = Cipher.getInstance("DESede/ECB/NoPadding");
			} else if (key.length() == 48) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(str2bytes(key), "DESede");// key
				// 生成算法
				enc = Cipher.getInstance("DESede/ECB/NoPadding");
			} else {
				//logger.info("Key length is error");
				return null;
			}
			// 初始化
			enc.init(opmode, keySpec);
			// 返回加解密结果
			return (bytesToHex(enc.doFinal(str2bytes(data))))
					.toUpperCase();// 开始计算
		} catch (Exception e) {
			//logger.info(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 将16进制组成的字符串转换成byte数组 例如 hex2Byte("0710BE8716FB"); 将返回一个byte数组
	 * b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
	 * @param src 待转换的16进制字符串
	 * @return
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

//	/**
//	 * 去掉填充的80和00
//	 * 
//	 * @param data
//	 *            填充80的数据
//	 * @return
//	 */
//	public static String subbing80(String data) {
//		for (int i = 1; i <= 4; i++) {
//			int lengthIndex00 = data.lastIndexOf("00");
//			if (lengthIndex00 == data.length() - 2) {
//				data = data.substring(0, lengthIndex00);
//			}
//		}
//		data = data.substring(0, data.length() - 2);
//		byte[] dataByte = hexToBytes(data);
//		data = new String(dataByte);
//		return data;
//	}
	
	 /**
     *	去掉填充的80和00
     * @param data 填充80的数据
     * @return
     */
    public static String subbing80(String data) {
    	for (int i = 1; i <= 8; i++) {
			int lengthIndex00 = data.lastIndexOf("00");
			if (lengthIndex00 >= data.length()-16 && lengthIndex00 <= data.length()) {
				if (lengthIndex00 == data.length() - 2) {
					data = data.substring(0,lengthIndex00);
				}
			}
			
		}
    	data = data.substring(0,data.length()-2);
    	byte[] dataByte = hexToBytes(data);
    	data = new String(dataByte);
    	return data;
    }

	
	/**
	   * @param hex
	   *            每两个字节进行处理
	   * @return
	   */
	  public static byte[] hexToBytes(String hex) {
	    byte[] buffer = new byte[hex.length() / 2];
	    String strByte;

	    for (int i = 0; i < buffer.length; i++) {
	      strByte = hex.substring(i * 2, i * 2 + 2);
	      buffer[i] = (byte) Integer.parseInt(strByte, 16);
	    }

	    return buffer;
	  }
	  /**
	   * 字节数组转为16进制
	   * 
	   * @param bytes
	   *          字节数组
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
	     * 填充80数据，首先在数据块的右边追加一个'80',如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
	     * @param data 待填充80的数据
	     * @return
	     */
	    public static String padding80(String data) {
	        int padlen = 8 - (data.length() / 2) % 8;
	        StringBuffer padstr = new StringBuffer();
	        for (int i = 0; i < padlen - 1; i++)
	            padstr.append("00");
	        data = data + "80" + padstr.toString();
	        return data;
	    }	  
}
