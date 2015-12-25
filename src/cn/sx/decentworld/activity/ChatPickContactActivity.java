package cn.sx.decentworld.activity;

import java.util.List;

import android.widget.ListView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.ChatPickContactAdapter;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.component.ToastComponent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_chat_pick_contact)
public class ChatPickContactActivity extends BaseFragmentActivity {
	@ViewById(R.id.lv_pick_contact)
	ListView lvPickContact;
	@Bean
	ToastComponent toast;
	private ChatPickContactAdapter adapter;
	List<ContactUser> mDatas;

	@AfterViews
	public void init() {
		mDatas = ContactUser.queryAllList();
		if (null != mDatas) {
			adapter = new ChatPickContactAdapter(mContext, mDatas);
			lvPickContact.setAdapter(adapter);
			// lvPickContact.setOnItemClickListener(TransmitActivity.this);
		} else {
			toast.show("获取联系人列表失败");
		}
	}
}
