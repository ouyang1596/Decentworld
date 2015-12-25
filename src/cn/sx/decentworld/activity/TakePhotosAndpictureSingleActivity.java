package cn.sx.decentworld.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.ImageBean;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.PickPhotoUtil;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class TakePhotosAndpictureSingleActivity extends Activity {
	private GridView gridview;
	private ProgressDialog mProgressDialog;
	private static final int MAX_COUNT = 1;
	private ProgressDialog mDirDialog;
	private ImageLoader mImageLoader;
	private ArrayList<Integer> chooseItem = new ArrayList<Integer>();
	private HashMap<String, ArrayList<String>> mGruopMap = new HashMap<String, ArrayList<String>>();
	private ArrayList<ImageBean> imgBeanLists = new ArrayList<ImageBean>();
	private static final String IMAGE_FILE_LOCATION = Environment
			.getExternalStorageDirectory() + "/temp.jpg";
	private Uri imageUri = Uri.fromFile(new File(IMAGE_FILE_LOCATION));
	public static final int CLIP_IMAGE = 3;
	// 所有的图片
	private ArrayList<String> mAllImgs;
	private final static int SCAN_OK = 1;
	private final static int SCAN_FOLDER_OK = 2;
	private DisplayImageOptions options;
	private boolean isOneEntered;
	private int aspectX, aspectY, outputX, outputY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photos_picture_single);
		TGetIntent();
		initView();
		initData();
		setListener();
	}

	public void TGetIntent() {
		aspectX = getIntent().getIntExtra(Constants.ASPECTX, 1);
		aspectY = getIntent().getIntExtra(Constants.ASPECTY, 1);
		outputX = getIntent().getIntExtra(Constants.OUTPUTX, 350);
		outputY = getIntent().getIntExtra(Constants.OUTPUTY, 350);
	}

	private void setListener() {
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (chooseItem.get(0) == 0 && 0 == position) {
					// 调用系统相机
					// 判断是否已满MAX_COUNT张图片
					tempCameraPath = Constants.CAMERA_PATH + "/"
							+ System.currentTimeMillis() + ".jpg";
					Log.e("bm", "path============" + tempCameraPath);
					PickPhotoUtil.getInstance().takePhoto(
							TakePhotosAndpictureSingleActivity.this,
							"tempUser", tempCameraPath);
				} else {
					startPhotoZoom(Uri.fromFile(new File(gridAdatper
							.getItem(position))));
					return;
				}
			}
		});
	}

	private String tempCameraPath = "";

	/**
	 * 裁剪图片方法实现
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, CLIP_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PickPhotoUtil.PickPhotoCode.PICKPHOTO_TAKE:

				File fi = new File("");
				PickPhotoUtil.getInstance().takeResult(this, data, fi);
				// 相机的图片
				ArrayList<String> camepaths = new ArrayList<String>();
				LogUtils.i("bm", "tempCameraPath=" + tempCameraPath);
				camepaths.add(tempCameraPath);
				startPhotoZoom(Uri.fromFile(new File(tempCameraPath)));
				break;
			case CLIP_IMAGE:
				if (data != null) {
					Bitmap photo = data.getParcelableExtra("data");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					if (null == photo) {
						photo = BitmapFactory.decodeFile(IMAGE_FILE_LOCATION);
					}
					String fileName = ImageUtils.generateFileName();
					String filePath = Constants.HomePath + "/clipImage"
							+ fileName + ".png";
					ImageUtils.saveBitmap(filePath, photo);
					photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					byte[] datas = baos.toByteArray();
					Intent intent = new Intent();
					// intent.putExtra("bitmap", datas);
					intent.putExtra("filePath", filePath);
					setResult(RESULT_OK, intent);
					this.finish();
				} else {
					finish();
				}
				break;
			}
		}
	}

	ArrayList<String> nowStrs = new ArrayList<String>();
	GridAdapter gridAdatper;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SCAN_OK:
				// 关闭进度条
				mProgressDialog.dismiss();
				// 扫描完成后，给listview赋值------给所有图片赋值
				imgBeanLists = subGroupOfImage(mGruopMap);
				// 获取到mAllImgs；并显示到数据中
				gridAdatper = new GridAdapter();
				gridAdatper.setData(mAllImgs);
				gridview.setAdapter(gridAdatper);
				break;
			case SCAN_FOLDER_OK:
				mDirDialog.dismiss();
				// 获取到mAllImgs；并显示到数据中
				GridAdapter gridAdatper1 = new GridAdapter();
				gridAdatper1.setData(nowStrs);
				gridview.setAdapter(gridAdatper1);
				break;
			}
		}

	};

	private void initView() {
		gridview = (GridView) findViewById(R.id.gridview);
	}

	private void initData() {
		// 初始化数据，所有图片应在281张以内
		chooseItem.add(0);
		// imageLoader配置
		DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(imageOptions)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.memoryCacheSize(2 * 1024 * 1024)
				// .memoryCache(new WeakMemoryCache())
				.build();
		ImageLoader.getInstance().init(config);
		mImageLoader = ImageLoader.getInstance();

		options = new DisplayImageOptions.Builder().cacheOnDisc()
				.showImageForEmptyUri(R.drawable.friends_sends_pictures_no)
				.showImageOnFail(R.drawable.friends_sends_pictures_no)
				.showStubImage(R.drawable.friends_sends_pictures_no).build();

		mAllImgs = new ArrayList<String>();
		getImages();
	}

	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
	 */
	private void getImages() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}

		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

		new Thread(new Runnable() {

			@Override
			public void run() {
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = TakePhotosAndpictureSingleActivity.this
						.getContentResolver();
				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);

				while (mCursor.moveToNext()) {
					// 获取图片的路径
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));

					// 获取该图片的父路径名
					File pa_file = new File(path).getParentFile();
					String parentName = pa_file.getAbsolutePath();
					if (mAllImgs.size() < 1000000/* 281 */) {
						mAllImgs.add(path);
					}
					// 根据父路径名将图片放入到mGruopMap中
					if (!mGruopMap.containsKey(parentName)) {
						ArrayList<String> chileList = new ArrayList<String>();
						chileList.add(path);
						mGruopMap.put(parentName, chileList);
					} else {
						mGruopMap.get(parentName).add(path);
					}
				}

				mCursor.close();

				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(SCAN_OK);

			}
		}).start();

	}

	/**
	 * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中 所以需要遍历HashMap将数据组装成List
	 * 
	 * @param mGruopMap
	 * @return
	 */
	private ArrayList<ImageBean> subGroupOfImage(
			HashMap<String, ArrayList<String>> gruopMap) {
		if (gruopMap.size() == 0) {
			return null;
		}
		ArrayList<ImageBean> list = new ArrayList<ImageBean>();
		Iterator<Map.Entry<String, ArrayList<String>>> it = gruopMap.entrySet()
				.iterator();
		ImageBean ig0 = new ImageBean();
		ig0.setFolderName("所有图片");
		ig0.setImageCounts(0);
		ig0.setTopImagePath("");
		list.add(0, ig0);
		while (it.hasNext()) {
			Map.Entry<String, ArrayList<String>> entry = it.next();
			ImageBean mImageBean = new ImageBean();
			String key = entry.getKey();
			List<String> value = entry.getValue();
			File dir_file = new File(key);
			mImageBean.setFolderName(dir_file.getName());
			mImageBean.setImageCounts(value.size());
			mImageBean.setTopImagePath(value.get(0));// 获取该组的第一张图片
			mImageBean.setFa_filepath(key);
			list.add(mImageBean);
		}

		return list;

	}

	// gridview的Adapter
	class GridAdapter extends BaseAdapter {
		// 根据三种不同的布局来应用
		final int VIEW_TYPE = 2;
		final int TYPE_1 = 0;
		final int TYPE_2 = 1;
		LayoutInflater inflater;
		private ArrayList<String> gridStrings;
		/**
		 * 用来存储图片的选中情况
		 */
		private HashMap<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();

		public GridAdapter() {
			gridStrings = new ArrayList<String>();
			inflater = LayoutInflater
					.from(TakePhotosAndpictureSingleActivity.this);
		}

		public void setData(ArrayList<String> strs) {
			if (null != strs) {
				gridStrings.clear();
				gridStrings.addAll(strs);
				notifyDataSetChanged();
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return gridStrings.size();
		}

		@Override
		public String getItem(int position) {
			if (chooseItem.get(0) == 0) {
				return gridStrings.get(position - 1);
			} else {
				Log.e("cxm",
						"position====" + position + ",path="
								+ gridStrings.get(position));
				return gridStrings.get(position);
			}
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getItemViewType(int position) {
			if (chooseItem.get(0) == 0) {
				if (position == 0) {
					return TYPE_1;
				} else {
					return TYPE_2;
				}
			} else {
				return TYPE_2;
			}
		}

		@Override
		public int getViewTypeCount() {
			if (chooseItem.get(0) == 0) {
				return VIEW_TYPE;
			} else {
				return 1;
			}
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			GridHolder gridHolder = null;
			PhotoHolder photoHodler = null;
			int type = getItemViewType(position);
			if (convertView == null) {
				switch (type) {
				case TYPE_1:
					// 显示拍照
					photoHodler = new PhotoHolder();
					convertView = inflater.inflate(R.layout.take_photo, null);
					convertView.setTag(photoHodler);
					break;
				case TYPE_2:
					convertView = inflater.inflate(R.layout.grid_item_single,
							null);
					gridHolder = new GridHolder();
					gridHolder.grid_image = (ImageView) convertView
							.findViewById(R.id.grid_image);
					convertView.setTag(gridHolder);
					break;
				default:
					break;
				}
			} else {
				switch (type) {
				case TYPE_1:
					// 显示拍照
					photoHodler = (PhotoHolder) convertView.getTag();
					break;
				case TYPE_2:
					gridHolder = (GridHolder) convertView.getTag();
					break;
				default:
					break;
				}
			}

			if (type == TYPE_2) {
				// 判断是否已经添加
				mImageLoader.displayImage("file://" + getItem(position),
						gridHolder.grid_image, options);
			}

			return convertView;
		}

		class PhotoHolder {

		}

		class GridHolder {
			ImageView grid_image;
		}

	}

}
