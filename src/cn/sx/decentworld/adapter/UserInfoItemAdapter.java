/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.EditClassesActivity_;
import cn.sx.decentworld.activity.EditCommon1Activity_;
import cn.sx.decentworld.activity.EditCommon2Activity_;
import cn.sx.decentworld.activity.EditCommon3Activity_;
import cn.sx.decentworld.activity.EditCorporationActivity_;
import cn.sx.decentworld.activity.EditDepartmentActivity_;
import cn.sx.decentworld.activity.EditIsMarriedActivity_;
import cn.sx.decentworld.activity.EditJobActivity_;
import cn.sx.decentworld.activity.EditNicknameActivity_;
import cn.sx.decentworld.activity.EditOccupationActivity_;
import cn.sx.decentworld.activity.EditRealnameActivity_;
import cn.sx.decentworld.activity.EditSchoolActivity_;
import cn.sx.decentworld.activity.EditSignActivity_;
import cn.sx.decentworld.activity.EditUserInfoActivity;
import cn.sx.decentworld.bean.EditUserInfoItem;
import cn.sx.decentworld.logSystem.LogUtils;

/**
 * @ClassName: UserInfoItemAdapter.java
 * @Description:
 * @author: cj
 * @date: 2015年10月11日 上午10:19:55
 */
public class UserInfoItemAdapter extends BaseAdapter {
	private static final String TAG = "UserInfoItemAdapter";
	private Context context;
	private LayoutInflater inflater;
	private List<EditUserInfoItem> mDatas;
	private FragmentManager fm;

	private boolean isEdit = false;
	private String[] indexs;

	public UserInfoItemAdapter(Context context, List<EditUserInfoItem> mDatas) {
		this.context = context;
		this.mDatas = mDatas;
		inflater = LayoutInflater.from(context);
	}

	public UserInfoItemAdapter(Context context, List<EditUserInfoItem> mDatas, boolean isEdit) {
		this.context = context;
		this.mDatas = mDatas;
		inflater = LayoutInflater.from(context);
		this.isEdit = isEdit;
	}

