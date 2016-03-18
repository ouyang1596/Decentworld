/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.ArrayList;

import cn.sx.decentworld.fragment.ImageDetailFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @ClassName: ImagePagerAdapter.java
 * @Description: 显示大图适配器
 * @author: cj
 * @date: 2016年2月24日 下午7:59:22
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter
{
    public ArrayList<String> fileList;
    public int localOrNet;

    public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList, int localOrNet)
    {
        super(fm);
        this.fileList = fileList;
        this.localOrNet = localOrNet;
    }

    @Override
    public int getCount()
    {
        return fileList == null ? 0 : fileList.size();
    }

    @Override
    public Fragment getItem(int position)
    {
        String url = fileList.get(position);
        return ImageDetailFragment.newInstance(url, localOrNet);
    }
}
