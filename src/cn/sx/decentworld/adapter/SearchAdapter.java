/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.BaseFragmentActivity;
import cn.sx.decentworld.activity.NearCardDetailActivity_;
import cn.sx.decentworld.bean.NearbyStrangerInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.dialog.AddFriendDialog;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment;
import cn.sx.decentworld.dialog.AddFriendDialog.AddFriendListener;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment.OnCommomPromptListener;
import cn.sx.decentworld.dialog.InputNumberDialog;
import cn.sx.decentworld.dialog.InputNumberDialog.InputNumberListener;
import cn.sx.decentworld.engine.ContactEngine;
import cn.sx.decentworld.engine.ContactEngine.AddCallback;
import cn.sx.decentworld.entity.SearchResult;
import cn.sx.decentworld.entity.dao.ContactUserDao;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.fragment.stranger.StrangerListFragment;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.SelfInfoManager;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.ToastUtil;
import cn.sx.decentworld.widget.CircularImageView;

import com.android.volley.Request.Method;

/**
 * 
 * @ClassName: SearchAdapter.java
 * @Description: 搜索适配器
 * @author: cj
 * @date: 2016年3月22日 下午3:05:21
 */
public class SearchAdapter extends BaseAdapter implements OnCommomPromptListener {
	private static final String TAG = "SearchAdapter";
	private Context context;
	private List<SearchResult> data;
	private LayoutInflater inflater;
	private FragmentManager fragmentManager;
	private HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
	private SendUrl mSendUrl;
	private String recommendedID;

