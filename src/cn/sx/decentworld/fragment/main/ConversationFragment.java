/**
 * 
 */
package cn.sx.decentworld.fragment.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.ChatActivity;
import cn.sx.decentworld.activity.ChatActivity_;
import cn.sx.decentworld.activity.DawanZhongchouActivity;
import cn.sx.decentworld.activity.SearchActivity_;
import cn.sx.decentworld.adapter.ConversationListAdapter;
import cn.sx.decentworld.bean.ConversationList;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.XmppHelper;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment.OnCommomPromptListener;
import cn.sx.decentworld.engine.ConversationEngine;
import cn.sx.decentworld.entity.LaunchChatEntity;
import cn.sx.decentworld.entity.db.ContactUser;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.listener.LoginListener;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.LogRecord;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.utils.ThreadUtils;
import cn.sx.decentworld.utils.ToastUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.umeng.analytics.MobclickAgent;

/**
 * @ClassName: ConversationFragment.java
 * @Description:
 * @author: cj
 * @date: 2016年1月11日 上午11:12:06
 */
@EFragment(R.layout.fragment_main_conversation)
public class ConversationFragment extends BaseFragment implements OnClickListener, OnCommomPromptListener
{
    private static final String TAG = "ConversationFragment";
    private static final int GET_FRIEND_CONV_LIST = 1;
    public static boolean isLoading = false;// 判断是否在网络请求当中

    /**
     * 工具类
     */
    @Bean
    ToastComponent toast;
    @Bean
    GetUserInfo getUserInfo;

    @ViewById(R.id.tv_header_title)
    TextView tvTitle;
    @ViewById(R.id.iv_search)
    ImageView ivSearch;

    /** 网络错误 **/
    @ViewById(R.id.rl_fragment_chat_chat_error)
    RelativeLayout rl_fragment_chat_chat_error;
    @ViewById(R.id.tv_connect_errormsg)
    TextView tv_connect_errormsg;

    @ViewById(R.id.fragment_chat_chat_lv)
    ListView fragment_chat_chat_lv;

    /** 消息资源list */
    private String userID = "";
    private ConversationListAdapter conversationAdapter;
    /** 保存会话数据 **/
    private CommomPromptDialogFragment mCommomPromptDialog;
    private List<ConversationList> data;

    /**
     * 入口
     */
    @AfterViews
    void init()
    {
        LogUtils.i(TAG, "init");
        EventBus.getDefault().register(this);
        userID = DecentWorldApp.getInstance().getDwID();
        tvTitle.setText(getResources().getString(R.string.main_bottom_chat));
        ivSearch.setVisibility(View.VISIBLE);
        viewPrice = View.inflate(getActivity(), R.layout.item_zhongchou, null);
        tvPrice = (TextView) viewPrice.findViewById(R.id.tv_price_today);
        initLister();
        isPrepared = true;
        // 加载会话列表
        lazyLoad();
        getSharePrice();
    }

