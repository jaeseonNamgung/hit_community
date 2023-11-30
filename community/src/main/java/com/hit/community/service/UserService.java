package com.hit.community.service;

import com.hit.community.dto.UserDTO;
import com.hit.community.entity.UserAccount;
import com.hit.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(UserDTO userDTO){
        UserAccount userAccount = userDTO.toEntity();
        userRepository.save(userAccount);
    }

}
