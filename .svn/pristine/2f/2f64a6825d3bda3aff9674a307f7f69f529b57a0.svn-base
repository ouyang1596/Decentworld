package cn.sx.decentworld.dialog;

import java.io.File;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sx.decentworld.R;
import cn.sx.decentworld.activity.PictureChoiceActivity_;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent;
import cn.sx.decentworld.component.ToastComponent;
import cn.sx.decentworld.utils.ViewUtil;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @ClassName: RegisterThreeItemDialogFragment.java
 * @Description:
 * @author: dyq
 * @date: 2015年7月22日 下午3:51:37
 */
@EFragment(R.layout.activity_register_three_item)
public class RegisterThreeItemDialogFragment extends DialogFragment implements OnClickListener {
    
    // TextView tv_three_item_info;
    static final public int MONEY_INFO = 1;
    static final public int ABILITY_INFO = 2;
    static final public int FACE_INFO = 3;
    // 默认为0，为还没设置图片,当前最新设了图片的选项
    private int NOW_PIC_INDEX = 0;
    private boolean tocamera = false;
    private int had_set_bitmap = 0;
    
    @ViewById(R.id.root_activity_register_three_item_dialog)
    RelativeLayout root_activity_register_three_item_dialog;
    
    @ViewById(R.id.rl_have_face)
    RelativeLayout rl_have_face;
    
    @ViewById(R.id.bt_have_money)
    Button bt_have_money;
    
    @ViewById(R.id.bt_have_ability)
    Button bt_have_ability;
    
    @ViewById(R.id.bt_have_face)
    Button bt_have_face;
    
    @ViewById(R.id.activity_register_three_item_dialog_back)
    RelativeLayout activity_register_three_item_dialog_back;
    
    @ViewById(R.id.activity_register_three_item_dialog_next)
    RelativeLayout activity_register_three_item_dialog_next;
    @ViewById(R.id.tv_three_item_info)
    TextView tv_three_item_info;
    
    @ViewById(R.id.rl_have_money)
    RelativeLayout rl_have_money;
    
    @ViewById(R.id.rl_have_ability)
    RelativeLayout rl_have_ability;
    
    @ViewById(R.id.imageaaa)
    ImageView imageaaa;
    
    @ViewById(R.id.iv_have_money)
    ImageView iv_have_money;
    
    @ViewById(R.id.iv_have_ability)
    ImageView iv_have_ability;
    
    @ViewById(R.id.iv_have_face)
    ImageView iv_have_face;
    
    private RegisterThreeItemCallbackListener listener;
    
    @Bean
    ChoceAndTakePictureComponent ChoceAndTakePictureComponent;
    
    @Bean
    ToastComponent toast;
    
    @AfterViews
    public void init() {
        ViewUtil.scaleContentView(root_activity_register_three_item_dialog);
        bt_have_money.setOnClickListener(this);
        bt_have_ability.setOnClickListener(this);
        bt_have_face.setOnClickListener(this);
        activity_register_three_item_dialog_back.setOnClickListener(this);
        rl_have_money.setOnClickListener(this);
        rl_have_ability.setOnClickListener(this);
        rl_have_face.setOnClickListener(this);
    }
    
    /**
     * 点击注册按钮
     */
    @Click(R.id.activity_register_three_item_dialog_next)
    public void donext() {
        if (had_set_bitmap == 1 || had_set_bitmap == 2 || had_set_bitmap == 3) {
            listener.forBitmapSure();
            toast.show("正在上传图片...");
            Log.i("dw", "正在上传图片...");
            activity_register_three_item_dialog_next.setEnabled(false);
        }
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog m_dialog = new Dialog(getActivity(), R.style.ShouchangDialog);
        m_dialog.show();
        return m_dialog;
    }
    
    @Override
    public void onClick(View v) {
        
        if (listener != null) {
            switch (v.getId()) {
                case R.id.activity_register_three_item_dialog_back:
                    listener.oncancel();
                    break;
                default:
                    break;
            }
            
            if (!tocamera) {
                tocamera = true;
                switch (v.getId()) {
                    case R.id.bt_have_money:
                        kind_photo(1);
                        // first_click = 1;
                        break;
                    case R.id.bt_have_ability:
                        kind_photo(2);
                        // first_click = 2;
                        break;
                    case R.id.bt_have_face:
                        kind_photo(3);
                        // first_click = 3;
                        break;
                    default:
                        break;
                }
            } else {
                int ii = 0;
                switch (v.getId()) {
                // 不同认证进入不同拍照
                    case R.id.rl_have_money:
                        // if(had_set_bitmap==1){
                        // listener.forBitmapSure();
                        // break;
                        // }
                        take_photo(1);
                        break;
                    case R.id.rl_have_ability:
                        // if(had_set_bitmap==2){
                        // listener.forBitmapSure();
                        // break;
                        // }
                        take_photo(2);
                        break;
                    case R.id.rl_have_face:
                        // if(had_set_bitmap==3){
                        // listener.forBitmapSure();
                        // break;
                        // }
                        take_photo(3);
                        break;
                    case R.id.bt_have_money:
                        kind_photo(1);
                        break;
                    case R.id.bt_have_ability:
                        kind_photo(2);
                        break;
                    case R.id.bt_have_face:
                        kind_photo(3);
                        break;
                    default:
                        break;
                }
                // if (ii != first_click) {
                // re_back();
                // switch (v.getId()) {
                // case R.id.bt_have_money:
                // first_click = 1;
                // kind_photo(1);
                // break;
                // case R.id.bt_have_ability:
                // first_click = 2;
                // kind_photo(2);
                // break;
                // case R.id.bt_have_face:
                // kind_photo(3);
                // first_click = 3;
                // break;
                // default:
                // break;
                // }
                // } else {
                //
                // }
            }
            
        }
    }
    
