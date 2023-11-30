package com.hit.community.controller;

import com.hit.community.dto.UserDTO;
import com.hit.community.service.UserService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class RestUserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity.ok(userDTO);
    }

}
