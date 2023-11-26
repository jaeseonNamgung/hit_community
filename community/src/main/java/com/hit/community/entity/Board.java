package com.hit.community.entity;

import com.hit.community.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Board extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // auto_increment
    private Long id;

    @Column
    private Long userId;

    @Column(length = 20, nullable = true) // nullable = false
    private String boardWriter;

    @Column // defalt는 크기 255, null 가능
    private Integer boardPass;

    @Column
    private String boardTitle;

    @Column(length = 800)
    private String boardContents;

    @Column
    private int boardHits = 0;

    @Builder
    private Board(Long id,
                  Long userId,
                  String boardWriter,
                  Integer boardPass,
                  String boardTitle,
                  String boardContents,
                  int boardHits,
                  LocalDateTime boardCreatedTime,
                  LocalDateTime boardUpdatedTime) {
        this.id = id;
        this.userId = userId;
        this.boardWriter = boardWriter;
        this.boardPass = boardPass;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.createdTime = boardCreatedTime;
        this.updatedTime = boardUpdatedTime;
    }

    public BoardDTO toResponseDto() {
        return BoardDTO.builder()
                .id(id)
                .userId(userId)
                .boardWriter(boardWriter)
                .boardPass(boardPass)
                .boardTitle(boardTitle)
                .boardContents(boardContents)
                .boardHits(boardHits)
                .boardCreatedTime(createdTime)
                .boardUpdatedTime(updatedTime)
                .build();
    }
}
