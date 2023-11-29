package com.hit.community.dto;

import com.hit.community.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;

    public User toEntity(){
        return User.builder().id(id).build();
    }
}
