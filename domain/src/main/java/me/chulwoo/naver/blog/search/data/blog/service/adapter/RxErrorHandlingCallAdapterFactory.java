package me.chulwoo.naver.blog.search.data.blog.service.adapter;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import me.chulwoo.naver.blog.search.data.blog.service.exception.RetrofitException;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);

        boolean isCompletable = rawType == Completable.class;
        boolean isObservable = rawType == Observable.class;
        boolean isFlowable = rawType == Flowable.class;
        boolean isSingle = rawType == Single.class;
        boolean isMaybe = rawType == Maybe.class;

        if (!isCompletable && !isObservable && !isFlowable && !isSingle && !isMaybe) {
            return null;
        } else {
            return new RxJava2CallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit),
                    isCompletable, isObservable, isFlowable, isSingle, isMaybe);
        }
    }

    private static class RxJava2CallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private final Retrofit retrofit;
        private final CallAdapter<R, Object> wrapped;
        private final boolean isCompletable;
        private final boolean isObservable;
        private final boolean isFlowable;
        private final boolean isSingle;
        private final boolean isMaybe;

        public RxJava2CallAdapterWrapper(Retrofit retrofit, CallAdapter<R, Object> wrapped,
                                         boolean isCompletable, boolean isObservable, boolean isFlowable, boolean isSingle, boolean isMaybe) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
            this.isCompletable = isCompletable;
            this.isObservable = isObservable;
            this.isFlowable = isFlowable;
            this.isSingle = isSingle;
            this.isMaybe = isMaybe;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object adapt(Call<R> call) {
            if (isCompletable) {
                return ((Completable) wrapped.adapt(call)).onErrorResumeNext(throwable -> Completable.error(parseThrowable(throwable)));
            } else if (isObservable) {
                return ((Observable) wrapped.adapt(call)).onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    @Override
                    public ObservableSource apply(Throwable throwable) throws Exception {
                        return Observable.error(parseThrowable(throwable));
                    }
                });
            } else if (isFlowable) {
                return ((Flowable) wrapped.adapt(call)).onErrorResumeNext(new Function<Throwable, Publisher>() {
                    @Override
                    public Publisher apply(Throwable throwable) throws Exception {
                        return Flowable.error(parseThrowable(throwable));
                    }
                });
            } else if (isSingle) {
                return ((Single) wrapped.adapt(call)).onErrorResumeNext(new Function<Throwable, SingleSource>() {
                    @Override
                    public SingleSource apply(Throwable throwable) throws Exception {
                        return Single.error(parseThrowable(throwable));
                    }
                });
            } else if (isMaybe) {
                return ((Maybe) wrapped.adapt(call)).onErrorResumeNext(new Function<Throwable, MaybeSource>() {
                    @Override
                    public MaybeSource apply(Throwable throwable) throws Exception {
                        return Maybe.error(parseThrowable(throwable));
                    }
                });
            } else {
                return null;
            }
        }

        private Exception parseThrowable(Throwable throwable) {

            // We had non-200 http error
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit);
            }

            // A network error happened
            if (throwable instanceof IOException) {
                return RetrofitException.networkError((IOException) throwable);
            }

            // We don't know what happened. We need to simply convert to an unknown error

            return RetrofitException.unexpectedError(throwable);
        }
    }
}
