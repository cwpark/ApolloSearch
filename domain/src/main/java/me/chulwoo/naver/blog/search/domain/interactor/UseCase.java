package me.chulwoo.naver.blog.search.domain.interactor;

public interface UseCase<Request, Response> {

    Response execute(Request request);
}
