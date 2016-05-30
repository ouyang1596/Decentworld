/**
 * 
 */
package cn.sx.decentworld.fragment.chatroom;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatRoomAddThemeActivity;
import cn.sx.decentworld.activity.ChatRoomEditActivity;
import cn.sx.decentworld.activity.PictureClipActivity_;
import cn.sx.decentworld.bean.SubjectBean;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.dialog.CameraAlbumDialogFragment;
import cn.sx.decentworld.dialog.CameraAlbumDialogFragment.OnCameraAlbumClickListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ToastUtil;

/**
 * @ClassName: ChatRoomAddThemeContentFragment.java
 * @Description:
 * @author: yj
 * @date: 2016年3月30日 上午10:40:28
 */
public class ChatRoomAddThemeContentFragment extends Fragment implements OnClickListener, OnCameraAlbumClickListener {
	private static final String TAG = "ChatRoomAddThemeContentFragment";
	public ImageView ivCover;// 主题封面
	private String cover;
	public EditText etSubjectName, etSubjectContent;
	private Button btnPhotos, btnSave;
	private CameraAlbumDialogFragment cameraAlbumDialogFragment;
	private String filePath;
	private SendUrl mSendUrl;
	private Activity mActivity;
	SubjectBean mSubjectBean;
	private String roomID;
	public int isNewOrEdit;// 判断是添加主题还是修改主题 0新建 1修改
	private String subjectContent;
	public int width;// etSubjectContent的宽度

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.item_add_theme, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		mActivity = getActivity();
		mSendUrl = new SendUrl(getActivity());
		ivCover = (ImageView) getActivity().findViewById(R.id.iv_audio_cover);
		ivCover.setOnClickListener(this);
		etSubjectName = (EditText) getActivity().findViewById(R.id.et_subject_name);
		etSubjectContent = (EditText) getActivity().findViewById(R.id.et_subject_content);
		btnPhotos = (Button) getActivity().findViewById(R.id.btn_photo);
		btnPhotos.setOnClickListener(this);
		btnSave = (Button) getActivity().findViewById(R.id.btn_content_save);
		btnSave.setOnClickListener(this);
		queryDatabases();
		etSubjectContent.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				width = etSubjectContent.getWidth();
				LogUtils.d(TAG, "width " + width);
				etSubjectContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			}
		});
	}

	private void queryDatabases() {
		if (mActivity instanceof ChatRoomAddThemeActivity) {
			ChatRoomAddThemeActivity activity = (ChatRoomAddThemeActivity) mActivity;
			roomID = activity.getRoomID();
		} else if (mActivity instanceof ChatRoomEditActivity) {
			ChatRoomEditActivity activity = (ChatRoomEditActivity) mActivity;
			roomID = activity.getRoomID();
		}
		mSubjectBean = SubjectBean.queryByRoomIDAndIsNewOrEdit(roomID, isNewOrEdit);
		if (null == mSubjectBean) {
			return;
		}
		if (null != mSubjectBean.subjectBackground) {
			ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE + mSubjectBean.subjectBackground, ivCover,
					ImageLoaderHelper.mOptions);
		}
		etSubjectName.setText(mSubjectBean.subjectName);
		if (null != mSubjectBean.subjectContent) {
			// Pattern p = Pattern.compile(Constants.IMG);
			// Matcher m = p.matcher(mSubjectBean.subjectContent);
			// while (m.find()) {
			//
			// }
			// subjectContent = mSubjectBean.subjectContent;
			// subjectContent = subjectContent.replace("\r\n", "br/");
			// Spanned spanned = Html
			// .fromHtml(mSubjectBean.subjectContent, new
			// MyImageGetter(getActivity(), etSubjectContent), null);
			// etSubjectContent.setText(spanned);
			// etSubjectContent.setMovementMethod(LinkMovementMethod.getInstance());
			// ImageSpan[] spans = spanned.getSpans(0, spanned.length(),
			// ImageSpan.class);
			// for (int i = 0; i < spans.length; i++) {
			// LogUtils.i("bm", "src--" + spans[i].getSource());
			// }
		}
	}

	public String getCover() {
		return cover;
	}

	public String getSubjectName() {
		return etSubjectName.getText().toString();
	}

	public String getSubjectContent() {
		return etSubjectContent.getText().toString();
	}

	/**
	 * 显示拍照相册对话框
	 * */
	private void showCameraAlbumDialog() {
		if (null == cameraAlbumDialogFragment) {
			cameraAlbumDialogFragment = new CameraAlbumDialogFragment();
		}
		cameraAlbumDialogFragment.setOnCameraAlbumClickListener(ChatRoomAddThemeContentFragment.this);
		cameraAlbumDialogFragment.show(getActivity().getSupportFragmentManager(), "cameraAlbumDialogFragment");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_audio_cover:
			requestCode = Constants.REQUEST_CODE_COVER;
			showCameraAlbumDialog();
			break;
		case R.id.btn_photo:
			requestCode = Constants.REQUEST_CODE_PIC;
			showCameraAlbumDialog();
			break;
		case R.id.btn_content_save:
			mSubjectBean = SubjectBean.queryByRoomIDAndIsNewOrEdit(roomID, isNewOrEdit);
			if (null == mSubjectBean) {
				mSubjectBean = new SubjectBean(roomID, isNewOrEdit);
			}
			mSubjectBean.subjectBackground = cover;
			mSubjectBean.subjectContent = etSubjectContent.getText().toString();
			mSubjectBean.subjectName = etSubjectName.getText().toString();
			mSubjectBean.save();
			break;
		}
	}

	private int requestCode;

	@Override
	public void onCameraAlbumClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.tv_camera:
			intent = new Intent(getActivity(), PictureClipActivity_.class);
			intent.putExtra(Constants.CAMERA_ALBUM, 1);
			intent.putExtra(Constants.ASPECTX, 7);
			intent.putExtra(Constants.ASPECTY, 4);
			intent.putExtra(Constants.OUTPUTX, 700);
			intent.putExtra(Constants.OUTPUTY, 400);
			startActivityForResult(intent, requestCode);
			break;
		case R.id.tv_album:
			intent = new Intent(getActivity(), PictureClipActivity_.class);
			intent.putExtra(Constants.CAMERA_ALBUM, 0);
			intent.putExtra(Constants.ASPECTX, 7);
			intent.putExtra(Constants.ASPECTY, 4);
			intent.putExtra(Constants.OUTPUTX, 700);
			intent.putExtra(Constants.OUTPUTY, 400);
			startActivityForResult(intent, requestCode);
			break;
		}
	}

	private Handler mUploadHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				JSONObject object = new JSONObject(msg.obj.toString());
				String imgUrl = object.getString("file");
				// String imgUrl =
				// "http://112.74.13.117/data/487066/icon/icon.jpg";
				String tempImagUrl = Constants.IMG + imgUrl + Constants.IMG_END;
				Bitmap bitmap = BitmapFactory.decodeFile(filePath);
				LogUtils.d(TAG, "width " + width + "bitmap.getWidth() " + bitmap.getWidth());
				if (bitmap.getWidth() >= width) {
					float scaleWidth = width / bitmap.getWidth() * 0.9f;
					bitmap = ImageUtils.scaleImage(bitmap, scaleWidth, scaleWidth);
				}
				textWrap(tempImagUrl, bitmap);
			} catch (JSONException e) {

			}
		};
	};

	private void textWrap(String imgUrl, Bitmap bitmap) {
		ImageSpan span = new ImageSpan(getActivity(), bitmap);
		SpannableString ss = new SpannableString(imgUrl);
		ss.setSpan(span, 0, imgUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// if (CommUtil.isNotBlank(etSubjectContent.getText().toString())) {
		// etSubjectContent.append("\r\n");
		// }
		// etSubjectContent.append(ss);
		// 将选择的图片追加到EditText中光标所在位置
		int index = etSubjectContent.getSelectionStart();
		// 获取光标所在位置
		Editable editable = etSubjectContent.getEditableText();
		if (index < 0 || index >= editable.length()) {
			editable.append(ss);
		} else {
			editable.insert(index, ss);
		}
		LogUtils.i("bm", "content--" + etSubjectContent.getText().toString());
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
			cover = filePath;
			ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE + filePath, ivCover, ImageLoaderHelper.mOptions);
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

	private void uploadImage(HashMap<String, String> map, File[] file) {
		showProgressDialog();
		mSendUrl.httpRequestWithImage(map, file, Constants.CONTEXTPATH_OPENFIRE + ConstantNet.UPLOAD_IMAGE, new HttpCallBack() {

			@Override
			public void onSuccess(String resultJSON, ResultBean resultBean) {
				hideProgressDialog();
				if (resultBean.getResultCode() == 2222) {
					LogUtils.d(TAG, "uploadImage " + resultJSON);
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
				LogUtils.e(TAG, "e--" + e);
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
			}
		});
	}

	private ProgressDialog mProDialog;

	private void showProgressDialog() {
		if (null == mProDialog) {
			mProDialog = ProgressDialog.show(getActivity(), null, "loading");
		} else {
			mProDialog.show();
		}
	}

	private void hideProgressDialog() {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (null != mProDialog) {
					mProDialog.hide();
				}
			}
		});
	}

	private void showToast(final String data) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ToastUtil.showToast(data);
			}
		});
	}
}
