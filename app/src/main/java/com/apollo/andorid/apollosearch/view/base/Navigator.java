package com.apollo.andorid.apollosearch.view.base;

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
