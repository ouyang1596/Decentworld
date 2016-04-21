/**
 * 
 */
package cn.sx.decentworld.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.ConstantNet;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.network.SendUrl;
import cn.sx.decentworld.network.SendUrl.HttpCallBack;
import cn.sx.decentworld.network.entity.ResultBean;
import cn.sx.decentworld.utils.AES;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;

/**
 *
 * @ClassName: VerifyBenefitPwdDialog.java
 * @Description:  验证收益密码对话框
 * @author: cj
 * @date: 2016年3月24日 上午10:37:07
 */
public class VerifyBenefitPwdDialog extends DialogFragment implements OnClickListener
{
    private static final String TAG = "VerifyBenefitPwdDialog";
    private ImageView ivClose;
    private LinearLayout llPasswordContainer;
    private VerifyBenefitPwdListener mListener;
    private TextView tvTitle;
    private List<TextView> numList = new ArrayList<TextView>();
    private StringBuffer buffer = new StringBuffer();
    
    private int[] bgs = new int[]{R.drawable.com_pwd_keyboard_bg_0,R.drawable.com_pwd_keyboard_bg_1,R.drawable.com_pwd_keyboard_bg_2,
            R.drawable.com_pwd_keyboard_bg_3,R.drawable.com_pwd_keyboard_bg_4,R.drawable.com_pwd_keyboard_bg_5,R.drawable.com_pwd_keyboard_bg_6};

    /**
     * 设置监听
     * 
     * @param listener
     */
    public void setListener(VerifyBenefitPwdListener listener)
    {
        this.mListener = listener;
    }
    
    
    /**
     * 设置监听
     * 
     * @param listener
     */
    public void setMListener(VerifyBenefitPwdListener listener)
    {
        this.mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new Dialog(getActivity() , R.style.ShouchangDialog);
        return dialog;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_verify_benefit_pwd, container, false);
        ivClose = (ImageView) view.findViewById(R.id.iv_password_keyboard_close);
        ivClose.setOnClickListener(this);
        llPasswordContainer = (LinearLayout) view.findViewById(R.id.ll_password_keyboard_password_container);
        tvTitle = (TextView) view.findViewById(R.id.tv_password_keyboard_password_title);
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_0));
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_1));
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_2));
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_3));
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_4));
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_5));
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_6));
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_7));
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_8));
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_9));
        numList.add((TextView) view.findViewById(R.id.tv_password_keyboard_num_delete));
        for (int i = 0; i < numList.size(); i++)
        {
            numList.get(i).setOnClickListener(this);
        }
        //改变标题颜色
        String titleText = "请先输入收益密码";
        SpannableStringBuilder builder = new SpannableStringBuilder(titleText);
        int con = titleText.indexOf("密码");
        builder.setSpan(new ForegroundColorSpan(Color.RED), con, con+"密码".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTitle.setText(builder);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        setFullScreen();
    }
    
    /**
     * 设置让DialogFragment的布局充满全屏
     * */
    private void setFullScreen() 
    {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_password_keyboard_close:
                // 关闭界面
                dismiss();
                break;
            case R.id.tv_password_keyboard_num_0:
                updateUI(0);
                break;
            case R.id.tv_password_keyboard_num_1:
                updateUI(1);
                break;
            case R.id.tv_password_keyboard_num_2:
                updateUI(2);
                break;
            case R.id.tv_password_keyboard_num_3:
                updateUI(3);
                break;
            case R.id.tv_password_keyboard_num_4:
                updateUI(4);
                break;
            case R.id.tv_password_keyboard_num_5:
                updateUI(5);
                break;
            case R.id.tv_password_keyboard_num_6:
                updateUI(6);
                break;
            case R.id.tv_password_keyboard_num_7:
                updateUI(7);
                break;
            case R.id.tv_password_keyboard_num_8:
                updateUI(8);
                break;
            case R.id.tv_password_keyboard_num_9:
                updateUI(9);
                break;
            case R.id.tv_password_keyboard_num_delete:
                delete();
                break;
            default:
                break;
        }
    }

    /**
     * 删除一个数字
     */
    private void delete()
    {
        int length = buffer.length();
        if (length > 0)
        {
            buffer.deleteCharAt(length - 1);
            setPasswordContainer(buffer.length());
            LogUtils.i(TAG, "buffer=" + buffer.toString() + ",length=" + buffer.length());
        }
    }

    /**
     * 添加一个数字
     */
    private void updateUI(int num)
    {
        buffer.append(num);
        setPasswordContainer(buffer.length());
        LogUtils.i(TAG, "buffer=" + buffer.toString() + ",length=" + buffer.length());
        int length = buffer.length();
        if (length >= 6)
        {
            verifyBenefitPwd(buffer.toString());
        }
    }

    /**
     * 更新密码控件
     * 
     * @param length
     */
    private void setPasswordContainer(int length)
    {
        if(length<=6)
        {
            llPasswordContainer.setBackgroundResource(bgs[length]);
        }
    }

    
    /**
     * 验证收益密码回调
     */
    public interface VerifyBenefitPwdListener
    {
        public void onSuccess(String tempToken);
        public void onFailure(String cause);
    }
    
    /**
     * 验证收益密码
     */
    private void verifyBenefitPwd(String pwd)
    {
        final Handler handler = new Handler()
        {
            public void handleMessage(android.os.Message msg) 
            {
                if(msg.what == 1)
                {
                    mListener.onSuccess(msg.obj.toString());
                    dismiss();
                }
                else
                {
                    mListener.onFailure(msg.obj.toString());
                    dismiss();
                }
            };
        };
        
        String dwID = DecentWorldApp.getInstance().getDwID();
        String password = AES.encodeWithRandomStr(pwd);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("dwID",dwID);
        map.put("password", password);
        LogUtils.i(TAG, "verifyBenefitPwd...begin,dwID="+dwID+",password="+password);
        SendUrl sendUrl = new SendUrl(getActivity());
        sendUrl.httpRequestWithParams(map, Constants.CONTEXTPATH+ConstantNet.API_CHECK_BENEFIT_PWD, Method.GET, new HttpCallBack()
        {
            @Override
            public void onSuccess(String resultJSON, ResultBean resultBean)
            {
                LogUtils.i(TAG, "verifyBenefitPwd...onSuccess,code="+resultBean.getResultCode()+",msg="+resultBean.getMsg()+",data="+resultBean.getData());
                if(resultBean.getResultCode() == 2222)
                {
                    LogUtils.i(TAG, "verifyBenefitPwd...success");
                    String jsonStr = resultBean.getData().toString();
                    JSONObject parseObject = JSON.parseObject(jsonStr);
                    String tempToken = parseObject.getString("token");
                    
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = tempToken;
                    handler.sendMessage(message);
                }
                else
                {
                    LogUtils.i(TAG, "verifyBenefitPwd...failure,cause by:"+resultBean.getMsg());
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = "密码错误";
                    handler.sendMessage(message);
                }
            }
            
            @Override
            public void onFailure(String e)
            {
                LogUtils.i(TAG, "verifyBenefitPwd...onFailure,cause by:"+e);
                Message message = Message.obtain();
                message.what = 0;
                message.obj = Constants.NET_WRONG;
                handler.sendMessage(message);
            }
        });
    }
}
