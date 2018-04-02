package com.capton.baseapp.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.capton.baseapp.bean.DynamicResult;
import com.capton.baseapp.bean.PicBean;
import com.capton.capton.R;
import com.capton.common.base.CommonAdapter;
import com.capton.common.base.CommonViewHolder;
import com.capton.common.base.JsonUtil;

import java.util.List;

/**
 * Created by capton on 2018/3/5.
 */

public class DynamicAdapter extends CommonAdapter <DynamicResult.DataBean>{

    PicAdapter picAdapter;
    public DynamicAdapter(Context context, List<DynamicResult.DataBean> dataList, int resId) {
        super(context, dataList, resId);
    }

    @Override
    public void bindView(CommonViewHolder holder, DynamicResult.DataBean data, int position) {
        holder.setText(R.id.nick,data.getAuthor().getNick());
        holder.setText(R.id.content,data.getText());
        holder.setImage(R.id.avater,data.getAuthor().getAvater());

        RecyclerView recyclerView = holder.getView(R.id.picsRv);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        PicBean picBean = (PicBean) JsonUtil.strToObject(data.getPicUrls(), PicBean.class);
        picAdapter = new PicAdapter(context,picBean.getPicList(),R.layout.item_pic);
        recyclerView.setAdapter(picAdapter);

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
