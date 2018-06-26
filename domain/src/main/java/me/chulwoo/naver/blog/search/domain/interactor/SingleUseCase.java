package me.chulwoo.naver.blog.search.domain.interactor;

import io.reactivex.Single;

public interface SingleUseCase<Request, Response> extends UseCase<Request, Single<Response>> {
}
