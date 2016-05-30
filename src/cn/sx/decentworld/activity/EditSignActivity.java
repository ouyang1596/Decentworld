/**
 * 
 */
package cn.sx.decentworld.activity;

import android.content.Intent;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.logSystem.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: EditSignActivity.java
 * @Description：修改个性签名
 * @author: cj
 * @date: 2015年10月21日 上午8:46:07
 */

@EActivity(R.layout.activity_edit_sign)
public class EditSignActivity extends BaseFragmentActivity implements OnClickListener
{
    private static final String TAG = "EditSignActivity";
    @Bean
    ToastComponent toast;
    /**
     * 输入昵称编辑框
     */
    @ViewById(R.id.et_edit_sign)
    EditText et_edit_sign;
    @ViewById(R.id.iv_back)
    ImageView ivBack;
    @ViewById(R.id.tv_header_title)
    TextView tvTitle;
    /**
     * 完成按钮
     */
    @ViewById(R.id.btn_edit_sign_complete)
    Button mBtnComplete;

    private int position = -1;
    private String title = "";
    private String oldData = "";

    @AfterViews
    void init()
    {
        parseIntent();
        tvTitle.setText("修改" + title);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
        mBtnComplete.setOnClickListener(this);
        initData();
    }

    /**
     * 解析数据
     */
    private void parseIntent()
    {
        title = getIntent().getStringExtra("title");
        position = getIntent().getIntExtra("position", -1);
        oldData = getIntent().getStringExtra("oldData");
        LogUtils.v(TAG, "title=" + title + ",position=" + position + ",oldData=" + oldData);
    }

    /**
     * 初始化数据
     */
    private void initData()
    {
        if (!oldData.equals(""))
            et_edit_sign.setText(oldData);
        // 将光标设置到文字的末尾
        CharSequence text = et_edit_sign.getText();
        if (text instanceof Spannable)
        {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_edit_sign_complete:
            {
                String newData = et_edit_sign.getText().toString();
                if (!newData.equals(oldData))
                {
                    LogUtils.v(TAG,"onClick() newData=" + newData);
                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    intent.putExtra("newData", newData);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

}