	public SearchAdapter(Context context, List<SearchResult> list) {
		super();
		this.context = context;
		this.data = list;
		inflater = LayoutInflater.from(context);
		mSendUrl = new SendUrl(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public SearchResult getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup parent) {
		ViewHolder vh = null;
		if (con == null) {
			vh = new ViewHolder();
			con = inflater.inflate(R.layout.activity_interesting_group_joined_item, null);
			vh.ivSearchAvatar = (CircularImageView) con.findViewById(R.id.iv_search_avatar);
			vh.ivDoubt = (CircularImageView) con.findViewById(R.id.iv_doubt);
			vh.ivSearchAvatar.setOnClickListener(mOnCListener);
			vh.ivSearchAdd = (ImageView) con.findViewById(R.id.iv_search_add);
			vh.ivSearchAdd.setOnClickListener(mOnCListener);
			vh.tvEnjoy = (TextView) con.findViewById(R.id.tv_enjoy);
			vh.tvEnjoy.setOnClickListener(mOnCListener);
			vh.tvSearchAge = (TextView) con.findViewById(R.id.tv_Search_age);
			vh.tvSearchName = (TextView) con.findViewById(R.id.tv_search_name);
			vh.tvSearchOccupation = (TextView) con.findViewById(R.id.tv_search_occupation);
			vh.tvSearchSex = (TextView) con.findViewById(R.id.tv_search_sex);
			vh.tvSearchMatch = (TextView) con.findViewById(R.id.tv_search_match);
			vh.tvSearchWorth = (TextView) con.findViewById(R.id.tv_search_worth);
			vh.tvSearchTagRealName = (TextView) con.findViewById(R.id.tv_search_tag_realname);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}

		SearchResult searchResult = data.get(position);
		showMatchTxt(vh, searchResult);
		if ("0".equals(searchResult.realName)) {
			vh.tvSearchTagRealName.setVisibility(View.GONE);
		} else {
			vh.tvSearchTagRealName.setVisibility(View.VISIBLE);
		}
		if (ContactUser.UserType.IMPEACH.getIndex() == searchResult.userType) {
			vh.ivDoubt.setVisibility(View.VISIBLE);
			vh.ivDoubt.setImageResource(R.drawable.doubt_person);
		} else if (ContactUser.UserType.NORMAL.getIndex() == searchResult.userType) {
			vh.ivDoubt.setVisibility(View.GONE);
		} else if (ContactUser.UserType.VIP.getIndex() == searchResult.userType) {
			vh.ivDoubt.setVisibility(View.VISIBLE);
			vh.ivDoubt.setImageResource(R.drawable.wan_person);
		}
		vh.ivSearchAvatar.setTag(Constants.ITEM_POSITION, position);
		vh.ivSearchAdd.setTag(Constants.ITEM_POSITION, position);
		vh.tvEnjoy.setTag(Constants.ITEM_POSITION, position);
		if ("女".equals(searchResult.gender)) {
			vh.tvEnjoy.setText("看好她");
		} else {
			vh.tvEnjoy.setText("看好他");
		}
		if (ContactUserDao.isContact(searchResult.dwID)) {
			vh.ivSearchAdd.setVisibility(View.GONE);
		} else {
			vh.ivSearchAdd.setVisibility(View.VISIBLE);
		}
		vh.tvSearchName.setText(searchResult.name);
		vh.tvSearchOccupation.setText(searchResult.occupation);
		vh.tvSearchSex.setText(searchResult.gender);
		vh.tvSearchWorth.setText(searchResult.worth);
		// 设置头像
		String avatarPath = ImageUtils.getIconByDwID(searchResult.dwID, ImageUtils.ICON_SMALL);
		if (CommUtil.isNotBlank(avatarPath)) {
			ImageLoaderHelper.mImageLoader.displayImage(avatarPath, vh.ivSearchAvatar, ImageLoaderHelper.mOptions);
		}
		if (searchResult.isRecommended) {
			vh.tvEnjoy.setVisibility(View.GONE);
		} else {
			vh.tvEnjoy.setVisibility(View.VISIBLE);
		}
		return con;
	}

	private void showMatchTxt(ViewHolder vh, SearchResult searchResult) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < searchResult.matchList.size(); i++) {
			builder.append(searchResult.matchList.get(i).key + ":" + searchResult.matchList.get(i).value + ";");
			// TextView textView = new TextView(context);
			// textView.setTextColor(Color.parseColor("#99000000"));
			// if (i >= 2) {
			// textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			// textView.setText("...");
			// vh.llSearchMatch.addView(textView);
			// break;
			// } else {
			// String value = searchResult.matchList.get(i).key + ":"
			// + searchResult.matchList.get(i).value;
			// String match = getTag(Constants.MATCH);
			// int index = value.indexOf(match);
			// SpannableStringBuilder highLightTxt = new SpannableStringBuilder(
			// value);
			// // i 未起始字符索引，j 为结束字符索引
			// highLightTxt.setSpan(new ForegroundColorSpan(Color.RED), index,
			// index + match.length(),
			// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			// textView.setText(highLightTxt);
			// vh.llSearchMatch.addView(textView);
			// }
		}
		if (builder.length() <= 0) {
			return;
		}
		String value = builder.substring(0, builder.length() - 1);
		String match = getTag(Constants.MATCH);
		SpannableStringBuilder highLightTxt = new SpannableStringBuilder(value);
		// 正则表达式查找所有的相匹配的子串
		Pattern p = Pattern.compile(match);
		Matcher m = p.matcher(value);
		indexMap.clear();
		while (m.find()) {
			indexMap.put(m.start(), m.end());
		}
		for (Map.Entry<Integer, Integer> entry : indexMap.entrySet()) {
			highLightTxt.setSpan(new ForegroundColorSpan(Color.RED), entry.getKey(), entry.getValue(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		vh.tvSearchMatch.setText(highLightTxt);
	}

	/**
	 * Holder
	 */
	private static class ViewHolder {
		// LinearLayout ll_item;
		// CircularImageView civ_icon;
		// TextView tv_name;
		// ImageView iv_add;
		// ImageView iv_chat;
		CircularImageView ivSearchAvatar, ivDoubt;
		ImageView ivSearchAdd;
		TextView tvEnjoy;
		TextView tvSearchName, tvSearchSex, tvSearchOccupation, tvSearchAge, tvSearchWorth, tvSearchTagRealName, tvSearchMatch;
	}

	private OnClickListener mOnCListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag(Constants.ITEM_POSITION);
			SearchResult searchResult = data.get(position);
			Intent intent;
			switch (v.getId()) {
			case R.id.iv_search_avatar:// 头像
				intent = new Intent(context, NearCardDetailActivity_.class);
				intent.putExtra(Constants.DW_ID, searchResult.dwID);
				context.startActivity(intent);
				break;
			case R.id.iv_search_add:// 添加
				fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
				AddFriendDialog dialog = new AddFriendDialog();
				String searchType = getTag(Constants.SEARCH_TYPE);
				if ("0".equals(searchType)) {
					dialog.setNeedMoney(false);
				} else if ("1".equals(searchType)) {
					dialog.setNeedMoney(false);
				} else if ("3".equals(searchType)) {
					dialog.setNeedMoney(true);
				}
				dialog.setOnListener(new AddFriendListenerImp(searchResult));
				dialog.show(fragmentManager, "dialog");
				break;
			case R.id.tv_enjoy:
				recommendedID = searchResult.getDwID();
				if (FileUtils.getPromptStatus(context, Constants.RECOMMEND) == CommomPromptDialogFragment.RECOMMEND) {
					isRecommended(recommendedID);
					return;
				}
				showCommomDialog(CommomPromptDialogFragment.RECOMMEND, Constants.RECOMMEND_PROMPT);
				break;
			}
		}
	};

