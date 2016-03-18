/**
 * 
 */
package cn.sx.decentworld.listener;

/**
 * @ClassName: NetCallback.java
 * @Description: 网络请求回调监听
 * @author: cj
 * @date: 2016年3月17日 下午4:21:29
 */
public interface NetCallback
{
    public void onSuccess(String msg);
    public void onFailure(String cause);
}
