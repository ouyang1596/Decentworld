/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OnActivityResult;
import com.googlecode.androidannotations.annotations.ViewById;

import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.PublicWorkComponent;
import cn.sx.decentworld.utils.ViewUtil;
import cn.sx.decentworld.widget.ImageViewForGridView;

/**
 * @ClassName: PublicWorkActivity.java
 * @Description: 发布作品圈
 * @author: yj
 * @date: 2015年7月27日 下午3:08:26
 */
@EActivity(R.layout.activity_publicwork)
public class PublicWorkActivity extends BaseFragmentActivity implements OnClickListener
{

	public static final int PUBLIC_WORK = 10;
	
	@ViewById(R.id.ll_public_work_root)
	LinearLayout ll_public_work_root;

	// 作品描述
	@ViewById(R.id.tv_public_work_description)
	TextView tv_public_work_description;
	// 作品图像
	@ViewById(R.id.gv_public_work_pictures)
	ImageViewForGridView gv_public_work_pictures;

	// 部分朋友可见
	@ViewById(R.id.rl_public_work_see)
	RelativeLayout rl_public_work_see;

	// 不给谁看
	@ViewById(R.id.rl_public_work_no_see)
	RelativeLayout rl_public_work_no_see;

	// 仅我的朋友可见
	@ViewById(R.id.ll_public_work_friend)
	LinearLayout ll_public_work_friend;
	@ViewById(R.id.rb_public_work_friend)
	RadioButton rb_public_work_friend;

	// 陌生人可见
	@ViewById(R.id.ll_public_work_stranger)
	LinearLayout ll_public_work_stranger;
	@ViewById(R.id.rb_public_work_stranger)
	RadioButton rb_public_work_stranger;
	
	@ViewById(R.id.tv_public_work_confirm)
	TextView tv_public_work_confirm;

	@Bean
	ChoceAndTakePictureComponent c;

	@Bean
	PublicWorkComponent publicWorkComponent;

	@Bean
	ToastComponent toast;

	@AfterViews
	public void init()
	{
		ViewUtil.scaleContentView(ll_public_work_root);
		publicWorkComponent.initNavbar();
		initListener();
	}

	/**
	 * 
	 */
	private void initListener()
	{
		rl_public_work_see.setOnClickListener(this);
		rl_public_work_no_see.setOnClickListener(this);

		ll_public_work_friend.setOnClickListener(this);
		ll_public_work_stranger.setOnClickListener(this);
		tv_public_work_confirm.setOnClickListener(this);
	}

	@OnActivityResult(PUBLIC_WORK)
	void onResult(int resultCode, Intent data)
	{

	}

	private Boolean isFriend = false;
	private Boolean isStranger = false;
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.rl_public_work_see:
			toast.show("1 设置跳转页面");
			break;
		case R.id.rl_public_work_no_see:
			toast.show("2  设置跳转页面");
			break;

		case R.id.ll_public_work_friend:
			if (isFriend)
			{
				rb_public_work_friend.setButtonDrawable(R.drawable.public_work_no_checked);
				isFriend = false;
			}
			else {
				rb_public_work_friend.setButtonDrawable(R.drawable.public_work_checked);
				isFriend = true;
			}
			
			break;
		case R.id.ll_public_work_stranger:
			if (isStranger)
			{
				rb_public_work_stranger.setButtonDrawable(R.drawable.public_work_no_checked);
				isStranger = false;
			}
			else {
				rb_public_work_stranger.setButtonDrawable(R.drawable.public_work_checked);
				isStranger = true;
			}
			break;
		case R.id.tv_public_work_confirm:
			toast.show("发表成功");
			break;

		default:
			break;
		}
	}
}
