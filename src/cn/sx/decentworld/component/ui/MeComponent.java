/**
 * 
 */
package cn.sx.decentworld.component.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.MeSettingActivity_;
import cn.sx.decentworld.activity.RechargeAliPayWXActivity_;
import cn.sx.decentworld.bean.DisplayAuthority;
import cn.sx.decentworld.bean.EditUserInfoItem;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.bean.manager.UserInfoManager;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.fragment.ChatFragment;
import cn.sx.decentworld.fragment.WorkCircleFragment;
import cn.sx.decentworld.fragment.WorkCircleFragment_;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.widget.CustomScrollView;
import cn.sx.decentworld.widget.CustomScrollView.OnScrollListener;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: MeComponent.java
 * @Description:
 * @author: dyq
 * @date: 2015年8月13日 下午5:21:15
 */

@EBean
public class MeComponent implements OnClickListener {
    private static final String TAG = "MeComponent";
    @RootContext
    Context context;
    @RootContext
    Activity activity;
    @Bean
    ToastComponent toast;
    @Bean
    GetUserInfo getUserInfo;
    private Boolean isFit = false;// 我的设置界面是否已经适配
    private FragmentManager fragmentManager;

    // 根布局
    @ViewById(R.id.rl_chat_me_root)
    RelativeLayout rl_chat_me_root;
    @ViewById(R.id.rel_me_title)
    RelativeLayout relMeTitle;

    // 设置
    @ViewById(R.id.rl_chat_me_setting)
    RelativeLayout ll_chat_me_setting;
    // 充值
    @ViewById(R.id.rl_chat_me_recharge)
    RelativeLayout ll_chat_me_recharge;

    // 用户详细信息 Tab
    @ViewById(R.id.ll_chat_me_user_detail)
    LinearLayout ll_chat_me_user_detail;
    // 用户作品圈 Tab
    @ViewById(R.id.ll_chat_me_work_circle)
    LinearLayout ll_chat_me_work_circle;
    // Tab切换图标
    // @ViewById(R.id.iv_chat_me_tab)
    // ImageView iv_chat_me_tab;

    // 编辑信息
    @ViewById(R.id.tv_chat_me_edit)
    TextView tv_chat_me_edit;

    @ViewById(R.id.sv_chat_me)
    CustomScrollView sv_chat_me;

    @ViewById(R.id.tv_chat_me_introduce)
    TextView tv_chat_me_introduce;

    private String dwID;

    @Bean
    ChoceAndTakePictureComponent choceAndTakePictureComponent;

