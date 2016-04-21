package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.NotificationManager;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.AppearanceBean;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RegisterComponent;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.widget.HackyViewPager;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_examine)
public class ExamineActivity extends BaseFragmentActivity implements OnClickListener {
	@ViewById(R.id.vp_examine)
	HackyViewPager vpExamine;
	@ViewById(R.id.iv_pass)
	ImageView ivPass;
	@ViewById(R.id.iv_no_pass)
	ImageView ivNoPass;
	@ViewById(R.id.tv_condition)
	TextView tvCondition;
	@ViewById(R.id.tv_name)
	TextView tvName;
	@ViewById(R.id.tv_pass)
	TextView tvPass;
	@ViewById(R.id.tv_nopass)
	TextView tvNoPass;
	@ViewById(R.id.et_introduce)
	EditText etIntroduce;
	@ViewById(R.id.tv_sex)
	TextView tvSex;
	@ViewById(R.id.tv_occupation_shortIntroduce)
	TextView tvOccupation;
	@ViewById(R.id.ll_dots)
	LinearLayout llDots;
	@ViewById(R.id.tv_ifEnter)
	TextView tvIfEnter;
	private int currentItem = 0; // 当前图片的索引号
	private List<View> dots;
	@Bean
	ToastComponent toast;
	ArrayList<String> mUrls = new ArrayList<String>();
	List<ImageView> imgvs = new ArrayList<ImageView>();
	private String checkJson;
	private AppearanceBean bean;
	private boolean isTrue;
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
		initLayout();
		EGetIntent();
		cancelNotify();
		initView();
	}

	private void initLayout() {
		// 窗口对齐屏幕宽度
		Window win = this.getWindow();
		win.getDecorView().setPadding(10, 60, 10, 0);
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		// lp.gravity = Gravity.BOTTOM;// 设置对话框置顶显示
		win.setAttributes(lp);
	}

	private void initView() {
		LogUtils.i("bm", "--data--" + bean.toString());
		// tvCondition.setText(bean.condition);
		tvIfEnter.setVisibility(View.GONE);
		tvCondition.setText("此人手上的词句，应和框内提示的一致，不一致，肯定是假；词句一致，可滑动看其他照片是否同一人，并最终选择，让不让她进入我们真实社区。谢谢！");
		tvPass.setText("我认为是真");
		tvNoPass.setText("我认为有假");
		tvName.setText(bean.showName);
		tvOccupation.setText(bean.occupation);
		tvSex.setText(bean.gender);
		dots = new ArrayList<View>();
		addMotherPic();
		if (bean.picAmount > 0) {
			for (int i = 0; i < bean.picAmount; i++) {
				if (i == 0) {
					mUrls.add(ImageUtils.getExamineByDwID(bean.dwID, ""));
				} else {
					mUrls.add(ImageUtils.getExamineByDwID(bean.dwID, i + 1 + ""));
				}
				imgvs.add(new ImageView(mContext));
				addDot();
			}
		}
		CustomPageAdapter pageAdapter = new CustomPageAdapter();
		vpExamine.setAdapter(pageAdapter);
		vpExamine.setOnPageChangeListener(new CustomPageChangeListener());
		ivPass.setOnClickListener(this);
		ivNoPass.setOnClickListener(this);
	}

	private void addMotherPic() {
		mUrls.add(ImageUtils.getMotherIconByDwID(bean.dwID));
		imgvs.add(new ImageView(mContext));
		addDot();
	}

	private void cancelNotify() {
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 取消通知
		manager.cancel(Constants.NOTICE_ID);
	}

	private void EGetIntent() {
		checkJson = getIntent().getStringExtra("check");
		bean = JsonUtils.json2Bean(checkJson, AppearanceBean.class);
		LogUtils.i("bm", "examineActivity--" + bean.toString());
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_pass:
			if (isTrue) {
				handPass();
			} else {
				handleIsTrue();
			}
			break;
		case R.id.iv_no_pass:
			if (isTrue) {
				handleNoPass();
			} else {
				handleFake();
			}
			break;
		}
	}

	private void handleIsTrue() {
		isTrue = true;
		vpExamine.setVisibility(View.GONE);
		llDots.setVisibility(View.GONE);
		tvCondition.setText(bean.shortIntroduce);
		etIntroduce.setText(bean.introduce);
		tvIfEnter.setVisibility(View.VISIBLE);
		tvPass.setText("通过");
		tvNoPass.setText("不通过");
	}

	private void handPass() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("supporterID", DecentWorldApp.getInstance().getDwID());
		map.put(Constants.DW_ID, bean.dwID);
		registerComponent.examinePass(map, mHandler);
	}

	private void handleNoPass() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("unpassID", DecentWorldApp.getInstance().getDwID());
		map.put(Constants.DW_ID, bean.dwID);
		registerComponent.examineNoPass(map, mHandler);
	}

	private void handleFake() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fakeVoteID", DecentWorldApp.getInstance().getDwID());
		map.put(Constants.DW_ID, bean.dwID);
		registerComponent.examineFake(map, mHandler);
	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 */
	private class CustomPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		public void onPageSelected(int position) {
			currentItem = position;
			if (dots.size() > oldPosition && dots.size() > position) {
				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			}
			oldPosition = position;
			if (currentItem == 0) {
				tvCondition.setText("此人手上的词句，应和框内提示的一致，不一致，肯定是假；词句一致，可滑动看其他照片是否同一人，并最终选择，让不让她进入我们真实社区。谢谢！");
			} else {
				tvCondition.setText("这几张照片，您认为都是同一人？");
			}
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

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
			ImageLoaderHelper.mImageLoader.displayImage(mUrls.get(arg1), imageView, ImageLoaderHelper.mOptions);
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
			setCurrentDot();
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}

	private void addDot() {
		View dot = createDot();
		llDots.addView(dot);
		dots.add(dot);
	}

	public View createDot() {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10, 10);
		lp.setMargins(10, 0, 10, 0);
		View view = new View(this);
		view.setLayoutParams(lp);
		view.setBackgroundResource(R.drawable.dot_normal);
		return view;
	}

	/**
	 * 设置默认的选中点
	 * */
	private void setCurrentDot() {
		if (bean.picAmount > 0 && currentItem >= 0 && dots.size() > currentItem) {
			currentItem = vpExamine.getCurrentItem();
			dots.get(currentItem).setBackgroundResource(R.drawable.dot_focused);
		}
	}
}
