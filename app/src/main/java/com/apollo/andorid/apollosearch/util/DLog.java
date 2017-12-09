package com.apollo.andorid.apollosearch.util;

import android.text.TextUtils;
import android.util.Log;

import com.apollo.andorid.apollosearch.Config;
import com.apollo.andorid.apollosearch.data.source.remote.exception.RetrofitException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class DLog implements HttpLoggingInterceptor.Logger {

    public static final int LOG_MAX_LENGTH = 4000;

    public static void e(Object obj, Throwable e, Object... messages) {
        DLog.e(obj.getClass(), e, messages);
    }

    public static void e(Object obj, Object... messages) {
        DLog.e(obj.getClass(), messages);
    }

    public static void e(Throwable e) {
        if (Config.DEBUG) {
            e.printStackTrace();
        }

        if (e instanceof RetrofitException) {
            RetrofitException retrofitException = (RetrofitException) e;
            if (retrofitException.getKind().equals(RetrofitException.Kind.NETWORK) ||
                    retrofitException.getKind().equals(RetrofitException.Kind.HTTP)) {
                return;
            }
        }

        // report to crash analytics tools
    }

    public static void e(Class cls, Object... messages) {
        if (Config.DEBUG) {
            for (Object message : messages) {
                Log.e(cls.getSimpleName(), message.toString());
            }
        } else {
            // report to crash analytics tools
        }
    }

    public static void w(Object obj, Object... messages) {
        w(obj.getClass(), messages);
    }

    public static void w(Class cls, Object... messages) {
        if (Config.DEBUG) {
            for (Object message : messages) {
                Log.w(cls.getSimpleName(), message.toString());
            }
        }
    }

    public static void i(Object obj, Object... messages) {
        i(obj.getClass(), messages);
    }

    public static void i(Class cls, Object... messages) {
        if (Config.DEBUG) {
            for (Object message : messages) {
                Log.i(cls.getSimpleName(), message.toString());
            }
        }
    }

    public static void d(Object obj, Object... messages) {
        d(obj.getClass(), messages);
    }

    public static void d(Class cls, Object... messages) {
        if (Config.DEBUG) {
            for (Object message : messages) {
                Log.d(cls.getSimpleName(), message.toString());
            }
        }
    }

    public static void v(Object obj, Object... messages) {
        v(obj.getClass(), messages);
    }

    public static void v(String delimiter, Class cls, Object... messages) {
        if (Config.DEBUG) {
            for (Object message : messages) {
                Log.v(cls.getSimpleName(), message.toString());
            }
        }
    }

    private static String convertPretty(String src) {
        JsonElement json = convertJson(src);
        if (json != null) {
            return prettyJson(json);
        }

        return src;
    }

    protected static String convertPretty(CharSequence delimiter, Object... message) {
        List<String> prettyStringList = new LinkedList<>();

        for (Object msg : message) {
            if (msg == null) {
                prettyStringList.add(convertPretty("null"));
            } else {
                prettyStringList.add(convertPretty(msg.toString()));
            }
        }

        return TextUtils.join(delimiter, prettyStringList);
    }

    private static JsonElement convertJson(String src) {
        try {
            JsonElement result = new JsonParser().parse(src);
            if (result.isJsonNull()) {
                return null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    private static String prettyJson(JsonElement src) {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create()
                .toJson(src);
    }

    @Override
    public void log(String message) {
        DLog.d(OkHttpClient.class, convertPretty(message));
    }
}
