package com.hit.community.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {
    NOTICE("CATEGORY_NOTICE", "공지"),
    FREE("CATEGORY_FREE", "자유"),
    QUESTION("CATEGORY_QUESTION", "질문");

    private final String key;
    private final String title;
}
