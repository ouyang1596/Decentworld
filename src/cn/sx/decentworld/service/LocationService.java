/**
 * 
 */
package cn.sx.decentworld.service;

import java.util.HashMap;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.LocationProvider;
import cn.sx.decentworld.listener.NotifyCallback;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ToastUtil;

import com.amap.api.location.AMapLocation;
import com.android.volley.Request;

/**
 * 
 * @ClassName: LocationService.java
 * @Description: 地理位置的获取与上传服务器的服务
 * @author: cj
 * @date: 2016年1月18日 上午10:56:44
 */
public class LocationService extends Service {
	private static final String TAG = "LocationService";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 第一次启动时，会执行，再次启动便不会再执行
	 * */
	@Override
	public void onCreate() {
		super.onCreate();
		submitUserLocation();
	}

	/**
	 * 如果Service已经启动，则执行此方法
	 * */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 提交地理坐标到服务器
	 */
	private void submitUserLocation() {
		LocationProvider provider = LocationProvider.getInstance(this);
		provider.setNotify("SCHEDULE_LOCATION", new NotifyCallback() {
			@Override
			public void execute(AMapLocation location) {
				if (location == null) {
					return;
				}
				LocationProvider.latitude = location.getLatitude();
				LocationProvider.longitude = location.getLongitude();
				LogUtils.i(Constants.TAG_BM, "获取到定位信息" + "\n" + "locationAddress=" + location.getAddress() + "\n" + "laititude="
						+ location.getLatitude() + "\nlongtitude=" + location.getLongitude() + "\n");
				uploadLocation(DecentWorldApp.getInstance().getDwID(), location.getLatitude(), location.getLongitude());
			}
		});
		provider.startLocation(false);
	}

	/**
	 * 上传用户所在位置的经纬度
	 *
	 * @param latitude
	 *            纬度
	 * @param longitude
	 *            经度
	 */
	public void uploadLocation(String dwID, final double latitude, final double longitude) {
		LogUtils.e(Constants.TAG_BM, "location_request");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", dwID);
		map.put("user_lt", String.valueOf(latitude));
		map.put("user_ln", String.valueOf(longitude));
		// 实时上传用户坐标接口
		SendUrl sendUrl = new SendUrl(this);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + "/user/updateLocation", Request.Method.GET,
				new SendUrl.HttpCallBack() {
					@Override
					public void onSuccess(String response, ResultBean msg) {
						if (msg.getResultCode() == 3000) {
							LogUtils.i("bm", "uploadLocation...end");
						}
						if (msg.getResultCode() == 3001) {
							LogUtils.i("bm", "uploadLocation...failure");
						}
					}

					@Override
					public void onFailure(String e) {
						LogUtils.e("bm", "failure--" + e);
					}
				});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtils.i("bm", "-----service--onDestroy--------");
	}
}