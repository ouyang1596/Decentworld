/**
 * 
 */
package cn.sx.decentworld.utils;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import cn.sx.decentworld.R;

/**
 * @ClassName: SoundPoolUtils.java
 * @Description:
 * @author: cj
 * @date: 2015年12月14日 下午3:15:27
 */
public class SoundPoolUtils {
	private static SoundPool soundPool;
	private static HashMap<Integer, Integer> mapSrc = new HashMap<Integer, Integer>();

	public static void playShortSound(Context context) {
		if (null == soundPool) {
			soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
			mapSrc.put(1, soundPool.load(context, R.raw.received_message, 0));
		}
		play(1, 0);
	}

	/**
	 * 播放声音池的声音
	 * 
	 * @param sound
	 * @param number
	 */
	private static void play(int sound, int number) {
		soundPool.play(mapSrc.get(sound),// 播放的声音资源
				1.0f,// 左声道，范围为0--1.0
				1.0f,// 右声道，范围为0--1.0
				0, // 优先级，0为最低优先级
				number,// 循环次数,0为不循环
				0);// 播放速率，0为正常速率
	}

	public static void release() {
		if (null != soundPool) {
			soundPool.release();
		}
	}
}
