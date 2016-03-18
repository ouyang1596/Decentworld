/**
 * 
 */
package cn.sx.decentworld.chat.interfaces;

/**
 * @ClassName: SendMsgListener.java
 * @Description: 发送文件监听的接口
 * @author: cj
 * @date: 2016年3月7日 下午9:38:09
 */
public interface SendFileListener
{
    //没有连接到openfire服务器
    public void onNotConnected();
    
    // 开始
    public void onStart();

    // 成功
    public void onSuccess();

    // 失败
    public void onFailure(String packetID,String cause);

    //网络错误
    public void onNetWrong();

}
