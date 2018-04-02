package com.capton.baseapp;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by capton on 2018/3/5.
 */

public class GlideImageLoader implements ImageLoader {



    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
         Glide.with(activity).load(new File(path)).override(150,150).into(imageView);

    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(new File(path)).into(imageView);


    }

    @Override
    public void clearMemoryCache() {

    }
}
