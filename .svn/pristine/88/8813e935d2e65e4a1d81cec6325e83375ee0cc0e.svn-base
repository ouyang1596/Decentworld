/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.GroupGridAdapter;
import cn.sx.decentworld.component.ToastComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: InterestingGroupJoinedInfoActivity.java
 * @Description: 
 * @author: dyq
 * @date: 2015年7月29日 上午10:50:03
 */
@EActivity(R.layout.activity_interesting_group_joined_item_info)
public class InterestingGroupJoinedInfoActivity extends BaseFragmentActivity {
	@Bean
	ToastComponent toast;
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			int ii=(Integer) msg.obj;
//			toast.show(ii+"");
			/**
			 * 服务器操作群聊指定人删除
			 */
			/**
			 * 客户端数据删除
			 */
			members.remove(ii);
			toast.show(members.size()+"");
			gadapter.notifyDataSetChanged();
		};
	};
	 List<String> members = new ArrayList<String>();
	 private FragmentManager fragmentManager;
	 private boolean if_topSwitch=true,if_interrupt=true;
	 private GroupGridAdapter gadapter;
	
	
	 @ViewById(R.id.interesting_group_joined_info_gridview)
	 cn.sx.decentworld.widget.ExpandGridView interesting_group_joined_info_gridview;
	 
	 @Click(R.id.main_header_left)
	 public void doback(){
		 this.finish();
	 }
	 @ViewById(R.id.inter_group_joined_settop)
	 ImageView inter_group_joined_settop;
	 
	 @Click(R.id.inter_group_joined_settop)
	 public void change_icon(){
		 if(if_topSwitch){
			 inter_group_joined_settop.setImageResource(R.drawable.switch_rectangle_open);
				if_topSwitch=false;
				}
				else{
					inter_group_joined_settop.setImageResource(R.drawable.switch_rectangle_close);
					if_topSwitch=true;
				}
	 }
	 @ViewById(R.id.inter_group_joined_no_interrupt)
	 ImageView inter_group_joined_no_interrupt;
	 
	 @Click(R.id.inter_group_joined_no_interrupt)
	 public void change_icon2(){
		 if(if_interrupt){
			 inter_group_joined_no_interrupt.setImageResource(R.drawable.switch_rectangle_open);
				if_interrupt=false;
				}
				else{
					inter_group_joined_no_interrupt.setImageResource(R.drawable.switch_rectangle_close);
					if_interrupt=true;
				}
	 }
	 
	 
	@ViewById(R.id.main_header_right)
	RelativeLayout main_header_right;
	@ViewById(R.id.tv_header_title)
	TextView main_header_title;
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	@ViewById(R.id.main_header_left_tv)
	TextView main_header_left_tv;
	
	
	@AfterViews
	void init(){
//		real_groupname.setText(getIntent().getStringExtra("name"));
		main_header_left.setVisibility(View.VISIBLE);
		main_header_left_tv.setText(R.string.group_chat);
		main_header_title.setText(R.string.chat_info);
		main_header_right.setVisibility(View.INVISIBLE);
		members.add("萝1");
		members.add("萝2");
		members.add("萝3");
		members.add("萝4");
		members.add("萝5");
		gadapter=new GroupGridAdapter(InterestingGroupJoinedInfoActivity.this,R.layout.grid,false, members,handler);
		interesting_group_joined_info_gridview.setAdapter(gadapter);
		interesting_group_joined_info_gridview.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					if(gadapter.candelete==true){
						gadapter.candelete=false;
						gadapter.notifyDataSetChanged();
					}
				}
				return true;
			}
		});
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if(gadapter.candelete==true){
			gadapter.candelete=false;
			gadapter.notifyDataSetChanged();
		}
		
		super.onBackPressed();
	}
}
