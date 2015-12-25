package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.MainFragmentComponent;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * 
 * @ClassName: GroupChatInfoActivity.java
 * @Description:
 * @author: yj
 * @date: 2015-8-8 下午9:24:39
 */
@EActivity(R.layout.fragment_chat_chat_group_info)
public class GroupChatInfoActivity extends BaseFragmentActivity
{
	@Bean
	ToastComponent toast;

	@Bean
	MainFragmentComponent mainFragmentComponent;
	// 环信常量
	private static final int REQUEST_CODE_ADD_USER = 0;
	private static final int REQUEST_CODE_EXIT = 1;
	private static final int REQUEST_CODE_EXIT_DELETE = 2;
	private static final int REQUEST_CODE_CLEAR_ALL_HISTORY = 3;
	private static final int REQUEST_CODE_ADD_TO_BALCKLIST = 4;
	private static final int REQUEST_CODE_EDIT_GROUPNAME = 5;
	private static final int REQUEST_CODE_DELETE_USER = 6;

	private boolean wheather_the_owner = false;
	String st1;
	String st2;
	String st3;
	String st4;
	String st5;
	String st6;
	String st7;

	// 用于在gridview的点击事件
	// private Timer timer=new Timer(1000, 500);
	private boolean overtime = false;

	String longClickUsername = null;

	String st = "";
	private String groupId;
	// private EMGroup group;
	// private GridAdapter adapter;

	public static GroupChatInfoActivity instance;

	private ProgressDialog progressDialog;

	List<String> members = new ArrayList<String>();
	private FragmentManager fragmentManager;
	private boolean if_topSwitch = true, if_interrupt = true;
	// private GroupGridAdapter gadapter;

	@ViewById(R.id.root_fragment_chat_chat_group_info)
	LinearLayout root_fragment_chat_chat_group_info;

	@ViewById(R.id.group_chat_gridview)
	cn.sx.decentworld.widget.ExpandGridView userGridview;

	@ViewById(R.id.real_groupname)
	TextView real_groupname;

	@ViewById(R.id.my_name_groupchat)
	TextView my_name_groupchat;

	@ViewById(R.id.set_top_switcher)
	ImageView set_top_switcher;

	@ViewById(R.id.btn_exit_grp)
	Button exitBtn;

	@Click(R.id.set_chat_top)
	void changeIcon()
	{
		if (if_topSwitch)
		{
			set_top_switcher.setImageResource(R.drawable.switch_rectangle_open);
			if_topSwitch = false;
		}
		else
		{
			set_top_switcher.setImageResource(R.drawable.switch_rectangle_close);
			if_topSwitch = true;
		}
	}

	@ViewById(R.id.interrupt_switcher)
	ImageView interrupt_switcher;

	@Click(R.id.no_interrupt)
	void changeinterrupt_Icon()
	{
		if (if_interrupt)
		{
			interrupt_switcher.setImageResource(R.drawable.switch_rectangle_open);
			if_interrupt = false;
		}
		else
		{
			interrupt_switcher.setImageResource(R.drawable.switch_rectangle_close);
			if_interrupt = true;
		}
	}

	@Click(R.id.main_header_left)
	public void back()
	{
		this.finish();
	}

