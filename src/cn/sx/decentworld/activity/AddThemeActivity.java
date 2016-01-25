package cn.sx.decentworld.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_theme)
public class AddThemeActivity extends BaseFragmentActivity implements
		OnClickListener {
	@ViewById(R.id.lv_add_new_theme)
	ListView lvAddNewTheme;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.tv_send)
	TextView tvSend;
	@ViewById(R.id.iv_cover)
	ImageView ivCover;
	@ViewById(R.id.tv_subj_name)
	EditText etSubjectName;
	@ViewById(R.id.btn_content_save)
	Button btnContentSave;
	private static final int REQUEST_CODE_COVER = 100;
	private static final int REQUEST_CODE_PIC = 200;
	@Bean
	ToastComponent toast;
	private List<ThemeBean> mData;
	private String roomID;
	private String subjectBackground;
	@Bean
	ChatRoomInfoSettingAndGetting chatroomInfoSettingAndGetting;
	File[] file = new File[4];
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			finish();
		};
	};

	@AfterViews
	public void init() {
		AGetIntent();
		initAdapter();
		getNewTheme();
		setOnClickListener();
	}

	private void setOnClickListener() {
		ivCover.setOnClickListener(this);
		tvSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				HashMap<String, String> map = new HashMap<String, String>();
				if (null == subjectBackground) {
					toast.show("请先选择一张背景图片");
					return;
				}
				if (etSubjectName.length() <= 0) {
					toast.show("请先填写聊天室名字");
					return;
				}
				file[0] = new File(subjectBackground);
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
				chatroomInfoSettingAndGetting.submitImageWithParams(map, file,
						Constants.API_CREATE_SUBJECT, mHandler);
			}
		});
		btnContentSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				saveNewTheme();
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void AGetIntent() {
		roomID = getIntent().getStringExtra(ChatRoomMeActivity.ROOMID);
		LogUtils.e("bm", "roomID==" + roomID);
	}

	ThemeAdapter adapter;
	Integer position;

	private void initAdapter() {
		mData = new ArrayList<ThemeBean>();
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
					// intent.putExtra(Constants.ASPECTX, 5);
					// intent.putExtra(Constants.ASPECTY, 1);
					// intent.putExtra(Constants.OUTPUTX, 500);
					// intent.putExtra(Constants.OUTPUTY, 100);
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
			// Picasso.with(mContext).load(new File(filePath))
			// .error(R.drawable.solid_heart).into(ivCover);
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

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
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
		}
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
}
