package me.chulwoo.naver.blog.search.data.di.module;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.chulwoo.naver.blog.search.data.blog.service.BlogService;
import me.chulwoo.naver.blog.search.data.blog.service.adapter.RxErrorHandlingCallAdapterFactory;
import me.chulwoo.naver.blog.search.data.blog.service.interceptor.NaverRequestInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServiceModule {

    // TODO: flavor
    private static final String BASE_URL = "https://openapi.naver.com/";

    @Provides
    @Singleton
    BlogService provideBlogService(Retrofit retrofit) {
        return retrofit.create(BlogService.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder(interceptors()).build())
                .build();
    }

    List<Interceptor> interceptors() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new NaverRequestInterceptor());
        return interceptors;
    }

    OkHttpClient.Builder clientBuilder(List<Interceptor> interceptors) {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
        for (Interceptor interceptor : interceptors) {
            okClientBuilder.addInterceptor(interceptor);
        }
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // TODO flavor
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);
        return okClientBuilder;
    }
}
