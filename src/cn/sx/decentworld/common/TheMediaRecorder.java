/**
 * 
 */
package cn.sx.decentworld.common;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import cn.sx.decentworld.utils.FileUtils;
import android.media.MediaRecorder;

/**
 * @ClassName: TheMediaRecorder.java
 * @Description:
 * @author: dyq
 * @date: 2015年9月22日 下午12:33:56
 */
public class TheMediaRecorder extends MediaRecorder {
	private String Dir;
	private MediaRecorder mMediaRecorder;
	private String mCurFile;
	private boolean isprepared = false;

	private TheMediaRecorder(String dir) {
		Dir = dir;
	}

	public static TheMediaRecorder getInstance(String mDir) {
		TheMediaRecorder rr = new TheMediaRecorder(mDir);
		return rr;
	}

	/*
	 * 回调借口 *
	 */
	public AudioParedListener mAudioParedListener;

	public interface AudioParedListener {
		public void wellPared();
	}

	/*
	 * 回调函数，当TheMediaRecorder准备好时回调回Button *
	 */
	public void setAudioListener(AudioParedListener rr) {
		mAudioParedListener = rr;
	}

	public void prepareAudio() {

		// 注意 文件路徑，文件名
		File dir = new File(Dir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String fileName = System.currentTimeMillis() + ".mp3";
		File file = new File(dir, fileName);
		mCurFile = file.getAbsolutePath();
		mMediaRecorder = new MediaRecorder();

		mMediaRecorder.setOutputFile(file.getAbsolutePath());

		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);

		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		try {
			mMediaRecorder.prepare();
			mMediaRecorder.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mAudioParedListener != null) {
			mAudioParedListener.wellPared();
			isprepared = true;
		}
	}

	public void release() {
		if (mMediaRecorder != null) {
			mMediaRecorder.reset();
			mMediaRecorder = null;
		}
	}

	public void cancel() {
		release();
		File file = new File(mCurFile);
		file.delete();
	}

	public int getVoiceLevel(int maxLevel) {
		if (isprepared) {
			try {
				int j = (maxLevel * mMediaRecorder.getMaxAmplitude() / 32768) + 1;
				System.out.println("voice" + j);
				return j;
			} catch (Exception e) {

			}
		}
		return 1;

	}

	public String getCurFilePath() {
		return mCurFile;
	}
}
