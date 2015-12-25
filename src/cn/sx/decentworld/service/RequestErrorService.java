/**
 *      与服务器通讯的过程中，出现错误的请求或者是环信请求数据成功但自己服务器
 *      却因为网络原因而导致的请求失败将在后台中保存，待网络完整畅通后继续执行
 * 
 * 
 */
package cn.sx.decentworld.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @ClassName: RequestErrorService.java
 * @Description: 网络请求失败回调请求服务
 * @author: yj
 * @date: 2015年8月10日 下午5:28:25
 */
public class RequestErrorService extends Service {

	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
