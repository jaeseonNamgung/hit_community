package com.hit.community.entity;

import com.hit.community.constant.ActivityCategory;
import com.hit.community.dto.BoardDTO;
import com.hit.community.dto.SearchBoardResponse;
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
public class Board extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // auto_increment
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column // defalt는 크기 255, null 가능
    private Integer boardPass;

    @Column
    private String boardTitle;

    @Column // (length = 800)
    private String boardContents;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int boardHits;

    @Enumerated(EnumType.STRING)
    @Column
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column
    private ActivityCategory activityCategory;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    private Board(Long id,
                  Member member,
                  Integer boardPass,
                  String boardTitle,
                  String boardContents,
                  int boardHits,
                  Category category,
                  ActivityCategory activityCategory,
                  LocalDateTime boardCreatedTime,
                  LocalDateTime boardUpdatedTime) {
        this.id = id;
        this.member = member;
        this.boardPass = boardPass;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.category = category;
        this.activityCategory = activityCategory;
        this.createdTime = boardCreatedTime;
        this.updatedTime = boardUpdatedTime;
    }

    public BoardDTO toResponseDto() {
        return BoardDTO.builder()
                .id(id)
                .userId(member.getId())
                .boardPass(boardPass)
                .boardTitle(boardTitle)
                .boardContents(boardContents)
                .boardHits(boardHits)
                .boardCreatedTime(createdTime)
                .boardUpdatedTime(updatedTime)
                .build();
    }
    public SearchBoardResponse toSearchBoardRequest() {
        return SearchBoardResponse.of(
                member.getName(),
                boardTitle,
                boardContents,
                boardHits,
                boardPass,
                category,
                activityCategory,
                createdTime,
                updatedTime);
    }
}
