package com.hit.community.entity;

import com.hit.community.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long boardId;

    @Column
    private String commentWriter;

    @Column
    private String commentContents;

    @Builder
    private Comment(Long id,
                    Long userId,
                    Long boardId,
                    String commentWriter,
                    String commentContents,
                    LocalDateTime commentCreatedTime,
                    LocalDateTime commentUpdatedTime){
        this.id=id;
        this.userId=userId;
        this.boardId=boardId;
        this.commentWriter=commentWriter;
        this.commentContents=commentContents;
        this.createdTime=commentCreatedTime;
        this.updatedTime=commentUpdatedTime;
    }

    public CommentDTO toResponseDTO(){
        return CommentDTO.builder()
                .id(id)
                .userId(userId)
                .boardId(boardId)
                .commentWriter(commentWriter)
                .commentContents(commentContents)
                .commentCreatedTime(createdTime)
                .commentUpdatedTime(updatedTime)
                .build();
    }
}
