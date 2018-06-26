package me.chulwoo.naver.blog.search.navigator;

import android.content.Intent;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import me.chulwoo.naver.blog.search.ui.base.BaseActivity;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class NavigatorImpl implements Navigator {

    private final WeakReference<BaseActivity> activityRef;

    public NavigatorImpl(@NonNull BaseActivity activity) {
        this.activityRef = new WeakReference<>(activity);
    }

    @Override
    public Intent intentFor(Class cls) {
        if (activityRef.get() != null) {
            return new Intent(activityRef.get(), cls);
        }

        return null;
    }

    @Override
    public void start(Intent intent) {
        if (activityRef.get() != null && intent != null) {
            activityRef.get().startActivity(intent);
        }
    }

    @Override
    public void startActivity(Class cls) {
        if (activityRef.get() != null) {
            Intent intent = new Intent(activityRef.get(), cls);
            activityRef.get().startActivity(intent);
        }
    }

    @Override
    public void finish() {
        if (activityRef.get() != null) {
            activityRef.get().finish();
        }
    }
}
