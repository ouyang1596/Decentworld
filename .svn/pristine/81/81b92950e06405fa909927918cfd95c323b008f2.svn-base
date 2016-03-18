package cn.sx.decentworld.activity;

import java.util.HashMap;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.sina.weibo.SinaWeibo.ShareParams;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.utils.LogUtils;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_share)
public class ShareActivity extends BaseFragmentActivity implements
		OnClickListener {
	@ViewById(R.id.iv_wechat)
	ImageView ivWeChat;
	@ViewById(R.id.iv_sina)
	ImageView ivSina;
	@ViewById(R.id.iv_shortmessage)
	ImageView ivShortMessage;
	@ViewById(R.id.ll_wechat)
	LinearLayout llWechat;
	@ViewById(R.id.ll_wechat_moments)
	LinearLayout llWechatMoments;
	@ViewById(R.id.tv_header_title)
	TextView tvTitle;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	private boolean ifShow = true;
	@Bean
	ToastComponent toast;

	@AfterViews
	public void init() {
		// 分享
		ShareSDK.initSDK(this);
		tvTitle.setText("分享");
		ivBack.setVisibility(View.VISIBLE);
		ivShortMessage.setOnClickListener(this);
		ivSina.setOnClickListener(this);
		ivWeChat.setOnClickListener(this);
		llWechat.setOnClickListener(this);
		llWechatMoments.setOnClickListener(this);
		ivBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_sina:
			sinaShare();
			break;
		case R.id.iv_shortmessage:
			shortMessageShare();
			break;
		case R.id.iv_wechat:
			// wechatMomentShare();
			if (ifShow) {
				llWechat.setVisibility(View.VISIBLE);
				llWechatMoments.setVisibility(View.VISIBLE);
				ifShow = false;
			} else {
				llWechat.setVisibility(View.GONE);
				llWechatMoments.setVisibility(View.GONE);
				ifShow = true;
			}
			break;
		case R.id.ll_wechat:
			weChatShare();
			break;
		case R.id.ll_wechat_moments:
			wechatMomentShare();
			break;
		case R.id.iv_back:
			finish();
			break;
		}
	}

	protected void sinaShare() {
		ShareParams sp = new ShareParams();
		sp.setText("滴滴答答http://112.74.13.117/data/apk/share/dw.apk");
		// sp.setImagePath("/storage/emulated/0/decentworld/pic_thumbnail/fbb3981e-b755-4057-8c36-b50e5ef0148a.jpg");
		Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
		weibo.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				LogUtils.e("bm", "error--" + arg2);
				showToast("error");
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				showToast("onComplete");
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				showToast("onCancel");
			}
		}); // 设置分享事件回调
		// 执行图文分享
		weibo.share(sp);
	}

	public void shortMessageShare() {
		ShareParams sp = new ShareParams();
		sp.setText("我来了http://112.74.13.117/data/apk/share/dw.apk");
		// sp.setImagePath("/storage/emulated/0/decentworld/pic_thumbnail/fbb3981e-b755-4057-8c36-b50e5ef0148a.jpg");
		Platform shortMessage = ShareSDK.getPlatform(ShortMessage.NAME);
		shortMessage.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				showToast("error");
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				showToast("onComplete");
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				showToast("onCancel");
			}
		}); // 设置分享事件回调
		// 执行图文分享
		shortMessage.share(sp);
	}

	protected void wechatMomentShare() {
		ShareParams sp = new ShareParams();
		// sp.setText("我来了http://www.baidu.com");
		sp.setShareType(Platform.SHARE_WEBPAGE);
		sp.setTitle("大腕");
		sp.setText("大腕");
		sp.setUrl("http://112.74.13.117/data/apk/share/dw.apk");
		// sp.setText("Decent World http://www.baidu.com");
		// sp.setImageData(BitmapFactory.decodeResource(getResources(),
		// R.drawable.ic_launcher));
		sp.setImagePath(Constants.HOME_PATH + "/share/" + "share.png");
		Platform weibo = ShareSDK.getPlatform(WechatMoments.NAME);
		weibo.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				showToast("error--");
				LogUtils.e("bm", "error--" + arg2);
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				showToast("onComplete");
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				showToast("onCancel");
			}
		}); // 设置分享事件回调
		// 执行图文分享
		weibo.share(sp);
	}

	protected void weChatShare() {
		ShareParams sp = new ShareParams();
		sp.setShareType(Platform.SHARE_WEBPAGE);
		sp.setTitle("大腕");
		sp.setText("大腕");
		sp.setUrl("http://112.74.13.117/data/apk/share/dw.apk");
		// sp.setImageData(BitmapFactory.decodeResource(getResources(),
		// R.drawable.ic_launcher));
		sp.setImagePath(Constants.HOME_PATH + "/share/" + "share.png");
		Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
		weChat.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				showToast("error");
				LogUtils.e("bm", "error--" + arg2);
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				showToast("onComplete");
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				showToast("onCancel");
			}
		}); // 设置分享事件回调
		// 执行图文分享
		weChat.share(sp);
	}

	private void showToast(final String data) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				toast.show(data);
			}
		});
	}
}
