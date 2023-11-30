package com.hit.community.controller;

import com.hit.community.dto.CommentDTO;
import com.hit.community.entity.Comment;
import com.hit.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class RestCommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> saveComment(@RequestBody CommentDTO commentDTO) {
        commentService.save(commentDTO);
        return ResponseEntity.ok(commentDTO);
    }

}
