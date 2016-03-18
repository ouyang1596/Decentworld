package cn.sx.decentworld.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import cn.sx.decentworld.R;

public class SlipButton extends View implements OnTouchListener
{
    private static final String TAG = "SlipButton";
    // 记录当前按钮是否打开,true为打开,flase为关闭
    private boolean NowChoose = false;
    // 记录用户是否在滑动
    private boolean isSlip = false;
    // 按下时的x,当前的x,
    private float DownX, NowX;
    // 打开和关闭状态下,游标的Rect
    private Rect Btn_On, Btn_Off;
    //
    private boolean isChgLsnOn = false;
    // 滑动事件监听
    private SlipButtonChangeListener slipButtonChangeListener;
    // 图片资源
    private Bitmap slip_btn;
    private Bitmap slip_bg, slip_bg_family, slip_bg_friend, slip_bg_all;
    // 选择第几个
    private int num = -1;
    public static final int SWITCH_NO = -1;
    public static final int SWITCH_FAMILY = 2;
    public static final int SWITCH_FRIEND = 1;
    public static final int SWITCH_ALL = 0;
    //是否选择完成
    private boolean isFinish = false;

    /**
     * 构造函数
     * 
     * @param context
     */
    public SlipButton(Context context)
    {
        this(context, null);
    }

    /**
     * 构造函数
     * 
     * @param context
     * @param attrs
     */
    public SlipButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init()
    {
        slip_btn = BitmapFactory.decodeResource(getResources(), R.drawable.slip_btn);
        slip_bg = BitmapFactory.decodeResource(getResources(), R.drawable.slip_bg);
        slip_bg_family = BitmapFactory.decodeResource(getResources(), R.drawable.slip_bg_family);
        slip_bg_friend = BitmapFactory.decodeResource(getResources(), R.drawable.slip_bg_friend);
        slip_bg_all = BitmapFactory.decodeResource(getResources(), R.drawable.slip_bg_all);
        // 打开开关时，游标的显示的区域
        Btn_On = new Rect(0 , 0 , slip_btn.getWidth() , slip_btn.getHeight());
        // 关闭开关时，游标显示的区域
        Btn_Off = new Rect(slip_bg.getWidth() - slip_btn.getWidth() , 0 , slip_bg.getWidth() , slip_btn.getHeight());
        // 设置监听器,也可以直接复写OnTouchEvent
        setOnTouchListener(this);
    }

