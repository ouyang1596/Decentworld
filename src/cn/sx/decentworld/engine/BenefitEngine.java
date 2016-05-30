/**
 * 
 */
package cn.sx.decentworld.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.NoblePerson;
import cn.sx.decentworld.bean.RecommendUnavailable;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.config.ConstantConfig;
import cn.sx.decentworld.entity.dao.SelfExtraInfoDao;
import cn.sx.decentworld.entity.db.SelfExtraInfo;
import cn.sx.decentworld.listener.NetCallback;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.manager.SelfInfoManager;
import cn.sx.decentworld.manager.SelfExtraInfoManager;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.AES;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;

/**
 * @ClassName: IncomeEngine.java
 * @Description: 收益业务类
 * @author: cj
 * @date: 2016年3月16日 下午4:31:29
 */
public class BenefitEngine
{
    private static final String TAG = "BenefitEngine";
    private String userID = "";
    private SendUrl sendUrl;

    /**
     * 初始化函数
     */
    public BenefitEngine()
    {
        userID = DecentWorldApp.getInstance().getDwID();
        sendUrl = new SendUrl(DecentWorldApp.getInstance().getApplicationContext());
    }
    
    /**
     * 推荐新用户
     * @param phoneNum
     * @param amount
     */
    public void recommendUser(String phoneNum, String amount, NetCallback listener)
    {
        recommendUserE(phoneNum, amount, listener);
    }
    

    /**
     * 获取现金收益账号
     * 
     * @param listener
     */
    public void getCashBenefitAccount(GetCashBenefitAccountListener listener)
    {
        getCashBenefitAccountE(listener);
    }

    public interface GetCashBenefitAccountListener
    {
        void onSuccess(int type, String account);

        void onFailure(String cause);
    }

