package cn.sx.decentworld.activity;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.ChatRoomInfoSettingAndGetting;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.widget.CircularImageView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_my_info)
public class ChatRoomEditMyInfoActivity extends BaseFragmentActivity {
	@ViewById(R.id.et_self_introduce)
	EditText etSelfIntroduce;
	@ViewById(R.id.iv_chatroom_head)
	CircularImageView ivChatRoomHead;
	@ViewById(R.id.ll_chatroom_head)
	LinearLayout llChatRoomHead;
	@ViewById(R.id.btn_OK)
	Button btnOk;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.tv_nickname)
	TextView tvNickName;
	private String picPath;
	private int chatRoomSize;
	private String ownerNickName;
	@Bean
	ToastComponent toast;
	@Bean
	ChatRoomInfoSettingAndGetting chatRoomInfoSettingAndGetting;
	private String icon, profile;
	public static final int GET_OWNER_INFO = 0;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2222:
				try {
					LogUtils.i("bm", "msg--" + msg.obj.toString());
					JSONObject object = new JSONObject(msg.obj.toString());
					icon = object.getString("icon");
					profile = object.getString("profile");
					ImageLoaderHelper.mImageLoader.displayImage(icon,
							ivChatRoomHead, ImageLoaderHelper.mOptions);
					etSelfIntroduce.setText(profile);
				} catch (JSONException e) {
					toast.show("解析错误");
					return;
				}
				break;
			case 3333:
				ImageLoaderHelper.mImageLoader.displayImage(ImageUtils
						.getIconByDwID(DecentWorldApp.getInstance().getDwID(),
								ImageUtils.ICON_MAIN), ivChatRoomHead,
						ImageLoaderHelper.mOptions);
				break;

			}

		};
	};
	private Handler mSetUserInfoHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			toast.show("succ");
			finish();
		};
	};

	@AfterViews
	public void init() {
		CGetIntent();
		setNickName();
		tvTitle.setText("修改资料");
		ivBack.setVisibility(View.VISIBLE);
		llChatRoomHead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(),
						TakePhotosAndpictureSingleActivity.class);
				intent.putExtra(Constants.ASPECTX, 1);
				intent.putExtra(Constants.ASPECTY, 1);
				intent.putExtra(Constants.OUTPUTX, 450);
				intent.putExtra(Constants.OUTPUTY, 450);
				startActivityForResult(intent, Constants.REQUEST_CODE);
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (chatRoomSize == 0) {
					toast.show("请先创建聊天室");
					return;
				}
				if (null == picPath) {
					toast.show("请重新选择一张图片");
					return;
				}
				if (ImageUtils.fileLength(picPath) > 2 * 1024 * 1024) {
					Bitmap scalePic = ImageUtils.scalePic(picPath);
					String filePath = Constants.HOME_PATH + "/temp"
							+ ImageUtils.generateFileName() + ".png";
					ImageUtils.saveBitmap(filePath, scalePic);
					picPath = filePath;
				}
				if (etSelfIntroduce.length() <= 0) {
					toast.show("请先输入内容");
					return;
				}
				File[] file = new File[1];
				file[0] = new File(picPath);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
				map.put("ownerProfile", etSelfIntroduce.getText().toString());
				chatRoomInfoSettingAndGetting.submitImageWithParams(map, file,
						Constants.API_SET_OWNER_INFO, mSetUserInfoHandler);
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		initRequest();
	}

	private void CGetIntent() {
		chatRoomSize = getIntent().getIntExtra(
				ChatRoomMeActivity.CHATROOM_SIZE, 0);
		ownerNickName = getIntent().getStringExtra(
				ChatRoomMeActivity.CHATROOM_NICKNAME);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
		chatRoomInfoSettingAndGetting.getOwnerInfo(map, mHandler);
	}

	private void setNickName() {
		if (null != ownerNickName) {
			tvNickName.setText(ownerNickName);
		} else {
			tvNickName.setText("");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int arg1, Intent data) {
		super.onActivityResult(requestCode, arg1, data);
		if (null == data) {
			return;
		}
		String filePath = data.getStringExtra("filePath");
		switch (requestCode) {
		case Constants.REQUEST_CODE:
			picPath = filePath;
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			ivChatRoomHead.setImageBitmap(bitmap);
			break;
		}
	}
}
