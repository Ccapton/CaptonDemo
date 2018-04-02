package com.capton.baseapp.adapter;

import android.content.Context;
import android.view.View;

import com.capton.capton.R;
import com.capton.common.base.CommonViewHolder;

import java.util.List;

/**
 * Created by capton on 2018/3/5.
 */

public class AddPicAdapter extends PicAdapter {

    public static interface OnItemDeletedListener{
        void onItemDeleted(List<String> dataList);
    }
    OnItemDeletedListener onItemDeletedListener;

    public OnItemDeletedListener getOnItemDeletedListener() {
        return onItemDeletedListener;
    }

    public void setOnItemDeletedListener(OnItemDeletedListener onItemDeletedListener) {
        this.onItemDeletedListener = onItemDeletedListener;
    }

    public AddPicAdapter(Context context, List<String> dataList, int resId) {
        super(context, dataList, resId);
    }

    @Override
    public void bindView(CommonViewHolder holder, String data, final int position) {
        super.bindView(holder, data, position);

            holder.getView(R.id.delete).setVisibility(View.VISIBLE);
            holder.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDataList().remove(position);
                    notifyDataSetChanged();
                    if (onItemDeletedListener!=null)
                        onItemDeletedListener.onItemDeleted(getDataList());
                }
            });

    }
}
