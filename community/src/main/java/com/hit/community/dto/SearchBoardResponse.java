package com.hit.community.dto;

import com.hit.community.constant.ActivityCategory;
import com.hit.community.entity.Category;

import java.time.LocalDateTime;

public record SearchBoardResponse(
        String name,
        String title,
        String content,
        int hits,
        Integer pass,
        Category category,
        ActivityCategory activityCategory,
        LocalDateTime createdTime,
        LocalDateTime updatedTime

) {

    public static SearchBoardResponse of(
            String name,
            String title,
            String content,
            int hits,
            Integer pass,
            Category category,
            ActivityCategory activityCategory,
            LocalDateTime createdTime,
            LocalDateTime updatedTime
    ){
        return new SearchBoardResponse
                (name, title, content, hits, pass, category,
                        activityCategory, createdTime, updatedTime);
    }

}
