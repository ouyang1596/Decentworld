/**
 * 
 */
package cn.sx.decentworld.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.PictureClipActivity_;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ToastUtil;

/**
 * @ClassName: DoubtVerifyFragment.java
 * @author: yj
 * @date: 2016年1月12日 下午3:25:20
 */
public class DoubtFirstStepFragment extends Fragment implements OnClickListener {
	private TextView tvCheck;
	public String realName, IDCard;
	private EditText etRealName, etIDCard;
	private ImageView ivIDCard;
	public String picPath;

	// private String
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_doubt_first_step, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		tvCheck = (TextView) getActivity().findViewById(R.id.tv_check);
		tvCheck.setOnClickListener(this);
		ivIDCard = (ImageView) getActivity().findViewById(R.id.iv_idcard);
		ivIDCard.setOnClickListener(this);
		etIDCard = (EditText) getActivity().findViewById(R.id.et_id_card);
		etRealName = (EditText) getActivity().findViewById(R.id.et_real_name);
	}

	public interface OnDoubtFirstClickListener {
		void OnDoubtFirstClick(View view);
	}

	private OnDoubtFirstClickListener mOnDoubtFirstClickListener;

	public void setOnDoubtFirstClickListener(
			OnDoubtFirstClickListener onDoubtFirstClickListener) {
		mOnDoubtFirstClickListener = onDoubtFirstClickListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_idcard:
			Intent intent = new Intent(getActivity(),
					PictureClipActivity_.class);
			intent.putExtra(Constants.CAMERA_ALBUM, 1);
			intent.putExtra(Constants.ASPECTX, 1);
			intent.putExtra(Constants.ASPECTY, 1);
			intent.putExtra(Constants.OUTPUTX, 400);
			intent.putExtra(Constants.OUTPUTY, 400);
			startActivityForResult(intent, Constants.REQUEST_CODE);
			break;

		default:
			if (etRealName.length() <= 0) {
				ToastUtil.showToast("请先输入实名");
				return;
			}
			realName = etRealName.getText().toString();
			if (etIDCard.length() <= 0) {
				ToastUtil.showToast("请先输入身份证号码");
				return;
			}
			IDCard = etIDCard.getText().toString();
			if (null == picPath) {
				ToastUtil.showToast("请先拍一张身份证");
				return;
			}
			if (mOnDoubtFirstClickListener != null) {
				mOnDoubtFirstClickListener.OnDoubtFirstClick(v);
			}
			break;
		}

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
}
