/**
 * 
 */
package cn.sx.decentworld.activity;

import android.widget.Button;
import android.widget.RelativeLayout;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ui.MainFragmentComponent;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ChatRoomTopicDetail.java
 * @Description:
 * @author: dyq
 * @date: 2015年8月4日 下午4:36:11
 */
@EActivity(R.layout.activity_chat_room_detail)
public class ChatRoomTopicDetail extends BaseFragmentActivity {
	private static final String TAG = "ChatRoomTopicDetail";
	@Bean
	TitleBar titleBar;
	@Bean
	MainFragmentComponent compont;

	@ViewById(R.id.chat_rooom_detail_bt_join)
	Button chat_rooom_detail_bt_join;

	@ViewById(R.id.root_activity_chat_room_data)
	RelativeLayout root_activity_chat_room_data;

	@AfterViews
	public void init() {
		ViewUtil.scaleContentView(root_activity_chat_room_data);
		titleBar.setTitleBar("返回", "话题类容");
	}

	@Click(R.id.chat_rooom_detail_bt_join)
	public void tojoin() {
		// compont.toActivity(ChatRoomActivity_.class);
	}

	@Click(R.id.main_header_left)
	public void doback() {
		this.finish();
	}
}
