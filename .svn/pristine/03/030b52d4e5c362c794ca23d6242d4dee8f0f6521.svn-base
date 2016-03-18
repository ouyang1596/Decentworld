/**
 * 
 */
package cn.sx.decentworld.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.FriendDetailActivity;
import cn.sx.decentworld.adapter.WorkAdapter;
import cn.sx.decentworld.bean.Comment;
import cn.sx.decentworld.bean.Praise;
import cn.sx.decentworld.bean.WorkBean;
import cn.sx.decentworld.common.Urls;
import cn.sx.decentworld.widget.ListViewForScrollView;

/**
 * @ClassName: WorkCircleFragment.java
 * @Description:展示自己的作品圈
 * @author: cj
 * @date: 2015年10月12日 上午10:50:37
 */
@EFragment(R.layout.fragment_work_circle)
public class WorkCircleFragment extends BaseFragment
{
	private Context context;
	private String nickName = "Jackchen" ;
	
	@ViewById(R.id.lv_work_circle)
	ListViewForScrollView lv_work_circle;
	public WorkAdapter adaper;
	public List<WorkBean> uu;
	public WorkBean bean;
	
	/**
	 * 依附所在的Activity
	 */
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		context = activity;
	}
	
	@AfterViews
	void init()
	{
		
		// 初始化作品圈的数据
		initData();
		adaper = new WorkAdapter(getActivity(), uu, false);
		lv_work_circle.setAdapter(adaper);
		
		//将初始化是否完成的标志位设为完成
		isPrepared = true;
	}
	
	public void initData()
	{
		/**
		 * 模拟数据 开始位置
		 */
		String nickname =  this.nickName;
		String time;

		ArrayList<Praise> praises;
		ArrayList<Praise> tramples;
		ArrayList<Comment> comments;

		// 时间数据
		SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日  HH:mm");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		time = formatter.format(curDate);

		// 照片数据
		List<ArrayList<String>> urls = new ArrayList<ArrayList<String>>();
		ArrayList<String> url_temp;
		for (int i = 0; i < 4; i++)
		{
			url_temp = new ArrayList<String>();
			for (int j = 0; j < 5; j++)
			{
				url_temp.add(Urls.URL_LIST[i * 5 + j]);
			}
			urls.add(url_temp);
		}

		// 点赞数据
		praises = new ArrayList<Praise>();
		Praise praise = null;
		for (int i = 0; i < 88; i++)
		{
			praise = new Praise();
			praise.setAvatar(Urls.URL_AVATAR[1]);
			praise.setNickname("功夫巨星");
			praise.setTime(time);
			praises.add(praise);
		}

		// 踩数据
		tramples = new ArrayList<Praise>();
		for (int i = 0; i < 3; i++)
		{
			praise = new Praise();
			praise.setAvatar(Urls.URL_AVATAR[3]);
			praise.setNickname("天外飞仙");
			praise.setTime(time);
			tramples.add(praise);
		}

		// 评论数据
		comments = new ArrayList<Comment>();
		Comment comment1 = new Comment();
		comment1.setC_name("安妮");
		comment1.setC_content("成龙大哥好帅啊！");
		comment1.setC_date(time);
		Comment comment2 = new Comment();
		comment2.setC_isreply(true);
		comment2.setC_reply_name("熊二");
		comment2.setC_name("安妮");
		comment2.setC_content("武打明星！");
		comment2.setC_date(time);
		comments.add(comment1);
		comments.add(comment2);

		// 加载数据
		ArrayList<String> url = new ArrayList<String>();
		for (int i = 4; i < 9; i++)
		{
			url.add(Urls.URL_LIST[i]);
		}

		bean = new WorkBean();
		bean.setAvatar(Urls.URL_AVATAR[3]);
		bean.setNickname(nickName);
		bean.setTime(time);
		bean.setDescription("加载成功 ：成龙，国家一级演员，大中华区影坛和国际功夫影星。");
		bean.setUrls(url);
		bean.setPraises(praises);
		bean.setGood(praises.size());
		bean.setTramples(tramples);
		bean.setBad(tramples.size());
		bean.setComments(comments);

		// 其它数据
		uu = new ArrayList<WorkBean>();
		WorkBean bWorkBean;
		for (int i = 0; i < urls.size(); i++)
		{
			bWorkBean = new WorkBean();
			bWorkBean.setAvatar(Urls.URL_AVATAR[3]);
			bWorkBean.setNickname(nickName);
			bWorkBean.setTime(time);
			bWorkBean.setDescription("成龙，国家一级演员，大中华区影坛和国际功夫影星。");
			bWorkBean.setUrls(urls.get(i));
			bWorkBean.setPraises(praises);
			bWorkBean.setGood(praises.size());
			bWorkBean.setTramples(tramples);
			bWorkBean.setBad(tramples.size());
			bWorkBean.setComments(comments);
			uu.add(bWorkBean);
		}

		/**
		 * 模拟数据 结束位置
		 */
	}


	/**
	 * 延时加载
	 */
	@Override
	protected void lazyLoad()
	{
		
	}


	/**
	 * 跳到指定的Tab
	 */
	@Override
	public void turnToTab(int tab)
	{
		
	}
	
}
