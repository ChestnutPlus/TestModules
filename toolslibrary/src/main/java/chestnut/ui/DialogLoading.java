package chestnut.ui;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import testiflytek.test.chestnut.toolslibrary.R;

/**
 * Created by Chestnut on 2016/10/30.
 */

public class DialogLoading {

    private Dialog dialog = null;
    private TextView dialogTxt = null;
    private static Subscription subscription = null;
    private LinearLayout linearLayout = null;

    public DialogLoading() {
    }

    /**
     * 传入Activity初始化
     * @param activity  必须，因为要设置背景不变暗。
     */
    public DialogLoading(Activity activity) {
        if (dialog==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            View view = layoutInflater.inflate(R.layout.dialog_loading, null);
            dialogTxt = (TextView) view.findViewById(R.id.loading_txt);
            linearLayout = (LinearLayout) view.findViewById(R.id.dialog_loading_layout);
            dialog = new Dialog(activity,R.style.Dialog_loading);
            dialog.setContentView(view);
        }
    }

    /**
     * 显示Loading
     * @param text  文字
     * @param cancelable   loading是否能取消
     * @param autoDismissTime   loading自动取消时间，单位：秒，传入-1/0表示无限时间
     */
    public void show(String text,boolean cancelable,int autoDismissTime) {
        linearLayout.setBackgroundResource(R.drawable.toast_bg);
        dialogTxt.setText(text);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.setCancelable(cancelable);
        dialog.show();
        if (subscription!=null)
            subscription.unsubscribe();
        if (autoDismissTime>0) {
            Observable observable = Observable.timer(autoDismissTime, TimeUnit.SECONDS);
            subscription = observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                        if (dialog.isShowing())
                            dialog.dismiss();
                    });
        }
    }

    /**
     * 显示Loading，没有背景和文字。
     * @param cancelable 能否点击取消
     * @param autoDismissTime loading自动取消时间，单位：秒，传入-1/0表示无限时间
     */
    public void showWithoutBg(boolean cancelable,int autoDismissTime) {
        linearLayout.setBackgroundColor(Color.TRANSPARENT);
        dialogTxt.setText("");
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.setCancelable(cancelable);
        dialog.show();
        if (subscription!=null)
            subscription.unsubscribe();
        if (autoDismissTime>0) {
            Observable observable = Observable.timer(autoDismissTime, TimeUnit.SECONDS);
            subscription = observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                        if (dialog.isShowing())
                            dialog.dismiss();
                    });
        }
    }

    /**
     * 取消Loading
     */
    public void dismiss() {
        if (subscription!=null)
            subscription.unsubscribe();
        dialogTxt.setText("");
        if (dialog.isShowing())
            dialog.dismiss();
    }
}
