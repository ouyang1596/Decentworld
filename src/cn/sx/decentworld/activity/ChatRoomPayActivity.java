package cn.sx.decentworld.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.MyChatRoom;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.ChatRoomInfoSettingAndGetting;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_chat_room_pay)
public class ChatRoomPayActivity extends BaseFragmentActivity {
	@ViewById(R.id.btn_OK)
	Button btnOK;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@Bean
	ToastComponent toast;
	@Bean
	ChatRoomInfoSettingAndGetting chatRoomInfoSettingAndGetting;
	public static final int CREATE_CHATROOM = 0;
	public static final String JSON_DATA = "JSON_DATA";
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CREATE_CHATROOM:
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					String roomID = object.getString("roomID");
					Intent intent = new Intent(mContext,
							ChatRoomAddThemeActivity_.class);
					intent.putExtra(ChatRoomMeActivity.ROOMID, roomID);
					startActivity(intent);
					finish();
				} catch (JSONException e) {
					toast.show("解析错误");
				}
				break;
			}
		}
	};

	@AfterViews
	public void init() {
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
				chatRoomInfoSettingAndGetting.createChatRoom(map, mHandler);
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
