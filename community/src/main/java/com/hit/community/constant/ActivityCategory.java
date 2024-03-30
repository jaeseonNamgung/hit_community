package com.hit.community.constant;

public enum ActivityCategory {
    STUDY, EXERCISE, HOBBIES, MORE;

    public static ActivityCategory getCategory(SearchCategory category) {
        switch (category.name()){
            case "STUDY" ->{
                return STUDY;
            }case "EXERCISE" ->{
                return EXERCISE;
            }case "HOBBIES" ->{
                return HOBBIES;
            }default -> {
                return MORE;
            }
        }
    }
}
