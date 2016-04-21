package cn.sx.decentworld.activity;

import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.sx.decentworld.R;
import cn.sx.decentworld.logSystem.LogUtils;
import cn.sx.decentworld.widget.AutoSizeImageView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_ceshi)
public class CeshiActivity extends BaseFragmentActivity {
	@ViewById(R.id.rel_ceshi)
	RelativeLayout rel;
	@ViewById(R.id.iv_doubt)
	ImageView ivDoubt;

	@ViewById(R.id.iv_contact_avatar)
	AutoSizeImageView iv;

	@AfterViews
	void init() {
		rel.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(160, 160);
				lp.setMargins(0, iv.getWidth() - ivDoubt.getHeight() / 2, 0, 0);
				ivDoubt.setLayoutParams(lp);
				rel.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
		ivDoubt.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				ivDoubt.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}
}