    private String price;
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            try
            {
                JSONObject object = new JSONObject(msg.obj.toString());
                price = object.getString("price");
                tvPrice.setText("当前价格" + price + "=股权0.05%");
            }
            catch (JSONException e)
            {
            }
        };
    };

    private void getSharePrice()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Constants.DW_ID, DecentWorldApp.getInstance().getDwID());
        isLoading = true;
        getUserInfo.getShareprice(map, ConstantNet.API_GET_SHAREPRICE, mHandler);
    }

    /**
     * 初始化监听
     */
    private void initLister()
    {
        ivSearch.setOnClickListener(this);
        rl_fragment_chat_chat_error.setOnClickListener(this);
    }

    /**
     * 加载数据
     */
    @Override
    protected void lazyLoad()
    {
        LogUtils.i(TAG, "lazyLoad");
        if (isPrepared)
        {
            data = new ArrayList<ConversationList>();
            initData(data);
            initListView(data);
            initUnReadMsgCount();
            receiveOfflineMsg();
            LogUtils.i(TAG, "会话列表初始化完毕");
        }
    }

    /**
     * 初始化会话列表数据,onResume中也会调用
     */
    private void initData(List<ConversationList> data)
    {
        LogUtils.v(TAG, "initData");
        List<ConversationList> temp = ConversationEngine.getInstance().getConv();
        data.clear();
        for (ConversationList conv : temp)
        {
            if (conv.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_FRIEND)
            {
                data.add(conv);
            }
            Collections.sort(data);
        }
        if (conversationAdapter != null)
        {
            conversationAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 
     */
    View viewPrice;
    TextView tvPrice;

    protected void initListView(final List<ConversationList> data)
    {
        fragment_chat_chat_lv.addHeaderView(viewPrice);
        conversationAdapter = new ConversationListAdapter(getActivity() , data);
        fragment_chat_chat_lv.setAdapter(conversationAdapter);
        fragment_chat_chat_lv.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                position = position - 1;
                if (position == -1)
                {
                    if (!isLoading)
                    {
                        Intent intent = new Intent(getActivity() , DawanZhongchouActivity.class);
                        intent.putExtra(ConstantIntent.PRICE, price);
                        startActivity(intent);
                    }
                    else
                    {
                        ToastUtil.showToast("正在网络请求中。。。");
                    }

                    return;
                }
                ConversationList conversationList = data.get(position);
                if (FileUtils.getPromptStatus(ConversationFragment.this.getActivity(), Constants.FRIEND) == CommomPromptDialogFragment.FRIEND)
                {
                    Intent intent = new Intent(getActivity() , ChatActivity_.class);
                    // intent.putExtra(ChatActivity.OTHER_ID,
                    // conversationList.getDwID());
                    // intent.putExtra(ChatActivity.OTHER_NICKNAME,
                    // conversationList.getTitle());
                    // intent.putExtra(ChatActivity.CHAT_RELATIONSHIP,
                    // conversationList.getChatRelationship());
                    // intent.putExtra(ChatActivity.CHAT_TYPE,
                    // conversationList.getChatType());
                    // intent.putExtra(ChatActivity.OTHER_WORTH,
                    // Float.valueOf(conversationList.getWorth()));

                    LaunchChatEntity entity = new LaunchChatEntity(conversationList.getDwID() , conversationList.getTitle() , Float.valueOf(conversationList.getWorth()) , conversationList
                            .getChatType() , conversationList.getChatRelationship() , conversationList.getUserType());
                    intent.putExtra(ChatActivity.LAUNCH_CHAT_KEY, entity);

                    startActivity(intent);
                }
                else
                {
                    mCommomPromptDialog = new CommomPromptDialogFragment();
                    mCommomPromptDialog.setObject(conversationList);
                    mCommomPromptDialog.setEnter(CommomPromptDialogFragment.FRIEND);
                    mCommomPromptDialog.setOnCommomPromptListener(ConversationFragment.this);
                    mCommomPromptDialog.setTips("你与他是朋友关系，他与你每说一句话，将按照他的身价向你付费。同理，注意你的身价哦！");
                    mCommomPromptDialog.show(ConversationFragment.this.getActivity().getSupportFragmentManager(), "mCommomPromptDialog");
                }
            }
        });
    }

    @Override
    public void turnToTab(int tab)
    {

    }

    /**
     * 初始化未读消息数： 根据list中的数据计算未读消息数
     */
    private void initUnReadMsgCount()
    {
        int count = 0;
        if (null != data && data.size() > 0)
        {
            for (ConversationList conv : data)
            {
                if (conv.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_FRIEND)
                    count += conv.getUnReadCount();
            }
        }
        // 将消息通知到MainFragment
        EventBus.getDefault().post(count, NotifyByEventBus.NT_UNREAD_CONVERSATION_COUNT);
    }

    /**
     * 向服务器发送presence，接收离线消息
     */
    public void receiveOfflineMsg()
    {
        /** 初始化完毕，可以接受离线消息（3） **/
        DecentWorldApp.getInstance().setMainActivityInit(true);
        XmppHelper.sendPresence();
    }

    /**
     * 重新加载好友会话列表
     */
    @Subscriber(tag = NotifyByEventBus.NT_RE_LOAD_FRIEND_CONV)
    public void reLoadFriendConv(String casue)
    {
        LogUtils.v(TAG, "reLoadFriendConv...cause by:" + casue);
        initData(data);
        initUnReadMsgCount();
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_search:
                startActivity(new Intent(getActivity() , SearchActivity_.class));
                break;
            case R.id.rl_fragment_chat_chat_error:
                manualConnect();
                break;

            default:
                break;
        }
    }

    @Override
    public void onCommomPromtClick(View view)
    {
        ConversationList conversationList = (ConversationList) mCommomPromptDialog.getObject();
        Intent intent = new Intent(getActivity() , ChatActivity_.class);
        // intent.putExtra(ChatActivity.OTHER_ID, conversationList.getDwID());
        // intent.putExtra(ChatActivity.OTHER_NICKNAME,
        // conversationList.getTitle());
        // intent.putExtra(ChatActivity.CHAT_RELATIONSHIP,
        // conversationList.getChatRelationship());
        // intent.putExtra(ChatActivity.CHAT_TYPE,
        // conversationList.getChatType());
        // intent.putExtra(ChatActivity.OTHER_WORTH,
        // Float.valueOf(conversationList.getWorth()));

        LaunchChatEntity entity = new LaunchChatEntity(conversationList.getDwID() , conversationList.getTitle() , Float.valueOf(conversationList.getWorth()) , conversationList.getChatType() ,
                conversationList.getChatRelationship() , conversationList.getUserType());
        intent.putExtra(ChatActivity.LAUNCH_CHAT_KEY, entity);

        startActivity(intent);
    }

    /**
     * 显示网络状态（此处为与Openfire服务器连接的状态）
     * 
     * @param state
     *            状态：断开连接（0），已连接（1）
     */
    @Subscriber(tag = NotifyByEventBus.NT_NETWORK_STATE_OF)
    public void netWorkState(int state)
    {
        String info = "";
        if (state == NetworkUtils.RECONNECTION_SUCCESSFUL)
        {
            // 已经连接,隐藏错误提示
            info = NetworkUtils.RECONNECTION_SUCCESSFUL_STR;
            rl_fragment_chat_chat_error.setVisibility(View.GONE);
        }
        else
        {
            if (state == NetworkUtils.CONNECTION_CLOSED)
            {
                // 断开连接，显示错误提示
                info = NetworkUtils.CONNECTION_CLOSED_STR;
            }
            else if (state == NetworkUtils.CONNECTION_CLOSED_ERROR)
            {
                info = NetworkUtils.CONNECTION_CLOSED_STR;
            }
            else if (state == NetworkUtils.RECONNECTING_IN)
            {
                info = NetworkUtils.RECONNECTING_IN_STR;
            }
            else if (state == NetworkUtils.RECONNECTION_FAILED)
            {
                info = NetworkUtils.RECONNECTION_FAILED_STR;
            }
            rl_fragment_chat_chat_error.setVisibility(View.VISIBLE);
            tv_connect_errormsg.setText(info);
        }
    }

    // ///////////////////////////////////////手动重连///////////////////////////////////////
    /**
     * 触发手动重连
     */
    private void manualConnect()
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                if (msg.what == 0)
                {
                    rl_fragment_chat_chat_error.setVisibility(View.VISIBLE);

                    if (LogUtils.IS_SHOW_TEST_CODE)
                    {
                        toast.show(msg.obj.toString());
                        tv_connect_errormsg.setText(msg.obj.toString());
                    }
                    else
                    {
                        tv_connect_errormsg.setText("连接失败");
                        toast.show("连接失败");
                    }

                }
                else if (msg.what == 1)
                {
                    rl_fragment_chat_chat_error.setVisibility(View.GONE);
                    toast.show("已连接");
                }
            };
        };
        // 判断重连线程是否存在；Thread name:Smack Reconnection Manager
        // 如果存在，则提示重连中，否则新建XMPPConnection，进行登录连接；
        boolean exitOfReconnectThread = ThreadUtils.isExitOfReconnectThread();
        if (exitOfReconnectThread)
        {
            rl_fragment_chat_chat_error.setVisibility(View.VISIBLE);
            tv_connect_errormsg.setText("网络不太好，连接中...");
            // Log信息
            if (LogUtils.IS_SHOW_TEST_CODE)
            {
                LogUtils.i(TAG, "重连线程存在");
                toast.show("网络不太好，连接中...");
                LogRecord.getInstance().saveGeneralLog("conn", "The ReconnectionThread is Exit");
            }
        }
        else
        {
            if (NetworkUtils.isNetWorkConnected(getActivity()))
            {
                if (LogUtils.IS_SHOW_TEST_CODE)
                {
                    toast.show("重新登录");
                }
                // 发送错误报告到服务器；
                LogRecord.getInstance().sendReportWhenManualConn();
                XmppHelper.reLogin(getActivity(), new LoginListener()
                {
                    @Override
                    public void onLoginSuccess()
                    {
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onLoginFailure(Exception exception)
                    {
                        Message message = Message.obtain();
                        message.obj = exception.toString();
                        handler.sendMessage(message);
                    }
                });
            }
            else
            {
                rl_fragment_chat_chat_error.setVisibility(View.VISIBLE);
                tv_connect_errormsg.setText("请检查网络");
                toast.show("请检查网络");
            }

            // Log信息
            if (LogUtils.IS_SHOW_TEST_CODE)
            {
                LogUtils.i(TAG, "重连线程bu存在");
                LogRecord.getInstance().saveGeneralLog("conn", "The ReconnectionThread is not Exit");
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        initData(data);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

}
