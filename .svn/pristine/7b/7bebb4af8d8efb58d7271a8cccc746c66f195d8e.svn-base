package cn.sx.decentworld.abstractclass;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.widget.ImageView;

/**
 * 
 * @ClassName: AbstractPlayListener
 * @Description: 播放语音监听回调
 * @author: Jackchen
 * @date: 2016年5月7日 下午4:49:57
 */
public abstract class AbstractPlayListener implements OnCompletionListener
{
    private int position;
    private ImageView ivVoice;
    public AbstractPlayListener(int position,ImageView ivVoice)
    {
        this.position = position;
        this.ivVoice = ivVoice;
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        onFinish(mp, ivVoice);
    }

    public abstract void onFinish(MediaPlayer mp, ImageView ivVoice);

    public abstract void play(int position,int duration);

    public abstract void stop(ImageView ivVoice);
}
