package testmodules.chestnut;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chestnut.rx.RxBus;
import chestnut.rx.RxEvent;
import chestnut.ui.Toastc;
import chestnut.utils.LogUtils;
import testmodules.R;

public class Main2Activity extends RxAppCompatActivity {

    GridView gridView;

    Toastc toastc = null;

    String[] strings = {
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9"
    };

    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/mp3/";
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        toastc = new Toastc(this, Toast.LENGTH_SHORT);
        //创建一个List对象
        List<Map<String, Object>> listItems = new ArrayList<>();
        for(int i=0;i<strings.length;i++){
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("txt", strings[i]);
            listItems.add(listItem);
        }
        //创建一个SimpleAdapter
        //	List对象中的 imageId 作为 key , 其值为 images[i]
        //	而 Adapter 中，new String[] {} 里面就是 List 对象中的 Key
        //	其作用是作 数据 List 和 UI 的一个映射。
        gridView = (GridView) findViewById(R.id.gridView);
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                listItems,
                android.R.layout.simple_list_item_1,
                new String[]{"txt"},
                new int[]{android.R.id.text1});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(onItemClickListener);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private AdapterView.OnItemClickListener onItemClickListener = (adapterView, view, i, l) -> {
        toastc.setText(strings[i]+"_"+l).show();
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(path+l+".mp3");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            LogUtils.e("io:"+e.getMessage());
            e.printStackTrace();
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    try {
                        mediaPlayer.setDataSource(path+msg.arg1+".mp3");
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        LogUtils.e("io:"+e.getMessage());
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @OnClick(R.id.btn)
    public void btn(Button button) {

        RxBus.getDefault().toObserverable(RxEvent.class)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .map(rxEvent -> {
                    LogUtils.e("1:"+rxEvent.toString());
                    return rxEvent.toString();
                })
                .subscribe();

//        long temp = 96742038;
//        for (int i = 0; i < 8; i++) {
//            Message message = new Message();
//            message.what = 0x01;
//            message.arg1 = (int) (temp / Math.pow(10,7-i));
//            temp -= message.arg1 * Math.pow(10,7-i);
//            message.arg2 = i;
//            handler.sendMessageDelayed(message,600*(i));
//        }
    }
}
