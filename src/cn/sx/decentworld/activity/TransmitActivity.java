package cn.sx.decentworld.activity;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.TransmitAdapter;
import cn.sx.decentworld.bean.ContactUser;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.fragment.ChatFragment;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_transmit)
public class TransmitActivity extends BaseFragmentActivity implements
		OnItemClickListener {
	@ViewById(R.id.lv_transmit)
	ListView lvTransmit;
	@Bean
	ToastComponent toast;
	private TransmitAdapter adapter;
	List<ContactUser> mDatas;

	@AfterViews
	public void init() {
		mDatas = ContactUser.queryAllList();
		if (null != mDatas) {
			adapter = new TransmitAdapter(mContext, mDatas);
			lvTransmit.setAdapter(adapter);
			lvTransmit.setOnItemClickListener(TransmitActivity.this);
		} else {
			toast.show("获取联系人列表失败");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		ContactUser user = adapter.getItem(position);
		Intent intent = new Intent();
		intent.putExtra(Constants.DW_ID, user.getDwID());
		setResult(RESULT_OK, intent);
		finish();
	}
}
