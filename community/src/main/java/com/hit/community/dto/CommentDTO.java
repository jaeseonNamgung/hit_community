package com.hit.community.dto;

import com.hit.community.entity.Board;
import com.hit.community.entity.Comment;
import com.hit.community.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private Long userId;
    private Long boardId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime commentCreatedTime;
    private LocalDateTime commentUpdatedTime;

    @Builder
    public CommentDTO(Long id,
                      Long userId,
                      Long boardId,
                      String commentWriter,
                      String commentContents,
                      LocalDateTime commentCreatedTime,
                      LocalDateTime commentUpdatedTime){
        this.id = id;
        this.userId = userId;
        this.boardId = boardId;
        this.commentWriter = commentWriter;
        this.commentContents = commentContents;
        this.commentCreatedTime = commentCreatedTime;
        this.commentUpdatedTime = commentUpdatedTime;
    }

    // entity 를 dto 메서드의 매개변수로 받는 방법에 문제가 있는가?
    public Comment toEntity(User user, Board board) {
        return Comment.builder()
                .id(id)
                .user(user)
                .board(board)
                .commentWriter(commentWriter)
                .commentContents(commentContents)
                .commentCreatedTime(commentCreatedTime)
                .commentUpdatedTime(commentUpdatedTime)
                .build();
    }

}
