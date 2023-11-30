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

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column
    private String commentWriter;

    @Column
    private String commentContents;

    @Builder
    private Comment(Long id,
                    UserAccount userAccount,
                    Board board,
                    String commentWriter,
                    String commentContents,
                    LocalDateTime commentCreatedTime,
                    LocalDateTime commentUpdatedTime){
        this.id=id;
        this.userAccount = userAccount;
        this.board=board;
        this.commentWriter=commentWriter;
        this.commentContents=commentContents;
        this.createdTime=commentCreatedTime;
        this.updatedTime=commentUpdatedTime;
    }

    public CommentDTO toResponseDTO(){
        return CommentDTO.builder()
                .id(id)
                .userId(userAccount.getId())
                .boardId(board.getId())
                .commentWriter(commentWriter)
                .commentContents(commentContents)
                .commentCreatedTime(createdTime)
                .commentUpdatedTime(updatedTime)
                .build();
    }
}
