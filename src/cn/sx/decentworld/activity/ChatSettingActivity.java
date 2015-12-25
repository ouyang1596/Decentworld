/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.ChatSettingGridAdapter;
import cn.sx.decentworld.bean.ChatSetting;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.EmptyMsgDialog;
import cn.sx.decentworld.dialog.EmptyMsgDialog.EmptyMsgListener;
import cn.sx.decentworld.widget.ExpandGridView;

import com.activeandroid.query.Select;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ChatDetailsActivity.java
 * @Description:聊天设置界面
 * @author: cj
 * @date: 2015年7月13日 下午1:47:28
 */

@EActivity(R.layout.chat_setting)
public class ChatSettingActivity extends BaseFragmentActivity
{
	private static final String TAG = "ChatSettingActivity";
	// 工具
	@Bean
	ToastComponent toast;
	@Bean
	TitleBar titleBar;

	// 布局控件
	@ViewById(R.id.chat_setting_gridview)
	ExpandGridView chat_setting_gridview;
	@ViewById(R.id.rl_chat_details_set_bg)
	RelativeLayout rl_chat_details_set_bg;

	@ViewById(R.id.rl_chat_details_empty_record)
	RelativeLayout rl_chat_details_empty_record;

	@ViewById(R.id.rl_chat_details_whistleblowing)
	RelativeLayout rl_chat_details_whistleblowing;

	// 开关控件 置顶、免打扰
	@ViewById(R.id.iv_switch_top)
	ImageView iv_switch_top;
	@ViewById(R.id.iv_switch_no_disturb)
	ImageView iv_switch_ddisturb;

	private ArrayList<String> members = new ArrayList<String>();
	private ChatSettingGridAdapter gridAdapter;
	private FragmentManager fragmentManager;
	private String otherID = "";
	private boolean isCheckedTop = false;// 置顶判断
	private boolean isCheckedNDisturb = false;// 免打扰开关
	private boolean isClear = false;// 清空判断

	@AfterViews
	public void init()
	{
		titleBar.setTitleBar("聊天详情", null);
		otherID = DecentWorldApp.getInstance().getDwID();
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData()
	{
		fragmentManager = getSupportFragmentManager();
		otherID = getIntent().getStringExtra("otherID");
		ChatSetting chatSetting = ChatSetting.queryByDwID(otherID);
		if(chatSetting!=null)
		{
			if(chatSetting.isMsgTop())
			{
				isCheckedTop = true;
				iv_switch_top.setImageResource(R.drawable.switch_rectangle_open);
			}
			if(chatSetting.isMsgNoDisturb())
			{
				isCheckedNDisturb = true;
				iv_switch_ddisturb.setImageResource(R.drawable.switch_rectangle_open);
			}
		}
		members.add(otherID);
		gridAdapter = new ChatSettingGridAdapter(ChatSettingActivity.this , R.layout.chat_setting_gridview_item , members);
		chat_setting_gridview.setAdapter(gridAdapter);
		chat_setting_gridview.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				if (position == gridAdapter.getCount() - 1)
				{
					Intent intent = new Intent(ChatSettingActivity.this , ChatPickContactActivity_.class);
					Bundle bundle = new Bundle();
					bundle.putStringArrayList("members_had_included", members);
					bundle.putBoolean("turn_another_activity", true);
					bundle.putInt("execute", PickContactActivity_.EXECUTE_NOW);
					intent.putExtras(bundle);
					startActivity(intent);
				} else {
					// 跳到查看对方详情的界面
					Intent intent = new Intent(ChatSettingActivity.this,
							NearCardDetailActivity_.class);
					intent.putExtra(Constants.DW_ID, members.get(position));
					startActivity(intent);
				}
			}
		});
	}

	/**
	 * 置顶开关
	 */
	@Click(R.id.iv_switch_top)
	void switchTop()
	{
		if (isCheckedTop)
		{
			isCheckedTop = false;
			iv_switch_top.setImageResource(R.drawable.switch_rectangle_close);
		}
		else
		{
			isCheckedTop = true;
			iv_switch_top.setImageResource(R.drawable.switch_rectangle_open);
		}
	}

	/**
	 * 消息面打扰开关
	 */
	@Click(R.id.iv_switch_no_disturb)
	void switchNDisturb()
	{
		if (isCheckedNDisturb)
		{
			isCheckedNDisturb = false;
			iv_switch_ddisturb.setImageResource(R.drawable.switch_rectangle_close);
		}
		else
		{
			isCheckedNDisturb = true;
			iv_switch_ddisturb.setImageResource(R.drawable.switch_rectangle_open);
		}
	}

	/**
	 * 设置聊天背景
	 */
	@Click(R.id.rl_chat_details_set_bg)
	void setChatBg()
	{
		toast.show(Constants.DEVELOPING_NOTIFY);
//		Intent intent = new Intent(ChatSettingActivity.this , ChatSettingSetBgActivity_.class);
//		startActivity(intent);
	}

	/**
	 * 清空聊天记录
	 */
	@Click(R.id.rl_chat_details_empty_record)
	void emptyRecord()
	{
		EmptyMsgListener listener = new EmptyMsgListener()
		{
			@Override
			public void onEmpty()
			{
//				String sql = "userID = ? and ";
//				new Select().from(DWMessage.class).where("", args)
				//删除本地消息
				
				//清除回话该人的会话列表
				toast.show(Constants.DEVELOPING_NOTIFY);
			}

			@Override
			public void onCancel()
			{
				toast.show("取消");
			}
		};
		// 弹出确定对话框
		EmptyMsgDialog transmitConfirmfDialog = new EmptyMsgDialog();
		transmitConfirmfDialog.setOnListener(listener);
		transmitConfirmfDialog.setCancelable(false);
		transmitConfirmfDialog.show(fragmentManager, "EmptyMsgDialog");
	}

	/**
	 * 举报
	 */
	@Click(R.id.rl_chat_details_whistleblowing)
	void whistleBlowing()
	{
		toast.show(Constants.DEVELOPING_NOTIFY);
//		Intent intent = new Intent(ChatSettingActivity.this , ChatSettingWhistleblowingActivity_.class);
//		startActivity(intent);
	}

	/**
	 * 返回
	 */
	@Click(R.id.main_header_left)
	void setBack()
	{
		//保存数据
		ChatSetting chatSetting = ChatSetting.queryByDwID(otherID);
		if(chatSetting==null)
		{
			chatSetting = new ChatSetting();
			chatSetting.setUserID(DecentWorldApp.getInstance().getDwID());
			chatSetting.setOtherID(otherID);
		}
		chatSetting.setMsgTop(isCheckedTop);
		chatSetting.setMsgTopTime(String.valueOf(System.currentTimeMillis()));
		chatSetting.setMsgNoDisturb(isCheckedNDisturb);
		chatSetting.save();
		
		//回传参数
		Intent intent = new Intent();
		intent.putExtra("isclear", isClear);
		setResult(RESULT_OK, intent);
		finish();
	}

}
