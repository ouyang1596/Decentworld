/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.GroupGridAdapter;
import cn.sx.decentworld.component.Common;
import cn.sx.decentworld.component.ToastComponent;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: InterestingGroupBuildActivity.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月31日 下午2:37:08
 */
@EActivity(R.layout.activitiy_interesting_group_build)
public class InterestingGroupBuildActivity extends BaseFragmentActivity {

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (msg.obj.equals(1)) {
					gridView.setVisibility(View.GONE);
				} else if (msg.obj.equals(2)) {
					gridView.setVisibility(View.VISIBLE);
				}
				break;
			case 2:
				int ii = (Integer) msg.obj;
				/**
				 * 服务器操作群聊指定人删除
				 */
				/**
				 * 客户端数据删除
				 */
				members.remove(ii);
				toast.show(members.size() + "");
				gadapter.notifyDataSetChanged();
				break;
			}

		};
	};
	@Bean
	ToastComponent toast;

	@Bean
	Common common;

	@ViewById(R.id.build_activity_interesting_group_item_info_root)
	LinearLayout build_activity_interesting_group_item_info_root;
	
	@ViewById(R.id.main_header_right)
	RelativeLayout main_header_right;
	
	@ViewById(R.id.main_header_right_tv)
	TextView main_header_right_tv;
	
	@ViewById(R.id.main_header_right_btn)
	ImageView main_header_right_btn;
	
	@ViewById(R.id.tv_header_title)
	TextView main_header_title;
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	@ViewById(R.id.main_header_left_tv)
	TextView main_header_left_tv;

	@ViewById(R.id.build_gv_members_inter_group)
	cn.sx.decentworld.widget.ExpandGridView gridView;
	@ViewById(R.id.build_iv_if_toshow_member)
	ImageView build_iv_if_toshow_member;

	@ViewById(R.id.build_group_name)
	TextView build_group_name;
	@ViewById(R.id.build_tv_place_inter_group)
	TextView build_tv_place_inter_group;

	@Click(R.id.main_header_left)
	public void click_back() {
		this.finish();
	}

	@Click(R.id.rl_toshow_something)
	public void showsomething() {
	    common.toActivity(InterestingGroupSomeThing_.class);
	}

	private boolean if_show = true;
	//gridView数据来源(包括自己名字)
	private List<String> members = new ArrayList<String>();
	//点击后增加的人链表（不包括自己）
	private ArrayList<String> members_had_included=new ArrayList<String>();
	
	
	private GroupGridAdapter gadapter;
//	private Timer timer=new Timer(1000, 500);
	private boolean overtime=false;
	
	@Click(R.id.build_rl_if_toshow_member)
	void if_toshow() {
		Message msg = new Message();
		msg.what = 1;
		if (if_show) {
			build_iv_if_toshow_member
					.setImageResource(R.drawable.fragment_chat_contact_listitem_hide);
			if_show = false;
			msg.obj = 1;
			handler.sendMessage(msg);
		} else {
			build_iv_if_toshow_member
					.setImageResource(R.drawable.fragment_chat_contact_listitem_show);
			if_show = true;
			msg.obj = 2;
			handler.sendMessage(msg);
		}
	}
	
