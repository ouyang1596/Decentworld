package cn.sx.decentworld.activity;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.SubjectBean;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.dialog.CameraAlbumDialogFragment;
import cn.sx.decentworld.dialog.CameraAlbumDialogFragment.OnCameraAlbumClickListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ToastUtil;
import cn.sx.decentworld.widget.AutoSizeImageView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_edit_mysubject)
public class ChatRoomEditMySubjectActivity extends BaseFragmentActivity implements OnClickListener, OnCameraAlbumClickListener {
	private static final String TAG = "ChatRoomEditMySubjectActivity";
	private SubjectBean mSubjectBean;
	@ViewById(R.id.iv_cover)
	AutoSizeImageView ivSubjectBackground;
	@ViewById(R.id.et_subject_name)
	EditText etSubjectName;
	@ViewById(R.id.et_subject_content)
	EditText etSubjectContent;
	@ViewById(R.id.btn_photo)
	Button btnPhotos;
	@ViewById(R.id.btn_content_save)
	Button btnSave;
	@ViewById(R.id.tv_send)
	TextView tvSend;
	private int subjectID;// 当前主题的id
	private String subjectBackground;
	private String subjectName;
	private SendUrl mSendUrl;
	private int requestCode;
	private String filePath;

	@AfterViews
	public void init() {
		CGetIntent();
		initData();
	}

	private void CGetIntent() {
		mSubjectBean = (SubjectBean) getIntent().getSerializableExtra(ConstantIntent.SUBJECTBEAN);
	}

