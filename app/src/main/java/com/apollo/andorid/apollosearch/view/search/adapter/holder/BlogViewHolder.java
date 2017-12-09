package com.apollo.andorid.apollosearch.view.search.adapter.holder;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.apollo.andorid.apollosearch.R;
import com.apollo.andorid.apollosearch.data.Blog;
import com.apollo.andorid.apollosearch.view.base.BaseViewHolder;
import com.apollo.andorid.apollosearch.view.search.adapter.BlogItem;

import butterknife.BindView;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class BlogViewHolder extends BaseViewHolder {

    @BindView(R.id.title) TextView title;
    @BindView(R.id.desc) TextView desc;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.pubdate) TextView pubDate;

    public BlogViewHolder(View itemView) {
        super(itemView);
    }

    public void bindTo(BlogItem blogItem) {
        itemView.setOnClickListener(v -> {
            try {
                blogItem.getOnClickAction().run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Blog blog = blogItem.getBlog();
        title.setText(Html.fromHtml(blog.getTitle()));
        if (TextUtils.isEmpty(blog.getDescription())) {
            desc.setVisibility(View.GONE);
        } else {
            desc.setText(Html.fromHtml(blog.getDescription()));
            desc.setVisibility(View.VISIBLE);
        }
        name.setText(blog.getBloggerName());
        name.setOnClickListener(v -> {
            try {
                blogItem.getOnNameClickAction().run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        pubDate.setText(blogItem.getTimeAgo(itemView.getContext()));
    }
}
