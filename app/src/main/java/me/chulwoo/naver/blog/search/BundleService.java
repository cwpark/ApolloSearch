package me.chulwoo.naver.blog.search;

import android.os.Bundle;

public class BundleService {

    private final Bundle data;

    public BundleService(Bundle savedState, Bundle intentExtras) {
        data = new Bundle();
        if (savedState != null) {
            data.putAll(savedState);
        }

        if (intentExtras != null) {
            data.putAll(intentExtras);
        }
    }

    public Object get(String key) {
        return data.get(key);
    }

    public Bundle getAll() {
        return data;
    }
}
