/**
 * 
 */
package cn.sx.decentworld.fragment.me;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.EditUserInfoActivity_;
import cn.sx.decentworld.adapter.UserInfoAdapter;
import cn.sx.decentworld.bean.UserInfoField.Field;
import cn.sx.decentworld.bean.UserInfoField.FieldGroup;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.engine.UserDataEngine;
import cn.sx.decentworld.engine.UserDataEngine.GetWealthListener;
import cn.sx.decentworld.entity.SelfUserField;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.SelfInfoManager;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.widget.ListViewForScrollView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: InnateFragment.java
 * @Description: 资料
 * @author: cj
 * @date: 2016年1月18日 下午7:48:34
 */
@EFragment(R.layout.fragment_me_innate)
public class InnateFragment extends BaseFragment implements GetWealthListener
{
    /** 常量 **/
    private static final String TAG = "InnateFragment";
    private static final int R_GET_USER_THREE_ICON = 1;

    /** 工具类 **/
    @Bean
    ToastComponent toast;
    @Bean
    GetUserInfo getUserInfo;

    /** 界面资源 **/
    @ViewById(R.id.iv_me_innate_edit)
    ImageView iv_me_innate_edit;
    @ViewById(R.id.lv_me_innate)
    ListViewForScrollView mLvUserInfo;

    public ImageView getIvEdit()
    {
        return iv_me_innate_edit;
    }

    /** 变量 **/
    private String userID = "";
    private List<SelfUserField> mDatas;
    private UserInfoAdapter mAdapter;

    /**
     * 入口
     */
    @AfterViews
    void init()
    {
        EventBus.getDefault().register(this);
        userID = DecentWorldApp.getInstance().getDwID();
        mDatas = new ArrayList<SelfUserField>();
        mAdapter = new UserInfoAdapter(getActivity() , mDatas);
        mLvUserInfo.setAdapter(mAdapter);
    }

    /**
     * 数据延迟加载
     */
    @Override
    protected void lazyLoad()
    {
    }

    @Override
    public void turnToTab(int tab)
    {

    }

    @Override
    public void onResume()
    {
        super.onResume();
        LogUtils.v(TAG, "onResume");
        filterData();
    }

    /**
     * 筛选数据,并刷新列表
     */
    private void filterData()
    {
        LogUtils.v(TAG, "filterData() ");
        mDatas.clear();
        List<SelfUserField> temp = SelfInfoManager.getInstance().getSelfUserInfo().getFields();
        for (SelfUserField item : temp)
        {
            String fieldValue = item.getFieldValue();
            int fieldCode = item.getFieldCode();
            int group = item.getGroup();
            if (CommUtil.isNotBlank(fieldValue) && !fieldValue.equals("-1") && item.isDisplayAuth() && FieldGroup.isClientShow(group)
                    && fieldCode != cn.sx.decentworld.bean.UserInfoField.Field.GR.getFieldCode() && fieldCode != cn.sx.decentworld.bean.UserInfoField.Field.LOCATION.getFieldCode()
                    && fieldCode != cn.sx.decentworld.bean.UserInfoField.Field.CARD.getFieldCode() && fieldCode != cn.sx.decentworld.bean.UserInfoField.Field.CLASS.getFieldCode())
                mDatas.add(item);
        }
        Collections.sort(mDatas);
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    /**
     * 界面销毁
     */
    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 编辑个人资料
     */
    @Click(R.id.iv_me_innate_edit)
    public void edit(View v)
    {
        // 编辑资料
        Intent intent = new Intent(getActivity() , EditUserInfoActivity_.class);
        startActivityForResult(intent, R_GET_USER_THREE_ICON);
    }

    /**
     * 更新身家
     */
    @Subscriber(tag = NotifyByEventBus.NT_REFRESH_WEALTH)
    public void refreshWealth(String info)
    {
        LogUtils.i(TAG, info);
        UserDataEngine.getInstance().getWealth(this);
    }

    /**
     * 获取身家完成
     */
    @Override
    public void onGetWealthFinished(String wealth)
    {
        filterData();
    }
}
