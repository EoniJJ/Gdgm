package com.example.arron.gdgm.network;

import java.io.InputStream;

/**
 * Created by Arron on 2017/4/11.
 */

public abstract class ResponseParser<T> {
    public abstract T parse(InputStream inputStream);
}
