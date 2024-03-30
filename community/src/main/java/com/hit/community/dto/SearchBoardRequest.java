package com.hit.community.dto;

import com.hit.community.constant.SearchCategory;
import com.hit.community.entity.Category;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;

public record SearchBoardRequest(
        SearchCategory searchCategory,
        String text,
        PageRequest pageRequest
) {
    public static SearchBoardRequest of(
            SearchCategory searchCategory,
            String text,
            PageRequest pageRequest
            ){
        return new SearchBoardRequest(searchCategory, text, pageRequest);
    }
}
