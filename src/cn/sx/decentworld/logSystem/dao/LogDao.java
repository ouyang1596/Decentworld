/**
 * 
 */
package cn.sx.decentworld.logSystem.dao;

import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.logSystem.entity.LogEntity;

/**
 * @ClassName: LogDao.java
 * @Description: 将日志文件保存到文件或者上传到服务器，目前为Bmob服务器
 * @author: cj
 * @date: 2016年4月18日 下午6:31:04
 */
public class LogDao
{
    /**
     * 创建verbose日志对象
     * 
     * @param tag
     * @param msg
     * @return
     */
    public static LogEntity saveVLog(String tag, String vLog)
    {
        LogEntity entity = new LogEntity();
        entity.setTag(tag);
        entity.setvLog(vLog);
        entity.save(DecentWorldApp.getGlobalContext());
        return entity;
    }

    /**
     * 创建调试日志对象
     * 
     * @param tag
     * @param msg
     * @return
     */
    public static LogEntity saveDLog(String tag, String dLog)
    {
        LogEntity entity = new LogEntity();
        entity.setTag(tag);
        entity.setdLog(dLog);
        entity.save(DecentWorldApp.getGlobalContext());
        return entity;
    }

    /**
     * 创建info日志对象
     * 
     * @param tag
     * @param msg
     * @return
     */
    public static LogEntity saveILog(String tag, String iLog)
    {
        LogEntity entity = new LogEntity();
        entity.setTag(tag);
        entity.setiLog(iLog);
        entity.save(DecentWorldApp.getGlobalContext());
        return entity;
    }

    /**
     * 创建警告日志对象
     * 
     * @param tag
     * @param msg
     * @return
     */
    public static LogEntity saveWLog(String tag, String wLog)
    {
        LogEntity entity = new LogEntity();
        entity.setTag(tag);
        entity.setwLog(wLog);
        entity.save(DecentWorldApp.getGlobalContext());
        return entity;
    }

    /**
     * 创建错误日志对象
     * 
     * @param tag
     * @param msg
     * @return
     */
    public static LogEntity saveELog(String tag, String eLog)
    {
        LogEntity entity = new LogEntity();
        entity.setTag(tag);
        entity.seteLog(eLog);
        entity.save(DecentWorldApp.getGlobalContext());
        return entity;
    }

}
