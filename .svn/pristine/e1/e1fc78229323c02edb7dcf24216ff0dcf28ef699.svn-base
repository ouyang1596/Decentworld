/**
 * 
 */
package cn.sx.decentworld.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.RecommendUnavaliableAdapter;
import cn.sx.decentworld.bean.RecommendUnavaliable;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.LogUtils;
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
@EActivity(R.layout.activity_recommend_unavaliable)
public class RecommendUnavaliableActivity extends BaseFragmentActivity implements OnClickListener
{
    /**
     * 常量
     */
    public static final String TAG = "RecommendUnavaliable";
    private static final int GET_UNAVALIABLE_LIST = 1;

    /**
     * 工具类
     */
    @Bean
    GetUserInfo getUserInfo;
    @Bean
    ToastComponent toast;

    /**
     * 界面资源
     */
    @ViewById(R.id.iv_back)
    ImageView iv_back;
    
    @ViewById(R.id.lv_recommend_unavaliable)
    ListView lv_recommend_unavaliable;

    /**
     * 变量
     */
    private String userID = "";
    private List<RecommendUnavaliable> listData;
    private RecommendUnavaliableAdapter adapter;

    /**
     * 入口
     */
    @AfterViews
    void init()
    {
        userID = DecentWorldApp.getInstance().getDwID();
        listData = new ArrayList<RecommendUnavaliable>();
        adapter = new RecommendUnavaliableAdapter(this , listData);
        lv_recommend_unavaliable.setAdapter(adapter);
        iv_back.setOnClickListener(this);
        loadData();
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
            // 没有网络，提示
        }
    }

    /**
     * 加载网络数据
     */
    private void loadNetData()
    {
        getUserInfo.getRecommendUnavaliable(userID, handler, GET_UNAVALIABLE_LIST);

    }

    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case GET_UNAVALIABLE_LIST:
                    processResult(msg);
                    break;
                default:
                    break;
            }
        };
    };

    /**
     * 处理从网络获取的结果
     * 
     * @param msg
     */
    protected void processResult(Message msg)
    {
        if (msg.arg1 == 1)
        {
            //清除数据
            listData.clear();
            // 获取数据成功
            String jsonStr = msg.obj.toString();
            LogUtils.i(TAG, "从网络获取的已经推荐的人的列表数据为："+jsonStr);
            JSONObject object = JSON.parseObject(jsonStr);
            String jsonArray = object.getString("UnavaliableList");
            JSONArray array = JSON.parseArray(jsonArray);
            if(array.size()>0)
            {
                for(int i=0;i<array.size();i++)
                {
                    object = array.getJSONObject(i);
                    RecommendUnavaliable unavaliable = new RecommendUnavaliable();
                    unavaliable.setPhoneNum(object.getString("phoneNum"));
                    unavaliable.setAmount(object.getFloatValue("amount"));
                    unavaliable.setStatus(object.getIntValue("status"));
                    listData.add(unavaliable);
                }
            }
            else
            {
                toast.show("没有人哦！");
            }
        }
        else
        {
            // 获取数据失败
            toast.show("获取数据失败");
            
        }
        if(adapter !=null)
            adapter.notifyDataSetChanged();

    }

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

}
