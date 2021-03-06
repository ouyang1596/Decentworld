/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.FriendDetailComponent;
import cn.sx.decentworld.dialog.EditAndModificationDialog;
import cn.sx.decentworld.dialog.EditAndModificationDialog.EditAndModificationListener;
import cn.sx.decentworld.dialog.ReminderDialog;
import cn.sx.decentworld.dialog.ReminderDialog.ReminderListener;
import cn.sx.decentworld.entity.dao.ContactUserDao;
import cn.sx.decentworld.entity.dao.DWMessageDao;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.entity.db.Conversation;
import cn.sx.decentworld.entity.db.DWMessage;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: FriendDetailSettingActivity.java
 * @Description: 
 * @author: cj
 * @date: 2015年8月11日 下午3:42:50
 */
@EActivity(R.layout.activity_friend_detail_setting)
public class FriendDetailSettingActivity extends BaseFragmentActivity implements OnClickListener
{
	private static final String TAG= "FriendDetailSettingActivity";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;
	
	@Bean
	FriendDetailComponent friend_detail_Component;
	
	private String userId;
	private EditAndModificationDialog editAndModificationDialog;
	private ReminderDialog reminderDialog;
	
	@ViewById(R.id.ll_friend_detail_root)
	LinearLayout ll_friend_detail_root;

	@ViewById(R.id.iv_friend_detail_setting_1)
	ImageView iv_friend_detail_setting_1;
	@ViewById(R.id.iv_friend_detail_setting_2)
	ImageView iv_friend_detail_setting_2;
	@ViewById(R.id.iv_friend_detail_setting_3)
	ImageView iv_friend_detail_setting_3;
	
	@ViewById(R.id.ll_friend_detail_set_remark)
	LinearLayout ll_friend_detail_set_remark;
	@ViewById(R.id.ll_friend_detail_delete_contact)
	LinearLayout ll_friend_detail_delete_contact;
	@ViewById(R.id.ll_friend_detail_whistle_blowing)
	LinearLayout ll_friend_detail_whistle_blowing;
	
	
	//从数据库拿取数据
	private Boolean[] checkBoxIsChecked = new Boolean[3];
	
	/**
	 * 删除好友操作的handler
	 */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constants.SUCC:
				//页面跳转
				reminderDialog.dismiss();
				//本地数据库通讯录表删除该人
				ContactUserDao.delete(userId);
				//将消息页面该对象的聊天记录删除
				Conversation.deleteByDwID(userId);
				/** 删除指定ID的消息 **/
				DWMessageDao.delete(userId);
				//数据更新
				//默认页面跳转完毕后，当前activity为mainActivity的CharFragment
				startActivity(new Intent(FriendDetailSettingActivity.this,MainActivity_.class));
				finish();
				
				break;
			case Constants.FAILURE:
				toast.show(msg.obj+"");
				break;
			default:
				break;
			}
		};
	};
	
	private void out(){
		finish();
	}
	
	@AfterViews
	public void init()
	{
		ViewUtil.scaleContentView(ll_friend_detail_root);
		userId = getIntent().getStringExtra("userId");
		titleBar.setTitleBar("详细资料", "资料设置");
		initData();
		initView();
	}
	
	/**
	 * 
	 */
	private void initData()
	{
		for (int i = 0; i < checkBoxIsChecked.length; i++)
		{
			checkBoxIsChecked[i] = false;
		}
	}

	/**
	 * 
	 */
	private void initView()
	{
		iv_friend_detail_setting_1.setOnClickListener(this);
		iv_friend_detail_setting_2.setOnClickListener(this);
		iv_friend_detail_setting_3.setOnClickListener(this);
		ll_friend_detail_set_remark.setOnClickListener(this);
		ll_friend_detail_delete_contact.setOnClickListener(this);
		ll_friend_detail_whistle_blowing.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.iv_friend_detail_setting_1:
			if (checkBoxIsChecked[0])
			{
				iv_friend_detail_setting_1.setImageResource(R.drawable.switch_rectangle_close);
				checkBoxIsChecked[0] = false;
			} else
			{
				iv_friend_detail_setting_1.setImageResource(R.drawable.switch_rectangle_open);
				checkBoxIsChecked[0] = true;
			}
			break;
		case R.id.iv_friend_detail_setting_2:
			if (checkBoxIsChecked[1])
			{
				iv_friend_detail_setting_2.setImageResource(R.drawable.switch_rectangle_close);
				checkBoxIsChecked[1] = false;
			} else
			{
				iv_friend_detail_setting_2.setImageResource(R.drawable.switch_rectangle_open);
				checkBoxIsChecked[1] = true;
			}
			break;
		case R.id.iv_friend_detail_setting_3:
			if (checkBoxIsChecked[2])
			{
				iv_friend_detail_setting_3.setImageResource(R.drawable.switch_rectangle_close);
				checkBoxIsChecked[2] = false;
			} else
			{
				iv_friend_detail_setting_3.setImageResource(R.drawable.switch_rectangle_open);
				checkBoxIsChecked[2] = true;
			}
			break;
		case R.id.ll_friend_detail_set_remark:
			editAndModificationDialog = new EditAndModificationDialog();
			editAndModificationDialog.setListener(RemarkListener);
			editAndModificationDialog.setTitle("为朋友设置备注名");
			editAndModificationDialog.show(getSupportFragmentManager(), "");
			
			break;
		case R.id.ll_friend_detail_delete_contact:
			reminderDialog = new ReminderDialog();
			reminderDialog.setListener(reminderListener);
			reminderDialog.setInfo("将联系人删除，同时删除\n与该联系人的聊天记录");
			reminderDialog.show(getSupportFragmentManager(), "");
			
			break;
		case R.id.ll_friend_detail_whistle_blowing:
			Intent intent = new Intent(FriendDetailSettingActivity.this, ReportAdditionActivity_.class);
			startActivity(intent);
			
			break;
		default:
			break;
		}
		
	}
	
	EditAndModificationListener RemarkListener = new EditAndModificationListener()
	{
		@Override
		public void confirm(String info)
		{
//			String niakname = editAndModificationDialog.getInfo();
			String niakname = info;
			/**
			 * 修改昵称
			 * 1.修改界面昵称
			 * 2.数据库同步更新
			 */
			//1.修改界面
//			FriendDetailComponent.ll_user_detail_header_remark.setVisibility(View.VISIBLE);
//			FriendDetailComponent.tv_user_detail_header_remark.setText(niakname);
			//2.修改数据库
		}
	};
	
	ReminderListener reminderListener = new ReminderListener()
	{
		@Override
		public void confirm()
		{
			//1.删除数据库中的联系人，通知数据发生改变
			
			//2.删除本地与该人有关的聊天信息
			
			//3.跳到联系人列表（即finish掉前前两个人Activity）
//			ActivityCollector.finishAll();
//			finish();
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("dwID", DecentWorldApp.getInstance().getDwID());
			hashmap.put("friendID", userId);
			String api  ="/deleteFriend";
			friend_detail_Component.deleteFriend(hashmap, api, handler);
			
		}
	};
	
	@Click(R.id.main_header_left)
	void back()
	{
		finish();
	}
}
