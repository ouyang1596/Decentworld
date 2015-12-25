/**
 * 
 */
package cn.sx.decentworld.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;

public class CookieUtils {

	private static List<Cookie> cookies;

	public static List<Cookie> getCookies() {
		return cookies != null ? cookies : new ArrayList<Cookie>();
	}

	public static void setCookies(List<Cookie> cookies) {
		CookieUtils.cookies = cookies;
	}

}
