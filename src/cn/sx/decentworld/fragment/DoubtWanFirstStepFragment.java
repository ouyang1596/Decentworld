/**
 * 
 */
package cn.sx.decentworld.fragment;

import java.io.File;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.PictureClipActivity_;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ToastUtil;

/**
 * @ClassName: DoubtVerifyFragment.java
 * @author: yj
 * @date: 2016年1月12日 下午3:25:20
 */
public class DoubtWanFirstStepFragment extends Fragment implements OnClickListener {
	private TextView tvCheck/* , tvDemand */;
	public String realName, IDCard;
	private EditText etIDCard;
	private TextView tvRealName;
	private ImageView ivIDCard;
	public String picPath;
	private SendUrl mSendUrl;
	private Handler mUploadHandle = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (mOnDoubtWanFirstClickListener != null) {
				mOnDoubtWanFirstClickListener.OnDoubtFirstClick(tvCheck);
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_doubt_first_step, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		mSendUrl = new SendUrl(getActivity());
		// tvDemand = (TextView) getActivity().findViewById(R.id.tv_demand);
		// tvDemand.setText(condition);
		tvCheck = (TextView) getActivity().findViewById(R.id.tv_check);
		tvCheck.setOnClickListener(this);
		ivIDCard = (ImageView) getActivity().findViewById(R.id.iv_idcard);
		ivIDCard.setOnClickListener(this);
		etIDCard = (EditText) getActivity().findViewById(R.id.et_id_card);
		tvRealName = (TextView) getActivity().findViewById(R.id.tv_real_name);
		tvRealName.setText(UserInfoEngine.getInstance().getUserInfo().getRealName());
	}

	public interface OnDoubtWanFirstClickListener {
		void OnDoubtFirstClick(View view);
	}

	private OnDoubtWanFirstClickListener mOnDoubtWanFirstClickListener;

	public void setOnDoubtWanFirstClickListener(OnDoubtWanFirstClickListener onDoubtWanFirstClickListener) {
		mOnDoubtWanFirstClickListener = onDoubtWanFirstClickListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_idcard:
			Intent intent = new Intent(getActivity(), PictureClipActivity_.class);
			intent.putExtra(Constants.CAMERA_ALBUM, 1);
			intent.putExtra(Constants.ASPECTX, 1);
			intent.putExtra(Constants.ASPECTY, 1);
			intent.putExtra(Constants.OUTPUTX, 400);
			intent.putExtra(Constants.OUTPUTY, 400);
			startActivityForResult(intent, Constants.REQUEST_CODE);
			break;
		default:
			if (tvRealName.length() <= 0) {
				ToastUtil.showToast("请先输入实名");
				return;
			}
			realName = tvRealName.getText().toString();
			if (etIDCard.length() <= 0) {
				ToastUtil.showToast("请先输入身份证号码");
				return;
			}
			IDCard = etIDCard.getText().toString();
			if (null == picPath) {
				ToastUtil.showToast("请先拍一张身份证");
				return;
			}
			uploadID();
			break;
		}
	}

	private void uploadID() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		map.put("realName", tvRealName.getText().toString());
		map.put("cardNum", etIDCard.getText().toString());
		File[] file = new File[1];
		file[0] = new File(picPath);
		showProgressDialog();
		mSendUrl.httpRequestWithImage(map, file, Constants.CONTEXTPATH + ConstantNet.API_WAN_START_CHECK, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				hideProgressDialog();
				if (2222 == msg.getResultCode()) {
					mUploadHandle.sendEmptyMessage(msg.getResultCode());
				} else {
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
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
		if (requestCode == Constants.REQUEST_CODE) {
			picPath = data.getStringExtra("filePath");
			int fileLength = ImageUtils.fileLength(picPath);
			Bitmap bitmap = getBitmap(picPath, fileLength);
			ivIDCard.setImageBitmap(bitmap);
		}
	}

	/**
	 * 获取bitmap，超过2M，进行缩小
	 * */
	private Bitmap getBitmap(String picPath, int fileLength) {
		Bitmap bitmap;
		if (fileLength > 1024 * 1024 * 1.5) {
			bitmap = ImageUtils.scalePic(picPath);
		} else {
			bitmap = BitmapFactory.decodeFile(picPath);
		}
		return bitmap;
	}

	private void showToast(final String data) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ToastUtil.showToast(data);
			}
		});
	}

	private ProgressDialog mProDialog;

	private void showProgressDialog() {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (null == mProDialog) {
					mProDialog = ProgressDialog.show(getActivity(), null, "loading");
				} else {
					mProDialog.show();
				}
			}
		});
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
}
