package com.capton.baseapp;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by capton on 2018/3/5.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.top = 0;
        outRect.bottom = space;
        outRect.left = 0;
        outRect.right  = space;
        if(parent.getChildLayoutPosition(view) % 2 == 0){

        }

    }
}