    /**
     * 验证用户是否曾经设置过提现密码
     * 
     * @param listener
     */
    public void isSetBenefitPwd(final IsSetBenefitListener listener)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        LogUtils.v(TAG, "isSetBenefitPwd() params:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_HAS_INCOME_PWD, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.v(TAG, "isSetBenefitPwd() resultJSON:" + resultJSON);
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "isSetBenefitPwd() success");
                    String str = resultBean.getData().toString();
                    JSONObject jsonObject = JSON.parseObject(str);
                    boolean isSet = jsonObject.getBooleanValue("init");
                    listener.onSuccess(isSet);
                }
                else if (resultBean.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "isSetBenefitPwd() failure");
                    listener.onFailure("请求出错");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "isSetIncomePwd...onFailure");
                listener.onFailure(Constants.NET_WRONG);
            }
        });

    }

    /**
     * 验证用户是否曾经设置过提现密码的回调监听
     */
    public interface IsSetBenefitListener
    {
        public void onSuccess(boolean isSet);

        public void onFailure(String cause);
    }

    /**
     * 设置虚拟币提现的提取密码
     * 
     * @param strangerID
     *            用户ID
     * @param password
     *            密码
     * @param handler
     *            回调
     * @param requestCode
     *            请求码
     */
    public void setBenefitPwd(String password, final NetCallback listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 1:
                        listener.onSuccess(msg.obj.toString());
                        break;
                    default:
                        break;
                }
            };
        };

        String dwID = DecentWorldApp.getInstance().getDwID();
        password = AES.encodeWithRandomStr(password);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", dwID);
        map.put("password", password);
        LogUtils.v(TAG, "setBenefitPwd() params:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_DRAW_CASH_SET_PWD, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.v(TAG, "setBenefitPwd() onSuccess,resultJSON:" + resultJSON);
                if (resultBean.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "setBenefitPwd() success");
                    sendHandlerMsg(handler, 1, "设置密码成功");
                }
                else if (resultBean.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "setBenefitPwd() failure");
                    sendHandlerMsg(handler, 0, resultBean.getMsg());
                }
                else
                {
                    LogUtils.e(TAG, "setBenefitPwd...未知返回码");
                    sendHandlerMsg(handler, 0, resultBean.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "setBenefitPwd() onFailure,cause by:" + e);
                sendHandlerMsg(handler, 0, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 设置是否自动转账
     * 
     * @param currentStatus
     * @param password
     * @param listener
     */
    public void setAutoTransfer(boolean currentStatus, String tempToken, final NetCallback listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 1:
                        listener.onSuccess("设置成功");
                        break;
                    default:
                        break;
                }
            };
        };
        HashMap<String, String> map = new HashMap<String, String>();
        String userID = DecentWorldApp.getInstance().getDwID();
        map.put("dwID", userID);
        if (currentStatus)
            map.put("auto", "false");
        else
            map.put("auto", "true");
        map.put("tempToken", tempToken);
        LogUtils.v(TAG, "setAutoTransfer...begin:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_SET_AUTO_TRANSFER, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "setAutoTransfer...begin,response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "setAutoTransfer...success");
                    sendHandlerMsg(handler, 1, "");
                }
                if (msg.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "setAutoTransfer...failure,cause by:" + msg.getMsg());
                    sendHandlerMsg(handler, 0, msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "setAutoTransfer...onFailure,cause by:" + e);
                sendHandlerMsg(handler, 0, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 设置虚拟币提现账号
     * 
     * @param bandCard
     * @param tempToken
     * @param listener
     */
    public void setBankCard(String bankCard, String bankName, String tempToken, final NetCallback listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 1:
                        listener.onSuccess("设置成功");
                        break;
                    default:
                        break;
                }
            };
        };

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        map.put("bankCard", bankCard);
        map.put("bankName", bankName);
        map.put("tempToken", tempToken);
        LogUtils.v(TAG, "setBankCard...begin:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_DRAW_CASH_SET_ACCOUNT, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "setBankCard...begin,response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "setBankCard...success");
                    sendHandlerMsg(handler, 1, "");
                }
                if (msg.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "setBankCard...failure,cause by:" + msg.getMsg());
                    sendHandlerMsg(handler, 0, "设置失败");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "setBankCard...onFailure,cause by:" + e);
                sendHandlerMsg(handler, 0, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 虚拟币提现
     */
    public void withdrawWealth(String amount, String tempToken, final NetCallback listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 1:
                        listener.onSuccess("设置成功");
                        break;
                    default:
                        break;
                }
            };
        };
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        map.put("amount", amount);
        map.put("tempToken", tempToken);
        LogUtils.v(TAG, "withdrawWealth...begin:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_DRAW_CASH, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "withdrawWealth...begin,response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "withdrawWealth...success");
                    JSONObject jsonObject = JSON.parseObject(msg.getData().toString());
                    String wealth = jsonObject.getString("wealth");
                    SelfInfoManager.getInstance().notifyWealthChanged(Float.valueOf(wealth));
                    sendHandlerMsg(handler, 1, "");
                }
                if (msg.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "withdrawWealth...failure,cause by:" + msg.getMsg());
                    sendHandlerMsg(handler, 0, msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "withdrawWealth...onFailure,cause by:" + e);
                sendHandlerMsg(handler, 0, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 是否满足提现的要求
     * 
     * @param currentAmount
     * @return
     */
    public boolean isEnoughWithDrawCash(float currentAmount)
    {
        if (currentAmount > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 提取推荐的收益
     * 
     * @param userID
     * @param pwd
     */
    public void drawCash(String tempToken, final DrawCashListener listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 1:
                        listener.onSuccess();
                        break;
                    default:
                        break;
                }
            };
        };
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        map.put("tempToken", tempToken);
        LogUtils.v(TAG, "extraRecomBenefit...begin:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_DRAW_RECOMMEND_BENEFIT, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {

                LogUtils.v(TAG, "extraRecomBenefit...begin,response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "extraRecomBenefit...success");
                    sendHandlerMsg(handler, 1, msg.getMsg());
                }
                else if (msg.getResultCode() == 3333)
                {
                    /** 没有贵人 **/
                    LogUtils.i(TAG, "extraRecomBenefit...failure,cause by:" + msg.getMsg());
                    sendHandlerMsg(handler, 0, msg.getMsg());
                }
                else
                {
                    /** 有推荐人，提现金额不足 **/
                    LogUtils.w(TAG, "extraRecomBenefit...failure,cause by:不知名的结果码");
                    sendHandlerMsg(handler, 0, msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "extraRecomBenefit...onFailure,cause by:" + e.toString());
                sendHandlerMsg(handler, 0, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 提现回调监听
     */
    public interface DrawCashListener
    {
        public void onSuccess();

        public void onFailure(String cause);
    }

    // ///////////////////////////////////////提取推荐的收益...end////////////////////////////////////////////////////

    // ///////////////////////////////////////设置收益账号...begin////////////////////////////////////////////////////

    /**
     * 设置用户提现的账号（支付宝或微信） <<<<<<< .mine
     * 
     * @param accountType
     *            账号类型（支付宝 0 | 微信 1）
     * @param account
     *            账号
     * @param tempToken
     *            临时令牌
     * @param listener
     *            =======
     * 
     * @param strangerID
     *            自己的id
     * @param accountType
     *            账号类型（支付宝 0 | 微信 1）
     * @param account
     *            账号
     * @param activity
     *            >>>>>>> .r1454
     */
    public void setPaycardAccount(final int accountType, final String account, String tempToken, final SetPaycardAccountLitener listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 1:
                        listener.onSuccess(accountType, account);
                        break;
                    default:
                        break;
                }
            };
        };

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        map.put("accountType", String.valueOf(accountType));
        map.put("account", account);
        map.put("tempToken", tempToken);
        LogUtils.v(TAG, "setPaycardAccount...begin:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_SET_ACCOUNT, Method.POST, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                // 设置成功 设置失败（原因）

                LogUtils.v(TAG, "setPaycardAccount...begin,response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "setPaycardAccount...success");
                    sendHandlerMsg(handler, 1, "");
                }
                else if (msg.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "setPaycardAccount...failure,case by:" + msg.getMsg());
                    sendHandlerMsg(handler, 0, msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "setPaycardAccount...onFailure,cause by:" + e);
                sendHandlerMsg(handler, 0, Constants.NET_WRONG);
            }
        });
    }

    public interface SetPaycardAccountLitener
    {
        public void onSuccess(int accountType, String account);

        public void onFailure(String cause);
    }

    // ///////////////////////////////////////设置收益账号...end////////////////////////////////////////////////////

    /**
     * 获取谁是我的贵人
     * 
     * @param userID
     * @param getMyGrCallback
     */
    public void getMyGr(String userID, GetMyGrCallback getMyGrCallback)
    {
        getMyGrE(userID, getMyGrCallback);
    }

    /** 获取贵人的回调接口 **/
    public interface GetMyGrCallback
    {
        // 成功，返回 贵人的ID
        public void onSuccess(String dwID);

        // 失败，返回失败的原因
        public void onFailure(String cause);
    }

    /**
     * 获取我是谁的贵人的列表
     * 
     * @param userID
     *            用户ID
     * @param getMyProtegesCallback
     *            回调
     */
    public void getMyProteges(GetMyProtegesCallback getMyProtegesCallback)
    {
        getMyProtegesE(getMyProtegesCallback);
    }

    public interface GetMyProtegesCallback
    {
        // 成功，返回 贵人的ID
        public void onSuccess(float totalBenefit, List<NoblePerson> noblePersons);

        // 失败，返回失败的原因
        public void onFailure(String cause);
    }

    /**
     * 设置谁是我的贵人 可以实现 1.设置一个新的贵人 2.修改贵人 3.删除贵人
     * 
     * @param json
     */
    public void setGr(final String grID, SetGrCallback setGrCallback)
    {
        setGrE(grID, setGrCallback);
    }

    public interface SetGrCallback
    {
        // 成功，返回 贵人的ID
        public void onSuccess(String grID);

        // 失败，返回失败的原因
        public void onFailure(String cause);
    }

    /**
     * 获取可不用的推荐列表
     * 
     * @param listener
     */
    public void getRecommendUnavailableList(GetRecommendUnavailableListListener listener)
    {
        getRecommendUnavailableListE(listener);
    }

    /**
     * 获取推荐不可用的列表
     */
    public interface GetRecommendUnavailableListListener
    {
        // 获取成功，返回有效数据
        void onSuccess(List<RecommendUnavailable> unavaliable);

        // 获取列表失败，返回失败原因
        void onFailure(String cause);

        // 暂时没有推荐列表
        void onNoList(String info);
    }


    // /////////////////////////////////////////私有方法////////////////////////////////////////////
    /**
     * 推荐新用户
     */
    private void recommendUserE(String phoneNum, String amount, final NetCallback listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                String info = msg.obj.toString();
                switch (msg.what)
                {
                    case -1:
                        listener.onFailure(info);
                        break;
                    case 0:
                        listener.onSuccess(info);
                        break;
                    case 1:
                        listener.onFailure(info);
                        break;
                    default:
                        break;
                }
            };
        };
        String userID = DecentWorldApp.getInstance().getDwID();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        map.put("phoneNum", phoneNum);
        map.put("amount", amount);
        LogUtils.v(TAG, "recommendUserE() params:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_RECOMMEND_V13, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "recommendUserE() response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "recommendUserE() success");
                    String data = msg.getData().toString();
                    if(CommUtil.isNotBlank(data))
                    {
                        JSONObject object = JSON.parseObject(data);
                        String wealth = object.getString("wealth");
                        SelfInfoManager.getInstance().notifyWealthChanged(Float.valueOf(wealth));
                    }
                    sendHandlerMsg(handler, 0, "推荐成功");
                }
                else if (msg.getResultCode() == 3333)
                {
                    sendHandlerMsg(handler,1, msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "recommendUserE() onFailure,caused by:" + e);
                sendHandlerMsg(handler, -1, Constants.NET_WRONG);
            }
        });
    }
    
    /**
     * 获取现金收益账号
     */
    private void getCashBenefitAccountE(final GetCashBenefitAccountListener listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case -1:
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 0:
                        String result = msg.obj.toString();
                        JSONObject object = JSON.parseObject(result);
                        int type = 0;
                        String t = object.getString("type");
                        if(t.equals(""))
                        {
                            type = ConstantConfig.PayMethod.NO.getIndex();
                        }
                        else if(t.equals("ALIPAY"))
                        {
                            type = ConstantConfig.PayMethod.ALIPAY.getIndex();
                        }else
                        {
                            type = ConstantConfig.PayMethod.WXPAY.getIndex();
                        }
                        String account = object.getString("account");
                        listener.onSuccess(type, account);
                        break;
                    case 1:
                        listener.onFailure(msg.obj.toString());
                        break;
                    default:
                        break;
                }
            };
        };
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        LogUtils.v(TAG, "getCashBenefitAccountE() params:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_GET_CASH_BEBEFIT_ACCOUNT, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "getCashBenefitAccountE() response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "getCashBenefitAccountE() success");
                    sendHandlerMsg(handler, 0, msg.getData().toString());
                }
                else if (msg.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "getCashBenefitAccountE() failure,cause by:" + msg.getMsg());
                    sendHandlerMsg(handler, 1, "获取失败");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "getCashBenefitAccountE() onFailure,cause by:" + e);
                sendHandlerMsg(handler, -1, Constants.NET_WRONG);
            }
        });

    }

    
    

    /**
     * @param userID
     * @param getMyGrCallback
     */
    private void getMyGrE(String userID, final GetMyGrCallback getMyGrCallback)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        getMyGrCallback.onFailure(msg.obj.toString());
                        break;
                    case 1:
                        /** 清除数据库 **/
                        NoblePerson.deleteToMe();
                        String resultStr = msg.obj.toString();
                        /** 解析数据并保存 **/
                        JSONObject jsonObject = JSON.parseObject(resultStr);
                        JSONObject object = jsonObject.getJSONObject("userInfo");
                        NoblePerson myProtege = new NoblePerson(NoblePerson.GR_TO_ME);
                        myProtege.setGender(object.getString("gender"));
                        myProtege.setOtherID(object.getString("id"));
                        myProtege.setOccupation(object.getString("occupation"));
                        myProtege.setShowName(object.getString("showName"));
                        myProtege.setUserType(object.getIntValue("userType"));
                        myProtege.setWorth(object.getFloatValue("worth"));
                        myProtege.save();
                        getMyGrCallback.onSuccess(object.getString("id"));
                        break;
                    default:
                        break;
                }
            };
        };
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        LogUtils.v(TAG, "getMyGr...begin:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_MY_GR, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "getMyGr...begin,response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "getMyGr...success");
                    sendHandlerMsg(handler, 1, msg.getData().toString());
                }
                else if (msg.getResultCode() == 3333)
                {
                    /** 没有贵人 **/
                    LogUtils.i(TAG, "getMyGr...failure,cause by:" + msg.getMsg());
                    sendHandlerMsg(handler, 0, "没有贵人");
                }
                else
                {
                    LogUtils.w(TAG, "getMyGr...failure,cause by:不知名的结果码");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "getMyGr...onFailure,cause by:" + e);
                sendHandlerMsg(handler, 0, Constants.NET_WRONG);
            }
        });

    }

    /**
     * @param userID2
     */
    private void getMyProtegesE(final GetMyProtegesCallback getMyProtegesCallback)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        getMyProtegesCallback.onFailure(msg.obj.toString());
                        break;
                    case 1:
                        /** 解析Json数据 **/
                        String json_result = msg.obj.toString();
                        JSONObject jsonObject = JSON.parseObject(json_result);
                        // 我作为别人的贵人获得的总收益
                        float grTotalBenefit = jsonObject.getFloatValue("totalBenefit");
                        SelfExtraInfo extraInfo = SelfExtraInfoManager.getInstance().getEntity();
                        extraInfo.setGrTotalBenefit(grTotalBenefit);
                        extraInfo.save();
                        //清除历史数据 
                        NoblePerson.deleteToOther();
                        String result = jsonObject.getString("result");
                        JSONArray array = JSON.parseArray(result);
                        List<NoblePerson> temp = new ArrayList<NoblePerson>();
                        if (array.size() > 0)
                        {
                            for (int i = 0; i < array.size(); i++)
                            {
                                JSONObject object = array.getJSONObject(i);
                                NoblePerson myProtege = new NoblePerson(NoblePerson.GR_TO_OTHER);
                                myProtege.setGender(object.getString("gender"));
                                myProtege.setOtherID(object.getString("id"));
                                myProtege.setOccupation(object.getString("occupation"));
                                myProtege.setShowName(object.getString("showName"));
                                myProtege.setUserType(object.getIntValue("userType"));
                                myProtege.setWorth(object.getFloatValue("worth"));
                                myProtege.save();
                                temp.add(myProtege);
                            }
                        }
                        getMyProtegesCallback.onSuccess(grTotalBenefit, temp);
                        break;
                    default:
                        break;
                }
            };
        };
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        LogUtils.v(TAG, "getMyProteges...begin:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_GET_GR_LIST_TO_OTHER, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "getMyProteges...begin,response:" + response);
                if (msg.getResultCode() == 2222)// 获取成功
                {
                    LogUtils.v(TAG, "getMyProteges...我是 " + msg.getMsg() + " 个人的贵人。");
                    sendHandlerMsg(handler, 1, msg.getData().toString());
                }
                if (msg.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "getMyProteges...failure,cause by:" + msg.getMsg());
                    sendHandlerMsg(handler, 0, "获取贵人列表失败");
                }
            }

            @Override
            public void onFailure(String error)
            {
                LogUtils.e(TAG, "getMyProteges...onFailure,cause by:" + error);
                sendHandlerMsg(handler, 0, Constants.NET_WRONG);
            }
        });

    }

    /**
     * @param strangerID
     * @param grID
     */
    private void setGrE(final String grID, final SetGrCallback setGrCallback)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        setGrCallback.onFailure(msg.obj.toString());
                        break;
                    case 1:
                        setGrCallback.onSuccess(grID);
                        break;
                    default:
                        break;
                }
            };
        };
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        map.put("grID", grID);
        LogUtils.v(TAG, "setUserGr...begin:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_SET_GR_TO_ME, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "setUserGr...begin,response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    LogUtils.v(TAG, "setUserGr...success");// 成功
                    // 更新身家
                    String jsonStr = msg.getData().toString();
                    JSONObject jsonObject = JSON.parseObject(jsonStr);
                    Float wealth = jsonObject.getFloat("wealth");
                    SelfInfoManager.getInstance().notifyWealthChanged(wealth);
                    // 通知
                    sendHandlerMsg(handler, 1, grID);
                }
                if (msg.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "setUserGr...failure,causer by:" + msg.getMsg());// 失败
                    sendHandlerMsg(handler, 0, msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "setUserGr...onFailure,causer by:" + e);
                sendHandlerMsg(handler, 0, Constants.NET_WRONG);
            }
        });
    }

    /**
     * 发送通知
     * 
     * @param handler
     * @param what
     * @param object
     */
    private void sendHandlerMsg(Handler handler, int what, Object object)
    {
        Message message = Message.obtain();
        message.what = what;
        message.obj = object;
        handler.sendMessage(message);
    }

    /**
     * 从网络获取已经推荐的列表
     */
    private void getRecommendUnavailableListE(final GetRecommendUnavailableListListener listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {

                switch (msg.what)
                {
                    case -1:
                        // 获取失败
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 0:
                        // 获取成功
                        List<RecommendUnavailable> temp = new ArrayList<RecommendUnavailable>();
                        String jsonStr = msg.obj.toString();
                        JSONObject object = JSON.parseObject(jsonStr);
                        String jsonArray = object.getString("UnavaliableList");
                        JSONArray array = JSON.parseArray(jsonArray);
                        if (array.size() > 0)
                        {
                            for (int i = 0; i < array.size(); i++)
                            {
                                object = array.getJSONObject(i);
                                RecommendUnavailable unavaliable = new RecommendUnavailable();
                                unavaliable.setPhoneNum(object.getString("phoneNum"));
                                unavaliable.setAmount(object.getFloatValue("amount"));
                                unavaliable.setStatus(object.getIntValue("status"));
                                temp.add(unavaliable);
                            }
                            listener.onSuccess(temp);
                        }
                        else
                        {
                            listener.onNoList("暂无推荐列表");
                        }

                        break;
                    case 1:
                        // 暂无推荐人
                        listener.onNoList("暂无推荐列表");
                        break;
                    default:
                        break;
                }
            };
        };
        String userID = DecentWorldApp.getInstance().getDwID();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        LogUtils.v(TAG, "getRecommendUnavaliable...begin:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_RECOMMEND_UNAVALIABLE, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {

                LogUtils.v(TAG, "getRecommendUnavaliable...begin,response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    // 获取成功
                    sendHandlerMsg(handler, 0, msg.getData().toString());
                }
                else if (msg.getResultCode() == 3333)
                {
                    // 暂无推荐
                    sendHandlerMsg(handler, 1, "暂无推荐人");
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "getRecommendUnavaliable...onFailure,cause by:" + e.toString());
                sendHandlerMsg(handler, -1, Constants.NET_WRONG);
            }
        });
    }

   
  

    // //////////////////////////////////////////共有方法//////////////////////////////

    /**
     * 获取推荐收益详细列表
     * 
     * @param otherID
     *            被推荐人的ID
     * @param listener
     *            回调监听
     */
    public void getRecommendBenefitDetail(String otherID, NetCallback listener)
    {
        getRecommendBenefitDetailE(otherID, listener);
    }

    // //////////////////////////////////////////私有方法//////////////////////////////

    /**
     * 推荐别人获得的收益列表详情
     * 
     * @param otherID
     * @param listener
     */
    private void getRecommendBenefitDetailE(String otherID, final NetCallback listener)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case -1:
                        listener.onFailure(msg.obj.toString());
                        break;
                    case 0:
                        listener.onSuccess(msg.obj.toString());
                        break;
                    case 1:
                        listener.onFailure(msg.obj.toString());
                        break;
                    default:
                        break;
                }
            };
        };
        String userID = DecentWorldApp.getInstance().getDwID();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID", userID);
        map.put("recommenderedID", otherID);
        LogUtils.v(TAG, "getRecommendBenefitDetail...begin:" + JSON.toJSONString(map));
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH + ConstantNet.API_RECOMMEND_BENEFIT_DETAIL, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String response, ResultBean msg)
            {
                LogUtils.v(TAG, "getRecommendBenefitDetail...begin,response:" + response);
                if (msg.getResultCode() == 2222)
                {
                    // list 包含key为 amount,time,status
                    LogUtils.v(TAG, "getRecommendBenefitDetail...success");
                    sendHandlerMsg(handler, 0, msg.getData().toString());
                }

                if (msg.getResultCode() == 3333)
                {
                    LogUtils.w(TAG, "getRecommendBenefitDetail...failure");
                    sendHandlerMsg(handler, 1, msg.getMsg());
                }
            }

            @Override
            public void onFailure(String e)
            {
                LogUtils.e(TAG, "getRecommendBenefitDetail...onFailure");
                sendHandlerMsg(handler, -1, Constants.NET_WRONG);
            }
        });
    }

}