	private void initData() {
		mSendUrl = new SendUrl(this);
		subjectID = mSubjectBean.id;
		subjectBackground = mSubjectBean.subjectBackground;
		subjectName = mSubjectBean.subjectName;
		etSubjectName.setText(subjectName);
		if (CommUtil.isNotBlank(subjectBackground)) {
			ImageLoaderHelper.mImageLoader.displayImage(subjectBackground, ivSubjectBackground, ImageLoaderHelper.mOptions);
		}
		etSubjectName.setText(subjectName);
		// if (CommUtil.isNotBlank(mSubjectBean.subjectContent)) {
		// Spanned spanned = Html.fromHtml(mSubjectBean.subjectContent, new
		// MyImageGetter(mContext, etSubjectContent), null);
		// etSubjectContent.setText(spanned);
		// etSubjectContent.setMovementMethod(LinkMovementMethod.getInstance());
		// }
		btnPhotos.setOnClickListener(this);
		tvSend.setOnClickListener(this);
		ivSubjectBackground.setOnClickListener(this);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			finish();
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_cover:
			requestCode = Constants.REQUEST_CODE_COVER;
			showCameraAlbumDialog();
			break;
		case R.id.tv_send:
			if (CommUtil.isBlank(subjectBackground)) {
				ToastUtil.showToast("请先上传一张图片");
				return;
			}
			if (CommUtil.isBlank(etSubjectName.getText().toString())) {
				ToastUtil.showToast("请先输入主题名");
				return;
			}
			if (CommUtil.isBlank(etSubjectContent.getText().toString())) {
				ToastUtil.showToast("请先输入主题内容");
				return;
			}
			changeSubject();
			break;
		case R.id.btn_photo:
			requestCode = Constants.REQUEST_CODE_PIC;
			showCameraAlbumDialog();
			break;
		case R.id.iv_back:
			finish();
			break;
		}
	}

	private void changeSubject() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("subjectName", etSubjectName.getText().toString());
		map.put("roomID", mSubjectBean.roomID);
		map.put("subjectID", "" + subjectID);
		map.put("ownerID", DecentWorldApp.getInstance().getDwID());
		map.put("subjectContent", etSubjectContent.getText().toString());
		File[] file = new File[1];
		file[0] = new File(subjectBackground);
		showProgressDialog();
		mSendUrl.httpRequestWithImage(map, file, Constants.CONTEXTPATH_OPENFIRE + Constants.API_UPDATA_SUBJECT,
				new HttpCallBack() {

					@Override
					public void onSuccess(String resultJSON, ResultBean resultBean) {
						LogUtils.i(TAG, "changeSubject---" + resultBean.toString());
						hideProgressDialog();
						if (resultBean.getResultCode() == 2222) {
							mHandler.sendEmptyMessage(2222);
						} else {
							showToast(resultBean.getMsg());
						}
					}

					@Override
					public void onFailure(String e) {
						LogUtils.e(TAG, "changeSubject---e--->" + e);
						hideProgressDialog();
						showToast(Constants.NET_WRONG);
					}
				});
	}

	private CameraAlbumDialogFragment cameraAlbumDialogFragment;

	/**
	 * 显示拍照相册对话框
	 * */
	private void showCameraAlbumDialog() {
		if (null == cameraAlbumDialogFragment) {
			cameraAlbumDialogFragment = new CameraAlbumDialogFragment();
		}
		cameraAlbumDialogFragment.setOnCameraAlbumClickListener(this);
		cameraAlbumDialogFragment.show(getSupportFragmentManager(), "cameraAlbumDialogFragment");
	}

	@Override
	public void onCameraAlbumClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.tv_camera:
			intent = new Intent(this, PictureClipActivity_.class);
			intent.putExtra(Constants.CAMERA_ALBUM, 1);
			intent.putExtra(Constants.ASPECTX, 1);
			intent.putExtra(Constants.ASPECTY, 1);
			intent.putExtra(Constants.OUTPUTX, 400);
			intent.putExtra(Constants.OUTPUTY, 400);
			startActivityForResult(intent, requestCode);
			break;
		case R.id.tv_album:
			intent = new Intent(this, PictureClipActivity_.class);
			intent.putExtra(Constants.CAMERA_ALBUM, 0);
			intent.putExtra(Constants.ASPECTX, 1);
			intent.putExtra(Constants.ASPECTY, 1);
			intent.putExtra(Constants.OUTPUTX, 400);
			intent.putExtra(Constants.OUTPUTY, 400);
			startActivityForResult(intent, requestCode);
			break;
		}
	}

	private ProgressDialog mProDialog;

	private void showProgressDialog() {
		if (null == mProDialog) {
			mProDialog = ProgressDialog.show(this, null, "loading");
		} else {
			mProDialog.show();
		}
	}

	private void hideProgressDialog() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (null != mProDialog) {
					mProDialog.hide();
				}
			}
		});
	}

	private void showToast(final String data) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ToastUtil.showToast(data);
			}
		});
	}

	private Handler mUploadHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				JSONObject object = new JSONObject(msg.obj.toString());
				// String imgUrl = object.getString("file");
				String imgUrl = "http://112.74.13.117/data/487066/icon/icon.jpg";
				String tempImagUrl = Constants.IMG + imgUrl + Constants.IMG_END;
				Bitmap bitmap = BitmapFactory.decodeFile(filePath);
				textWrap(tempImagUrl, bitmap);
			} catch (JSONException e) {
			}
		};
	};

	private void textWrap(String imgUrl, Bitmap bitmap) {
		ImageSpan span = new ImageSpan(this, bitmap);
		SpannableString ss = new SpannableString(imgUrl);
		ss.setSpan(span, 0, imgUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		etSubjectContent.append("\r\n");
		etSubjectContent.append(ss);
		LogUtils.i("bm", "content--" + etSubjectContent.getText().toString());
	}

	private void uploadImage(HashMap<String, String> map, File[] file) {
		showProgressDialog();
		mSendUrl.httpRequestWithImage(map, file, Constants.CONTEXTPATH_OPENFIRE + ConstantNet.UPLOAD_IMAGE, new HttpCallBack() {

			@Override
			public void onSuccess(String resultJSON, ResultBean resultBean) {
				LogUtils.i(TAG, "uploadImage===>" + resultBean.toString());
				hideProgressDialog();
				if (resultBean.getResultCode() == 2222) {
					showToast("succ");
					Message mesg = mUploadHandler.obtainMessage();
					mesg.what = 2222;
					mesg.obj = resultBean.getData().toString();
					mUploadHandler.sendMessage(mesg);
				} else {
					showToast(resultBean.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				LogUtils.e(TAG, "uploadImage---e--->" + e);
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			return;
		}
		filePath = data.getStringExtra("filePath");
		switch (requestCode) {
		case Constants.REQUEST_CODE_COVER:
			subjectBackground = filePath;
			ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE + filePath, ivSubjectBackground,
					ImageLoaderHelper.mOptions);
			break;
		case Constants.REQUEST_CODE_PIC:
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
			File[] file = new File[1];
			file[0] = new File(filePath);
			uploadImage(map, file);
			break;
		}
	}
}
