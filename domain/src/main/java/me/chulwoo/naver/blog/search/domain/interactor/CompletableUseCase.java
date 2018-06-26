package me.chulwoo.naver.blog.search.domain.interactor;

import io.reactivex.Completable;

public interface CompletableUseCase<Request> extends UseCase<Request, Completable> {
}
