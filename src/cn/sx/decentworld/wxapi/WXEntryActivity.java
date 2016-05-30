package cn.sx.decentworld.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sx.decentworld.DecentWorldApp;
import cn.sx.decentworld.activity.LoginActivity_;
import cn.sx.decentworld.activity.WeixinInComeActivity;
import cn.sx.decentworld.common.ConstantIntent;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.logSystem.LogUtils;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @ClassName: BindAccountDetailActivity.java
 * @Description: 绑定账号详细界面
 * @author: cj
 * @date: 2015年12月23日 上午11:51:03
 */
public class WXEntryActivity extends FragmentActivity implements IWXAPIEventHandler {
	private static final String TAG = "WXEntryActivity";
	private IWXAPI wxReq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wxReq = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		wxReq.handleIntent(getIntent(), this);
	}

	// @Override
	// protected void onNewIntent(Intent intent) {
	// LogUtils.d(TAG, "onNewIntent");
	// super.onNewIntent(intent);
	// setIntent(intent);
	// wxReq.handleIntent(intent, this);
	// }

	@Override
	public void onReq(BaseReq req) {
		LogUtils.i(TAG, "onReq");
		switch (req.getType()) {
		case 1:

			break;
		default:
			break;
		}
	}

	@Override
	public void onResp(BaseResp resp) {
		LogUtils.d(TAG, "resp type " + resp.getType());
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:// 用户同意
			if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
				String code = ((SendAuth.Resp) resp).code;
				LogUtils.d(TAG, "CODE " + code);
				if (0 == DecentWorldApp.WX_AUTHORIZE_STATE) {
					Intent intent = new Intent(this, LoginActivity_.class);
					intent.putExtra(ConstantIntent.WX_CODE, code);
					startActivity(intent);
				} else if (1 == DecentWorldApp.WX_AUTHORIZE_STATE) {
					Intent intent = new Intent(this, WeixinInComeActivity.class);
					intent.putExtra(ConstantIntent.WX_CODE, code);
					startActivity(intent);
				}
			}
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:// 用户取消
			LogUtils.i(TAG, "用户取消");
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:// 用户拒绝授权
			LogUtils.i(TAG, "用户拒绝授权");
			break;
		default:
			LogUtils.i(TAG, "");
			break;
		}
		finish();
	}

	// /**
	// * 处理微信发出的向第三方应用请求app message
	// * <p>
	// * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
	// * 做点其他的事情，包括根本不打开任何页面
	// */
	// public void onGetMessageFromWXReq(WXMediaMessage msg) {
	// Intent iLaunchMyself =
	// getPackageManager().getLaunchIntentForPackage(getPackageName());
	// startActivity(iLaunchMyself);
	// }
	//
	// /**
	// * 处理微信向第三方应用发起的消息
	// * <p>
	// * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
	// * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
	// 回调。
	// * <p>
	// * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
	// */
	// public void onShowMessageFromWXReq(WXMediaMessage msg) {
	// if (msg != null && msg.mediaObject != null && (msg.mediaObject instanceof
	// WXAppExtendObject)) {
	// WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
	// Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
	// }
	// }
}
