package com.hit.community.controller;

import com.hit.community.dto.MemberDTO;
import com.hit.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class RestUserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<MemberDTO> createUser(@RequestBody MemberDTO memberDTO){
        userService.save(memberDTO);
        return ResponseEntity.ok(memberDTO);
    }

}