    private void re_back() {
        bt_have_money.setVisibility(View.VISIBLE);
        rl_have_money.setVisibility(View.GONE);
        bt_have_ability.setVisibility(View.VISIBLE);
        rl_have_ability.setVisibility(View.GONE);
        bt_have_face.setVisibility(View.VISIBLE);
        rl_have_face.setVisibility(View.GONE);
    }
    
    public void kind_photo(int kind) {
        re_back();
        tv_three_item_info.setVisibility(View.VISIBLE);
        switch (kind) {
            case 1:
                bt_have_money.setVisibility(View.GONE);
                rl_have_money.setVisibility(View.VISIBLE);
                tv_three_item_info.setText(R.string.money_info);
                // Intent intent = new Intent(getActivity(),
                // PictureChoiceActivity_.class);
                // intent.putExtra("which", getActivity().getLocalClassName());
                // startActivityForResult(intent, 2001);
                
                break;
            case 2:
                bt_have_ability.setVisibility(View.GONE);
                rl_have_ability.setVisibility(View.VISIBLE);
                tv_three_item_info.setText(R.string.ability_info);
                break;
            case 3:
                bt_have_face.setVisibility(View.GONE);
                rl_have_face.setVisibility(View.VISIBLE);
                tv_three_item_info.setText(R.string.face_info);
                break;
            default:
                break;
        }
        
    }
    
    public void take_photo(int kind) {
        Intent intent = new Intent(getActivity(), PictureChoiceActivity_.class);
        switch (kind) {
            case 1:
                intent.putExtra("which", getActivity().getLocalClassName());
                startActivityForResult(intent, MONEY_INFO);
                break;
            case 2:
                intent.putExtra("which", getActivity().getLocalClassName());
                startActivityForResult(intent, ABILITY_INFO);
                break;
            case 3:
                intent.putExtra("which", getActivity().getLocalClassName());
                startActivityForResult(intent, FACE_INFO);
                break;
            default:
                break;
        }
    }
    
    public void clear_all_pic() {
        iv_have_money.setImageResource(R.drawable.login_three_item_only_pic_bg);
        iv_have_ability.setImageResource(R.drawable.login_three_item_only_pic_bg);
        iv_have_face.setImageResource(R.drawable.login_three_item_only_pic_bg);
    }
    
    public void setListener(RegisterThreeItemCallbackListener listener) {
        this.listener = listener;
    }
    
    public interface RegisterThreeItemCallbackListener {
        public void oncancel();
        
        // public void kind_photo(int kind);
        // public void take_photo();
        public void forBitmapSure();
        
        public void getPicture(File file, String switch_info);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 以下图片由于展示固定大小，上传图片的图片为
        // File file = (File) data.getSerializableExtra("values");
        // BitmapFactory.Options original_options = new BitmapFactory.Options();
        // int inSampleSize = 2;
        // original_options.inJustDecodeBounds = false;
        // original_options.inSampleSize = inSampleSize;
        // Bitmap original_bitmap = BitmapFactory.decodeFile(
        // file.getAbsolutePath(), original_options);
        
        super.onActivityResult(requestCode, resultCode, data);
        if (MONEY_INFO == requestCode) {
            try {
                File file = (File) data.getSerializableExtra("values");
                if (file != null) {
                    Bitmap resizeBitmap = getBitmap(file);
                    
                    if (NOW_PIC_INDEX != 1) {
                        clear_all_pic();
                        NOW_PIC_INDEX = 1;
                    }
                    iv_have_money.setImageBitmap(resizeBitmap);
                    had_set_bitmap = 1;
                    listener.getPicture(file, "1");
                    
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "请再次选择！", 0).show();
            }
            
        } else if (ABILITY_INFO == requestCode) {
            try {
                File file = (File) data.getSerializableExtra("values");
                if (file != null) {
                    
                    Bitmap resizeBitmap = getBitmap(file);
                    
                    if (NOW_PIC_INDEX != 2) {
                        clear_all_pic();
                        NOW_PIC_INDEX = 2;
                    }
                    iv_have_ability.setImageBitmap(resizeBitmap);
                    had_set_bitmap = 2;
                    listener.getPicture(file, "2");
                    
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "请再次选择！", 0).show();
            }
        } else {
            try {
                File file = (File) data.getSerializableExtra("values");
                if (file != null) {
                    Bitmap resizeBitmap = getBitmap(file);
                    
                    if (NOW_PIC_INDEX != 3) {
                        clear_all_pic();
                        NOW_PIC_INDEX = 3;
                    }
                    iv_have_face.setImageBitmap(resizeBitmap);
                    had_set_bitmap = 3;
                    listener.getPicture(file, "3");
                    
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "请再次选择！", 0).show();
            }
        }
    }
    
    /**
     *将文件转换成固定大小的图片
     * @param file
     * @return
     */
    private Bitmap getBitmap(File file) {
        Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        float scaleWidth = (float) 120 / bmpWidth; // 按固定大小缩放 sWidth
                                                   // 写多大就多大
        float scaleHeight = (float) 120 / bmpHeight; //
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 产生缩放后的Bitmap对象
        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, false);
        bitmap.recycle();
        return resizeBitmap;
    }
    
}
