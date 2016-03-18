/**
 * 
 */
package cn.sx.decentworld.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.adapter.WorkCommentAdapter;
import cn.sx.decentworld.bean.Comment;
import cn.sx.decentworld.bean.Praise;
import cn.sx.decentworld.component.TitleBar;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.widget.PasteEditText;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: WorkCircleCommentActivity.java
 * @Description:
 * @author: cj
 * @date: 2015年8月6日 下午2:19:04
 */

@EActivity(R.layout.activity_work_circle_comments)
public class WorkCircleCommentActivity extends BaseFragmentActivity implements OnClickListener
{
	private static final String TAG= "WorkCircleCommentActivity";
	@Bean
	TitleBar titleBar;
	@Bean
	ToastComponent toast;

	@ViewById(R.id.lv_work_circle_comment)
	ListView lv_work_circle_comment;
	
	@ViewById(R.id.et_work_circle_comment_edit)
	PasteEditText et_work_circle_comment_edit;
	@ViewById(R.id.btn_work_circle_comment_send)
	Button btn_work_circle_comment_send;
	
	private int positionOfItem;
	private WorkCommentAdapter workCommentAdapter;
	private ArrayList<Comment> comments;
	

	@AfterViews
	void init()
	{
		titleBar.setTitleBar("返回", "评论列表");
		initPosition();
		initDatas();

	}

	/**
	 * 
	 */
	private void initDatas()
	{
		btn_work_circle_comment_send.setOnClickListener(this);

		comments = (ArrayList<Comment>) getIntent().getSerializableExtra("comment");
		
		workCommentAdapter = new WorkCommentAdapter(WorkCircleCommentActivity.this, R.layout.item_work_comment, comments);
		lv_work_circle_comment.setAdapter(workCommentAdapter);
	}

	private void initPosition()
	{
		Intent intent = getIntent();
		positionOfItem = intent.getIntExtra("item", 0);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_work_circle_comment_send:
			send();
			toast.show("发送成功");
			break;
		default:
			break;
		}

	}
	
	@Click(R.id.main_header_left)
	void back()
	{
		finish();
	}

	/**
	 * 发表评论
	 */
	private void send()
	{
		if (!et_work_circle_comment_edit.getText().toString().isEmpty())
		{
			Comment comment = new Comment();
			comment.setC_content(et_work_circle_comment_edit.getText().toString());
//			comment.setC_name(DecentWorldApp.getInstance().getUserName());
			
			SimpleDateFormat formatter = new SimpleDateFormat ("MM月dd日  HH:mm");     
			Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
			String time = formatter.format(curDate);   
			comment.setC_date(time);

			comments.add(comment);
			lv_work_circle_comment.setSelection(comments.size()-1);
			et_work_circle_comment_edit.setText("");

		} else
		{
			toast.show("请输入内容");
		}
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_work_circle_comment_edit.getWindowToken(), 0);
	}
}
