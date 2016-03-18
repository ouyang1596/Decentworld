/**
 * 
 */
package cn.sx.decentworld.utils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cn.sx.decentworld.common.Base64;

/**
 * @ClassName: MsgVerify.java
 * @Description: 消息认证，保证消息安全传输
 * @author: cj
 * @date: 2016年1月11日 下午10:17:12
 */
public class MsgVerify
{

    /**
     * 获得随机数
     * @return
     */
    public static String getSalt()
    {
        int length = 16;
        Random random = new Random();
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 得到私钥
     * 
     * @param key
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String key) throws Exception
    {

        byte[] keyBytes;

        keyBytes = Base64.decode(key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        RSAPublicKey publickey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

        return publickey;
    }

//    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception
//    {
////      Cipher cipher = Cipher.getInstance("RSA");
//        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        // 模长
//        int key_len = publicKey.getModulus().bitLength() / 8;
//        // 加密数据长度 <= 模长-11
//        String[] datas = splitString(data, key_len - 11);
//        String mi = "";
//        // 如果明文长度大于模长-11则要分组加密
//        for (String s : datas)
//        {
//            mi += bcd2Str(cipher.doFinal(s.getBytes()));
//        }
//        return mi;
//    }
    
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)  
            throws Exception {  
        if (publicKey == null) {  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
//            cipher = Cipher.getInstance("RSA");  
            cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
    }


    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len)
    {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0)
        {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++)
        {
            if (i == x + z - 1 && y != 0)
            {
                str = string.substring(i * len, i * len + y);
            }
            else
            {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes)
    {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++)
        {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }
    

}
