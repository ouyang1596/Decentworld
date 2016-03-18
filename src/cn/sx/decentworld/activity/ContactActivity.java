package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.ContactAdapter;
import cn.sx.decentworld.adapter.ContactAdapter.OnContactClickListener;
import cn.sx.decentworld.bean.ContactBean;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.AddFriendDialog;
import cn.sx.decentworld.dialog.AddFriendDialog.AddFriendListener;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.widget.CustomScrollView;
import cn.sx.decentworld.widget.CustomScrollView.ScrollViewListener;
import cn.sx.decentworld.widget.ListViewForScrollView;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_contact)
public class ContactActivity extends BaseFragmentActivity implements OnContactClickListener {
	@ViewById(R.id.lv_contact)
	ListViewForScrollView lvContact;
	@ViewById(R.id.lv_recommend)
	ListViewForScrollView lvRecommend;
	@ViewById(R.id.tv_header_title)
	TextView tvHeadTitle;
	@ViewById(R.id.iv_delete_back)
	ImageView ivBack;
	@ViewById(R.id.cScro)
	CustomScrollView cScro;
	@Bean
	ToastComponent toast;
	@Bean
	GetUserInfo getUserInfo;
	private String data;
	private List<ContactBean> contactBeans;
	private List<ContactBean> recommendbeans;
	public static final String JSON_DATA = "JSON_DATA";
	private static final String mSearchType = "3";
	private ContactAdapter contactAdapter;
	private ContactAdapter recommendAdapter;
	private SendUrl mSendUrl;
	private Handler getContactHandle = new Handler() {
		public void handleMessage(Message msg) {
			data = msg.obj.toString();
			LogUtils.i("bm", "data--" + data);
			parserJsonData();
		};
	};

	@AfterViews
	public void init() {
		CGetIntent();
		initData();
		uploadMobiles();
	}

