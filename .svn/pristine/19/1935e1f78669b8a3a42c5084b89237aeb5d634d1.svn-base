//package cn.sx.decentworld.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Intent;
//import android.os.Parcelable;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ImageView.ScaleType;
//import android.widget.LinearLayout;
//import cn.sx.decentworld.R;
//import cn.sx.decentworld.component.ToastComponent;
//import cn.sx.decentworld.utils.ViewUtil;
//
//import com.googlecode.androidannotations.annotations.AfterViews;
//import com.googlecode.androidannotations.annotations.Bean;
//import com.googlecode.androidannotations.annotations.EActivity;
//import com.googlecode.androidannotations.annotations.ViewById;
//
//@EActivity(R.layout.activity_register_what_you_have)
//public class RegisterWhatYouHaveUseLessActivity extends BaseFragmentActivity
//		implements OnClickListener {
//	@ViewById(R.id.vp_what_you_have)
//	ViewPager vpWahtYouHave;
//	@ViewById(R.id.iv_back)
//	ImageView ivBack;
//	@ViewById(R.id.btn_OK)
//	Button btnOk;
//	@Bean
//	ToastComponent toast;
//	@ViewById(R.id.root_register_what_you_have)
//	LinearLayout llWhatYouHave;
//	private FragmentManager fragmentManager;
//	private int[] imageResId; // 图片ID
//	private List<ImageView> imageViews; // 滑动的图片集合
//	private List<View> dots; // 图片下面的那些点
//	private int currentItem = 0; // 当前图片的索引号
//	private static final int TALENT = 0;
//	private static final int MONEY = 1;
//	private static final int APPEARANCE = 2;
//
//	@AfterViews
//	public void init() {
//		ViewUtil.scaleContentView(llWhatYouHave);
//		fragmentManager = getSupportFragmentManager();
//		imageResId = new int[] { R.drawable.ic_launcher,
//				R.drawable.icon_mute_normal, R.drawable.icon_speaker_on };
//		imageViews = new ArrayList<ImageView>();
//		// 初始化图片资源
//		for (int i = 0; i < imageResId.length; i++) {
//			ImageView imageView = new ImageView(this);
//			imageView.setTag(i);
//			imageView.setOnClickListener(this);
//			imageView.setImageResource(imageResId[i]);
//			imageView.setScaleType(ScaleType.CENTER_CROP);
//			imageViews.add(imageView);
//		}
//		dots = new ArrayList<View>();
//		dots.add(findViewById(R.id.v_dot0));
//		dots.add(findViewById(R.id.v_dot1));
//		dots.add(findViewById(R.id.v_dot2));
//		vpWahtYouHave.setAdapter(new CustomPageAdapter());// 设置填充ViewPager页面的适配器
//		// 设置一个监听器，当ViewPager中的页面改变时调用
//		vpWahtYouHave.setOnPageChangeListener(new CustomPageChangeListener());
//		ivBack.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				backAction();
//			}
//
//		});
//		btnOk.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				int curItem = vpWahtYouHave.getCurrentItem();
//				switch (curItem) {
//				case TALENT:
//					startActivity(new Intent(mContext,
//							RegisterTalentActivity_.class));
//					break;
//				case MONEY:
//					startActivity(new Intent(mContext,
//							RegisterMoneyActivity_.class));
//					break;
//				case APPEARANCE:
//					startActivity(new Intent(mContext,
//							RegisterAppearanceActivity_.class));
//					break;
//				}
//				finish();
//			}
//		});
//	}
//
//	private void backAction() {
//		startIntent(RegisterIsStudentActivity_.class);
//	}
//
//	/**
//	 * 填充ViewPager页面的适配器
//	 * 
//	 * @author Administrator
//	 */
//	private class CustomPageAdapter extends PagerAdapter {
//
//		@Override
//		public int getCount() {
//			return imageResId.length;
//		}
//
//		@Override
//		public Object instantiateItem(View arg0, int arg1) {
//			((ViewPager) arg0).addView(imageViews.get(arg1));
//			return imageViews.get(arg1);
//		}
//
//		@Override
//		public void destroyItem(View arg0, int arg1, Object arg2) {
//			((ViewPager) arg0).removeView((View) arg2);
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//
//		@Override
//		public void restoreState(Parcelable arg0, ClassLoader arg1) {
//
//		}
//
//		@Override
//		public Parcelable saveState() {
//			return null;
//		}
//
//		@Override
//		public void startUpdate(View arg0) {
//
//		}
//
//		@Override
//		public void finishUpdate(View arg0) {
//
//		}
//	}
//
//	/**
//	 * 当ViewPager中页面的状态发生改变时调用
//	 * 
//	 * @author Administrator
//	 * 
//	 */
//	private class CustomPageChangeListener implements OnPageChangeListener {
//		private int oldPosition = 0;
//
//		public void onPageSelected(int position) {
//			currentItem = position;
//			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
//			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
//			oldPosition = position;
//		}
//
//		public void onPageScrollStateChanged(int arg0) {
//
//		}
//
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//		}
//	}
//
//	@Override
//	public void onClick(View view) {
//		int tag = (Integer) ((ImageView) view).getTag();
//		switch (tag) {
//		case TALENT:
//			startActivity(new Intent(mContext, RegisterTalentActivity_.class));
//			break;
//		case MONEY:
//			startActivity(new Intent(mContext, RegisterMoneyActivity_.class));
//			break;
//		case APPEARANCE:
//			startActivity(new Intent(mContext,
//					RegisterAppearanceActivity_.class));
//			break;
//		}
//		finish();
//	}
//
//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//		backAction();
//	}
// }
