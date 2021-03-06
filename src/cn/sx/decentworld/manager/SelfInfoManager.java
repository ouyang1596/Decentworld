/**
 * 
 */
package cn.sx.decentworld.manager;

import java.util.List;

import org.simple.eventbus.EventBus;

import android.view.View;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.bean.UserInfoField.Field;
import cn.sx.decentworld.common.CommUtil;
import cn.sx.decentworld.common.NotifyByEventBus;
import cn.sx.decentworld.entity.SelfUserField;
import cn.sx.decentworld.entity.SelfUserInfo;
import cn.sx.decentworld.entity.dao.SelfInfoDao;
import cn.sx.decentworld.entity.db.ContactUser.UserType;
import cn.sx.decentworld.entity.db.SelfInfo;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.utils.JsonUtils;
import cn.sx.decentworld.utils.sputils.UserInfoHelper;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName: UserInfoEngine.java
 * @Description: 个人信息管理模块（加载、修改、上传）、其它业务信息不能放到这个类中
 * @author: cj
 * @date: 2016年3月15日 下午3:31:03
 */
public class SelfInfoManager
{
    private static final String TAG = "SelfInfoManager";
    private static SelfInfoManager instance;
    // 当前用户信息,内存数据
    private SelfUserInfo mSelfUserInfo;
    // 数据库数据
    private SelfInfo entity;

    /**
     * 获取实例
     * 
     * @return
     */
    public static SelfInfoManager getInstance()
    {
        if (instance == null)
            synchronized (SelfInfoManager.class)
            {
                if (instance == null)
                    instance = new SelfInfoManager();
            }
        return instance;
    }

    /**
     * 私有构造函数
     */
    private SelfInfoManager()
    {
        init();
    }

    /**
     * 初始化数据
     */
    private void init()
    {
        LogUtils.d(TAG, "initSelfUserInfo()");
        entity = SelfInfoDao.query();
        if (entity != null)
        {
            String content = entity.getContent();
            if (CommUtil.isNotBlank(content))
            {
                mSelfUserInfo = JSON.parseObject(content, SelfUserInfo.class);
                List<SelfUserField> fields = mSelfUserInfo.getFields();
                for (SelfUserField field : fields)
                    field.setModify(false);
            }
            else
            {
                mSelfUserInfo = new SelfUserInfo();
            }
        }
        else
        {
            entity = new SelfInfo();
            entity.save();
            mSelfUserInfo = new SelfUserInfo();
        }
    }

    // ////////////////////////////////////////////初始化数据//////////////////////////////////////////
    /**
     * 重新加载个人信息
     */
    public void reLoad()
    {
        LogUtils.d(TAG, "reLoad()");
        entity = SelfInfoDao.query();
        if (entity != null)
        {
            String content = entity.getContent();
            mSelfUserInfo = JSON.parseObject(content, SelfUserInfo.class);
            List<SelfUserField> fields = mSelfUserInfo.getFields();
            for (SelfUserField field : fields)
                field.setModify(false);
        }
    }

    /**
     * 当用户手动退出软件后，调用该函数清理内存中的数据
     */
    public static void clear()
    {
        instance = null;
    }

    // ////////////////////////////////////////对方公开的方法////////////////////////////////

    /**
     * 保存个人信息到数据库
     */
    public void saveSelfUserInfo(String versionNum)
    {
        // 用户信息版本号
        UserInfoHelper.setAllUserInfoVersion(versionNum);
        // 修改版本号
        getSelfUserInfo().setVersionNum(versionNum);
        if (entity != null)
        {
            entity.setVersionNum(versionNum);
            String content = JSON.toJSONString(mSelfUserInfo);
            entity.setContent(content);
            entity.save();
        }
    }

    /**
     * 获取个人信息
     */
    public SelfUserInfo getSelfUserInfo()
    {
        return mSelfUserInfo;
    }

    /**
     * 获取用户类型
     */
    public int getUserTypeInt()
    {
        SelfUserField userTypeField = getSelfUserInfo().getSelfUserField(Field.USER_TYPE);
        if (userTypeField != null)
        {
            String fieldValue = userTypeField.getFieldValue();
            if (CommUtil.isNotBlank(fieldValue))
            {
                return Integer.valueOf(fieldValue);
            }
        }
        return UserType.IMPEACH.getIndex();
    }

