//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.component.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import cn.sx.decentworld.component.ToastComponent_;

public final class LoginComponent_
    extends LoginComponent
{

    private Context context_;

    private LoginComponent_(Context context) {
        context_ = context;
        init_();
    }

    public void afterSetContentView_() {
        if (!(context_ instanceof Activity)) {
            return ;
        }
        ((ToastComponent_) toastComponent).afterSetContentView_();
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
        toastComponent = ToastComponent_.getInstance_(context_);
    }

    public static LoginComponent_ getInstance_(Context context) {
        return new LoginComponent_(context);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
