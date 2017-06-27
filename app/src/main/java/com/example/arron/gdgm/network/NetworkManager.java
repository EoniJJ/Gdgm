package com.example.arron.gdgm.network;

import android.os.Handler;
import android.os.Looper;

import com.arron.networklibrary.RequestFactory;
import com.arron.networklibrary.callback.IRequestCallback;
import com.arron.networklibrary.entity.RequestEntity;
import com.arron.networklibrary.entity.ResponseEntity;
import com.example.arron.gdgm.network.parser.StringResponseParser;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Arron on 2017/4/11.
 */

public class NetworkManager {
    public static final int NETWORK_OK = 200;
    private static final Handler handler = new Handler(Looper.getMainLooper());

    private NetworkManager() {

    }

    public static void post(String url, RequestEntity requestEntity, final ResponseParser responseParser, final NetworkCallback networkCallback) {
        RequestFactory.getRequestManager().post(url, requestEntity, new IRequestCallback() {
            @Override
            public void onSuccess(ResponseEntity responseEntity) {
                final String responseUrl = responseEntity.getUrl();
                if (responseEntity.getCode() == NETWORK_OK) {
                    final Object result = responseParser.parse(responseEntity.getResponse());
                    if (result != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                networkCallback.onSuccess(responseUrl, result);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                networkCallback.onException(new IOException());
                            }
                        });
                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            networkCallback.onFailed(responseUrl, "");
                        }
                    });
                }
            }

            @Override
            public void onFailure(final Throwable throwable) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        networkCallback.onException(throwable);
                    }
                });
            }
        });
    }

    public static void post(String url, Map<String, String> formRequestBody, final ResponseParser responseParser, final NetworkCallback networkCallback) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setFormRequestBody(formRequestBody);
        post(url, requestEntity, responseParser, networkCallback);
    }


    public static void get(String url, RequestEntity requestEntity, final ResponseParser responseParser, final NetworkCallback networkCallback) {
        RequestFactory.getRequestManager().get(url, requestEntity, new IRequestCallback() {
            @Override
            public void onSuccess(ResponseEntity responseEntity) {
                final String responseUrl = responseEntity.getUrl();
                if (responseEntity.getCode() == NETWORK_OK) {
                    final Object result = responseParser.parse(responseEntity.getResponse());
                    if (result != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                networkCallback.onSuccess(responseUrl, result);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                networkCallback.onException(new IOException());
                            }
                        });
                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            networkCallback.onFailed(responseUrl, "");
                        }
                    });
                }
            }

            @Override
            public void onFailure(final Throwable throwable) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        networkCallback.onException(throwable);
                    }
                });
            }
        });
    }

    public static void get(String url,  ResponseParser responseParser, final NetworkCallback networkCallback) {
        get(url, null, responseParser, networkCallback);
    }

    public static void get(String url, RequestEntity requestEntity, final NetworkCallback networkCallback) {
        get(url, requestEntity, new StringResponseParser(), networkCallback);
    }

    public static void get(String url, NetworkCallback networkCallback) {
        get(url, new StringResponseParser(), networkCallback);
    }

    public static void post(String url, Map<String, String> formRequestBody, NetworkCallback networkCallback) {
        post(url, formRequestBody, new StringResponseParser(), networkCallback);
    }
    public static void post(String url,RequestEntity requestEntity, NetworkCallback networkCallback) {
        post(url, requestEntity, new StringResponseParser(), networkCallback);
    }
    public static void cancel(Object tag) {
        RequestFactory.getRequestManager().cancel(tag);
    }

}
