/**
 * 
 */
package cn.sx.decentworld.listener;

import java.util.List;

import cn.sx.decentworld.entity.db.MomentEntity;

/**
 * @ClassName: GetMomentEntityCallback.java
 * @Description: 获取朋友圈列表回调监听
 * @author: cj
 * @date: 2016年4月11日 下午2:23:10
 */
public interface GetMomentEntityListener
{
    void onSuccess(String info);
    void onFailure(String cause);
}
