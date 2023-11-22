package com.hit.community.repository;

import com.hit.community.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void crud(){    // create, read, update, delete
        boardRepository.save(new Board());
        System.out.println(">>> " + boardRepository.findAll());
    }
}