package com.hit.community.dto;

import com.hit.community.entity.UserAccount;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;

    public UserAccount toEntity(){
        return UserAccount.builder().id(id).userName(userName).build();
    }
}
