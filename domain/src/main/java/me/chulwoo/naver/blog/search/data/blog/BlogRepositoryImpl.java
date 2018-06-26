package me.chulwoo.naver.blog.search.data.blog;

import io.reactivex.Single;
import me.chulwoo.naver.blog.search.data.blog.converter.BlogConverter;
import me.chulwoo.naver.blog.search.data.blog.db.BlogDb;
import me.chulwoo.naver.blog.search.data.blog.service.BlogService;
import me.chulwoo.naver.blog.search.domain.entity.PageInfo;
import me.chulwoo.naver.blog.search.domain.entity.Post;
import me.chulwoo.naver.blog.search.domain.entity.Sort;
import me.chulwoo.naver.blog.search.domain.repository.BlogRepository;

public class BlogRepositoryImpl implements BlogRepository {

    private static final int DEFAULT_PAGE_SIZE = 20;

    private final BlogDb db; // doesn't used yet
    private final BlogService service;
    private final BlogConverter converter;

    public BlogRepositoryImpl(BlogDb db, BlogService service, BlogConverter converter) {
        this.db = db;
        this.service = service;
        this.converter = converter;
    }

    @Override
    public Single<PageInfo<Post>> search(String keyword, Sort sort, int page) {
        String sortQuery = "";
        switch (sort) {
            case SIMILLAR:
                sortQuery = "sim";
                break;
            case DATE:
                sortQuery = "date";
                break;
        }

        int pageSize = DEFAULT_PAGE_SIZE;
        int start = ((page - 1) * pageSize) + 1;

        return service.search(keyword, pageSize, start, sortQuery).map(converter::convert);
    }
}
