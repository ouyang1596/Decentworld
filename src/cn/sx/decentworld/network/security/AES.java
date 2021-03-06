package cn.sx.decentworld.network.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * @ClassName: AES.java
 * @Description: AES对称加密解密类
 * @author: yj
 * @date: 2015-7-25 下午11:14:36
 */
public class AES
{

    public static String PRIVATE_KEY = "decentworld$#$#Y";

    /**
     * 加密
     */
    public static String Encrypt(String str)
    {
        return Encrypt(str, PRIVATE_KEY);
    }

    /**
     * 解密
     */
    public static String Decrypt(String str)
    {
        return Decrypt(str, PRIVATE_KEY);
    }

    /**
     * 加密
     * 
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String Encrypt(String str, String key)
    {

        try
        {
            if (str == null || key == null)
                return null;
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8") , "AES"));
            byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解密
     * 
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String Decrypt(String str, String key)
    {
        try
        {
            if (str == null || key == null)
                return null;
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8") , "AES"));
            byte[] bytes = Base64.decode(str, Base64.DEFAULT);
            bytes = cipher.doFinal(bytes);
            return new String(bytes , "utf-8");
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return "";
    }

    static int[][] array =
    {
    { 9, 7, 2, 6, 2, 6 },
    { 0, 4, 7, 2, 2, 3 },
    { 4, 8, 1, 0, 3, 2 } };

    public static String getAesKey(char[] timestampChar)
    {
        String key = "";
        for (int i = 0; i < 6; i++)
        {
            key += timestampChar[array[0][i]];
        }
        return key + "78abcd+-id";
    }
}
