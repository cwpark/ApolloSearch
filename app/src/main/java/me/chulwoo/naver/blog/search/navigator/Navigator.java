package me.chulwoo.naver.blog.search.navigator;

import android.content.Intent;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public interface Navigator {

    Intent intentFor(Class cls);

    void start(Intent intent);

    void startActivity(Class cls);

    void finish();
}
