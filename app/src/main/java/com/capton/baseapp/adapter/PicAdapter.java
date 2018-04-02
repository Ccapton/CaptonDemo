package com.capton.baseapp.adapter;

import android.content.Context;

import com.capton.capton.R;
import com.capton.common.base.CommonAdapter;
import com.capton.common.base.CommonViewHolder;

import java.util.List;

/**
 * Created by capton on 2018/3/5.
 */

public class PicAdapter extends CommonAdapter<String> {



    public PicAdapter(Context context, List<String> dataList, int resId) {
        super(context, dataList, resId);

    }

    @Override
    public void bindView(CommonViewHolder holder, String data, final int position) {
        holder.setImage(R.id.dynamicPic,data);

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
