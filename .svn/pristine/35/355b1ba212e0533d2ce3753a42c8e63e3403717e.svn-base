/**
 * 
 */
package cn.sx.decentworld.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.WorkAdapter;
import cn.sx.decentworld.bean.Comment;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.EditUserInfoItem;
import cn.sx.decentworld.bean.Praise;
import cn.sx.decentworld.bean.WorkBean;
import cn.sx.decentworld.common.Urls;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.component.ui.FriendDetailComponent;
import cn.sx.decentworld.fragment.UserDetailFragment;
import cn.sx.decentworld.fragment.UserDetailFragment_;
import cn.sx.decentworld.fragment.WorkCircleFragment;
import cn.sx.decentworld.fragment.WorkCircleFragment_;
import cn.sx.decentworld.utils.ActivityCollector;
import cn.sx.decentworld.utils.ViewUtil;
import cn.sx.decentworld.widget.CircularImage;
import cn.sx.decentworld.widget.HackyViewPager;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * @ClassName: FriendDetailActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年8月11日 下午2:57:37
 */
@EActivity(R.layout.activity_friend_info)
public class FriendDetailActivity extends BaseFragmentActivity implements OnClickListener {
    private static final String TAG = "FriendDetailActivity";
    
    @Bean
    ToastComponent toast;
    @Bean
    FriendDetailComponent friendDetailComponent;
    @ViewById(R.id.rl_friend_info_root)
    RelativeLayout rl_friend_info_root;
    
    public static String user_dwID;
    public static String user_nickname;
    public static int chatType;
    public static int chatRelationship;
    
    /**
     * 头像
     */
    @ViewById(R.id.vp_friend_info_pager)
    HackyViewPager vp_friend_info_pager;
    
    /**
     * 设置
     */
    @ViewById(R.id.rl_friend_info_setting)
    RelativeLayout rl_friend_info_setting;
    
    /**
     * 聊天
     */
    @ViewById(R.id.iv_friend_info_avatar)
    CircularImage iv_friend_info_avatar;
    
    // 个人简介
    @ViewById(R.id.ll_friend_info_detail)
    LinearLayout ll_friend_info_detail;
    // 作品圈
    @ViewById(R.id.ll_friend_info_work_circle)
    LinearLayout ll_friend_info_work_circle;
    @ViewById(R.id.iv_friend_info_tab)
    ImageView iv_friend_info_tab;
    
    public List<EditUserInfoItem> userInfoDatas;
    public static WorkAdapter adaper;
    public static ArrayList<WorkBean> uu;
    
    @ViewById(R.id.sl_friend_info_pullToRefresh)
    PullToRefreshScrollView sl_friend_info_pullToRefresh;
    
    public static ScrollView mScrollView;
    
