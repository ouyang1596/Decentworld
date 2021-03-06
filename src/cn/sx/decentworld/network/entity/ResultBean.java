package cn.sx.decentworld.network.entity;

import android.text.TextUtils;

/**
 * 
 * @ClassName: ResultBean.java
 * @Description: 网络请求时使用到的实体
 * @author: cj
 * @date: 2016年3月2日 下午10:07:43
 */
public class ResultBean
{
    private int resultCode;
    private String msg;
    private Object data;

    public int getResultCode()
    {
        return resultCode;
    }

    public void setResultCode(int resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ResultBean [resultCode=" + resultCode + ", msg=" + msg + ", data=" + data + "]";
    }

    public static boolean isSuccess(ResultBean msg)
    {

        if (msg == null)
            return false;

        if (msg.getResultCode() != 2000)
            return false;

        if (TextUtils.isEmpty(msg.getData().toString()))
            return false;

        return true;
    }

}