	@Override
	public int getCount() {
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.activity_edit_user_info_item, null);
			holder.llRoot = (LinearLayout) convertView.findViewById(R.id.ll_edit_user_info_item_root);
			holder.ll_edit_user_info_item_title_child = (LinearLayout) convertView
					.findViewById(R.id.ll_edit_user_info_item_title_child);
			holder.ll_title = (LinearLayout) convertView.findViewById(R.id.ll_edit_user_info_item_title);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_edit_user_info_item_title);
			holder.rl_content = (RelativeLayout) convertView.findViewById(R.id.rl_edit_user_info_item_content);
			holder.iv_switch = (ImageView) convertView.findViewById(R.id.iv_edit_user_info_item_switch);
			holder.tv_key = (TextView) convertView.findViewById(R.id.tv_edit_user_info_item_key);
			holder.tv_value = (TextView) convertView.findViewById(R.id.tv_edit_user_info_item_value);
			holder.iv_more = (ImageView) convertView.findViewById(R.id.iv_edit_user_info_item_more);
			holder.tv_value1 = (TextView) convertView.findViewById(R.id.tv_edit_user_info_item_value1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final EditUserInfoItem infoItem = mDatas.get(position);
		if (infoItem.getIndex().equals("realName")) {
			DecentWorldApp.realName = infoItem.getValue();
		}

		if (position == 0 && mDatas.get(position).getIndex().equals("sign")) {
			holder.ll_edit_user_info_item_title_child.setVisibility(View.VISIBLE);
			holder.tv_title.setVisibility(View.VISIBLE);
			holder.tv_title.setText(infoItem.getTitle());
			holder.tv_key.setVisibility(View.GONE);
			holder.tv_value.setVisibility(View.GONE);
			holder.tv_value1.setVisibility(View.VISIBLE);
			holder.tv_value1.setText(infoItem.getValue());
		} else {
			holder.tv_value1.setVisibility(View.GONE);
			if (position == 0) {
				if (infoItem.getKey().equals("")) {
					holder.tv_title.setVisibility(View.GONE);
				} else {
					holder.ll_edit_user_info_item_title_child.setVisibility(View.VISIBLE);
					holder.tv_title.setVisibility(View.VISIBLE);
					holder.tv_title.setText(infoItem.getTitle());
				}
				holder.tv_key.setText(infoItem.getKey());
				holder.tv_value.setText(infoItem.getValue());
			} else {
				String title1 = mDatas.get(position - 1).getTitle();
				String title2 = mDatas.get(position).getTitle();
				if (title1.equals(title2)) {
					holder.tv_title.setVisibility(View.GONE);
					holder.ll_edit_user_info_item_title_child.setVisibility(View.GONE);
				} else {
					holder.ll_edit_user_info_item_title_child.setVisibility(View.VISIBLE);
					holder.tv_title.setVisibility(View.VISIBLE);
					holder.tv_title.setText(infoItem.getTitle());
				}
				holder.tv_key.setVisibility(View.VISIBLE);
				holder.tv_value.setVisibility(View.VISIBLE);
				holder.tv_key.setText(infoItem.getKey());
				if (mDatas.get(position).getIndex().equals("age")) {
					if (infoItem.getValue().equals("-1")) {
						holder.tv_value.setText(context.getResources().getString(R.string.default_age_description));
					} else {
						holder.tv_value.setText(infoItem.getValue());
					}
				} else {
					holder.tv_value.setText(infoItem.getValue());
				}
			}
		}

		// 如果是编辑页面，则出现more图标，Item可以点击
		if (isEdit) {
			if ((position == 4) || (position == 5) || (position == 6) || (position == 7) || (position == 8)) {
				holder.iv_more.setVisibility(View.INVISIBLE);
			} else {
				holder.iv_more.setVisibility(View.VISIBLE);
				holder.rl_content.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (position == 0) {
							LogUtils.i(TAG, "position=" + position + ",getTitle=" + mDatas.get(position).getTitle());
							Intent intent = new Intent(context, EditSignActivity_.class);
							intent.putExtra("position", position);
							intent.putExtra("oldData", mDatas.get(position).getValue());
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_SIGN);
						} else if (mDatas.get(position).getIndex().equals("realName")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditRealnameActivity_.class);
							intent.putExtra("position", position);
							intent.putExtra("oldData", mDatas.get(position).getValue());
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_REALNAME);
						} else if (mDatas.get(position).getIndex().equals("nickName")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditNicknameActivity_.class);
							intent.putExtra("position", position);
							intent.putExtra("oldData", mDatas.get(position).getValue());
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_NICKNAME);
						} else if (mDatas.get(position).getIndex().equals("school")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditSchoolActivity_.class);
							intent.putExtra("oldData", mDatas.get(position).getValue());
							intent.putExtra("position", position);
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_SCHOOL);
						} else if (mDatas.get(position).getIndex().equals("department")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditDepartmentActivity_.class);
							intent.putExtra("oldDepartment", mDatas.get(position).getValue());
							intent.putExtra("position", position);
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_DEPARTMENT);
						} else if (mDatas.get(position).getIndex().equals("classes")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditClassesActivity_.class);
							intent.putExtra("oldClasses", mDatas.get(position).getValue());
							intent.putExtra("position", position);
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_CLASSES);
						} else if (mDatas.get(position).getIndex().equals("occupation")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditOccupationActivity_.class);
							intent.putExtra("oldOccupation", mDatas.get(position).getValue());
							intent.putExtra("position", position);
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_OCCUPATION);
						} else if (mDatas.get(position).getIndex().equals("corporation")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditCorporationActivity_.class);
							intent.putExtra("oldCorporation", mDatas.get(position).getValue());
							intent.putExtra("position", position);
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_CORPORATION);
						} else if (mDatas.get(position).getIndex().equals("job")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditJobActivity_.class);
							intent.putExtra("oldJob", mDatas.get(position).getValue());
							intent.putExtra("position", position);
							((FragmentActivity) context)
									.startActivityForResult(intent, EditUserInfoActivity.REQUESTCODE_EDIT_JOB);
						}
						// 既可以自定义，也可以选择
						else if (mDatas.get(position).getIndex().equals("religion")
								|| mDatas.get(position).getIndex().equals("speciality")
								|| mDatas.get(position).getIndex().equals("idol")
								|| mDatas.get(position).getIndex().equals("footprint")
								|| mDatas.get(position).getIndex().equals("likebooks")
								|| mDatas.get(position).getIndex().equals("likemusic")
								|| mDatas.get(position).getIndex().equals("likemovie")
								|| mDatas.get(position).getIndex().equals("likesport")
								|| mDatas.get(position).getIndex().equals("cate")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditCommon1Activity_.class);
							intent.putExtra("position", position);
							intent.putExtra("title", mDatas.get(position).getKey());
							intent.putExtra("oldData", mDatas.get(position).getValue());
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_COMMON1);
						}
						// 只可以自定义
						else if (mDatas.get(position).getIndex().equals("hometown")
								|| mDatas.get(position).getIndex().equals("phoneNum")
								|| mDatas.get(position).getIndex().equals("email")
								|| mDatas.get(position).getIndex().equals("qq")
								|| mDatas.get(position).getIndex().equals("wechat")
								|| mDatas.get(position).getIndex().equals("blog")
								|| mDatas.get(position).getIndex().equals("vehicle")
								|| mDatas.get(position).getIndex().equals("motto")
								|| mDatas.get(position).getIndex().equals("age")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditCommon2Activity_.class);
							intent.putExtra("position", position);
							intent.putExtra("title", mDatas.get(position).getKey());
							intent.putExtra("oldData", mDatas.get(position).getValue());
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_COMMON2);
						}
						// 只可以选择
						else if (mDatas.get(position).getIndex().equals("nation")
								|| mDatas.get(position).getIndex().equals("bloodType")
								|| mDatas.get(position).getIndex().equals("constellatory")) {
							LogUtils.i(TAG, "position=" + position + ",getKey=" + mDatas.get(position).getKey());
							Intent intent = new Intent(context, EditCommon3Activity_.class);
							intent.putExtra("position", position);
							intent.putExtra("title", mDatas.get(position).getKey());
							intent.putExtra("oldData", mDatas.get(position).getValue());
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_COMMON3);
						} else if (mDatas.get(position).getIndex().equals("maritalStatus")) {
							Intent intent = new Intent(context, EditIsMarriedActivity_.class);
							intent.putExtra("position", position);
							intent.putExtra("title", mDatas.get(position).getKey());
							intent.putExtra("oldData", mDatas.get(position).getValue());
							((FragmentActivity) context).startActivityForResult(intent,
									EditUserInfoActivity.REQUESTCODE_EDIT_COMMON3);
						}
					}
				});
			}
			// 开关的显示与隐藏
			if (mDatas.get(position).getIndex().equals("userType") || mDatas.get(position).getIndex().equals("nickName")
					|| mDatas.get(position).getIndex().equals("userId")) {
				holder.iv_switch.setVisibility(View.INVISIBLE);
			} else {
				holder.iv_switch.setVisibility(View.VISIBLE);
				if (mDatas.get(position).isShow()) {
					holder.iv_switch.setImageResource(R.drawable.switch_rectangle_open);
				} else {
					holder.iv_switch.setImageResource(R.drawable.switch_rectangle_close);
				}
				holder.iv_switch.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						LogUtils.i(TAG, "点击了开关");
						ImageView iv = (ImageView) v;
						if (mDatas.get(position).isShow()) {
							iv.setImageResource(R.drawable.switch_rectangle_close);
							mDatas.get(position).setShow(false);
						} else {
							iv.setImageResource(R.drawable.switch_rectangle_open);
							mDatas.get(position).setShow(true);
						}
					}
				});

			}

		} else {
			// 如果是不是编辑页面，只是信息展示界面，则隐藏more图标，Item不可以点击
			holder.iv_more.setVisibility(View.GONE);
			holder.iv_switch.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		LinearLayout llRoot;
		LinearLayout ll_title;
		RelativeLayout rl_content;
		TextView tv_title;
		TextView tv_key;
		TextView tv_value;
		TextView tv_value1;
		ImageView iv_more;

		ImageView iv_switch;
		LinearLayout ll_edit_user_info_item_title_child;
	}

}
