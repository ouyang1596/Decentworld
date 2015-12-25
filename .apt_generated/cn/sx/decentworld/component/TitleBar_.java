//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.component;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class TitleBar_
    extends TitleBar
{

    private Context context_;

    private TitleBar_(Context context) {
        context_ = context;
        init_();
    }

    public void afterSetContentView_() {
        if (!(context_ instanceof Activity)) {
            return ;
        }
        main_header_right_btn = ((ImageView) findViewById(cn.sx.decentworld.R.id.main_header_right_btn));
        main_header_left = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.main_header_left));
        main_header_right_tv = ((TextView) findViewById(cn.sx.decentworld.R.id.main_header_right_tv));
        main_header_title = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_header_title));
        main_header_left_tv = ((TextView) findViewById(cn.sx.decentworld.R.id.main_header_left_tv));
        ((ToastComponent_) toast).afterSetContentView_();
        init();
    }

    /**
     * You should check that context is an activity before calling this method
     * 
     */
    public View findViewById(int id) {
        Activity activity_ = ((Activity) context_);
        return activity_.findViewById(id);
    }

    @SuppressWarnings("all")
    private void init_() {
        if (context_ instanceof Activity) {
            Activity activity = ((Activity) context_);
        }
        if (context_ instanceof Activity) {
            activity = ((Activity) context_);
        }
        context = context_;
        toast = ToastComponent_.getInstance_(context_);
    }

    public static TitleBar_ getInstance_(Context context) {
        return new TitleBar_(context);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