	@ViewById(R.id.group_chat_group_icon)
	ImageView group_chat_group_icon;
	//
	// @AfterViews
	// public void dosomething() {
	// ViewUtil.scaleContentView(root_fragment_chat_chat_group_info);
	// st1 = getResources().getString(R.string.being_added);
	// st2 = getResources().getString(R.string.is_quit_the_group_chat);
	// st3 = getResources().getString(R.string.chatting_is_dissolution);
	// st4 = getResources().getString(R.string.are_empty_group_of_news);
	// st5 = getResources()
	// .getString(R.string.is_modify_the_group_name);
	// st6 = getResources().getString(
	// R.string.Modify_the_group_name_successful);
	// st7 = getResources().getString(
	// R.string.change_the_group_name_failed_please);
	//
	// main_header_left.setVisibility(View.VISIBLE);
	// main_header_left_tv.setText(R.string.group_chat);
	// main_header_title.setText(R.string.chat_info);
	// main_header_right.setVisibility(View.INVISIBLE);
	//
	// // 获取传过来的groupid
	// groupId = getIntent().getStringExtra("groupId");
	// group = EMGroupManager.getInstance().getGroup(groupId);
	//
	// // we are not supposed to show the group if we don't find the group
	// if (group == null) {
	// finish();
	// return;
	// }
	// instance = this;
	// st = getResources().getString(R.string.people);
	//
	// fragmentManager = getSupportFragmentManager();
	// initData();
	//
	// }
	//
	// @Click(R.id.btn_exit_grp)
	// public void click_out_or_dismiss(){
	// if(wheather_the_owner){
	// progressDialog.setMessage(st3);
	// progressDialog.show();
	// deleteGrop();
	// }
	// else{
	// progressDialog.setMessage(st2);
	// progressDialog.show();
	// exitGrop();
	// }
	// }
	// public void initData() {
	// if (progressDialog == null) {
	// progressDialog = new ProgressDialog(GroupChatInfoActivity.this);
	// progressDialog.setMessage(getResources().getString(R.string.being_added));
	// progressDialog.setCanceledOnTouchOutside(false);
	// }
	// // 如果自己是群主，显示解散按钮
	// if (EMChatManager.getInstance().getCurrentUser()
	// .equals(group.getOwner())) {
	// // exitBtn.setVisibility(View.GONE);
	// exitBtn.setText(getResources().getString(R.string.dismiss_the_group_chat));
	// wheather_the_owner=true;
	// }
	//
	// real_groupname.setText(group.getGroupName());
	// //添加群成员
	// members.addAll(group.getMembers());
	//
	// adapter = new GridAdapter(this, R.layout.grid, members);
	// userGridview.setAdapter(adapter);
	//
	// // 保证每次进详情看到的都是最新的group
	// updateGroup();
	//
	// // 设置OnTouchListener
	// userGridview.setOnTouchListener(new OnTouchListener() {
	//
	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// Log.v("event", "down");
	// timer.start();
	//
	// break;
	// case MotionEvent.ACTION_MOVE:
	// Log.v("event", "move");
	// timer.cancel();
	// overtime=false;
	//
	// break;
	// case MotionEvent.ACTION_UP:
	// Log.v("event", "up");
	// timer.cancel();
	// if(!overtime){
	// if (adapter.isInDeleteMode) {
	// adapter.isInDeleteMode = false;
	// adapter.notifyDataSetChanged();
	// }
	// }
	// overtime=false;
	// break;
	// }
	// return false;
	// }
	// });
	//
	// /**
	// * 如果是群主则：长按某个人头像可以进入删除人状态
	// */
	// //
	// if(group.getOwner().equals(EMChatManager.getInstance().getCurrentUser())){
	// // userGridview.setOnItemLongClickListener(new OnItemLongClickListener()
	// {
	// //
	// // @Override
	// // public boolean onItemLongClick(AdapterView<?> parent, View view,
	// // int position, long id) {
	// // Log.v("resultBean_from_jason", "itemlongclick");
	// // if(!adapter.isInDeleteMode){
	// // adapter.isInDeleteMode=true;
	// // adapter.notifyDataSetChanged();
	// // }
	// // return false;
	// // }
	// // });
	// // }
	// }
	//
	// protected void updateGroup() {
	// new Thread(new Runnable() {
	// public void run() {
	// try {
	// final EMGroup returnGroup = EMGroupManager.getInstance()
	// .getGroupFromServer(groupId);
	// // 更新本地数据
	// EMGroupManager.getInstance().createOrUpdateLocalGroup(
	// returnGroup);
	//
	// runOnUiThread(new Runnable() {
	// public void run() {
	// ((TextView) findViewById(R.id.real_groupname))
	// .setText(group.getGroupName());
	// refreshMembers();
	// // if (EMChatManager.getInstance().getCurrentUser()
	// // .equals(group.getOwner())) {
	// // // 显示解散按钮
	// // exitBtn.setVisibility(View.GONE);
	// // } else {
	// // // 显示退出按钮
	// // exitBtn.setVisibility(View.VISIBLE);
	// // }
	//
	// }
	// });
	//
	// } catch (Exception e) {
	// // runOnUiThread(new Runnable() {
	// // public void run() {
	// // loadingPB.setVisibility(View.INVISIBLE);
	// // }
	// // });
	// }
	// }
	// }).start();
	// }
	//
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	//
	// if (resultCode == RESULT_OK) {
	// if (progressDialog == null) {
	// progressDialog = new ProgressDialog(GroupChatInfoActivity.this);
	// progressDialog.setMessage(st1);
	// progressDialog.setCanceledOnTouchOutside(false);
	// }
	// switch (requestCode) {
	// case REQUEST_CODE_DELETE_USER:
	// Bundle bundle = data.getExtras();
	// List<String> userlist = bundle
	// .getStringArrayList(("the_object"));
	// String new_user[]=new String[userlist.size()];
	// for(int i=0;i<new_user.length;i++){
	// new_user[i]=userlist.get(i);
	// }
	// //添加组员操作
	// addMembersToGroup(new_user);
	//
	// break;
	//
	// // case REQUEST_CODE_ADD_USER:// 添加群成员
	// // final String[] newmembers = data
	// // .getStringArrayExtra("newmembers");
	// // progressDialog.setMessage(st1);
	// // progressDialog.show();
	// // addMembersToGroup(newmembers);
	// // break;
	// // case REQUEST_CODE_EXIT: // 退出群
	// // progressDialog.setMessage(st2);
	// // progressDialog.show();
	// // exitGrop();
	// // break;
	// // case REQUEST_CODE_EXIT_DELETE: // 解散群
	// // progressDialog.setMessage(st3);
	// // progressDialog.show();
	// // deleteGrop();
	// // break;
	// case REQUEST_CODE_CLEAR_ALL_HISTORY:
	// // 清空此群聊的聊天记录
	// progressDialog.setMessage(st4);
	// progressDialog.show();
	// clearGroupHistory();
	// break;
	//
	// case REQUEST_CODE_EDIT_GROUPNAME: // 修改群名称
	// final String returnData = data.getStringExtra("data");
	// if (!TextUtils.isEmpty(returnData)) {
	// progressDialog.setMessage(st5);
	// progressDialog.show();
	//
	// new Thread(new Runnable() {
	// public void run() {
	// try {
	// EMGroupManager.getInstance().changeGroupName(
	// groupId, returnData);
	// runOnUiThread(new Runnable() {
	// public void run() {
	// ((TextView) findViewById(R.id.real_groupname))
	// .setText(returnData);
	// progressDialog.dismiss();
	// Toast.makeText(getApplicationContext(),
	// st6, 0).show();
	// }
	// });
	//
	// } catch (EaseMobException e) {
	// e.printStackTrace();
	// runOnUiThread(new Runnable() {
	// public void run() {
	// progressDialog.dismiss();
	// Toast.makeText(getApplicationContext(),
	// st7, 0).show();
	// }
	// });
	// }
	// }
	// }).start();
	// }
	// break;
	// // case REQUEST_CODE_ADD_TO_BALCKLIST:
	// // progressDialog.setMessage(st8);
	// // progressDialog.show();
	// // new Thread(new Runnable() {
	// // public void run() {
	// // try {
	// // EMGroupManager.getInstance().blockUser(groupId,
	// // longClickUsername);
	// // runOnUiThread(new Runnable() {
	// // public void run() {
	// // refreshMembers();
	// // progressDialog.dismiss();
	// // Toast.makeText(getApplicationContext(), stsuccess, 0).show();
	// // }
	// // });
	// // } catch (EaseMobException e) {
	// // runOnUiThread(new Runnable() {
	// // public void run() {
	// // progressDialog.dismiss();
	// // Toast.makeText(getApplicationContext(), st9, 0).show();
	// // }
	// // });
	// // }
	// // }
	// // }).start();
	// //
	// // break;
	// default:
	// break;
	// }
	// }
	// }
	// /**
	// * 修改群名称
	// * @param name
	// */
	// private void modify_group_name(final String name){
	// if (!TextUtils.isEmpty(name)) {
	// progressDialog.setMessage(st5);
	// progressDialog.show();
	//
	// new Thread(new Runnable() {
	// public void run() {
	// try {
	// EMGroupManager.getInstance().changeGroupName(
	// groupId, name);
	// runOnUiThread(new Runnable() {
	// public void run() {
	// ((TextView) findViewById(R.id.real_groupname))
	// .setText(name);
	// progressDialog.dismiss();
	// Toast.makeText(getApplicationContext(),
	// st6, 0).show();
	// }
	// });
	//
	// } catch (EaseMobException e) {
	// e.printStackTrace();
	// runOnUiThread(new Runnable() {
	// public void run() {
	// progressDialog.dismiss();
	// Toast.makeText(getApplicationContext(),
	// st7, 0).show();
	// }
	// });
	// }
	// }
	// }).start();
	// }
	// }
	// /**
	// * 增加群成员
	// *
	// * @param newmembers
	// */
	// private void addMembersToGroup(final String[] newmembers) {
	// final String st6 = getResources().getString(
	// R.string.Add_group_members_fail);
	// new Thread(new Runnable() {
	//
	// public void run() {
	// try {
	// // 创建者调用add方法
	// if (EMChatManager.getInstance().getCurrentUser()
	// .equals(group.getOwner())) {
	// EMGroupManager.getInstance().addUsersToGroup(groupId,
	// newmembers);
	// //直接更新组员gridview表格的data
	// for (int i = 0; i < newmembers.length; i++) {
	// members.add(newmembers[i]);
	// }
	// refreshMembers();
	//
	// } else {
	// // 一般成员调用invite方法
	// EMGroupManager.getInstance().inviteUser(groupId,
	// newmembers, null);
	// // 对方同意之后再更新gridview表格data
	//
	//
	// }
	// runOnUiThread(new Runnable() {
	// public void run() {
	// refreshMembers();
	// ((TextView) findViewById(R.id.real_groupname))
	// .setText(group.getGroupName());
	// progressDialog.dismiss();
	// }
	// });
	// } catch (final Exception e) {
	// runOnUiThread(new Runnable() {
	// public void run() {
	// progressDialog.dismiss();
	// Toast.makeText(getApplicationContext(),
	// st6 + e.getMessage(), 1).show();
	// }
	// });
	// }
	// }
	// }).start();
	// }
	//
	// /**
	// * 退出群组
	// *
	// * @param groupId
	// */
	// private void exitGrop() {
	// String st1 = getResources().getString(
	// R.string.Exit_the_group_chat_failure);
	// new Thread(new Runnable() {
	// public void run() {
	// try {
	// EMGroupManager.getInstance().exitFromGroup(groupId);
	// runOnUiThread(new Runnable() {
	// public void run() {
	// progressDialog.dismiss();
	// setResult(RESULT_OK);
	// finish();
	// if (ChatActivity.activityInstance != null)
	// ChatActivity.activityInstance.finish();
	// }
	// });
	// } catch (final Exception e) {
	// runOnUiThread(new Runnable() {
	// public void run() {
	// progressDialog.dismiss();
	// Toast.makeText(
	// getApplicationContext(),
	// getResources()
	// .getString(
	// R.string.Exit_the_group_chat_failure)
	// + " " + e.getMessage(), 1).show();
	// }
	// });
	// }
	// }
	// }).start();
	// }
	// /**
	// * 解散群组
	// *
	// * @param groupId
	// */
	// private void deleteGrop() {
	// final String st5 =
	// getResources().getString(R.string.Dissolve_group_chat_tofail);
	// new Thread(new Runnable() {
	// public void run() {
	// try {
	// EMGroupManager.getInstance().exitAndDeleteGroup(groupId);
	// runOnUiThread(new Runnable() {
	// public void run() {
	// progressDialog.dismiss();
	// setResult(RESULT_OK);
	// finish();
	// if(ChatActivity.activityInstance != null)
	// ChatActivity.activityInstance.finish();
	// }
	// });
	// } catch (final Exception e) {
	// runOnUiThread(new Runnable() {
	// public void run() {
	// progressDialog.dismiss();
	// Toast.makeText(getApplicationContext(), st5 + e.getMessage(), 1).show();
	// }
	// });
	// }
	// }
	// }).start();
	// }
	//
	// /**
	// * 清空群聊天记录
	// */
	// public void clearGroupHistory() {
	//
	// EMChatManager.getInstance().clearConversation(group.getGroupId());
	// progressDialog.dismiss();
	// //
	// adapter.refresh(EMChatManager.getInstance().getConversation(toChatUsername));
	//
	// }
	//
	// @Click(R.id.clear_all_history)
	// public void clear_all_history() {
	// String st9 = getResources().getString(R.string.sure_to_empty_this);
	// Intent intent = new Intent(GroupChatInfoActivity.this,
	// AlertDialogHx.class);
	// intent.putExtra("cancel", true);
	// intent.putExtra("titleIsCancel", true);
	// intent.putExtra("msg", st9);
	// startActivityForResult(intent, REQUEST_CODE_CLEAR_ALL_HISTORY);
	// }
	//
	// @Click(R.id.group_chat_name)
	// void change_group_name() {
	// System.out.println("setListener");
	//
	// Chatfragment_groupchat dialog2 = Chatfragment_groupchat
	// .newInstance(real_groupname.getText().toString());
	// dialog2.setOnListener(groupnameListener);
	// dialog2.show(fragmentManager, "dialog2");
	// }
	//
	// @Click(R.id.nick_name)
	// void change_my_name() {
	// Chatfragment_groupchat_nickname dialog = Chatfragment_groupchat_nickname
	// .newInstance(my_name_groupchat.getText().toString());
	// dialog.setOnListener(nicknameListener);
	// dialog.show(fragmentManager, "dialog");
	// }
	//
	// Groupchat_DialogCallbackListener groupnameListener = new
	// Groupchat_DialogCallbackListener() {
	// @Override
	// public void oncancel() {
	// Toast.makeText(GroupChatInfoActivity.this, "asd",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// @Override
	// public void save(String newContent) {
	// modify_group_name(newContent);
	// // real_groupname.setText(newContent);
	//
	// }
	// };
	//
	// @Override
	// public void onBackPressed() {
	// if (adapter.isInDeleteMode == true) {
	// adapter.isInDeleteMode = false;
	// adapter.notifyDataSetChanged();
	// }
	//
	// super.onBackPressed();
	// }
	//
	// Groupchat_DialogCallbackListener nicknameListener = new
	// Groupchat_DialogCallbackListener() {
	//
	// @Override
	// public void save(String newContent) {
	// my_name_groupchat.setText(newContent);
	// }
	//
	// @Override
	// public void oncancel() {
	// Toast.makeText(GroupChatInfoActivity.this, "click_nickname_cancel",
	// Toast.LENGTH_SHORT).show();
	// }
	// };
	//
	// private void refreshMembers() {
	// adapter.clear();
	//
	// List<String> members = new ArrayList<String>();
	// members.addAll(group.getMembers());
	// adapter.addAll(members);
	//
	// adapter.notifyDataSetChanged();
	// }
	//
	// /**
	// * 群组成员gridadapter
	// *
	// * @author admin_new
	// *
	// */
	// private class GridAdapter extends ArrayAdapter<String> {
	//
	// private int res;
	// public boolean isInDeleteMode;
	// private List<String> objects;
	//
	// public GridAdapter(Context context, int textViewResourceId,
	// List<String> objects) {
	// super(context, textViewResourceId, objects);
	// this.objects = objects;
	// res = textViewResourceId;
	// isInDeleteMode = false;
	// }
	//
	// @Override
	// public View getView(final int position, View convertView,
	// final ViewGroup parent) {
	// ViewHolder holder = null;
	// if (convertView == null) {
	// holder = new ViewHolder();
	// convertView = LayoutInflater.from(getContext()).inflate(res,
	// null);
	// holder.imageView = (ImageView) convertView
	// .findViewById(R.id.iv_avatar);
	// holder.textView = (TextView) convertView
	// .findViewById(R.id.tv_name);
	// holder.badgeDeleteView = (ImageView) convertView
	// .findViewById(R.id.badge_delete);
	// convertView.setTag(holder);
	// } else {
	// holder = (ViewHolder) convertView.getTag();
	// }
	// final LinearLayout button = (LinearLayout) convertView
	// .findViewById(R.id.button_avatar);
	// // 最后一个item，减人按钮
	// if (position == getCount() - 1) {
	// holder.textView.setText("");
	// // 设置成删除按钮
	// holder.imageView.setImageResource(R.drawable.icon_delete);
	// // button.setCompoundDrawablesWithIntrinsicBounds(0,
	// // R.drawable.smiley_minus_btn, 0, 0);
	// // 如果不是创建者或者没有相应权限，不提供加减人按钮
	// if (!group.getOwner().equals(
	// EMChatManager.getInstance().getCurrentUser())) {
	// // if current user is not group admin, hide add/remove btn
	// convertView.setVisibility(View.GONE);
	// } else { // 显示删除按钮
	// if (isInDeleteMode) {
	// // 正处于删除模式下，隐藏删除按钮
	// convertView.setVisibility(View.INVISIBLE);
	// } else {
	// // 正常模式
	// convertView.setVisibility(View.VISIBLE);
	// convertView.findViewById(R.id.badge_delete)
	// .setVisibility(View.INVISIBLE);
	// }
	//
	// button.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// isInDeleteMode = true;
	// notifyDataSetChanged();
	// }
	// });
	// }
	// } else if (position == getCount() - 2) { // 添加群组成员按钮
	// holder.textView.setText("");
	// holder.imageView.setImageResource(R.drawable.add_rectangle);
	// // button.setCompoundDrawablesWithIntrinsicBounds(0,
	// // R.drawable.smiley_add_btn, 0, 0);
	// // 如果不是创建者或者没有相应权限
	// if (!group.isAllowInvites()
	// && !group.getOwner().equals(
	// EMChatManager.getInstance().getCurrentUser())) {
	// // if current user is not group admin, hide add/remove btn
	// convertView.setVisibility(View.INVISIBLE);
	// } else {
	// // 正处于删除模式下,隐藏添加按钮
	// if (isInDeleteMode) {
	// convertView.setVisibility(View.INVISIBLE);
	// } else {
	// convertView.setVisibility(View.VISIBLE);
	// convertView.findViewById(R.id.badge_delete)
	// .setVisibility(View.INVISIBLE);
	// }
	// button.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// Bundle bundle = new Bundle();
	// bundle.putBoolean("turn_another_activity", true);
	// bundle.putInt("execute", PickContactActivity_.EXECUTE_LATER);
	//
	// //除去自己名字的全体组员链表
	// List<String> members_not_include_myself=new ArrayList<String>();
	// for(int i=0;i<members.size();i++){
	// if(members.get(i).equals(EMChatManager.getInstance().getCurrentUser())){
	// continue;
	// }
	// else{
	// members_not_include_myself.add(members.get(i));
	// }
	// }
	// if(members_not_include_myself.size()>0){
	// bundle.putStringArrayList("members_had_included", (ArrayList<String>)
	// (members_not_include_myself));
	// }
	// Intent intent = new Intent(
	// GroupChatInfoActivity.this,
	// PickContactActivity_.class);
	// intent.putExtras(bundle);
	// startActivityForResult(intent, REQUEST_CODE_DELETE_USER);
	//
	// // 进入选人页面
	// // startActivityForResult(
	// // (new Intent(GroupDetailsActivity.this,
	// // GroupPickContactsActivity.class).putExtra("groupId",
	// // groupId)),
	// // REQUEST_CODE_ADD_USER);
	// }
	// });
	// }
	// } else { // 普通item，显示群组成员
	// final String username = getItem(position);
	// convertView.setVisibility(View.VISIBLE);
	// button.setVisibility(View.VISIBLE);
	// // Drawable avatar =
	// // getResources().getDrawable(R.drawable.default_avatar);
	// // avatar.setBounds(0, 0, referenceWidth, referenceHeight);
	// // button.setCompoundDrawables(null, avatar, null, null);
	// holder.textView.setText(username);
	// UserUtils.setUserAvatar(getContext(), username,
	// holder.imageView);
	// // demo群组成员的头像都用默认头像，需由开发者自己去设置头像
	// if (isInDeleteMode) {
	// // 如果是删除模式下，显示减人图标
	// convertView.findViewById(R.id.badge_delete).setVisibility(
	// View.VISIBLE);
	// } else {
	// convertView.findViewById(R.id.badge_delete).setVisibility(
	// View.INVISIBLE);
	// }
	// final String st12 = getResources().getString(
	// R.string.not_delete_myself);
	// final String st13 = getResources().getString(
	// R.string.Are_removed);
	// final String st14 = getResources().getString(
	// R.string.Delete_failed);
	// final String st15 = getResources().getString(
	// R.string.confirm_the_members);
	// button.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// if (isInDeleteMode) {
	// // 如果是删除自己，return
	// if (EMChatManager.getInstance().getCurrentUser()
	// .equals(username)) {
	// startActivity(new Intent(
	// GroupChatInfoActivity.this,
	// AlertDialogHx.class)
	// .putExtra("msg", st12));
	// return;
	// }
	// if (!NetUtils.hasNetwork(getApplicationContext())) {
	// Toast.makeText(
	// getApplicationContext(),
	// getString(R.string.network_unavailable),
	// 0).show();
	// return;
	// }
	// EMLog.d("group", "remove user from group:"
	// + username);
	// deleteMembersFromGroup(username);
	// } else {
	// // 正常情况下点击user，可以进入用户详情或者聊天页面等等
	// startActivity(new Intent(GroupChatInfoActivity.this,
	// FriendDetailActivity_.class)
	// .putExtra("userId", group.getMembers().get(position)));
	//
	// }
	// }
	//
	// /**
	// * 删除群成员
	// *
	// * @param username
	// */
	// protected void deleteMembersFromGroup(final String username) {
	// final ProgressDialog deleteDialog = new ProgressDialog(
	// GroupChatInfoActivity.this);
	// deleteDialog.setMessage(st13);
	// deleteDialog.setCanceledOnTouchOutside(false);
	// deleteDialog.show();
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// try {
	// // 删除被选中的成员
	// EMGroupManager.getInstance()
	// .removeUserFromGroup(groupId,
	// username);
	// isInDeleteMode = false;
	// runOnUiThread(new Runnable() {
	//
	// @Override
	// public void run() {
	// deleteDialog.dismiss();
	// refreshMembers();
	// ((TextView) findViewById(R.id.main_header_title)).setText(group
	// .getGroupName()
	// + "("
	// + group.getAffiliationsCount()
	// + st);
	// }
	// });
	// } catch (final Exception e) {
	// deleteDialog.dismiss();
	// runOnUiThread(new Runnable() {
	// public void run() {
	// Toast.makeText(
	// getApplicationContext(),
	// st14 + e.getMessage(), 1)
	// .show();
	// }
	// });
	// }
	//
	// }
	// }).start();
	// }
	// });
	//
	// button.setOnLongClickListener(new OnLongClickListener() {
	//
	// @Override
	// public boolean onLongClick(View v) {
	// if (EMChatManager.getInstance().getCurrentUser()
	// .equals(username))
	// return true;
	// if (group.getOwner().equals(
	// EMChatManager.getInstance().getCurrentUser())) {
	// Intent intent = new Intent(
	// GroupChatInfoActivity.this,
	// AlertDialogHx.class);
	// intent.putExtra("msg", st15);
	// intent.putExtra("cancel", true);
	// startActivityForResult(intent,
	// REQUEST_CODE_ADD_TO_BALCKLIST);
	// longClickUsername = username;
	// }
	// return false;
	// }
	// });
	// }
	// return convertView;
	// }
	//
	// @Override
	// public int getCount() {
	// return super.getCount() + 2;
	// }
	// }
	//
	// private static class ViewHolder {
	// ImageView imageView;
	// TextView textView;
	// ImageView badgeDeleteView;
	// }
	// /**
	// *
	// * @ClassName: InterestingGroupBuildActivity.java
	// * @Description: 用于捕捉在gridview长按时间监听
	// * @author: dyq
	// * @date: 2015年8月11日 下午4:19:32
	// */
	// private class Timer extends CountDownTimer{
	//
	// /**
	// * @param millisInFuture
	// * @param countDownInterval
	// */
	// public Timer(long millisInFuture, long countDownInterval) {
	// super(millisInFuture, countDownInterval);
	// }
	//
	// /* (non-Javadoc)
	// * @see android.os.CountDownTimer#onTick(long)
	// */
	// @Override
	// public void onTick(long millisUntilFinished) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// /* (non-Javadoc)
	// * @see android.os.CountDownTimer#onFinish()
	// */
	// @Override
	// public void onFinish() {
	// overtime=true;
	// }
	//
	// }
}
