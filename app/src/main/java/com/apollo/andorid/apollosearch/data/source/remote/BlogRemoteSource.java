package com.apollo.andorid.apollosearch.data.source.remote;

import com.apollo.andorid.apollosearch.Config;
import com.apollo.andorid.apollosearch.data.Blog;
import com.apollo.andorid.apollosearch.data.SearchResult;
import com.apollo.andorid.apollosearch.data.Sort;
import com.apollo.andorid.apollosearch.data.source.BlogSource;
import com.apollo.andorid.apollosearch.data.source.remote.api.BlogApi;
import com.apollo.andorid.apollosearch.data.source.remote.base.RxErrorHandlingCallAdapterFactory;
import com.apollo.andorid.apollosearch.data.source.remote.interceptor.NaverRequestInterceptor;
import com.apollo.andorid.apollosearch.util.DLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class BlogRemoteSource implements BlogSource {

    private BlogApi blogApi;

    public BlogRemoteSource() {
        Retrofit retrofit = createRetrofit();
        blogApi = retrofit.create(BlogApi.class);
    }

    @Override
    public Maybe<SearchResult<Blog>> search(String keyword, int display, int start, Sort sort) {
        String sortQuery = "";
        switch (sort) {
            case SIMILLAR:
                sortQuery = "sim";
                break;
            case DATE:
                sortQuery = "date";
                break;
        }
        
        return blogApi.search(keyword, display, start, sortQuery);
    }

    private static final String BASE_URL = "https://openapi.naver.com/";

    protected Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getClientBuilder(getInterceptors()).build())
                .build();
    }

    protected Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
    }

    protected List<Interceptor> getInterceptors() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new NaverRequestInterceptor());
        return interceptors;
    }

    protected OkHttpClient.Builder getClientBuilder(List<Interceptor> interceptors) {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
        for (Interceptor interceptor : interceptors) {
            okClientBuilder.addInterceptor(interceptor);
        }
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new DLog());
        httpLoggingInterceptor.setLevel(Config.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);
        return okClientBuilder;
    }
}