    @AfterViews
    void init() {
        dwID = DecentWorldApp.getInstance().getDwID();
        sv_chat_me.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScroll(int scrollY) {
                LogUtils.e("bm", "y----" + scrollY);
                if (scrollY >= 100) {
                    relMeTitle.setVisibility(View.GONE);
                } else {
                    relMeTitle.setVisibility(View.VISIBLE);
                }
            }
        });
        ll_chat_me_setting.setOnClickListener(this);
        ll_chat_me_recharge.setOnClickListener(this);
        ll_chat_me_user_detail.setOnClickListener(this);
        ll_chat_me_work_circle.setOnClickListener(this);
    }

    /**
     * 设置FragmentManager
     * 
     * @param fragmentManager
     */
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void initFindScrollView() {
        // sv_chat_me.getLoadingLayoutProxy(false,
        // true).setLastUpdatedLabel("上拉加载");
        // sv_chat_me.getLoadingLayoutProxy(false, true).setPullLabel("");
        // sv_chat_me.getLoadingLayoutProxy(false,
        // true).setRefreshingLabel("正在加载...");
        // sv_chat_me.getLoadingLayoutProxy(false,
        // true).setReleaseLabel("放开以加载");
        // sv_chat_me.setMode(Mode.PULL_UP_TO_REFRESH);
        // sv_chat_me.setScrollingWhileRefreshingEnabled(!sv_chat_me.isScrollingWhileRefreshingEnabled());

        // sv_chat_me.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
        // @Override
        // public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
        //
        // if (refreshView.isRefreshing()) {
        // // uu.clear();
        // // Flag = 0;
        // // new GetDataTask().execute();
        // } else {
        // // Flag = 1;
        // // new GetDataTask().execute();
        // }
        // refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最近上传");
        // new GetDataTask().execute();
        // }
        // });
        // mScrollView = grament_me_work_sv.getRefreshableView();
    }

    // private class GetDataTask extends AsyncTask<Void, Void, String[]> {
    //
    // @Override
    // protected String[] doInBackground(Void... params) {
    // // Simulates a background job.
    // try {
    // Thread.sleep(2000);
    // } catch (InterruptedException e) {
    // }
    // return null;
    // }
    //
    // @Override
    // protected void onPostExecute(String[] result) {
    // getUserInfo.getWealth(dwID, meHandler, GET_USER_WEALTH);
    // getUserInfo.getWorth(dwID, meHandler, GET_USER_WORTH);
    // sv_chat_me.onRefreshComplete();
    // LogUtils.i(TAG, "刷新完成");
    // super.onPostExecute(result);
    // }
    // }

    private static final int GET_USER_WORTH = 1;
    private static final int GET_USER_WEALTH = 2;
    Handler meHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            UserInfo info = UserInfo.queryByDwID(dwID);
            switch (msg.what) {
                case GET_USER_WORTH:
                    String worth = msg.obj.toString();
                    LogUtils.i(TAG, "获取身价成功，worth = " + worth);
                    ChatFragment.tv_chat_me_worth.setText(worth);
                    info.setWorth(worth);
                    info.save();
                    break;
                case GET_USER_WEALTH:
                    String wealth = msg.obj.toString();
                    LogUtils.i(TAG, "获取身家成功，wealth = " + wealth);
                    ChatFragment.tv_chat_me_wealth.setText(wealth);
                    info.setWealth(wealth);
                    info.save();
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_chat_me_setting:
                // 跳转到看自己的设置界面
                intent = new Intent(context, MeSettingActivity_.class);
                context.startActivity(intent);
                break;
            case R.id.rl_chat_me_recharge:
                // 跳转到充值界面
                intent = new Intent(context, RechargeAliPayWXActivity_.class);
                context.startActivity(intent);
                break;
            case R.id.ll_chat_me_user_detail:
            // 跳转到用户详细资料界面
            {
                tv_chat_me_introduce.setVisibility(View.GONE);
                tv_chat_me_edit.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ll_chat_me_container, ChatFragment.userDetailFragment);
                fragmentTransaction.commit();
                ll_chat_me_user_detail.setClickable(false);
                tv_chat_me_edit.setVisibility(View.VISIBLE);
            }
                break;
            case R.id.ll_chat_me_work_circle:
            // 跳转到用户作品圈界面
            {
                tv_chat_me_introduce.setVisibility(View.VISIBLE);
                tv_chat_me_edit.setVisibility(View.GONE);

                WorkCircleFragment workCircleFragment = new WorkCircleFragment_();
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ll_chat_me_container, workCircleFragment);
                fragmentTransaction.commit();

                ll_chat_me_user_detail.setClickable(true);
                tv_chat_me_edit.setVisibility(View.INVISIBLE);
            }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化个人详细信息，从数据库拿取
     */
    public List<EditUserInfoItem> initUserInfoDatas() {

        List<EditUserInfoItem> userInfoDatas = new ArrayList<EditUserInfoItem>();
        //清除UserInfoDatas里面的数据库中的值
        userInfoDatas.clear();
        EditUserInfoItem infoItem;
        //从数据库中获取用户的信息
        UserInfo userInfo = UserInfoManager.getUserInfoInstance();// 用户信息
        //数据库中的获取用户的权限
        DisplayAuthority displayAuthority = DisplayAuthority.queryByDwID(dwID);// 用户信息的权限

        if (userInfo != null && displayAuthority != null) {
            infoItem = new EditUserInfoItem(displayAuthority.getShowSign(), "sign", "个性签名", "", userInfo.getSign());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowRealName(), "realName", "基本信息", "实名",
                    userInfo.getRealName());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowNickName(), "nickname", "基本信息", "昵称",
                    userInfo.getNickName());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowAge(), "age", "基本信息", "年龄", userInfo.getAge() + "");
            userInfoDatas.add(infoItem);
            if (userInfo.getSex() == 0) {
                infoItem = new EditUserInfoItem(displayAuthority.getShowSex(), "sex", "基本信息", "性别", "女");
                userInfoDatas.add(infoItem);
            } else if (userInfo.getSex() == 1) {
                infoItem = new EditUserInfoItem(displayAuthority.getShowSex(), "sex", "基本信息", "性别", "男");
                userInfoDatas.add(infoItem);
            }

            infoItem = new EditUserInfoItem(displayAuthority.getShowPosition(), "position", "基本信息", "位置",
                    userInfo.getPosition());
            userInfoDatas.add(infoItem);
            if (userInfo.getType() == 0) {
                infoItem = new EditUserInfoItem(displayAuthority.getShowType(), "type", "基本信息", "进入类型", "才");
                userInfoDatas.add(infoItem);
            } else if (userInfo.getType() == 1) {
                infoItem = new EditUserInfoItem(displayAuthority.getShowType(), "type", "基本信息", "进入类型", "财");
                userInfoDatas.add(infoItem);
            } else if (userInfo.getType() == 2) {
                infoItem = new EditUserInfoItem(displayAuthority.getShowType(), "type", "基本信息", "进入类型", "貌");
                userInfoDatas.add(infoItem);
            }
            infoItem = new EditUserInfoItem(displayAuthority.getShowWealth(), "wealth", "基本信息", "身家",
                    userInfo.getWealth());
            userInfoDatas.add(infoItem);

            infoItem = new EditUserInfoItem(false, "dwID", "基本信息", "DW号", userInfo.getDwID());
            userInfoDatas.add(infoItem);

            if (userInfo.getIfStudent().equals("0")) {
                infoItem = new EditUserInfoItem(displayAuthority.getShowOccupation(), "occupation", "基本信息", "行业",
                        userInfo.getOccupation());
                userInfoDatas.add(infoItem);
                infoItem = new EditUserInfoItem(displayAuthority.getShowCorporation(), "corporation", "基本信息", "公司",
                        userInfo.getCorporation());
                userInfoDatas.add(infoItem);
                infoItem = new EditUserInfoItem(displayAuthority.getShowjob(), "job", "基本信息", "职位", userInfo.getJob());
                userInfoDatas.add(infoItem);
                infoItem = new EditUserInfoItem(displayAuthority.getShowSchool(), "school", "我的历史", "毕业学校",
                        userInfo.getSchool());
                userInfoDatas.add(infoItem);
                infoItem = new EditUserInfoItem(displayAuthority.getShowDepartment(), "department", "我的历史", "毕业院系",
                        userInfo.getDepartment());
                userInfoDatas.add(infoItem);
            } else if (userInfo.getIfStudent().equals("1")) {
                infoItem = new EditUserInfoItem(displayAuthority.getShowSchool(), "school", "基本信息", "学校",
                        userInfo.getSchool());
                userInfoDatas.add(infoItem);
                infoItem = new EditUserInfoItem(displayAuthority.getShowDepartment(), "department", "基本信息", "院系",
                        userInfo.getDepartment());
                userInfoDatas.add(infoItem);
                infoItem = new EditUserInfoItem(displayAuthority.getShowClasses(), "classes", "基本信息", "班级",
                        userInfo.getClasses());
                userInfoDatas.add(infoItem);

                infoItem = new EditUserInfoItem(displayAuthority.getShowOccupation(), "occupation", "基本信息", "行业",
                        userInfo.getOccupation());
                userInfoDatas.add(infoItem);
                infoItem = new EditUserInfoItem(displayAuthority.getShowCorporation(), "corporation", "基本信息", "公司",
                        userInfo.getCorporation());
                userInfoDatas.add(infoItem);
                infoItem = new EditUserInfoItem(displayAuthority.getShowjob(), "job", "基本信息", "职位", userInfo.getJob());
                userInfoDatas.add(infoItem);
            }

            // Map<String,String[]> map = new HashMap<String,String[]>();
            // map.put("hometown", new String[]{"我的历史","故乡"});
            // map.put("nation", new String[]{"我的历史","民族"});
            //
            // for(Map.Entry<String, String[]> entry:map.entrySet())
            // {
            // String key = entry.getKey();
            // String[] value = entry.getValue();
            //
            // try
            // {
            // Field dis = displayAuthority.getClass().getDeclaredField(key);
            // boolean result1 = (Boolean) dis.get(displayAuthority);
            //
            // Field info = userInfo.getClass().getDeclaredField(key);
            // String result2 = (String) info.get(userInfo);
            // infoItem = new EditUserInfoItem(result1,key, value[0],
            // value[1],result2);
            // userInfoDatas.add(infoItem);
            // }
            // catch (Exception e)
            // {
            // LogUtils.i(TAG, "数据转换异常"+e.toString());
            // }
            // }

            infoItem = new EditUserInfoItem(displayAuthority.getShowHometown(), "hometown", "我的历史", "故乡",
                    userInfo.getHometown());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowNation(), "nation", "我的历史", "民族",
                    userInfo.getNation());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowPhoneNum(), "phoneNum", "我的历史", "电话",
                    userInfo.getPhoneNum());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowEmail(), "email", "我的历史", "邮箱", userInfo.getEmail());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowQQ(), "qq", "我的历史", "QQ", userInfo.getQq());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowWechat(), "wechat", "我的历史", "微信",
                    userInfo.getWechat());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowBlog(), "blog", "我的历史", "微博", userInfo.getBlog());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowVehicle(), "vehicle", "我的历史", "车子",
                    userInfo.getVehicle());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowBloodType(), "bloodType", "我的历史", "血型",
                    userInfo.getBloodType());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowConstellatory(), "constellatory", "我的历史", "星座",
                    userInfo.getConstellatory());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowMaritalStatus(), "maritalStatus", "我的历史", "婚否",
                    userInfo.getMaritalStatus());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowReligion(), "religion", "我的历史", "宗教",
                    userInfo.getReligion());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowSpeciality(), "speciality", "我的历史", "特长",
                    userInfo.getSpeciality());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowIdol(), "idol", "我的历史", "偶像", userInfo.getIdol());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowMotto(), "motto", "我的历史", "座右铭",
                    userInfo.getMotto());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowFootprint(), "footprint", "我的历史", "足迹",
                    userInfo.getFootprint());
            userInfoDatas.add(infoItem);

            infoItem = new EditUserInfoItem(displayAuthority.getShowLikebooks(), "likebooks", "我的爱好", "喜欢的书",
                    userInfo.getLikebooks());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowLikemusic(), "likemusic", "我的爱好", "喜欢的音乐",
                    userInfo.getLikemusic());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowLikesmovies(), "likesmovies", "我的爱好", "喜欢的电影",
                    userInfo.getLikesmovies());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowLikesport(), "likesport", "我的爱好", "喜欢的运动",
                    userInfo.getLikesport());
            userInfoDatas.add(infoItem);
            infoItem = new EditUserInfoItem(displayAuthority.getShowCate(), "cate", "我的爱好", "美食", userInfo.getCate());
            userInfoDatas.add(infoItem);
        }
        return userInfoDatas;
    }

    /**
     * 获取个人的四张图片网络路径，并保存到数据库中
     * 
     * @param dwID
     */
    public void getIconPath(String dwID) {
        LogUtils.i(TAG, "getIconPath...从服务器获取用户的四张图片");
        String iconSmall = ImageUtils.getIconByDwID(dwID, ImageUtils.ICON_SMALL);
        String iconMain = ImageUtils.getIconByDwID(dwID, ImageUtils.ICON_MAIN);
        String iconExtra1 = ImageUtils.getIconByDwID(dwID, ImageUtils.ICON_EXTRA_1);
        String iconExtra2 = ImageUtils.getIconByDwID(dwID, ImageUtils.ICON_EXTRA_2);
        LogUtils.i(TAG, "iconSmall = " + iconSmall + ",iconMain=" + iconMain + ",iconExtra1=" + iconExtra1
                + ",iconExtra2=" + iconExtra2);
        // 重新获取四张图片的地址并保存在数据库中
        // toast.show("id="+dwID);
        UserInfo userInfo = UserInfo.queryByDwID(dwID);
        if (null == userInfo) {
            return;
        }
        userInfo.setIconSmall(iconSmall);
        userInfo.setIcon(iconMain);
        userInfo.setIcon2(iconExtra1);
        userInfo.setIcon3(iconExtra2);
        userInfo.save();
        // 将内存中的数据进行更新
        UserInfo info = UserInfoManager.getUserInfoInstance();
        info.setIconSmall(iconSmall);
        info.setIcon(iconMain);
        info.setIcon2(iconExtra1);
        info.setIcon3(iconExtra2);
    }
}
