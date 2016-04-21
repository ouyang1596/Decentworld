/**
 * 
 */
package cn.sx.decentworld.listener;

import cn.sx.decentworld.entity.db.CommentEntity;

/**
 * @ClassName: CommentCallback.java
 * @Description: 评论回调
 * @author: cj
 * @date: 2016年4月15日 上午11:15:46
 */
public interface CommentCallback
{
    void onSuccess(String info,CommentEntity commentEntity);
    void onFailure(String cause,CommentEntity commentEntity);
}
