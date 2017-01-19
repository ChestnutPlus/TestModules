package chestnut.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

import rx.Observable;
import rx.Subscriber;

/**
 * <pre>
 *     author: Chestnut
 *     blog  :
 *     time  : 2017年1月18日
 *     desc  : 下载相关类
 *             封装了：DownloadManager
 *             让其变得简单一点。
 *     thanks To:
 *     android version:
 *     permission:
 *          <uses-permission android:name="android.permission.INTERNET" />                      #网络权限
 *          <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />        #写存储设备权限
 *     dependent on:
 * </pre>
 *
 */
public class DownloadUtils {

    private static final String TAG = "DownloadUtils";
    private static final boolean OpenLog = true;

    private DownloadUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 下载文件
     * @param context   上下文
     * @param url       下载地址
     * @param path      文件存放路径
     * @param fileName  文件名称
     * @param title     下载任务的标题
     * @param mimeType  下载的文件类型 app: application/vnd.android
     * @return  返回下载的任务ID号。
     */
    public static Observable<DownloadStatus> down(Context context, String url, String path, String fileName, String title, String mimeType) {
        Context appContext = context.getApplicationContext();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                .setDestinationInExternalPublicDir(path, fileName)
                .setTitle(title)
                .setMimeType(mimeType)
                .setVisibleInDownloadsUi(true)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDescription(path);
        DownloadManager downloadManager = (DownloadManager) appContext.getSystemService(Context.DOWNLOAD_SERVICE);
        long taskId = downloadManager.enqueue(request);
        ContentObserver[] observer = {null};
        return Observable.create(new Observable.OnSubscribe<DownloadStatus>() {
            @Override
            public void call(Subscriber<? super DownloadStatus> subscriber) {
                observer[0] = new ContentObserver(null) {
                    private boolean isDoing = true;
                    @Override
                    public void onChange(boolean selfChange) {
                        int[] status = getBytesAndStatus(appContext,taskId);

                        switch (status[2]) {
                            //case DownloadManager.STATUS_FAILED:
                            //case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_SUCCESSFUL:
                            case DownloadManager.STATUS_RUNNING:
                                //case DownloadManager.STATUS_PENDING:
                                isDoing = true;
                                break;
                            default:
                                isDoing = false;
                                break;
                        }

                        if (!isDoing)
                            return;

                        if (status[2] == DownloadManager.STATUS_SUCCESSFUL) {
                            appContext.getContentResolver().unregisterContentObserver(observer[0]);
                            subscriber.onCompleted();
                            LogUtils.e(OpenLog,TAG,"onChange-STATUS_SUCCESSFUL-"+taskId);
                        }
                        else
                            subscriber.onNext(new DownloadStatus(taskId,status[1],status[0],status[2]));
                    }
                };
                appContext.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, observer[0]);
            }
        });
    }

    /**
     * 作为返回的 Rx 的传递对象。
     */
    public static class DownloadStatus {
        public long taskId = 0;
        public int fileTotalBytes = 0;
        public int fileDownBytes = 0;
        public int status = 0;

        public DownloadStatus(long taskId, int fileTotalBytes, int fileDownBytes, int status) {
            this.taskId = taskId;
            this.fileTotalBytes = fileTotalBytes;
            this.fileDownBytes = fileDownBytes;
            this.status = status;
        }

        @Override
        public String toString() {
            return "DownloadStatus{" +
                    "taskId=" + taskId +
                    ", fileTotalBytes=" + fileTotalBytes +
                    ", fileDownBytes=" + fileDownBytes +
                    ", status=" + status +
                    '}';
        }
    }

    /**
     * 得到下载的状态
     * @param context       上下文
     * @param downloadId    ID
     * @return  0 : 已经下载的量(bytes) , 1 : 总的大小， 2 ： 下载的状态
     */
    public static int[] getBytesAndStatus(Context context, long downloadId) {
        int[] bytesAndStatus = new int[] { -1, -1, -1};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor c = null;
        try {
            c = downloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return bytesAndStatus;
    }
}

/*
    String url = "http://113.107.216.43/imtt.dd.qq.com/16891/DAB4554776F77B1E051959E730ED1F78.apk?mkey=587f20475137af82&f=d588&c=0&fsname=com.huiyu.honeybot.honeybotapplication_1.0.019_1.apk&csr=4d5s&p=.apk";
        DownloadUtils.down(this,url,Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),"Test.apk","测试","application/vnd.android")
            .subscribe(
            downloadStatus ->
            LogUtils.e(OpenLog,TAG,"next:"+downloadStatus.toString()),
            throwable -> LogUtils.e(OpenLog,TAG,"error:"+throwable.getMessage()),
            ()-> LogUtils.e(OpenLog,TAG,"完成！"));
*/
