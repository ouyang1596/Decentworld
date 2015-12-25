/**
 * 
 */
package cn.sx.decentworld.common;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.widget.Toast;
import cn.sx.decentworld.bean.DWException;
import cn.sx.decentworld.bean.DWException.OnExceptionListener;

/**
 * @ClassName: MediaManager.java
 * @Description:
 * @author: yj
 * @date: 2015年9月22日 下午4:28:41
 */
public class MediaManager {
	private static MediaPlayer mMediaPlayer = new MediaPlayer();
	private static DWException mDWException = new DWException();
	private static Toast toast;

	public static void startLocal(Context context, String filePath,
			OnCompletionListener onCompletionListener,
			OnExceptionListener onExceptionListener) {
		if (null == toast) {
			toast = Toast.makeText(context, "文件可能损坏,播放声音失败", Toast.LENGTH_LONG);
		}
		if (null == mMediaPlayer) {
			mMediaPlayer = new MediaPlayer();
		}
		mMediaPlayer.setOnCompletionListener(onCompletionListener);
		mDWException.setOnExceptionListener(onExceptionListener);
		try {
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(filePath);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (Exception e) {
			toast.show();
			mDWException.setException(e);
		}
	}

	public static void startNetwork(Context context, String url,
			OnCompletionListener onCompletionListener,
			OnExceptionListener onExceptionListener) {
		if (null == toast) {
			toast = Toast.makeText(context, "文件可能损坏,播放声音失败", Toast.LENGTH_LONG);
		}
		if (null == mMediaPlayer) {
			mMediaPlayer = new MediaPlayer();
		}
		mMediaPlayer.setOnCompletionListener(onCompletionListener);
		mDWException.setOnExceptionListener(onExceptionListener);
		try {
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(url);
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer arg0) {
					mMediaPlayer.start();
				}
			});
		} catch (Exception e) {
			toast.show();
			mDWException.setException(e);
		}
	}

	public static boolean isPlaying() {
		return mMediaPlayer.isPlaying();
	}

	public static void stop() {
		if (null != mMediaPlayer) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}
