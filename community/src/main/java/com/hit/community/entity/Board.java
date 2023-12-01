package com.hit.community.entity;

import com.hit.community.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // auto_increment
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount userAccount;

    // 삭제
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

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    private Board(Long id,
                  UserAccount userAccount,
                  String boardWriter,
                  Integer boardPass,
                  String boardTitle,
                  String boardContents,
                  int boardHits,
                  LocalDateTime boardCreatedTime,
                  LocalDateTime boardUpdatedTime) {
        this.id = id;

        this.userAccount = userAccount;

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
                .userId(userAccount.getId())
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
