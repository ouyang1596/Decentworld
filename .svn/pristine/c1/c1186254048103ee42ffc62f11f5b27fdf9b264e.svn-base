package cn.sx.decentworld.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.security.AES;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.CookieUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * @ClassName: SendUrl.java
 * @Description: url请求类
 * @author: yj
 * @date: 2015-7-26 下午7:22:36
 */
public class SendUrl {
	private Context context;

	// private RequestQueue requestQueue;

	public SendUrl(Context context) {
		this.context = context;
		// requestQueue = Volley.newRequestQueue(this.context);
	}

	/**
	 * 带参数的http请求，若无参请求，将hashmap设置为null即可
	 * 
	 * @param requestMap
	 *            参数hashmap
	 * @param url
	 *            请求url
	 * @param method
	 *            请求方式 Method.POST or Method.GET(Volley)
	 * @param httpCallBack
	 *            回调接口
	 * 
	 * 
	 */
	public void httpRequestWithParams(final HashMap<String, String> requestMap,
			String url, int method, final HttpCallBack httpCallBack) {
		// String time = String.valueOf(System.currentTimeMillis());
		// char[] timestampChar = time.toCharArray();
		// String key = AES.getAesKey(timestampChar);
		// String privateKey = AES.Encrypt(AES.PRIVATE_KEY, key);
		// requestMap.put("privateKey", privateKey);
		// requestMap.put("timestamp", time);
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.addHeader("http-equiv", "content-type");
		asyncHttpClient.addHeader("content", "text/html");
		asyncHttpClient.addHeader("charset", "utf-8");
		saveCookie(asyncHttpClient);
		RequestParams requestparams = null;

		requestparams = IteratorMap(requestMap);
		requestparams.setContentEncoding(HTTP.UTF_8);

		// try
		// {
		// String url8 = new String(url.getBytes(), "utf-8");
		// }
		// catch (UnsupportedEncodingException e1)
		// {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		asyncHttpClient.get(url, requestparams, new TextHttpResponseHandler() {
			ResultBean resultBean = new ResultBean();

			@Override
			public void onSuccess(int arg0, Header[] arg1, final String response) {
				CookieUtils.setCookies(getCookie());
				try {

					new Thread() {

						public void run() {
							AES helper = new AES();
							// String resultInfo = helper.Decrypt(response);
							resultBean = JsonUtils
									.getResultMsgFromJson(response);
							// System.out.println(resultBean.toString());
							// Log.v("resultBean_from_jason",
							// resultBean.toString());
							// Activity activity = ((Activity) context);
							// activity.runOnUiThread(new Runnable() {
							// @Override
							// public void run() {
							httpCallBack.onSuccess(response, resultBean);
							// }
							// });
						};

					}.start();

				} catch (Exception e) {
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				httpCallBack.onFailure(arg2);
			}
		});

	}

	/**
	 * 带参数的http请求（参数放在HashMap<String,Double>），若无参请求，将hashmap设置为null即可
	 * 
	 * @param requestMap
	 * @param url
	 * @param method
	 * @param httpCallBack
	 */
	public void httpRequestWithDouble(final HashMap<String, Double> requestMap,
			String url, int method, String dw_id,
			final HttpCallBack httpCallBack) {
		// String time = String.valueOf(System.currentTimeMillis());
		// char[] timestampChar = time.toCharArray();
		// String key = AES.getAesKey(timestampChar);
		// String privateKey = AES.Encrypt(AES.PRIVATE_KEY, key);
		// requestMap.put("privateKey", privateKey);
		// requestMap.put("timestamp", time);
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		saveCookie(asyncHttpClient);
		RequestParams requestparams = null;
		requestparams = IteratorMapDouble(requestMap);
		requestparams.put("dw_userid", dw_id);
		asyncHttpClient.get(url, requestparams, new TextHttpResponseHandler() {
			ResultBean resultBean = new ResultBean();

			@Override
			public void onSuccess(int arg0, Header[] arg1, final String response) {
				CookieUtils.setCookies(getCookie());
				try {
					new Thread() {
						public void run() {
							AES helper = new AES();
							// String resultInfo = helper.Decrypt(response);
							resultBean = JsonUtils
									.getResultMsgFromJson(response);
							// System.out.println(resultBean.toString());
							// Log.v("resultBean_from_jason",
							// resultBean.toString());
							// Activity activity = ((Activity) context);
							// activity.runOnUiThread(new Runnable() {
							// @Override
							// public void run() {
							httpCallBack.onSuccess(response, resultBean);
							// }
							// });
						};

					}.start();

				} catch (Exception e) {
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				httpCallBack.onFailure(arg2);
			}
		});

	}

	/**
	 * 带参数上传图片 POST
	 * 
	 * @param params
	 *            requestMap 参数params
	 * @param images
	 * @param url
	 * @param httpCallBack
	 */
	public void httpRequestWithImage(HashMap<String, String> params,
			File[] images, String url, final HttpCallBack httpCallBack) {
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		saveCookie(asyncHttpClient);
		RequestParams requestparams = null;
		if (params != null) {
			requestparams = IteratorMap(params);
		}
		try {
			for (int i = 0; i < images.length; i++) {
				if (i == 0) {
					requestparams.put("file", images[i]);
				} else {
					requestparams.put("file" + i, images[i]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		asyncHttpClient.post(url, requestparams, new TextHttpResponseHandler() {

			ResultBean resultBean = new ResultBean();

			@Override
			public void onSuccess(int arg0, Header[] arg1, final String response) {
				CookieUtils.setCookies(getCookie());
				try {

					new Thread() {
						public void run() {
							resultBean = JsonUtils
									.getResultMsgFromJson(response);
							httpCallBack.onSuccess(response, resultBean);
						};

					}.start();

				} catch (Exception e) {
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				httpCallBack.onFailure(arg0 + arg2 + arg3.getMessage());
			}

		});
	}

	/**
	 * 带参数上传图片 POST
	 * 
	 * @param params
	 *            requestMap 参数params
	 * @param images
	 * @param url
	 * @param httpCallBack
	 */
	public void httpRequestWithImageMulti(HashMap<String, String> params,
			File[] images, String url, final HttpCallBack httpCallBack) {
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		saveCookie(asyncHttpClient);
		RequestParams requestparams = null;
		if (params != null) {
			requestparams = IteratorMap(params);
		}
		try {
			if (null != images) {
				requestparams.put("file", images);
				
			}
		} catch (FileNotFoundException e1) {
		}
		asyncHttpClient.post(url, requestparams, new TextHttpResponseHandler() {

			ResultBean resultBean = new ResultBean();

			@Override
			public void onSuccess(int arg0, Header[] arg1, final String response) {
				CookieUtils.setCookies(getCookie());
				try {

					new Thread() {
						public void run() {
							resultBean = JsonUtils
									.getResultMsgFromJson(response);
							httpCallBack.onSuccess(response, resultBean);
						};

					}.start();

				} catch (Exception e) {
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				httpCallBack.onFailure(arg0 + arg2 + arg3.getMessage());
			}
		});
	}

	/**
	 * 遍历haspmap
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private RequestParams IteratorMap(HashMap<String, String> params) {
		RequestParams requestparams = new RequestParams();
		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String values = (String) entry.getValue();
			requestparams.put(key, values);
		}

		return requestparams;

	}

	@SuppressWarnings("rawtypes")
	private RequestParams IteratorMapDouble(HashMap<String, Double> params) {
		RequestParams requestparams = new RequestParams();
		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String values = (String) entry.getValue();
			requestparams.put(key, values);
		}

		return requestparams;

	}

	public interface HttpCallBack {
		/**
		 * 网络回调函数,此函数调用,说明网络请求失败
		 * 
		 * @param String
		 */
		public void onFailure(String e);

		/**
		 * 网络回调函数,此函数调用,说明网络请求成功
		 * 
		 * @param response
		 */
		public void onSuccess(String response, ResultBean msg);

	}

	protected void saveCookie(AsyncHttpClient client) {
		if (null != client) {
			PersistentCookieStore cookieStore = new PersistentCookieStore(
					context);
			client.setCookieStore(cookieStore);
		}
	}

	protected List<Cookie> getCookie() {
		PersistentCookieStore cookieStore = new PersistentCookieStore(context);
		List<Cookie> cookies = cookieStore.getCookies();
		return cookies;
	}

	public void clearCookie() {
		PersistentCookieStore cookieStore = new PersistentCookieStore(context);
		cookieStore.clear();
	}
}
