/**
 * 
 */
package cn.sx.decentworld.stateMachine;

import cn.sx.decentworld.entity.db.DWMessage;

/**
 * @ClassName: MsgProCallBack.java
 * @Description: 消息后处理回调接口
 * @author: cj
 * @date: 2016年1月6日 上午10:04:44
 */
public interface MsgHandleListener
{
    /** 线程处理完消息后回调状态 0 **/
    public void finished(DWMessage dwMessage);
    public void unFinished(DWMessage dwMessage);
}
