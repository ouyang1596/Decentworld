/**
 * 
 */
package cn.sx.decentworld.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.text.format.DateUtils;
import android.text.format.Time;
import cn.sx.decentworld.common.CommUtil;

/**
 * @ClassName: TimeUtils.java
 * @Description:
 * @author: yj
 * @date: 2015年10月24日 下午6:04:34
 */
public class TimeUtils
{
	private static final String TAG = "TimeUtils";
	private static SimpleDateFormat formatBuilder;

	// 获取当前时间
	public static String currentTimeFormate()
	{
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-HH:mm");
		String t = format.format(new Date());
		return t;
	}

	public static String getDate(String format)
	{
		formatBuilder = new SimpleDateFormat(format);
		return formatBuilder.format(new Date());
	}

	public static String getDate(String format, Date date)
	{
		formatBuilder = new SimpleDateFormat(format);
		return formatBuilder.format(date);
	}

	//
	public static String getDate(String format, String time_stamp)
	{
		formatBuilder = new SimpleDateFormat(format);
		Long time = new Long(time_stamp);
		String d = formatBuilder.format(time);
		Date date = null;
		try
		{
			date = formatBuilder.parse(d);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			LogUtils.i("TimeRender", e.toString());
		}
		return formatBuilder.format(date);
	}

	public static String getDate()
	{
		return getDate("HH:mm:ss");
	}

	public static String getDateAllMessage()
	{
		return getDate("yyyy-MM-dd HH:mm:ss");
	}

	// 将字符串的long时间转化成指定形式的时间
	public static String getDate_refer_Time_stamp(String time_stamp)
	{
		return getDate("HH:mm:ss", time_stamp);
	}

	public static Date getDateType(String time_stamp)
	{
		Long time = new Long(time_stamp);
		String date = formatBuilder.format(time);
		Date result = null;
		try
		{
			result = formatBuilder.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			LogUtils.i("TimeRender", e.toString());
		}
		return result;
	}



	/**
	 * 是否显示时间
	 * @param before
	 *            上一条消息的时间
	 * @param now
	 *            最新一条消息的时间
	 * @return
	 */
	public static boolean isShowTime(String before, String now)
	{
		if (CommUtil.isBlank(before) || CommUtil.isBlank(now))
		{
			LogUtils.i(TAG, "isShowTime(String before,String now)参数为空");
		}
		long interval = 1000 * 60 * 5;
		long time1 = Long.valueOf(before);
		long time2 = Long.valueOf(now);
		if (time2 - time1 > interval)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 显示时间格式为今天、昨天、yyyy-MM-dd hh:mm
	 * @param context
	 * @param when
	 * @return String
	 */
	public static String DataLong2String(String longString)
	{
		long when = Long.valueOf(longString);
		
		Time then = new Time();
		then.set(when);
		Time now = new Time();
		now.setToNow();

		String formatStr;
		if (then.year != now.year)
		{
			formatStr = "yyyy-MM-dd";
		}
		else if (then.yearDay != now.yearDay)
		{
			// If it is from a different day than today, show only the date.
			formatStr = "MM-dd";
		}
		else
		{
			// Otherwise, if the message is from today, show the time.
			formatStr = "HH:MM";
		}

		if (then.year == now.year && then.yearDay == now.yearDay)
		{
			//今天
			return getDate("HH:mm:ss", String.valueOf(when));
		}
		else if ((then.year == now.year) && ((now.yearDay - then.yearDay) == 1))
		{
			//昨天
			return "昨天 "+getDate("HH:mm:ss", String.valueOf(when));
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			String temp = sdf.format(when);
			if (temp != null && temp.length() == 5 && temp.substring(0, 1).equals("0"))
			{
				temp = temp.substring(1);
			}
			return temp;
		}
	}
	
	
	// public static final SimpleDateFormat COMM_DATETIME_FMT = new
	// SimpleDateFormat(
	// "yyyy-MM-dd HH:mm:ss");
	// public static final SimpleDateFormat COMM_DATE_FMT = new
	// SimpleDateFormat(
	// "yyyy-MM-dd");
	// public static final SimpleDateFormat COMM_TIME_FMT = new
	// SimpleDateFormat(
	// "HH:mm:ss");

    public static String weekHandle(int WeekOfMonth) {
        String week = null;
        switch (WeekOfMonth) {
            case 1:
                week = "SUNDAY";
                break;

            case 2:
                week = "MONDAY";
                break;
            case 3:
                week = "TUESDAY";
                break;
            case 4:
                week = "WEDNESDAY";
                break;
            case 5:
                week = "THURSDAY";
                break;
            case 6:
                week = "FRIDAY";
                break;
            case 7:
                week = "SATURDAY";
                break;
        }
        return week;
    }

    /**
     * 将一年中的月份转化成为英文
     * 
     * @param month0fYear
     * @return
     */
    public static String MonthHandle(int month0fYear) {
        String month = null;
        switch (month0fYear) {
            case 1:
                month = "JAN";
                break;

            case 2:
                month = "FEB";
                break;
            case 3:
                month = "MAR";
                break;
            case 4:
                month = "APR";
                break;
            case 5:
                month = "MAY";
                break;
            case 6:
                month = "JUN";
                break;
            case 7:
                month = "JUL";
                break;
            case 8:
                month = "AUG";
                break;
            case 9:
                month = "SEP";
                break;
            case 10:
                month = "OCT";
                break;
            case 11:
                month = "NOV";
                break;
            case 12:
                month = "DEC";
                break;

        }
        return month;
    }
}
