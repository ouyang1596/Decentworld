/**
 * 
 */
package cn.sx.decentworld.adapter;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import cn.sx.decentworld.R;
import cn.sx.decentworld.bean.ThemeBean;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.utils.ImageLoaderHelper;

/**
 * @ClassName: ThemeAdapter.java
 * @author: yj
 * @date: 2015年11月7日 上午9:59:41
 */
public class ThemeAdapter extends BaseAdapter implements OnClickListener {
	private Context mContext;
	private List<ThemeBean> mData;
	private boolean ifLocal;

	public ThemeAdapter(Context context, List<ThemeBean> data, boolean ifLocal) {
		mContext = context;
		mData = data;
		this.ifLocal = ifLocal;
	}

	public void setIfLocalNotifyDataSetChanged(boolean ifLocal) {
		this.ifLocal = ifLocal;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public ThemeBean getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View con, ViewGroup arg2) {
		ViewHolder vh;
		if (null == con) {
			con = View.inflate(mContext, R.layout.item_edit_theme, null);
			vh = new ViewHolder();
			vh.ivAddPic = (ImageView) con.findViewById(R.id.iv_add_pic);
			vh.etContent = (EditText) con.findViewById(R.id.et_content);
			con.setTag(vh);
		} else {
			vh = (ViewHolder) con.getTag();
		}
		ThemeBean theme = mData.get(position);
		vh.etContent.setText(theme.subjectContent);
		if (null != theme.picPath) {
			if (ifLocal) {
				// Picasso.with(mContext).load(new File(theme.picPath))
				// .config(Config.RGB_565).into(vh.ivAddPic);
				ImageLoaderHelper.mImageLoader.displayImage(Constants.URI_FILE
						+ theme.picPath, vh.ivAddPic,
						ImageLoaderHelper.mOptions);
			} else {
				// Picasso.with(mContext).load(theme.picPath)
				// .config(Config.RGB_565).into(vh.ivAddPic);
				ImageLoaderHelper.mImageLoader.displayImage(theme.picPath,
						vh.ivAddPic, ImageLoaderHelper.mOptions);
			}
		}
		vh.ivAddPic.setTag(Constants.ITEM_POSITION, position);
		vh.ivAddPic.setOnClickListener(this);
		vh.etContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence data, int arg1, int arg2,
					int arg3) {
				if (data.length() > 0) {
					ThemeBean item = mData.get(position);
					item.subjectContent = data.toString();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		return con;
	}

	@Override
	public void onClick(View view) {
		if (null != mOnClickListener) {
			mOnClickListener.onClick(view);
		}
	}

	public void setOnThemeClickListener(
			OnThemeClickListener onThemeClickListener) {
		this.mOnClickListener = onThemeClickListener;
	}

	private OnThemeClickListener mOnClickListener;

	public interface OnThemeClickListener {
		public void onClick(View view);
	}

	class ViewHolder {
		ImageView ivAddPic;
		EditText etContent;
	}
}
