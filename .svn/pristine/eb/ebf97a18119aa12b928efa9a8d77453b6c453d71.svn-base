//package cn.sx.decentworld.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import android.os.Handler;
//import android.os.Message;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnKeyListener;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import cn.sx.decentworld.R;
//import cn.sx.decentworld.adapter.SearchAdapter;
//import cn.sx.decentworld.bean.Stranger;
//import cn.sx.decentworld.component.KeyboardComponent;
//import cn.sx.decentworld.component.ToastComponent;
//import cn.sx.decentworld.component.ui.SearchComponent;
//import cn.sx.decentworld.network.utils.JsonUtils;
//import cn.sx.decentworld.utils.ViewUtil;
//
//import com.googlecode.androidannotations.annotations.AfterViews;
//import com.googlecode.androidannotations.annotations.Bean;
//import com.googlecode.androidannotations.annotations.Click;
//import com.googlecode.androidannotations.annotations.EActivity;
//import com.googlecode.androidannotations.annotations.ViewById;
//
///**
// * @ClassName: SearchActivity.java
// * @Description: 搜索界面
// * @author: yj
// * @date: 2015年7月17日 上午10:58:08
// */
//
//@EActivity(R.layout.activity_contact_search)
//public class SearchUseLessActivity extends BaseFragmentActivity implements
//		OnClickListener {
//	private String TAG = "SearchActivity";
//	@Bean
//	ToastComponent toast;
//	@Bean
//	KeyboardComponent keyboard;
//	// 编辑框
//	@ViewById(R.id.activity_contact_search_et)
//	EditText activity_contact_search_et;
//	@ViewById(R.id.activity_contact_search_tip)
//	RelativeLayout activity_contact_search_tip;
//	@ViewById(R.id.activity_contact_search_root)
//	LinearLayout activity_contact_search_root;
//	// 手机号搜索
//	@ViewById(R.id.activity_contact_search_choice1)
//	LinearLayout activity_contact_search_choice1;
//	@ViewById(R.id.activity_contact_search_choice2)
//	LinearLayout activity_contact_search_choice2;
//	@ViewById(R.id.activity_contact_search_choice3)
//	LinearLayout activity_contact_search_choice3;
//	@ViewById(R.id.activity_contact_search_choice4)
//	LinearLayout activity_contact_search_choice4;
//	@ViewById(R.id.activity_contact_search_choice5)
//	LinearLayout activity_contact_search_choice5;
//	@ViewById(R.id.activity_contact_search_choice6)
//	LinearLayout activity_contact_search_choice6;
//	@ViewById(R.id.activity_contact_search_choice7)
//	LinearLayout activity_contact_search_choice7;
//	// 手机号搜索
//	@ViewById(R.id.activity_contact_search_choice1_tv)
//	TextView activity_contact_search_choice1_tv;
//	// DW号搜索
//	@ViewById(R.id.activity_contact_search_choice2_tv)
//	TextView activity_contact_search_choice2_tv;
//	// 昵称搜索
//	@ViewById(R.id.activity_contact_search_choice3_tv)
//	TextView activity_contact_search_choice3_tv;
//	// 实名搜索
//	@ViewById(R.id.activity_contact_search_choice4_tv)
//	TextView activity_contact_search_choice4_tv;
//	// 公司搜索
//	@ViewById(R.id.activity_contact_search_choice5_tv)
//	TextView activity_contact_search_choice5_tv;
//	// 学校搜索
//	@ViewById(R.id.activity_contact_search_choice6_tv)
//	TextView activity_contact_search_choice6_tv;
//	// 关键字搜索
//	@ViewById(R.id.activity_contact_search_choice7_tv)
//	TextView activity_contact_search_choice7_tv;
//
//	@ViewById(R.id.pb_contact_search_tip)
//	ProgressBar pb_contact_search_tip;
//
//	@ViewById(R.id.tv_contact_search_tip)
//	TextView tv_contact_search_tip;
//
//	@Bean
//	SearchComponent searchComponent;
//
//	@ViewById(R.id.activity_contact_search)
//	ListView lvSearch;
//
//	// 搜索结果的list
//	private List<Stranger> list;
//	private SearchAdapter searchAdater;
//	private int searchType = 0;
//
//	@AfterViews
//	public void init() {
//		ViewUtil.scaleContentView(activity_contact_search_root);
//		initEdit();
//		activity_contact_search_choice1.setOnClickListener(this);
//		activity_contact_search_choice2.setOnClickListener(this);
//		activity_contact_search_choice3.setOnClickListener(this);
//		activity_contact_search_choice4.setOnClickListener(this);
//		activity_contact_search_choice5.setOnClickListener(this);
//		activity_contact_search_choice6.setOnClickListener(this);
//		activity_contact_search_choice7.setOnClickListener(this);
//		list = new ArrayList<Stranger>();
//		searchAdater = new SearchAdapter(SearchUseLessActivity.this, list);
//		lvSearch.setAdapter(searchAdater);
//	}
//
//	/**
//	 * 初始化编辑框
//	 */
//	private void initEdit() {
//		activity_contact_search_et.setOnKeyListener(new OnKeyListener() {
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				if (keyCode == KeyEvent.KEYCODE_ENTER) {
//					// 修改回车键功能
//					// 先隐藏键盘
//					keyboard.closeKeyboard(activity_contact_search_et);
//					// 执行搜索
//					activity_contact_search_tip.setVisibility(View.VISIBLE);
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							try {
//								Thread.sleep(2000);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//							activity_contact_search_tip
//									.setVisibility(View.GONE);
//						}
//					});
//
//				}
//				return false;
//			}
//		});
//
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			public void run() {
//				keyboard.openKeybord(activity_contact_search_et);
//			}
//		}, 500);
//	}
//
//	Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case 0:
//				// 成功
//				pb_contact_search_tip.setVisibility(View.GONE);
//				// JSONObject jo = JSON.parseObject(msg.obj.toString());
//				// String userID = jo.getString("dwID");
//
//				// Stranger stranger = StrangerManager.queryByDwID(userID);
//				// if (stranger == null)
//				// stranger = new Stranger();
//				//
//				// stranger.setNickName(jo.getString("nickName"));
//				// stranger.setIcon(ImageUtils.getIconByDwID(userID,
//				// ImageUtils.ICON_SMALL));
//				// stranger.setDwID(userID);
//				// stranger.setWorth(jo.getString("worth"));
//				// stranger.setSearchType(searchType);
//				// stranger.save();
//
//				// 注意每个Bean对象要设置searchType，在调用添加朋友的接口时需要；
//				List<Stranger> datas = (List<Stranger>) JsonUtils
//						.json2BeanArray(msg.obj.toString(), Stranger.class);
//				if (null != datas) {
//					list.addAll(datas);
//					// dw_id_searchuser = stranger.getDwID();
//					searchAdater.notifyDataSetChanged();
//				}
//				break;
//			case 1:
//				// 失败
//				searchAdater.notifyDataSetChanged();
//				pb_contact_search_tip.setVisibility(View.GONE);
//				tv_contact_search_tip.setVisibility(View.VISIBLE);
//				tv_contact_search_tip.setText(msg.obj.toString());
//				break;
//			default:
//				break;
//			}
//		};
//	};
//
//	// 搜索按钮
//	@Click(R.id.activity_contact_search_cancel)
//	public void activity_contact_search_cancel() {
//		keyboard.closeKeyboard(activity_contact_search_et);
//		// 执行搜索的代码
//		String phoneNum = activity_contact_search_et.getText().toString();
//		list.clear();
//		searchComponent.searchFriendBy(phoneNum, searchType, handler);
//		pb_contact_search_tip.setVisibility(View.VISIBLE);
//		tv_contact_search_tip.setVisibility(View.GONE);
//	}
//
//	// 清除按钮
//	@Click(R.id.activity_contact_search_clear)
//	public void activity_contact_search_clear() {
//		activity_contact_search_et.setText("");
//		list.clear();
//		searchAdater.notifyDataSetChanged();
//	}
//
//	@Click(R.id.activity_contact_search_root)
//	public void activity_contact_search_root() {
//		keyboard.dismissKeyboard(activity_contact_search_root);
//	}
//
//	// 选择搜索方式
//	@Override
//	public void onClick(View v) {
//		int id = v.getId();
//		switch (id) {
//		case R.id.activity_contact_search_choice1:
//			setAllTabColor(getResources().getColor(R.color.activity_serach_no));
//			activity_contact_search_choice1_tv.setTextColor(getResources()
//					.getColor(R.color.activity_serach));
//			searchType = 0;// 电话号码，不扣费
//			break;
//		case R.id.activity_contact_search_choice2:
//			setAllTabColor(getResources().getColor(R.color.activity_serach_no));
//			activity_contact_search_choice2_tv.setTextColor(getResources()
//					.getColor(R.color.activity_serach));
//			searchType = 1;// dwID号，不扣费
//			break;
//		case R.id.activity_contact_search_choice3:
//			setAllTabColor(getResources().getColor(R.color.activity_serach_no));
//			activity_contact_search_choice3_tv.setTextColor(getResources()
//					.getColor(R.color.activity_serach));
//			searchType = 2;// 昵称
//			break;
//		case R.id.activity_contact_search_choice4:
//			setAllTabColor(getResources().getColor(R.color.activity_serach_no));
//			activity_contact_search_choice4_tv.setTextColor(getResources()
//					.getColor(R.color.activity_serach));
//			searchType = 3;// 实名
//			break;
//		case R.id.activity_contact_search_choice5:
//			setAllTabColor(getResources().getColor(R.color.activity_serach_no));
//			activity_contact_search_choice5_tv.setTextColor(getResources()
//					.getColor(R.color.activity_serach));
//			searchType = 4;// 公司
//			break;
//		case R.id.activity_contact_search_choice6:
//			setAllTabColor(getResources().getColor(R.color.activity_serach_no));
//			activity_contact_search_choice6_tv.setTextColor(getResources()
//					.getColor(R.color.activity_serach));
//			searchType = 5;// 学校
//			break;
//		case R.id.activity_contact_search_choice7:
//			setAllTabColor(getResources().getColor(R.color.activity_serach_no));
//			activity_contact_search_choice7_tv.setTextColor(getResources()
//					.getColor(R.color.activity_serach));
//			searchType = 6;// 关键字
//			break;
//		}
//	}
//
//	/**
//	 * 将所有的搜索Tab键设置成黑色
//	 * 
//	 * @param color
//	 */
//	private void setAllTabColor(int color) {
//		activity_contact_search_choice1_tv.setTextColor(color);
//		activity_contact_search_choice2_tv.setTextColor(color);
//		activity_contact_search_choice3_tv.setTextColor(color);
//		activity_contact_search_choice4_tv.setTextColor(color);
//		activity_contact_search_choice5_tv.setTextColor(color);
//		activity_contact_search_choice6_tv.setTextColor(color);
//		activity_contact_search_choice7_tv.setTextColor(color);
//	}
//
// }