	// class ItemClick implements OnClickListener
	// {
	// private ViewHolder holder;
	// private SearchResult result;
	//
	// public ItemClick(ViewHolder holder, SearchResult result)
	// {
	// this.holder = holder;
	// this.result = result;
	// }
	//
	// @Override
	// public void onClick(View v)
	// {
	// Intent intent;
	// }
	// }

	/**
	 * 添加好友监听
	 */
	class AddFriendListenerImp implements AddFriendListener {
		private SearchResult searchResult;

		public AddFriendListenerImp(SearchResult searchResult) {
			this.searchResult = searchResult;
		}

		@Override
		public void withReason(String reason) {
			ContactEngine.getInstance().add(searchResult, reason, getTag(Constants.SEARCH_TYPE), new AddCallback() {
				public void onSuccess(String info) {
					Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
					Activity activity = (Activity) context;
					activity.finish();
				}

				@Override
				public void onFailure(String cause) {
					Toast.makeText(context, cause, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	private HashMap<String, String> map = new HashMap<String, String>();

	public String getTag(String key) {
		return map.get(key);
	}

	public void setTag(String key, String value) {
		map.put(key, value);
	}

	private Handler mIsRecommendedHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			recommendByID();
		};
	};

	private void isRecommended(String dwID) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Constants.DW_ID, dwID);
		mSendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_IS_RECOMMENDED, Method.POST,
				new HttpCallBack() {

					@Override
					public void onSuccess(String resultJSON, ResultBean resultBean) {
						LogUtils.d(TAG, "isRecommended " + resultJSON);
						if (2222 == resultBean.getResultCode()) {
							mIsRecommendedHandler.sendEmptyMessage(0);
						} else {
							showToast(resultBean.getMsg());
						}
					}

					@Override
					public void onFailure(String e) {
						showToast(Constants.NET_WRONG);
					}
				});
	}

	private void showToast(final String data) {
		((Activity) context).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ToastUtil.showToast(data);
			}
		});
	}

	private Handler recommendByIDHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ToastUtil.showToast("推荐成功");
			try {
				JSONObject object = new JSONObject(msg.obj.toString());
				String wealth = object.getString("wealth");
				SelfInfoManager.getInstance().notifyWealthChanged(Float.valueOf(wealth));
			} catch (JSONException e) {
				LogUtils.d(TAG, "error " + e.getLocalizedMessage());
			}
		};
	};

	private void recommendByID() {
		InputNumberDialog dialog = new InputNumberDialog();
		dialog.setTitle("请输入推荐金额");
		dialog.setHint("请输入金额(不小于10大洋)");
		dialog.setMinNum(10);
		dialog.setListener(new InputNumberListener() {
			@Override
			public void confirm(int money) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(Constants.DW_ID, recommendedID);
				map.put("recommenderID", DecentWorldApp.getInstance().getDwID());
				map.put("amount", "" + money);
				mSendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_RECOMMENDED_BY_ID, Method.POST,
						new HttpCallBack() {

							@Override
							public void onSuccess(String resultJSON, ResultBean resultBean) {
								LogUtils.d(TAG, "recommendByID " + resultJSON);
								if (2222 == resultBean.getResultCode()) {
									Message message = recommendByIDHandler.obtainMessage();
									message.obj = resultBean.getData();
									recommendByIDHandler.sendMessage(message);
								} else {
									showToast(resultBean.getMsg());
								}
							}

							@Override
							public void onFailure(String e) {
								showToast(Constants.NET_WRONG);
							}
						});
			}
		});
		dialog.show(((BaseFragmentActivity) context).getSupportFragmentManager(), "publish");
	}

	private CommomPromptDialogFragment mCommomPromptDialog;

	private void showCommomDialog(int enter, String prompt) {
		if (null == mCommomPromptDialog) {
			mCommomPromptDialog = new CommomPromptDialogFragment();
		}
		mCommomPromptDialog.setEnter(enter);
		mCommomPromptDialog.setOnCommomPromptListener(this);
		mCommomPromptDialog.setTips(prompt);
		mCommomPromptDialog.show(((BaseFragmentActivity) context).getSupportFragmentManager(), "mCommomPromptDialog");
	}

	@Override
	public void onCommomPromtClick(View view) {
		isRecommended(recommendedID);
	}
}
