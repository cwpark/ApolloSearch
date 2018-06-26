package me.chulwoo.naver.blog.search.ui.base;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class ErrorMessageModel {

    @StringRes int resourceId = 0;
    String message = "";

    public ErrorMessageModel(int resourceId) {
        this.resourceId = resourceId;
    }

    public ErrorMessageModel(String message) {
        this.message = message;
    }

    public String getErrorMessage(Context context) {
        if (resourceId != 0) {
            return context.getString(resourceId);
        } else {
            return message;
        }
    }
}