    /**
     * 获取用户类型
     */
    public String getUserTypeString()
    {
        SelfUserField userTypeField = getSelfUserInfo().getSelfUserField(Field.USER_TYPE);
        if (userTypeField != null)
        {
            String fieldValue = userTypeField.getFieldValue();
            if (CommUtil.isNotBlank(fieldValue))
            {
                return fieldValue;
            }
        }
        return "";
    }

    /**
     * 获取显示的名字，按优先级顺序显示，实名、昵称
     * 
     * @return 名字
     */
    public String getShowName()
    {
        /** 显示实名 **/
        SelfUserField realNameField = getSelfUserInfo().getSelfUserField(Field.REALNAME);
        if (realNameField != null)
        {
            boolean displayAuth = realNameField.isDisplayAuth();
            if (displayAuth)
            {
                String fieldValue = realNameField.getFieldValue();
                if (CommUtil.isNotBlank(fieldValue))
                {
                    return fieldValue;
                }
            }
        }

        /** 显示昵称 **/
        SelfUserField nickNameField = getSelfUserInfo().getSelfUserField(Field.NICKNAME);
        if (nickNameField != null)
        {
            boolean displayAuth = nickNameField.isDisplayAuth();
            if (displayAuth)
            {
                String fieldValue = nickNameField.getFieldValue();
                if (CommUtil.isNotBlank(fieldValue))
                {
                    return fieldValue;
                }
            }
        }
        return "";
    }

    /**
     * 获取实名
     */
    public String getRealName()
    {
        SelfUserField realNameField = getSelfUserInfo().getSelfUserField(Field.REALNAME);
        if (realNameField != null)
        {
            String fieldValue = realNameField.getFieldValue();
            if (CommUtil.isNotBlank(fieldValue))
            {
                return fieldValue;
            }
        }
        return "";
    }

    /**
     * 获取银行卡
     */
    public String getBankCard()
    {
        SelfUserField bankCardField = getSelfUserInfo().getSelfUserField(Field.BANKCARD);
        if (bankCardField != null)
        {
            String fieldValue = bankCardField.getFieldValue();
            if (CommUtil.isNotBlank(fieldValue))
            {
                return fieldValue;
            }
        }
        return "";
    }

    /**
     * 获取座右铭 因为此时单例中的变量没有初始化完毕，所以在获取座右铭时，直接获取；
     */
    public String getMotto()
    {
        SelfInfo entity = SelfInfoDao.query();
        if (entity != null)
        {
            String content = entity.getContent();
            SelfUserInfo mSelfUserInfo = JSON.parseObject(content, SelfUserInfo.class);
            if (mSelfUserInfo != null)
            {
                String fieldValue = mSelfUserInfo.getFieldValue(Field.MOTTO);
                if (CommUtil.isNotBlank(fieldValue))
                    return fieldValue;
            }
        }
        return "";
    }

    /**
     * 获取一句话简介
     * 
     * @return ""(没有)
     */
    public String getShortIntroduce()
    {
        SelfUserField shortIntroduceField = getSelfUserInfo().getSelfUserField(Field.SHORT_INTRODUCE);
        if (shortIntroduceField != null)
        {
            String fieldValue = shortIntroduceField.getFieldValue();
            if (CommUtil.isNotBlank(fieldValue))
                return fieldValue;
        }
        return "";
    }

    /**
     * 获取人物志
     * 
     * @return ""(没有)
     */
    public String getIntroduce()
    {
        SelfUserField introduceField = getSelfUserInfo().getSelfUserField(Field.INTRODUCE);
        if (introduceField != null)
        {
            String fieldValue = introduceField.getFieldValue();
            if (CommUtil.isNotBlank(fieldValue))
                return fieldValue;
        }
        return "";
    }

    /**
     * 获取身家
     * 
     * @return "0.0"(没有)
     */
    public String getWealth()
    {
        SelfUserField wealthField = getSelfUserInfo().getSelfUserField(Field.WEALTH);
        if (wealthField != null)
        {
            String fieldValue = wealthField.getFieldValue();
            if (CommUtil.isNotBlank(fieldValue))
                return fieldValue;
        }
        return "0.0";
    }

    
    /**
     * 获取个性签名
     * 
     * @return ""(没有)
     */
    public String getSign()
    {
        SelfUserField signField = getSelfUserInfo().getSelfUserField(Field.SIGN);
        if (signField != null)
        {
            String fieldValue = signField.getFieldValue();
            if (CommUtil.isNotBlank(fieldValue))
                return fieldValue;
        }
        return "未设置个性签名";
    }

