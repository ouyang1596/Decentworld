/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.RecommendUnavaliableAdapter;
import cn.sx.decentworld.bean.RecommendUnavailable;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.engine.BenefitEngine;
import cn.sx.decentworld.engine.BenefitEngine.GetRecommendUnavailableListListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.NetworkUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: RecommendUnavaliable.java
 * @Description: 已经推荐过的人，但是对方没有注册或者没有相互加为好友
 * @author: cj
 * @date: 2016年1月28日 下午8:30:45
 */
public class RecommendUnavaliableActivity extends BaseFragmentActivity implements OnClickListener
{
    /**
     * 常量
     */
    public static final String TAG = "RecommendUnavaliable";
    

    /**
     * 控件
     */
    private ImageView mBack;
    private ListView mListView;
    
    /**
     * 变量
     */
    private String userID = "";
    private List<RecommendUnavailable> mData;
    private RecommendUnavaliableAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        setContentView(R.layout.activity_recommend_unavaliable);
        initVars();
        initViews();
        initEvents();
        loadData();
    }

    /**
     * 初始化变量
     */
    private void initVars()
    {
        userID = DecentWorldApp.getInstance().getDwID();
        mData = new ArrayList<RecommendUnavailable>();
        mAdapter = new RecommendUnavaliableAdapter(this , mData);
    }
    
    /**
     * 初始化事件
     */
    private void initViews()
    {
        mBack = (ImageView) findViewById(R.id.iv_back);
        mListView = (ListView) findViewById(R.id.lv_recommend_unavaliable);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 初始化事件
     */
    private void initEvents()
    {
        mBack.setOnClickListener(this);
    }



    /**
     * 加载数据
     */
    private void loadData()
    {
        if (NetworkUtils.isNetWorkConnected(this))
        {
            loadNetData();
        }
        else
        {
            
            //加载本地数据
            
            
            // 没有网络，提示
            toast("请检查网络");
        }
    }

    /**
     * 加载网络数据
     */
    private void loadNetData()
    {
        new BenefitEngine().getRecommendUnavailableList(new GetRecommendUnavailableListListener()
        {
            
            @Override
            public void onSuccess(List<RecommendUnavailable> unavaliable)
            {
                //清除数据
                mData.clear();
                mData.addAll(unavaliable);
                if(mAdapter !=null)
                    mAdapter.notifyDataSetChanged();
            }
            
            @Override
            public void onNoList(String info)
            {
                toast(info);
            }
            
            @Override
            public void onFailure(String cause)
            {
                toast(cause);
            }
        });

    }


    /**
     * 点击事件
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
    
    /**
     * 弹出提示信息
     * @param info
     */
    private void toast(String info)
    {
        Toast.makeText(RecommendUnavaliableActivity.this, info, Toast.LENGTH_SHORT).show();
    }

}
