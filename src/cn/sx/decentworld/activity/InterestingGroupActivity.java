/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.InterestingDistanceAndGroupAdapter;
import cn.sx.decentworld.adapter.InterestingGroupAdapter;
import cn.sx.decentworld.bean.GroupInfo;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: InterestingGroupActivity.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月24日 上午10:34:56
 */
@EActivity(R.layout.activity_interesting_group)
public class InterestingGroupActivity extends BaseFragmentActivity {
	List<String> list_distance=new ArrayList<String>();
	List<GroupInfo>list=new ArrayList<GroupInfo>();
	GroupInfo gi1=new GroupInfo("兴趣组1", R.drawable.ic_launcher, "haohaohaohaohaohaohaohaohaohao", 50, 11, "1km");
	GroupInfo gi2=new GroupInfo("兴趣组2", R.drawable.ic_launcher, "haohaohaohaohaohaohaohaohaohao", 50, 12, "1km");
	GroupInfo gi3=new GroupInfo("兴趣组3", R.drawable.ic_launcher, "haohaohaohaohaohaohaohaohaohao", 50, 13, "2km");
	GroupInfo gi4=new GroupInfo("兴趣组4", R.drawable.ic_launcher, "haohaohaohaohaohaohaohaohaohao", 50, 14, "2km");
	@ViewById(R.id.lv_recommand_group)
	ListView lv_recommand_group;
	@ViewById(R.id.lv_group_distance_group)
	ListView lv_group_distance_group;
	@ViewById(R.id.main_header_right)
	RelativeLayout main_header_right;
	@ViewById(R.id.tv_header_title)
	TextView main_header_title;
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	@ViewById(R.id.main_header_left_tv)
	TextView main_header_left_tv;
	
	
	
	@ViewById(R.id.sv_interesting_group_near)
	ScrollView sv_interesting_group_near;
	
	
	@Click(R.id.main_header_left)
	public void click_back(){
		this.finish();
	}
	@ItemClick(R.id.lv_recommand_group)
	public void recommand_item_click(){
		startActivity(new Intent(this,InterestingGroupInfoActivity_.class));
	}
	@AfterViews
	void dosometh(){
		ViewUtil.scaleContentView(sv_interesting_group_near);
		
		
	Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					startActivity(new Intent(InterestingGroupActivity.this,InterestingGroupInfoActivity_.class));
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		main_header_left.setVisibility(View.VISIBLE);
		main_header_left_tv.setText(R.string.discover);
		main_header_title.setText(R.string.interesting_group);
		main_header_right.setVisibility(View.INVISIBLE);
		list.add(gi1);
		list.add(gi2);
		list.add(gi3);
		list.add(gi4);
		int m=0;
		List<String> list_group=new ArrayList<String>();
		list_group.add(list.get(0).getDistance());
		for (int i = 1; i < list.size(); i++) {
			
			if(list.get(i).getDistance()!=list_group.get(m)){
				list_group.add(list.get(i).getDistance());
				m++;
			}
		}
		lv_recommand_group.setAdapter(new InterestingGroupAdapter(InterestingGroupActivity.this, list));
		
		
		
		lv_group_distance_group.setAdapter(new InterestingDistanceAndGroupAdapter(InterestingGroupActivity.this, list_group,list,handler));
	}
}
