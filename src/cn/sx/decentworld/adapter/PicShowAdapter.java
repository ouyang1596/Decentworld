/**
 * 
 */
package cn.sx.decentworld.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.ImageLoaderHelper;

/**
 * @ClassName: PicShowAdapter.java
 * @author: yj
 * @date: 2016年4月12日 上午9:55:19
 */
public class PicShowAdapter extends BaseAdapter {
	private Context mContext;
	private String[] mUrls;

	public PicShowAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		return null == mUrls ? 0 : mUrls.length;
	}

	@Override
	public Object getItem(int position) {
		return mUrls[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View con, ViewGroup parent) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(mContext, R.layout.item_image, null);
			vh = new ViewHolder();
			vh.iv = (ImageView) con.findViewById(R.id.iv);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					DecentWorldApp.getInstance().windowWidth / 3 - 30, DecentWorldApp.getInstance().windowWidth / 3 - 30);
			vh.iv.setLayoutParams(params);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		ImageLoaderHelper.mImageLoader.displayImage(mUrls[position], vh.iv, ImageLoaderHelper.mOptions);
		return con;
	}

	public void setUrls(String[] urls) {
		mUrls = urls;
	}

	class ViewHolder {
		ImageView iv;
	}
}
