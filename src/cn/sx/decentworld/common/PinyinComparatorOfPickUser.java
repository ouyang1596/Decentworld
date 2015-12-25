/**
 * 
 */
package cn.sx.decentworld.common;

import java.util.Comparator;

import cn.sx.decentworld.bean.PickUser;

/**
 * @ClassName: PinyinComparatorOfPickUser.java
 * @Description:
 * @author: dyq
 * @date: 2015年8月7日 上午8:27:59
 */
public class PinyinComparatorOfPickUser implements Comparator<PickUser>
{
	@Override
	public int compare(PickUser lhs, PickUser rhs)
	{
		if (lhs.getSortLetters().equals("@") || rhs.getSortLetters().equals("#"))
		{
			return -1;
		}
		else if (lhs.getSortLetters().equals("#") || rhs.getSortLetters().equals("@"))
		{
			return 1;
		}
		else
		{
			return lhs.getSortLetters().compareTo(rhs.getSortLetters());
		}
	}

}
