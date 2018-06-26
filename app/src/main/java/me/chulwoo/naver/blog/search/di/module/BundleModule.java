package me.chulwoo.naver.blog.search.di.module;

import dagger.Module;
import dagger.Provides;
import me.chulwoo.naver.blog.search.ui.base.BaseActivity;
import me.chulwoo.naver.blog.search.BundleService;
import me.chulwoo.naver.blog.search.di.qualifier.extra.Keyword;
import me.chulwoo.naver.blog.search.di.qualifier.extra.Url;
import me.chulwoo.naver.blog.search.di.scope.ActivityScope;
import me.chulwoo.naver.blog.search.ui.ExtraKeys;

@Module
public class BundleModule {

    @Provides
    @ActivityScope
    static BundleService provideBundleService(BaseActivity context) {
        return context.getBundleService();
    }

    @Provides
    @ActivityScope
    @Keyword
    static String provideKeyword(BundleService bundleService) {
        String keyword = (String) bundleService.get(ExtraKeys.KEYWORD);
        return keyword == null ? "" : keyword;
    }

    @Provides
    @ActivityScope
    @Url
    static String provideUrl(BundleService bundleService) {
        String url = (String) bundleService.get(ExtraKeys.URL);
        return url == null ? "" : url;
    }
}
