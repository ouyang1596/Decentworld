/**
 * 
 */
package cn.sx.decentworld.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.manager.MsgNotifyManager;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: SettingVoice.java
 * @Description: 设置消息声音
 * @author: cj
 * @date: 2016年1月6日 上午9:26:35
 */
@EActivity(R.layout.activity_setting_voice)
public class SettingVoice extends BaseFragmentActivity implements OnClickListener
{
    private static final String TAG = "SettingVoice";
    @Bean 
    ToastComponent toast;
    @Bean
    TitleBar titleBar;
    
    @ViewById(R.id.main_header_left)
    LinearLayout main_header_left;
    
    /** 小孩声音 **/
    @ViewById(R.id.tv_child_voice)
    TextView tv_child_voice;
    
    /** 美女声音 **/
    @ViewById(R.id.tv_girl_voice)
    TextView tv_girl_voice;
    

    
    
    /**
     * 入口
     */
    @AfterViews
    void init()
    {
        titleBar.setTitleBar("返回", "设置声音");
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener()
    {
        main_header_left.setOnClickListener(this);
        tv_child_voice.setOnClickListener(this);
        tv_girl_voice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_child_voice:
                MsgNotifyManager.getInstance().setSingleVoice(R.raw.child_voice);
                break;
            case R.id.tv_girl_voice:
                MsgNotifyManager.getInstance().setSingleVoice(R.raw.girl_voice);
                break;
            case R.id.main_header_left:
                break;
            default:
                break;
        }
        finish();
    }
    
    
    


}
