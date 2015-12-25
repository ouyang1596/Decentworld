/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.MagnateToOtherAdapter;
import cn.sx.decentworld.bean.MyProtege;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.bean.manager.UserInfoManager;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.network.request.SetUserInfo;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.ImageLoaderHelper;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.ToastUtils;
import cn.sx.decentworld.widget.CircularImage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: MagnateWindowActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年10月27日 上午10:40:16
 */
@EActivity(R.layout.activity_magnate_window)
public class MagnateWindowActivity extends BaseFragmentActivity {
	private static final String TAG = "";
	private String dwID;
	private static final int ADD_MY_MAGNATE = 1;

	private FragmentManager fm;
	private MagnateToOtherAdapter magnateToOtherAdapter;
	private List<MyProtege> mDatas;

	@Bean
	TitleBar titleBar;

	@Bean
	SetUserInfo setUserInfo;
	@Bean
	GetUserInfo getUserInfo;

	/**
	 * 添加贵人
	 */
	@ViewById(R.id.btn_magnate_window_to_me_add)
	Button btn_magnate_window_to_me_add;

	/**
	 * 修改贵人
	 */
	@ViewById(R.id.btn_magnate_window_to_me_modify)
	Button btn_magnate_window_to_me_modify;

	/**
	 * 删除贵人
	 */
	@ViewById(R.id.btn_magnate_window_to_me_delete)
	Button btn_magnate_window_to_me_delete;

	/**
	 * 显示贵人ID
	 */
	@ViewById(R.id.tv_magnate_to_me_name)
	TextView tv_magnate_to_me_name;

	/**
	 * 总收益
	 */
	@ViewById(R.id.tv_magnate_to_other_total_revenue)
	TextView tv_magnate_to_other_total_revenue;

	@ViewById(R.id.lv_magnate_to_other_names)
	ListView lv_magnate_to_other_names;

	@ViewById(R.id.iv_magnate_to_me_avatar)
	CircularImage iv_magnate_to_me_avatar;

	@AfterViews
	void init() {
		fm = getSupportFragmentManager();
		titleBar.setTitleBar("返回", "贵人窗口");
		dwID = DecentWorldApp.getInstance().getDwID();
		// 初始化我的贵人
		String grID = UserInfoManager.getUserInfoInstance().getGrId();
		String icon = ImageUtils.getIconByDwID(grID, ImageUtils.ICON_SMALL);
		if (CommUtil.isNotBlank(grID) && CommUtil.isNotBlank(icon)) {
			tv_magnate_to_me_name.setText(grID);
			// Picasso.with(MagnateWindowActivity.this).load(icon)
			// .resize(120, 120).into(iv_magnate_to_me_avatar);
			ImageLoaderHelper.mImageLoader.displayImage(icon,
					iv_magnate_to_me_avatar, ImageLoaderHelper.mOptions);
		}

		// 从网络获取数据
		getUserInfo.getMyProteges(dwID, handler, GET_MY_MAGNATE);
		getUserInfo.getGrAllBenefit(dwID, handler, GET_GR_ALL_BENEFIT);
	}

