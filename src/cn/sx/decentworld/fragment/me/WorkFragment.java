/**
 * 
 */
package cn.sx.decentworld.fragment.me;

import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.MomentAdapter;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.fragment.BaseFragment;
import cn.sx.decentworld.widget.ListViewForScrollView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: WorkFragment.java
 * @Description: 展示自己的作品圈
 * @author: cj
 * @date: 2016年1月18日 下午7:52:56
 */
@EFragment(R.layout.fragment_me_work)
public class WorkFragment extends BaseFragment
{
    /**
     * 常量
     */
    private static final String TAG = "WorkFragment";

    /**
     * 工具类
     */
    @Bean
    ToastComponent toast;

    /**
     * 界面资源
     */
    @ViewById(R.id.lv_work_circle)
    ListViewForScrollView lv_work_circle;

    /**
     * 变量
     */
    private boolean isPrepared = false;
    public MomentAdapter workAdapter;


    /**
     * 入口
     */
    @AfterViews
    void init()
    {

        isPrepared = true;
        lazyLoad();
    }

    /**
     * 数据延迟加载
     */
    @Override
    protected void lazyLoad()
    {
        /** 初始化作品圈的数据 **/

    }

    @Override
    public void turnToTab(int tab)
    {
        // TODO Auto-generated method stub

    }

    public void initData()
    {

    }

}
