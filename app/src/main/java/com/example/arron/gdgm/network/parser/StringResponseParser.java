package com.example.arron.gdgm.network.parser;

import com.example.arron.gdgm.network.ResponseParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Arron on 2017/4/11.
 */

public class StringResponseParser extends ResponseParser<String> {
    @Override
    public String parse(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byte[] bytes = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, len);
            }
            return byteArrayOutputStream.toString("gb2312");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                if (byteArrayOutputStream!=null) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
