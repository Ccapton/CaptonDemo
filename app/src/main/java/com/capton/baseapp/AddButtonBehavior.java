package com.capton.baseapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by capton on 2018/3/5.
 */

public class AddButtonBehavior extends CoordinatorLayout.Behavior {
    public AddButtonBehavior() {
        super();
    }

    public AddButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    // 隐藏动画是否正在执行
    private boolean isAnimatingOut = false;
    // 展示动画是否正在执行
    private boolean isAnimatingIn = false;
    public FloatingActionButton floatingActionButton;

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        floatingActionButton = (FloatingActionButton) child;
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimatingOut && floatingActionButton.getVisibility() == View.VISIBLE) {
            // 手指上滑，隐藏FAB
            scaleHide(listener);
        } else if ((dyConsumed < 0 || dyUnconsumed < 0)&& !isAnimatingIn && floatingActionButton.getVisibility() != View.VISIBLE) {
            scaleShow(listener2);// 手指下滑，显示FAB
        }
    }


    private Animator.AnimatorListener listener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            isAnimatingOut = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAnimatingOut = false;
            floatingActionButton.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            isAnimatingOut = false;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
    private Animator.AnimatorListener listener2 = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            isAnimatingIn = true;
            floatingActionButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAnimatingIn = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            isAnimatingIn = false;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public  void scaleHide(Animator.AnimatorListener listener){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(floatingActionButton,"translationY",0,300);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(floatingActionButton,"alpha",1f,0f);
        animatorSet.playTogether(animator,animator2);
        animatorSet.setDuration(250);
        animatorSet.addListener(listener);
        animatorSet.start();
    }
    public  void scaleShow(Animator.AnimatorListener listener){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(floatingActionButton,"translationY",300,0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(floatingActionButton,"alpha",0f,1f);
        animatorSet.playTogether(animator,animator2);
        animatorSet.setDuration(250);
        animatorSet.addListener(listener);
        animatorSet.start();
    }
}
