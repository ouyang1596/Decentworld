/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sx.decentworld.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.LocationProvider;
import cn.sx.decentworld.listener.NotifyCallback;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

public class MapActivity extends BaseFragmentActivity {
	private final static String TAG = "map";
	static MapView mMapView = null;

	RelativeLayout sendButton = null;

	TextView textView;

	TextView main_left_tv;

	LinearLayout main_left;

	TextView main_title;

	EditText indexText = null;
	int index = 0;
	static AMapLocation lastLocation = null;
	public static MapActivity instance = null;
	ProgressDialog progressDialog;
	private AMap aMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.activity_baidumap);

		main_left = (LinearLayout) findViewById(R.id.main_header_left);
		main_left.setVisibility(View.VISIBLE);
		main_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		main_left_tv = (TextView) findViewById(R.id.main_header_left_tv);
		main_left_tv.setText("聊天详情");
		textView = (TextView) findViewById(R.id.main_header_right_tv);
		textView.setText("发送");
		main_title = (TextView) findViewById(R.id.tv_header_title);
		main_title.setText("地理位置");
		sendButton = (RelativeLayout) findViewById(R.id.main_header_right);
		Intent intent = getIntent();
		double latitude = intent.getDoubleExtra("latitude", 999);
		double longtitude = intent.getDoubleExtra("longitude", 999);
		mMapView = (MapView) findViewById(R.id.mapView);
		mMapView.onCreate(savedInstanceState);// 必须要写
		aMap = mMapView.getMap();
		aMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
		initMapView();
		if (latitude == 999 || longtitude == 999) {
			showMapWithLocationClient();
		} else {
			String address = intent.getStringExtra("address");
			LatLng p = new LatLng(latitude, longtitude);
			showMap(latitude, longtitude, address);
		}
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendLocation(v);
			}
		});

	}

	private void showMap(double latitude, double longtitude, String address) {
		sendButton.setVisibility(View.INVISIBLE);
		LatLng convertLatLng = new LatLng(latitude, longtitude);
		MarkerOptions ooA = new MarkerOptions()
				.position(convertLatLng)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)).zIndex(4)
				.draggable(true);
		aMap.addMarker(ooA);
		aMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f));
	}

	private void showMapWithLocationClient() {
		String str1 = getResources().getString(
				R.string.Making_sure_your_location);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(str1);

		progressDialog.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface arg0) {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				Log.d("map", "cancel retrieve location");
				finish();
			}
		});

		progressDialog.show();

		LocationProvider.getInstance(this).startLocation(new NotifyCallback() {
			@Override
			public void execute(AMapLocation location) {
				if (location == null) {
					return;
				}
				Log.d("map", "On location change received:" + location);
				Log.d("map", "addr:" + location.getAddress());
				sendButton.setEnabled(true);
				if (progressDialog != null) {
					progressDialog.dismiss();
				}

				if (lastLocation != null) {
					if (lastLocation.getLatitude() == location.getLatitude()
							&& lastLocation.getLongitude() == location
									.getLongitude()) {
						Log.d("map", "same location, skip refresh");
						// mMapView.refresh(); //need this refresh?
						return;
					}
				}
				lastLocation = location;
				aMap.clear();
				LatLng convertLatLng = new LatLng(lastLocation.getLatitude(),
						lastLocation.getLongitude());
				MarkerOptions ooA = new MarkerOptions()
						.position(convertLatLng)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.icon_marka)).zIndex(4)
						.draggable(true);
				aMap.addMarker(ooA);
				aMap.moveCamera(CameraUpdateFactory.changeLatLng(convertLatLng));
				aMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f));
			}
		});
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
		lastLocation = null;
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		super.onDestroy();
	}

	private void initMapView() {
		mMapView.setLongClickable(true);
	}

	public void back(View v) {
		finish();
	}

	public void sendLocation(View view) {
		Intent intent = this.getIntent();
		intent.putExtra("latitude", lastLocation.getLatitude());
		intent.putExtra("longitude", lastLocation.getLongitude());
		intent.putExtra("address", lastLocation.getAddress());
		this.setResult(RESULT_OK, intent);
		finish();
		overridePendingTransition(R.anim.slide_in_from_left,
				R.anim.slide_out_to_right);
	}

}
