package com.capton.baseapp.upload;

import android.content.Context;

import com.capton.baseapp.bean.SavedPicBean;
import com.capton.capton.R;
import com.capton.common.base.JsonUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by capton on 2018/3/6.
 */

public class QiniuUploadUtil {
    public static interface UploadListener{
        void progress(double percent,String key);
        void complete(String key,ResponseInfo info,String url);
    }
    public static interface TokenListener{
        void tokenGet(String token);
    }
    public static UploadManager uploadManager;
    static {
        Configuration config = new Configuration.Builder()
                .zone(FixedZone.zone2)
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    public static void getUpLoadToken(Context context,String key, final TokenListener tokenListener){
        OkGo.<String>post(context.getResources().getString(R.string.baseurl)+
                context.getResources().getString(R.string.getUploadToken))
                .params("key",key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                         if(tokenListener!=null){
                            tokenListener.tokenGet(response.body());
                         }
                    }
                });
    }

    public static void upload(File file,String key, String token, final UploadListener listener){
         uploadManager.put(file,key,token,new UpCompletionHandler(){

            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
               //res包含hash、key等信息，具体字段取决于上传策略的设置
              /*  if(info.isOK()) {
                    Log.i("qiniu", "Upload Success");
                } else {
                    Log.i("qiniu", "Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }*/
              SavedPicBean savedPicBean = (SavedPicBean) JsonUtil.strToObject(JsonUtil.objToString(response),SavedPicBean.class);
              String url = savedPicBean.getNameValuePairs().getUrl() + key;
              if(listener!=null)
                    listener.complete(key,info,url);
             }
        },new UploadOptions(null, null, false,
                 new UpProgressHandler(){
                     public void progress(String key, double percent){
                       //  Log.i("qiniu", key + ": " + percent);
                         if(listener!=null)
                             listener.progress(percent,key);

                     }
                 }, null));
    }

    public static String  randomFileName(String username){
        long current = System.currentTimeMillis();
        return username + current;
    }
}
