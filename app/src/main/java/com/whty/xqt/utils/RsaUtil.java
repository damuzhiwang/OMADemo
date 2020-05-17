package com.whty.xqt.utils;


import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RsaUtil {
	//model值
	private static String privateKeyN = "B9028778C1A02BE6D89CC9128406D425246A60B9A5F604D0B1E3C7F9DDFAD1061DAA8DB08234E528B708F1A20B126FD7B04B93A2BD6255B042756F5AAB5AD6EE3A0A8C8815E2819A8E7F224D4E1E34F6B1F5BA30C822B17545362109EB29CC096FAC9384EA16A40978B7B4528C66028A60CCA104D6719735ACC45487F482775F";
//	private static String privateKeyN = "CEF2FB7283D9B8A3E4AF17C65FC4FB061E4A9091C3DE76A77B61D9AC7B6D55D38B271242D5F8E8D842631DBCFF3D0D8FF3181A3227C9FD95966F5946F005B1B0BF1E8A50AE0F0BB45A814A80FD7CF80D75931560D1F0BAD6397F979CF5F27D4ACB96D7E9064186B042672CA1D94884ED343EB220A280BFFCB9F225D6D6D2D519";
			//公钥
	private static String privateKeyE = "10001";// 私钥指数
	
	/**
     * RSA签名
     *
     * @param N    RSA的模modulus
     * @param E    RSA公钥的指数exponent
     * @param D    RSA私钥的指数exponent
     * @param data 输入数据(必须为8的倍数)
     * @param mode 0-加密，1-解密
     * @param type 0-私钥加密，公钥解密 1-公钥加密，私钥解密
     * @return 签名后的数据 为null表示操作失败
     */
    public static String generateRSA(String N, String E, String D, String data,
                                     int mode, int type)
    {
        try
        {
            // 判断加密还是解密
            int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
                    : Cipher.DECRYPT_MODE;

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            BigInteger big_N = new BigInteger(N);
            Key key = null;

            if (mode != type)
            { // 生成公钥
                BigInteger big_E = new BigInteger(E);
                KeySpec keySpec = new RSAPublicKeySpec(big_N, big_E);
                key = keyFactory.generatePublic(keySpec);
            }
            else
            { // 生成私钥
                BigInteger big_D = new BigInteger(D);
                KeySpec keySpec = new RSAPrivateKeySpec(big_N, big_D);
                key = keyFactory.generatePrivate(keySpec);
            }

            // 获得一个RSA的Cipher类，使用私钥加密
            Cipher cipher = Cipher.getInstance("RSA"); // RSA/ECB/PKCS1Padding

            // 初始化
            cipher.init(opmode, key);

            // 返回加解密结果
            return (bytesToHexString(cipher.doFinal(str2bytes(data))))
                    .toUpperCase();// 开始计算
        }
        catch (Exception e)
        {
            //logger.info(e.getMessage());
        }
        return null;
    }
    
    
    /**
	 * 将byte数组转换�?6进制组成的字符串 例如 �?��byte数组 b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
	 * byte2hex(b); 将返回一个字符串"0710BE8716FB"
	 * 
	 * @param bytes
	 *            待转换的byte数组
	 * @return
	 */
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
	 * �?6进制组成的字符串转换成byte数组 例如 hex2Byte("0710BE8716FB"); 将返回一个byte数组
	 * b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
	 * 
	 * @param src
	 *            待转换的16进制字符�?
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
	
	/**
	 * RSA加密数据
	 * @param data
	 * @return
	 */
	public static String encryData(String data){
		BigInteger xd = new BigInteger(privateKeyE, 16);
		BigInteger xn = new BigInteger(privateKeyN, 16);
		String encryData = generateRSA(xn + "", xd + "", xd + "",data, 0, 1);
//		System.out.println("加密:" + encryData);
		return encryData;
	}
	
	/**
	 * RSA解密数据
	 * @param data
	 * @return
	 */
	public static String decryData(String data){
		BigInteger xd = new BigInteger(privateKeyE, 16);
		BigInteger xn = new BigInteger(privateKeyN, 16);
		String decryData = generateRSA(xn + "", xd + "", xd + "",data, 1, 1);
//		System.out.println("加密:" + encryData);
		return decryData;
	}
	
	public static void main(String[] args) {
		/***************公钥加密****************/
		//model值
		String privateKeyN = "B9028778C1A02BE6D89CC9128406D425246A60B9A5F604D0B1E3C7F9DDFAD1061DAA8DB08234E528B708F1A20B126FD7B04B93A2BD6255B042756F5AAB5AD6EE3A0A8C8815E2819A8E7F224D4E1E34F6B1F5BA30C822B17545362109EB29CC096FAC9384EA16A40978B7B4528C66028A60CCA104D6719735ACC45487F482775F";
		//公钥
		String privateKeyE = "10001";// 私钥指数

		BigInteger xd = new BigInteger(privateKeyE, 16);
		BigInteger xn = new BigInteger(privateKeyN, 16);
		String f4 = generateRSA(xn + "", xd + "", xd + "",
				"12345678", 0, 1);
		System.out.println("加密:" + f4);
		
		
		
		/***************私钥解密****************/
		//model值
		String privateKeyN1 = "B9028778C1A02BE6D89CC9128406D425246A60B9A5F604D0B1E3C7F9DDFAD1061DAA8DB08234E528B708F1A20B126FD7B04B93A2BD6255B042756F5AAB5AD6EE3A0A8C8815E2819A8E7F224D4E1E34F6B1F5BA30C822B17545362109EB29CC096FAC9384EA16A40978B7B4528C66028A60CCA104D6719735ACC45487F482775F";
		//私钥
		String privateKeyD = "BA90BA5C23A5899FF8BFE264318F514F4331A2E8935B4BEFC7F3932D7D93F844DA95AADC5BF14B458B8E115CFF1D507B88E42DB4E695CB233B61813145AB0D20E7027ABD3890562CC7BA1B3F6518F08BB86F1642DD17B49E4D8749AFE84CE00A07FDDE22CB8E74497EC8A4C64ED10AAD45CA6A51AF8691FB8E0C21C3006A761";
		// 公钥指数

		BigInteger xd1 = new BigInteger(privateKeyD, 16);
		BigInteger xn1 = new BigInteger(privateKeyN1, 16);
		String SK = generateRSA(xn1 + "", xd1 + "", xd1 + "", f4, 1,
				1);//
		System.out.println("jie密:" + SK);
	}
}
