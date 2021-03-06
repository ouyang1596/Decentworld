/**
 * 
 */
package cn.sx.decentworld.activity;

import java.io.File;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.ChatRoomBuildComponent;
import cn.sx.decentworld.dialog.EditAndModificationDialog;
import cn.sx.decentworld.dialog.EditAndModificationDialog.EditAndModificationListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.request.SetRoomInfo;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ChatRoomBuildctivity.java
 * @Description:
 * @author: dyq
 * @date: 2015年8月5日 上午8:26:57
 */
@EActivity(R.layout.activity_chat_room_build)
public class ChatRoomBuildActivity extends BaseFragmentActivity {
	private static final String TAG = "ChatRoomBuildActivity";
	private static final int REQUEST_CONTENT = 1;
	private static final int MODIF_ICON = 2;
	private static final int REQUEST_CODE_LOCAL = 3;
	private static final int ADD_ROOM_BACKGROUND = 4;

	public static final int CREATE_ROOM_SUCCESS = 5;// 创建聊天室成功

	@Bean
	ChatRoomBuildComponent chatRoomBuildComponent;
	private EditAndModificationDialog editAndModificationDialog;

	@Bean
	TitleBar titleBar;

	@ViewById(R.id.root_activitiy_chat_room_build)
	RelativeLayout root_activitiy_chat_room_build;

	@ViewById(R.id.add_topic_button)
	Button add_topic_button;

	// 聊天室名字
	@ViewById(R.id.et_chat_room_build_name)
	EditText et_chat_room_build_name;
	// 聊天室描述
	@ViewById(R.id.add_topic_content2)
	TextView add_topic_content2;

	// 主持人头像
	@ViewById(R.id.iv_chat_room_build_icon)
	ImageView iv_chat_room_build_icon;

	// 主持人姓名
	@ViewById(R.id.tv_chat_room_build_hostname)
	TextView tv_chat_room_build_hostname;

	@ViewById(R.id.et_chat_room_build_owner_introduction)
	EditText et_chat_room_build_owner_introduction;

	@ViewById(R.id.iv_chat_room_build_background)
	ImageView iv_chat_room_build_background;

	@Bean
	ToastComponent toast;

	@ViewById(R.id.iv_chat_room_build_modif_icon)
	ImageView iv_chat_room_build_modif_icon;

	@ViewById(R.id.iv_chat_room_build_modif_name)
	ImageView iv_chat_room_build_modif_name;

	// 选择相片
	@Bean
	ChoceAndTakePictureComponent choceAndTakePictureComponent;

	/**
	 * 创建聊天室提交的到服务器的信息
	 */
	private File roomBackground;
	private String roomName = "";
	private String roomDescription = "";

	private File ownerIcon;
	private String ownerName = "";
	private String ownerIntroduction = "";

	private String ownerID;

	@AfterViews
	public void init() {
		ViewUtil.scaleContentView(root_activitiy_chat_room_build);
		titleBar.setTitleBar("返回", "添加话题");
	}

	@Click(R.id.main_header_left)
	void back() {
		this.finish();
	}

	@Click(R.id.add_topic_content2)
	public void toadd_Content() {
		Intent intent = new Intent(ChatRoomBuildActivity.this, ChatRoomAddContentActivity_.class);
		Bundle bundle = new Bundle();
		bundle.putString("old_content", add_topic_content2.getText().toString());
		intent.putExtras(bundle);
		startActivityForResult(intent, REQUEST_CONTENT);
	}

	/**
	 * 1.第一次点击时保存数据（显示“保存”） 2.第二次点击时清除以前保存的数据（显示“清除”）
	 */
	@Click(R.id.btn_chat_room_build_save)
	public void save() {

	}

	@Bean
	SetRoomInfo setRoomInfo;

