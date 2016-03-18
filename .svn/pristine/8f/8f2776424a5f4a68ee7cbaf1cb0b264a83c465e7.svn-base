package cn.sx.decentworld.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.RegisterInfo;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.RegisterComponent;
import cn.sx.decentworld.dialog.BackDialogFragment;
import cn.sx.decentworld.dialog.BackDialogFragment.OnBackClickListener;
import cn.sx.decentworld.dialog.DialogDeletePicFragment;
import cn.sx.decentworld.dialog.DialogDeletePicFragment.OnDeleteClickListener;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register_appearance)
public class RegisterAppearanceCheckActivity extends BaseFragmentActivity
		implements OnLongClickListener, OnBackClickListener {
	@ViewById(R.id.vp_pic)
	ViewPager vpPic;
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.iv_appearance)
	ImageView ivAppearance;
	@ViewById(R.id.fl_vp)
	FrameLayout flVp;
	@ViewById(R.id.iv_identify_card)
	ImageView ivIdentifyCard;
	@ViewById(R.id.ll_dots)
	LinearLayout llDots;
	@ViewById(R.id.root_activity_register_appearance)
	LinearLayout llRegisterAppearance;
	private List<Bitmap> bitmaps;
	private List<ImageView> imageViews; // 滑动的图片集合
	private List<View> dots; // 图片下面的那些点
	private int currentItem = 0; // 当前图片的索引号
	private CustomPageAdapter customPageAdapter;
	private static final int REQUEST_CODE = 100;
	// 储存图片路径
	private List<String> picPathList;
	private FragmentManager fragmentManager;
	private static final int ONE_MAX = 1;
	private static final int THREE_MAX = 3;
	@Bean
	ToastComponent toast;
	@Bean
	RegisterComponent registerComponent;

	@AfterViews
	public void init() {
		tvTitle.setText("颜值");
		initData();
		fragmentManager = getSupportFragmentManager();
		dotNormalBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		ivIdentifyCard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						TakePhotosAndpictureActivity.class);
				intent.putExtra("max_count", 1);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
		ivAppearance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						TakePhotosAndpictureActivity.class);
				intent.putExtra("max_count", 3);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				backAction();
			}
		});
		customPageAdapter = new CustomPageAdapter();
		vpPic.setAdapter(customPageAdapter);// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		vpPic.setOnPageChangeListener(new CustomPageChangeListener());
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Handler handler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						toast.show("提交成功,请返回登录页面");
						startActivity(new Intent(mContext, LoginActivity_.class));
						finish();
					};
				};
				File[] images = new File[4];
				if (picPathList.isEmpty() && DecentWorldApp.tempPicPath == null) {
					toast.show("请先选择一张图片");
					return;
				}
				for (int i = 0; i < picPathList.size(); i++) {
					images[i] = new File(picPathList.get(i));
				}
				if (null != DecentWorldApp.tempPicPath) {
					images[3] = new File(DecentWorldApp.tempPicPath);
				}
				HashMap<String, String> hashmap = new HashMap<String, String>();
				hashmap.put("material", "");
				hashmap.put(Constants.DW_ID, DecentWorldApp.getInstance()
						.getDwID());
				hashmap.put("type", Constants.APPEARANCE);
				// registerComponent.submitImageType(images, the_switch,
				// handler);
				registerComponent.submitImageWithParams(hashmap, images,
						Constants.API_UPDATE_TYPE_AGAIN, handler);
			}
		});
	}

	private void setCurrentDot() {
		if (bitmaps.size() > 0 && currentItem >= 0 && dots.size() > currentItem) {
			currentItem = vpPic.getCurrentItem();
			dots.get(currentItem).setBackgroundResource(R.drawable.dot_focused);
		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 */
	private class CustomPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			LogUtils.i("bm", "getCount=" + bitmaps.size());
			return bitmaps.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			LogUtils.i("bm", "instantiateItem=" + arg1);
			ImageView imageView = imageViews.get(arg1);
			((ViewPager) arg0).addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			LogUtils.i("bm", "destroyItem=" + arg2);
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

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class CustomPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		public void onPageSelected(int position) {
			currentItem = position;
			if (dots.size() > oldPosition && dots.size() > position) {
				dots.get(oldPosition).setBackgroundResource(
						R.drawable.dot_normal);
				dots.get(position)
						.setBackgroundResource(R.drawable.dot_focused);
			}

			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (REQUEST_CODE == requestCode) {
				// 判断返回的数据
				int max_count = data.getExtras().getInt("max_count");
				ArrayList<String> pic_filse = data.getExtras()
						.getStringArrayList("pic_paths");
				// 显示到页面上
				if (!pic_filse.isEmpty()) {
					handlePic(pic_filse);
					if (1 == max_count) {
						DecentWorldApp.tempPicPath = pic_filse.get(0);
						String pic_path = DecentWorldApp.tempPicPath;
						try {
							BitmapFactory.Options options = new BitmapFactory.Options();
							options.inSampleSize = 2;
							loadPic(max_count, options, pic_path);
							customPageAdapter.notifyDataSetChanged();
						} catch (OutOfMemoryError e) {
						}
					} else if (3 == max_count) {
						LogUtils.i("bm", "size==" + pic_filse.size());
						DecentWorldApp.mPicList.addAll(pic_filse);
						// 显示最后一张
						String pic_path = DecentWorldApp.mPicList
								.get(DecentWorldApp.mPicList.size() - 1);
						try {
							BitmapFactory.Options options = new BitmapFactory.Options();
							options.inSampleSize = 2;
							// File f = new File(pic_path);
							for (int i = 0; i < pic_filse.size(); i++) {
								String pic_path_show = pic_filse.get(i);
								picPathList.add(pic_path_show);
								loadPic(max_count, options, pic_path_show);
							}
							customPageAdapter.notifyDataSetChanged();
						} catch (OutOfMemoryError e) {
						}
					}

				}
			}
		}
	}

	/**
	 * 将大于2M的图片进行压缩处理
	 * */
	private void handlePic(ArrayList<String> pic_filse) {
		for (int i = 0; i < pic_filse.size(); i++) {
			String filePath = pic_filse.get(i);
			if (ImageUtils.fileLength(filePath) > 2 * 1024 * 1024) {
				File newFilePath = handleFile(filePath);
				pic_filse.remove(i);
				pic_filse.add(i, newFilePath.getAbsolutePath());
			}
		}
	}

	/**
	 * 将图片压缩到指定的大小
	 * */
	private File handleFile(String filePath) {
		Bitmap bitmap = ImageUtils.scalePic(filePath);
		String picPath = Constants.HomePath + "/temp"
				+ ImageUtils.generateFileName() + ".png";
		ImageUtils.saveBitmap(picPath, bitmap);
		File file = new File(picPath);
		return file;
	}

	private void loadPic(int max_count, BitmapFactory.Options options,
			String pic_path1) {
		Bitmap takeBitmap = BitmapFactory.decodeFile(pic_path1, options);
		bitmaps.add(takeBitmap);
		ImageView imageView = new ImageView(this);
		imageView.setImageBitmap(takeBitmap);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		// imageView.setTag(bitmaps.size() - 1);
		imageView.setOnLongClickListener(this);
		imageViews.add(imageView);
		View dot = createDot();
		dot.setTag(max_count);
		llDots.addView(dot);
		dots.add(dot);
	}

	private Bitmap dotNormalBitmap;

	public View createDot() {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10, 10);
		lp.setMargins(10, 0, 10, 0);
		View view = new View(this);
		view.setLayoutParams(lp);
		view.setBackgroundResource(R.drawable.dot_normal);
		return view;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DecentWorldApp.mPicList.clear();
		DecentWorldApp.tempPicPath = null;
	}

	@Override
	public boolean onLongClick(View view) {
		deleteAction();
		return true;
	}

	@Override
	public void backClick(int state) {
		if (state == Constants.SAVE) {
			RegisterInfo info = RegisterInfo
					.queryByDwID(DecentWorldApp.MAIN_KEY);
			if (info == null) {
				toast.show("保存失败");
				startIntent(RegisterWhatYouHaveActivity_.class);
				return;
			}
			int size = picPathList.size();
			if (size > 0) {
				switch (size) {
				case 1:
					info.picbeautyOnePath = picPathList.get(0);
					info.picbeautyTwoPath = null;
					info.picbeautyThreePath = null;
					break;
				case 2:
					info.picbeautyOnePath = picPathList.get(0);
					info.picbeautyTwoPath = picPathList.get(1);
					info.picbeautyThreePath = null;
					break;
				case 3:
					info.picbeautyOnePath = picPathList.get(0);
					info.picbeautyTwoPath = picPathList.get(1);
					info.picbeautyThreePath = picPathList.get(2);
					break;
				}
			} else {
				info.picbeautyOnePath = null;
				info.picbeautyTwoPath = null;
				info.picbeautyThreePath = null;
			}
			if (null != DecentWorldApp.tempPicPath) {
				info.picIdPath = DecentWorldApp.tempPicPath;
			} else {
				info.picIdPath = null;
			}
			info.save();
			startIntent(RegisterWhatYouHaveActivity_.class);
		} else {
			startIntent(RegisterWhatYouHaveActivity_.class);
		}
	}

	/**
	 * 初始化
	 * */
	private void initData() {
		bitmaps = new ArrayList<Bitmap>();
		picPathList = new ArrayList<String>();
		imageViews = new ArrayList<ImageView>();
		dots = new ArrayList<View>();
		RegisterInfo info = RegisterInfo.queryByDwID(registerComponent.tel);
		if (null == info) {
			info = new RegisterInfo(registerComponent.tel);
		}
		String picbeautyOnePath = info.picbeautyOnePath;
		String picbeautyTwoPath = info.picbeautyTwoPath;
		String picbeautyThreePath = info.picbeautyThreePath;
		String picIdPath = info.picIdPath;
		if (null != picbeautyOnePath) {
			initPicThree(picbeautyOnePath);
		}
		if (null != picbeautyTwoPath) {
			initPicThree(picbeautyTwoPath);
		}
		if (null != picbeautyThreePath) {
			initPicThree(picbeautyThreePath);
		}
		if (null != picIdPath) {
			initPicOne(picIdPath);
		}
	}

	private void initPicOne(String picIdPath) {
		DecentWorldApp.tempPicPath = picIdPath;
		Bitmap bitmap = getBitmap(picIdPath);
		bitmaps.add(bitmap);
		DecentWorldApp.tempPicPath = picIdPath;
		ImageView imageView = new ImageView(this);
		imageView.setImageBitmap(bitmap);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setOnLongClickListener(this);
		imageViews.add(imageView);
		View dot = createDot();
		dot.setTag(ONE_MAX);
		llDots.addView(dot);
		dots.add(dot);
	}

	private void initPicThree(String path) {
		picPathList.add(path);
		Bitmap bitmap = getBitmap(path);
		bitmaps.add(bitmap);
		DecentWorldApp.mPicList.add(path);
		ImageView imageView = new ImageView(this);
		imageView.setImageBitmap(bitmap);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setOnLongClickListener(this);
		imageViews.add(imageView);
		picPathList.add(path);
		View dot = createDot();
		dot.setTag(THREE_MAX);
		llDots.addView(dot);
		dots.add(dot);
	}

	private Bitmap getBitmap(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap bitmapUpload = BitmapFactory.decodeFile(path, options);
		return bitmapUpload;
	}

	private void backAction() {
		if (picPathList.size() > 0 || null != DecentWorldApp.tempPicPath) {
			BackDialogFragment bf = BackDialogFragment
					.newInstance("您是否需要保存已经写好的信息？");
			bf.setOnBackClickListener(RegisterAppearanceCheckActivity.this);
			bf.show(fragmentManager, "backDialogs");
		} else {
			startIntent(RegisterWhatYouHaveActivity_.class);
		}
	}

	private DialogDeletePicFragment dp;

	private void deleteAction() {
		dp = DialogDeletePicFragment.newInstance("确定要删除这张图片？");
		dp.setOnDeleteClickListener(new OnDeleteClickListener() {

			@Override
			public void deleteClick(int state) {
				switch (state) {
				case Constants.ENSURE:
					int position = vpPic.getCurrentItem();
					int tag = (Integer) dots.get(position).getTag();
					if (1 == tag) {
						DecentWorldApp.tempPicPath = null;
					} else {
						if (DecentWorldApp.mPicList.size() > position) {
							DecentWorldApp.mPicList.remove(position);
							picPathList.remove(position);
						} else {
							DecentWorldApp.mPicList.remove(position - 1);
							picPathList.remove(position - 1);
						}
					}
					bitmaps.remove(position);
					imageViews.remove(position);
					dots.remove(position);
					llDots.removeViewAt(position);
					customPageAdapter.notifyDataSetChanged();
					LogUtils.i("bm",
							"--------size=" + DecentWorldApp.mPicList.size());
					break;
				case Constants.CANCEL:
					dp.dismiss();
					break;
				}
			}
		});
		dp.show(fragmentManager, "backDialogs");
	}

}
