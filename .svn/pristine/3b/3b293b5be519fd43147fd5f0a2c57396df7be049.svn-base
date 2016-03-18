package cn.sx.decentworld.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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

@EActivity(R.layout.activity_edit)
public class ChatRoomEditActivity extends BaseFragmentActivity implements
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
	@ViewById(R.id.tv_percent)
	TextView tvPercent;
	@ViewById(R.id.btn_fee_scale_save)
	Button btnFeeScaleSave;
	@ViewById(R.id.btn_content_save)
	Button btnContentSave;
	@ViewById(R.id.rg_content)
	RadioGroup rgFeeContent;
	@ViewById(R.id.et_fee)
	EditText etFee;
	@ViewById(R.id.rb_content)
	RadioButton rbContent;
	@ViewById(R.id.rb_fee_scale)
	RadioButton rbFeeScale;
	@ViewById(R.id.iv_cover)
	ImageView ivCover;
	@ViewById(R.id.sl_fee)
	public ScrollLayout slFee;
	@ViewById(R.id.tv_send)
	TextView tvSend;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.lv_add_new_theme)
	ListView lvAddNewTheme;
	@ViewById(R.id.tv_subj_name)
	EditText etSubjectName;
	private static final int REQUEST_CODE_COVER = 100;
	private static final int REQUEST_CODE_PIC = 200;
	public static final int GET_CURRENT_SUBJECT = 1;
	public static final int SET_CHARGE_AMOUNT = 2;
	private boolean ifFeeScaleShow;
	private boolean isScaleOrFixed;// true Scale,false fixed
	@Bean
	ToastComponent toast;
	File[] file = new File[4];

	@Bean
	ChatRoomInfoSettingAndGetting chatRoomInfoSettingAndGetting;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_CURRENT_SUBJECT:
				try {
					LogUtils.i("bm", "data--" + msg.obj.toString());
					JSONObject object = new JSONObject(msg.obj.toString());
					String chargeAmount = object.getString("chargeAmount");
					etFee.setText(chargeAmount);
					JSONObject jsonData = object.getJSONObject("subject");
					String roomID = jsonData.getString("roomId");
					String picBg = jsonData.getString("subjectBackground");
					// Picasso.with(mContext).load(picBg).into(ivCover);
					ImageLoaderHelper.mImageLoader.displayImage(picBg, ivCover,
							ImageLoaderHelper.mOptions);
					String subjectName = jsonData.getString("subjectName");
					etSubjectName.setText(subjectName);
					ThemeBean themeBean = new ThemeBean();
					if (jsonData.has("subjectContent")) {
						String subjectContent = jsonData
								.getString("subjectContent");
						themeBean.subjectContent = subjectContent;
					}
					if (jsonData.has("imgUrl")) {
						String imgUrl = jsonData.getString("imgUrl");
						themeBean.picPath = imgUrl;
					}
					ThemeBean themeBean1 = new ThemeBean();
					if (jsonData.has("subjectContent1")) {
						String subjectContent1 = jsonData
								.getString("subjectContent1");
						themeBean1.subjectContent = subjectContent1;
					}
					if (jsonData.has("imgUrl1")) {
						String imgUrl1 = jsonData.getString("imgUrl1");
						themeBean1.picPath = imgUrl1;
					}
					ThemeBean themeBean2 = new ThemeBean();
					if (jsonData.has("subjectContent2")) {
						String subjectContent2 = jsonData
								.getString("subjectContent2");
						themeBean2.subjectContent = subjectContent2;
					}
					if (jsonData.has("imgUrl2")) {
						String imgUrl2 = jsonData.getString("imgUrl2");
						themeBean2.picPath = imgUrl2;
					}
					if (null != themeBean.picPath
							&& null != themeBean.subjectContent) {
						mData.add(themeBean);
					}
					if (null != themeBean1.picPath
							&& null != themeBean1.subjectContent) {
						mData.add(themeBean1);
					}
					if (null != themeBean2.picPath
							&& null != themeBean2.subjectContent) {
						mData.add(themeBean2);
					}
					adapter.setIfLocalNotifyDataSetChanged(false);
				} catch (JSONException e) {
					toast.show("解析错误");
				}
				break;
			default:
				finish();
				break;
			}
		};
	};

	@AfterViews
	public void init() {
		ViewUtil.scaleContentView(llEdit);
		// ImageLoaderHelper.initImageLoader(this);
		CGetIntent();
		initAdapter();
		SubjectBean subjectBean = SubjectBean.queryByRoomIDAndIsNewOrEdit(
				roomID, Constants.EDIT_NEW_THEME);
		if (null != subjectBean) {
			getNewTheme();
		} else {
			initRequest();
		}
		rgFeeScale.setOnClickListener(this);
		tvFeeScale.setOnClickListener(this);
		rbFixed.setOnClickListener(this);
		etFee.addTextChangedListener(mTextWatcher);
		rgFeeContent.setOnCheckedChangeListener(this);
		rgFeeScale.setOnCheckedChangeListener(this);
		tvSend.setOnClickListener(this);
		ivCover.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		btnContentSave.setOnClickListener(this);
		btnFeeScaleSave.setOnClickListener(this);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("roomID", roomID);
		chatRoomInfoSettingAndGetting.getCurrentSubject(map, mHandler);
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
			switch (rgFeeContent.getCheckedRadioButtonId()) {
			case R.id.rb_content:
				updataSubject();
				break;

			case R.id.rb_fee_scale:
				if (etFee.length() <= 0) {
					toast.show("请先填写数值");
					return;
				}
				Float count = Float.valueOf(etFee.getText().toString());
				if (isScaleOrFixed) {
					count = count / 100.0f;
				}
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("roomID", roomID);
				map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
				map.put("amount", "" + count);
				chatRoomInfoSettingAndGetting.setChargeAmount(map, mHandler);
				break;
			}
			break;
		case R.id.iv_cover:
			Intent intent = new Intent(getApplicationContext(),
					TakePhotosAndpictureSingleActivity.class);
			intent.putExtra(Constants.ASPECTX,
					Constants.CHAT_ROOM_SEVEN);
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

	private void updataSubject() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (null == coverPath) {
			toast.show("请先重新选择一张背景图片");
			return;
		}
		if (etSubjectName.length() <= 0) {
			toast.show("请先填写聊天室名字");
			return;
		}
		file[0] = new File(coverPath);
		map.put("subjectName", etSubjectName.getText().toString());
		map.put("roomID", roomID);
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
		LogUtils.e("bm", "roomID--" + roomID);
		chatRoomInfoSettingAndGetting.submitImageWithParams(map, file,
				Constants.API_UPDATA_SUBJECT, mHandler);
	}

	ThemeAdapter adapter;
	Integer position;
	private List<ThemeBean> mData;

	private void initAdapter() {
		mData = new ArrayList<ThemeBean>();
		adapter = new ThemeAdapter(this, mData, false);
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
					Intent intentPic = new Intent(getApplicationContext(),
							TakePhotosAndpictureActivity.class);
					intentPic.putExtra("max_count", Constants.CHOICE_ONE_PIC);
					startActivityForResult(intentPic, REQUEST_CODE_PIC);
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
					adapter.setIfLocalNotifyDataSetChanged(true);
				}
			}
		});
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

	public void addNewTheme() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (null == coverPath) {
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
		file[0] = new File(coverPath);
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
				Constants.EDIT_NEW_THEME);
		if (null == info) {
			info = new SubjectBean(roomID, Constants.EDIT_NEW_THEME);
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
				Constants.EDIT_NEW_THEME);
		if (null == info) {
			info = new SubjectBean(roomID, Constants.EDIT_NEW_THEME);
		}
		if (null != coverPath) {
			info.subjectBackground = coverPath;
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
				Constants.EDIT_NEW_THEME);
		if (null == info) {
			return;
		}
		if (null != info.subjectBackground) {
			coverPath = info.subjectBackground;
			// Picasso.with(mContext).load(new File(coverPath)).into(ivCover);
			ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE
					+ coverPath, ivCover, ImageLoaderHelper.mOptions);
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
				tvPercent.setVisibility(View.GONE);
			} else if (Constants.PERCENT.equals(info.isFixedOrPercent)) {
				// rbScale.setChecked(true);
				// tvPercent.setVisibility(View.VISIBLE);
			}
			etFee.setText(info.chargeAmount);
		}
	}

	private String coverPath;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			return;
		}
		String filePath = data.getStringExtra("filePath");
		switch (requestCode) {
		case REQUEST_CODE_COVER:
			coverPath = filePath;
			// Picasso.with(mContext).load(new File(filePath)).into(ivCover);
			ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE
					+ filePath, ivCover, ImageLoaderHelper.mOptions);
			LogUtils.i("bm", "tag-------------");
			break;
		case REQUEST_CODE_PIC:
			ThemeBean theme = mData.get(position);
			theme.picPath = filePath;
			adapter.setIfLocalNotifyDataSetChanged(true);
			break;
		}
		// ArrayList<String> picFiles = data.getExtras().getStringArrayList(
		// "pic_paths");
		// if (null == picFiles || picFiles.size() <= 0) {
		// return;
		// }
		// String picPath = picFiles.get(0);
		// switch (requestCode) {
		// case REQUEST_CODE_COVER:
		// coverPath = picPath;
		// Picasso.with(mContext).load(new File(picPath)).into(ivCover);
		// break;
		// case REQUEST_CODE_PIC:
		// ThemeBean theme = mData.get(position);
		// theme.picPath = picPath;
		// adapter.setIfLocalNotifyDataSetChanged(true);
		// break;
		// }
	}
}
