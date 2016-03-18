/**
 * 
 */
package cn.sx.decentworld.chat.interfaces;

import cn.sx.decentworld.bean.DWMessage;

/**
 * @ClassName: ResendMsg.java
 * @Description: 重新发送消息的接口
 * @author: cj
 * @date: 2016年3月8日 下午3:44:35
 */
public interface ReSendMsgListener
{
    //重新发送文本消息
    public void onReSendText(DWMessage dwMessage);
    //重新发送语音消息
    public void onReSendVoice(DWMessage dwMessage);
    //重新发送图片消息
    public void onReSendPicture(DWMessage dwMessage);
    //重新发送名片消息
    public void onReSendCard(DWMessage dwMessage);
}
