package cn.sx.decentworld.activity;

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
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.SupporterAndCSAdapter;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.SupporterAndCSBean;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.entity.LaunchChatEntity;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.ToastUtil;

import com.android.volley.Request.Method;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_customer_service)
public class CustomerServiceActivity extends BaseFragmentActivity implements OnClickListener, OnItemClickListener {
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.tv_header_title)
	TextView tvHeadtitle;
	@ViewById(R.id.lv_cs)
	ListView lvCs;
	private SupporterAndCSAdapter adapter;
	private List<SupporterAndCSBean> supporters;
	private SendUrl mSendUrl;
	private Handler mGetCSHandle = new Handler() {
		public void handleMessage(android.os.Message msg) {
			supporters = (List<SupporterAndCSBean>) JsonUtils.json2BeanArray(msg.obj.toString(), SupporterAndCSBean.class);
			adapter = new SupporterAndCSAdapter(mContext, supporters);
			lvCs.setAdapter(adapter);
			lvCs.setOnItemClickListener(CustomerServiceActivity.this);
		};
	};

	@AfterViews
	public void init() {
		mSendUrl = new SendUrl(this);
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(this);
		tvHeadtitle.setText("在线客服");
		getCSList();
	}

	private void getCSList() {
		mSendUrl.httpRequestWithParams(new HashMap<String, String>(), Constants.CONTEXTPATH + ConstantNet.API_GET_CS_LIST,
				Method.POST, new HttpCallBack() {

					@Override
					public void onSuccess(String response, ResultBean msg) {
						if (2222 == msg.getResultCode()) {
							try {
								Message message = mGetCSHandle.obtainMessage();
								JSONObject object = new JSONObject(msg.getData().toString());
								JSONArray array = object.getJSONArray("csList");
								message.obj = array.toString();
								mGetCSHandle.sendMessage(message);
							} catch (JSONException e) {
							}
						} else {
							showToast(msg.getMsg());
						}
					}

					@Override
					public void onFailure(String e) {
						showToast(Constants.NET_WRONG);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		SupporterAndCSBean bean = adapter.getItem(position);
		Intent intent = new Intent(this, ChatActivity_.class);
//		intent.putExtra(ChatActivity.OTHER_ID, bean.id);
//		intent.putExtra(ChatActivity.OTHER_NICKNAME, bean.showName);
//		intent.putExtra(ChatActivity.CHAT_TYPE, DWMessage.CHAT_TYPE_SINGLE);
//		intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, DWMessage.CHAT_RELATIONSHIP_FRIEND);
//		intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(bean.worth));
        LaunchChatEntity entity = new LaunchChatEntity(bean.id, bean.showName, Float.valueOf(bean.worth),
                DWMessage.CHAT_TYPE_SINGLE, DWMessage.CHAT_RELATIONSHIP_FRIEND, Integer.valueOf(bean.userType));
        intent.putExtra(ChatActivity.LAUNCH_CHAT_KEY, entity);
        
		startActivity(intent);
	}
}
