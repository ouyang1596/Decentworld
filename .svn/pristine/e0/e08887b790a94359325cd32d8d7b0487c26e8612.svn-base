/**
 * 
 */
package cn.sx.decentworld.manager;

import java.util.HashMap;

import com.android.volley.Request.Method;

import cn.sx.decentworld.callback.HttpCallback;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ToastUtils;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @ClassName: CommHttp.java
 * @Description: 公共Http请求，从服务器拿取数据 
 * 使用流程：
 * 1.初始化；
 * 2.调用对应的方法；
 * @author: cj
 * @date: 2015年12月12日 下午5:48:11
 */
public class CommHttp
{
	private static final String TAG = "CommHttp";
	private Context context;
	private SendUrl sendUrl;
	
	/**
	 * 初始化
	 */
	public CommHttp(Context context)
	{
		this.context = context;
		this.sendUrl = new SendUrl(context);
	}
	
	/**
	 * 根据dwID获取昵称和身价
	 * @param dwID
	 * @param handler
	 */
	public void getNicknameAndWorth(final String dwID,final Handler handler,final int requestCode)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		LogUtils.i(TAG, "getNicknameAndWorth...begin,dwID="+dwID);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH+"/user/getUserNickAndWorth", Method.GET, new HttpCallBack()
		{
			@Override
			public void onSuccess(String response, ResultBean msg)
			{
				LogUtils.i(TAG, "getNicknameAndWorth...begin,msg.getResultCode="+msg.getResultCode()+",msg.getMsg="+msg.getMsg()+",msg.getData="+msg.getData());
				if(msg.getResultCode() == 2222)
				{
					Message message = Message.obtain();
					message.what = requestCode;
					message.obj = msg.getData();
					handler.sendMessage(message);
					
				}else if(msg.getResultCode() == 3333)
				{
					LogUtils.i(TAG, "getNicknameAndWorth...failure,cause by:"+msg.getMsg());
					ToastUtils.toast(context, msg.getMsg());
				}
			}
			
			@Override
			public void onFailure(String e)
			{
				LogUtils.i(TAG, "getNicknameAndWorth...failure,cause by:"+e);
				ToastUtils.toast(context, Constants.NET_WRONG);
			}
		});
	}
	
	

}