	private void uploadMobiles() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phoneNums", phoneNums);
		LogUtils.i("bm", "phoneNums--" + phoneNums);
		getUserInfo.uploadContact(map, getContactHandle);
	}

	private HashMap<String, String> mobileNameMap;

	private String phoneNums;

	@SuppressWarnings("unchecked")
	private void CGetIntent() {
		phoneNums = getIntent().getStringExtra("phoneNums");
		mobileNameMap = (HashMap<String, String>) getIntent().getSerializableExtra("mobileNameMap");
	}

	private void initData() {
		mSendUrl = new SendUrl(this);
		contactBeans = new ArrayList<ContactBean>();
		recommendbeans = new ArrayList<ContactBean>();
		tvHeadTitle.setText("您可能认识的人");
		recommendAdapter = new ContactAdapter(this, recommendbeans);
		recommendAdapter.setOnContactClickListener(this);
		// View view = View.inflate(this,
		// R.layout.item_contact_recommend_footer, null);
		// lvRecommend.addFooterView(view);
		lvRecommend.setAdapter(recommendAdapter);
		lvRecommend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == recommendAdapter.getCount()) {
					uploadMobiles();
				}
			}
		});
		View vHead = View.inflate(mContext, R.layout.item_friends_contact, null);
		lvContact.addHeaderView(vHead);
		contactAdapter = new ContactAdapter(this, contactBeans);
		contactAdapter.setOnContactClickListener(this);
		lvContact.setAdapter(contactAdapter);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		cScro.setOnScrollViewChangeListener(new ScrollViewListener() {

			@Override
			public void onYScrolled(int scrollY) {
				LogUtils.i("bm", "scrollY--" + scrollY);
			}
		});
		getFriendsString();
	}

	private String friendsString;

	private void getFriendsString() {
		List<ContactUser> temp = ContactUser.queryAll();
		if (null == temp) {
			return;
		}
		friendsString = "[" + DecentWorldApp.getInstance().getDwID() + "]";
		for (int i = 0; i < temp.size(); i++) {
			friendsString += "[" + temp.get(i).getFriendID() + "]";
		}
		LogUtils.i("bm", "friendsString--" + friendsString);
	}

	private void parserJsonData() {
		try {
			JSONObject object = new JSONObject(data);
			JSONArray contactArray = object.getJSONArray("recommend_contact");
			LogUtils.e("bm", "array--" + contactArray.toString());
			List<ContactBean> contacts = (List<ContactBean>) JsonUtils.json2BeanArray(contactArray.toString(), ContactBean.class);
			replaceName(contacts);
			JSONArray recommendArray = object.getJSONArray("recommend_random");
			List<ContactBean> recommends = (List<ContactBean>) JsonUtils.json2BeanArray(recommendArray.toString(),
					ContactBean.class);
			for (int i = 0; i < recommends.size(); i++) {
				if (friendsString.contains(recommends.get(i).dwID)) {
					recommends.remove(i);
					i = i - 1;
				}
			}
			if (contacts.size() > recommends.size()) {
				int removeSize = (contacts.size() - recommends.size()) / 2;
				for (int i = 0; i < removeSize; i++) {
					recommends.add(contacts.get(i));
				}
				for (int i = 0; i < removeSize; i++) {
					contacts.remove(0);
				}
			}
			if (recommends.size() > contacts.size()) {
				int removeSize = (recommends.size() - contacts.size()) / 2;
				for (int i = 0; i < removeSize; i++) {
					contacts.add(recommends.get(i));
				}
				for (int i = 0; i < removeSize; i++) {
					recommends.remove(0);
				}
			}
			if (contacts.size() > 0) {
				if (contactBeans.size() <= 0) {
					contactBeans.addAll(contacts);
					contactAdapter.notifyDataSetChanged();
				}
				recommendbeans.addAll(recommends);
				recommendAdapter.notifyDataSetChanged();
			} else {
				finish();
			}
		} catch (JSONException e) {
			toast.show("解析错误");
		}
	}

	private void replaceName(List<ContactBean> datas) {
		for (int i = 0; i < datas.size(); i++) {
			if (friendsString.contains(datas.get(i).dwID)) {
				datas.remove(i);
				i = i - 1;
				continue;
			}
			ContactBean contactBean = datas.get(i);
			contactBean.contactRecommends = 1;
			if (null != mobileNameMap.get(contactBean.phoneNum)) {
				contactBean.name = mobileNameMap.get(contactBean.phoneNum);
			}
		}
	}

	@Override
	public void OnContactClick(View v) {
		int position = (Integer) v.getTag(Constants.ITEM_POSITION);
		String rondom = (String) v.getTag(Constants.ITEM_TAG);
		if ("0".equals(rondom)) {
			handleClickListenerContact(v, position);
		} else if ("1".equals(rondom)) {
			handleClickListenerRondom(v, position);
		}

	}

	private void handleClickListenerContact(View v, int position) {
		final ContactBean contactBean = contactBeans.get(position);
		Intent intent;
		switch (v.getId()) {
		case R.id.tv_contact_name:
			intent = new Intent(this, ChatActivity_.class);
			intent.putExtra(ChatActivity.OTHER_ID, contactBean.dwID);
			intent.putExtra(ChatActivity.OTHER_NICKNAME, contactBean.name);
			intent.putExtra(ChatActivity.CHAT_TYPE, DWMessage.CHAT_TYPE_SINGLE);
			judgeRelationship(contactBean, intent);
			intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(contactBean.worth));
			startActivity(intent);
			break;
		case R.id.iv_contact_avatar:
			intent = new Intent(this, NearCardDetailActivity_.class);
			intent.putExtra(Constants.DW_ID, contactBean.dwID);
			startActivity(intent);
			break;
		case R.id.iv_contact_add:
			AddFriendDialog dialog = AddFriendDialog.newInstance();
			dialog.setNeedMoney(false);
			dialog.setOnListener(new AddFriendListener() {

				@Override
				public void withReason(String reason) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
					map.put("friendID", contactBean.dwID);
					map.put("remark", contactBean.name);
					map.put("addReason", reason);
					addFriendRequest(map, Constants.API_ADD_FRIEND_CONTACT);
				}
			});
			dialog.show(getSupportFragmentManager(), "dialog");
			break;
		}
	}

	private void handleClickListenerRondom(View v, int position) {
		final ContactBean contactBean = recommendbeans.get(position);
		Intent intent;
		switch (v.getId()) {
		case R.id.tv_contact_name:
			intent = new Intent(this, ChatActivity_.class);
			intent.putExtra(ChatActivity.OTHER_ID, contactBean.dwID);
			intent.putExtra(ChatActivity.OTHER_NICKNAME, contactBean.name);
			intent.putExtra(ChatActivity.CHAT_TYPE, DWMessage.CHAT_TYPE_SINGLE);
			judgeRelationship(contactBean, intent);
			intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(contactBean.worth));
			startActivity(intent);
			break;
		case R.id.iv_contact_avatar:
			intent = new Intent(this, NearCardDetailActivity_.class);
			intent.putExtra(Constants.DW_ID, contactBean.dwID);
			startActivity(intent);
			break;
		case R.id.iv_contact_add:
			AddFriendDialog dialog = AddFriendDialog.newInstance();
			dialog.setNeedMoney(true);
			dialog.setOnListener(new AddFriendListener() {

				@Override
				public void withReason(String reason) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
					map.put("friendID", contactBean.dwID);
					map.put("searchType", "3");
					map.put("addReason", reason);
					addFriendRequest(map, Constants.API_ADD_FRIEND_NO_CONTACT);
				}
			});
			dialog.show(getSupportFragmentManager(), "dialog");

			break;
		}
	}

	private void judgeRelationship(ContactBean contactBean, Intent intent) {
		if (ContactUser.isContact(contactBean.dwID)) {
			intent.putExtra("chatRelationship", DWMessage.CHAT_RELATIONSHIP_FRIEND);
		} else {
			intent.putExtra("chatRelationship", DWMessage.CHAT_RELATIONSHIP_STRANGER);
		}
	}

	/**
	 * 申请添加朋友
	 * */
	private void addFriendRequest(HashMap<String, String> map, String api) {
		mSendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + api, Method.POST, new HttpCallBack() {

			@Override
			public void onSuccess(String response, ResultBean msg) {
				if (2222 == msg.getResultCode()) {
					showToastInfo("申请已发送");
				} else {
					showToastInfo(msg.getMsg());
				}
			}

			@Override
			public void onFailure(String e) {
				showToastInfo(Constants.NET_WRONG);
			}
		});
	}

	private void showToastInfo(final String data) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
