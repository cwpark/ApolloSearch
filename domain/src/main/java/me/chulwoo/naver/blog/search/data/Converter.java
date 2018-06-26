package me.chulwoo.naver.blog.search.data;

public interface Converter<F, T> {

    T convert(F f);
}
