package me.chulwoo.naver.blog.search.data.blog.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.chulwoo.naver.blog.search.data.Converter;
import me.chulwoo.naver.blog.search.data.blog.model.SearchResultDto;
import me.chulwoo.naver.blog.search.domain.entity.PageInfo;

public class PageInfoConverter<T> implements Converter<SearchResultDto<T>, PageInfo<T>> {

    private static final String RFC_1123_DATE_TIME = "EEE, dd MMM yyyy HH:mm:ss z";
    private static final int SEARCH_START_LIMIT = 1000;

    @Override
    public PageInfo<T> convert(SearchResultDto<T> dto) {
        PageInfo<T> pageInfo = new PageInfo<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(RFC_1123_DATE_TIME, Locale.KOREA);
            Date date = dateFormat.parse(dto.getLastBuildDate());
            pageInfo.setCreatedAt(date.getTime());
        } catch (ParseException e) {
            pageInfo.setCreatedAt(0);
        }
        int totalCount = dto.getTotal();
        int start = dto.getStart();
        pageInfo.setTotalResultCount(totalCount);
        boolean hasNext = (start + dto.getDisplay() - 1) != totalCount && start <= SEARCH_START_LIMIT;
        pageInfo.hasNext(hasNext);
        List<T> items = new ArrayList<>();
        if (dto.getItems() != null) {
            items.addAll(dto.getItems());
        }
        pageInfo.setItems(items);
        return pageInfo;
    }
}