    /**
     * 绘制函数
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        Log.e(TAG, "onDraw");
        // 绘图函数
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x;
        {
            if (isSlip)// 是否是在滑动状态
            {
                int state = xState(NowX);
                if(state == SWITCH_NO)
                {
                    // 画出"无"时的背景
                    canvas.drawBitmap(slip_bg, matrix, paint);
                }else if(state ==SWITCH_FAMILY)
                {
                    // 画出"亲"时的背景
                    canvas.drawBitmap(slip_bg_family, matrix, paint);
                }else if(state == SWITCH_FRIEND)
                {
                    // 画出"朋"时的背景
                    canvas.drawBitmap(slip_bg_friend, matrix, paint);
                }else if(state == SWITCH_ALL)
                {
                 // 画出"全"时的背景
                    canvas.drawBitmap(slip_bg_all, matrix, paint);
                }

                //确定游标位置
                if (NowX >= slip_bg.getWidth())// 是否划出指定范围,不能让游标跑到外头,必须做这个判断
                    x = slip_bg.getWidth() - slip_btn.getWidth() / 2;// 减去游标1/2的长度...
                else
                    x = NowX - slip_btn.getWidth() / 2;
            }
            else
            {
                // 非滑动状态
                if (num == SWITCH_NO)
                {
                    canvas.drawBitmap(slip_bg, matrix, paint);
                }
                else if (num == SWITCH_FAMILY)
                {
                    canvas.drawBitmap(slip_bg_family, matrix, paint);
                }
                else if (num == SWITCH_FRIEND)
                {
                    canvas.drawBitmap(slip_bg_friend, matrix, paint);
                }
                else
                {
                    canvas.drawBitmap(slip_bg_all, matrix, paint);
                }
                x = 0;
            }

            // 对游标位置进行异常判断...
            if (x < 0)
            {
                x = 0;
            }
            else if (x > slip_bg.getWidth() - slip_btn.getWidth())
            {
                x = slip_bg.getWidth() - slip_btn.getWidth();
            }
            // 画出游标.
            canvas.drawBitmap(slip_btn, x, 0, paint);
        }
    }

    /**
     * 触摸事件
     */
    public boolean onTouch(View v, MotionEvent event)
    {
        // 根据动作来执行代码
        switch (event.getAction())
        {
        // 滑动
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ACTION_MOVE");
                NowX = event.getX();
                if (isChgLsnOn)
                    slipButtonChangeListener.OnChanged(true,xState(event.getX()),false);
                break;
            // 按下
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN");
                if (event.getX() < 0 || event.getX() > slip_bg.getWidth() || event.getY() < 0 || event.getY() > slip_bg.getHeight())
                    return false;
                isSlip = true;
                DownX = event.getX();
                NowX = event.getX();
                if (isChgLsnOn)
                    slipButtonChangeListener.OnChanged(true,xState(event.getX()),false);
                break;
            // 松开
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP");
                isSlip = false;
                SlipButton.this.measure(0, 0);
                // 如果设置了监听器,就调用其方法..
                if (isChgLsnOn)
                    slipButtonChangeListener.OnChanged(false,xState(event.getX()),true);
                num = SWITCH_NO;
                break;
            // 取消，不会由用户直接触发
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "ACTION_CANCEL");
                int m = (Btn_Off.right + Btn_On.left) / 2;
                isSlip = false;
                int LastChoose = num;
                SlipButton.this.measure(0, 0);
                int w = slip_bg.getWidth();
                if (event.getX() >= (m))
                    NowChoose = true;
                else
                    NowChoose = false;
                if (isChgLsnOn && (LastChoose != num))// 如果设置了监听器,就调用其方法..
                    slipButtonChangeListener.OnChanged(false,LastChoose,false);
                break;
            default:
        }
        // 重画控件
        invalidate();
        return true;
    }

    /**
     * 设置以前发选择
     * 
     * @param HistoryChosen
     */
    public void SetHistoryChosen(int HistoryChosen)
    {
        num = HistoryChosen;
//        slipButtonChangeListener.OnChanged(num);
        invalidate();
    }

    /**
     * 设置监听事件
     * 
     * @param listener
     */
    public void SetOnChangedListener(SlipButtonChangeListener listener)
    {
        // 设置监听器,当状态修改的时候
        isChgLsnOn = true;
        slipButtonChangeListener = listener;
    }

    /**
     * 监听
     * 
     * @author Administrator
     */
    public interface SlipButtonChangeListener
    {
        abstract void OnChanged(boolean isPress,int checkState,boolean isFinish);
    }
    
    /**
     * 判断当前游标在x处开关的状态
     * @param x
     * @return
     */
    private int xState(float x)
    {
        int selectState = 0;
        int slipBtnWidth = slip_btn.getWidth();
        int width = (slip_bg.getWidth() - slip_btn.getWidth())/3;
        int x1 = 0;
        int x2 = slipBtnWidth+slip_btn.getWidth()/2;
        int x3 = slipBtnWidth + width + slip_btn.getWidth()/2;
        int x4 = slipBtnWidth +width*2 +slip_btn.getWidth()/2;
        int x5 = slip_bg.getWidth();
        
        if(x>=x1&&x<x2)
        {
            //没有选择
            selectState = SWITCH_NO;
        }else if(x>=x2&&x<x3)
        {
            //选择“亲”
            selectState = SWITCH_FAMILY;
        }else if(x>=x3&&x<x4)
        {
          //选择“朋”
            selectState = SWITCH_FRIEND;
        }else if(x>=x4)
        {
          //选择“全”
            selectState = SWITCH_ALL;
        }
        
        return selectState;
    }
    
    private void drawSlipBtn()
    {
        
    }

}
