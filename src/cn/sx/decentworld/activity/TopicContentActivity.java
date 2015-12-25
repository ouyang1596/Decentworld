package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.ThemeAdapter;
import cn.sx.decentworld.adapter.ThemeAdapter.OnThemeClickListener;
import cn.sx.decentworld.bean.ThemeBean;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.request.ChatRoomInfoSettingAndGetting;
import cn.sx.decentworld.utils.ImageLoaderHelper;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_topic_content)
public class TopicContentActivity extends BaseFragmentActivity {
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.lv_add_new_theme)
	ListView lvAddNewTheme;
	@ViewById(R.id.tv_subj_name)
	EditText etSubjectName;
	@ViewById(R.id.iv_cover)
	ImageView ivCover;
	@ViewById(R.id.iv_head)
	ImageView ivHead;
	@ViewById(R.id.btn_OK)
	Button btnOK;
	@ViewById(R.id.tv_introduce)
	TextView tvIntroduce;
	public static final int GET_CURRENT_SUBJECT = 1;
	public static final int CHATROOM_ENTER = 2;
	private static final int REQUEST_CODE_PIC = 200;
	@Bean
	ToastComponent toast;
	@Bean
	ChatRoomInfoSettingAndGetting chatRoomInfoSettingAndGetting;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_CURRENT_SUBJECT:
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					JSONObject jsonData = object.getJSONObject("subject");
					JSONObject ownerObject = object.getJSONObject("owner");
					String ownerProfile = ownerObject.getString("ownerProfile");
					tvIntroduce.setText(ownerProfile);
					String ownerIcon = ownerObject.getString("ownerIcon");
					if (CommUtil.isNotBlank(ownerIcon)) {
						// Picasso.with(mContext).load(ownerIcon)
						// .error(R.drawable.solid_heart).into(ivHead);
						ImageLoaderHelper.mImageLoader.displayImage(ownerIcon,
								ivHead, ImageLoaderHelper.mOptions);
					}
					String roomID = jsonData.getString("roomId");
					String picBg = jsonData.getString("subjectBackground");
					// Picasso.with(mContext).load(picBg).config(Config.RGB_565)
					// .into(ivCover);
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
			case CHATROOM_ENTER:
				Intent intent = new Intent(mContext,
						ChatRoomChatActivity_.class);
				intent.putExtra("json_data", msg.obj.toString());
				startActivity(intent);
				break;
			default:
				finish();
				break;
			}

		};
	};
	ThemeAdapter adapter;
	Integer position;
	private List<ThemeBean> mData;

	private void initAdapter() {
		mData = new ArrayList<ThemeBean>();
		adapter = new ThemeAdapter(this, mData, false);
		lvAddNewTheme.setAdapter(adapter);
		adapter.setOnThemeClickListener(new OnThemeClickListener() {

			@Override
			public void onClick(View view) {
				position = (Integer) view.getTag(Constants.ITEM_KEY);
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

	@AfterViews
	public void init() {
		etSubjectName.setEnabled(false);
		TGetIntent();
		initAdapter();
		initRequest();
		tvTitle.setText("话题内容");
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnOK.setText("进入聊天室");
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				requestEnterChatRoom();
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("roomID", roomID);
		chatRoomInfoSettingAndGetting.getCurrentSubject(map, mHandler);
	}

	private SendUrl sendUrl;

	public void requestEnterChatRoom() {
		showProgressDialog();
		sendUrl = new SendUrl(mContext);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dwID", DecentWorldApp.getInstance().getDwID());
		map.put("roomID", roomID);
		map.put("nickName", nickName);
		sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH_OPENFIRE
				+ "/joinChatRoom", Method.GET, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				hideProgressDialog();
				if (msg.getResultCode() == 2222) {
					Message message = new Message();
					message.what = CHATROOM_ENTER;
					message.obj = msg.getData().toString();
					mHandler.sendMessage(message);
				} else {
					showToast(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				hideProgressDialog();
				showToast(Constants.NET_WRONG);
			}

			private void showToast(final String data) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						toast.show(data);
					}
				});
			}
		});
	}

	private ProgressDialog mProDialog;

	private void showProgressDialog() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (null == mProDialog) {
					mProDialog = ProgressDialog.show(mContext, null, "loading");
				} else {
					mProDialog.show();
				}
			}
		});
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

	private String roomID, nickName;

	private void TGetIntent() {
		roomID = getIntent().getStringExtra("roomID");
		nickName = getIntent().getStringExtra("nickName");
	}
}
