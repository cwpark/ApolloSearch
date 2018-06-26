package me.chulwoo.naver.blog.search.domain.entity;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class PageInfo<Item> {

    private long createdAt;
    private int totalResultCount;
    private List<Item> items;
    @Accessors(fluent = true)
    private boolean hasNext;

    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }
}
