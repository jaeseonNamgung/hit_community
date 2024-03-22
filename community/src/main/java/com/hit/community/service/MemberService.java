package com.hit.community.service;

import com.hit.community.dto.MemberRequest;
import com.hit.community.dto.MemberResponse;
import com.hit.community.entity.Member;
import com.hit.community.entity.Role;
import com.hit.community.repository.MemberInfoRepository;
import com.hit.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;

    @Transactional
    public Member registerMember(MemberRequest memberRequest) {
        //
        Member member = Member.builder()
                .name(memberRequest.name())
                .email(memberRequest.email())
                .profile(memberRequest.profile())
                .role(Role.USER)
                .build();
        return memberRepository.save(member);
    }

    public MemberResponse getMember(Long id){
        return memberRepository.findById(id).map(MemberResponse::fromMemberResponse)
                .orElseThrow(RuntimeException::new);
    }

}
