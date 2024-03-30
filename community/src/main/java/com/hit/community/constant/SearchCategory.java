package com.hit.community.constant;

import lombok.Getter;

@Getter
public enum SearchCategory {
    WRITER, TITLE, STUDY, EXERCISE, HOBBIES, MORE;

    public ActivityCategory getActivityCategory() {
        return null;
    }
}
