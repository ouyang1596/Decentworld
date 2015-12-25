/**
 * 
 */
package cn.sx.decentworld.manager;

import java.util.HashMap;

import cn.sx.decentworld.R;
import cn.sx.decentworld.utils.LogUtils;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * @ClassName: AudioManager.java
 * @Description: 声音管理
 * 1.消息提示音
 * 2.系统推送提示音
 * @author: cj
 * @date: 2015年12月3日 下午5:50:20
 */
public class DWAudioManager
{
	private static final String TAG = "DWAudioManager";
	private Context context;
	private SoundPool pool;
	private HashMap<Integer, Integer> spMap;
	
	/**
	 * 声音种类
	 */
	public static final int RECEIVE_MESSAGE = 1;//接收消息
	/**
	 * 构造函数
	 */
	public DWAudioManager(Context context)
	{
		this.context = context;
		initSound();
	}
	
	private void initSound()
	{
		pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		spMap = new HashMap<Integer, Integer>();
		spMap.put(RECEIVE_MESSAGE, pool.load(context, R.raw.received_message, 1));
	}
	
	/**
	 * 播放声音
	 * @param soundType 声音种类，DWAudioManager中定义的
	 * @param loopCount 循环次数
	 */
	public void playSound(int soundType,int loopCount)
	{
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volumnRatio = volumnCurrent / audioMaxVolumn;
		pool.play(spMap.get(soundType), volumnRatio, volumnRatio, 1, loopCount,  1f);
	}
	
	
	/**
	 * 播放声音
	 * @param soundType 声音种类，DWAudioManager中定义的
	 * 默认播放一次
	 */
	public void playSound(int soundType)
	{
		LogUtils.i(TAG, "播放声音");
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volumnRatio = volumnCurrent / audioMaxVolumn;
		pool.play(spMap.get(soundType), volumnRatio, volumnRatio, 1, 1,  1f);
	}
}
