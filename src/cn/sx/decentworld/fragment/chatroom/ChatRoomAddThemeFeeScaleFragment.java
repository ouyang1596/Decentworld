/**
 * 
 */
package cn.sx.decentworld.fragment.chatroom;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatRoomAddThemeActivity;
import cn.sx.decentworld.activity.ChatRoomEditActivity;
import cn.sx.decentworld.bean.SubjectBean;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.ToastUtil;

import com.android.volley.Request.Method;

/**
 * @ClassName: ChatRoomAddThemeChargeAmountFragment.java
 * @Description:
 * @author: yj
 * @date: 2016年3月30日 上午10:41:16
 */
public class ChatRoomAddThemeFeeScaleFragment extends Fragment implements OnClickListener {
	public EditText etFee;
	private Button btnFeeSave;
	private SendUrl mSendUrl;
	private Activity mActivity;
	private String roomID;
	private SubjectBean mSubjectBean;
	public int isNewOrEdit;// 判断是添加主题还是修改主题 0新建 1修改
	public boolean isSetChargeAmount = false;// 是否设置了收费标准

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fee_scale, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		mActivity = getActivity();
		getRoomID();
		mSendUrl = new SendUrl(getActivity());
		etFee = (EditText) getActivity().findViewById(R.id.et_fee);
		etFee.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mSubjectBean = SubjectBean.queryByRoomIDAndIsNewOrEdit(roomID, isNewOrEdit);
				if (null == mSubjectBean) {
					mSubjectBean = new SubjectBean(roomID, isNewOrEdit);
				}
				mSubjectBean.chargeAmount = s.toString();
				mSubjectBean.save();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		btnFeeSave = (Button) getActivity().findViewById(R.id.btn_fee_scale_save);
		btnFeeSave.setOnClickListener(this);
		queryDatabases();
	}

	private void queryDatabases() {
		mSubjectBean = SubjectBean.queryByRoomIDAndIsNewOrEdit(roomID, isNewOrEdit);
		if (null == mSubjectBean) {
			return;
		}
		etFee.setText(mSubjectBean.chargeAmount);
	}

	private void getRoomID() {
		if (mActivity instanceof ChatRoomAddThemeActivity) {
			ChatRoomAddThemeActivity activity = (ChatRoomAddThemeActivity) mActivity;
			roomID = activity.getRoomID();
		} else if (mActivity instanceof ChatRoomEditActivity) {
			ChatRoomEditActivity activity = (ChatRoomEditActivity) mActivity;
			roomID = activity.getRoomID();
		}
	}

	public String getFee() {
		return etFee.getText().toString();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_fee_scale_save:
			if (etFee.length() <= 0) {
				ToastUtil.showToast("请填写聊天室收费标准");
				return;
			}
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
			map.put("roomID", roomID);
			map.put("amount", etFee.getText().toString());
			showProgressDialog();
			mSendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH_OPENFIRE + Constants.API_SET_CHARGE_AMOUNT, Method.GET,
					new HttpCallBack() {

						@Override
						public void onSuccess(String resultJSON, ResultBean resultBean) {
							hideProgressDialog();
							if (resultBean.getResultCode() == 2222)// 获取成功
							{
								isSetChargeAmount = true;
								showToast("设置成功");
							} else {
								showToast(resultBean.getMsg());
							}
						}

						@Override
						public void onFailure(String e) {
							hideProgressDialog();
							showToast(Constants.NET_WRONG);
						}
					});
			break;

		default:
			break;
		}
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
