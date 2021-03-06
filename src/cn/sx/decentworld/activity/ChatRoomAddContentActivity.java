/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: ChatRoomAddContentActivity.java
 * @Description:
 * @author: dyq
 * @date: 2015年8月5日 上午10:42:33
 */
@EActivity(R.layout.activity_chat_room_add_content)
public class ChatRoomAddContentActivity extends BaseFragmentActivity {
	private static final String TAG = "ChatRoomAddContentActivity";
	@ViewById(R.id.ll_chat_room_add_content_root)
	LinearLayout ll_chat_room_add_content_root;

	@Bean
	TitleBar titleBar;

	@ViewById(R.id.et_chat_room_add_content_decription)
	EditText et_chat_room_add_content_decription;

	String old_content = "";

	@AfterViews
	public void init() {
		ViewUtil.scaleContentView(ll_chat_room_add_content_root);
		titleBar.setTitleBar("返回", "添加类容", "完成");

		Bundle bundle = getIntent().getExtras();
		if (bundle.getString("old_content") != null) {
			old_content = bundle.getString("old_content");
		}
		if (!old_content.equals("点击进入编辑")) {
			et_chat_room_add_content_decription.setText(old_content);
		}
	}

	@Click(R.id.main_header_left)
	public void doback() {
		finish();
	}

	@Click(R.id.main_header_right)
	public void complete() {
		Intent intent = new Intent();
		if (et_chat_room_add_content_decription.getText().toString().equals(""))
			intent.putExtra("new_content", "点击进入编辑");
		else
			intent.putExtra("new_content", et_chat_room_add_content_decription.getText().toString());

		setResult(RESULT_OK, intent);
		finish();
		LogUtils.i(TAG, "点击确定确定");
	}
}
