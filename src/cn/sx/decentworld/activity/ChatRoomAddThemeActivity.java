package cn.sx.decentworld.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.ThemeAdapter;
import cn.sx.decentworld.adapter.ThemeAdapter.OnThemeClickListener;
import cn.sx.decentworld.bean.SubjectBean;
import cn.sx.decentworld.bean.ThemeBean;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.ChatRoomInfoSettingAndGetting;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ViewUtil;
import cn.sx.decentworld.widget.ScrollLayout;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_chatroom_add)
public class ChatRoomAddThemeActivity extends BaseFragmentActivity implements
		OnClickListener, OnCheckedChangeListener {
	@ViewById(R.id.root_activity_edit)
	LinearLayout llEdit;
	@ViewById(R.id.rb_fee_scale)
	TextView tvFeeScale;
	@ViewById(R.id.rg_fee_Scale)
	RadioGroup rgFeeScale;
	// @ViewById(R.id.rb_scale)
	// RadioButton rbScale;
	@ViewById(R.id.rb_fixed)
	RadioButton rbFixed;
	@ViewById(R.id.rg_content)
	RadioGroup rgFeeContent;
	@ViewById(R.id.et_fee)
	EditText etFee;
	@ViewById(R.id.tv_subj_name)
	EditText etSubjectName;
	@ViewById(R.id.rb_content)
	RadioButton rbContent;
	@ViewById(R.id.rb_fee_scale)
	RadioButton rbFeeScale;
	@ViewById(R.id.sl_fee)
	public ScrollLayout slFee;
	@ViewById(R.id.tv_send)
	TextView tvSend;
	@ViewById(R.id.iv_cover)
	ImageView ivCover;
	@ViewById(R.id.tv_percent)
	TextView tvPercent;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.btn_content_save)
	Button btnContentSave;
	@ViewById(R.id.btn_fee_scale_save)
	Button btnFeeScaleSave;
	private static final int REQUEST_CODE_COVER = 100;
	private static final int REQUEST_CODE_PIC = 200;
	public static final int GET_CURRENT_SUBJECT = 1;
	public static final int SET_CHARGE_AMOUNT = 2;
	private boolean ifFeeScaleShow;
	private boolean isScaleOrFixed;// true Scale,false fixed
	@Bean
	ToastComponent toast;
	File[] file = new File[4];
	private String subjectBackground;
	@Bean
	ChatRoomInfoSettingAndGetting chatRoomInfoSettingAndGetting;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			finish();
		};
	};

	@AfterViews
	public void init() {
		ViewUtil.scaleContentView(llEdit);
		CGetIntent();
		initData();
		initAdapter();
		getNewTheme();
		rgFeeScale.setOnClickListener(this);
		tvFeeScale.setOnClickListener(this);
		rbFixed.setOnClickListener(this);
		etFee.addTextChangedListener(mTextWatcher);
		rgFeeContent.setOnCheckedChangeListener(this);
		rgFeeScale.setOnCheckedChangeListener(this);
		tvSend.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		ivCover.setOnClickListener(this);
		btnFeeScaleSave.setOnClickListener(this);
		btnContentSave.setOnClickListener(this);
	}

	private String roomID;

	private void CGetIntent() {
		roomID = getIntent().getStringExtra(ChatRoomMeActivity.ROOMID);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.rg_fee_Scale:
			if (ifFeeScaleShow) {
				rgFeeScale.setVisibility(View.GONE);
			} else {
				rgFeeScale.setVisibility(View.VISIBLE);
			}
			break;

		case R.id.tv_send:
			addNewTheme();
			// switch (rgFeeContent.getCheckedRadioButtonId()) {
			// case R.id.rb_content:
			// addNewTheme();
			// break;
			//
			// case R.id.tv_fee_scale:
			// if (etFee.length() <= 0) {
			// toast.show("请先填写数值");
			// return;
			// }
			// Float count = Float.valueOf(etFee.getText().toString());
			// if (isScaleOrFixed) {
			// count = count / 100.0f;
			// }
			// HashMap<String, String> map = new HashMap<String, String>();
			// map.put("roomID", roomID);
			// map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
			// map.put("amount", "" + count);
			// chatRoomInfoSettingAndGetting.setChargeAmount(map, mHandler);
			// break;
			// }
			break;
		case R.id.iv_cover:
			Intent intent = new Intent(getApplicationContext(),
					TakePhotosAndpictureSingleActivity.class);
			intent.putExtra(Constants.ASPECTX, Constants.CHAT_ROOM_SEVEN);
			intent.putExtra(Constants.ASPECTY, Constants.CHAT_ROOM_FOUR);
			intent.putExtra(Constants.OUTPUTX,
					Constants.CHAT_ROOM_SEVEN_HUNDRED);
			intent.putExtra(Constants.OUTPUTY,
					Constants.CHAT_ROOM_OUTPUT_FOUR_HUNDRED);
			startActivityForResult(intent, REQUEST_CODE_COVER);
			break;
		case R.id.iv_back:
			finish();
			break;
		case R.id.btn_content_save:
			saveNewTheme();
			break;
		case R.id.btn_fee_scale_save:
			saveChargeAmount();
			break;
		}
	}

	private TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence data, int arg1, int arg2,
				int arg3) {
			if (isScaleOrFixed) {
				Float feeCount = null;
				if (null != data.toString() && !("".equals(data.toString()))) {
					feeCount = Float.valueOf(data.toString());
				}
				if (feeCount == null) {
					return;
				}
				if (feeCount > 100 || feeCount < 0) {
					etFee.setText("");
				}
				LogUtils.e("bm", "---" + feeCount);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void afterTextChanged(Editable arg0) {

		}
	};

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int arg1) {
		switch (radioGroup.getId()) {
		case R.id.rg_content:
			switch (radioGroup.getCheckedRadioButtonId()) {
			case R.id.rb_content:
				slFee.setToScreen(0);
				break;

			case R.id.rb_fee_scale:
				slFee.setToScreen(1);
				break;
			}
			break;
		case R.id.rg_fee_Scale:
			switch (radioGroup.getCheckedRadioButtonId()) {
			// case R.id.rb_scale:
			// isScaleOrFixed = true;
			// tvPercent.setVisibility(View.VISIBLE);
			// break;
			case R.id.rb_fixed:
				isScaleOrFixed = false;
				tvPercent.setVisibility(View.GONE);
				break;
			}
			break;
		}
	}

	@ViewById(R.id.lv_add_new_theme)
	ListView lvAddNewTheme;
	ThemeAdapter adapter;
	Integer position;
	private List<ThemeBean> mData;

	private void initAdapter() {
		adapter = new ThemeAdapter(this, mData, true);
		View view = View.inflate(mContext, R.layout.item_edit_theme_bottom,
				null);
		lvAddNewTheme.addFooterView(view);
		lvAddNewTheme.setAdapter(adapter);
		adapter.setOnThemeClickListener(new OnThemeClickListener() {

			@Override
			public void onClick(View view) {
				position = (Integer) view.getTag(Constants.ITEM_POSITION);
				switch (view.getId()) {
				case R.id.iv_add_pic:
					Intent intent = new Intent(getApplicationContext(),
							TakePhotosAndpictureSingleActivity.class);
					intent.putExtra(Constants.ASPECTX,
							Constants.CHAT_ROOM_SEVEN);
					intent.putExtra(Constants.ASPECTY, Constants.CHAT_ROOM_FOUR);
					intent.putExtra(Constants.OUTPUTX,
							Constants.CHAT_ROOM_SEVEN_HUNDRED);
					intent.putExtra(Constants.OUTPUTY,
							Constants.CHAT_ROOM_OUTPUT_FOUR_HUNDRED);
					startActivityForResult(intent, REQUEST_CODE_PIC);
					break;
				}
			}
		});
		lvAddNewTheme.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (position == adapter.getCount()) {
					mData.add(new ThemeBean());
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	private void initData() {
		mData = new ArrayList<ThemeBean>();
	}

	public void addNewTheme() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (null == subjectBackground) {
			toast.show("请先选择一张背景图片");
			return;
		}
		if (etSubjectName.length() <= 0) {
			toast.show("请先填写聊天室名字");
			return;
		}
		if (etFee.length() <= 0) {
			toast.show("请先填写收费标准");
			return;
		}
		file[0] = new File(subjectBackground);
		map.put("subjectName", etSubjectName.getText().toString());
		map.put("roomID", roomID);
		map.put("ownerID", DecentWorldApp.getInstance().getDwID());
		map.put("chargeAmount", "" + etFee.getText().toString());
		for (int i = 0; i < mData.size(); i++) {
			ThemeBean theme = mData.get(i);
			if (null != theme.picPath) {
				file[1 + i] = new File(theme.picPath);
			}
			switch (i) {
			case 0:
				if (null != theme.subjectContent) {
					map.put("subjectContent", theme.subjectContent);
				}
				break;
			case 1:
				if (null != theme.subjectContent) {
					map.put("subjectContent1", theme.subjectContent);
				}
				break;
			case 2:
				if (null != theme.subjectContent) {
					map.put("subjectContent2", theme.subjectContent);
				}
				break;
			}
		}
		chatRoomInfoSettingAndGetting.submitImageWithParams(map, file,
				Constants.API_CREATE_SUBJECT, mHandler);
	}

	public void saveChargeAmount() {
		SubjectBean info = SubjectBean.queryByRoomIDAndIsNewOrEdit(roomID,
				Constants.ADD_NEW_THEME);
		if (null == info) {
			info = new SubjectBean(roomID, Constants.ADD_NEW_THEME);
		}
		switch (rgFeeScale.getCheckedRadioButtonId()) {
		case R.id.rb_fixed:
			info.isFixedOrPercent = Constants.FIXED;
			break;
		// case R.id.rb_scale:
		// info.isFixedOrPercent = Constants.PERCENT;
		// break;
		}
		if (etFee.length() > 0) {
			info.chargeAmount = etFee.getText().toString();
			info.save();
		}
		toast.show("已暂存");
	}

	public void saveNewTheme() {
		SubjectBean info = SubjectBean.queryByRoomIDAndIsNewOrEdit(roomID,
				Constants.ADD_NEW_THEME);
		if (null == info) {
			info = new SubjectBean(roomID, Constants.ADD_NEW_THEME);
		}
		if (null != subjectBackground) {
			info.subjectBackground = subjectBackground;
		} else {
			info.subjectBackground = null;
		}
		if (etSubjectName.length() > 0) {
			info.subjectName = etSubjectName.getText().toString();
		} else {
			info.subjectName = null;
		}
		for (int i = 0; i < mData.size(); i++) {
			ThemeBean theme = mData.get(i);
			if (null != theme.picPath) {
				switch (i) {
				case 0:
					info.imgUrl = theme.picPath;
					break;

				case 1:
					info.imgUrl1 = theme.picPath;
					break;
				case 2:
					info.imgUrl2 = theme.picPath;
					break;
				}
			} else {
				switch (i) {
				case 0:
					info.imgUrl = null;
					break;

				case 1:
					info.imgUrl1 = null;
					break;
				case 2:
					info.imgUrl2 = null;
					break;
				}
			}
			if (null != theme.subjectContent) {
				switch (i) {
				case 0:
					info.subjectContent = theme.subjectContent;
					break;
				case 1:
					info.subjectContent1 = theme.subjectContent;
					break;
				case 2:
					info.subjectContent2 = theme.subjectContent;
					break;
				}
			} else {
				switch (i) {
				case 0:
					info.subjectContent = null;
					break;
				case 1:
					info.subjectContent1 = null;
					break;
				case 2:
					info.subjectContent2 = null;
					break;
				}
			}

		}
		info.save();
		toast.show("已暂存");
	}

	public void getNewTheme() {
		SubjectBean info = SubjectBean.queryByRoomIDAndIsNewOrEdit(roomID,
				Constants.ADD_NEW_THEME);
		if (null == info) {
			return;
		}
		if (null != info.subjectBackground) {
			subjectBackground = info.subjectBackground;
			// Picasso.with(mContext).load(new File(subjectBackground))
			// .into(ivCover);
			ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE
					+ subjectBackground, ivCover, ImageLoaderHelper.mOptions);
		}
		if (null != info.subjectName) {
			etSubjectName.setText(info.subjectName);
		}
		if (null != info.imgUrl || null != info.subjectContent) {
			ThemeBean themeBean = new ThemeBean();
			themeBean.picPath = info.imgUrl;
			themeBean.subjectContent = info.subjectContent;
			mData.add(themeBean);
		}
		if (null != info.imgUrl1 || null != info.subjectContent1) {
			ThemeBean themeBean = new ThemeBean();
			themeBean.picPath = info.imgUrl1;
			themeBean.subjectContent = info.subjectContent1;
			mData.add(themeBean);
		}
		if (null != info.imgUrl2 || null != info.subjectContent2) {
			ThemeBean themeBean = new ThemeBean();
			themeBean.picPath = info.imgUrl2;
			themeBean.subjectContent = info.subjectContent2;
			mData.add(themeBean);
		}
		adapter.notifyDataSetChanged();
		if (null != info.chargeAmount && null != info.isFixedOrPercent) {
			if (Constants.FIXED.equals(info.isFixedOrPercent)) {
				rbFixed.setChecked(true);
			} else if (Constants.PERCENT.equals(info.isFixedOrPercent)) {
				// rbScale.setChecked(true);
			}
			etFee.setText(info.chargeAmount);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			return;
		}
		String filePath = data.getStringExtra("filePath");
		switch (requestCode) {
		case REQUEST_CODE_COVER:
			subjectBackground = filePath;
			// Picasso.with(mContext).load(new File(filePath)).into(ivCover);
			ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE
					+ filePath, ivCover, ImageLoaderHelper.mOptions);
			break;

		case REQUEST_CODE_PIC:
			ThemeBean theme = mData.get(position);
			theme.picPath = filePath;
			adapter.notifyDataSetChanged();
			break;
		}
	}
}
