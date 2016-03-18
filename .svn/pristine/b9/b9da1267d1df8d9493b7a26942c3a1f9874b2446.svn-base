/**
 * 
 */
package cn.sx.decentworld.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.PraiseAdapter;
import cn.sx.decentworld.bean.Praise;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: PraiseActivity.java
 * @Description: 
 * @author: cj
 * @date: 2015年8月12日 下午5:48:55
 */
@EActivity(R.layout.activity_praise)
public class PraiseActivity extends BaseFragmentActivity
{
	private static final String TAG= "PraiseActivity";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;
	
	@ViewById(R.id.lv_praise)
	ListView lv_praise;
	
	private ArrayList<Praise> praises;
	private PraiseAdapter praiseAdapter;
	private int isPraiseOrTrample;
	
	@AfterViews
	public void init()
	{
		initData();
	}
	
	/**
	 * 初始化数据
	 */
	private void initData()
	{
		isPraiseOrTrample = getIntent().getIntExtra("isPraiseOrTrample", 1);
		
		if (isPraiseOrTrample == 1)
		{
			titleBar.setTitleBar("返回","赞");
			praises = (ArrayList<Praise>) getIntent().getSerializableExtra("praise");
		}
		if (isPraiseOrTrample == 2)
		{
			titleBar.setTitleBar("返回","踩");
			praises = (ArrayList<Praise>) getIntent().getSerializableExtra("trample");
		}

		praiseAdapter = new PraiseAdapter(PraiseActivity.this, praises,isPraiseOrTrample);
		lv_praise.setAdapter(praiseAdapter);
	}
	
	@Click(R.id.main_header_left)
	void back()
	{
		finish();
	}


}
