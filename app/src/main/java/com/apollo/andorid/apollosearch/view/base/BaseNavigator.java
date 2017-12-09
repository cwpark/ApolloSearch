package com.apollo.andorid.apollosearch.view.base;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class BaseNavigator implements Navigator {

    private final WeakReference<Activity> activityRef;

    public BaseNavigator(@NonNull Activity activity) {
        this.activityRef = new WeakReference<>(activity);
    }

    public BaseNavigator(@NonNull Fragment fragment) {
        this.activityRef = new WeakReference<>(fragment.getActivity());
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
