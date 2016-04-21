/**
 * 
 */
package cn.sx.decentworld.abstractclass;

import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * @ClassName: AbstractOnPageChangeListener.java
 * @Description: ViewPager中页面切换时的抽象类
 * @author: cj
 * @date: 2016年4月9日 下午3:35:52
 */
public class AbstractOnPageChangeListener implements OnPageChangeListener
{
    @Override
    public void onPageSelected(int pageIndex) {
        if (pageIndex == 0) {
            // StrangerFragment fragment = (StrangerFragment)
            // fragmentsList
            // .get(0);
            // fragment.initRequest();
        } else if (pageIndex == 1) {
            // if (DecentWorldApp.ifFromAppOwner) {
            // chat.switchScrollLayoutView(3);
            // DecentWorldApp.ifFromAppOwner = false;
            // }
        } else if (pageIndex == 2) {
//            if (DecentWorldApp.ifFixed) {
//                news.adapter.requestEnterChatRoom(DecentWorldApp.chatRoomInfo);
//            }
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // 当页面滑动过程中会一直调用
        // arg0 :当前页面，即你点击滑动的页面
        // arg1 :当前页面偏移的百分比
        // arg2 :当前页面偏移的像素
        // LogUtils.i(TAG, "onPageScrolled...arg0=" + arg0 + ",arg1="
        // + arg1 + ",arg2=" + arg2);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (arg0 == 0) {
            // LogUtils.i(TAG, "onPageScrollStateChanged...什么都没有做");
        } else if (arg0 == 1) {
            // LogUtils.i(TAG, "onPageScrollStateChanged...正在滑动");
        } else if (arg0 == 2) {
            // LogUtils.i(TAG, "onPageScrollStateChanged...滑动完毕");
        }
    }

}
