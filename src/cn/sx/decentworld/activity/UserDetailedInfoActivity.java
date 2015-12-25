/**
 * 
 */
package cn.sx.decentworld.activity;



import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import cn.sx.decentworld.R;
import cn.sx.decentworld.widget.CircularImage;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;



/**
 * @ClassName: UserDetailedInfoActivity.java
 * @Description: 
 * @author: cj
 * @date: 2015年7月16日 上午11:23:04
 */

@EActivity(R.layout.activity_user_detailed_information)
public class UserDetailedInfoActivity extends BaseFragmentActivity
{
//	@ViewById(R.id.iv_user_detail_info_avatar)
//	CircularImage circularImage;
	
	@ViewById(R.id.main_header_left)
	LinearLayout main_header_left;
	
	@AfterViews
	public void init()
	{
//		circularImage.setImageResource(R.drawable.chat_icon_4);
		main_header_left.setVisibility(View.VISIBLE);
	}

}
