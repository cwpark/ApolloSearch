package me.chulwoo.naver.blog.search.domain.interactor.search;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.chulwoo.naver.blog.search.domain.entity.PageInfo;
import me.chulwoo.naver.blog.search.domain.entity.Post;
import me.chulwoo.naver.blog.search.domain.entity.Sort;
import me.chulwoo.naver.blog.search.domain.interactor.SingleUseCase;
import me.chulwoo.naver.blog.search.domain.repository.BlogRepository;

public class SearchBlogPosts implements SingleUseCase<SearchBlogPosts.Params, PageInfo<Post>> {

    private final BlogRepository repository;

    public SearchBlogPosts(@NonNull BlogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<PageInfo<Post>> execute(Params params) {
        return repository.search(params.keyword, params.sort, params.page);
    }

    @Data
    @AllArgsConstructor
    public static class Params {

        private String keyword;
        private Sort sort;
        private int page;
    }
}