	public static final int GET_MY_MAGNATE = 1;// 我是谁的贵人
	public static final int GET_GR_ALL_BENEFIT = 2;// 获得作为比人的贵人的总收益
	public static final int ADD_GR = 3;// 添加贵人
	public static final int MODIFY_GR = 4;// 修改贵人
	public static final int DELETE_GR = 5;// 删除贵人
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_MY_MAGNATE:
				// 解析Json数据
				String json_result = (String) msg.obj;
				mDatas = (List<MyProtege>) JsonUtils.json2BeanArray(
						json_result, MyProtege.class);
				// 设置到界面
				magnateToOtherAdapter = new MagnateToOtherAdapter(
						MagnateWindowActivity.this, mDatas, fm);
				lv_magnate_to_other_names.setAdapter(magnateToOtherAdapter);
				// 清理数据库
				MyProtege.deleteAll();
				// 保存到数据库
				for (MyProtege myProtege : mDatas) {
					myProtege.save();
				}
				LogUtils.i(TAG, "获取我是谁的贵人的列表完成,我是 " + mDatas.size() + " 个人的贵人");
				break;
			case GET_GR_ALL_BENEFIT:
				String json_all_benefit = (String) msg.obj;
				JSONObject benefitObject = (JSONObject) JSON
						.parse(json_all_benefit);
				tv_magnate_to_other_total_revenue.setText(benefitObject
						.getString("totalBenefit"));
				LogUtils.i(
						TAG,
						"我作为比人的贵人获取的总收益为："
								+ benefitObject.getString("totalBenefit"));
				break;
			case ADD_GR: {
				String grID = msg.obj.toString();
				setAndModifyGr(grID);
				ToastUtils.toast(MagnateWindowActivity.this, "添加贵人成功");
			}
				break;
			case MODIFY_GR: {
				String grID = msg.obj.toString();
				setAndModifyGr(grID);
				ToastUtils.toast(MagnateWindowActivity.this, "修改贵人成功");
			}
				break;
			case DELETE_GR: {
				String grID = msg.obj.toString();
				setAndModifyGr(grID);
				ToastUtils.toast(MagnateWindowActivity.this, "删除贵人成功");
			}
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 
	 * @param grID
	 */
	private void setAndModifyGr(String grID) {
		String icon = ImageUtils.getIconByDwID(grID, ImageUtils.ICON_SMALL);
		if (CommUtil.isNotBlank(grID) && CommUtil.isNotBlank(icon)) {
			tv_magnate_to_me_name.setText(grID);
			// Picasso.with(MagnateWindowActivity.this).load(icon)
			// .resize(120, 120).into(iv_magnate_to_me_avatar);
			ImageLoaderHelper.mImageLoader.displayImage(icon,
					iv_magnate_to_me_avatar, ImageLoaderHelper.mOptions);
		} else {
			tv_magnate_to_me_name.setText("");
			// Picasso.with(MagnateWindowActivity.this)
			// .load(R.drawable.default_avatar).resize(120, 120)
			// .into(iv_magnate_to_me_avatar);
			iv_magnate_to_me_avatar.setImageResource(R.drawable.default_avatar);
		}

		UserInfo info = UserInfo.queryByDwID(dwID);
		if (info != null) {
			info.setGrId(grID);
			info.save();
		}
		UserInfoManager.getUserInfoInstance().setGrId(grID);
	}

	/**
	 * 返回
	 */
	@Click(R.id.main_header_left)
	void back() {
		finish();
	}

	/**
	 * 添加贵人
	 */
	@Click(R.id.btn_magnate_window_to_me_add)
	void addMagnate() {
		Intent intent = new Intent(MagnateWindowActivity.this,
				PickContactActivity_.class);
		intent.putExtra(PickContactActivity.PICK_TYPE,
				PickContactActivity.TYPE_SINGLE_PICK);
		startActivityForResult(intent, ADD_GR);
	}

	/**
	 * 修改贵人
	 */
	@Click(R.id.btn_magnate_window_to_me_modify)
	void modifyMagnate() {
		Intent intent = new Intent(MagnateWindowActivity.this,
				PickContactActivity_.class);
		intent.putExtra(PickContactActivity.PICK_TYPE,
				PickContactActivity.TYPE_SINGLE_PICK);
		startActivityForResult(intent, MODIFY_GR);
	}

	/**
	 * 删除贵人
	 */
	@Click(R.id.btn_magnate_window_to_me_delete)
	void deleteMagnate() {
		setUserInfo.setUserGr(dwID, "", handler, DELETE_GR);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == ADD_GR) {
				String grID = data.getStringExtra("dwID");
				LogUtils.i(TAG, "设置或修改贵人返回了结果,grID=" + grID);
				if (CommUtil.isNotBlank(grID)) {
					// 将信息上传到服务器（设置和修改）
					setUserInfo.setUserGr(dwID, grID, handler, ADD_GR);
				}
			} else if (requestCode == MODIFY_GR) {
				String grID = data.getStringExtra("dwID");
				LogUtils.i(TAG, "设置或修改贵人返回了结果,grID=" + grID);
				if (CommUtil.isNotBlank(grID)) {
					// 将信息上传到服务器（设置和修改）
					setUserInfo.setUserGr(dwID, grID, handler, MODIFY_GR);
				}
			}
		}
	}

}
