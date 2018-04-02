package com.capton.baseapp.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.blankj.utilcode.util.SPUtils;
import com.capton.capton.R;
import com.capton.capton.databinding.ActivitySettingBinding;
import com.capton.common.base.BaseActivity;
import com.capton.common.base.JsonUtil;
import com.capton.common.base.SpConstant;
import com.capton.common.user.User;
import com.capton.ep.EasyPermission;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by capton on 2018/3/5.
 */

public class SettingActivity extends BaseActivity<ActivitySettingBinding>{

    List<String> settingList = new ArrayList<>();

    @Override
    protected void yourOperation() {
          setTitle("设置");

          settingList.add("设置1");
          settingList.add("设置2");

          if(!TextUtils.isEmpty(SPUtils.getInstance().getString(SpConstant.USER))){
              try{
                  User user = (User) JsonUtil.strToObject(SPUtils.getInstance().getString(SpConstant.USER),User.class);
                  if (user !=null)
                      settingList.add("退出登录");
              }catch (Exception e){
                  e.printStackTrace();
              }
          }
          binding.settingLv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,settingList));
          binding.settingLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  if(settingList.get(position).equals("退出登录")){
                      SPUtils.getInstance().remove(SpConstant.USER);
                      EventBus.getDefault().post(true,"logoutSuccess");
                      finish();
                  }
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
        return R.layout.activity_setting;
    }

    @Override
    public void clickMore() {

    }

    @Override
    public void clickRightText() {

    }
}
