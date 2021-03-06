/**
 * 
 */
package cn.sx.decentworld.utils;

import java.text.DecimalFormat;

import android.widget.EditText;

/**
 * @ClassName: DataTransferUtils.java
 * @Description: 数据转换工具
 * @author: cj
 * @date: 2016年1月14日 下午6:34:29
 */
public class DataConvertUtils
{
    
    /**
     * 将浮点型的数字分割成整数部分和小数部分，以字符串的形式返回
     * @param num
     * @return
     */
    public static String[] splitFloat(float num)
    {
        String[] result = new String[]{"",""};
        int a = (int) Math.floor(num);
        
        float remain = num - a;
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        String b = decimalFormat.format(remain);
        
        result[0] = String.valueOf(a);
        result[1] = b;
        
        return result;
    }
    
    /**
     * 格式化浮点型数据，保留两位小数
     * @param num
     * @param format
     * @return
     */
    public static String formatFloat(float num)
    {
        if(num<=0)
        {
            return "0.00";
        }
        else if(num>0&&num<1)
        {
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            String b = decimalFormat.format(num);
            return "0"+b;
        }
        else
        {
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            String b = decimalFormat.format(num);
            return b;
        }
    }
    
    /**
     * 格式化浮点型数据
     * @param num
     * @param format
     * @return
     */
    public static String formatFloat(float num,String format)
    {
        int a = (int) Math.floor(num);
        DecimalFormat decimalFormat = new DecimalFormat(format);
        String b = decimalFormat.format(num);
        return a+b;
    }
    
    public static void formatPhone(String phoneNum,EditText etMobile)
    {
        String contents = phoneNum.toString();
        int length = contents.length();
        if (length == 4)
        {
            if (contents.substring(3).equals(new String(" ")))
            { // -
                contents = contents.substring(0, 3);
                etMobile.setText(contents);
                etMobile.setSelection(contents.length());
            }
            else
            { // +
                contents = contents.substring(0, 3) + " " + contents.substring(3);
                etMobile.setText(contents);
                etMobile.setSelection(contents.length());
            }
        }
        else if (length == 9)
        {
            if (contents.substring(8).equals(new String(" ")))
            { // -
                contents = contents.substring(0, 8);
                etMobile.setText(contents);
                etMobile.setSelection(contents.length());
            }
            else
            {// +
                contents = contents.substring(0, 8) + " " + contents.substring(8);
                etMobile.setText(contents);
                etMobile.setSelection(contents.length());
            }
        }
    }

}
