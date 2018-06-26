package me.chulwoo.naver.blog.search.domain.repository;

import io.reactivex.Single;
import me.chulwoo.naver.blog.search.domain.entity.PageInfo;
import me.chulwoo.naver.blog.search.domain.entity.Post;
import me.chulwoo.naver.blog.search.domain.entity.Sort;

public interface BlogRepository {

    Single<PageInfo<Post>> search(String keyword, Sort sort, int page);
}
