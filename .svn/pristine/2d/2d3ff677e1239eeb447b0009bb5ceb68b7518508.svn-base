package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.smack.packet.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.PickContactForCallingCardAdapter;
import cn.sx.decentworld.adapter.PickContactForCallingCardContactAdapter;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.bean.SearchResult;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.SearchComponent;
import cn.sx.decentworld.engine.ChatSingleEngine;
import cn.sx.decentworld.engine.ChatSingleEngine.SendCardToListener;
import cn.sx.decentworld.entity.dao.ContactUserDao;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.widget.ClearEditText;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName: SearchActivity.java
 * @Description: 搜索界面
 * @author: yj
 * @date: 2015年7月17日 上午10:58:08
 */
@EActivity(R.layout.activity_pick_contact_for_calling_card)
public class PickContactForCallingCardActivity extends BaseFragmentActivity implements OnClickListener ,SendCardToListener{
	@ViewById(R.id.cet_search)
	ClearEditText cetSearch;
	@ViewById(R.id.tv_cancel)
	TextView tvCancel;
	@ViewById(R.id.tv_search)
	TextView tvSearch;
	@ViewById(R.id.lv_search)
	PullToRefreshListView lvSearch;
	@ViewById(R.id.lv_contact)
	ListView lvContact;
	private int page;
	@Bean
	SearchComponent searchComponent;
	private String name, dwID;
	@Bean
	ToastComponent toast;
	private String mSearchType;// 0表示手机号1表示dwID号3表示普通搜索
	// private int searchType = SearchResult.SEARCH_TYPE_PHONE;//
	// 搜索类型,默认为按电话号码搜素
	private List<SearchResult> data; // 搜索结果的list
	private PickContactForCallingCardAdapter pickContactForCallingAdapter;
	private List<ContactUser> mDatas = new ArrayList<ContactUser>();
	private PickContactForCallingCardContactAdapter contactAdapter;

	@AfterViews
	public void init() {
	    EventBus.getDefault().register(this);
		PGetIntent();
		initAdapter();
		// rgSearchTag.setOnCheckedChangeListener(this);
		tvCancel.setOnClickListener(this);
		tvSearch.setOnClickListener(this);
		cetSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() <= 0) {
					lvSearch.setVisibility(View.GONE);
					lvContact.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void PGetIntent() {
		name = getIntent().getStringExtra(Constants.NAME);
		dwID = getIntent().getStringExtra(Constants.DW_ID);
	}

	@SuppressWarnings("unchecked")
	private void initAdapter() {
		data = new ArrayList<SearchResult>();
		pickContactForCallingAdapter = new PickContactForCallingCardAdapter(PickContactForCallingCardActivity.this, data);
		lvSearch.setVisibility(View.GONE);
		lvContact.setVisibility(View.VISIBLE);
		lvSearch.setAdapter(pickContactForCallingAdapter);
		lvSearch.setMode(Mode.BOTH);
		lvSearch.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				data.clear();
				page = 0;
				searchPeople(page);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page++;
				searchPeople(page);
			}
		});
		lvSearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				position = position - 1;
				SearchResult item = pickContactForCallingAdapter.getItem(position);
				// 这里写发送名片接口
				String otherID = item.dwID;
				String otherNickname = item.name;
				float otherWorth = Float.valueOf(item.worth);
				int userType = item.userType;
				ChatSingleEngine.getInstance().sendCardTo(dwID, name, otherID, otherNickname, otherWorth,userType,PickContactForCallingCardActivity.this);
			}
		});
		// ========我的朋友联系人==========
		List<ContactUser> temp = ContactUserDao.queryAll();
		mDatas.addAll(temp);
		contactAdapter = new PickContactForCallingCardContactAdapter(this, mDatas);
		lvContact.setAdapter(contactAdapter);
		lvContact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ContactUser item = contactAdapter.getItem(position);
				// 这里写发送名片接口
				String otherID = item.getFriendID();
				String otherNickname = item.getShowName();
				float otherWorth = Float.valueOf(item.getWorth());
				int userType = item.userType;
				ChatSingleEngine.getInstance().sendCardTo(dwID, name, otherID, otherNickname, otherWorth,userType,PickContactForCallingCardActivity.this);

			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_cancel:
			finish();
			break;
		case R.id.tv_search:
			lvContact.setVisibility(View.GONE);
			lvSearch.setVisibility(View.VISIBLE);
			data.clear();
			page = 0;
			searchPeople(page);
			break;
		}
	}

	private void searchPeople(int page) {
		if (cetSearch.length() <= 0) {
			toast.show("请输入要搜索的内容");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchKey", cetSearch.getText().toString());
		map.put("page", "" + page);
		searchComponent.searchFriendBy(map, mSearchHandler);
	}

	private Handler mSearchHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			lvSearch.onRefreshComplete();
			switch (msg.what) {
			case 2222:
				try {
					JSONObject object = new JSONObject(msg.obj.toString());
					mSearchType = object.getString("searchType");
					JSONArray array = object.getJSONArray("result");
					LogUtils.e("bm", "array--" + array.toString());
					List<SearchResult> datas = (List<SearchResult>) JsonUtils
							.json2BeanArray(array.toString(), SearchResult.class);
					if (null != datas) {
						data.addAll(datas);
						pickContactForCallingAdapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					toast.show("解析错误");
				}
				break;
			default:
				data.clear();
				pickContactForCallingAdapter.notifyDataSetChanged();
				break;
			}
		};
	};
	

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    
    ///////////////////////////////////////////////处理发送名片回执///////////////////////////////////////////
    private DWMessage dwMessage;

    /**
     * 发送成功
     */
    @Override
    public void onSuccess(DWMessage dwMessage)
    {
        this.dwMessage = dwMessage;
    }


    /**
     * 发送失败
     */
    @Override
    public void onFailure(String cause)
    {
        toast.show("发送失败,请检查网络");
    }
    
    
    /**
     * 发送名片回执
     */
    @Subscriber(tag = NotifyByEventBus.NT_SEND_CARD_ACK)
    public void sendCardSuccess(String packetId)
    {
        if(dwMessage != null && packetId.endsWith(dwMessage.getTxtMsgID()))
        {
            toast.show("发送成功");
            finish();
        }
    }
    
    /**
     * 发送名片回执
     */
    @Subscriber(tag = NotifyByEventBus.NT_WEALTH_SHORTAGE)
    public void sendCardFailure(String packetId)
    {
        if(dwMessage != null && packetId.endsWith(dwMessage.getTxtMsgID()))
        {
            toast.show("身家不足");
            finish();
        }
    }
    
}
