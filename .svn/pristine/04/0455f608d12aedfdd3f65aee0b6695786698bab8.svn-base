/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.BuildGroupLocationDialog;
import cn.sx.decentworld.dialog.BuildGroupLocationDialog.AddressListener;
import cn.sx.decentworld.dialog.BuildGroupNameDialog;
import cn.sx.decentworld.dialog.BuildGroupNameDialog.NameListener;
import cn.sx.decentworld.dialog.BuildGroupTipsDialog;
import cn.sx.decentworld.utils.ViewUtil;

/**
 * @ClassName: InterestingGroupBuildRuleActivity.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月31日 上午9:18:30
 */
@EActivity(R.layout.activity_build_group_rule)
public class InterestingGroupBuildRuleActivity extends BaseFragmentActivity {

	@Bean
	ToastComponent toast;

	@ViewById(R.id.main_header_right)
	RelativeLayout main_header_right;

	@ViewById(R.id.tv_header_title)
	TextView main_header_title;
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	@ViewById(R.id.main_header_left_tv)
	TextView main_header_left_tv;

	
	@ViewById(R.id.root_activitiy_build_group_rule)
	LinearLayout root_activitiy_build_group_rule;
	
	String full_address = "";
	String address1 = "";
	String group_name = "";
	Thread threadd = new Thread() {
		public void run() {
			try {
				sleep(2000);
				dialog3.dismiss();
				Intent intent = new Intent(
						InterestingGroupBuildRuleActivity.this,
						InterestingGroupBuildActivity_.class);
				intent.putExtra("full_address", full_address);
				intent.putExtra("group_name", group_name);
				startActivity(intent);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
	BuildGroupLocationDialog dialog;
	BuildGroupNameDialog dialog2;
	BuildGroupTipsDialog dialog3;
	FragmentManager fragmentManager;
	AddressListener listener = new AddressListener() {
		@Override
		public void PutAddress(String address) {
			full_address = address;

			dialog2 = BuildGroupNameDialog.newInstance();
			dialog2.setListener(name_listener);
			dialog2.show(fragmentManager, "dialog2");
		}

	};
	NameListener name_listener = new NameListener() {

		@Override
		public void putName(String name) {
			if (!name.equals("")) {
				group_name = name;
				try {
					dialog2.dismiss();
					dialog3 = BuildGroupTipsDialog.newInstance();
					dialog3.setCancelable(false);
					dialog3.show(fragmentManager, "dialog3");
					threadd.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				dialog2.showtips();
			}
		}

	};

	@AfterViews
	void init() {
		ViewUtil.scaleContentView(root_activitiy_build_group_rule);
		main_header_left.setVisibility(View.VISIBLE);
		main_header_left_tv.setText(R.string.interesting_group);
		main_header_title.setText(R.string.build_group);
		main_header_right.setVisibility(View.INVISIBLE);

		fragmentManager = getSupportFragmentManager();
		/*
		 * 坐标定位
		 */
		address1 = "前海合作区总站";
	}

	@Click(R.id.bt_rule_toshow_fragment)
	public void toshow() {
		dialog = BuildGroupLocationDialog.newInstance(address1);
		dialog.setListener(listener);
		dialog.show(fragmentManager, "dialog");
	}

	@Click(R.id.main_header_left)
	public void toback() {
		finish();
	}
	/* (non-Javadoc)
	 * @see cn.sx.decentworld.activity.BaseFragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		threadd = new Thread() {
			public void run() {
				try {
					sleep(2000);
					dialog3.dismiss();
					Intent intent = new Intent(
							InterestingGroupBuildRuleActivity.this,
							InterestingGroupBuildActivity_.class);
					intent.putExtra("full_address", full_address);
					intent.putExtra("group_name", group_name);
					startActivity(intent);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
	}

}
