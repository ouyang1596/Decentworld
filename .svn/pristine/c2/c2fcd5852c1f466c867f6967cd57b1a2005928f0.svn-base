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
import android.widget.ScrollView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.InterestingGroupGridAdapter;
import cn.sx.decentworld.bean.GroupMember;
import cn.sx.decentworld.component.Common;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: InterestingGroupInfoActivity.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月27日 上午10:06:16
 */
@EActivity(R.layout.activity_interesting_group_item_info)
public class InterestingGroupInfoActivity extends BaseFragmentActivity {
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				gridView.setVisibility(View.GONE);
				break;
			case 2:
				gridView.setVisibility(View.VISIBLE);
			}
			
		};
	};
	@Bean
	Common common;
	
	@ViewById(R.id.main_header_right)
	RelativeLayout main_header_right;
	@ViewById(R.id.tv_header_title)
	TextView main_header_title;
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	@ViewById(R.id.main_header_left_tv)
	TextView main_header_left_tv;

	@ViewById(R.id.gv_members_inter_group)
	cn.sx.decentworld.widget.ExpandGridView gridView;

	@Click(R.id.main_header_left)
	public void click_back() {
		this.finish();
	}
@Click(R.id.rl_toshow_something)
public void showsomething(){
    common.toActivity(InterestingGroupSomeThing_.class);
}
	@ViewById(R.id.activity_interesting_group_item_info_root)
	ScrollView activity_interesting_group_item_info_root;

	@ViewById(R.id.iv_if_toshow_member)
	ImageView iv_if_toshow_member;
	
	@ViewById(R.id.rl_if_toshow_member)
	RelativeLayout rl_if_toshow_member;
	
	private boolean if_show=true;
	@Click(R.id.rl_if_toshow_member)
	void if_toshow(){
		Message msg=new Message();
		if(if_show){
			iv_if_toshow_member.setImageResource(R.drawable.fragment_chat_contact_listitem_hide);
			 if_show=false;
			 msg.what=1;
			 handler.sendMessage(msg);
				}
				else{
					iv_if_toshow_member.setImageResource(R.drawable.fragment_chat_contact_listitem_show);
					if_show=true;
					 msg.what=2;
					 handler.sendMessage(msg);
				}
	}
	
	@AfterViews
	void dosomethy() {
		
		ViewUtil.scaleContentView(activity_interesting_group_item_info_root);
		main_header_left.setVisibility(View.VISIBLE);
		main_header_left_tv.setText(R.string.interesting_group);
		main_header_title.setText(R.string.interesting_group_info);
		main_header_right.setVisibility(View.INVISIBLE);
		List<GroupMember> ll = new ArrayList<GroupMember>();
		GroupMember m1 = new GroupMember(R.drawable.ic_launcher, "萝莉1");
		GroupMember m2 = new GroupMember(R.drawable.ic_launcher, "萝莉2");
		GroupMember m3 = new GroupMember(R.drawable.ic_launcher, "萝莉3");
		GroupMember m4 = new GroupMember(R.drawable.ic_launcher, "萝莉4");
		GroupMember m5 = new GroupMember(R.drawable.ic_launcher, "萝莉5");
		ll.add(m1);
		ll.add(m2);
		ll.add(m3);
		ll.add(m4);
		ll.add(m5);
		gridView.setAdapter(new InterestingGroupGridAdapter(
				getApplicationContext(), ll));
	}
}
