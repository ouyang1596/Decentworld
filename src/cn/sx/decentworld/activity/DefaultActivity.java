package cn.sx.decentworld.activity;

import java.util.Calendar;

import org.jivesoftware.smack.XMPPConnection;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.ChoiceInfo;
import cn.sx.decentworld.bean.UserInfo;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.listener.ConnectOpenFireListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.task.ConnectOpenFireTask;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.utils.PromptUtils;
import cn.sx.decentworld.utils.TimeUtils;
import cn.sx.decentworld.utils.sputils.UserInfoHelper;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: DefaultActivity.java
 * @Description: 程序默认启动的界面
 * @author: cj
 * @date: 2016年3月9日 下午3:15:04
 */
@EActivity(R.layout.activity_default)
public class DefaultActivity extends BaseFragmentActivity
{
    private static final String TAG = "DefaultActivity";
    // private XMPPConnection con;
    private static final int SUCCESS = 1;
    private static final int FAILURE = 0;
    private Calendar mCalendar;
    @ViewById(R.id.tv_year)
    TextView tvYear;
    @ViewById(R.id.tv_month)
    TextView tvMonth;
    @ViewById(R.id.tv_week)
    TextView tvWeek;
    @ViewById(R.id.tv_day)
    TextView tvDay;
    @ViewById(R.id.tv_motto)
    TextView tvMotto;
    @ViewById(R.id.tv_motto_six)
    TextView tvMottoSix;
    @Bean
    ToastComponent toast;

    // 用户ID
    private String userID;

    /**
     * 初始化入口
     */
    @AfterViews
    public void init()
    {
        LogUtils.i(TAG, "init");
        initVar();
        initMotto();
        initDate();
        redirectToActivity();
        deleteChoiceInfo();
    }

    /**
     * 初始化变量
     */
    private void initVar()
    {
        userID = DecentWorldApp.getInstance().getDwID();
    }

    /**
     * 初始化座右铭
     */
    private void initMotto()
    {
        if (CommUtil.isBlank(userID))
        {
            return;
        }
        UserInfo userInfo = UserInfo.queryByDwID(userID);
        if (null == userInfo)
        {
            return;
        }
        String motto = userInfo.getMotto();
        if (!TextUtils.isEmpty(motto))
        {
            if (motto.length() > 6)
            {
                tvMottoSix.setText(motto.substring(0, 6));
                tvMotto.setText(motto.substring(6));
            }
            else
            {
                tvMottoSix.setText(motto);
                tvMotto.setText("");
            }
        }
    }

    /**
     * 初始化时间
     */
    private void initDate()
    {
        mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        tvYear.setText("" + year);
        int m = mCalendar.get(Calendar.MONTH) + 1;
        String month = TimeUtils.MonthHandle(m);
        tvMonth.setText(month);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        tvDay.setText("" + day);
        int w = mCalendar.get(Calendar.DAY_OF_WEEK);
        String week = TimeUtils.weekHandle(w);
        tvWeek.setText(week);
        LogUtils.e("bm", "year--" + year + "month--" + month + "day--" + day + "week--" + w);
    }

    /**
     * 判断跳转到哪个界面
     */
    private void redirectToActivity()
    {
        if (!UserInfoHelper.isLogined(this))
        {
            LogUtils.i(TAG, "没有登录过，跳转到登录界面");
            Intent intent = new Intent(DefaultActivity.this , LoginActivity_.class);
            startActivity(intent);
            finish();
        }// 检查是否有被挤下线的状态，有则弹出被挤下线的对话框
        else if (UserInfoHelper.isCrushOffline(DecentWorldApp.getInstance().getDwID()))
        {
            // 被挤下线
            PromptUtils promptUtils = new PromptUtils(DefaultActivity.this);
            promptUtils.showOffLine("被挤下线", "您有账号异地登陆，被挤下线\n  请重新登录试试！");
        }
        else
        {
            LogUtils.i(TAG, "以前登录过，配置文件中有登录信息");
            if (NetworkUtils.isNetWorkConnected(mContext))
            {
                // 上传日志文件
                if (DecentWorldApp.getInstance().isUploadLogFile())
                {
                    LogUtils.uploadLogFile();
                    DecentWorldApp.getInstance().setUploadLogFile(false);
                }
                LogUtils.i(TAG, "有网络，加载网络数据");
                String dwID = DecentWorldApp.getInstance().getDwID();
                String password = DecentWorldApp.getInstance().getPassword();
                ConnectOpenFireTask task = new ConnectOpenFireTask();
                task.setOnConnectOpenFireListener(new ConnectOpenFireListener()
                {
                    @Override
                    public void onSuccess()
                    {
                        LogUtils.i(TAG, "登录openfire成功");
                        Intent intent = new Intent(DefaultActivity.this , LoadNetdataActivity_.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure()
                    {
                        LogUtils.i(TAG, "登录openfire失败");
                        Intent intent = new Intent(DefaultActivity.this , LoginActivity_.class);
                        startActivity(intent);
                        finish();
                    }
                });
                task.execute(dwID, password);
            }
            else
            {
                LogUtils.i(TAG, "没有网络，加载历史数据");
                Intent intent = new Intent(DefaultActivity.this , LoadNetdataActivity_.class);
                startActivity(intent);
                finish();
            }
        }
    }

    /**
     * 删除选择的数据
     */
    private void deleteChoiceInfo()
    {
        ChoiceInfo info = ChoiceInfo.queryByDwID(DecentWorldApp.MAIN_KEY);
        if (null != info)
        {
            info.delete();
        }
    }

}
