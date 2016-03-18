package cn.sx.decentworld.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import cn.sx.decentworld.R;
import cn.sx.decentworld.widget.ViewHolder;

public class PhotoSingleChoiceAdapter extends CommonAdapter<String> {

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */

	public static List<String> mSelectedImage = new LinkedList<String>();
	/**
	 * 文件夹路径
	 */
	private String mDirPath;
	private Context context;
	public PhotoSingleChoiceAdapter(Context context, List<String> mDatas, int itemLayoutId,
			String dirPath) {
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
		this.context = context;
	}



	@Override
	public void convert(ViewHolder helper, String item) {
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
			helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);
	}
}
