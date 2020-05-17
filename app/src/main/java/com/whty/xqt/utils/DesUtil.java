package com.whty.xqt.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class DesUtil {
    private final static String SIGNATURE_ALGORITHM = "SHA1WithRSA";

    public static String sessionKey;

    /**
     * ECB模式中的DES算法(数据不需要填充)
     *
     * @param key  加解密密钥(8个字节)
     * @param data 输入:待加/解密数据(长度必须是8的倍数) 输出:加/解密后的数据
     * @param mode 0-加密，1-解密
     * @return
     */
    public static void des(byte[] key, byte[] data, int mode) {
        try {
            if (key.length == 8) {
                // 判断加密还是解密
                int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
                        : Cipher.DECRYPT_MODE;
                // 生成安全密钥
                SecretKey keySpec = new SecretKeySpec(key, "DES");// key
                // 生成算法
                Cipher enc = Cipher.getInstance("DES/ECB/NoPadding");
                // 初始化
                enc.init(opmode, keySpec);
                // 加解密结果
                byte[] temp = enc.doFinal(data); // 开始计算
                System.arraycopy(temp, 0, data, 0, temp.length); // 将加解密结果复制一份到data
            }
        } catch (Exception e) {
            //logger.info(e.getMessage());
        }
    }

    /**
     * 填充paddingStr数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加到数据块的右边，直到数据块的长度是8的倍数。
     *
     * @param data 待填充的数据
     * @return
     */
    public static String padding(String data, String paddingStr) {
        int padlen = 8 - (data.length() / 2) % 8;
        if (padlen != 8) {
            StringBuffer padstr = new StringBuffer();
            for (int i = 0; i < padlen; i++)
                padstr.append(paddingStr);
            data += padstr;
            return data;
        } else {
            return data;
        }
    }

    /**
     * 填充05数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x05到数据块的右边，直到数据块的长度是8的倍数。
     *
     * @param data 待填充05的数据
     * @return
     */
    public static String padding05(String data) {
        return padding(data, "05");
    }

    /**
     * 填充20数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x20到数据块的右边，直到数据块的长度是8的倍数。
     *
     * @param data 待填充20的数据
     * @return
     */
    public static String padding20(String data) {
        return padding(data, "20");
    }

    /**
     * 填充00数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
     *
     * @param data 待填充00的数据
     * @return
     */
    public static String padding00(String data) {
        return padding(data, "00");
    }

    /**
     * 填充80数据，首先在数据块的右边追加一个'80',如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
     *
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

    /**
     * 将16进制组成的字符串转换成byte数组 例如 hex2Byte("0710BE8716FB"); 将返回一个byte数组
     * b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
     *
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


    /**
     * ECB模式中的DES/3DES/TDES算法(数据不需要填充),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与
     * 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
     *
     * @param key  加解密密钥(8字节:DES算法 16字节:3DES/TDES算法
     *             24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
     * @param data 待加/解密数据(长度必须是8的倍数)
     * @param mode 0-加密，1-解密
     * @return 加解密后的数据 为null表示操作失败
     */
    public static String desecb(String key, String data, int mode) {
        try {

            // 判断加密还是解密
            int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
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
                keySpec = new SecretKeySpec(str2bytes(key + key.substring(0, 16)), "DESede");// 将key前8个字节复制到keyecb的最后8个字节
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
            return (bytesToHexString(enc.doFinal(str2bytes(data))))
                    .toUpperCase();// 开始计算
        } catch (Exception e) {
            //logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 对数据进行加�?
     *
     * @param key  加密的key
     * @param data 要加密的数据
     * @return
     */
    public static String encrypt(String key, String data) {
        String dData = null;
        try {
            if (data != null) {
                byte[] dataByte = data.getBytes();
                String hexStr = bytesToHexString(dataByte);
                String tempData = padding80(hexStr);
                dData = desecb(key, tempData, 0);
            }
        } catch (Exception e) {
            //logger.error("encrypt exception:",e);
        }
        return dData;
    }

    /**
     * 对数据进行解�?
     *
     * @param key  解密的key
     * @param data 要解密的数据
     * @return
     */
    public static String decrypt(String key, String data) {
        String result = null;
        try {
            if (data != null) {
                String value = desecb(key, data.trim(), 1);
                value = unPadding80(value);
                byte[] edata = str2bytes(value);
                result = new String(edata, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            //logger.error("decrypt exception:",e);
        }
        return result;
    }

    /**
     * 逆向去掉�?0
     *
     * @param data
     * @return
     */
    public static String unPadding80(String data) {
        int len = data.length();
        int padlen = (len / 2) % 8;
        if (padlen != 0) {
            return data;
        } else {
            String tempStr = data.substring(data.length() - 16);
            for (int i = 0; i < 8; i++) {
                int start = 2 * i;
                String temp = tempStr.substring(start, start + 2);
                if ("80".equals(temp)) {
                    if (i == 7) {
                        return data.substring(0, len - 2);
                    } else {
                        int x = Integer.parseInt(tempStr.substring(start + 2),
                                16);
                        if (x == 0) {
                            return data.substring(0, len - 16 + start);
                        }
                    }
                }
            }
            return data;
        }
    }

    /**
     * md5加密
     *
     * @param s 待加密字符串
     * @return
     */
    public static String encodeByMD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将byte数组转换�?6进制组成的字符串 例如 �?��byte数组 b[0]=0x07;b[1]=0x10;...b[5]=0xFB; byte2hex(b); 将返回一个字符串"0710BE8716FB"
     *
     * @param bytes 待转换的byte数组
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
        return buff.toString().toUpperCase();
    }


    public static byte[] signature(byte[] plainText, RSAPrivateKey privateKey, String signAlgor) throws Exception {
        if (signAlgor == null || signAlgor.equals("")) {
            signAlgor = SIGNATURE_ALGORITHM;
        }
        Signature signature = Signature.getInstance(signAlgor);
        signature.initSign(privateKey);
        signature.update(plainText);
        byte[] signData = signature.sign();
        return signData;
    }


    public static void main(String args[]) {
//		String str="123";
//		System.out.println(encodeByMD5(str));
//		String key="525DD8AF5EF3B2C9C3D9A564C237CB4C";
//		String data="test你好123";
//		data=encrypt(key,data);
//		System.out.println(data);
        String data = "FD9A5D89DAB75DCD88BF1AD7C35F2F54DD89766AA71CB3738CAEA7D46EB96A050695C1A1F6A211D6F3456F94ACF3BA993454FA2354E40E0491AD40CDC439B936F66EC16ACE224111D915783FDA73D7CF610B0C0EFB48BD51";
        String desecb = decrypt("181D244714EE2A1A4E1E6C344359D89D", data);
        System.out.println(desecb);
    }
}
