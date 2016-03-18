package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.NotificationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RegisterComponent;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.widget.HackyViewPager;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_examine)
public class ExamineActivity extends BaseFragmentActivity implements
		OnClickListener {
	@ViewById(R.id.vp_examine)
	HackyViewPager vpExamine;
	@ViewById(R.id.iv_pass)
	ImageView ivPass;
	@ViewById(R.id.iv_no_pass)
	ImageView ivNoPass;
	@Bean
	ToastComponent toast;
	ArrayList<String> mUrls = new ArrayList<String>();
	List<ImageView> imgvs = new ArrayList<ImageView>();
	private String name, sex, amount, dwID;
	@Bean
	RegisterComponent registerComponent;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2222:
				toast.show("审核结束");
				finish();
				break;

			default:
				finish();
				break;
			}
		};
	};

	@AfterViews
	public void init() {
		new InitAsyn().execute();
	}

	private void initView() {
		// ImageLoaderHelper.initImageLoader(mContext);
		Integer count = 0;
		if (null != amount && !("".equals(amount))) {
			count = Integer.valueOf(amount);
		}
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				if (i == 0) {
					mUrls.add(ImageUtils.getExamineByDwID(dwID, ""));
				} else {
					mUrls.add(ImageUtils.getExamineByDwID(dwID, "" + i));
				}
				imgvs.add(new ImageView(mContext));
			}
		}
		CustomPageAdapter pageAdapter = new CustomPageAdapter();
		vpExamine.setAdapter(pageAdapter);
		ivPass.setOnClickListener(this);
		ivNoPass.setOnClickListener(this);
	}

	private void cancelNotify() {
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 取消通知
		manager.cancel(Constants.NOTICE_ID);
	}

	private void EGetIntent() {
		name = getIntent().getStringExtra("name");
		sex = getIntent().getStringExtra("sex");
		amount = getIntent().getStringExtra("amount");
		LogUtils.e("bm", "--amount--" + amount);
		dwID = getIntent().getStringExtra("dwID");
		LogUtils.i("bm", "--name--" + name + "--sex--" + sex + "--amount--"
				+ amount + "--dwID--" + dwID);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_pass:
			handPass();
			break;

		case R.id.iv_no_pass:
			handNoPass();
			break;
		}
	}

	private void handPass() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("supporterID", DecentWorldApp.getInstance().getDwID());
		map.put(Constants.DW_ID, dwID);
		registerComponent.examinePass(map, mHandler);
	}

	private void handNoPass() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, dwID);
		registerComponent.examineNoPass(map, mHandler);
	}

	class InitAsyn extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			EGetIntent();
			cancelNotify();
			initView();
		}
	}

	private class CustomPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mUrls.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			ImageView imageView = imgvs.get(arg1);
			imageView.setScaleType(ScaleType.FIT_XY);
			ImageLoaderHelper.mImageLoader.displayImage(mUrls.get(arg1),
					imageView, ImageLoaderHelper.mOptions);
			((ViewPager) arg0).addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void finishUpdate(ViewGroup container) {
			super.finishUpdate(container);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}
}
