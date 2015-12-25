package cn.sx.decentworld.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.R.string;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.PhotoMultiChoiceAdapter;
import cn.sx.decentworld.adapter.PhotoSingleChoiceAdapter;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.ImageFloder;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.dialog.ListImageDirPopupWindow;
import cn.sx.decentworld.dialog.ListImageDirPopupWindow.OnImageDirSelected;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * 
 * @ClassName: PictureChoiceActivity.java
 * @Description:
 * @author: yj
 * @date: 2015年7月28日 下午6:58:34
 */

@EActivity(R.layout.activity_picture_choice)
public class PictureChoiceActivity extends BaseFragmentActivity implements
		OnImageDirSelected {
	private static final String TAG = "PictureChoiceActivity";
	private ProgressDialog mProgressDialog;
	// 头部资源
	private TextView main_header_title;
	private LinearLayout main_header_left;
	private TextView main_header_left_tv;
	private TextView main_header_right_tv;
	private ImageView main_header_right_btn;
	public static final int OK = 1;
	public static final int CLIP_IMAGE = 3;
	// 最多图文件夹存在图的数量
	private int mPicsSize;
	// 最多图的文件夹文件
	private File mImgDir;
	private List<String> mImgs;

	private GridView mGirdView;
	private PhotoSingleChoiceAdapter mAdapter;
	private PhotoMultiChoiceAdapter mmAdapter;
	private HashSet<String> mDirPaths = new HashSet<String>();

	private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

	private RelativeLayout mBottomLy;
	private TextView mChooseDir;
	private TextView mImageCount;

	int totalCount = 0;

	private int mScreenHeight;

	private ListImageDirPopupWindow mListImageDirPopupWindow;
	private Bitmap bitmap;
	private Object obj;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mProgressDialog.dismiss();
			data2View();
			initListDirPopupWindw();
		}
	};

	private String cls;

	private String clsName;

	private String packageName;

	/**
	 * 根据最多图的文件夹，展示其所有的图片文件，在mGirdView上适配数据
	 */
	private void data2View() {
		if (mImgDir == null) {
			return;
		}

		List<String> re = new ArrayList<String>();

		mImgs = Arrays.asList(mImgDir.list());
		for (String filename : mImgs) {
			if ((filename.endsWith(".jpg") || filename.endsWith(".png") || filename
					.endsWith(".jpeg"))) {
				re.add(filename);
			}
		}
		// mImgs.removeAll(re);

		// && !cls.equals("activity.ChatActivity_"))
		if (!cls.equals("activity.PublicWorkActivity_")) {
			mAdapter = new PhotoSingleChoiceAdapter(getApplicationContext(),
					re, R.layout.photo_single_grid_item,
					mImgDir.getAbsolutePath());
			mGirdView.setAdapter(mAdapter);

			mGirdView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					File f = new File(mImgDir, mImgs.get(position));
					try {
						if (cls.equals("activity.ChatSettingSetBgActivity_")) {
							Intent intent = new Intent(getApplicationContext(),
									ChatActivity_.class);
							intent.putExtra("values", f);
							startActivity(intent);
							finish();
						} else if (cls.equals("activity.ChatActivity_")
								|| cls.contains("LoginActivity")
								|| cls.contains("Test")
								|| cls.equals("activity.ChatRoomBuildActivity_")
								|| cls.equals("activity.ChatRoomChatActivity_")) {
							Intent intent = new Intent(getApplicationContext(),
									getclass(packageName).getClass());
							intent.putExtra("values", f);
							setResult(RESULT_OK, intent);
							finish();
						}
						// else if (cls.contains("LoginActivity"))
						// {
						// Intent intent = new Intent(getApplicationContext() ,
						// getclass(packageName).getClass());
						// intent.putExtra("values", f);
						// setResult(RESULT_OK, intent);
						// finish();
						// }
						// else if (cls.contains("Test"))
						// {
						// Intent intent = new Intent(getApplicationContext() ,
						// getclass(packageName).getClass());
						// intent.putExtra("values", f);
						// setResult(RESULT_OK, intent);
						// finish();
						// }
						// else if
						// (cls.equals("activity.ChatRoomBuildActivity_"))
						// {
						// Intent intent = new Intent(getApplicationContext() ,
						// getclass(packageName).getClass());
						// intent.putExtra("values", f);
						// setResult(RESULT_OK, intent);
						// finish();
						// }
						else if (cls.equals("ChatFragment")) {
							Intent intent = new Intent();
							intent.putExtra("values", f);
							setResult(RESULT_OK, intent);
							finish();
						}
						// else if
						// (cls.equals("activity.EditUserInfoActivity_"))
						// {
						// Intent intent = new Intent();
						// intent.putExtra("values", f);
						// setResult(RESULT_OK, intent);
						// finish();
						// }
						else {
							startPhotoZoom(Uri.fromFile(f));
						}
					} catch (Exception e) {
					}
				}
			});
		} else {
			mmAdapter = new PhotoMultiChoiceAdapter(getApplicationContext(),
					re, R.layout.photo_mulit_grid_item,
					mImgDir.getAbsolutePath());
			mGirdView.setAdapter(mmAdapter);
		}
		mImageCount.setText(totalCount + "张");
	};

	private void initListDirPopupWindw() {
		mListImageDirPopupWindow = new ListImageDirPopupWindow(
				LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
				mImageFloders, LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.list_dir, null));

		mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		mListImageDirPopupWindow.setOnImageDirSelected(this);
	}

	@AfterViews
	public void init() {
		Intent intent = getIntent();
		cls = intent.getStringExtra("which");
		packageName = "cn.sx.decentworld.";
		packageName += cls;

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;

		initView();
		getImages();
		initEvent();
	}

	/**
	 * 获取媒体库内全部图片
	 */
	private void getImages() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return;
		}
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

		new Thread(new Runnable() {
			@Override
			public void run() {
				String firstImage = null;
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = PictureChoiceActivity.this
						.getContentResolver();
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);

				Log.e("TAG", mCursor.getCount() + "");
				while (mCursor.moveToNext()) {
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));

					if (firstImage == null)
						firstImage = path;

					File parentFile = new File(path).getParentFile();
					if (parentFile == null)
						continue;
					String dirPath = parentFile.getAbsolutePath();
					ImageFloder imageFloder = null;
					if (mDirPaths.contains(dirPath)) {
						continue;
					} else {
						mDirPaths.add(dirPath);
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}
					if (parentFile.list() == null)
						continue;
					int picSize = parentFile.list(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String filename) {
							if (filename.endsWith(".jpg")
									|| filename.endsWith(".png")
									|| filename.endsWith(".jpeg"))
								return true;
							return false;
						}
					}).length;
					totalCount += picSize;

					imageFloder.setCount(picSize);
					mImageFloders.add(imageFloder);

					if (picSize > mPicsSize) {
						mPicsSize = picSize;
						mImgDir = parentFile;
					}
				}
				mCursor.close();
				mDirPaths = null;
				mHandler.sendEmptyMessage(0x110);

			}
		}).start();

	}

	/**
	 * activity的页面布局
	 */
	private void initView() {
		main_header_title = (TextView) findViewById(R.id.tv_header_title);
		main_header_title.setText("图片选择");
		main_header_title.setVisibility(View.VISIBLE);
		main_header_left = (LinearLayout) findViewById(R.id.main_header_left);
		main_header_left_tv = (TextView) findViewById(R.id.main_header_left_tv);
		main_header_left_tv.setText("返回");
		main_header_left.setVisibility(View.VISIBLE);
		main_header_right_tv = (TextView) findViewById(R.id.main_header_right_tv);
		main_header_right_tv.setText("选择");
		main_header_right_btn = (ImageView) findViewById(R.id.main_header_right_btn);
		main_header_right_btn.setVisibility(View.GONE);
		mGirdView = (GridView) findViewById(R.id.id_gridView);
		mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
		mImageCount = (TextView) findViewById(R.id.id_total_count);
		mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
	}

	@Click(R.id.main_header_left)
	public void main_header_left() {
		this.finish();
	}

	@Bean
	ChoceAndTakePictureComponent pic;

	@Click(R.id.main_header_right)
	public void main_header_right() {
		List<String> ll = mmAdapter.mSelectedImage;
		if (ll.size() != 0) {
			Intent intent;
			try {
				intent = new Intent(getApplicationContext(), getclass(
						packageName).getClass());
				// String [] toll = new String[ll.size()];
				// for(int i =0;i<toll.length;i++){
				// toll[i] = ll.get(i);
				// }
				// intent.putExtra("values", toll);
				intent.putStringArrayListExtra("values", (ArrayList<String>) ll);
				setResult(RESULT_OK, intent);
				mmAdapter.mSelectedImage.clear();
				finish();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 点击下栏切换其他
	 */
	private void initEvent() {
		mBottomLy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mListImageDirPopupWindow
						.setAnimationStyle(R.style.anim_popup_dir);
				mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = .3f;
				getWindow().setAttributes(lp);
			}
		});
	}

	/**
	 * 选择不同文件夹，适配该文件夹下全部图片
	 */
	@Override
	public void selected(ImageFloder floder) {
		mImgDir = new File(floder.getDir());
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;

			}
		}));

		if (!cls.equals("activity.PublicWorkActivity_")) {
			mAdapter = new PhotoSingleChoiceAdapter(getApplicationContext(),
					mImgs, R.layout.photo_single_grid_item,
					mImgDir.getAbsolutePath());
			mGirdView.setAdapter(mAdapter);

		} else {
			mmAdapter = new PhotoMultiChoiceAdapter(getApplicationContext(),
					mImgs, R.layout.photo_mulit_grid_item,
					mImgDir.getAbsolutePath());
			mGirdView.setAdapter(mmAdapter);
		}
		mImageCount.setText(floder.getCount() + "张");
		mChooseDir.setText(floder.getName());
		mListImageDirPopupWindow.dismiss();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CLIP_IMAGE && resultCode == RESULT_OK) {
			if (data != null) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					if (null == photo) {
						photo = BitmapFactory.decodeFile(Environment
								.getExternalStorageDirectory() + "/temp.jpg");
					}
					photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					byte[] datas = baos.toByteArray();
					Intent intent = null;
					try {
						intent = new Intent(this, getclass(packageName)
								.getClass());
					} catch (Exception e) {
						LogUtils.i(TAG, e.toString());
					}
					intent.putExtra("bitmap", datas);
					setResult(RESULT_OK, intent);
					this.finish();
				}
			}
		} else if (requestCode == ChoceAndTakePictureComponent.TAKE_PICKTURE
				&& resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), pic.getImageName(), 1)
					.show();
		}
	}

	private static final String IMAGE_FILE_LOCATION = Environment
			.getExternalStorageDirectory() + "/temp.jpg";
	private Uri imageUri = Uri.fromFile(new File(IMAGE_FILE_LOCATION));

	/**
	 * 裁剪图片方法实现
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, CLIP_IMAGE);
	}

	public Object getclass(String className) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException// className是类名
	{
		Object obj = Class.forName(className).newInstance(); // 以String类型的className实例化类
		return obj;
	}

}
