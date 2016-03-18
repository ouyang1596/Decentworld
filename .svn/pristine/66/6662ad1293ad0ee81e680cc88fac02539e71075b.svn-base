/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.engine.ProcessUserData;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.engine.UserKeyEngine;
import cn.sx.decentworld.engine.UserKeyEngine.UserKeyListener;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: LoadNetdataActivity.java
 * @Description: 初次登陆时加载数据
 * @author: cj
 * @date: 2016年1月30日 上午10:16:40
 */
@EActivity(R.layout.activity_load_net_data)
public class LoadNetdataActivity extends BaseFragmentActivity
{
    /**
     * 常量
     */
    public static final String TAG = "LoadNetdataActivity";
    private static final int HANDLER_KEY_SUCCESS = 0;// 获取key成功
    private static final int HANDLER_KEY_FAILURE = 1;// 获取key失败
    private static final int HANDLER_LOAD_ALL_DATA = 2;// 获取用户的信息并返回

    /**
     * 工具类
     */
    @Bean
    GetUserInfo getUserInfo;

    @ViewById(R.id.tv_load_net_data)
    TextView tvLoadNetData;

    /**
     * 入口
     */
    @AfterViews
    void init()
    {
        // 判断是否需要网络请求（暂时是每次新建进程都从网络获取数据）
        if (isLoadData())
        {
            LogUtils.i(TAG, "init...从网络获取数据");
            loadData();
        }
        else
        {
            LogUtils.i(TAG, "init...不需要从网络获取数据，直接进入MainActivity");
            Intent intent = new Intent(this , MainActivity_.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 是否需要网络请求
     */
    private boolean isLoadData()
    {
        // TODO something
        // 如果登陆过，就不需要重新加载
        // return !DecentWorldApp.getInstance().isLogin();
        return true;
    }

    /**
     * 加载网络数据
     * 
     * @param isLoadData
     */
    private void loadData()
    {
        // 获取key，上传key
        UserKeyEngine userKey = UserKeyEngine.getInstance();
        userKey.getKey(LoadNetdataActivity.this, new UserKeyListener()
        {
            @Override
            public void onSucceed()
            {
                /** 加载用户数据 **/
                handler.sendEmptyMessage(HANDLER_KEY_SUCCESS);
            }

            @Override
            public void onFailed(String casue)
            {
                LogUtils.i(TAG, "获取和上传用户的key失败，casue：" + casue);
                handler.sendEmptyMessage(HANDLER_KEY_FAILURE);
            }
        });
    }

    /**
     * 网络请求回调
     */
    Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case HANDLER_KEY_SUCCESS:
                    LogUtils.i(TAG, "从网络加载数据 ...loadNetData");
                    getUserInfo.loadAllData(DecentWorldApp.getInstance().getDwID(), handler, HANDLER_LOAD_ALL_DATA);
                    LogUtils.i(TAG, "获取和上传用户的key成功");
                    break;
                case HANDLER_KEY_FAILURE:
                    tvLoadNetData.setText("获取key失败");
                    LogUtils.i(TAG, "获取和上传用户的key失败");
                    break;
                case HANDLER_LOAD_ALL_DATA:
                    if (msg.arg1 == 1)
                    {
                        LogUtils.i(TAG, "获取数据成功，数据为：" + msg.obj.toString());
                        String result = msg.obj.toString();
                        if (CommUtil.isNotBlank(result))
                        {
                            new ProcessUserData().processLoginNetData(msg.obj.toString());
                            loadDataToMemory();

                            Intent intent = new Intent(LoadNetdataActivity.this , MainActivity_.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else if (msg.arg1 == 0)
                    {
                        LogUtils.i(TAG, "获取数据失败");
                        tvLoadNetData.setText("获取用户数据失败");
                    }
                    break;
                default:
                    break;
            }
        }

        /**
         * 将基本数据加载到内存中
         */
        private void loadDataToMemory()
        {
            UserInfoEngine.getInstance().initData();
        };
    };

}
