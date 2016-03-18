/**
 * 
 */
package cn.sx.decentworld.fragment.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import cn.sx.decentworld.activity.SearchActivity_;
import cn.sx.decentworld.adapter.ConversationListAdapter;
import cn.sx.decentworld.bean.ConversationList;
import cn.sx.decentworld.bean.DWMessage;
import cn.sx.decentworld.bean.MsgAndInfo;
import cn.sx.decentworld.bean.NotifyByEventBus;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.XmppHelper;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment;
import cn.sx.decentworld.dialog.CommomPromptDialogFragment.OnCommomPromptListener;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.fragment.stranger.StrangerListFragment;
import cn.sx.decentworld.listener.LoginListener;
import cn.sx.decentworld.manager.ConversationManager;
import cn.sx.decentworld.network.request.GetUserInfo;
import cn.sx.decentworld.network.request.SetFriendInfo;
import cn.sx.decentworld.utils.FileUtils;
import cn.sx.decentworld.utils.ImageUtils;
import cn.sx.decentworld.utils.LogRecord;
import cn.sx.decentworld.utils.LogUtils;
import cn.sx.decentworld.utils.NetworkUtils;
import cn.sx.decentworld.utils.ThreadUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

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
    /**
     * 工具类
     */
    @Bean
    ToastComponent toast;
    @Bean
    GetUserInfo getUserInfo;
    @Bean
    SetFriendInfo setFriendInfo;

    /**
     * 界面资源
     */

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

    /**
     * 变量
     */
    /** 消息资源list */
    private String userID = "";
    private List<ConversationList> conversationData;
    private ConversationListAdapter conversationAdapter;
    /** 保存会话数据 **/
    private Map<String, ConversationList> conversationMap;
    private CommomPromptDialogFragment mCommomPromptDialog;

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
        initLister();
        // TODO
        isPrepared = true;
        //加载会话列表
        lazyLoad();
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
            List<ConversationList> convList = ConversationManager.getInstance().getFriendConversationList();
            if (convList.size() > 0)
            {
                initConversation();
                receiveOfflineMsg();
            }
            else
            {
                if (NetworkUtils.isNetWorkConnected(getActivity()))
                {
                    /** 有网络，从网络拿取数据，并解析和显示 **/
                    getUserInfo.getFriendConvList(userID, handler, GET_FRIEND_CONV_LIST);
                }
                else
                {
                    /** 没有网络，从本地拿取数据 **/
                    initConversation();
                    receiveOfflineMsg();
                }
            }
        }
    }

    @Override
    public void turnToTab(int tab)
    {

    }

    /**
     * 初始化会话列表 包括初始化变量、数据、未读消息
     */
    public void initConversation()
    {
        initVar();
        initData();
        initUnReadMsgCount();
    }

    /**
     * 初始化会话列表相关变量 list map adapter listview
     */
    private void initVar()
    {
        if (conversationData == null || conversationMap == null)
        {
            conversationData = new ArrayList<ConversationList>();
            conversationMap = new HashMap<String, ConversationList>();
            conversationAdapter = new ConversationListAdapter(getActivity() , conversationData);
            fragment_chat_chat_lv.setAdapter(conversationAdapter);
            fragment_chat_chat_lv.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    ConversationList conversationList = conversationData.get(position);
                    if (FileUtils.getPromptStatus(ConversationFragment.this.getActivity(), Constants.FRIEND) == CommomPromptDialogFragment.FRIEND)
                    {
                        Intent intent = new Intent(getActivity() , ChatActivity_.class);
                        intent.putExtra(ChatActivity.OTHER_ID, conversationList.getDwID());
                        intent.putExtra(ChatActivity.OTHER_NICKNAME, conversationList.getTitle());
                        intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, conversationList.getChatRelationship());
                        intent.putExtra(ChatActivity.CHAT_TYPE, conversationList.getChatType());
                        intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(conversationList.getWorth()));
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
    }

    /**
     * 初始化会话列表数据
     */
    private void initData()
    {
        conversationData.clear();
        conversationMap.clear();
        List<ConversationList> convList = ConversationManager.getInstance().getFriendConversationList();
        /** 将从数据库获取的会话列表加载到内存中 **/
        if (convList.size() > 0)
        {
            for (ConversationList con : convList)
            {
                String key = con.getDwID() + con.getChatType();
                conversationData.add(con);
                conversationMap.put(key, con);
            }
            //排序
            sort(conversationData);
        }
        /** 刷新界面 **/
        if (conversationAdapter != null)
        {
            conversationAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化未读消息数 根据list中的数据计算未读消息数
     */
    private void initUnReadMsgCount()
    {
        // 从会话列表中计算未读消息总数
        int count = 0;
        if (conversationData == null)
            count = 0;
        else
        {
            for (ConversationList conv : conversationData)
            {
                if (conv.getChatRelationship() == DWMessage.CHAT_RELATIONSHIP_FRIEND)
                    count += conv.getUnReadCount();
            }
        }
        /** 将消息通知到MainFragment **/
        EventBus.getDefault().post(count, NotifyByEventBus.NT_UNREAD_CONVERSATION_COUNT);
    }

    /**
     * 接收到刷新会话列表的通知
     * 
     * @param tag
     */
    @Subscriber(tag = NotifyByEventBus.NT_INIT_FRIEND_CONVERSATION)
    public void initConversation(String tag)
    {
        LogUtils.i(TAG, "初始化会话列表，tag =" + tag);
        initConversation();
    }

    /**
     * 刷新会话列表和chatTab选项卡(5个地方会调用)
     */
    @Subscriber(tag = NotifyByEventBus.NT_REFRESH_CONVERSATION)
    public void refreshConversation(MsgAndInfo msgAndInfo)
    {
        ConversationManager.getInstance().refreshFriendConvByMsg(msgAndInfo, conversationData, conversationMap);
        //排序
        sort(conversationData);
        if (conversationAdapter != null)
        {
            conversationAdapter.notifyDataSetChanged();
        }
        // 显示小红点
        initUnReadMsgCount();
    }

    /**
     * 清除指定联系人会话列表
     * 
     * @param conversationKey
     */
    @Subscriber(tag = NotifyByEventBus.NT_CLEAR_CONVERSATION_UNREAD)
    public void clearUnread(String conversationKey)
    {
        ConversationList conversation = conversationMap.get(conversationKey);
        if (conversation != null)
        {
            conversation.setUnReadCount(0);
            conversation.updateUnReadCount();
            initUnReadMsgCount();
            conversationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume()
    {
        LogUtils.i(TAG, "onResume");
        super.onResume();
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

    /**
     * 触发手动重连
     */
    private void manualConnect()
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg) {
                if(msg.what == 0)
                {
                    rl_fragment_chat_chat_error.setVisibility(View.VISIBLE);
                    tv_connect_errormsg.setText("连接失败");
                    toast.show("连接失败");
                }else if(msg.what == 1)
                {
                    rl_fragment_chat_chat_error.setVisibility(View.GONE);
                    toast.show("连接成功");
                }
            };
        };
        //判断重连线程是否存在；Thread name:Smack Reconnection Manager
        //如果存在，则不做操作，否则新建XMPPConnection，进行登录连接；
        boolean exitOfReconnectThread = ThreadUtils.isExitOfReconnectThread();
        if(exitOfReconnectThread)
        {
            LogUtils.i(TAG, "重连线程存在");
            LogRecord.getInstance().saveGeneralLog("conn", "The ReconnectionThread is Exit");
            toast.show("网络不太好，重连中...");
        }
        else
        {
            LogUtils.i(TAG, "重连线程bu存在");
            LogRecord.getInstance().saveGeneralLog("conn", "The ReconnectionThread is not Exit");
            if(NetworkUtils.isNetWorkConnected(getActivity()))
            {
                //发送错误报告到服务器；
                LogRecord.getInstance().sendReportWhenManualConn();
                XmppHelper.reLogin(new LoginListener()
                {
                    @Override
                    public void onLoginSuccess()
                    {
                        handler.sendEmptyMessage(1);
                    }
                    
                    @Override
                    public void onLoginFailure(Exception exception)
                    {
                        handler.sendEmptyMessage(0);
                    }
                });
            }
            else
            {
                rl_fragment_chat_chat_error.setVisibility(View.VISIBLE);
                tv_connect_errormsg.setText("请检查网络");
                toast.show("请检查网络");
            }
        }
    }

    /**
     * 回调处理
     */
    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case GET_FRIEND_CONV_LIST:
                    processFriendConvList(msg);
                    break;

                default:
                    break;
            }
        };
    };

    /**
     * 处理获取的好友会话列表
     * 
     * @param msg
     */
    protected void processFriendConvList(Message msg)
    {
        if (msg.arg1 == 1)
        {
            ConversationList.deleteConversation(userID, 0);
            LogUtils.i(TAG, "msg.obj.toString=" + msg.obj.toString());
            String jsonStr = msg.obj.toString();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            String jsonArrayStr = jsonObject.getString("friendCoversations");
            // 解析数据
            JSONArray array = JSON.parseArray(jsonArrayStr);
            if (null == array)
            {
                return;
            }
            if (array.size() > 0)
            {
                for (int i = 0; i < array.size(); i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    int chatRelationship = object.getIntValue("chatRelationship");
                    int chatType = object.getIntValue("chatType");
                    String content = object.getString("content");
                    int messageType = object.getIntValue("messageType");
                    String otherID = object.getString("otherID");
                    String title = object.getString("title");
                    String worth = object.getString("worth");
                    long time = object.getLongValue("time");
                    String icon = ImageUtils.getIconByDwID(otherID, ImageUtils.ICON_SMALL);
                    // 构造会话对象
                    ConversationList conversation = new ConversationList(userID , otherID , icon , title , content , time);
                    conversation.setMessageType(messageType);
                    conversation.setChatType(chatType);
                    conversation.setChatRelationship(0);
                    conversation.setWorth(worth);
                    conversation.save();
                }
            }
        }
        else
        {
            LogUtils.i(TAG, "获取好友会话列表失败");
        }
        // 更新会话列表
        initConversation();
        receiveOfflineMsg();
    }

    @Override
    public void onCommomPromtClick(View view)
    {
        ConversationList conversationList = (ConversationList) mCommomPromptDialog.getObject();
        Intent intent = new Intent(getActivity() , ChatActivity_.class);
        intent.putExtra(ChatActivity.OTHER_ID, conversationList.getDwID());
        intent.putExtra(ChatActivity.OTHER_NICKNAME, conversationList.getTitle());
        intent.putExtra(ChatActivity.CHAT_RELATIONSHIP, conversationList.getChatRelationship());
        intent.putExtra(ChatActivity.CHAT_TYPE, conversationList.getChatType());
        intent.putExtra(ChatActivity.OTHER_WORTH, Float.valueOf(conversationList.getWorth()));
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
        if(state == NetworkUtils.RECONNECTION_SUCCESSFUL)
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
     * 排序
     * @param strangerConversationList
     */
    private void sort(List<ConversationList> conversationData)
    {
        if(conversationData!=null && conversationData.size()>0)
            Collections.sort(conversationData);
    }

}
