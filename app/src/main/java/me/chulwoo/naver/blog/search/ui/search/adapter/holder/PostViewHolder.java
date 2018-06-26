package me.chulwoo.naver.blog.search.ui.search.adapter.holder;

import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import me.chulwoo.naver.blog.search.R;
import me.chulwoo.naver.blog.search.domain.entity.Post;
import me.chulwoo.naver.blog.search.ui.base.BaseViewHolder;
import me.chulwoo.naver.blog.search.ui.search.adapter.PostAdapterContract;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class PostViewHolder extends BaseViewHolder {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.pubdate)
    TextView pubDate;

    private final PostAdapterContract.Presenter presenter;

    public PostViewHolder(View itemView, PostAdapterContract.Presenter presenter) {
        super(itemView);
        this.presenter = presenter;
    }

    public void bindTo(Post post, int position) {
        itemView.setOnClickListener(v -> presenter.onPostClick(position));
        title.setText(Html.fromHtml(post.getTitle()));
        if (TextUtils.isEmpty(post.getSummary())) {
            desc.setVisibility(View.GONE);
        } else {
            desc.setText(Html.fromHtml(post.getSummary()));
            desc.setVisibility(View.VISIBLE);
        }
        name.setText(post.getBloggerName());
        name.setOnClickListener(v -> presenter.onBloggerNameClick(position));
        pubDate.setText(DateUtils.getRelativeTimeSpanString(post.getCreatedAt()));
    }
}
