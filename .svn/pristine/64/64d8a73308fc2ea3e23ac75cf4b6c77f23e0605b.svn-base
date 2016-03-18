/**
	 *    该类主要是为了登录时传送password散列值
	 *    用到了common-codec  jar包
	 */
package cn.sx.decentworld.network.utils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName: MD5Utils.java
 * @Description: MD5工具类
 * @author: yj
 * @date: 2015-7-26 下午10:30:57
 */
public class MD5Utils {
	
	
	/**
	 * 将字符串进行加密处理
	 * 
	 * @param text
	 *            要加密的信息
	 * @return 加密后的信息
	 */
	public static String md5(String text) {

		return DigestUtils.md5Hex(getContentBytes(text, "utf-8"));

	}

	/**
	 * 将字符串转成 byte 数组
	 * 
	 * @param content
	 *            内容
	 * @param charset
	 *            编码格式
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
					+ charset);
		}
	}
	
	
	
}
