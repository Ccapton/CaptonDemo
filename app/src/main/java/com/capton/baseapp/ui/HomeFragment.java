package com.capton.baseapp.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.capton.baseapp.adapter.DynamicAdapter;
import com.capton.baseapp.bean.DynamicResult;
import com.capton.bd.BottomDialog;
import com.capton.capton.R;
import com.capton.capton.databinding.FragmentHomeBinding;
import com.capton.common.base.BaseFragment;
import com.capton.common.base.JsonUtil;
import com.capton.ep.EasyPermission;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;


/**
 * Created by capton on 2018/3/5.
 */

public class HomeFragment extends BaseFragment <FragmentHomeBinding>{

    DynamicAdapter adapter ;
    int page = 1;
    int size = 10;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getDynamics(true);


        binding.refresh.setEnableLoadmore(true);
        binding.refresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page ++;
                getDynamics(true);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getDynamics(false);

            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialog.Builder builder = new BottomDialog.Builder(getContext());
                builder.setContentView(R.layout.layout_select_addtype);
                final BottomDialog bottomDialog = builder.create();
                View addDynamic = bottomDialog.getContentView().findViewById(R.id.addDynamic);
                addDynamic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtils.startActivity(AddDynamicActivity.class);
                        bottomDialog.dismiss();
                    }
                });
                bottomDialog.show();
            }
        });

    }

    private void getDynamics(final boolean isLoadMore) {
        OkGo.<String>get(getString(R.string.baseurl)+getString(R.string.getDynamicsAll))
                .params("page",page)
                .params("size",size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(response.code() == 200){
                            DynamicResult dynamicResult = (DynamicResult) JsonUtil.strToObject(response.body(),DynamicResult.class);
                            if(adapter == null){
                                adapter = new DynamicAdapter(getContext(),dynamicResult.getData(),R.layout.item_dynamic);
                                binding.homeRv.setAdapter(adapter);
                            }else {
                                if (!isLoadMore)
                                    adapter.getDataList().clear();
                                adapter.getDataList().addAll(dynamicResult.getData());
                                adapter.notifyDataSetChanged();
                            }
                            binding.refresh.finishLoadmore();
                            binding.refresh.finishRefresh();
                        }else {
                            binding.refresh.finishLoadmore();
                            binding.refresh.finishRefresh();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        page--;
                        binding.refresh.finishLoadmore();
                        binding.refresh.finishRefresh();
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public String[] getPermissions() {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
    }

    @Override
    public EasyPermission.OnPermissionListener getPermissionListener() {
        return null;
    }

}
