package com.apollo.andorid.apollosearch.data.source.remote.interceptor;

import com.apollo.andorid.apollosearch.util.DLog;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class NaverRequestInterceptor implements Interceptor {

    private static final String NAVER_CLIENT_KEY = "wtA3cqUUCmvk6sDPpxln";
    private static final String NAVER_CLIENT_SECRET = "YXjbTJtMEW";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Headers headers = request.headers().newBuilder()
                .add("X-Naver-Client-Id", NAVER_CLIENT_KEY)
                .add("X-Naver-Client-Secret", NAVER_CLIENT_SECRET)
                .build();
        request = request.newBuilder().headers(headers).build();
        try {
            return chain.proceed(request);
        } catch (Exception e) {
            DLog.e(this, "onError: " + e + ", " + request.url());
            throw e;
        }
    }
}