    /**
     * 保存"人物志"
     * 
     * @param introduce
     *            人物志
     * @param shortIntroduce
     *            一句话简介
     * @param versionNum
     *            新的版本号
     */
    public void saveIntroduce(String introduce, String shortIntroduce, String versionNum)
    {
        LogUtils.d(TAG, "setIntroduce() params[introduce=" + introduce + ",shortIntroduce=" + shortIntroduce + ",versionNum=" + versionNum + "]");
        // 设置一句话简介
        SelfUserField shortIntroduceField = getSelfUserInfo().getSelfUserField(Field.SHORT_INTRODUCE);
        if (shortIntroduceField != null)
            shortIntroduceField.setFieldValue(shortIntroduce);
        // 设置人物志
        SelfUserField introduceField = getSelfUserInfo().getSelfUserField(Field.INTRODUCE);
        if (introduceField != null)
            introduceField.setFieldValue(introduce);
        if (entity != null)
        {
            if (!versionNum.equals("-1"))
                entity.setVersionNum(versionNum);
            String content = JSON.toJSONString(mSelfUserInfo);
            entity.setContent(content);
            entity.save();
        }
    }

    /**
     * 改变实名后
     * 
     * @param realname
     *            实名
     * @param wealth
     *            最新身家
     * @param versionNum
     *            最新版本号
     */
    public void afterRealnameChanged(String realname, float wealth, String versionNum)
    {
        modifyRealname(realname);
        notifyWealthChanged(wealth);
        saveSelfUserInfo(versionNum);
    }

    /**
     * 将最新身价保存到内存和数据库中，并广播界面更新数据
     * 
     * @param worth
     *            最新身价
     */
    public void notifyWorthChanged(float worth)
    {
        if (worth != -1)
        {
            SelfUserField selfUserField = getSelfUserInfo().getSelfUserField(Field.WORTH);
            if (selfUserField != null)
                selfUserField.setFieldValue(String.valueOf(worth));
            if (entity != null)
            {
                String content = JSON.toJSONString(mSelfUserInfo);
                entity.setContent(content);
                entity.save();
            }
            EventBus.getDefault().post(String.valueOf(worth), NotifyByEventBus.NT_NOTIFY_WORTH_CHANGED);
            LogUtils.v(TAG, "notifyWorthChanged() params[worth=" + worth + ",info=" + "modify finished]");
        }
        else
        {
            LogUtils.v(TAG, "notifyWorthChanged() params[worth=" + worth + ",info=" + "not modify]");
        }
    }

    /**
     * 将最新身家保存到内存和数据库中，并广播界面更新数据
     * 
     * @param wealth
     *            最新身家
     */
    public void notifyWealthChanged(float wealth)
    {
        if (wealth >= 0)
        {
            SelfUserField selfUserField = getSelfUserInfo().getSelfUserField(Field.WEALTH);
            if (selfUserField != null)
                selfUserField.setFieldValue(String.valueOf(wealth));
            if (entity != null)
            {
                String content = JSON.toJSONString(mSelfUserInfo);
                entity.setContent(content);
                entity.save();
            }
            // 发送身家改变的广播通知，其它界面如果有注册，则会收到消息
            EventBus.getDefault().post(String.valueOf(wealth), NotifyByEventBus.NT_NOTIFY_WEALTH_CHANGED);
            LogUtils.v(TAG, "notifyWealthChanged() params[wealth=" + wealth + ",info=" + "modify finished]");
        }
        else
        {
            LogUtils.v(TAG, "notifyWealthChanged() params[wealth=" + wealth + ",info=" + "not modify]");
        }
    }

    // ////////////////////////////////////////私有方法////////////////////////////////

    /**
     * 将最新实名保存到内存中
     * @param realname 实名
     */
    private void modifyRealname(String realname)
    {
        if (CommUtil.isNotBlank(realname))
        {
            SelfUserField realnameField = getSelfUserInfo().getSelfUserField(Field.REALNAME);
            if (realnameField != null)
                realnameField.setFieldValue(realname);
        }
    }
}
