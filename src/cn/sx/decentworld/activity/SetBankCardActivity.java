/**
 * 
 */
package cn.sx.decentworld.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import cn.sx.decentworld.R;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.engine.UserInfoEngine;
import cn.sx.decentworld.utils.LogUtils;

/**
 * @ClassName: SetBankCardActivity.java
 * @Description:
 * @author: cj
 * @date: 2016年3月17日 下午2:50:43
 */
@EActivity(R.layout.activity_set_bank_card)
public class SetBankCardActivity extends BaseFragmentActivity implements TextWatcher
{
    /**
     * 常量
     */
    private static final String TAG = "SetBankCardActivity";

    /**
     * 工具类
     */
    @Bean
    ToastComponent toast;

    /**
     * 界面资源
     */
    @ViewById(R.id.et_set_bank_card_realname)
    EditText etRealname;
    @ViewById(R.id.et_set_bank_card_account)
    EditText etAccount;
    @ViewById(R.id.et_set_bank_card_name)
    EditText etName;
    @ViewById(R.id.iv_set_bank_card_edit)
    ImageView ivEditRealname;
    @ViewById(R.id.btn_set_bank_card_submit)
    Button btnSubmit;

    /**
     * 变量
     */

    /**
     * 入口
     */
    @AfterViews
    void init()
    {
        initVariables();
        initViews();
        initDatas();
    }

    /**
     * 初始化变量
     */
    private void initVariables()
    {
        etAccount.addTextChangedListener(this);
    }

    /**
     * 初始化界面
     */
    private void initViews()
    {
        String realname = UserInfoEngine.getInstance().getUserInfo().getRealName();
        etRealname.setText(realname);
        etRealname.setEnabled(false);

    }

    /**
     * 初始化数据
     */
    private void initDatas()
    {
        // TODO Auto-generated method stub

    }

    /**
     * 修改实名
     * 
     * @param v
     */
    @Click(R.id.iv_set_bank_card_edit)
    void modifyRealname(View v)
    {
        toast.show("开发中...");
    }
    
    /**
     * 提交
     * @param v
     */
    @Click(R.id.btn_set_bank_card_submit)
    void submit(View v)
    {
        toast.show("提交");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        if (s.length() < 6)
        {
            etName.setText("");
        }
        else if (s.length() == 6)
        {
            // 查询银行卡对应的银行名
            String  result = s.toString();
            LogUtils.i(TAG, "输入的内容为："+result);

            // 结果显示在控件中
            etName.setText("查找结果");
        }
    }

}
