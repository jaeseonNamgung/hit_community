package com.hit.community.controller;

import com.hit.community.dto.MemberRequest;
import com.hit.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class RestMemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberRequest> createUser(@RequestBody MemberRequest memberRequest){
        memberService.registerMember(memberRequest);
        return ResponseEntity.ok(memberRequest);
    }

}
