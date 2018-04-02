package com.capton.baseapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.capton.baseapp.adapter.AddPicAdapter;
import com.capton.baseapp.ImagePickerUtil;
import com.capton.baseapp.bean.AddDynamicResult;
import com.capton.baseapp.bean.PicBean;
import com.capton.baseapp.upload.QiniuUploadUtil;
import com.capton.capton.R;
import com.capton.capton.databinding.ActivityAddDynamicBinding;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.capton.baseapp.ImagePickerUtil.IMAGE_PICKER;

/**
 * Created by capton on 2018/3/5.
 */

public class AddDynamicActivity extends BaseActivity <ActivityAddDynamicBinding> implements AddPicAdapter.OnItemDeletedListener {

    private int MAX_PIC_NUM = 4;
    private int leftPicNum = 4;
    private AddPicAdapter addPicAdapter;

    @Override
    protected void yourOperation() {

        setTitle("发布动态");
        setRightText("发布");
        setShowRightText(true);

            binding.addPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImagePickerUtil.startSelect(AddDynamicActivity.this,leftPicNum,false);
                }
            });



    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    public EasyPermission.OnPermissionListener getPermissionListener() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_dynamic;
    }

    @Override
    public void clickMore() {

    }

    List<String> uploadedList = new ArrayList<>();
    ProgressDialog dialog;
    private void uploadPic(final File file, final String key, final User user){
        if (dialog == null)
        dialog = ProgressDialog.show(this,"发布动态","上传图片");
        QiniuUploadUtil.getUpLoadToken(this,key, new QiniuUploadUtil.TokenListener() {
            @Override
            public void tokenGet(String token) {
                QiniuUploadUtil.upload(file,key, token, new QiniuUploadUtil.UploadListener() {
                    @Override
                    public void progress(double percent, String key) {
                        System.out.println("AddDynamicActivity.progress key:"+key+" percent:"+percent);
                    }

                    @Override
                    public void complete(String key, ResponseInfo info, String url) {
                        System.out.println("AddDynamicActivity.complete "+info.toString());
                        System.out.println("AddDynamicActivity.complete "+url);
                        if (dialog.isShowing())
                            dialog.dismiss();
                        uploadedList.add(url);
                        if(uploadedList.size() == addPicAdapter.getDataList().size()) {
                            recordToServer(new PicBean(), user);
                            if (info.isOK())
                                ToastUtils.showShort("上传图片成功");
                            else
                                ToastUtils.showShort("上传图片失败");
                        }
                    }
                });
            }
        });
    }

    private void recordToServer(PicBean picBean,User user){
        picBean.setPicList(uploadedList);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"发布动态","正在发布");
        OkGo.<String>post(getString(R.string.baseurl) + getString(R.string.addDynamic) + user.getId())
                .params("token", user.getToken())
                .params("text", binding.content.getText().toString())
                .params("picUrls", JsonUtil.objToString(picBean))
                .params("nick", user.getNick())
                .params("avater", user.getAvater())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            AddDynamicResult result = (AddDynamicResult) JsonUtil.strToObject(response.body(), AddDynamicResult.class);
                            if (result.getCode() == 0) {
                                ToastUtils.showShort("发表成功");
                                finish();
                            } else {
                                ToastUtils.showShort("发表失败，请尝试重新登录后操作");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }

    private String  randomFileName(String username){
        long current = System.currentTimeMillis();
         return username + current;
    }

    @Override
    public void clickRightText() {
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(SpConstant.USER))) {
            try {
                User user = (User) JsonUtil.strToObject(SPUtils.getInstance().getString(SpConstant.USER), User.class);
                if (user != null) {
                    if (addPicAdapter != null) {
                        if (addPicAdapter.getDataList().size() ==0 ){
                            ToastUtils.showShort("请选择图片");
                        }else {
                            for (int i = 0; i < addPicAdapter.getDataList().size(); i++) {
                                uploadPic(new File(addPicAdapter.getDataList().get(i)),randomFileName(user.getUsername()),user);
                            }
                        }
                    } else {
                        ToastUtils.showShort("请选择图片");

                    }
                }else {
                    ToastUtils.showShort("登录后操作");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(images!=null){
                    if(images.size()!=0){
                        List<String> picList= new ArrayList<>();
                        for (int i = 0; i < images.size(); i++) {
                            picList.add(images.get(i).path);
                        }
                        if (addPicAdapter == null){
                            addPicAdapter = new AddPicAdapter(this,picList,R.layout.item_pic);
                            addPicAdapter.setOnItemDeletedListener(AddDynamicActivity.this);
                            binding.picsRv.setAdapter(addPicAdapter);
                        } else {
                            addPicAdapter.getDataList().addAll(picList);
                            addPicAdapter.notifyDataSetChanged();
                        }
                        leftPicNum = MAX_PIC_NUM - addPicAdapter.getDataList().size();
                        if (leftPicNum <= 0){
                            binding.addPic.setVisibility(View.GONE);
                        }
                    }
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemDeleted(List<String> dataList) {
        leftPicNum = MAX_PIC_NUM - dataList.size();
        if (leftPicNum <= 0){
            binding.addPic.setVisibility(View.GONE);
        } else {
            binding.addPic.setVisibility(View.VISIBLE);
        }
    }
}
