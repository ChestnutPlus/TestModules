package chestnut.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import testiflytek.test.chestnut.toolslibrary.R;

/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2016年10月30日11:09:31
 *     desc  : 使用Toast作为一个显示通知
 *     thanks To:
 *     dependent on:
 *          Toastc
 *     updateLog：
 *          1.0.0
 * </pre>
 */
public class ToastNote {

    public enum NoteType {
        Note,
        Correct,
        Error,
        Wrong,
    }

    private Toastc toastc = null;
    private TextView toastTxt = null;
    private ImageView toastType = null;

    public ToastNote() {
    }

    /**
     * 构造器
     * @param context   上下文
     */
    public ToastNote(Context context) {
        if (toastc==null ) {
            toastc = new Toastc(context, Toast.LENGTH_SHORT);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.toast_layout,null);
            toastTxt = (TextView) view.findViewById(R.id.toast_txt);
            toastType = (ImageView) view.findViewById(R.id.toast_type);
            toastc.setView(view, Gravity.CENTER);
        }
    }

    /**
     * 显示Toast
     * @param text  文字
     * @param type  Note,Correct,Error,Wrong
     */
    public void show(String text,NoteType type) {
        toastType.setImageLevel(type.ordinal());
        switch (type) {
            case Note:
            case Correct:
                toastTxt.setTextColor(Color.WHITE);
                break;
            case Error:
            case Wrong:
                toastTxt.setTextColor(Color.YELLOW);
                break;
        }
        toastTxt.setText(text);
        toastc.show();
    }

    /**
     * 显示Toast，默认
     * @param text  文字
     */
    public void show(String text) {
        show(text,NoteType.Note);
    }
}