//	
//	/**
//	 * 创建兴趣组
//	 */
//	@Click(R.id.main_header_right_tv)
//	public void setInterestingGroup(){
//		if (members.size() > 2) {
//			final ProgressDialog progressDialog;
//
//			String st1 = getResources().getString(
//					R.string.is_to_create_a_interesting_group_chat);
//			progressDialog = new ProgressDialog(this);
//			progressDialog.setMessage(st1);
//			progressDialog.setCanceledOnTouchOutside(false);
//			progressDialog.show();
//
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					String interesting_groupName = EMChatManager.getInstance()
//							.getCurrentUser() + "+";
//					final String st2 = getResources().getString(
//							R.string.Failed_to_create_interesting_group)
//							+ ",";
//					for (int i = 0; i < members.size(); i++) {
//						interesting_groupName += members.get(i) + "+";
//					}
//					// 去掉最后一个"+"号
//					interesting_groupName=interesting_groupName.substring(0, interesting_groupName.length() - 1);
//
//					if (interesting_groupName.length() > 18) {
//						interesting_groupName=interesting_groupName.substring(0, 17);
//						interesting_groupName += "...";
//					}
//
//					String[] interesting_members = new String[members.size()-1];
//					//组员的字符串数组注意要跳过自己（i+1）
//					for (int i = 0; i < interesting_members.length; i++) {
//						interesting_members[i] = members.get(i+1);
//
//					}
//					try {
//						EMGroupManager.getInstance()
//								.createPublicGroup(interesting_groupName, "",
//										interesting_members, true, 200);
//						runOnUiThread(new Runnable() {
//							public void run() {
//								progressDialog.dismiss();
//								setResult(RESULT_OK);
//								finish();
//							}
//						});
//					} catch (final EaseMobException e) {
//						runOnUiThread(new Runnable() {
//							public void run() {
//								progressDialog.dismiss();
//								Toast.makeText(
//										getApplicationContext(),
//										st2 + e.getLocalizedMessage(),
//										1).show();
//							}
//						});
//					}
//				}
//
//			}).start();
//
//		} else {
//			toast.show("人数不够");
//		}
//	}
//	@AfterViews
//	public void init() {
//		ViewUtil.scaleContentView(build_activity_interesting_group_item_info_root);
//		
//		String full_address = getIntent().getStringExtra("full_address");
//		String group_name = getIntent().getStringExtra("group_name");
//		build_group_name.setText(group_name);
//		build_tv_place_inter_group.setText(full_address);
//
//		
//		members.add(EMChatManager.getInstance().getCurrentUser());
//		main_header_left.setVisibility(View.VISIBLE);
//		main_header_left_tv.setText(R.string.cancel);
//		main_header_title.setText(R.string.interesting_group_info);
//		main_header_right.setVisibility(View.VISIBLE);
//		main_header_right_btn.setVisibility(View.INVISIBLE);
//		main_header_right_tv.setText(getResources().getString(R.string.set));
//		gadapter = new GroupGridAdapter(InterestingGroupBuildActivity.this,
//				R.layout.grid, false, members, handler);
//		gridView.setAdapter(gadapter);
//		gridView.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					Log.v("event", "down");
//					timer.start();
//					
//					break;
//				case MotionEvent.ACTION_MOVE:
//					Log.v("event", "move");
//					timer.cancel();
//					overtime=false;
//					
//					break;
//				case MotionEvent.ACTION_UP:
//					Log.v("event", "up");
//					timer.cancel();
//					if(!overtime){
//						if (gadapter.candelete) {
//							gadapter.candelete = false;
//							gadapter.notifyDataSetChanged();
//						}
//					}
//					overtime=false;
//					break;
//				}
//				return false;
//			}
//			
//		});
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// 进入选取联系人activity选人
//				if (position == gadapter.getCount() - 2) {
//					Bundle bundle = new Bundle();
//					bundle.putBoolean("turn_another_activity", true);
//					bundle.putInt("execute", PickContactActivity_.EXECUTE_LATER);
//					
////					members_had_included=(ArrayList<String>) members.subList(1, members.size());
//					members_had_included=new ArrayList<String>();
//					//因为members的第一位为存自己的名字，故跳过0位。
//					for(int i=1;i<members.size();i++){
//						members_had_included.add(members.get(i));
//					}
////					members_had_included.remove(0);
//					if(members_had_included.size()>0){
//						bundle.putStringArrayList("members_had_included",members_had_included);
//					}
//					Intent intent = new Intent(
//							InterestingGroupBuildActivity.this,
//							PickContactActivity_.class);
//					intent.putExtras(bundle);
//					startActivityForResult(intent, 1);
//				}
////				else if(position==gadapter.getCount() -1){
////					if (!gadapter.candelete) {
////						gadapter.candelete = true;
////						gadapter.notifyDataSetChanged();
////					} else {
////						gadapter.candelete = false;
////						gadapter.notifyDataSetChanged();
////					}
////				}
//			}
//		});
//		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				if(!gadapter.candelete){
//					gadapter.candelete=true;
//					gadapter.notifyDataSetChanged();
//				}
//				return false;
//			}
//		});
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int,
//	 * android.content.Intent)
//	 */
//	@Override
//	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//		switch (arg0) {
//		case 1: {
//			if (arg1 == RESULT_OK) {
//				Bundle bundle = arg2.getExtras();
//				List<String> userlist = bundle
//						.getStringArrayList(("the_object"));
//				String string = "";
//				for (int i = 0; i < userlist.size(); i++) {
//					members.add(userlist.get(i));
//				}
//				toast.show(string);
////				gadapter.notifyDataSetChanged();
//				gadapter = new GroupGridAdapter(InterestingGroupBuildActivity.this,
//						R.layout.grid, false, members, handler);
//				gridView.setAdapter(gadapter);
//			}
//
//		}
//			break;
//
//		default:
//			break;
//		}
//	}
//	/**
//	 * 
//	 * @ClassName: InterestingGroupBuildActivity.java
//	 * @Description: 用于捕捉在gridview长按时间监听
//	 * @author: dyq
//	 * @date: 2015年8月11日 下午4:19:32
//	 */
//	private class Timer extends CountDownTimer{
//
//		/**
//		 * @param millisInFuture
//		 * @param countDownInterval
//		 */
//		public Timer(long millisInFuture, long countDownInterval) {
//			super(millisInFuture, countDownInterval);
//		}
//
//		/* (non-Javadoc)
//		 * @see android.os.CountDownTimer#onTick(long)
//		 */
//		@Override
//		public void onTick(long millisUntilFinished) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		/* (non-Javadoc)
//		 * @see android.os.CountDownTimer#onFinish()
//		 */
//		@Override
//		public void onFinish() {
//			overtime=true;
//		}
//		
//	}
}
