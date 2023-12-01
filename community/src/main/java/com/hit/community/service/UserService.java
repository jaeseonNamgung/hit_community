package com.hit.community.service;

import com.hit.community.dto.MemberDTO;
import com.hit.community.entity.Member;
import com.hit.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(MemberDTO memberDTO){
        Member member = memberDTO.toEntity();
        userRepository.save(member);
    }

}
