package com.capton.baseapp.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.capton.baseapp.ImagePickerUtil;
import com.capton.baseapp.bean.UpdateUserResult;
import com.capton.baseapp.upload.QiniuUploadUtil;
import com.capton.capton.R;
import com.capton.capton.databinding.ActivityAvaterBinding;
import com.capton.common.base.BaseActivity;
import com.capton.common.base.JsonUtil;
import com.capton.common.base.SpConstant;
import com.capton.common.user.User;
import com.capton.ep.EasyPermission;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.qiniu.android.http.ResponseInfo;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import static com.capton.baseapp.ImagePickerUtil.IMAGE_PICKER;

/**
 * Created by capton on 2018/3/6.
 */

public class AvaterActivity extends BaseActivity <ActivityAvaterBinding>{

    User user;
    @Override
    protected void yourOperation() {
         setTitle("用户头像");
         setRightText("更换头像");
         setShowRightText(true);
         baseBinding.container.setBackgroundResource(R.color.black);

        user = (User) JsonUtil.strToObject(SPUtils.getInstance().getString(SpConstant.USER),User.class);

        Glide.with(this).load(user.getAvater()).error(R.mipmap.ic_launcher).into(binding.avater);

    }

    @Override
    public String[] getPermissions() {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    }

    @Override
    public EasyPermission.OnPermissionListener getPermissionListener() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_avater;
    }

    @Override
    public void clickMore() {

    }

    @Override
    public void clickRightText() {
         ImagePickerUtil.startSelect(this,1,true);
    }

    private void updateAvater(final String url){
        final ProgressDialog dialog = ProgressDialog.show(this,"头像更换","更新中");
        OkGo.<String>post(getString(R.string.baseurl)+getString(R.string.updateUser)+user.getId())
                .params("token",user.getToken())
                .params("avater",url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try{
                            UpdateUserResult result = (UpdateUserResult) JsonUtil.strToObject(response.body(),UpdateUserResult.class);
                            if (result.getCode() == 0){
                                user.setAvater(url);
                                SPUtils.getInstance().put(SpConstant.USER,JsonUtil.objToString(result.getData()));
                                EventBus.getDefault().post(user,"updateSuccess");
                            }
                            ToastUtils.showShort(result.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if (dialog.isShowing())
                            dialog.dismiss();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (dialog.isShowing())
                            dialog.dismiss();
                        ToastUtils.showShort(response.message());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(images!=null){
                    if(images.size()!=0){
                        final String key = QiniuUploadUtil.randomFileName(user.getUsername());
                        final ProgressDialog dialog = ProgressDialog.show(this,"头像更换","上传中");
                        QiniuUploadUtil.getUpLoadToken(this,
                                key, new QiniuUploadUtil.TokenListener() {
                                    @Override
                                    public void tokenGet(String token) {
                                        QiniuUploadUtil.upload(new File(images.get(0).path),key,token, new QiniuUploadUtil.UploadListener() {
                                            @Override
                                            public void progress(double percent, String key) {
                                                System.out.println("AvaterActivity.progress "+percent);
                                            }

                                            @Override
                                            public void complete(String key, ResponseInfo info,String url) {
                                                if (dialog.isShowing())
                                                    dialog.dismiss();
                                                if(info.isOK())
                                                    ToastUtils.showShort("图片上传成功");
                                                else
                                                    ToastUtils.showShort("图片上传失败");
                                                Glide.with(AvaterActivity.this).load(url).into(binding.avater);
                                                updateAvater(url);
                                            }
                                        });
                                    }
                                });
                    }
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
