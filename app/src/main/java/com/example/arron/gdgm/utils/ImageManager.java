package com.example.arron.gdgm.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

/**
 * Created by Arron on 2017/4/13.
 */

public class ImageManager {
    private ImageManager(){}
    private static class SingletonHolder {
        private static final ImageManager INSTANCE = new ImageManager();
    }

    public static ImageManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void loadImage(Context context, Bitmap bitmap, ImageView imageView) {
        imageView.setImageBitmap(bitmap);
    }
}