    Handler scrollViewHandler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            mScrollView.scrollTo(0, 0);
        }
    };
    
    private UserDetailFragment userDetailFragment;
    private WorkCircleFragment workCircleFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    
    @ViewById(R.id.tv_friend_info_title)
    TextView tv_friend_info_title;
    
    @AfterViews
    public void init() {
        ActivityCollector.clear();
        ActivityCollector.addActivity(this);
        // 适配
        ViewUtil.scaleContentView(rl_friend_info_root);
        
        user_dwID = getIntent().getStringExtra("user_dwID");
        user_nickname = getIntent().getStringExtra("user_nickname");
        chatType = getIntent().getIntExtra("chatType", DWMessage.CHAT_TYPE_SINGLE);
        chatRelationship = getIntent().getIntExtra("chatRelationship", 0);
        
        tv_friend_info_title.setText(user_nickname);// 昵称
        // 设置朋友的头像
        
        rl_friend_info_setting.setOnClickListener(this);
        ll_friend_info_detail.setOnClickListener(this);
        ll_friend_info_work_circle.setOnClickListener(this);
        
        initUserInfoDatas();
        // 初始化个人信息Tab页面
        userDetailFragment = new UserDetailFragment_();
        userDetailFragment.setData(userInfoDatas);
        workCircleFragment = new WorkCircleFragment_();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ll_friend_info_container, userDetailFragment);
        fragmentTransaction.commit();
        ll_friend_info_detail.setEnabled(false);
        
        friendDetailComponent.initScrollView(sl_friend_info_pullToRefresh);
        // scrollViewHandler.postDelayed(runnable, 80);
    }
    
    /**
     * 
     */
    private void initUserInfoDatas() {
        // 模拟数据
        userInfoDatas = new ArrayList<EditUserInfoItem>();
        EditUserInfoItem infoItem;
        for (int i = 0; i < 30; i++) {
            infoItem = new EditUserInfoItem("index" + i, "基本信息" + i, "FD实名" + i, "陈杰" + i);
            userInfoDatas.add(infoItem);
        }
    }
    
    @Override
    protected void onStop() {
        super.onStop();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    
    /**
     * 返回
     */
    @Click(R.id.main_header_left)
    void back() {
        finish();
    }
    
    public static WorkBean bean;
    
    public void initWorkDatas() {
        /**
         * 模拟数据 开始位置
         */
        String nickname;
        String time;
        
        ArrayList<Praise> praises;
        ArrayList<Praise> tramples;
        ArrayList<Comment> comments;
        
        nickname = null;// DecentWorldApp.getInstance().getUserName();
        
        // 时间数据
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日  HH:mm");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        time = formatter.format(curDate);
        
        // 照片数据
        List<ArrayList<String>> urls = new ArrayList<ArrayList<String>>();
        ArrayList<String> url_temp;
        for (int i = 0; i < 4; i++) {
            url_temp = new ArrayList<String>();
            for (int j = 0; j < 5; j++) {
                url_temp.add(Urls.URL_LIST[i * 5 + j]);
            }
            urls.add(url_temp);
        }
        
        // 点赞数据
        praises = new ArrayList<Praise>();
        Praise praise = null;
        for (int i = 0; i < 88; i++) {
            praise = new Praise();
            praise.setAvatar(Urls.URL_AVATAR[1]);
            praise.setNickname("功夫巨星");
            praise.setTime(time);
            praises.add(praise);
        }
        
        // 踩数据
        tramples = new ArrayList<Praise>();
        for (int i = 0; i < 3; i++) {
            praise = new Praise();
            praise.setAvatar(Urls.URL_AVATAR[3]);
            praise.setNickname("天外飞仙");
            praise.setTime(time);
            tramples.add(praise);
        }
        
        // 评论数据
        comments = new ArrayList<Comment>();
        Comment comment1 = new Comment();
        comment1.setC_name("安妮");
        comment1.setC_content("成龙大哥好帅啊！");
        comment1.setC_date(time);
        Comment comment2 = new Comment();
        comment2.setC_isreply(true);
        comment2.setC_reply_name("熊二");
        comment2.setC_name("安妮");
        comment2.setC_content("武打明星！");
        comment2.setC_date(time);
        comments.add(comment1);
        comments.add(comment2);
        
        // 加载数据
        ArrayList<String> url = new ArrayList<String>();
        for (int i = 4; i < 9; i++) {
            url.add(Urls.URL_LIST[i]);
        }
        
        bean = new WorkBean();
        bean.setAvatar(Urls.URL_AVATAR[3]);
        bean.setNickname(FriendDetailActivity.user_nickname);
        bean.setTime(time);
        bean.setDescription("加载成功 ：成龙，国家一级演员，大中华区影坛和国际功夫影星。");
        bean.setUrls(url);
        bean.setPraises(praises);
        bean.setGood(praises.size());
        bean.setTramples(tramples);
        bean.setBad(tramples.size());
        bean.setComments(comments);
        
        // 其它数据
        FriendDetailActivity.uu = new ArrayList<WorkBean>();
        WorkBean bWorkBean;
        for (int i = 0; i < urls.size(); i++) {
            bWorkBean = new WorkBean();
            bWorkBean.setAvatar(Urls.URL_AVATAR[3]);
            bWorkBean.setNickname(FriendDetailActivity.user_nickname);
            bWorkBean.setTime(time);
            bWorkBean.setDescription("成龙，国家一级演员，大中华区影坛和国际功夫影星。");
            bWorkBean.setUrls(urls.get(i));
            bWorkBean.setPraises(praises);
            bWorkBean.setGood(praises.size());
            bWorkBean.setTramples(tramples);
            bWorkBean.setBad(tramples.size());
            bWorkBean.setComments(comments);
            FriendDetailActivity.uu.add(bWorkBean);
        }
        
        /**
         * 模拟数据 结束位置
         */
    }
    
    /**
     * 处理点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_friend_info_setting:
                startActivity(new Intent(this, FriendDetailSettingActivity_.class).putExtra("userId", user_dwID));
                break;
            // case R.id.ll_friend_info_chat:
            // {
            // startActivity(new Intent(this ,
            // ChatActivity_.class).putExtra("user_dwID",
            // user_dwID).putExtra("user_nickname",
            // user_nickname).putExtra("chatType",
            // DWMessage.CHAT_TYPE_SINGLE_FRIEND));
            // this.finish();
            // }
            //
            // break;
            case R.id.ll_friend_info_detail: {
                iv_friend_info_tab.setBackground(getResources().getDrawable(R.drawable.user_detail_info_tab_left));
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ll_friend_info_container, userDetailFragment);
                fragmentTransaction.commit();
                ll_friend_info_detail.setEnabled(false);
                ll_friend_info_work_circle.setEnabled(true);
            }
                break;
            case R.id.ll_friend_info_work_circle: {
                iv_friend_info_tab.setBackground(getResources().getDrawable(R.drawable.user_detail_info_tab_right));
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ll_friend_info_container, workCircleFragment);
                fragmentTransaction.commit();
                ll_friend_info_detail.setEnabled(true);
                ll_friend_info_work_circle.setEnabled(false);
            }
                break;
            
            default:
                break;
        }
        
    }
    
}
