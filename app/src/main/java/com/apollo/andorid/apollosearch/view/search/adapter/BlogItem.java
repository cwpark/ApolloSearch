package com.apollo.andorid.apollosearch.view.search.adapter;

import android.content.Context;

import com.apollo.andorid.apollosearch.R;
import com.apollo.andorid.apollosearch.data.Blog;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;

import io.reactivex.functions.Action;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class BlogItem {

    private Blog blog;
    private Action onClickAction;
    private Action onNameClickAction;

    public BlogItem(Blog blog, Action onClickAction, Action onNameClickAction) {
        this.blog = blog;
        this.onClickAction = onClickAction;
        this.onNameClickAction = onNameClickAction;
    }

    public Blog getBlog() {
        return blog;
    }

    public String getTimeAgo(Context context) {
        if (blog.getPostDate() == null) {
            return "";
        }

        Period period = Period.between(blog.getPostDate(), LocalDate.now());
        if (period.getYears() > 0) {
            return context.getString(R.string.format_years_ago, period.getYears());
        } else if (period.getMonths() > 0) {
            return context.getString(R.string.format_months_ago, period.getMonths());
        } else {
            if (period.getDays() > 0) {
                return context.getString(R.string.format_days_ago, period.getDays());
            } else {
                return context.getString(R.string.today);
            }
        }
    }

    public Action getOnClickAction() {
        return onClickAction;
    }

    public Action getOnNameClickAction() {
        return onNameClickAction;
    }
}
