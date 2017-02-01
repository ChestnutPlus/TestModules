package chestnut.ui;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import testiflytek.test.chestnut.toolslibrary.R;

/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2016年10月20日16:28:09
 *     desc  : DialogLoading
 *     thanks To:
 *     dependent on:
 *     updateLog：
 *          1.0.0   初始化
 *          1.0.1   2017年1月31日23:36:02  by  栗子
 *                  1.  删除一些方法
 *                  2.  新增Rx订阅版的ShowLoading
 *          1.0.2   2017年2月1日15:33:05   by  栗子
 *                  1.  新增dismiss方法
 * </pre>
 */

public class DialogLoading {

    private Dialog dialog = null;
    private TextView dialogTxt = null;
    private LinearLayout linearLayout = null;
    private Subscriber<? super Integer> subscriber = null;

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
     * Rx监听Loading
     * @param text  msg
     * @param outSideCancel     点击外部是否能取消
     * @param backPressCancel   点击返回键是否能取消
     * @param autoDismissTime   超时时间/   小于零默认无限，单位毫秒
     * @return  Observable<Integer> -1 超时，1 用户点击退出。0 :代码中退出。
     */
    public Observable<Integer> rxShow(String text,boolean outSideCancel, boolean backPressCancel,int autoDismissTime) {
        linearLayout.setBackgroundResource(R.drawable.toast_bg);
        dialogTxt.setText(text);
        dialog.setCanceledOnTouchOutside(outSideCancel);
        dialog.setCancelable(backPressCancel);
        dialog.show();
        if (autoDismissTime>0)
            return Observable.create(new Observable.OnSubscribe<Integer>() {
                @Override
                public void call(Subscriber<? super Integer> subscriber) {
                    DialogLoading.this.subscriber = subscriber;
                    dialog.setOnCancelListener(dialogInterface -> {
                        subscriber.onNext(1);
                        subscriber.onCompleted();
                        DialogLoading.this.subscriber = null;
                        dialog.dismiss();
                    });
                }
            }).timeout(autoDismissTime, TimeUnit.MILLISECONDS,
                    Observable.just(-1).map(integer -> {
                        DialogLoading.this.subscriber = null;
                        dialog.dismiss();
                        return integer;
                    }));
        else
            return Observable.create(subscriber -> {
                DialogLoading.this.subscriber = subscriber;
                dialog.setOnCancelListener(dialogInterface -> {
                    subscriber.onNext(1);
                    subscriber.onCompleted();
                    DialogLoading.this.subscriber = null;
                    dialog.dismiss();
                });
            });
    }

    /**
     * 取消Loading
     */
    public void dimiss() {
        if (DialogLoading.this.subscriber != null && dialog!=null && dialog.isShowing()) {
            subscriber.onNext(0);
            subscriber.onCompleted();
            DialogLoading.this.subscriber = null;
            dialog.dismiss();
        }
    }
}
