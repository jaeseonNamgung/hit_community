package com.hit.community.dto;

import com.hit.community.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String userName;

    public Member toEntity(){
        return Member.builder().id(id).userName(userName).build();
    }
}
