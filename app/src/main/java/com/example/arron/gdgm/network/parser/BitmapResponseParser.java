package com.example.arron.gdgm.network.parser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.arron.gdgm.network.ResponseParser;

import java.io.InputStream;

/**
 * Created by Arron on 2017/4/13.
 */

public class BitmapResponseParser extends ResponseParser<Bitmap> {
    @Override
    public Bitmap parse(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }
}
