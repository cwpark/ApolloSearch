package me.chulwoo.naver.blog.search.util;

import android.util.Log;

import me.chulwoo.naver.blog.search.Config;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class DLog {//implements HttpLoggingInterceptor.Logger {

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
}
