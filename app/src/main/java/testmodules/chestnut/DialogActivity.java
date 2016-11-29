package testmodules.chestnut;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chestnut.ui.Toastc;
import chestnut.utils.IntentUtils;
import chestnut.utils.LogUtils;
import testmodules.R;

public class DialogActivity extends Activity {

    private Toastc toastc = null;

    @BindView(R.id.gridView)
    GridView gridView;

    private int[] icons = {
            R.mipmap.icon1,
            R.mipmap.icon2,
            R.mipmap.icon3,
            R.mipmap.icon4,
            R.mipmap.icon5,
    };

    private String[] title = {
            "微信",
            "QQ",
            "支付宝",
            "银行",
            "米聊",
    };

    private AdapterView.OnItemClickListener onItemClickListener = (adapterView, view, i, l) -> {
        toastc.setText("你选了第"+(i+1)+"张图片").show();
        switch (i) {
            case 0:
                break;
            case 1:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                break;
            case 2:
                break;
        }
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//需要添加的语句
        initView();
    }
    private void initView() {
        toastc = new Toastc(this,Toast.LENGTH_LONG);
        //创建一个List对象
        List<Map<String, Object>> listItems = new ArrayList<>();
        for(int i=0;i<icons.length;i++){
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("imageId", icons[i]);
            listItem.put("titleId", title[i]);
            listItems.add(listItem);
        }
        //创建一个SimpleAdapter
        //	List对象中的 imageId 作为 key , 其值为 images[i]
        //	而 Adapter 中，new String[] {} 里面就是 List 对象中的 Key
        //	其作用是作 数据 List 和 UI 的一个映射。
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                listItems,
                R.layout.adapter_item,
                new String[]{"imageId","titleId"},
                new int[]{R.id.img,R.id.title});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(onItemClickListener);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
