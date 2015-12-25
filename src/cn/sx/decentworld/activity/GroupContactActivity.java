/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.GroupAdapter;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.MainFragmentComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: GroupChatActivity.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月28日 上午11:02:57
 */
@EActivity(R.layout.fragment_chat_chat_group)
public class GroupContactActivity extends BaseFragmentActivity
{
	@Bean
	ToastComponent compon;
	@ViewById(R.id.lv_group_chat_joined)
	cn.sx.decentworld.widget.ListViewForScrollView groupListView;

	@ViewById(R.id.progress_bar)
	LinearLayout progressBar;

	@Bean
	MainFragmentComponent component;

	//
	// // 环信变量
	// protected List<EMGroup> grouplist;
	// private GroupAdapter groupAdapter;
	// private InputMethodManager inputMethodManager;
	// public static GroupContactActivity instance;
	// private SyncListener syncListener;
	// Handler handler = new Handler();
	//
	// class SyncListener implements HXSyncListener {
	// @Override
	// public void onSyncSucess(final boolean success) {
	// runOnUiThread(new Runnable() {
	// public void run() {
	// if (success) {
	// handler.postDelayed(new Runnable() {
	// @Override
	// public void run() {
	// refresh();
	// progressBar.setVisibility(View.GONE);
	// }
	// }, 1000);
	// } else {
	// if (!GroupContactActivity.this.isFinishing()) {
	// String s1 = getResources()
	// .getString(
	// R.string.Failed_to_get_group_chat_information);
	// compon.show(s1);
	// progressBar.setVisibility(View.GONE);
	// }
	// }
	// }
	// });
	// }
	// }
	//
	@Click(R.id.main_header_left)
	public void click_back()
	{
		this.finish();
	}

	@Click(R.id.main_header_right_btn)
	public void tobuild_group()
	{
		Bundle bundle = new Bundle();
		bundle.putBoolean("turn_another_activity", true);
		bundle.putInt("execute", PickContactActivity_.EXECUTE_NOW);
		Intent intent = new Intent(this , PickContactActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	//
	// @Override
	// protected void onActivityResult(int arg0, int arg1, Intent arg2) {
	// super.onActivityResult(arg0, arg1, arg2);
	// switch (arg0) {
	// case 110: {
	// if (arg1 == RESULT_OK) {
	// // Bundle bundle = arg2.getExtras();
	// // List<String> userlist = bundle
	// // .getStringArrayList(("the_object"));
	// // String string = "";
	// // for (int i = 0; i < userlist.size(); i++) {
	// // string = userlist.get(i) + string;
	// // }
	// // compon.show(string);
	//
	//
	// // 进入群聊
	// // Intent intent = new Intent(GroupContactActivity.this,
	// // ChatActivity_.class);
	// // // it is group chat
	// // intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
	// //
	// intent.putExtra("groupId",EMChatManager.getInstance().getAllGroups().get(EMChatManager.getInstance().getAllGroups().size()-1).getGroupId());
	// // startActivity(intent);
	//
	// }
	//
	// }
	// break;
	// }
	// }
	//
	// @AfterViews
	// void init() {
	//
	// /**
	// * 环信
	// */
	// instance = this;
	// inputMethodManager = (InputMethodManager)
	// getSystemService(Context.INPUT_METHOD_SERVICE);
	// grouplist = EMGroupManager.getInstance().getAllGroups();
	//
	// groupAdapter = new GroupAdapter(this, 1, grouplist);
	// groupListView.setAdapter(groupAdapter);
	// groupListView.setOnItemClickListener(new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view,
	// int position, long id) {
	//
	// // 进入群聊
	// Intent intent = new Intent(GroupContactActivity.this,
	// ChatActivity_.class);
	// // it is group chat
	// intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
	// intent.putExtra("groupId", groupAdapter.getItem(position)
	// .getGroupId());
	// startActivityForResult(intent, 0);
	//
	// }
	//
	// });
	// groupListView.setOnTouchListener(new OnTouchListener() {
	//
	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	// if (getWindow().getAttributes().softInputMode !=
	// WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
	// if (getCurrentFocus() != null)
	// inputMethodManager.hideSoftInputFromWindow(
	// getCurrentFocus().getWindowToken(),
	// InputMethodManager.HIDE_NOT_ALWAYS);
	// }
	// return false;
	// }
	// });
	//
	// syncListener = new SyncListener();
	// HXSDKHelper.getInstance().addSyncGroupListener(syncListener);
	//
	// if (!HXSDKHelper.getInstance().isGroupsSyncedWithServer()) {
	// progressBar.setVisibility(View.VISIBLE);
	// } else {
	// progressBar.setVisibility(View.GONE);
	// }
	//
	// refresh();
	//
	// main_header_left.setVisibility(View.VISIBLE);
	// main_header_left_tv.setText(R.string.address_list);
	// main_header_title.setText(R.string.group_chat);
	// main_header_right_tv.setVisibility(View.INVISIBLE);
	// main_header_right_btn.setImageResource(R.drawable.build_group_icon);
	//
	// }
	//
	// // 环信方法
	// public void refresh() {
	// if (groupListView != null && groupAdapter != null) {
	// grouplist = EMGroupManager.getInstance().getAllGroups();
	// groupAdapter = new GroupAdapter(GroupContactActivity.this, 1,
	// grouplist);
	// groupListView.setAdapter(groupAdapter);
	// groupAdapter.notifyDataSetChanged();
	// }
	// }
	//
	// @Override
	// public void onResume() {
	// super.onResume();
	// grouplist = EMGroupManager.getInstance().getAllGroups();
	// groupAdapter = new GroupAdapter(this, 1, grouplist);
	// groupListView.setAdapter(groupAdapter);
	// groupAdapter.notifyDataSetChanged();
	// }
	//
	// @Override
	// protected void onDestroy() {
	// if (syncListener != null) {
	// HXSDKHelper.getInstance().removeSyncGroupListener(syncListener);
	// syncListener = null;
	// }
	// super.onDestroy();
	// instance = null;
	// }

}
