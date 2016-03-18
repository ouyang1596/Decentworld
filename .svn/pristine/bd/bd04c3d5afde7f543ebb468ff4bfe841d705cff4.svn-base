/**
 * 
 */
package cn.sx.decentworld.widget;

import cn.sx.decentworld.R;
import cn.sx.decentworld.common.Constants;
import cn.sx.decentworld.common.TheMediaRecorder;
import cn.sx.decentworld.common.TheMediaRecorder.AudioParedListener;
import cn.sx.decentworld.dialog.RecorderDialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;

/**
 * @ClassName: RecorderButton.java
 * @Description:
 * @author: dyq
 * @date: 2015年9月22日 下午12:32:34
 */
public class RecorderButton extends Button implements AudioParedListener {

	private static final int STATE_NORMAL = 1;
	private static final int STATE_RECORDING = 2;
	private static final int STATE_WANTCANCLE = 3;
	// y轴方向取消的最短距离
	private static final int DISTANCE_Y_WANTCANCEL = 50;
	private int mCurStete = STATE_NORMAL;
	private RecorderDialog mDialog;
	// 是否達到longclick
	private boolean isReady = false;

	// TheMediaRecorder是否回調回來，是否在錄音著
	private boolean isRecording = false;
	// 不要弄成int型
	private float mTime;

	private TheMediaRecorder mTheMediaRecorder;

	public RecorderButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDialog = new RecorderDialog(context);

		// String SD_Path=Constants.RecorderAudiosPath;
		String SD_Path = Constants.HomePath + Constants.AUDIO_PATH;

		mTheMediaRecorder = TheMediaRecorder.getInstance(SD_Path);

		// 调用回调方法,当MediaRecorder准备好时回调
		mTheMediaRecorder.setAudioListener(this);

		setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("长按了");
				isReady = true;
				mTheMediaRecorder.prepareAudio();
				return false;
			}
		});
	}

	/*
	 * 录音完成后的回调借口
	 */
	public interface AudioFinishedRecordeListener {
		public void FinishedRecordeListener(Float mTime, String FilePath);
	}

	private AudioFinishedRecordeListener mListener;

	// 回调方法
	public void setAudioFishedRecorde(AudioFinishedRecordeListener mm) {
		mListener = mm;
	}

	@Override
	public void wellPared() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessage(TheMediaRecorder_PREPARED);

	}

	// onlongclickListener()要写在构造器内
	// @Override
	// public void setOnLongClickListener(OnLongClickListener l) {
	// // TODO Auto-generated method stub
	//
	//
	// super.setOnLongClickListener(l);
	// System.out.println("长按了");
	// //MediaRecorder准备之后才开始
	// mDialog.showDialog();
	// isDialogShow=true;
	// }
	/*
	 * 线程呈现声音大小变化 *
	 */
	Runnable GetVoiceLevelRunnable = new Runnable() {
		public void run() {
			while (isRecording) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mTime += 0.1f;
				System.out.println("现在Time：" + mTime);
				mHandler.sendEmptyMessage(TheMediaRecorder_CHANGED);
			}
		}
	};
	public static final int TheMediaRecorder_PREPARED = 1;
	public static final int TheMediaRecorder_CHANGED = 2;
	public static final int TheMediaRecorder_DISMISS = 3;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TheMediaRecorder_PREPARED:
				// MediaRecorder准备之后才开始
				isRecording = true;
				mDialog.showDialog();
				new Thread(GetVoiceLevelRunnable).start();

				break;
			case TheMediaRecorder_CHANGED:
				mDialog.changeVoiceLevel(mTheMediaRecorder.getVoiceLevel(14));
				// 注意level Max
				break;
			case TheMediaRecorder_DISMISS:
				mDialog.dismiss();
				break;
			}

		};
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		// x和y都是相对于组件左上角的相对位置

		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (mCurStete != STATE_RECORDING) {
				changeState(STATE_RECORDING);
			}
			break;

		case MotionEvent.ACTION_MOVE:
			if (isRecording) {
				if (wantToCancel(x, y)) {
					changeState(STATE_WANTCANCLE);
					// 傳遞過去
					// mTheMediaRecorder.cancel();
				} else {
					changeState(STATE_RECORDING);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (!isReady) {
				reset();
				// mDialog.dismiss();
				// mHandler.sendEmptyMessage(TheMediaRecorder_DISMISS);
				return super.onTouchEvent(event);
			}
			if (!isRecording) {
				mDialog.tooShort();
				mTheMediaRecorder.cancel();
				mHandler.sendEmptyMessageDelayed(TheMediaRecorder_DISMISS, 1300);
			} else if (mTime < 0.5f) {// 别少了f
				System.out.println("结束时Time：" + mTime);
				mDialog.tooShort();
				mTheMediaRecorder.cancel();
				mHandler.sendEmptyMessageDelayed(TheMediaRecorder_DISMISS, 1300);
			} else if (mCurStete == STATE_RECORDING) {
				mDialog.dismiss();
				mTheMediaRecorder.release();
				if (mListener != null) {
					mListener.FinishedRecordeListener(mTime,
							mTheMediaRecorder.getCurFilePath());
				}
			} else if (mCurStete == STATE_WANTCANCLE) {
				mDialog.dismiss();
				mTheMediaRecorder.cancel();
			}
			reset();
			break;
		}

		return super.onTouchEvent(event);
	}

	private void reset() {
		isReady = false;
		isRecording = false;
		mTime = 0;
		changeState(STATE_NORMAL);

	}

	private boolean wantToCancel(int x, int y) {
		// TODO Auto-generated method stub
		if (x < 0 || x > getWidth()) {
			return true;
		}
		if (y < -DISTANCE_Y_WANTCANCEL
				|| y > getHeight() + DISTANCE_Y_WANTCANCEL) {
			return true;
		}
		return false;
	}

	private void changeState(int STATE) {
		// TODO Auto-generated method stub
		if (mCurStete != STATE) {
			switch (STATE) {
			case STATE_NORMAL:
				// setBackgroundResource(R.drawable.button_background);
				setText(R.string.str_recoder_normal);
				mCurStete = STATE_NORMAL;
				break;
			case STATE_RECORDING:
				// setBackgroundResource(R.drawable.button_click_background);
				setText(R.string.str_recoder_recording);
				mCurStete = STATE_RECORDING;
				if (isRecording) {
					mDialog.recording();
				}
				break;
			case STATE_WANTCANCLE:
				// setBackgroundResource(R.drawable.button_click_background);
				setText(R.string.str_recoder_want_cancel);
				mCurStete = STATE_WANTCANCLE;

				if (isRecording) {
					mDialog.wantToCancel();
				}
				break;
			}
		}
	}
}
