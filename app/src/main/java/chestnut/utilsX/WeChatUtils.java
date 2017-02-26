package chestnut.utilsX;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import chestnut.utils.LogUtils;

/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2017年2月26日
 *     desc  : 微信相关工具类
 *     thanks To :
 *     dependent on:
 * </pre>
 */

public class WeChatUtils {

    private IWXAPI iwxapi = null;
    public static int FLAG_SESSION = 0;
    public static int FLAG_TIMELINE = 1;
    public static int FLAG_COLLECT = 2;

    /**
     * 初始化微信
     */
    public WeChatUtils(Context context, String appKey) {
        iwxapi = WXAPIFactory.createWXAPI(context.getApplicationContext(), appKey, true);
        iwxapi.registerApp(appKey);
    }

    /**
     * 分享文字到微信
     * @param flag  标志：
     * @param txt   文字
     * @return  WeChatUtils
     */
    public WeChatUtils sharedTxt(int flag,String txt) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = txt;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = txt;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = flag;
        boolean bo = iwxapi.sendReq(req);
        LogUtils.e("WeChatUtils:"+bo);
        return this;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
