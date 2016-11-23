package chestnut.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import testiflytek.test.chestnut.toolslibrary.R;

/**
 * Created by Chestnut on 2016/10/30.
 */

public class DialogNote {

    private AlertDialog.Builder dialog = null;
    private AlertDialog dialogx = null;
    private Button button = null;
    private TextView textView = null;
    private ImageView imageView = null;
    private enum NoteType {
        Correct,
        Error,
        Note,
        Wrong,
    }

    /**
     * 构造器
     * @param activity   上下文
     */
    public DialogNote(Activity activity) {
        if (dialog==null) {
            dialog = new AlertDialog.Builder(activity, R.style.Dialog_note);  //先得到构造器
            dialog.setCancelable(true);
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            View view = layoutInflater.inflate(R.layout.dialog_note, null);
            imageView = (ImageView) view.findViewById(R.id.dialog_note_type);
            textView = (TextView) view.findViewById(R.id.dialog_note_msg);
            button = (Button) view.findViewById(R.id.dialog_note_ok);
            button.setOnClickListener(v -> {
                if (dialogx!=null)
                    dialogx.dismiss();
            });
            dialog.setView(view);
            dialog.create();
        }
    }

    /**
     * 展示
     * @param msg       信息
     * @param type      类型
     */
    private void show(String msg,NoteType type) {
        if (dialog==null)
            return;
        imageView.setImageLevel(type.ordinal());
        textView.setText(msg);
        if (dialogx==null)
            dialogx = dialog.show();
        else
            dialogx.show();
    }

    public void showCorrect(String msg) {
        show(msg, NoteType.Correct);
    }

    public void showError(String msg) {
        show(msg, NoteType.Error);
    }

    public void showNote(String msg) {
        show(msg, NoteType.Note);
    }

    public void showWrong(String msg) {
        show(msg, NoteType.Wrong);
    }
}