	/**
	 * 提交数据到服务器
	 */
	@Click(R.id.add_topic_button)
	public void tocreateChatRoom() {
		if (et_chat_room_build_name.getText().toString().equals("") || et_chat_room_build_name.getText().toString().equals(null)) {
			toast.show("标题不能为空，并且不能与已有的房间标题重名");
		} else {
			final ProgressDialog pd = new ProgressDialog(ChatRoomBuildActivity.this);

			Handler handler = new Handler() {
				public void handleMessage(android.os.Message msg) {
					switch (msg.what) {
					case 1:
						String roomID = (String) msg.obj;
						LogUtils.i(TAG, "创建聊天室成功，获得roomID=" + roomID);
						// pd.dismiss();
						toast.show("创建聊天室+" + roomID);
						// 直接跳到聊天室列表界面
						Intent intent = new Intent();
						intent.putExtra("creatRoomSuccess", CREATE_ROOM_SUCCESS);
						setResult(RESULT_OK, intent);
						finish();
						break;

					default:
						break;
					}
				};
			};

			/**
			 * 将信息提交到服务器 包括：聊天室名字、聊天室描述信息、主持人姓名、主持人头像、主持人dwID
			 */
			roomName = et_chat_room_build_name.getText().toString();
			roomDescription = add_topic_content2.getText().toString();
			ownerName = tv_chat_room_build_hostname.getText().toString();
			ownerID = DecentWorldApp.getInstance().getDwID();
			ownerIntroduction = et_chat_room_build_owner_introduction.getText().toString();
			File[] images = new File[2];
			images[0] = roomBackground;
			images[1] = ownerIcon;

			LogUtils.i(TAG, "聊天室的信息为：" + "\nroomName=" + roomName + "\nroomDescription=" + roomDescription + "\nownerName="
					+ ownerName + "\nownerID=" + ownerID + "\nownerIcon.getAbsolutePath=" + ownerIcon.getAbsolutePath()
					+ ",roomBackground=" + roomBackground.getAbsolutePath());
			setRoomInfo.creatChatRoom(roomName, roomDescription, ownerName, ownerID, ownerIntroduction, images, handler);
		}
	}

	/**
	 * 修改主持人在聊天室的头像
	 */
	@Click(R.id.iv_chat_room_build_modif_icon)
	void modifHostIcon() {
		choceAndTakePictureComponent.choicePicture(MODIF_ICON);
	}

	/**
	 * 修改主持人在聊天室的名字
	 */
	@Click(R.id.iv_chat_room_build_modif_name)
	void modifHostName() {
		editAndModificationDialog = new EditAndModificationDialog();
		editAndModificationDialog.setListener(new EditAndModificationListener() {
			@Override
			public void confirm(String info) {
				// tv_chat_room_build_hostname.setText(editAndModificationDialog.getInfo());
				tv_chat_room_build_hostname.setText(info);
			}
		});
		editAndModificationDialog.setTitle("修改姓名");
		editAndModificationDialog.show(getSupportFragmentManager(), "modif_name");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CONTENT) {
				add_topic_content2.setText(intent.getStringExtra("new_content"));
			} else if (requestCode == MODIF_ICON) {
				File file = (File) intent.getSerializableExtra("values");
				if (intent != null) {
					toast.show("选择一张相片");
					Bitmap resizeBitmap = ImageUtils.resizeBitmap(file, 120, 120);
					iv_chat_room_build_icon.setImageBitmap(resizeBitmap);
					LogUtils.i(TAG, file.getAbsolutePath() + "\n" + file.getPath() + "\n" + file.getName());
					ownerIcon = file;
					LogUtils.i(TAG, "选择图片完毕，图片路径为：" + file.getAbsolutePath());
				}
			} else if (requestCode == ADD_ROOM_BACKGROUND) {
				File file = (File) intent.getSerializableExtra("values");
				if (intent != null) {
					toast.show("选择一张相片");
					Bitmap resizeBitmap = ImageUtils.resizeBitmap(file, 680, 392);
					iv_chat_room_build_background.setImageBitmap(resizeBitmap);
					roomBackground = file;
					LogUtils.i(TAG, "选择图片完毕，图片路径为：" + file.getAbsolutePath());
				}
			}
		}
	}

	@Click(R.id.iv_chat_room_build_background)
	void addRoomBackground() {
		choceAndTakePictureComponent.choicePicture(ADD_ROOM_BACKGROUND);
	}

}
