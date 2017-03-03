package testmodules.VIewPager.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *      我们自定义的ViewHolder类可以根据布局文件的id或具体的itemView返回一个ViewHolder对象，
 *      Created by Chestnut on 2016/9/8.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private boolean isSetListener = false;

    /**
     *      2个 ViewHolder 的构造方法
     * @param itemView  传入布局文件
     */
    private BaseViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }
    public static BaseViewHolder create(Context context, int layoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseViewHolder(itemView);
    }
    public static BaseViewHolder create(View itemView) {
        return new BaseViewHolder(itemView);
    }


    /**
     *      并用SparseArray来缓存我们itemView中的子View，
     *      避免每次都要去解析子View，同时提供相关辅助方法
     *      设置itemView的内容。
     *
     * @param viewId    view 的 ID
     * @param <T>       T   泛型
     * @return          返回 View ，需强制类型转换
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    /**
     *      得到布局的 View
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }



    public boolean isSetListener() {
        return isSetListener;
    }

    public void setSetListener(boolean setListener) {
        isSetListener = setListener;
    }
}
