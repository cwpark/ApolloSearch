package me.chulwoo.naver.blog.search.ui.search.adapter;

import java.util.List;

import me.chulwoo.naver.blog.search.domain.entity.Post;

public interface PostAdapterContract {

    interface View {

        void bind(Presenter presenter, List<Post> posts);

        void notifyDataSetChanged();

        void notifyItemRangeInserted(int positionStart, int itemCount);
    }

    interface Presenter {

        void onPostClick(int position);

        void onBloggerNameClick(int position);
    }
}
